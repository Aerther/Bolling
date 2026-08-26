package football.core.engine;

import java.util.Vector;

import football.main.Constants;
import football.math.Vector2d;
import football.model.entity.Ball;
import football.model.entity.Button;
import football.model.entity.Player;
import football.model.enums.GoalSide;

public class PhysicsEngine {
	
	public static boolean didCollide(Button bt1, Button bt2) {
		Vector2d delta = bt1.position.subtract(bt2.position);
		
		int radiusSum = (bt1.radius + bt2.radius);
		
		float distance = delta.length();
		
		return distance <= radiusSum;
	}
	
	public static void doCollision(Button bt1, Button bt2) {
		float inverseMassSum = bt1.inverseMass + bt2.inverseMass;
		
		Vector2d delta = bt1.position.subtract(bt2.position);

		float distance = delta.length();
		
		if(distance == 0f) {
			delta = new Vector2d(0, 1);
			distance = 1;
		}
		
		Vector2d normal = delta.normalize();
		
		float penetration = bt1.radius + bt2.radius - distance;
		
		Vector2d mtd = normal.multiply(penetration / (inverseMassSum));
		
		bt1.position = bt1.position.add(mtd.multiply(bt1.inverseMass));
		bt2.position = bt2.position.add(mtd.multiply(- bt2.inverseMass));
		
		Vector2d relativeVelocity = bt1.velocity.subtract(bt2.velocity);
		
		float separationVelocity = relativeVelocity.dot(normal);
		
		// The buttons are already going off course of each other
		if(separationVelocity > 0.0f) return;
		
		float newSeparationVelocity = - separationVelocity * ((bt1.material.getRestitution() + bt2.material.getRestitution()) / 2f);
		
		float difference = newSeparationVelocity - separationVelocity;
		
		float impulseCalc = difference / (inverseMassSum);
		
		Vector2d impulse = normal.multiply(impulseCalc);
		
		if(bt2.isBall()) {
			bt1.velocity = bt1.velocity.add(impulse.multiply(bt1.inverseMass));
		}
		
		if(bt2.isBall()) {
			bt2.velocity = bt2.velocity.add(impulse.multiply(- bt2.inverseMass));
		}
	}
	
	public static void doKicking(Player player, Ball ball) {
		Vector2d delta = ball.position.subtract(player.position);
	    float distance = delta.length();

	    if(distance == 0f) {
	    	delta = new Vector2d(0, 1);
	    	distance = 1;
	    }
	    
	    Vector2d normal = delta.normalize();

	    ball.velocity = ball.velocity.add(normal.multiply(Constants.KICK_POWER));
	}
	
	public static void letBallInsideWalls(Ball ball) {
		if (ball == null) return;

	    float pitchCenterY = Constants.WINDOW_HEIGHT / 2.0f;
	    float goalTop = pitchCenterY - (Constants.GOAL_HEIGHT / 2.0f);
	    float goalBottom = pitchCenterY + (Constants.GOAL_HEIGHT / 2.0f);

	    boolean inGoalY = (ball.upperLimit() >= goalTop) && (ball.bottomLimit() <= goalBottom);

	    if (inGoalY) {
	        boolean pastLeftGoalLine = (ball.leftLimit() < Constants.PITCH_OFFSET);
	        boolean pastRightGoalLine = (ball.rightLimit() > Constants.WINDOW_WIDTH - Constants.PITCH_OFFSET);

	        if (pastLeftGoalLine || pastRightGoalLine) {
	            if (ball.upperLimit() < goalTop) {
	                ball.position.setY(goalTop + ball.radius);
	                
	                ball.velocity.setY(-ball.velocity.getY());
	            } else if (ball.bottomLimit() > goalBottom) {
	                ball.position.setY(goalBottom - ball.radius);
	                
	                ball.velocity.setY(-ball.velocity.getY());
	            }
	            
	            return;
	        }
	    }

	    PhysicsEngine.letInsideWalls(ball);
	}
	
	public static void letInsideWalls(Button button) {
		if (button == null) return;

	    boolean collidedX = false;
	    boolean collidedY = false;
	    
	    boolean isBall = button.isBall();
	    float offset = isBall ? Constants.PITCH_OFFSET : 0;

	    float minX = offset;
	    float minY = offset;
	    float maxX = Constants.WINDOW_WIDTH - offset;
	    float maxY = Constants.WINDOW_HEIGHT - offset;
	    
	    if (button.upperLimit() < minY) {
	        button.position.setY(minY + button.radius);
	        collidedY = true;
	    } else if (button.bottomLimit() > maxY) {
	        button.position.setY(maxY - button.radius);
	        collidedY = true;
	    }

	    if (button.rightLimit() > maxX) {
	        button.position.setX(maxX - button.radius);
	        collidedX = true;
	    } else if (button.leftLimit() < minX) {
	        button.position.setX(minX + button.radius);
	        collidedX = true;
	    }

	    if (isBall) {
	        float restitution = button.material.getRestitution();

	        if (collidedX) {
	            button.velocity.setX(-button.velocity.getX() * restitution);
	        }
	        
	        if (collidedY) {
	            button.velocity.setY(-button.velocity.getY() * restitution);
	        }
	    }
	}
}

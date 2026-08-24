package football.core.engine;

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
		int radiusSquared = radiusSum * radiusSum;
		
		float distance = delta.dot(delta);
		
		return distance <= radiusSquared;
	}
	
	public static void doCollision(Button bt1, Button bt2) {
		Vector2d delta = (bt1.position.subtract(bt2.position));

		float distance = delta.length();

		Vector2d mtd;
		
		if (distance != 0.0f) {
			mtd = delta.multiply(((bt1.radius + bt2.radius) - distance) / distance);
		}
		else {
			distance = bt1.radius + bt2.radius - 1.0f;
			delta = new Vector2d(bt1.radius + bt2.radius, 0.0f);


			mtd = delta.multiply(((bt1.radius + bt2.radius) - distance ) / distance);
		}

		float im1 = 1 / bt1.mass;
		float im2 = 1 / bt2.mass;

		bt1.position = bt1.position.add( mtd.multiply(im1 / (im1 + im2)) );
		bt2.position = bt2.position.subtract( mtd.multiply(im2 / (im1 + im2)) );
		
		Vector2d velocity = (bt1.velocity.subtract(bt2.velocity));
		float vn = velocity.dot( mtd.normalize() );

		if (vn > 0.0f) return;

		float restitution = (bt1.material.getRestitution() + bt2.material.getRestitution()) / 2f;
		
		float i = ( (-1) * (1.0f + restitution) * vn) / (im1 + im2);
		Vector2d impulse = mtd.multiply(i);
		
		if(!(bt1 instanceof Player)) {
			bt1.velocity = bt1.velocity.add( impulse.multiply(im1) );
		}
		
		if(!(bt2 instanceof Player)) {
			bt2.velocity = bt2.velocity.subtract( impulse.multiply(im2) );
		}
	}
	
	public static void doKicking(Player player, Ball ball) {
		Vector2d playerCenter = new Vector2d(player.position.x + player.radius, player.position.y + player.radius);
	    Vector2d ballCenter = new Vector2d(ball.position.x + ball.radius, ball.position.y + ball.radius);

	    Vector2d delta = ballCenter.subtract(playerCenter);
	    float distance = delta.length();

	    Vector2d kickDir;
	    
	    if (distance != 0.0f) {
	        kickDir = delta.multiply(1.0f / distance);
	    } else {
	        kickDir = new Vector2d(1.0f, 0.0f);
	    }

	    float KICK_POWER = 800.0f;

	    ball.velocity = ball.velocity.add(kickDir.multiply(KICK_POWER));
	}
	
	public static void letBallInsideWalls(Ball ball) {
		if (ball == null) return;

	    float pitchCenterY = Constants.WINDOW_HEIGHT / 2.0f;
	    float goalTop = pitchCenterY - (Constants.GOAL_HEIGHT / 2.0f);
	    float goalBottom = pitchCenterY + (Constants.GOAL_HEIGHT / 2.0f);

	    float posX = ball.position.getX();
	    float posY = ball.position.getY();
	    float radius = ball.radius;

	    // Check if ball is vertically within the goal post height
	    boolean inGoalY = (posY >= goalTop) && (posY <= goalBottom);

	    if (inGoalY) {
	        float leftNetBack = Constants.PITCH_OFFSET - Constants.GOAL_WIDTH;
	        float rightNetBack = Constants.WINDOW_WIDTH - Constants.PITCH_OFFSET + Constants.GOAL_WIDTH;
	        float restitution = ball.material.getRestitution();

	        // 1. Back wall of Left Goal
	        if (posX - radius < leftNetBack) {
	            ball.position.setX(leftNetBack + radius);
	            ball.velocity.setX(-ball.velocity.getX() * restitution);
	            return;
	        }

	        // 2. Back wall of Right Goal
	        if (posX + radius > rightNetBack) {
	            ball.position.setX(rightNetBack - radius);
	            ball.velocity.setX(-ball.velocity.getX() * restitution);
	            return;
	        }

	        // 3. Top and Bottom posts/nets of the goals (if ball is past the pitch lines)
	        boolean pastLeftPitchLine = (posX < Constants.PITCH_OFFSET);
	        boolean pastRightPitchLine = (posX > Constants.WINDOW_WIDTH - Constants.PITCH_OFFSET);

	        if (pastLeftPitchLine || pastRightPitchLine) {
	            if (posY - radius < goalTop) {
	                ball.position.setY(goalTop + radius);
	                ball.velocity.setY(-ball.velocity.getY() * restitution);
	            } else if (posY + radius > goalBottom) {
	                ball.position.setY(goalBottom - radius);
	                ball.velocity.setY(-ball.velocity.getY() * restitution);
	            }
	            // Return early so Calculator doesn't snap it back to the pitch
	            return; 
	        }

	        // 4. Ball is inside Goal Y height, BUT inside the pitch -> ALLOW IT TO MOVE FREELY
	        // (Do NOT call Calculator.letInsideWalls here, otherwise it blocks entry!)
	        
	        // Still apply top/bottom pitch boundaries just in case
	        if (posY - radius < Constants.PITCH_OFFSET) {
	            ball.position.setY(Constants.PITCH_OFFSET + radius);
	            ball.velocity.setY(-ball.velocity.getY() * restitution);
	        } else if (posY + radius > Constants.WINDOW_HEIGHT - Constants.PITCH_OFFSET) {
	            ball.position.setY(Constants.WINDOW_HEIGHT - Constants.PITCH_OFFSET - radius);
	            ball.velocity.setY(-ball.velocity.getY() * restitution);
	        }
	        
	        return;
	    }

	    PhysicsEngine.letInsideWalls(ball);
	}
	
	public static void letInsideWalls(Button button) {
		if (button == null) return;

	    boolean collidedX = false;
	    boolean collidedY = false;
	    
	    boolean isBall = button instanceof Ball;
	    float offset = isBall ? Constants.PITCH_OFFSET : 0;

	    float minX = offset;
	    float minY = offset;
	    float maxX = Constants.WINDOW_WIDTH - offset;
	    float maxY = Constants.WINDOW_HEIGHT - offset;
	    
	    if (button.upperLimit() - button.radius < minY) {
	        button.position.setY(minY + button.radius);
	        collidedY = true;
	    } else if (button.bottomLimit() - button.radius > maxY) {
	        button.position.setY(maxY - button.radius);
	        collidedY = true;
	    }

	    if (button.rightLimit() - button.radius > maxX) {
	        button.position.setX(maxX - button.radius);
	        collidedX = true;
	    } else if (button.leftLimit() - button.radius < minX) {
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

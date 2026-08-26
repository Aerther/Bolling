package football.ai;

import football.main.Constants;
import football.math.Vector2d;
import football.model.entity.Ball;
import football.model.entity.Bot;
import football.model.entity.Player;
import football.model.enums.GoalSide;

public class BotAlgorithm {
	
	public static Vector2d toBallPathFinding(Bot bot, Ball ball) {
		Vector2d delta = ball.position.subtract(bot.position);
		
		float distance = delta.length();

	    if(distance == 0f) {
	    	delta = new Vector2d(0, 1);
	    	distance = 1;
	    }
		
		Vector2d normal = delta.normalize();
		
		Vector2d movement = normal.multiply(bot.velocity.x, bot.velocity.y);
		
		return movement;
	}
	
	public static Vector2d defendingPathFinding(Bot bot, Ball ball) {
		GoalSide deffendingSide = bot.getTeam().goalSide;
		
		float x = 0;
		float y = Constants.WINDOW_HEIGHT / 2;
		
		if(deffendingSide == GoalSide.LEFT) {
			x = Constants.PITCH_OFFSET;
		} else if(deffendingSide == GoalSide.RIGHT) {
			x = Constants.WINDOW_WIDTH - Constants.PITCH_OFFSET;
		}
		
		Vector2d goalPoint = new Vector2d(x, y);
		
		Vector2d targetPosition = goalPoint.add(ball.position.subtract(goalPoint).multiply(0.5f));
		
		Vector2d delta = targetPosition.subtract(bot.position);
		
		float distance = delta.length();

	    if(distance == 0f) {
	    	delta = new Vector2d(0, 1);
	    	distance = 1;
	    }
	    
	    if(distance < 10) return new Vector2d(0, 0);
		
		Vector2d normal = delta.normalize();
		
		Vector2d movement = normal.multiply(bot.velocity.x, bot.velocity.y);
		
		return movement;
	}
	
	public static Vector2d blockingPathFinding(Bot bot, Player player, Ball ball) {
		Vector2d targetPosition = player.position.add(ball.position.subtract(player.position).multiply(0.5f));
		
		Vector2d delta = targetPosition.subtract(bot.position);
		
		float distance = delta.length();

	    if(distance == 0f) {
	    	delta = new Vector2d(0, 1);
	    	distance = 1;
	    }
	    
	    if(distance < 10) return new Vector2d(0, 0);
		
		Vector2d normal = delta.normalize();
		
		Vector2d movement = normal.multiply(bot.velocity.x, bot.velocity.y);
		
		return movement;
	}
	
	public static Vector2d attackingPathFinding(Bot bot, Ball ball) {
		GoalSide deffending = bot.getTeam().goalSide;
		
		float x = 0;
		
		if(deffending == GoalSide.RIGHT) {
			x = Constants.PITCH_TILE_SIZE * 2.5f + Constants.PITCH_OFFSET;
		} else if(deffending == GoalSide.LEFT) {
			x = Constants.WINDOW_WIDTH - (Constants.PITCH_TILE_SIZE * 2.5f + Constants.PITCH_OFFSET);
		}
		
		Vector2d targetPosition = bot.position.add(ball.position.subtract(bot.position));
		targetPosition.x = x;
		
		Vector2d delta = targetPosition.subtract(bot.position);
		
		float distance = delta.length();

	    if(distance == 0f) {
	    	delta = new Vector2d(0, 1);
	    	distance = 1;
	    }
	    
	    if(distance < 10) return new Vector2d(0, 0);
		
		Vector2d normal = delta.normalize();
		
		Vector2d movement = normal.multiply(bot.velocity.x, bot.velocity.y);
		
		return movement;
	}
}

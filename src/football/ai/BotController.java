package football.ai;

import football.math.Vector2d;
import football.model.entity.Ball;
import football.model.entity.Bot;
import football.model.entity.Player;
import football.model.enums.PlayStyle;

public class BotController {
	
	public Vector2d calculateMove(Bot bot, Ball ball, Player player) {
		PlayStyle style = bot.getPlayStyle();
		
		if(style == null) return new Vector2d(0, 0);
		
		return switch (style) {
		    case ATTACKING -> BotAlgorithm.attackingPathFinding(bot, ball);
		    case DEFENDING -> BotAlgorithm.defendingPathFinding(bot, ball);
		    case TOBALL -> BotAlgorithm.toBallPathFinding(bot, ball);
		    case BLOCKING -> BotAlgorithm.blockingPathFinding(bot, player, ball);
		    default -> BotAlgorithm.toBallPathFinding(bot, ball);
		};
	}
}

package football.model.entity;

import football.ai.BotController;
import football.math.Vector2d;
import football.model.enums.PlayStyle;

public class Bot extends Player {
	
	private BotController controller = new BotController();
	
	private PlayStyle playStyle;
	
	public Bot() {
		super();
		
		this.playStyle = PlayStyle.TOBALL;
	}
	
	// Getters and Setters
	
	public PlayStyle getPlayStyle() {
		return this.playStyle;
	}
	
	public void setPlayStyle(PlayStyle playStyle) {
		this.playStyle = playStyle;
	}
	
	public Vector2d move(Ball ball, Player player) {
		return controller.calculateMove(this, ball, player);
	}
}

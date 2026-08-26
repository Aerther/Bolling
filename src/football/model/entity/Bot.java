package football.model.entity;

import football.model.enums.PlayStyle;

public class Bot extends Player {
	
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
}

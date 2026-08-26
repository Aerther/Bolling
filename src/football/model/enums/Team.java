package football.model.enums;

import java.awt.Color;

public enum Team {
	RED(Color.RED, GoalSide.LEFT),
	BLUE(Color.BLUE, GoalSide.RIGHT);
	
	public Color color;
	public GoalSide goalSide;
	
	Team(Color color, GoalSide goalSide) {
		this.color = color;
		this.goalSide = goalSide;
	}
}

package football.model.enums;

import java.awt.Color;

public enum Team {
	RED(Color.RED),
	BLUE(Color.BLUE);
	
	public Color color;
	
	Team(Color color) {
		this.color = color;
	}
}

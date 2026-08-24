package football.model.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import football.model.enums.Material;

public class Ball extends Button {
	
	public Ball() {
		super();
		
		this.material = Material.RUBBER;
		this.mass = 0.01f;
		this.radius = 10;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d, Color.BLACK);
	}
	
	@Override
	public boolean isBall() {
		return true;
	}
}

package football.model.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import football.math.Vector2d;
import football.model.enums.Material;
import football.ui.draw.Drawer;

public abstract class Button {
	public Vector2d position = new Vector2d(0, 0);
	public Vector2d velocity = new Vector2d(0, 0);
	
	public int radius = 20;
	public float mass = 1;
	
	public boolean onAir = true;
	
	public Material material = Material.RUBBER;
	
	public Button() {
		this(0, 0, 20, 1);
	}
	
	public Button(int radius) {
		this(0, 0, radius, 1);
	}
	
	public Button(int x, int y, int radius) {
		this(x, y, radius, 1);
	}
	
	public Button(float x, float y, int radius, float mass) {
		this.position = new Vector2d(x, y);
		this.velocity = new Vector2d(0, 0);
		
		this.radius = radius;
		this.mass = mass;
	}
	
	public double bottomLimit() {
		return this.position.getY() + 2 * this.radius;
	}
	
	public double upperLimit() {
		return this.position.getY();
	}
	
	public double leftLimit() {
		return this.position.getX();
	}
	
	public double rightLimit() {
		return this.position.getX() + 2 * this.radius;
	}
	
	public void draw(Graphics2D g2d, Color color) {
		if(color == null) color = this.material.getColor();
		
		Drawer.drawButton(g2d, this, color);
	}
	
	public void draw(Graphics2D g2d) {
		Drawer.drawButton(g2d, this, null);
	}
	
	public boolean isBall() {
		return false;
	}
	
	public boolean isPlayer() {
		return false;
	}
}

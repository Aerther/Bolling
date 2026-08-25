package football.model.entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import football.math.Vector2d;
import football.model.enums.ControllerMode;
import football.model.enums.Material;
import football.model.enums.Team;
import football.ui.draw.Drawer;

public class Player extends Button {
	private int id;
	
	private boolean isKicking = false;
	
	private ControllerMode controllerMode = ControllerMode.WASD;
	
	private Team team = Team.RED;
	
	public Player() {
		super();
		
		this.originalVelocity.set(150, 150);
		
		this.material = Material.STEEL;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d, this.team.color);
		
		if(isKicking) {
			Drawer.drawKickingBorder(g2d, this, null, 2.0f);
		}
	}
	
	@Override
	public boolean isPlayer() {
		return true;
	}
	
	// Getters and Setters
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public boolean isKicking() {
		return isKicking;
	}

	public void setKicking(boolean isKicking) {
		this.isKicking = isKicking;
	}

	public ControllerMode getControllerMode() {
		return controllerMode;
	}

	public void setControllerMode(ControllerMode controllerMode) {
		this.controllerMode = controllerMode;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}

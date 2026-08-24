package football.ui.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import football.main.Constants;
import football.model.entity.Button;
import football.model.entity.Player;

public class Drawer {
	public static void drawButton(Graphics2D g2d, Button button, Color color) {
		g2d.setColor(button.material.getColor());
		
		if(color != null) {
			g2d.setColor(color);
		}
		
		g2d.fill(new Ellipse2D.Float(button.position.getX() - button.radius, button.position.getY() - button.radius, 2 * button.radius, 2 * button.radius));
	}
	
	public static void drawKickingBorder(Graphics2D g2d, Player player, Color color, float stroke) {
		g2d.setColor(Color.BLACK);
		
		if(color != null) {
			g2d.setColor(color);
		}
		
		Stroke oldStroke = g2d.getStroke();
		
		g2d.setStroke(new BasicStroke(stroke));
		
		g2d.draw(new Ellipse2D.Float(player.position.getX() - player.radius, player.position.getY() - player.radius, 2 * player.radius, 2 * player.radius));
		
		g2d.setStroke(oldStroke);
	}
	
	public static void drawPitch(Graphics2D g2d) {
		Drawer.drawPitch(g2d, 3.0f);
	}
	
	public static void drawPitch(Graphics2D g2d, float stroke) {
		Color color1 = Constants.PITCH_COLOR_1;
		Color color2 = Constants.PITCH_COLOR_2;
		
		int tileSize = Constants.PITCH_TILE_SIZE;
		
		for(int i = 0; i < Constants.WINDOW_WIDTH / tileSize; i++) {
			for(int j = 0; j < Constants.WINDOW_HEIGHT / tileSize; j++) {
				g2d.setColor(color2);
				
				if((i + j) % 2 == 0) {
					g2d.setColor(color1);
				}
				
				g2d.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
			}
		}
		
		Stroke oldStroke = g2d.getStroke();
		
		g2d.setStroke(new BasicStroke(stroke));
		
		Drawer.drawPitchLines(g2d);
		Drawer.drawGoals(g2d);
		Drawer.drawGoalsLines(g2d);
		Drawer.drawCenterPitchCircle(g2d);
		Drawer.drawCenterLine(g2d);
		
		g2d.setStroke(oldStroke);
	}
	
	private static void drawPitchLines(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		
		int offset = Constants.PITCH_OFFSET;
		int x = offset;
		int y = offset;
		int width = Constants.PITCH_WIDTH;
		int height = Constants.PITCH_HEIGHT;
		
		g2d.drawRect(x, y, width, height);
	}
	
	private static void drawGoals(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		
		int width = Math.min(Constants.GOAL_WIDTH, 50);
		int height = Constants.GOAL_HEIGHT;
		int y = Constants.WINDOW_HEIGHT / 2 - Constants.GOAL_HEIGHT / 2;
		
		int x1 = width;
		int x2 = 2 * width + Constants.PITCH_WIDTH;
		
		g2d.drawRect(x1, y, width, height);
		g2d.drawRect(x2, y, width, height);
	}
	
	private static void drawCenterPitchCircle(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		
		int circleRadius = 15;
		
		int x = Constants.WINDOW_WIDTH / 2 - circleRadius;
		int y = Constants.WINDOW_HEIGHT / 2 - circleRadius;
		
		g2d.fill(new Ellipse2D.Float(x, y, 2 * circleRadius, 2 * circleRadius));
	}
	
	private static void drawGoalsLines(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		
		float width = Constants.PITCH_TILE_SIZE * 1.75f;
		float height = Constants.PITCH_HEIGHT / 2;
		
		float offset = Constants.PITCH_OFFSET;
		float y = Constants.PITCH_HEIGHT / 4 + offset;
		
		float x1 = offset;
		float x2 = Constants.PITCH_WIDTH + offset - width;
		
		g2d.draw(new Rectangle2D.Float(x1, y, width, height));
		g2d.draw(new Rectangle2D.Float(x2, y, width, height));
	}
	
	private static void drawCenterLine(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		
		int offset = Constants.PITCH_OFFSET;
		
		int x = Constants.PITCH_WIDTH / 2 + offset;
		int y = offset;
		
		g2d.drawLine(x, y, x, y + Constants.PITCH_HEIGHT);
	}
}

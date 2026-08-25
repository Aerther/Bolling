package football.main;

import java.awt.Color;

public class Constants {
	public static final int TILE_SIZE = 15;
	public static final int SCREEN_WIDTH = 70;
	public static final int SCREEN_HEIGHT = 40;
	
	public static final int WINDOW_WIDTH = SCREEN_WIDTH * TILE_SIZE;
	public static final int WINDOW_HEIGHT = SCREEN_HEIGHT * TILE_SIZE;
	
	public static final int PITCH_OFFSET = 50;
	public static final int PITCH_WIDTH = WINDOW_WIDTH - 2 * PITCH_OFFSET;
	public static final int PITCH_HEIGHT = WINDOW_HEIGHT - 2 * PITCH_OFFSET;
	public static final int GOAL_HEIGHT = 150;
	public static final int GOAL_WIDTH = Math.min(PITCH_OFFSET / 2, 50);
	public static final int PITCH_TILE_SIZE = TILE_SIZE * 5;
	
	public static final Color PITCH_COLOR_1 = new Color(108, 168, 64);
	public static final Color PITCH_COLOR_2 = new Color(94, 152, 51);
	
	public static final float GRAVITY = 100f;
	public static final float ELASTIC_COLLISION = 0.9f;
	public static final float KICK_POWER = 800f;
	public static final int FPS = 60;
}

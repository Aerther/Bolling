package football.ui.draw;

import java.awt.Graphics2D;

import javax.swing.JLabel;

import football.core.engine.GameMatch;

public class TextRenderer {
	
	public static void updateScoreText(GameMatch game, JLabel label) {
		label.setText(game.getMatchScore());
	}
}

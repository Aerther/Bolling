package football.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import football.main.Constants;

public class GameFrame extends JFrame {
	
	public GameFrame() {
		this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
		this.setTitle("FOOTBALL");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		this.getContentPane().setLayout(null);
	    this.getContentPane().setBackground(Color.BLACK);
	    this.getContentPane().setPreferredSize(new Dimension(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
		
	    this.addComponents();
	    
		this.pack();
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	}
	
	public void addComponents() {
		GamePanel panel = new GamePanel();
		add(panel);
		
		panel.startGame();
	}
}

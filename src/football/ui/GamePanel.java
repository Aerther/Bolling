package football.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import football.core.engine.GameMatch;
import football.core.engine.MatchRules;
import football.core.engine.PhysicsEngine;
import football.core.input.KeyboardStatus;
import football.core.input.listener.KeyboardListener;
import football.main.Constants;
import football.math.Vector2d;
import football.model.entity.Ball;
import football.model.entity.Button;
import football.model.entity.Player;
import football.model.enums.ControllerMode;
import football.model.enums.GoalSide;
import football.model.enums.Movement;
import football.ui.draw.Drawer;
import football.ui.draw.TextRenderer;

public class GamePanel extends JPanel implements Runnable {
	
	private JLabel scoreLabel;
    
    private GameMatch gameMatch;
	
	public Thread gameThread;
	
	private KeyboardStatus keyboard = new KeyboardStatus();
	
	public GamePanel() {
		setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        setBackground(Color.BLACK);
        setFocusable(true);
        
        MouseAdapter adapter = new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		
                
                repaint();
        	}
            
            @Override
            public void mouseReleased(MouseEvent e) {
            	

                repaint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
            	
            }
        };
        
        this.gameMatch = new GameMatch();
        
        this.addButtonsToMatch();
        
        KeyAdapter keyAdapter = new KeyboardListener(keyboard);
        
        this.addTextDisplay();
        addKeyListener(keyAdapter);
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }
	
	public void addButtonsToMatch() {
		for(int i = 0; i < 1; i++) {
			Player player = new Player();
			player.position.set(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT * 2/3);
			gameMatch.addPlayer(player);
		}
		
		for(int i = 0; i < 1; i++) {
			Ball ball = new Ball();
			ball.position.set(Constants.WINDOW_WIDTH/2, Constants.WINDOW_HEIGHT/2);
			
			gameMatch.addBall(ball);
		}
	}
	
	private void addTextDisplay() {
		scoreLabel = new JLabel("0 x 0");
		
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        scoreLabel.setForeground(Color.WHITE);
        
        Dimension size = scoreLabel.getPreferredSize();

        int x = (Constants.WINDOW_WIDTH / 2) - (size.width / 2);
        int y = (Constants.PITCH_OFFSET / 2) - (size.height / 2);

        scoreLabel.setBounds(x, y, size.width, size.height);

        this.add(scoreLabel);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        Drawer.drawPitch(g2d);
        gameMatch.drawButtons(g2d);
    }
    
    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    private void update() {
        float dt = (1.0f / Constants.FPS);
        
    	float mx = 0.0f;
    	float my = 0.0f;
    	
    	List<Player> players = gameMatch.getPlayers();
    	
    	for(Player player : players) {
    		ControllerMode mode = player.getControllerMode();
    		
    		player.velocity = player.originalVelocity.multiply(1);
    		
    		mx = 0f;
    		my = 0f;
    		
        	player.setKicking(keyboard.isKeyPressed(mode, Movement.KICK));
        	
        	if(player.isKicking()) {
        		player.velocity = player.velocity.multiply(0.8f);
        	}
        	
        	if(keyboard.isKeyPressed(mode, Movement.UP)) {
        		my -= dt * player.velocity.getY();
        	}
        	
        	if(keyboard.isKeyPressed(mode, Movement.DOWN)) {
        		my += dt * player.velocity.getY();
        	}
        	
        	if(keyboard.isKeyPressed(mode, Movement.LEFT)) {
        		mx -= dt * player.velocity.getX();
        	}
        	 
        	if(keyboard.isKeyPressed(mode, Movement.RIGHT)) {
        		mx += dt * player.velocity.getX();
        	}
        	
        	Vector2d addVector = new Vector2d(mx, my);
        	
        	player.position = player.position.add(addVector);
        	
        	PhysicsEngine.letInsideWalls(player);
    	}
    	
    	List<Ball> balls = gameMatch.getBalls();
    	
    	balls.forEach(ball -> {
    		ball.position = ball.position.add(ball.velocity.multiply(dt));
        	ball.velocity = ball.velocity.multiply(0.97f);
        	
    		GoalSide goalSide = MatchRules.isBallInsideGoal(ball);
    		
    		if(goalSide != GoalSide.NONE) {
        		gameMatch.goalScored(goalSide);
        		
        		TextRenderer.updateScoreText(gameMatch, scoreLabel);
        		
        		ball.position.set(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2);
        		ball.velocity.set(0, 0);
        	}
    		
    		PhysicsEngine.letBallInsideWalls(ball);
    	});
    	
    	List<Button> buttons = gameMatch.getAllEntities();
    	
    	for(int i = 0; i < buttons.size(); i++) {
    		for(int j = i + 1; j < buttons.size(); j++) {
    			Button button1 = buttons.get(i);
    			Button button2 = buttons.get(j);
    			
    			if(PhysicsEngine.didCollide(button1, button2)) {
    				PhysicsEngine.doCollision(button1, button2);
    				
    				if(button1.isPlayer() && button2.isPlayer()) continue;
    				if(button1.isBall() && button2.isBall()) continue;
    				
    				Player player = null;
    				Ball ball = null;
    				
    				if(button1.isBall()) {
    					ball = (Ball) button1;
    					player = (Player) button2;
    				} else {
    					ball = (Ball) button2;
    					player = (Player) button1;
    				}
    				
    				if(!player.isKicking()) continue;
    				
    				PhysicsEngine.doKicking(player, ball);
    			}
    		}
    	}
    }

    @Override
    public void run() {
        while (true) {
            update();
            repaint();

            try {
                Thread.sleep(1000 / Constants.FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

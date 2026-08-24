package football.core.engine;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import football.model.entity.Ball;
import football.model.entity.Bot;
import football.model.entity.Button;
import football.model.entity.Player;
import football.model.enums.ControllerMode;
import football.model.enums.GameMode;
import football.model.enums.GoalSide;

public class GameMatch {
	private GameMode gameMode = GameMode.ALONE;
	
	private List<Button> allEntities;
	
	private List<Ball> balls;
	
	private List<Player> players;
	
	private List<Bot> bots;
	
	private int firstTeamScore = 0; // LEFT TEAM
	private int secondTeamScore = 0; // RIGHT TEAM
	
	private int countId = 1;
	
	public GameMatch() {
		this.allEntities = new ArrayList<>();
	    this.balls = new ArrayList<>();
	    this.players = new ArrayList<>();
	    this.bots = new ArrayList<>();
	}
	
	public void setPlayersControllers() {
		if(gameMode == GameMode.TWOLOCALPLAYERS) {
			setPlayerController(1, ControllerMode.WASD);
			setPlayerController(2, ControllerMode.ARROWS);
		}
	}
	
	public void setPlayerController(int id, ControllerMode controller) {
		Player player = this.findPlayerById(id);
		
		player.setControllerMode(controller);
	}
	
	public Player findPlayerById(int id) {
	    return players.stream().filter(player -> player.getId() == id).findFirst().orElse(null);
	}
	
	public void addPlayer(Player player) {
		player.setId(countId);
		countId++;
		
		players.add(player);
		allEntities.add(player);
	}
	
	public void addBot(Bot bot) {
		bots.add(bot);
		allEntities.add(bot);
	}
	
	public void addBall(Ball ball) {
		balls.add(ball);
		allEntities.add(ball);
	}
	
	public void goalScored(GoalSide goalSide) {
		if(goalSide == GoalSide.NONE) return;
		
		if(goalSide == GoalSide.RIGHT) {
			firstTeamScore++;
		} else {
			secondTeamScore++;
		}
	}
	
	public String getMatchScore() {
		return this.firstTeamScore + " x " + this.secondTeamScore;
	}
	
	public void drawButtons(Graphics2D g2d) {
		this.allEntities.forEach(entity -> entity.draw(g2d));;
	}
	
	// Getters and Setters

	public GameMode getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameMode gameMode) {
		this.gameMode = gameMode;
	}

	public List<Button> getAllEntities() {
		return allEntities;
	}

	public void setAllEntities(List<Button> allEntities) {
		this.allEntities = allEntities;
	}

	public List<Ball> getBalls() {
		return balls;
	}

	public void setBalls(List<Ball> balls) {
		this.balls = balls;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Bot> getBots() {
		return bots;
	}

	public void setBots(List<Bot> bots) {
		this.bots = bots;
	}

	public int getFirstTeamScore() {
		return firstTeamScore;
	}

	public void setFirstTeamScore(int firstTeamScore) {
		this.firstTeamScore = firstTeamScore;
	}

	public int getSecondTeamScore() {
		return secondTeamScore;
	}

	public void setSecondTeamScore(int secondTeamScore) {
		this.secondTeamScore = secondTeamScore;
	}
}

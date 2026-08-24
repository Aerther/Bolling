package football.core.engine;

import football.main.Constants;
import football.model.entity.Ball;
import football.model.enums.GoalSide;

public class MatchRules {
	public static GoalSide isBallInsideGoal(Ball ball) {
	    if (ball == null) return GoalSide.NONE;

	    float radius = ball.radius;
	    float posX = ball.position.getX();
	    float posY = ball.position.getY();

	    float pitchCenterY = Constants.WINDOW_HEIGHT / 2.0f;
	    float goalTop = pitchCenterY - (Constants.GOAL_HEIGHT / 2.0f);
	    float goalBottom = pitchCenterY + (Constants.GOAL_HEIGHT / 2.0f);

	    boolean fitsVertically = (posY - radius >= goalTop) && (posY + radius <= goalBottom);

	    if (!fitsVertically) return GoalSide.NONE;

	    float leftPitchLine = Constants.PITCH_OFFSET;
	    float rightPitchLine = Constants.WINDOW_WIDTH - Constants.PITCH_OFFSET;

	    boolean fullyInsideLeftGoal = (posX + radius < leftPitchLine);

	    boolean fullyInsideRightGoal = (posX - radius > rightPitchLine);

	    if(fullyInsideRightGoal) return GoalSide.RIGHT;
	    
	    if(fullyInsideLeftGoal) return GoalSide.LEFT;
	    
	    return GoalSide.NONE;
	}
}

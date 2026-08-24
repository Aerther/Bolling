package football.core.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import football.model.enums.ControllerMode;
import football.model.enums.Movement;

public class KeyboardStatus {
	private Map<Integer, Boolean> events = new HashMap<>();
	private Map<ControllerMode, Map<Movement, Integer>> mapping = new HashMap<>();
	
	public KeyboardStatus() {
		this.addWASDKeys();
		this.addArrowKeys();
	}
	
	private void addWASDKeys() {
        Map<Movement, Integer> map = new HashMap<>();
        map.put(Movement.UP, KeyEvent.VK_W);
        map.put(Movement.DOWN, KeyEvent.VK_S);
        map.put(Movement.LEFT, KeyEvent.VK_A);
        map.put(Movement.RIGHT, KeyEvent.VK_D);
        map.put(Movement.KICK, KeyEvent.VK_SPACE);

        mapping.put(ControllerMode.WASD, map);

        for (Integer key : map.values()) {
            events.put(key, false);
        }
    }

    private void addArrowKeys() {
        Map<Movement, Integer> map = new HashMap<>();
        map.put(Movement.UP, KeyEvent.VK_UP);
        map.put(Movement.DOWN, KeyEvent.VK_DOWN);
        map.put(Movement.LEFT, KeyEvent.VK_LEFT);
        map.put(Movement.RIGHT, KeyEvent.VK_RIGHT);
        map.put(Movement.KICK, KeyEvent.VK_ENTER);

        mapping.put(ControllerMode.ARROWS, map);

        for (Integer key : map.values()) {
            events.put(key, false);
        }
    }
	
	public void changeKeyState(Integer key, boolean state) {
		events.put(key, state);
	}
	
	public boolean isKeyPressed(ControllerMode controller, Movement move) {
		Integer key = this.mapping.get(controller).get(move);
		
		return this.events.get(key);
	}
}

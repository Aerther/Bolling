package football.core.input.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import football.core.input.KeyboardStatus;
import football.math.Vector2d;

public class KeyboardListener extends KeyAdapter {
	
	private KeyboardStatus keyStatus;
	
	public KeyboardListener(KeyboardStatus keyboardStatus) {
		this.keyStatus = keyboardStatus;
	}
	
	@Override
    public void keyPressed(KeyEvent e) {
        handleKey(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	handleKey(e, false);
    }
    
    private void handleKey(KeyEvent e, boolean state) {
    	keyStatus.changeKeyState(e.getKeyCode(), state);
    }
}

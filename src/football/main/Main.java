package football.main;

import javax.swing.SwingUtilities;

import football.ui.GameFrame;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameFrame().setVisible(true);
            }
        });
	}

}

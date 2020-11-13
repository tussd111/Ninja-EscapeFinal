package Main;
import Audio.AudioPlayer;

import javax.swing.JFrame;
public class Game {
	public static void main(String [] args ) {
		JFrame window = new JFrame("Ninja Escape");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);

	}
}

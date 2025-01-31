package GameState;

import TileMap.Background;
import Audio.AudioPlayer;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

	
	private Background bg;
	private int currentChoice = 0;
	private String[] options = {
		"Start",
		"Help",
		"Quit"
	};

	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	public AudioPlayer BgMusic;
	
	public MenuState(GameStateManager gsm) {


		this.gsm = gsm;

		try {

			bg = new Background("/Backgrounds/bg.gif", 1);
			bg.setVector(-0.1, 0);

			titleColor = new Color(207, 32, 32);
			titleFont = new Font(
					"Tahoma",
					Font.PLAIN,
					35);

			font = new Font("Tahoma", Font.PLAIN, 10);

		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void init() {

	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g) {

		// draw bg
		bg.draw(g);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("NINJA ESCAPE", 55, 80);
		
		// draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.WHITE);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 140 + i * 25);
		}
		
	}
	
	private void select() {

		if(currentChoice == 0) {
			gsm.setState(GameStateManager.LEVEL1STATE);


		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.HELP);
			// help
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
			System.out.println(currentChoice);
		}

	}
	public void keyReleased(int k) {}



}











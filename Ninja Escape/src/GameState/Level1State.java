package GameState;

import Main.GamePanel;
import Audio.AudioPlayer;
import Prop.Enemy;
import Prop.Key;
import Prop.UPFloor;
import javax.swing.*;
import java.awt.*;

import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Level1State extends GameState implements ImageObserver {
	int[][] positionFloors = {
			{0, 200}, {50, 200}, {100, 200}, {150, 200}, {200, 200}, {250, 200},{300, 200}
	};

	int[][] positionUpFloors = {
			{50, 50}, {200, 50}, {100, 150}, {150, 100},{230,50},{170,100},{200,100},{220,100}
	};

	int[][] positionItems = {
			{50, 35}, {285, 35}, {100, 135}, {280, 85}
	};

	// {x, y, กว้าง, สูง}
	int[][] positionLadders = {
			{100, 50, 20, 100},{150, 100, 20, 50}, {120, 152, 20, 52}, {200, 50, 20, 55}
	};

	ImageIcon bgp = new ImageIcon("Resources/Backgrounds/01.png");
	ImageIcon floor = new ImageIcon("Resources/Prop/bottomfloor2.png");
	ImageIcon ladder = new ImageIcon("Resources/Prop/ladder.png");
	ImageIcon item = new ImageIcon("Resources/Prop/key.png");
	ImageIcon door = new ImageIcon("Resources/Prop/door.png");
	ImageIcon character = new ImageIcon("Resources/Prop/zed.png");

	private AudioPlayer bgMusic;
	int keyPick = 0;
	int xMove = 10;
	int yMove = 175;
	String direction = "left";
	private int up = 0;
	private int down = 0;
	public int right = 0;
	public int left = 0;
	public List<UPFloor> upFloorList;
	public List<Key> keyList;
	public List<Enemy> enemyList;
	Fire fire = new Fire(xMove, yMove, direction);
	GameStateManager gsm;

	boolean shoot = false;
	boolean nextLv = false;
	boolean timestart = true;
	int t = 0;

	Thread time = new Thread(
			new Runnable(){

				@Override
				public void run() {
					while(true){
						if(timestart == false){
							t = t-1 ;
						}
						try{
							Thread.sleep(1000);
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}
			});


	public Level1State(GameStateManager gsm) {
		bgMusic = new AudioPlayer("/Music/level1-1.mp3");
		bgMusic.play();
		this.gsm = gsm;
		enemyList = new ArrayList<>();
		keyList = new ArrayList<>();
		for (int[] positionItem : positionItems) {
			Key key = new Key();
			key.setxPos(positionItem[0]);
			key.setyPos(positionItem[1]);
			keyList.add(key);
		}

		init();
	}

	public void init() {

		upFloorList = new ArrayList<>();
		for (int[] positionUpFloor :positionUpFloors) {
			UPFloor upFloor = new UPFloor();
			upFloor.setxPos(positionUpFloor[0]);
			upFloor.setyPos(positionUpFloor[1]);
			upFloorList.add(upFloor);
		}
		t = 50;
		timestart = false;
		time.start();
	}

	private void checkOver() {
		boolean chashEnemy = false;
		for (Enemy enemy : enemyList) {
			if (overlaps(enemy.getxPos(), enemy.getyPos(), 5, xMove, yMove, 10)) {
				chashEnemy = true;
			}
		}

		if (t == 0 || chashEnemy) {
			reset();
			gsm.setState(GameStateManager.OVER);
		}
	}

	private void checkNextLv() {
		if (keyPick == 4 && overlaps(xMove, yMove, 5, 290, 175, 1)) {
			reset();
			gsm.setState(GameStateManager.LEVEL2STATE);
		}

	}

	public void update() {}

	public void draw(Graphics2D g) {
		checkOver();
		// clear screen
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

		g.drawImage(bgp.getImage(),0,-30,320,240,this);
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Showcard Gothic",Font.TYPE1_FONT, 10));
		g.drawString("Key: "+ keyPick + " / 4", 265, 20);
		g.drawString("Time: "+ t, 150, 20);

		//พื้น
		for (int i = 0; i < 7; i++) {
			g.drawImage(floor.getImage(), positionFloors[i][0], positionFloors[i][1], 50, 40, this);
		}

		//พื้นลอย
		for (UPFloor upFloor : upFloorList) {
			g.drawImage(upFloor.image.getImage(), upFloor.getxPos(), upFloor.getyPos(), upFloor.getWidth(), upFloor.getHeight(), this);
		}

		//บันได
		for (int i = 0; i < 4; i++) {
			g.drawImage(ladder.getImage(), positionLadders[i][0], positionLadders[i][1], positionLadders[i][2], positionLadders[i][3], this);
		}

		//กุญแจ
		for (Key key : keyList) {
			g.drawImage(item.getImage(),key.getxPos(), key.getyPos(), 21, 21, this);
		}

		//ประตู
		g.drawImage(door.getImage(), 230, 100, 150, 150, this);

		// draw character
		g.drawImage(character.getImage(), xMove, yMove, 25, 35, this);

		for (Enemy enemy : enemyList) {
			g.drawImage(enemy.getImage(t).getImage(), enemy.getxPos(), enemy.getyPos(), 40, 40, this);
		}

		if (shoot) {
			fire.tick();
			fire.draw(g);
		}

	}

	@Override
	public void keyPressed(int k) {
		if (k == 87 &&
				(
						(xMove == 100 && (yMove <= 125 && yMove > 25)) ||
						(xMove == 120 && yMove> 125) ||
						(xMove == 150 && (yMove <= 125 && yMove > 80)) ||
						(xMove == 200 && (yMove <= 80 && yMove > 25))
				)
		) {
			up =- 10;

		} else if (k == 83 &&
				(
						(xMove == 100 && (yMove >= 25 && yMove < 125)) ||
						(xMove == 120 && yMove != 175) ||
						(xMove == 150 && (yMove >= 75 && yMove < 125)) ||
						(xMove == 200 && (yMove >= 25 && yMove < 75))
				)
		) {
			down =+ 10;

		} else if (k == 65 &&
				(
						(yMove == 175 && (xMove > 0 && xMove <= 290)) ||
						(yMove == 125 && (xMove > 90 && xMove <= 160)) ||
						(yMove == 25 && (xMove > 40 && xMove <= 110)) ||
						(yMove == 25 && (xMove > 190 && xMove <= 290)) ||
						(yMove == 75 && (xMove > 140 && xMove <= 280))
				)
		) {
			character = new ImageIcon("Resources/Prop/zedL.png");
			direction = "left";
			left =- 10;

		} else if (k == 68 &&
				(
					(yMove == 175 && (xMove >= 0 && xMove < 290)) ||
					(yMove == 125 && (xMove >= 90 && xMove < 160)) ||
					(yMove == 25 && (xMove >= 40 && xMove < 110)) ||
					(yMove == 25 && (xMove >= 190 && xMove < 290)) ||
					(yMove == 75 && (xMove >= 40 && xMove < 280))
				)
		) {
			character = new ImageIcon("Resources/Prop/zed.png");
			direction = "right";
			right =+ 10;

		} else if (k == 74 && keyPick == 4) { //event เมื่อกดยิง j
			shoot = true;
			fire = new Fire(xMove+10, yMove+15, direction);
		}
	}

	@Override
	public void keyReleased(int k) {
		shoot = false;
		xMove = xMove + left + right;
		yMove = yMove + up + down;
		left = 0;
		right = 0;
		up = 0;
		down =0;
		if(keyPick != 4) {
			checkPickItem();
		}
		checkNextLv();
		checkHit();
		if (keyPick == 4 && nextLv) {
			setEnemy();
			setBoss();
			setEnemy2();
			setEnemy3();
		}
	}

	private void setEnemy() {
		nextLv = false;
		Enemy enemy = new Enemy();
		enemy.setPoroL(new ImageIcon("Resources/Prop/PoroL.png"));
		enemy.setPoroR(new ImageIcon("Resources/Prop/PoroR.png"));
		enemy.setxPos(150);
		enemy.setyPos(170);
		enemyList.add(enemy);
	}
	private void setEnemy2() {
		nextLv = false;
		Enemy enemy = new Enemy();
		enemy.setPoroL(new ImageIcon("Resources/Prop/PoroL.png"));
		enemy.setPoroR(new ImageIcon("Resources/Prop/PoroR.png"));
		enemy.setxPos(200);
		enemy.setyPos(25);
		enemyList.add(enemy);
	}
	private void setEnemy3() {
		nextLv = false;
		Enemy enemy = new Enemy();
		enemy.setPoroL(new ImageIcon("Resources/Prop/PoroL.png"));
		enemy.setPoroR(new ImageIcon("Resources/Prop/PoroR.png"));
		enemy.setxPos(150);
		enemy.setyPos(70);
		enemyList.add(enemy);
	}
	private void setBoss() {
		nextLv = false;
		Enemy enemy = new Enemy();
		enemy.setPoroL(new ImageIcon("Resources/Enemy/shenL.png"));
		enemy.setPoroR(new ImageIcon("Resources/Enemy/shenR.png"));
		enemy.setxPos(230);
		enemy.setyPos(170);
		enemyList.add(enemy);
	}


	private void checkPickItem() {
		nextLv = true;
		for (Key key : keyList) {
			if(overlaps(xMove, yMove, 10, key.getxPos(), key.getyPos(), 2)) {
				keyList.remove(key);
				keyPick += 1;
				break;
			}
		}

	}

	private void checkHit() {
		for (Enemy enemy : enemyList) {
			if (overlaps(fire.getX(), fire.getY(), 25, enemy.getxPos(), enemy.getyPos(), 20)) {
				enemyList.remove(enemy);
			}
		}
	}

	public static boolean overlaps(int x1,int y1,int r1,int x2,int y2,int r2){
		return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2))<=r1+r2;
	}

	private void reset() {
		positionFloors = new int[][]{
				{0, 200}, {50, 200}, {100, 200}, {150, 200}, {200, 200}, {250, 200}, {300, 200}
		};

		positionUpFloors = new int[][]{
				{50, 50}, {200, 50}, {100, 150}, {150, 100}, {230, 50}, {170, 100}, {200, 100}, {220, 100}
		};

		positionItems = new int[][]{
				{50, 35}, {285, 35}, {100, 135}, {280, 85}
		};

		// {x, y, กว้าง, สูง}
		positionLadders = new int[][]{
				{100, 50, 20, 100}, {150, 100, 20, 50}, {120, 152, 20, 52}, {200, 50, 20, 55}
		};

		keyPick = 0;
		xMove = 230;
		yMove = 175;
		direction = "left";
		up = 0;
		down = 0;
		right = 0;
		left = 0;

		fire = new Fire(xMove, yMove, direction);
		shoot = false;
		timestart = true;
		t = 0;

		for (int[] positionItem : positionItems) {
			Key key = new Key();
			key.setxPos(positionItem[0]);
			key.setyPos(positionItem[1]);
			keyList.add(key);
		}
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
}
package GameState;

import Main.GamePanel;
import Prop.*;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

public class Level2State extends GameState implements ImageObserver, Runnable , ActionListener {
    int[][] positionFloors = {
            {0, 200}, {50, 200}, {100, 200}, {150, 200}, {200, 200}, {250, 200}, {300, 200}
    };

    int[][] positionUpFloors = {
            {50, 50}, {150, 100}, {30, 150}, {190,100}, {230,100}, {150,150}
    };

    int[][] positionItems = {
            {50, 39}, {210, 140}, {200, 90}
    };

    int[][] positionBushs = {
            {20, 178}, {270, 75}
    };

    int[][] positionArrows = {
            {2, 178}, {255, 75}
    };

    int[][] positionEnemys = {
            {50, 185}, {200, 85},{85,130}
    };
    int[][] positionBosss = {
            {100,18}
    };

    // {x, y, กว้าง, สูง}
    int[][] positionLadders = {
            {80, 50, 20, 100}, {50, 152, 20, 52}, {150, 100, 20, 50}
    };

    ImageIcon bgp = new ImageIcon("Resources/Backgrounds/bg9.png");
    ImageIcon floor = new ImageIcon("Resources/Prop/bottomfloor2.png");
    ImageIcon ladder = new ImageIcon("Resources/Prop/ladder.png");
    ImageIcon item = new ImageIcon("Resources/Prop/key.png");
    ImageIcon door = new ImageIcon("Resources/Prop/door.png");
    ImageIcon character = new ImageIcon("Resources/Prop/zed.png");
    ImageIcon bushImg = new ImageIcon("Resources/Prop/bush.png");
    ImageIcon arrowImg = new ImageIcon("Resources/Prop/ArrowSign.png");


    int keyPick = 0;
    int xMove = 230;
    int yMove = 175;
    String direction = "left";
    private int up = 0;
    private int down = 0;
    public int right = 0;
    public int left = 0;
    public List<UPFloor> upFloorList;
    public List<Key> keyList;
    public List<Bush> bushList;
    public List<Arrow> arrowList;
    public List<Enemy> enemyList;
    public List<Boss> bossList;

    Fire fire = new Fire(xMove, yMove, direction);
    GameStateManager gsm;

    boolean shoot = false;
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

    public Level2State(GameStateManager gsm) {
        this.gsm = gsm;
        keyList = new ArrayList<>();
        bushList = new ArrayList<>();
        arrowList = new ArrayList<>();
        enemyList = new ArrayList<>();
        bossList = new ArrayList<>();

        for (int[] positionItem : positionItems) {
            Key key = new Key();
            key.setxPos(positionItem[0]);
            key.setyPos(positionItem[1]);
            keyList.add(key);
        }

        for (int[] positionBush : positionBushs) {
            Bush bush = new Bush();
            bush.setxPos(positionBush[0]);
            bush.setyPos(positionBush[1]);
            bushList.add(bush);
        }
        for (int[] positionArrow: positionArrows) {
            Arrow arrow = new Arrow();
            arrow.setxPos(positionArrow[0]);
            arrow.setyPos(positionArrow[1]);
            arrowList.add(arrow);
        }

        for (int[] positionEnemy : positionEnemys) {
            Enemy enemy = new Enemy();
            enemy.setxPos(positionEnemy[0]);
            enemy.setyPos(positionEnemy[1]);
            enemyList.add(enemy);
        }
        for (int[] positionBosss : positionBosss) {
            Boss boss = new Boss();
            boss.setxPos(positionBosss[0]);
            boss.setyPos(positionBosss[1]);
            bossList.add(boss);
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
        t = 90;
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
        if (keyPick == 3 && overlaps(xMove, yMove, 5, 290, 175, 1)) {
            reset();
            gsm.setState(GameStateManager.WIN);
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
        g.drawString("Key: "+ keyPick + " / 3", 265, 20);
        g.drawString("Time: "+ t, 150, 20);

        //พื้น
        for (int i = 0; i < 7; i++) {
            g.drawImage(floor.getImage(), positionFloors[i][0], positionFloors[i][1], 50, 40, this);
        }

        //พื้นลอย
        for (UPFloor upFloor : upFloorList) {
            g.drawImage(upFloor.image.getImage(), upFloor.getxPos(), upFloor.getyPos(), upFloor.getWidth(), upFloor.getHeight(), this);
        }

        for (int i = 0; i < 3; i++) {
            //บันได
            g.drawImage(ladder.getImage(), positionLadders[i][0], positionLadders[i][1], positionLadders[i][2], positionLadders[i][3], this);
        }

        //กุญแจ
        for (Key key : keyList) {
            g.drawImage(item.getImage(),key.getxPos(), key.getyPos(), 21, 21, this);
        }

        g.drawImage(door.getImage(), 230, 100, 150, 150, this);

        // draw character
        g.drawImage(character.getImage(), xMove, yMove, 25, 35, this);

        //พุ่มไม้
        for (Bush bush : bushList) {
            g.drawImage(bushImg.getImage(), bush.getxPos(), bush.getyPos(), 40, 30, this);
        }

        //arrow
        for (Arrow arrow : arrowList) {
            g.drawImage(arrowImg.getImage(), arrow.getxPos(), arrow.getyPos(), 40, 30, this);
        }


        for (Enemy enemy : enemyList) {
            g.drawImage(enemy.getImage(t).getImage(), enemy.getxPos(), enemy.getyPos(), 30, 35, this);
        }
        for (Boss boss : bossList) {
            g.drawImage(boss.getImage(t).getImage(), boss.getxPos(), boss.getyPos(), 30, 40, this);
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
                        (xMove == 50 && (yMove <= 175 && yMove > 125)) ||
                        (xMove == 80 && (yMove <= 125 && yMove > 25)) ||
                        (xMove == 150 && (yMove <= 125 && yMove > 80))
                )
        ) {
            up =- 10;

        } else if (k == 83 &&
                (
                        (xMove == 50 && (yMove >= 125 && yMove < 175)) ||
                        (xMove == 80 && (yMove >= 25 && yMove < 125)) ||
                        (xMove == 150 && (yMove >= 75 && yMove < 125))
                )
        ) {
            down =+ 10;

        } else if (k == 65
                &&
                (
                        (yMove == 175 && (xMove > 0 && xMove <= 290)) ||
                        (yMove == 125 && (xMove > 140 && xMove <= 210)) ||
                        (yMove == 125 && (xMove > 20 && xMove <= 90)) ||
                        (yMove == 75 && (xMove > 140 && xMove <= 290)) ||
                        (yMove == 25 && (xMove > 40 && xMove <= 120))
                )
        ) {
            character = new ImageIcon("Resources/Prop/zedL.png");
            direction = "left";
            left =- 10;

        } else if (k == 68
                &&
                (
                        (yMove == 175 && (xMove >= 0 && xMove < 290)) ||
                        (yMove == 125 && (xMove >= 140 && xMove < 210)) ||
                        (yMove == 125 && (xMove >= 20 && xMove < 90)) ||
                        (yMove == 75 && (xMove >= 140 && xMove < 290)) ||
                        (yMove == 25 && (xMove >= 40 && xMove < 120))
                )
        ) {
            character = new ImageIcon("Resources/Prop/zed.png");
            direction = "right";
            right =+ 10;

        } else if (k == 74) { //event เมื่อกดยิง j
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
        checkPickItem();
        checkNextLv();
        checkTP();
        checkHit();
        checkHitBoss();
        if (keyPick == 3) {


        }
    }


    private void checkHit() {
        for (Enemy enemy : enemyList) {
            if (overlaps(fire.getX(), fire.getY(), 25, enemy.getxPos(), enemy.getyPos(), 20)) {
                enemyList.remove(enemy);
            }
        }
    }
    private void checkHitBoss() {
        for (Boss boss : bossList) {
            if (overlaps(fire.getX(), fire.getY(), 25, boss.getxPos(), boss.getyPos(), 20)) {
                bossList.remove(boss);
            }
        }
    }

    private void checkTP() {
        if(overlaps(xMove, yMove, 10, bushList.get(0).getxPos(), bushList.get(0).getyPos(), 2)) {
           xMove = 240;
           yMove = 75;
        }
        if(overlaps(xMove, yMove, 10, bushList.get(1).getxPos(), bushList.get(1).getyPos(), 2)) {
            xMove = 50;
            yMove = 175;
        }

    }

    private void checkPickItem() {
        for (Key key : keyList) {
            if(overlaps(xMove, yMove, 10, key.getxPos(), key.getyPos(), 5)) {
                keyList.remove(key);
                keyPick += 1;
                break;
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
                {50, 50}, {150, 100}, {30, 150}, {190, 100}, {230, 100}, {150, 150}
        };

        positionItems = new int[][]{
                {50, 39}, {210, 140}, {200, 90}
        };

        positionBushs = new int[][]{
                {20, 178}, {270, 75}
        };

        positionArrows = new int[][]{
                {2, 178}, {255, 75}
        };

        positionEnemys = new int[][]{
                {50, 185}, {200, 85}
        };

        // {x, y, กว้าง, สูง}
        positionLadders = new int[][]{
                {80, 50, 20, 100}, {50, 152, 20, 52}, {150, 100, 20, 50}
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
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {

    }
}
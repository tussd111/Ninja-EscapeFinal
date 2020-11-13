package GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Fire {

    private int x,y;
    private String direction = "";
    public ImageIcon image = new ImageIcon("Resources/Prop/ninjastar.png");

    public Fire(int x, int y, String direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void tick(){

        switch (direction){
            case "left" :
                x -= 8;
                break;
            case "right" :
                x += 8;
                break;
        }
    }
    public void draw(Graphics2D g){
        g.drawImage(image.getImage(), x, y, 20, 20,null);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
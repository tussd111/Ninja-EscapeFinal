package Prop;

import javax.swing.*;

public class UPFloor {
    private Integer xPos;
    private Integer yPos;
    private Integer width = 80;
    private Integer height = 25;
    public ImageIcon image = new ImageIcon("Resources/Prop/upperfloor.png");

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getxPos() {
        return xPos;
    }

    public void setxPos(Integer xPos) {
        this.xPos = xPos;
    }

    public Integer getyPos() {
        return yPos;
    }

    public void setyPos(Integer yPos) {
        this.yPos = yPos;
    }
}

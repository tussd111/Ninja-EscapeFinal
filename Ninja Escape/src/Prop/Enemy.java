package Prop;

import javax.swing.*;
import java.awt.*;

public class Enemy {
    private Integer xPos;
    private Integer yPos;

    ImageIcon poroL = new ImageIcon("Resources/Prop/PoroL.png");
    ImageIcon poroR = new ImageIcon("Resources/Prop/PoroR.png");

    public ImageIcon getImage(int t) {
        if(t%2 == 0) {
            return poroL;
        } else {
            return poroR;
        }
    }

    public ImageIcon getPoroL() {
        return poroL;
    }

    public void setPoroL(ImageIcon poroL) {
        this.poroL = poroL;
    }

    public ImageIcon getPoroR() {
        return poroR;
    }

    public void setPoroR(ImageIcon poroR) {
        this.poroR = poroR;
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

package Prop;

import javax.swing.*;
import java.awt.*;

public class Boss {
    private Integer xPos;
    private Integer yPos;

    ImageIcon  settL = new ImageIcon("Resources/Enemy/settL.png");
    ImageIcon  settR = new ImageIcon("Resources/Enemy/settR.png");

    public ImageIcon getImage(int t) {
        if(t%2 == 0) {
            return settL;
        } else {
            return settR;
        }
    }

    public ImageIcon getPoroL() {
        return settL;
    }

    public void setPoroL(ImageIcon poroL) {
        this.settL = poroL;
    }

    public ImageIcon getPoroR() {
        return settR;
    }

    public void setPoroR(ImageIcon poroR) {
        this.settR = poroR;
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

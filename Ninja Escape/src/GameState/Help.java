package GameState;

import Main.GamePanel;
import TileMap.Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

public class Help extends GameState implements ImageObserver {

    ImageIcon help = new ImageIcon("Resources/Backgrounds/help.jpg");


    public Help(GameStateManager gsm) {
        this.gsm = gsm;

    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g) {
// clear screen
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

        // draw help
        g.drawImage(help.getImage(), 0, 0, this);

        //button back menu
        g.drawString("Enter To Back Menu" ,10,230);


    }

    @Override
    public void keyPressed(int k) {
        if(k == KeyEvent.VK_ENTER) {
            gsm.setState(GameStateManager.MENUSTATE);
        }
    }

    @Override
    public void keyReleased(int k) {

    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}

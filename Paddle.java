package main;
/*
 * Original Game developed by Atari Inc.
 * 	Re-designed by Jan Bodnar http://zetcode.com/tutorials/javagamestutorial/breakout/
 * 		Further modified and edited by Reid Parker
 * 			Version 1.0 - May 5, 2015 -BugBuster-
 */

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class Paddle extends Sprite implements Commons {

   // String paddle = "../images/ball.png";

    int dx;

    public Paddle() {

        ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/paddleBlue.png"));
        image = ii.getImage();

        width = image.getWidth(null);
        heigth = image.getHeight(null);

        resetState();
    }

    public void move() {
        x += dx;
        if (x <= 2) 
          x = 2;
        if (x >= Commons.PADDLE_RIGHT)
          x = Commons.PADDLE_RIGHT;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -8;

        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 8;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public void resetState() {
        x = 350;
        y = 720;
    }
}

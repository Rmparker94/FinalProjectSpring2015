package main;
/*
 * Original Game developed by Atari Inc.
 * 	Re-designed by Jan Bodnar http://zetcode.com/tutorials/javagamestutorial/breakout/
 * 		Further modified and edited by Reid Parker
 * 			Version 1.0 - May 5, 2015 -BugBuster-
 */

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int heigth;
    protected Image image;


    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return heigth;
    }

    Image getImage()
    {
      return image;
    }

    Rectangle getRect()
    {
      return new Rectangle(x, y, 
          image.getWidth(null), image.getHeight(null));
    }
}

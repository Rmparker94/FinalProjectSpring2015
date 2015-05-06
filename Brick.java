package main;
/*
 * Original Game developed by Atari Inc.
 * 	Re-designed by Jan Bodnar http://zetcode.com/tutorials/javagamestutorial/breakout/
 * 		Further modified and edited by Reid Parker
 * 			Version 1.0 - May 5, 2015 -BugBuster-
 */

import javax.swing.ImageIcon;

public class Brick extends Sprite {

    boolean destroyed;


    public Brick(int x, int y) {
      this.x = x;
      this.y = y;

      ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/bug2.png"));
      image = ii.getImage();

      width = image.getWidth(null);
      heigth = image.getHeight(null);

      destroyed = false;
    }

    public boolean isDestroyed()
    {
      return destroyed;
    }

    public void setDestroyed(boolean destroyed)
    {
      this.destroyed = destroyed;
    }

}

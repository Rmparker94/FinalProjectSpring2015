package main;
/*
 * Original Game developed by Atari Inc.
 * 	Re-designed by Jan Bodnar http://zetcode.com/tutorials/javagamestutorial/breakout/
 * 		Further modified and edited by Reid Parker
 * 			Version 1.0 - May 5, 2015 -BugBuster-
 */

import javax.swing.ImageIcon;


public class Ball extends Sprite implements Commons {

   private int xdir;
   private int ydir;

  // protected String ball = "../workspaceHome/ball.png";

   public Ball() {

     xdir = 5;
     ydir = -3;

     ImageIcon ii = new ImageIcon(this.getClass().getResource("/images/ball1.png"));
     image = ii.getImage();

     width = image.getWidth(null);
     heigth = image.getHeight(null);

     resetState();
    }


    public void move()
    {
      x += xdir;
      y += ydir;

      if (x < .1) {
        setXDir(3);
      }

      if (x > BALL_RIGHT - .1) {
        setXDir(-3);
      }

      if (y < .1) {
        setYDir(5);
      }
    }

    public void resetState() 
    {
      x = 220;
      y = 600;
    }

    public void setXDir(int x)
    {
      xdir = x;
    }

    public void setYDir(int y)
    {
      ydir = y;
    }

    public int getYDir()
    {
      return ydir;
    }
}

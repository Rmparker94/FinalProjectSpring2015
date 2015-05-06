package main;
/*
 * Original Game developed by Atari Inc.
 * 	Re-designed by Jan Bodnar http://zetcode.com/tutorials/javagamestutorial/breakout/
 * 		Further modified and edited by Reid Parker
 * 			Version 1.0 - May 5, 2015 -BugBuster-
 * Audio Files: http://www.wavsource.com/sfx/sfx2.htm
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

public class Board extends JPanel implements Commons {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image ii;
	Timer timer;
	String message = "You fail! Play again? (Press spacebar)";
	Ball ball;
	Paddle paddle;
	Brick bricks[];

	private AudioInputStream paddleSound, wallSound, endGameSound, brickSound,
			victorySound;
	private Clip paddleSoundClip, wallSoundClip, endGameSoundClip,
			brickSoundClip, victorySoundClip;
	boolean ingame = true;
	int timerId;

	public Board() {
		try {
			paddleSound = AudioSystem
					.getAudioInputStream(new File("floop.wav"));
			paddleSoundClip = AudioSystem.getClip();
			paddleSoundClip.open(paddleSound);

			wallSound = AudioSystem.getAudioInputStream(new File(
					"metalSound.wav"));
			wallSoundClip = AudioSystem.getClip();
			wallSoundClip.open(wallSound);

			endGameSound = AudioSystem
					.getAudioInputStream(new File("laugh.wav"));
			endGameSoundClip = AudioSystem.getClip();
			endGameSoundClip.open(endGameSound);

			brickSound = AudioSystem.getAudioInputStream(new File(
					"quickFart.wav"));
			brickSoundClip = AudioSystem.getClip();
			brickSoundClip.open(brickSound);

			victorySound = AudioSystem.getAudioInputStream(new File("wow.wav"));
			victorySoundClip = AudioSystem.getClip();
			victorySoundClip.open(victorySound);

		} catch (Exception e) {
			System.out.println("Error with sound file");
		}

		addKeyListener(new TAdapter());
		setFocusable(true);

		bricks = new Brick[1000];
		setDoubleBuffered(true);
		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), 1000, 10);
	}

	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	public void gameInit() {

		ball = new Ball();
		paddle = new Paddle();

		int k = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 16; j++) {
				bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
				k++;
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		if (ingame) {
			g.drawImage(ball.getImage(), ball.getX(), ball.getY(),
					ball.getWidth(), ball.getHeight(), this);
			g.drawImage(paddle.getImage(), paddle.getX(), paddle.getY(),
					paddle.getWidth(), paddle.getHeight(), this);

			for (int i = 0; i < 80; i++) {
				if (!bricks[i].isDestroyed())
					g.drawImage(bricks[i].getImage(), bricks[i].getX(),
							bricks[i].getY(), bricks[i].getWidth(),
							bricks[i].getHeight(), this);
			}
		} else {

			Font font = new Font("Verdana", Font.BOLD, 18);
			FontMetrics metr = this.getFontMetrics(font);

			g.setColor(Color.BLUE);
			g.setFont(font);
			g.drawString(message,
					(Commons.WIDTH - metr.stringWidth(message)) / 2,
					Commons.WIDTH / 2);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			paddle.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			paddle.keyPressed(e);
			if (e.getKeyCode() == KeyEvent.VK_SPACE && ingame == false) {
				new Main();
			}
		}

	}

	class ScheduleTask extends TimerTask {

		public void run() {
			ball.move();
			paddle.move();
			checkCollision();
			repaint();
		}
	}

	public void stopGame() {
		ingame = false;
		timer.cancel();
	}

	public void checkCollision() {
		int score = 0;

		if (ball.getRect().getMaxY() > Commons.BOTTOM) {
			endGameSoundClip.start();
			stopGame();

		}

		for (int i = 0, j = 0; i < 80; i++) {
			if (bricks[i].isDestroyed()) {
				brickSoundClip.start();
				j++;
				score = j;
			}
			if (j == 40) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				message = "NICE! " + "Your score is: " + score + " - Play again? (Press spacebar)";
				victorySoundClip.start();
				stopGame();
			}
		}

		if ((ball.getRect()).intersects(paddle.getRect())) {

			int paddleLPos = (int) paddle.getRect().getMinX();
			int ballLPos = (int) ball.getRect().getMinX();

			int first = paddleLPos + 15;
			int second = paddleLPos + 30;
			int third = paddleLPos + 45;
			int fourth = paddleLPos + 60;
			int fifth = paddleLPos + 75;
			int sixth = paddleLPos + 99;

			if (ballLPos < first) {
				ball.setXDir(-3);
				ball.setYDir(-5);
				paddleSoundClip.setFramePosition(0);
				paddleSoundClip.start();
			}

			if (ballLPos >= first && ballLPos < second) {
				ball.setXDir(-2);
				ball.setYDir(-1 * ball.getYDir());
				paddleSoundClip.setFramePosition(0);
				paddleSoundClip.start();
			}

			if (ballLPos >= second && ballLPos < third) {
				ball.setXDir(-1);
				ball.setYDir(-5);
				paddleSoundClip.setFramePosition(0);
				paddleSoundClip.start();
			}

			if (ballLPos >= third && ballLPos < fourth) {
				ball.setXDir(0);
				ball.setYDir(-1 * ball.getYDir());
				paddleSoundClip.setFramePosition(0);
				paddleSoundClip.start();
			}

			if (ballLPos >= fourth && ballLPos < fifth) {
				ball.setXDir(2);
				ball.setYDir(-5);
				paddleSoundClip.setFramePosition(0);
				paddleSoundClip.start();
			}

			if (ballLPos >= fifth && ballLPos < sixth) {
				ball.setXDir(4);
				ball.setYDir(-5);
				paddleSoundClip.setFramePosition(0);
				paddleSoundClip.start();
			}
		}
		// collision with left&right&top wall
		if ((ball.getX() <= 2 || ball.getX() > BALL_RIGHT - 3 || ball.getY() < 1)) {
			wallSoundClip.setFramePosition(0);
			wallSoundClip.start();
		}

		for (int i = 0; i < 80; i++) {
			if ((ball.getRect()).intersects(bricks[i].getRect())) {

				int ballLeft = (int) ball.getRect().getMinX();
				int ballHeight = (int) ball.getRect().getHeight();
				int ballWidth = (int) ball.getRect().getWidth();
				int ballTop = (int) ball.getRect().getMinY();

				Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
				Point pointLeft = new Point(ballLeft - 1, ballTop);
				Point pointTop = new Point(ballLeft, ballTop - 1);
				Point pointBottom = new Point(ballLeft, ballTop + ballHeight
						+ 1);

				if (!bricks[i].isDestroyed()) {
					if (bricks[i].getRect().contains(pointRight)) {
						ball.setXDir(-1);
						brickSoundClip.setFramePosition(0);
						brickSoundClip.start();
					}

					else if (bricks[i].getRect().contains(pointLeft)) {
						ball.setXDir(1);
						brickSoundClip.setFramePosition(0);
						brickSoundClip.start();
					}

					if (bricks[i].getRect().contains(pointTop)) {
						ball.setYDir(5);
						brickSoundClip.setFramePosition(0);
						brickSoundClip.start();
					}

					else if (bricks[i].getRect().contains(pointBottom)) {
						ball.setYDir(-5);
						brickSoundClip.setFramePosition(0);
						brickSoundClip.start();
					}

					bricks[i].setDestroyed(true);
				}
			}
		}
	}

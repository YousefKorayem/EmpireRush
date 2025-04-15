package main;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
	//Panel dimensions information
	static final int GAME_WIDTH = 1280;
	static final int GAME_HEIGHT = 720;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

	//reference to the game level object
	GameLevel map;
	
	//Graphics and process data
	Thread gameThread;
	Image image;
	Graphics graphics;
	GameFrame frame;
	
	GamePanel(GameFrame frame){
		//set a reference to the frame holding the panel
		this.frame = frame;
		
		//construct the level and pass to it the path of the csv containing the level data
		map = new GameLevel(10, "level1.csv", new Dimension(720, 720), new Dimension(48,48));
		
		//window and thread stuff
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE);
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		map.draw(graphics);
		g.drawImage(image,  0,  0,  this);
	}
	

	public void checkCollisions() {
		
	}
	
	public void run() {
		//Game loop
		long lastTime = System.nanoTime();
		double tickNumber = 60;
		double ns = 1000000000 / tickNumber;
		double delta = 0;
		
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				map.move();
				checkCollisions();
				repaint();
				delta--;
			}
		}
	}
	
}

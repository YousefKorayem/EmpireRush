package main;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
	//Panel dimensions information
	static final int GAME_WIDTH = 1280;
	static final int GAME_HEIGHT = 720;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	
	//enemy entity holder
	ArrayList<Enemy> enemies;
	//reference to the game level object
	GameLevel map;
	
	//Graphics and process data
	Thread gameThread;
	Image image;
	Graphics graphics;
	GameFrame frame;
	
	GamePanel(GameFrame frame){
		//set a refernce to the frame holding the panel
		this.frame = frame;
		
		//construct the level and pass to it the path of the csv containing the level data
		map = new GameLevel(10, "level1.csv");
		
		//generate a test enemy and add it to the list of enemies
		Enemy e = new Enemy(1,1,3, map);
		enemies = new ArrayList<>();
		enemies.add(e);
		
		//window and thread stuff
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE);
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,  0,  0,  this);
	}
	
	//draw every item that should be drawn on the game field
	public void draw(Graphics g) {
		for(Enemy e : enemies) {
			e.draw(g);
		}
		for(Checkpoint cp : map.checkpoints) {
			cp.draw(g);
		}
		for(Camp c : map.camps) {
			c.draw(g);
			if(c.tower != null) c.tower.draw(g);
		}
	}
	
	public void move() {
		//here lies logic for the movement of the enemy
		Iterator<Enemy> iterator = enemies.iterator();
		while(iterator.hasNext()) {
			Enemy enemy = iterator.next();
			if(enemy.active == false) {
				iterator.remove();
				System.out.println("enemy inactive");
			}
			enemy.move();
			if(!map.contains(enemy.position)) {
				iterator.remove();
				System.out.println("enemy out of bounds");
			}
			if(enemy.active == false) {
				iterator.remove();
				System.out.println("enemy inactive");
			}
		}
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
				move();
				checkCollisions();
				repaint();
				delta--;
			}
		}
	}
	
}

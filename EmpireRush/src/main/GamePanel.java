package main;
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
	
	static final int GAME_WIDTH = 1280;
	static final int GAME_HEIGHT = 720;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	
	ArrayList<Enemy> enemies;
	GameLevel map;
	
	Thread gameThread;
	Image image;
	Graphics graphics;
	GameFrame frame;
	
	GamePanel(GameFrame frame){
		this.frame = frame;
		
		map = new GameLevel(new Point(0, 360));
		
		Enemy e = new Enemy(1,1,3, map);
		enemies = new ArrayList<>();
		enemies.add(e);
		
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
	
	public void draw(Graphics g) {
		for(Enemy e : enemies) {
			e.draw(g);
		}
	}
	
	public void move() {
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

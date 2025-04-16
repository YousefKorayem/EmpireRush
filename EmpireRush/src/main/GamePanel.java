package main;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
	//Panel dimensions information
	static final int GAME_WIDTH = 720;
	static final int GAME_HEIGHT = 720;
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

	//reference to the game level object
	GameLevel map;
	
	//Graphics and process data
	Thread gameThread;
	Image image;
	Graphics graphics;
	GameFrame frame;
	SidePanel sidePanel;
	
	Boolean paused;
	Boolean gameOver = false;
	
	String mouseSelection = "";
	
	GamePanel(GameFrame frame){
		//set a reference to the frame holding the panel
		this.frame = frame;
		
		//construct the level and pass to it the path of the csv containing the level data
		map = new GameLevel(10, 500, "level1.csv","level1script.txt", SCREEN_SIZE, new Dimension(48,48), this);
		paused = true;
		
		//window and thread stuff
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE);
		gameThread = new Thread(this);
		gameThread.start();
		
		setOpaque(true);
		setBackground(Color.black);
		
		// Mouse click listener for clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(!mouseSelection.equals("")) {
            		//try to place a tower here
            		switch (mouseSelection) {
            			case "LaserTower":
            				map.towers.add(new LaserTower(e.getPoint()));
            				mouseSelection = "";
            				sidePanel.mouseSelectionText = "No Selection";
            				sidePanel.repaint();
            				repaint();
            		}
            	}
            }
        });
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		map.draw(graphics);
		g.drawImage(image,  0,  0,  this);
	}
	

	public void checkCollisions() {
		
	}
	
	public void togglePause() {
		if(gameOver) return;
		paused = !paused;
	}
	
	public void run() {
		//Game loop
		long lastTime = System.nanoTime();
		double tickNumber = 60;
		double ns = 1000000000 / tickNumber;
		double delta = 0;
		long lastSpawnTime = System.nanoTime();

		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				if (!paused) {
					boolean t = spawnEnemy(now, lastSpawnTime);
					if(t) lastSpawnTime = now;
	                map.move(now);
	                if(map.enemies.isEmpty() && map.enemyQueue.isEmpty()) gameOver(map.score + map.gold - (map.maxHealth - map.health));
	                checkCollisions();
	                repaint();
	            }
				delta--;
			}
		}
	}
	
	public boolean spawnEnemy(long now, long lastSpawnTime) {
		//enemy spawning logic here
		if(!map.enemyQueue.isEmpty()) {
			if((now - lastSpawnTime)/100000 > map.enemyQueue.peek().delay) {
				Enemy e = map.enemyQueue.remove();
				e.active = true;
				map.enemies.add(e);
				return true;
			}
		}
		return false;
	}
	
	public void gameOver(int score) {
		gameOver = true;
		paused = true;
		frame.gameOverScreen(score);
	}

	public void mouseSelect(String string) {
		if(sidePanel.mouseSelectionText.equals("No Selection") || sidePanel.mouseSelectionText.equals("Too Expensive")) {
			switch (string) {
				case "LaserTower":
					if(map.gold < map.laserTowerCost) sidePanel.mouseSelectionText = "Too Expensive";
					else{
						map.transaction(-map.laserTowerCost);
						sidePanel.mouseSelectionText = "Selected: Laser Tower";
						mouseSelection = "LaserTower";
					}
					break;
				case "":
					sidePanel.mouseSelectionText = "No Selection";
					break;
			}

			sidePanel.repaint();
		}
	}

}

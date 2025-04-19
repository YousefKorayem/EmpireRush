package main;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel{
	//Panel dimensions information
	static final int WIDTH = 720;
	static final int HEIGHT = 720;
	static final Dimension SCREEN_SIZE = new Dimension(WIDTH, HEIGHT);

	GameController game;
	
	//Graphics and process data
	Thread gameThread;
	Image image;
	Graphics graphics;
	
	GamePanel(GameController game, int level){
		this.game = game;
		
		//window and thread stuff
		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE);
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,  0,  0,  this);
	}
	
	public void draw(Graphics g) {
//		for(Checkpoint cp : game.getCheckpoints()) {
//			cp.draw(g);
//		}
		for(Tower t : game.getTowers()) {
			t.draw(g);
			t.drawRange(g);
		}
		for(Enemy e : game.getEnemies()) {
			e.draw(g);
		}
	}
}

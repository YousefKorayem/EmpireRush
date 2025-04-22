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
		
		// Track mouse movement
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
            	Point mousePoint = new Point(e.getX(), e.getY());
            	if(game.getHeldTower() != null) {
        			game.getHeldTower().positionTower(mousePoint);
        			repaint();
        		}
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	
            	Point mousePoint = new Point(e.getX(), e.getY());
                Tower heldTower = game.getHeldTower();
                if (heldTower != null) {
                	for(Checkpoint cp : game.getCheckpoints()) {
                		if(cp.contains(mousePoint)) {
                			return;
                		}
                	}
                    // Set tower's final position
                    heldTower.placeTower(mousePoint);
                    // Clear the held tower
                    game.setHeldTower(null);
                    // Redraw
                    repaint();
                    return; 
                }
                
                for(Tower t : game.getTowers()) {
            		if(t.contains(mousePoint)) {
            			//we clicked on a tower. Select it!
            			game.setSelectedTower(t);
            			game.setHeldTower(null);
            			repaint();
            			return;
            		}
            	}
                
                System.out.println("clicked on nothing");
                game.setSelectedTower(null);
                repaint();
            }
        });
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		
		g.drawImage(image,  0,  0,  this);
	}
	
	public void draw(Graphics g) {
		drawBackground(g);
		for(Tower t : game.getTowers()) {
			t.draw(g);
		}
		
		if(game.getHeldTower() != null) {
			game.getHeldTower().simulateRange(g);
		}
		else if(game.getSelectedTower() != null) {
			game.getSelectedTower().drawRange(g);
		}
		
		for(Enemy e : game.getEnemies()) {
			e.draw(g);
		}
	}
	
	public void drawBackground(Graphics g) {
		for(Checkpoint cp : game.getCheckpoints()) {
			cp.draw(g);
		}
	}
}

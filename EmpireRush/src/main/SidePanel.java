package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePanel extends JPanel{
	static final int PANEL_WIDTH = 560;
	static final int PANEL_HEIGHT = 720;
	static final Dimension PANEL_SIZE = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	
	Image image;
	Graphics graphics;
	String mouseSelectionText = "No Selection";
	
	GameController game;
	
//	ArrayList<Button> buttons;
	
	SidePanel(GameController game, int level){
		this.game = game;
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setFocusable(true);
		setPreferredSize(PANEL_SIZE);
        setOpaque(true); 
		setBackground(Color.CYAN); // Set the background color
		
		//Creating startButton
		JButton playButton = new JButton("Play");
		playButton.addActionListener(e -> game.togglePause());
		add(playButton);
		
//		JButton laserTowerButton = new JButton("Laser Tower: 100g");
//		laserTowerButton.addActionListener(e -> gamePanel.mouseSelect("LaserTower"));
//		add(laserTowerButton);
		
		repaint();
		revalidate();
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        draw(g);
	}
	
	public void draw(Graphics g) {
		// Set font and color for text
        g.setColor(Color.WHITE);  // Set text color
        g.setFont(new Font("Arial", Font.BOLD, 24));  // Set font (font name, style, size)
        
        String healthString = "Health: " + game.getHealth();
        // Draw the text at the specified coordinates
        g.drawString(healthString, 250, 100);  // Draw text at (x, y)
        
        String scoreString = "Score: " + game.getScore();
        // Draw the text at the specified coordinates
        g.drawString(scoreString, 250, 200);  // Draw text at (x, y)
        
        String goldString = "Gold: " + game.getGold();
        // Draw the text at the specified coordinates
        g.drawString(goldString, 250, 300);  // Draw text at (x, y

        g.drawString(mouseSelectionText, 250, 400);
	}
}

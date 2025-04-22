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
	
	JButton pauseButton;
	
	SidePanel(GameController game, WindowController window){
		this.game = game;
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setFocusable(true);
		setPreferredSize(PANEL_SIZE);
		
		populatePanel(game, window);
		
		repaint();
		revalidate();
	}
	
	public void populatePanel(GameController game, WindowController window) {
		//Creating startButton
		pauseButton = new JButton("Play");
		pauseButton.addActionListener(e -> game.togglePause());
		add(pauseButton);
		
		//Creating mainMenuButton
		JButton fastForwardButton = new JButton("Toggle Fast Forward");
		fastForwardButton.addActionListener(e -> game.toggleFF());
		add(fastForwardButton);
		
		//Creating mainMenuButton
		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.addActionListener(e -> window.toMainMenuView());
		add(mainMenuButton);
		
		//Creating exitButton
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(e -> System.exit(0));
		add(quitButton);
		
		//Create tower buttons
		for(TowerType t: TowerType.values()) {
			JButton button = new JButton(t.displayName);
			button.addActionListener(e -> game.buyTower(t));
			add(button);
		}
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

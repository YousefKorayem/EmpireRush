package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

class LevelSelectPanel extends JPanel{
		//Set here the dimensions of the menu panel
		static final int WIDTH = 1280;
		static final int HEIGHT = 720;
		static final Dimension SCREEN_SIZE = new Dimension(WIDTH, HEIGHT);
		
		GameController game;
		WindowController window;
		
		LevelSelectPanel(WindowController windowController, GameController game){
			this.window = windowController;
			this.game = game;
			setFocusable(true);
			setPreferredSize(SCREEN_SIZE);
			populateMenu();
		}
		
		public void populateMenu() {
			JButton selectLevel = new JButton("Select Level 1");
			selectLevel.addActionListener(e -> game.setCurrentLevel(1));
			add(selectLevel);
			
			//Creating playButton
			JButton playButton = new JButton("Play Game");
			playButton.addActionListener(e -> game.newGame());
			add(playButton);
			
			revalidate();
			repaint();
		}
		
		public void paint(Graphics g) {
	        super.paint(g);
	        // Set font and color for text
	        g.setColor(Color.WHITE);  // Set text color
	        g.setFont(new Font("Arial", Font.BOLD, 24));  // Set font (font name, style, size)
	        
	        // Draw the text at the specified coordinates
	        g.drawString("Empire Rush", 250, 100);  // Draw text at (x, y)
		}
		
		
}

package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

class GameOverPanel extends JPanel{
		//Set here the dimensions of the menu panel
		static final int WIDTH = 1280;
		static final int HEIGHT = 720;
		static final Dimension SCREEN_SIZE = new Dimension(WIDTH, HEIGHT);
		
		String output;
		
		ArrayList<JButton> buttons;
		
		GameOverPanel(GameController game, WindowController window){
			
			setLayout(new FlowLayout(FlowLayout.CENTER));
			this.setFocusable(true);
			this.setPreferredSize(SCREEN_SIZE);
			
			//Creating title text
			if(game.getScore() == 0) output = "You Lost!";
			else {
				output = "You Win! Score: " + game.getScore();
			}
			
			//Creating mainMenuButton
			JButton mainMenuButton = new JButton("Main Menu");
			mainMenuButton.addActionListener(e -> window.toMainMenuView());
			add(mainMenuButton);
			
			//Creating exitButton
			JButton quitButton = new JButton("Quit");
			quitButton.addActionListener(e -> System.exit(0));
			add(quitButton);
			
			revalidate();
			repaint();
		}
		
		public void paint(Graphics g) {
	        super.paint(g);
	        // Set font and color for text
	        g.setColor(Color.WHITE);  // Set text color
	        g.setFont(new Font("Arial", Font.BOLD, 24));  // Set font (font name, style, size)
	        
	        // Draw the text at the specified coordinates
	        g.drawString(output, 250, 100);  // Draw text at (x, y)
		}
		
		
}

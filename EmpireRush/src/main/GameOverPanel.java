package main;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

 class GameOverPanel extends JPanel{
		//Set here the dimensions of the menu panel
		static final int MENU_WIDTH = 1280;
		static final int MENU_HEIGHT = 720;
		static final Dimension SCREEN_SIZE = new Dimension(MENU_WIDTH, MENU_HEIGHT);
		
		GameFrame frame;
		Graphics graphics;
		Image image;
		String output;
		
		ArrayList<JButton> buttons;
		
		GameOverPanel(GameFrame frame, int score){
			//Assign the frame to this panel's attribute
			this.frame = frame;
			
			setLayout(new FlowLayout(FlowLayout.CENTER));
			this.setFocusable(true);
			this.setPreferredSize(SCREEN_SIZE);
			setOpaque(true);
			setBackground(Color.black);
			
			//Creating title text
			if(score == 0) output = "You Lost!";
			else {
				output = "You Win! Score: " + score;
			}
			
			//Creating playButton
			JButton playButton = new JButton("Play Again");
			playButton.addActionListener(e -> frame.switchToGamePanels());
			add(playButton);
			
			//Creating exitButton
			JButton quitButton = new JButton("Quit");
			quitButton.addActionListener(e -> System.exit(0));
			add(quitButton);
			
			//Creating mainMenuButton
			JButton mainMenuButton = new JButton("Main Menu");
			mainMenuButton.addActionListener(e -> frame.switchToMainMenu());
			add(mainMenuButton);
			
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

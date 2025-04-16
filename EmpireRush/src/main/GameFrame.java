package main;
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{
	
	MainMenuPanel mainMenuPanel;
	
	GameFrame(){
		
		//Set the current content as the main menu that was just created
		mainMenuPanel = new MainMenuPanel(this);
		this.add(mainMenuPanel);
		
		//Set the title of the window
		this.setTitle("EmpireRush");
		
		//Disable resizing, set the background of the window, and make sure the process ends on closing the window
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		//Resize the window to fit around the panel, make the window visible, and make the window appear in the center of the screen
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public void switchToGamePanels() {
		getContentPane().removeAll();
		
		GamePanel gamePanel = new GamePanel(this);
        SidePanel sidePanel = new SidePanel(gamePanel);
        gamePanel.sidePanel = sidePanel;
        
        setLayout(new BorderLayout());
        
        add(gamePanel, BorderLayout.CENTER); // Game panel takes the main area
        add(sidePanel, BorderLayout.EAST);    // Side panel goes to the right side
        
        // Revalidate and repaint the frame to reflect changes
        revalidate();
        repaint();
	}
	
	public void switchToMainMenu() {
		getContentPane().removeAll();
        
        setLayout(new BorderLayout());
        
        add(mainMenuPanel);
        
        // Revalidate and repaint the frame to reflect changes
        revalidate();
        repaint();
	}
	
	public void gameOverScreen(int score) {
		getContentPane().removeAll();
		
		setLayout(new BorderLayout());
		
		add(new GameOverPanel(this, score));
		
		// Revalidate and repaint the frame to reflect changes
        revalidate();
        repaint();
	}
}

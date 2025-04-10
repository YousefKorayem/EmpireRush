package main;
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{
	
	GameFrame(){
		//Create a panel for the main menu; pass it this frame as an argument
		MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
		
		//Set the current content as the main menu that was just created
		this.setContentPane(mainMenuPanel);
		
		//Set the title of the window
		this.setTitle("EmpireRush");
		
		//Disable resizing, set the background of the window, and make sure the process ends on closing the window
		this.setResizable(false);;
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Resize the window to fit around the panel, make the window visible, and make the window appear in the center of the screen
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}

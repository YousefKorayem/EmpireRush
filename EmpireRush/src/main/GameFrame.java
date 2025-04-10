package main;
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{
	
	GameFrame(){
		MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
		this.setContentPane(mainMenuPanel);
		this.setTitle("EmpireRush");
		this.setResizable(false);;
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}

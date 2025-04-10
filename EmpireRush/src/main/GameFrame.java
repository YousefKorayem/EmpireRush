package main;
import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame{
	
	GamePanel gamePanel;
	MainMenuPanel mainMenuPanel;
	
	GameFrame(){
		mainMenuPanel = new MainMenuPanel();
		gamePanel = new GamePanel();
		this.add(mainMenuPanel);
		this.setTitle("EmpireRush");
		this.setResizable(false);;
		this.setBackground(Color.black);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public void toGamePanel() {
		this.removeAll();
		this.add(gamePanel);
		this.revalidate();
		this.repaint();
	}
	
	public void toMainMenu() {
		this.removeAll();
		this.add(mainMenuPanel);
		this.revalidate();
		this.repaint();
	}
	
}

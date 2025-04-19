package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class WindowController {
	//Window
	private JFrame gameFrame;
	
	//GameController
	private GameController gameController;
	
	//Panels
	private MainMenuPanel mainMenuPanel;
	private GamePanel gamePanel;
	private SidePanel sidePanel;
	private GameOverPanel gameOverPanel;
	
	public WindowController() {
		gameFrame = new JFrame();
		gameController = new GameController(this);
		mainMenuPanel = new MainMenuPanel(this, gameController);
		
		initWindow();
		toMainMenuView();
	}
	
	public void initWindow() {
		gameFrame.setTitle("Java Rush!");
		gameFrame.setResizable(false);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.pack();
		gameFrame.setVisible(true);
	}
	
	public void toMainMenuView() {
		gameFrame.getContentPane().removeAll();
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(mainMenuPanel);
		
		gameFrame.setLocationRelativeTo(null);
		gameFrame.pack();
		gameFrame.revalidate();
		gameFrame.repaint();
	}
	
	public void toGameView() {
		gameFrame.getContentPane().removeAll();
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(gamePanel, BorderLayout.CENTER);
		gameFrame.add(sidePanel, BorderLayout.EAST);
		
		gameFrame.pack();
		gameFrame.revalidate();
		gameFrame.repaint();
	}
	
	public void toGameOverView() {
		gameFrame.getContentPane().removeAll();
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(gameOverPanel);
		
		gameFrame.revalidate();
		gameFrame.repaint();
	}
	
	public void repaintAllActivePanels() {
		if(gameController.getGameState() == 1) {
			gamePanel.repaint();
			sidePanel.repaint();
			return;
		}
		if(gameController.getGameState() == -1) {
			mainMenuPanel.repaint();
		}
		
	}
	
	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	
	public void setSidePanel(SidePanel sidePanel) {
		this.sidePanel = sidePanel;
	}
	
	public SidePanel getSidePanel() {
		return sidePanel;
	}
	
	public void setGameOverPanel(GameOverPanel gameOverPanel) {
		this.gameOverPanel = gameOverPanel;
	}
	
	public GameOverPanel getGameOverPanel() {
		return gameOverPanel;
	}
}

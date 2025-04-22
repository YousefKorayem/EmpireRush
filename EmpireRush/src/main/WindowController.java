package main;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JFrame;

public class WindowController {
	//Window
	private JFrame gameFrame;
	
	//GameController
	private GameController game;
	
	//Panels
	private MainMenuPanel mainMenuPanel;
	private LevelSelectPanel levelSelectPanel;
	private GamePanel gamePanel;
	private SidePanel sidePanel;
	private GameOverPanel gameOverPanel;
	
	//View
	//0: MainMenu
	//1: Game
	//2: GameOver
	//3: Level Select
	private int view;
	
	public WindowController() {
		gameFrame = new JFrame();
		game = new GameController(this);
		mainMenuPanel = new MainMenuPanel(this, game);
		levelSelectPanel = new LevelSelectPanel(this, game);
		
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
		view = 0;
		if(game != null) game.setRunning(false);
		
		gameFrame.setLocationRelativeTo(null);
		gameFrame.pack();
		gameFrame.revalidate();
		gameFrame.repaint();
	}
	
	public void toLevelSelectView() {
		gameFrame.getContentPane().removeAll();
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(levelSelectPanel);
		view = 3;
		if(game != null) game.setRunning(false);
		
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
		view = 1;
		
		gameFrame.pack();
		gameFrame.revalidate();
		gameFrame.repaint();
	}
	
	public void toGameOverView() {
		gameFrame.getContentPane().removeAll();
		gameFrame.setLayout(new BorderLayout());
		gameFrame.add(gameOverPanel);
		view = 2;
		if(game != null) game.setRunning(false);
		
		gameFrame.revalidate();
		gameFrame.repaint();
	}
	
	public void repaintAllActivePanels() {
		if(game.getGameState() == 1 || game.getGameState() == 0) {
			gamePanel.repaint();
			sidePanel.repaint();
			return;
		}
		if(game.getGameState() == -1) {
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

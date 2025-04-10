package main;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MainMenuPanel extends JPanel implements Runnable{
		static final int MENU_WIDTH = 1280;
		static final int MENU_HEIGHT = 720;
		static final Dimension SCREEN_SIZE = new Dimension(MENU_WIDTH, MENU_HEIGHT);
		
		Thread menuThread;
		Image image;
		Graphics graphics;
		Random random;
		
		MenuButton title;
		MenuButton playButton;
		MenuButton quitButton;
		
		MainMenuPanel(){
			title = new MenuButton(640, 100, 200, 100, 1, "Empire Rush", Color.green);
			playButton = new MenuButton(640, 550, 300, 100, 2, "Play Game", Color.green);
			quitButton = new MenuButton(640, 750, 500, 100, 2, "Play Game", Color.green);
			
			this.setFocusable(true);
			this.addKeyListener(new AL());
			this.setPreferredSize(SCREEN_SIZE);
			
			menuThread = new Thread(this);
			menuThread.start();
		}
		
		public void run() {
			
		}
		
		public class AL extends KeyAdapter {
			
			public void keyPressed(KeyEvent e) {

			}
			
			public void keyReleased(KeyEvent e) {

			}
			
		}
}

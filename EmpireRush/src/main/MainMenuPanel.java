package main;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MainMenuPanel extends JPanel implements Runnable{
		static final int MENU_WIDTH = 1280;
		static final int MENU_HEIGHT = 720;
		static final Dimension SCREEN_SIZE = new Dimension(MENU_WIDTH, MENU_HEIGHT);
		
		GameFrame frame;
		Thread menuThread;
		Image image;
		Graphics graphics;
		Random random;
		
		ArrayList<MenuButton> buttons;
		
		MainMenuPanel(GameFrame frame){
			this.frame = frame;
			
			buttons = new ArrayList<>();
			MenuButton title = new MenuButton(540, 100, 400, 100, 1, "Empire Rush", Color.black, () -> System.out.println());
			MenuButton playButton = new MenuButton(540, 350, 400, 100, 2, "Play Game", Color.green, this::switchToGamePanel);
			MenuButton quitButton = new MenuButton(540, 550, 400, 100, 2, "Quit Game", Color.green, () -> System.exit(0));
			buttons.add(title);
			buttons.add(playButton);
			buttons.add(quitButton);
			
			 addMouseListener(new MouseAdapter() {
		            @Override
		            public void mouseClicked(MouseEvent e) {
		                for (MenuButton button : buttons) {
		                    button.click(e.getX(), e.getY());
		                }
		            }
		        });
			
			this.setFocusable(true);
			this.setPreferredSize(SCREEN_SIZE);
			
			menuThread = new Thread(this);
			menuThread.start();
		}
		
		public void paint(Graphics g) {
			image = createImage(getWidth(), getHeight());
			graphics = image.getGraphics();
			draw(graphics);
			g.drawImage(image, 0, 0, this);
		}
		
		public void draw(Graphics g) {
	        for (MenuButton button : buttons) {
	            button.draw((Graphics2D) g);
	        }
		}
		
		public void run() {
			//Game loop
			long lastTime = System.nanoTime();
			double tickNumber = 60;
			double ns = 1000000000 / tickNumber;
			double delta = 0;
			
//			long timer = System.currentTimeMillis(); // Track elapsed time in ms
//			int frames = 0; // How many times we repaint in one second
			
			while(true) {
				long now = System.nanoTime();
				delta += (now - lastTime)/ns;
//				System.out.println(delta);
				lastTime = now;
				if(delta >= 1) {
					repaint();
//					frames++;
					delta--;
				}
				
				// Every 1 second, print FPS and reset frame count
//		        if (System.currentTimeMillis() - timer >= 1000) {
//		            System.out.println("FPS: " + frames);
//		            frames = 0;
//		            timer += 1000;
//		        }
			}
		}
		
		public void switchToGamePanel() {
			GamePanel gamePanel = new GamePanel(frame);
			frame.setContentPane(gamePanel);
			frame.revalidate();
			frame.repaint();
		}
}

package main;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

 class MainMenuPanel extends JPanel{
		//Set here the dimensions of the menu panel
		static final int MENU_WIDTH = 1280;
		static final int MENU_HEIGHT = 720;
		static final Dimension SCREEN_SIZE = new Dimension(MENU_WIDTH, MENU_HEIGHT);
		
		//Here is a reference to the frame containing the panel, the thread the panel is running on
		GameFrame frame;
		Thread menuThread;
		
		//Graphics content
		Image image;
		Graphics graphics;
		
		//An arraylist that holds all the buttons currently on the main menu
		ArrayList<MenuButton> buttons;
		
		MainMenuPanel(GameFrame frame){
			//Assign the frame to this panel's attribute
			this.frame = frame;
			
			//Here generate all the buttons for the main menu, pass them their appropriate on click functions and add them to the arraylist
			buttons = new ArrayList<>();
			MenuButton title = new MenuButton(540, 100, 400, 100, 1, "Empire Rush", Color.black, () -> System.out.println());
			MenuButton playButton = new MenuButton(540, 350, 400, 100, 2, "Play Game", Color.green, this::switchToGamePanel);
			MenuButton quitButton = new MenuButton(540, 550, 400, 100, 2, "Quit Game", Color.green, () -> System.exit(0));
			buttons.add(title);
			buttons.add(playButton);
			buttons.add(quitButton);
			
			//Here add the listener and ensure that the onclick functionality of each button gets passed the coordinates of the mouse (not confident in this)
			addMouseListener(new MouseAdapter() {
		            @Override
		            public void mouseClicked(MouseEvent e) {
		                for (MenuButton button : buttons) {
		                    button.click(e.getX(), e.getY());
		                }
		            }
		        });
			
			//Extra panel mumbo jumbo
			this.setFocusable(true);
			this.setPreferredSize(SCREEN_SIZE);
			
			//Start the thread related to the process
//			menuThread = new Thread(this);
//			menuThread.start();
		}
		
		public void paint(Graphics g) {
			//Paint function; creating an image helps prevent flickering and acts as a buffer
			image = createImage(getWidth(), getHeight());
			graphics = image.getGraphics();
			draw(graphics);
			g.drawImage(image, 0, 0, this);
		}
		
		public void draw(Graphics g) {
			//Here draw all the contents for every element that should be in the panel
	        for (MenuButton button : buttons) {
	            button.draw((Graphics2D) g);
	        }
		}
		
//		public void run() {
//			//Game loop. Do I need this here? The menu is static and nothing is moving
//			long lastTime = System.nanoTime();
//			double tickNumber = 60;
//			double ns = 1000000000 / tickNumber;
//			double delta = 0;
//			
////			long timer = System.currentTimeMillis(); // Track elapsed time in ms
////			int frames = 0; // How many times we repaint in one second
//			
//			while(true) {
//				long now = System.nanoTime();
//				delta += (now - lastTime)/ns;
////				System.out.println(delta);
//				lastTime = now;
//				if(delta >= 1) {
//					repaint();
////					frames++;
//					delta--;
//				}
//				
//				// Every 1 second, print FPS and reset frame count
////		        if (System.currentTimeMillis() - timer >= 1000) {
////		            System.out.println("FPS: " + frames);
////		            frames = 0;
////		            timer += 1000;
////		        }
//			}
//		}
		
		//This function implements the switch to the game panel on clicking play game
		public void switchToGamePanel() {
			GamePanel gamePanel = new GamePanel(frame);
			frame.setContentPane(gamePanel);
			frame.revalidate();
			frame.repaint();
		}
}

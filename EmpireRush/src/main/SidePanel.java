package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SidePanel extends JPanel{
	static final int PANEL_WIDTH = 560;
	static final int PANEL_HEIGHT = 720;
	static final Dimension PANEL_SIZE = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
	
	Image image;
	Graphics graphics;
	GameFrame frame;
	
	GamePanel gamePanel;
	
//	ArrayList<Button> buttons;
	
	SidePanel(GamePanel gamePanel){
		this.gamePanel = gamePanel;
		
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setFocusable(true);
		setPreferredSize(PANEL_SIZE);
        setOpaque(true); 
		setBackground(Color.CYAN); // Set the background color
		
		//Creating startButton
		JButton playButton = new JButton("Play");
		playButton.addActionListener(e -> gamePanel.togglePause());
		add(playButton);
		
		repaint();
		revalidate();
	}
	
	public void paint(Graphics g) {
        super.paint(g);
        draw(g);
	}
	
	public void draw(Graphics g) {
		// Set font and color for text
        g.setColor(Color.WHITE);  // Set text color
        g.setFont(new Font("Arial", Font.BOLD, 24));  // Set font (font name, style, size)
        
        System.out.println(gamePanel.map.health);
        String healthString = "Health: " + gamePanel.map.health;
        // Draw the text at the specified coordinates
        g.drawString(healthString, 250, 100);  // Draw text at (x, y)
	}
}

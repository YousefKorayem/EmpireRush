package main;
import java.awt.*;
import java.awt.event.*;

public class MenuButton extends Rectangle{
	
	int id;
	String text;
	Color color;
	Runnable action;
	
	MenuButton(int x, int y, int width, int height, int id, String text, Color c, Runnable action){
		super(x, y, width, height);
		this.id = id;
		this.text = text;
		this.color = c;
		this.action = action;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.PLAIN, 60));
		g.drawString(text, x, y+height);
	}
	
	public void click(int x, int y) {
		if(this.contains(x, y) && action != null) {
			action.run();
		}
	}
	
}

package main;
import java.awt.*;

public class MenuButton extends Rectangle{
	
	int id;
	String text;
	Color color;
	
	MenuButton(int x, int y, int width, int height, int id, String text, Color c){
		super(x, y, width, height);
		this.id = id;
		this.text = text;
		this.color = c;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.PLAIN, 60));
		g.drawString(text, x, y);
	}
}

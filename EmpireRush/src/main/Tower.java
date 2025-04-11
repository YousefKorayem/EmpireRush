package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tower extends Rectangle{
	int type;
	Camp camp;
	
	Tower(int type, Camp c){
		this.type = type;
		this.camp = c;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(camp.x, camp.y, 10,10);
	}
}

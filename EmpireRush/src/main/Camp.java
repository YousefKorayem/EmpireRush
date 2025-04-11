package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

//camp is a rectangle
public class Camp extends Rectangle{
	
	Tower tower;
	
	Camp(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
	}
	
	public void buildTower(int id) {
		Tower t = new Tower(id, this);
		this.tower = t;
	}
}

package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public abstract class Tower extends Rectangle{
	Point position;
	Color color;
	int cooldown;
	long lastShotTime = 0;
	int shotDamage;
	Boolean drawAttack = false;
	Ellipse2D attackRange;
	
	int width;
	int height;
	
	Tower(Point position, double range, int cooldown, int shotDamage, int width, int height){
		this.position = position;
		attackRange = new Ellipse2D.Double(position.getX() - range/2, position.getY() - range/2, range, range);
		this.cooldown = cooldown;
		this.shotDamage = shotDamage;
		this.width = width;
		this.height = height;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int) (position.getX() - width/2), (int) (position.getY() - height/2), width, height);
		if(drawAttack) {
			drawAttack(g);
		}
	}
	

	public void attack(ArrayList<Enemy> enemies, long now) {
		// TODO Auto-generated method stub

	}
	
	public void drawAttack(Graphics g) {
		
	}
	
	public void drawRange(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		// Set transparency (alpha value between 0.0f and 1.0f)
        float alpha = 0.1f; // 50% transparent
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.setColor(Color.BLUE);
        g2d.fillOval((int) attackRange.getX(), (int) attackRange.getY(), (int) attackRange.getWidth(), (int) attackRange.getHeight());
        
        g2d.dispose();
	}
}

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
	double range;
	float rangeTransparency = 0.08f;
	Ellipse2D attackRange;
	
	int price;
	boolean active;
	
	Tower(Point position, double range, int cooldown, int shotDamage, int width, int height){
		positionTower(position);
		this.cooldown = cooldown;
		this.shotDamage = shotDamage;
		this.width = width;
		this.height = height;
		this.range = range;
	}
	
	public void setRange() {
		attackRange = new Ellipse2D.Double(position.getX() - range/2, position.getY() - range/2, range, range);
	}
	
	public void draw(Graphics g) { 
		g.setColor(color);
		g.fillOval(x, y, width, height);
		if(drawAttack) {
			drawAttack(g);
		}
	}
	

	public void attack(ArrayList<Enemy> enemies, long now) {
		// TODO Auto-generated method stub

	}
	
	public void drawAttack(Graphics g) {
		
	}
	
	public void positionTower(Point p) {
		position = p;
		x = (int) (position.getX() - width/2);
		y = (int) (position.getY() - height/2);
	}
	
	public void placeTower(Point p) {
		positionTower(p);
		active = true;
		setRange();
	}
	
	public void drawRange(Graphics g) {
		if(attackRange == null) return;
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, rangeTransparency));
        g2d.setColor(color);
        g2d.fillOval((int) attackRange.getX(), (int) attackRange.getY(), (int) attackRange.getWidth(), (int) attackRange.getHeight());
        
        g2d.dispose();
	}
	
	public void simulateRange(Graphics g) {
		Ellipse2D attackRange = new Ellipse2D.Double(position.getX() - range/2, position.getY() - range/2, range, range);
		Graphics2D g2d = (Graphics2D) g.create();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, rangeTransparency));
        g2d.setColor(color);
        g2d.fillOval((int) attackRange.getX(), (int) attackRange.getY(), (int) attackRange.getWidth(), (int) attackRange.getHeight());
        
        g2d.dispose();
	}
}

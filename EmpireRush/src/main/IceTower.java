package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class IceTower extends Tower{
	
	Point attackTarget;

	IceTower() {
		super(new Point(-100,-100), 250, 10000, 1, 30, 30);
		color = Color.blue;
		price = 75;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void attack(ArrayList<Enemy> enemies, long now){
		if(!active) return;
		if((now - lastShotTime)/100000 <= cooldown) return;
		for(Enemy e : enemies) {
			if(attackRange.intersects(e)) {
				e.takeDamage(shotDamage);
				drawAttack = true;
				attackTarget = new Point((int) e.getCenterX(), (int) e.getCenterY());
				lastShotTime = now;
				break;
			}
		}
	}
	
	@Override
	public void drawAttack(Graphics g) {
		g.drawLine((int) position.getX(), (int) position.getY(), attackTarget.x, attackTarget.y);
		drawAttack = false;
	}
}

package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class LaserTower extends Tower{
	
	Point attackTarget;

	LaserTower(Point position) {
		super(position, 250, 20000, 3, 30, 30);
		color = Color.red;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void attack(ArrayList<Enemy> enemies, long now){
//		System.out.println((now - lastShotTime)/100000)
//		System.out.println("should i fire?" + (now - lastShotTime)/100000 + "; cd: " + cooldown);
		if((now - lastShotTime)/100000 <= cooldown) return;
		for(Enemy e : enemies) {
			if(attackRange.intersects(e)) {
//				System.out.println("fired shot: " + (now - lastShotTime)/100000);
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

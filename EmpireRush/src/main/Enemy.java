package main;
import java.awt.*;
import java.awt.event.*;

public class Enemy extends Rectangle{
	boolean active;
	
	int health;
	int money;
	int damage;
	int speed;
	
	Dimension size;
	Point destination;
	Point direction;
	Rectangle nextCheckpoint;
	int pathStep = 1;
	
	GameLevel map;
	
	
	Enemy(int health, int damage, int speed, GameLevel map, Dimension size){
		//Create a hitbox object centered at the center of the spawnpoint
		super((int) map.spawn.getCenterX() - size.width/2, (int) map.spawn.getCenterY() - size.height/2, size.width, size.height);
		this.size = size;
		
		this.active = true;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		
		this.map = map;
		
		//are there any places to go?
		if(pathStep >= map.checkpoints.size()) {
//			System.out.println("Nowhere to go. Deactivating.");
			if(map.enemyObjective.contains(this)) {
//				System.out.println("I've made it to the objective!");
				map.takeDamage(health);
			}
			active = false;
			return;
			//flag for deletion next cycle and return
		}

		//get the next checkpoint
		nextCheckpoint = map.checkpoints.get(pathStep);
		//set my new destination
		destination = new Point((int) nextCheckpoint.getCenterX(), (int) nextCheckpoint.getCenterY());
		
//		System.out.print("Found a destination: ");
//		System.out.println(destination.x + ", " + destination.y);
	}
	
	public void move() {
		//Have I arrived at my destination
		if(nextCheckpoint.contains(this)) {
			//I've arrived; get me a new destination
//			System.out.println("Arrived. Is there a new destination?");
			pathStep++;
			//are there any more places to go?
			if(pathStep >= map.checkpoints.size()) {
//				System.out.println("Nowhere to go. Deactivating.");
				if(map.enemyObjective.contains(this)) {
//					System.out.println("I've made it to the objective!");
					map.takeDamage(health);
				}
				active = false;
				return;
				//flag for deletion next cycle and return
			}
			//get the next checkpoint
			nextCheckpoint = map.checkpoints.get(pathStep);
			//set my new destination
			destination = new Point(nextCheckpoint.x + nextCheckpoint.width/2, nextCheckpoint.y + nextCheckpoint.height/2);
			
//			System.out.print("Found a new destination: ");
//			System.out.println(destination.x + ", " + destination.y);
		}
		
		
		//Am I within the zone's horizontal?
		if(x >= nextCheckpoint.x && x + width <= nextCheckpoint.x + nextCheckpoint.width) {
//			System.out.println("I'm within the destination horizontally. Travelling vertically");	
			
			if(y < nextCheckpoint.y && y+size.height < nextCheckpoint.y) {
//				System.out.println("Completely above, moving down");
				direction = new Point(0, 1);
			}
			else if(y < nextCheckpoint.y && y+size.height >= nextCheckpoint.y && y+size.height < nextCheckpoint.y + nextCheckpoint.height){
//				System.out.println("On the border, moving down");
				direction = new Point(0, 1);
			}
			else if(y >= nextCheckpoint.y && y <= nextCheckpoint.y + nextCheckpoint.height && y+size.height > nextCheckpoint.y) {
//				System.out.println("On the border, moving up");
				direction = new Point(0, -1);
			}
			else if(y > nextCheckpoint.y + nextCheckpoint.height && y+size.height > nextCheckpoint.y + nextCheckpoint.height ) {
//				System.out.println("Completely below, moving up");
				direction = new Point(0, -1);
			}
		}
		else {
//			System.out.println("I'm not within the destination horizontally. Travelling horizontally.");
			
			if(x < nextCheckpoint.x && x+size.width < nextCheckpoint.x) {
//				System.out.println("Completely left, moving right");
				direction = new Point(1, 0);
			}
			else if(x < nextCheckpoint.x && x+size.width >= nextCheckpoint.x && x+size.width < nextCheckpoint.x + nextCheckpoint.width){
//				System.out.println("On the border, moving right");
				direction = new Point(1, 0);
			}
			else if(x >= nextCheckpoint.x && x <= nextCheckpoint.x + nextCheckpoint.width && x+size.width > nextCheckpoint.x) {
//				System.out.println("On the border, moving left");
				direction = new Point(-1, 0);
			}
			else if(x > nextCheckpoint.x + nextCheckpoint.width && x+size.width > nextCheckpoint.x + nextCheckpoint.width ) {
//				System.out.println("Completely right, moving left");
				direction = new Point(-1, 0);
			}
		}
		
		Point velocity = new Point(direction.x * speed, direction.y * speed);
		
		//Get my new position
		x += velocity.x;
		y += velocity.y;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, size.width,size.height);
	}
}

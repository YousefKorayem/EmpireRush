package main;
import java.awt.*;
import java.awt.event.*;

public class Enemy extends Rectangle{
	boolean active;
	
	int health;
	int damage;
	int speed;
	
	Point position;
	Point destination;
	Point direction;
	Rectangle nextCheckpoint;
	int pathStep = 0;
	
	GameLevel map;
	
	
	Enemy(int health, int damage, int speed, GameLevel map){
		super(map.spawnPoint.x, map.spawnPoint.y, 10, 10);
		this.active = true;
		this.health = health;
		this.damage = damage;
		this.speed = speed;
		
		this.map = map;
		this.position = map.spawnPoint;
		
		//are there any places to go?
		if(pathStep >= map.checkpoints.size()) {
			active = false;
			return;
			//flag for deletion next cycle and return
		}
		//get the next checkpoint
		nextCheckpoint = map.checkpoints.get(pathStep);
		//set my new destination
		destination = new Point(nextCheckpoint.x + nextCheckpoint.width/2, nextCheckpoint.y + nextCheckpoint.height/2);
		System.out.println("new destination at " + destination.x + ", " + destination.y);
	}
	
	public void move() {
		//Have I arrived at my destination
		if(nextCheckpoint.contains(position)) {
			System.out.println("arrived at destination");
			//I've arrived; get me a new destination
			pathStep++;
			//are there any more places to go?
			if(pathStep >= map.checkpoints.size()) {
				System.out.println("no more places to go");
				active = false;
				return;
				//flag for deletion next cycle and return
			}
			//get the next checkpoint
			nextCheckpoint = map.checkpoints.get(pathStep);
			//set my new destination
			destination = new Point(nextCheckpoint.x + nextCheckpoint.width/2, nextCheckpoint.y + nextCheckpoint.height/2);
			System.out.println("new destination at " + destination.x + ", " + destination.y);
		}
		
		//Am I within the zone's horizontal?
		if(position.x <= nextCheckpoint.x + nextCheckpoint.width && position.x >= nextCheckpoint.x) {
			//Move vertically to the zone
			if(position.y < nextCheckpoint.y) direction = new Point(0, 1);
			else direction = new Point(0, -1);
		}
		else {
			//Move horizontally to the zone
			if(position.x < nextCheckpoint.x) direction = new Point(1, 0);
			else direction = new Point(-1, 0);
		}
		
		Point velocity = new Point(direction.x * speed, direction.y * speed);
		
		//Get my new position
		position = new Point(position.x + velocity.x, position.y + velocity.y);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillOval(position.x, position.y, 10,10);
	}
}

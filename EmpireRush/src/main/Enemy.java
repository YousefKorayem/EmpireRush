package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Enemy extends Rectangle{
	boolean active;
	
	int health;
	int maxHealth;
	int money;
	int damage;
	int speed;
	int delay;
	Color color = Color.green;
	
	Dimension size;
	Point destination;
	Point direction;
	Point velocity;
	Checkpoint nextCheckpoint;
	
	int pathStep = 1;
	
	public static final Point up = new Point(0, -1);
	public static final Point down = new Point(0, 1);
	public static final Point left = new Point(-1, 0);
	public static final Point right = new Point(1, 0);
	
	public static double slideBias = 0.9;
	public static double slideShift = -0.005;
	public static int goldMultiplier = 10;
	
	GameController game;
	
	Enemy(int health, int damage, int speed, GameController game, Dimension size, int delay){
		//Create a hitbox object centered at the center of the spawnpoint
		super((int) game.getSpawn().getCenterX() - size.width/2 + game.random.nextInt(-((game.getSpawn().width-size.width)/2), ((game.getSpawn().width-size.width)/2)),
				(int) game.getSpawn().getCenterY() - size.height/2 + game.random.nextInt(-((game.getSpawn().height-size.width)/2), ((game.getSpawn().height-size.width)/2)),
				size.width,
				size.height);
		
		this.size = size;
		this.active = false;
		this.health = health;
		this.maxHealth = health;
		this.damage = damage;
		this.speed = speed;
		this.delay = delay;
		this.game = game;
		
		//Are there any places to go?
		if(destinationCheck()) return;

		//get the next checkpoint
		nextCheckpoint = game.getCheckpoints().get(pathStep);
		//set my new destination
		destination = new Point((int) nextCheckpoint.getCenterX(), (int) nextCheckpoint.getCenterY());
	}
	
	
	
	public void move() {
		//Have I arrived at my destination
		if(nextCheckpoint.contains(this)) {
			//Flip coin; if true, slide
			if(game.random.nextDouble() < slideBias) {
				//Get my new position
				x += velocity.x;
				y += velocity.y;
				slideBias += slideShift;
				return;
			}
			//I've arrived; get me a new destination
			pathStep++;
			//Are there any more places to go?
			if(destinationCheck()) return;
			//get the next checkpoint
			nextCheckpoint = game.getCheckpoints().get(pathStep);
			slideBias = 0.9;
			//set my new destination
			destination = new Point(nextCheckpoint.x + nextCheckpoint.width/2, nextCheckpoint.y + nextCheckpoint.height/2);
		}
		
		
		//Am I within the zone's horizontal?
		if(x >= nextCheckpoint.x && x + width <= nextCheckpoint.x + nextCheckpoint.width) {		
			if(y < nextCheckpoint.y && y+size.height < nextCheckpoint.y) {
				direction = down;
			}
			else if(y < nextCheckpoint.y && y+size.height >= nextCheckpoint.y && y+size.height < nextCheckpoint.y + nextCheckpoint.height){
				direction = down;
			}
			else if(y >= nextCheckpoint.y && y <= nextCheckpoint.y + nextCheckpoint.height && y+size.height > nextCheckpoint.y) {
				direction = up;
			}
			else if(y > nextCheckpoint.y + nextCheckpoint.height && y+size.height > nextCheckpoint.y + nextCheckpoint.height ) {
				direction = up;
			}
			velocity = new Point(direction.x * speed, direction.y * speed);
		}
		else {		
			if(x < nextCheckpoint.x && x+size.width < nextCheckpoint.x) {
				direction = right;
			}
			else if(x < nextCheckpoint.x && x+size.width >= nextCheckpoint.x && x+size.width < nextCheckpoint.x + nextCheckpoint.width){
				direction = right;
			}
			else if(x >= nextCheckpoint.x && x <= nextCheckpoint.x + nextCheckpoint.width && x+size.width > nextCheckpoint.x) {
				direction = left;
			}
			else if(x > nextCheckpoint.x + nextCheckpoint.width && x+size.width > nextCheckpoint.x + nextCheckpoint.width ) {
				direction = left;
			}
			velocity = new Point(direction.x * speed, direction.y * speed);
		}

		
		//Get my new position
		x += velocity.x;
		y += velocity.y;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, size.width,size.height);
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		if(health <= 0) {
			active = false;
			game.setScore(game.getScore() + maxHealth);
			game.setGold(game.getGold() + maxHealth * goldMultiplier);
		}
	}
	
	public boolean destinationCheck() {
		if(pathStep >= game.getCheckpoints().size()) {
			if(game.getObjective().contains(this)) {
				game.takeDamage(health);
			}
			//flag for deletion next cycle and return
			active = false;
			return true;
		}
		return false;
	}
}

package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GameLevel extends Rectangle{
	
	int maxHealth;
	int health;
	int score;
	int gold;
	Dimension mapSize;
	Dimension cellSize;
	
	Checkpoint spawn;
	Checkpoint enemyObjective;

	ArrayList<Tower> towers;
	ArrayList<Checkpoint> checkpoints;
	Queue<Enemy> enemyQueue;
	ArrayList<Enemy> enemies;
	
	GamePanel gamePanel;
	Random random;
	
	public static final int laserTowerCost = 100;
	
	GameLevel(int health, int gold, String mapPath, String scriptPath, Dimension mapSize, Dimension cellSize, GamePanel gamePanel){
		//hardcoded gamefield dimensions are here, consider changing
		super(0,0, mapSize.width, mapSize.height);
		this.mapSize = mapSize;
		this.cellSize = cellSize;
		this.gamePanel = gamePanel;
		
		
		enemies = new ArrayList<Enemy>();
		enemyQueue = new LinkedList<Enemy>();
		checkpoints = new ArrayList<Checkpoint>();
		towers = new ArrayList<Tower>();
		random = new Random();
		
		
		//Parse level data, add camps and checkpoints in appropriate data structures:
		parseLevelData(mapPath, scriptPath);
				
		this.health = health;
		this.maxHealth = health;
		this.gold = gold;
	}
	
	public void parseLevelData(String mapPath, String scriptPath) {

		//parse level data csv
				ArrayList<String[]> levelData = new ArrayList<>();
				try (BufferedReader br = new BufferedReader(new FileReader(mapPath))) {
			        String line;
			        while ((line = br.readLine()) != null) {
			            String[] row = line.split(",");
			            levelData.add(row);
			        }
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
				
		//create camps and checkpoints, place them in data structures and sort checkpoints
//		camps = new ArrayList<Camp>();
		checkpoints = new ArrayList<Checkpoint>();
		for(int i = 0; i < levelData.size(); i++) {
			String[] row = levelData.get(i);
			if(i == 0) {
				String line = row[0];
				if (line != null && line.startsWith("\uFEFF")) {
				    row[0] = line.substring(1); // remove the BOM
				}
			}
			for(int j = 0; j < row.length; j++) {
				String item = row[j];
				if(item.length() == 0) continue;
				switch(item.charAt(0)) {
//				case 'C':
//					camps.add(new Camp(j*cellSize.width, i*cellSize.height, cellSize.width, cellSize.height));
//				break;
				case 'X':
					checkpoints.add(new Checkpoint(j*cellSize.width, i*cellSize.height, cellSize.width, cellSize.height, Integer.parseInt(item.substring(1))));
					break;
				}
			}
		}
		
		Collections.sort(checkpoints);
		this.spawn = checkpoints.get(0);
		this.enemyObjective = checkpoints.get(checkpoints.size()-1);
		
		//parse script data .txt
		ArrayList<String[]> scriptData = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(scriptPath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] row = line.split(",");
	            scriptData.add(row);
	            queueEnemies(row);
	            
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void queueEnemies(String[] row) {
		int numberOfEnemies = Integer.parseInt(row[3]);
		int health = Integer.parseInt(row[0]);
		int damage = Integer.parseInt(row[1]);
		int speed = Integer.parseInt(row[2]);
		int delay = Integer.parseInt(row[4]);
		Dimension size = new Dimension(Integer.parseInt(row[5]), Integer.parseInt(row[6]));
		for(int i = 0; i < numberOfEnemies; i++) {
			Enemy e = new Enemy(health, damage,speed, this, size,delay);
			enemyQueue.add(e);
		}
	}
	
	public void draw(Graphics g) {
		for(Checkpoint cp : checkpoints) {
			cp.draw(g);
		}
		for(Tower t : towers) {
			t.draw(g);
			t.drawRange(g);
			//Draw range here
		}
//		for(Camp c : camps) {
//			c.draw(g);
//			if(c.tower != null) {
//				c.tower.draw(g);
//				c.tower.drawRange(g);
//			}
//		}
		for(Enemy e : enemies) {
			e.draw(g);
		}
	}

	public void move(long now) {
		//here lies logic for the movement of the enemy
		Iterator<Enemy> iterator = enemies.iterator();
		while(iterator.hasNext()) {
			Enemy enemy = iterator.next();
			if(enemy.active == false) {
				iterator.remove();
				return;
//				System.out.println("enemy inactive");
			}
			enemy.move();
			if(!this.contains(enemy)) {
				iterator.remove();
				return;
//				System.out.println("enemy out of bounds");
			}
			if(enemy.active == false) {
				iterator.remove();
				return;
//				System.out.println("enemy inactive");
			}
		}
		
		//here lies logic for tower attacking
		Iterator<Tower> iterator2 = towers.iterator();
		System.out.println(towers.size());
		while(iterator2.hasNext()){
			Tower tower = iterator2.next();
			tower.attack(enemies, now);
		}
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		gamePanel.sidePanel.repaint();
//		System.out.println("Current health:" + health);
		if(health <= 0) {
			gamePanel.gameOver(0);
		}
	}
	
	public void transaction(int money) {
		gold += money;
		gamePanel.sidePanel.repaint();
	}
}

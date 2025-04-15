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

public class GameLevel extends Rectangle{

	int health;
	Dimension mapSize;
	Dimension cellSize;
	
	Checkpoint spawn;
	Checkpoint enemyObjective;

	ArrayList<Camp> camps;
	ArrayList<Checkpoint> checkpoints;
	ArrayList<Enemy> enemies;
	
	GameLevel(int health, String path, Dimension mapSize, Dimension cellSize){
		//hardcoded gamefield dimensions are here, consider changing
		super(0,0, mapSize.width, mapSize.height);
		this.mapSize = mapSize;
		this.cellSize = cellSize;
		
		
		//parse level data csv
		ArrayList<String[]> levelData = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] row = line.split(",");
	            levelData.add(row);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		//create camps and checkpoints, place them in data structures and sort checkpoints
		camps = new ArrayList<Camp>();
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
//				System.out.println("Full string: [" + item + "]");
//				System.out.println("charAt(0): [" + item.charAt(0) + "]");
//				System.out.println("charAt(0) code: " + (int) item.charAt(0));
//				if(item.equals("")) System.out.print(" ");
//				System.out.print(item);
				if(item.length() == 0) continue;
				switch(item.charAt(0)) {
				case 'C':
					camps.add(new Camp(j*cellSize.width, i*cellSize.height, cellSize.width, cellSize.height));
				break;
				case 'X':
					checkpoints.add(new Checkpoint(j*cellSize.width, i*cellSize.height, cellSize.width, cellSize.height, Integer.parseInt(item.substring(1))));
					break;
				}
			}
//			System.out.println("end line");
		}
		
		Collections.sort(checkpoints);
		
		this.health = health;
		//set the first checkpoint as the spawn, and the last as the 
		this.spawn = checkpoints.get(0);
		this.enemyObjective = checkpoints.get(checkpoints.size()-1);	

		camps.get(0).buildTower(0);
		
		//generate a test enemy and add it to the list of enemies
		Enemy e = new Enemy(1,1,5, this, new Dimension(15,15));
		enemies = new ArrayList<>();
		enemies.add(e);
		
	}
	
	public void draw(Graphics g) {

		for(Checkpoint cp : checkpoints) {
			cp.draw(g);
		}
		for(Camp c : camps) {
			c.draw(g);
			if(c.tower != null) c.tower.draw(g);
		}
		for(Enemy e : enemies) {
			e.draw(g);
		}
	}

	public void move() {
		//here lies logic for the movement of the enemy
		Iterator<Enemy> iterator = enemies.iterator();
		while(iterator.hasNext()) {
			Enemy enemy = iterator.next();
			if(enemy.active == false) {
				iterator.remove();
				System.out.println("enemy inactive");
			}
			enemy.move();
			if(!this.contains(enemy)) {
				iterator.remove();
				System.out.println("enemy out of bounds");
			}
			if(enemy.active == false) {
				iterator.remove();
				System.out.println("enemy inactive");
			}
		}
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		System.out.println("Current health:" + health);
	}
}

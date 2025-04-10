package main;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class GameLevel extends Rectangle{

	int health;
	
	Point spawnPoint;
	Point destination;

	ArrayList<Camp> camps;
	ArrayList<Checkpoint> checkpoints;
	
	GameLevel(int health, String path){
		//hardcoded gamefield dimensions are here, consider changing
		super(0,0, 800, 800);
		
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
			for(int j = 0; j < row.length; j++) {
				String item = row[j];
				System.out.println(item);
				if(item.length() == 0) continue;
				switch(item.charAt(0)) {
				case 'C':
					camps.add(new Camp(j*100, i*100, 100, 100));
				break;
				case 'X':
					checkpoints.add(new Checkpoint(j*100, i*100, 100, 100, item.charAt(1)));
					break;
				}
			}
		}
		
		Collections.sort(checkpoints);
		
		this.health = health;
		//set the first checkpoint as the spawn, and the last as the destination
		this.spawnPoint = checkpoints.get(0).getLocation();
		this.destination = checkpoints.get(checkpoints.size()-1).getLocation();
		
		System.out.println("Finished parsing game level");
		System.out.println("Player health: " + this.health);
		System.out.println("Checkpoints: ");
		for(Checkpoint cp : checkpoints) {
			System.out.println(cp.id + ": " + cp.x + ", " + cp.y);
		}
	}
}

package main;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class GameLevel extends Rectangle{

	Point spawnPoint;
	ArrayList<Rectangle> enemyPath;
	
	GameLevel(Point spawnPoint){
		super(0,0, 720, 720);
		this.spawnPoint = spawnPoint;
		
		enemyPath = new ArrayList<>();
		Point destination1 = new Point(spawnPoint.x + 500, spawnPoint.y);
		Point destination2 = new Point(destination1.x, destination1.y + 200);
		Point destination3 = new Point(destination2.x - 500, destination2.y);
		enemyPath.add(new Rectangle(destination1.x - 10, destination1.y - 10, 20, 20));
		enemyPath.add(new Rectangle(destination2.x - 10, destination2.y - 10, 20, 20));
		enemyPath.add(new Rectangle(destination3.x - 10, destination3.y - 10, 20, 20));
	}
}

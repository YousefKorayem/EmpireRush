package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


//checkpoint is a rectangle with an id
public class Checkpoint extends Rectangle implements Comparable<Checkpoint>{
	int id;
	
	Checkpoint(int x, int y, int width, int height, int id){
		super(x, y, width, height);
		this.id = id;
	}

	public int compareTo(Checkpoint o) {
        return Integer.compare(this.id, o.id);
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}
}

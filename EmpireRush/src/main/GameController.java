package main;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GameController implements Runnable{
	
	private WindowController window;
	
	//With a map size of 720x720, a cellSize of 48x48 gives a map with 15x15 cellsd
	private static Dimension cellSize = new Dimension(48, 48);
	public Random random;
	private Thread gameThread;
	private volatile boolean running;
	double tickNumber = 60;
	
	//GameStates:
	//-1: No game running
	// 0: Game paused
	// 1: Game running 
	// 2: Game over
	private volatile int previousGameState;
	private volatile int gameState;
	private int currentLevel;
	
	private ArrayList<Checkpoint> checkpoints;
	private Checkpoint spawn;
	private Checkpoint objective;
	
	private ArrayList<Enemy> enemies;
	private Queue<Enemy> enemyQueue;
	private long lastSpawnTime;
	
	private ArrayList<Tower> towers;
	
	//Player data
	private int health;
	private int maxHealth;
	private int gold;
	private int score;
	
	//Mouse data
	private Tower heldTower;
	private Tower selectedTower;
	
	public GameController(WindowController window) {
		this.window = window;
		gameState = -1;
		previousGameState = -1;
		
		enemies = new ArrayList<Enemy>();
		enemyQueue = new LinkedList<Enemy>();
		checkpoints = new ArrayList<Checkpoint>();
		towers = new ArrayList<Tower>();
		random = new Random();
	}
	
	public void newGame() {
		if(currentLevel == 0) return;
		//1. Generate the gamePanel and sidePanel
		window.setGamePanel(new GamePanel(this, currentLevel));
		window.setSidePanel(new SidePanel(this, window));
		
		//2. Parse level data and construct the map 
		//	 by populating the enemyQueue, checkpoints, and towers lists
		String mapPath = "/data/level" + currentLevel + "_map.csv";
		String scriptPath = "/data/level" + currentLevel + "_script.txt";
		parseLevelData(mapPath, scriptPath);
		
		//3. Make sure the game is paused, and change the game level
		gameState = 0;
		
		//4. Go to the game view and start the thread
		window.toGameView();
		gameThread = new Thread(this);
		running = true;
		gameThread.start();
	}

	public void parseLevelData(String mapPath, String scriptPath) {
		//Parse the data for the map
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(mapPath)))) {
	        String line;
	        boolean firstLine = true;
	        int i = 0;
	        while ((line = br.readLine()) != null) {
	            String[] row = line.split(",");
	            if(firstLine) {
					String item = row[0];
					if (item.startsWith("\uFEFF")) {
					    row[0] = item.substring(1); // remove the BOM
					}
					firstLine = false;
					continue;
				}
				for(int j = 0; j < row.length; j++) {
					String item = row[j];
					if(item.length() == 0) continue;
					if(item.charAt(0) == 'X') {
						checkpoints.add(new Checkpoint(j*cellSize.width, i*cellSize.height, cellSize.width, cellSize.height, Integer.parseInt(item.substring(1))));
					}
				}
				i++;
	        }
	    } catch (IOException | NullPointerException e) {
	        e.printStackTrace();
	    }
		
		Collections.sort(checkpoints);
		this.setSpawn(checkpoints.get(0));
		this.setObjective(checkpoints.get(checkpoints.size()-1));
		
		//parse script data .txt
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(scriptPath)))) {
	        String line;
	        boolean firstLine = true;
	        while ((line = br.readLine()) != null) {
	            String[] row = line.split(",");
				if(firstLine) {
					health = Integer.parseInt(row[0]);
					setGold(Integer.parseInt(row[1]));
					setMaxHealth(health);
					firstLine = false;
				}
				else queueEnemies(row);
	            
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void processCheckpoints(String[] row) {
		
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
	
	public void run() {
		System.out.println("run() started on: " + Thread.currentThread().getName());
		
		//Game Loop
		long lastTime = System.nanoTime();
		double delta = 0;
		
		while(running) {
			double ns = 1000000000 / tickNumber;
			long now = System.nanoTime();
			
			if(gameState == 1) {
				// Detect unpause
		        if (previousGameState != 1) {
		            lastTime = now;  // Reset lastTime when coming back into game state
		            previousGameState = 1;
		            continue;  // Skip one loop iteration to avoid jump
		        }
				
				delta += (now - lastTime)/ns;
				lastTime = now;
				
				if(delta >= 1) {
					processSpawning(now);
					processEnemyMovement(now);
					processTowers(now);
					processEnemyDeletion(now);
					window.repaintAllActivePanels();
					delta--;
				}
			}
			
			else {
				 previousGameState = gameState; // keep track of when game was paused
		        try {
		            Thread.sleep(5); // don't burn CPU
		        } catch (InterruptedException e) {
		            Thread.currentThread().interrupt();
		        }
			}
			
			if(gameState == 2) {
				break;
			}
		}
	}
	
	public void processSpawning(long now) {
		
		if(!enemyQueue.isEmpty()) {
			//There's an enemy to spawn. Think about spawning now
			if((now - lastSpawnTime)/1000000 > enemyQueue.peek().delay) {
				Enemy e = enemyQueue.remove();
				e.active = true;
				enemies.add(e);
				lastSpawnTime = now;
			}
		}
	}
	
	public void processEnemyMovement(long now) {
		Iterator<Enemy> iterator = enemies.iterator();
		while(iterator.hasNext()) {
			iterator.next().move();
		}
	}
	
	public void processTowers(long now) {
		Iterator<Tower> iterator = towers.iterator();
		while(iterator.hasNext()) {
			iterator.next().attack(enemies, now);
		}
	}
	
	public void processEnemyDeletion(long now) {
		Iterator<Enemy> iterator = enemies.iterator();
		while(iterator.hasNext()) {
			Enemy e = iterator.next();
			if(!e.active) iterator.remove();
		}
		if(enemies.isEmpty() && enemyQueue.isEmpty()) processGameOver();
	}
	
	public void processGameOver() {
		GameOverPanel gameOverPanel = new GameOverPanel(this, window);
		window.setGameOverPanel(gameOverPanel);
		window.toGameOverView();
	}

	public void togglePause() {
		if(gameState == 0) {
			window.getSidePanel().pauseButton.setText("Pause");
			gameState = 1;
			previousGameState = 0;
			return;
		}
		if(gameState == 1) {
			window.getSidePanel().pauseButton.setText("Play");
			gameState = 0;
			previousGameState = 1;
			return;
		}
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		if(health <= 0) {
			gameState = 2;
			previousGameState = 1;
		}
	}
	
	public void transaction(int gold) {
		this.gold += gold;
		window.repaintAllActivePanels();
	}
	
	public void buyTower(TowerType towerType) {
		if(heldTower == null) {
			if(towerType.price > gold) {
				return;
			}
			else {
				transaction(-towerType.price);
				heldTower = towerType.createInstance();
				towers.add(heldTower);
				selectedTower = heldTower;
				return;
			}
		}
		else {
			transaction(heldTower.price);
			towers.remove(heldTower);
			heldTower = null;
			buyTower(towerType);
		}
	}
	
	public void toggleFF() {
		if(tickNumber == 60) {
			tickNumber = 120;
		}
		else {
			tickNumber = 60;
		}
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.previousGameState = this.gameState;
		this.gameState = gameState;
	}
	
	public ArrayList<Checkpoint> getCheckpoints(){
		return checkpoints;
	}
	
	public void setCheckpoints(ArrayList<Checkpoint> checkpoints) {
		this.checkpoints = checkpoints;
	}
	
	public ArrayList<Tower> getTowers(){
		return towers;
	}
	
	public void setTowers(ArrayList<Tower> towers) {
		this.towers = towers;
	}
	
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	
	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public Checkpoint getSpawn() {
		return spawn;
	}

	public void setSpawn(Checkpoint spawn) {
		this.spawn = spawn;
	}

	public Checkpoint getObjective() {
		return objective;
	}

	public void setObjective(Checkpoint objective) {
		this.objective = objective;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public boolean getRunning() {
		return running;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}

	public Tower getHeldTower() {
		return heldTower;
	}

	public void setHeldTower(Tower heldTower) {
		this.heldTower = heldTower;
	}

	public int getPreviousGameState() {
		return previousGameState;
	}

	public Tower getSelectedTower() {
		return selectedTower;
	}

	public void setSelectedTower(Tower selectedTower) {
		this.selectedTower = selectedTower;
	}
}

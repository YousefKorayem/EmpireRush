package main;
import java.lang.reflect.InvocationTargetException;

public enum TowerType {
//	CANNON("Cannon Tower", 100, CannonTower.class),
    ICE("Ice Tower", 75, IceTower.class),
	LASER("Laser Tower", 150, LaserTower.class);
	
	public final String displayName;
	public final int price;
	public final Class<? extends Tower> towerClass;
	
	TowerType(String displayName, int price, Class<? extends Tower> towerClass){
		this.displayName = displayName;
		this.price = price;
		this.towerClass = towerClass;
	}
	
	public static TowerType fromClass(Class<? extends Tower> cls) {
		for(TowerType t: values()) {
			if(t.towerClass.equals(cls)) return t;
		}
		return null;
	}
	
	public Tower createInstance() {
        try {
            return towerClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}

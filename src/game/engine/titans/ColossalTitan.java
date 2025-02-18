package game.engine.titans;


public class ColossalTitan extends Titan {
public static final int TITAN_CODE=4;

	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,
			int speed, int resourcesValue, int dangerLevel) {
		
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);	
	}
	
	public boolean move() {
   	 int speed = getSpeed();
   	 
   	 int newDistance= getDistance()-getSpeed();
   	 setDistance(newDistance);
   	 setSpeed(++speed);
   	 
    if(hasReachedTarget()) {
    	return true;
    }
    else {
    	return false;
    }
}
}

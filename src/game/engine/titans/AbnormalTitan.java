package game.engine.titans;

import game.engine.interfaces.Attackee;

public class AbnormalTitan extends Titan {
	public static final int TITAN_CODE=2;
	
	public AbnormalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase,
			int speed, int resourcesValue, int dangerLevel) {
		
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);		
	}
	
	public int attack(Attackee target) {
	int resourcesGained=0;
	
	resourcesGained+=target.takeDamage(getDamage());
	
	if(!target.isDefeated()) {
		resourcesGained+=target.takeDamage(getDamage());
	}
return resourcesGained;
}
}
package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class WallTrap extends Weapon {
	public static final int WEAPON_CODE=4;
	
	public WallTrap(int baseDamage) {
		super(baseDamage);
	}
		
		
		public int turnAttack(PriorityQueue<Titan> laneTitans) {
			int resourcesGained=0;
			
			
			if(!laneTitans.isEmpty()) {
                Titan titan=laneTitans.poll();
                
                if(!titan.isDefeated())
                	laneTitans.add(titan);
                
                if(titan.getDistance()<=0)
				this.attack(titan);
				
				if(titan.isDefeated()) 
					resourcesGained+=titan.getResourcesValue();
				
			}
			return resourcesGained;
	}

}

package game.engine.weapons;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon {
    public static final int WEAPON_CODE=3; 
	private final int minRange;
	private final int maxRange;
	
	public VolleySpreadCannon(int baseDamage, int minRange, int maxRange) {
		super(baseDamage);
		this.minRange=minRange;
		this.maxRange=maxRange;
	}

	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		
		int resourcesGained=0;
		ArrayList<Titan>Titans= new ArrayList<Titan>();
		
		while(!laneTitans.isEmpty()) {
			Titan titan= laneTitans.poll();
			
			
				if(titan.getDistance() >= minRange && titan.getDistance() <= maxRange) 
					this.attack(titan);
				
					if(!titan.isDefeated()) 
						Titans.add(titan);
					
					if(titan.isDefeated()) 
						resourcesGained+=titan.getResourcesValue();
					
		}
		   for(Titan t: Titans) 
		      laneTitans.add(t);
		
		return resourcesGained;
	}
	
	
	
	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

}

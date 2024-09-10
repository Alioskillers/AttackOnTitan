package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class SniperCannon extends Weapon{
	public static final int WEAPON_CODE=2;
	
	public SniperCannon(int baseDamage) {
		super(baseDamage);
		
	}
	
	
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int resourcesGained=0;
		
		if(!laneTitans.isEmpty()) {
		Titan titan= laneTitans.poll();
		this.attack(titan);
		
		if(!titan.isDefeated())
			laneTitans.add(titan);
			
			if(titan.isDefeated()) {
				resourcesGained+=titan.getResourcesValue();
			}
		}
		return resourcesGained;
}
}
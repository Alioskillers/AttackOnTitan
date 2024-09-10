package game.engine.weapons;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class PiercingCannon extends Weapon{
	public static final int WEAPON_CODE=1;
	
	public PiercingCannon(int baseDamage) {
		super(baseDamage);
	}
	
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
     
		int resourcesGained = 0;
		int titansAttacked = 0;
		ArrayList<Titan>Titans= new ArrayList<Titan>();
		
		while(!laneTitans.isEmpty() && titansAttacked< 5) {
			Titan titan = laneTitans.poll();
			resourcesGained+=this.attack(titan);
			if(!titan.isDefeated())
				Titans.add(titan);
				titansAttacked++;
			}
				
			for(Titan titan: Titans) {
				laneTitans.add(titan);
		}
	return resourcesGained;
}
}
package game.engine.lanes;
import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;

import java.util.PriorityQueue;
import java.util.ArrayList;

public class Lane implements Comparable<Lane> {

	private final Wall laneWall;
	private int dangerLevel;
	private final PriorityQueue<Titan> titans;
	private final ArrayList<Weapon> weapons;
	
	
	public Lane(Wall LaneWall) {
		this.laneWall= LaneWall;
		this.dangerLevel=0;
		this.titans= new PriorityQueue<>();
		this.weapons = new ArrayList<>();
	}
	
	
	public void addTitan(Titan titan) {
		titans.add(titan);
	}
	
	public void addWeapon(Weapon weapon) {
		weapons.add(weapon);
	}
	
	public void moveLaneTitans() {
		ArrayList<Titan> Titans= new ArrayList<Titan>(); //"to add the titan to the array list after moving it as a temp to not let the priority queue empty"
		while(!titans.isEmpty()) {
			Titan titan= titans.poll(); // removes the titan from the lane
		titan.move();
		Titans.add(titan);
		}
		for(Titan titan: Titans) { // loops to add the titan to the array list
			titans.add(titan);
		}
	}
	
	
	
	public int performLaneTitansAttacks() {
		int resourcesGathered=0;
		ArrayList<Titan> Titans=new ArrayList<Titan>();
		while(!titans.isEmpty()) {
			Titan titan= titans.poll();
			 Titans.add(titan);
			if(titan.hasReachedTarget()) {
			resourcesGathered+=titan.attack(laneWall);
			}
		}
		for(Titan titan: Titans) {
		titans.add(titan);
	    
		}
	return resourcesGathered;
	}

	
	
	
	public int performLaneWeaponsAttacks() {
		int resourcesGathered=0;
		
		for(Weapon weapon: weapons) {
		resourcesGathered+=weapon.turnAttack(titans);
		}
	return resourcesGathered;
	}
	
	
	
	public boolean isLaneLost() {
		if(laneWall.getCurrentHealth()<=0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	
	public void updateLaneDangerLevel() {
		int dangerLevel=0;
		int gatheredResources=0;
		
		ArrayList<Titan> remainingTitans= new ArrayList<>();
		
		while(!titans.isEmpty()&&!isLaneLost()) {
			Titan titan = titans.poll();
			if(titan.isDefeated()) {
				gatheredResources+=titan.getResourcesValue();
			}
			else {
				remainingTitans.add(titan);
				dangerLevel+=titan.getDangerLevel();
			}
		}
			titans.addAll(remainingTitans);
			this.dangerLevel=dangerLevel;	
	}
	
	
	
	
	public int compareTo(Lane o) {
		return this.dangerLevel- o.dangerLevel;
	}

	public int getDangerLevel() {
		return dangerLevel;
	}

	public void setDangerLevel(int dangerLevel) {
		this.dangerLevel = dangerLevel;
	}

	public Wall getLaneWall() {
		return laneWall;
	}

	public PriorityQueue<Titan> getTitans() {
		return titans;
	}

	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
}

package game.engine.base;

import game.engine.interfaces.Attackee;

public class Wall implements Attackee{

	private final int baseHealth;
	private int currentHealth;
	
	public Wall(int baseHealth) {
		this.baseHealth=baseHealth;
		this.currentHealth=baseHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth) {
		if(currentHealth<0) {
			currentHealth=0;
		}
		this.currentHealth = currentHealth;
	}

	public int getBaseHealth() {
		return baseHealth;
	}
	
	public int getResourcesValue() {
		return -1;
	}
}

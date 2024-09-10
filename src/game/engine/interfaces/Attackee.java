package game.engine.interfaces;

public interface Attackee {
	public int getCurrentHealth();
	public void setCurrentHealth(int health);
	public int getResourcesValue();

	default boolean isDefeated() {
		if(getCurrentHealth()<=0) {
			return true;
		}
		return false;
	}
	
	default int takeDamage(int damage) {
		int currentHealth = getCurrentHealth();
		currentHealth -=damage;
		setCurrentHealth(currentHealth);
		
		if(isDefeated()) {
			return getResourcesValue();
		}
		else {
		return 0;
	}
}
}
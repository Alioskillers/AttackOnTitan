package game.engine.interfaces;

public interface Attacker {
	public int getDamage();
	
	default int attack(Attackee target) {
		int damage = getDamage();
		int currentHealth = target.takeDamage(damage);
		
		if(target.isDefeated()) {
			return target.getResourcesValue();
		}
		return 0;
	}
}

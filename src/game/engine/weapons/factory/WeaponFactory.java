package game.engine.weapons.factory;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.WeaponRegistry;

import java.util.HashMap;
import java.io.IOException;

public class WeaponFactory {

	private final HashMap<Integer, WeaponRegistry> weaponShop;
	
	public WeaponFactory() throws IOException{
		this.weaponShop=DataLoader.readWeaponRegistry();
}
	
	
	public FactoryResponse buyWeapon(int resources, int weaponCode) throws InsufficientResourcesException{
		WeaponRegistry wpn = weaponShop.get(weaponCode);
		if(resources< wpn.getPrice()) {
			throw new InsufficientResourcesException("Not enough resources, resources provided = ",resources);
		}
		else {
			int remainingResources= resources-wpn.getPrice();
			return new FactoryResponse(wpn.buildWeapon(),remainingResources);
		}
	}
	
	
	public void addWeaponToShop(int code, int price) {
		weaponShop.put(code, new WeaponRegistry(code, price));
	}
	
	public void addWeaponToShop(int code, int price, int damage, String name) {
		weaponShop.put(code, new WeaponRegistry(code, price, damage, name));
	}
	
	public void addWeaponToShop(int code, int price, int damage, String name, int minRange, int
			maxRange) {
		weaponShop.put(code, new WeaponRegistry(code, price, damage, name, minRange, maxRange));
	}
	
	
	public HashMap<Integer, WeaponRegistry> getWeaponShop(){
		return weaponShop;
	}
}
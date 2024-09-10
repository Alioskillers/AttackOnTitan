package game.engine.dataloader;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.WeaponRegistry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class DataLoader {

	private static final String TITANS_FILE_NAME="titans.csv";
	private static final String WEAPONS_FILE_NAME="weapons.csv";
	
	public static HashMap<Integer, TitanRegistry> readTitanRegistry() throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader(TITANS_FILE_NAME));
		HashMap<Integer, TitanRegistry> tr = new HashMap<Integer, TitanRegistry>();
		
		String i = "";
		while((i = br.readLine()) != null){
			String[] a = i.split(",");
		
	
		int code= Integer.parseInt(a[0]);
		int baseHealth= Integer.parseInt(a[1]);
		int baseDamage= Integer.parseInt(a[2]);
		int heightInMeters= Integer.parseInt(a[3]);
		int speed= Integer.parseInt(a[4]);
		int resourcesValue= Integer.parseInt(a[5]);
		int dangerLevel= Integer.parseInt(a[6]);
		
		TitanRegistry t= new TitanRegistry(code, baseHealth, baseDamage, heightInMeters, speed, resourcesValue, dangerLevel);
		tr.put(code,t);
	}
		
		return tr;
	}
	
	public static HashMap<Integer, WeaponRegistry> readWeaponRegistry() throws IOException{
		HashMap<Integer, WeaponRegistry> wr = new HashMap<Integer, WeaponRegistry>();
		BufferedReader b = new BufferedReader(new FileReader(WEAPONS_FILE_NAME));
		
		String j = "";
	    while((j=b.readLine())!= null) {
	    	String[] w = j.split(",");
	    	
	    	int code = Integer.parseInt(w[0]);
	    	int price = Integer.parseInt(w[1]);
	    	int damage = Integer.parseInt(w[2]);
	    	
	    	WeaponRegistry r =null;
	    	
	    	if(w.length==6) {
	    		int minRange= Integer.parseInt(w[4]);
	    		int maxRange= Integer.parseInt(w[5]);
	    		
	    		r = new WeaponRegistry(code, price, damage, w[3], minRange, maxRange );
	    	}
	    	else {
	    		r = new WeaponRegistry(code, price, damage, w[3]);
	    	}
	    	wr.put(code, r);
	    }
	    return wr;
	}

	public static String getTitansFileName() {
		return TITANS_FILE_NAME;
	}

	public static String getWeaponsFileName() {
		return WEAPONS_FILE_NAME;
	}
}
package game.engine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;

public class Battle {

	private static final int [][] PHASES_APPROACHING_TITANS= {{ 1, 1, 1, 2, 1, 3, 4 },
			{ 2, 2, 2, 1, 3, 3, 4 },
			{ 4, 4, 4, 4, 4, 4, 4 } };
	private static final int WALL_BASE_HEALTH=10000;
	private int numberOfTurns;
	private int resourcesGathered;
	private BattlePhase battlePhase=BattlePhase.EARLY;
	private int numberOfTitansPerTurn=1;
	private int score;
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans;
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;
	
	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes, int initialResourcesPerLane) throws IOException{
		
		this.numberOfTurns= numberOfTurns;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.weaponFactory=new WeaponFactory();
		this.titansArchives=DataLoader.readTitanRegistry();
		this.approachingTitans=new ArrayList<>();
		this.lanes=new PriorityQueue<>();
		this.originalLanes=new ArrayList<>();
		this.resourcesGathered= initialResourcesPerLane * initialNumOfLanes;
		
		initializeLanes(initialNumOfLanes);
	}
	
	private void initializeLanes(int numOfLanes) {
		for(int i=0; i<numOfLanes;i++) {
			Lane lane = new Lane(new Wall(WALL_BASE_HEALTH));
			lanes.offer(lane);
			originalLanes.add(lane);
		}
	}
	
	
	public void refillApproachingTitans() {
		int index=0;
	
		switch (battlePhase) {
		
		case EARLY:
				index=0;
				break;
			
			
		case INTENSE:
				index=1;
				break;
			
		case GRUMBLING:
				index=2;
				
		}
			for(int i=0; i<PHASES_APPROACHING_TITANS[index].length; i++) {
						int code= PHASES_APPROACHING_TITANS[index][i];
				approachingTitans.add(titansArchives.get(code).spawnTitan(titanSpawnDistance));
						
					}
				}
		
	
	
	public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException, InvalidLaneException{
		if(!lanes.contains(lane) || lane.isLaneLost())
			throw new InvalidLaneException();
		FactoryResponse fr= getWeaponFactory().buyWeapon(resourcesGathered, weaponCode);
		lane.addWeapon(fr.getWeapon());
		setResourcesGathered(fr.getRemainingResources());
		performTurn();
	}
		
	
	
	public void passTurn() {
		
		moveTitans();
		performWeaponsAttacks();
		performTitansAttacks();
		addTurnTitansToLane();
		updateLanesDangerLevels();
		finalizeTurns();
	}
	
	private void addTurnTitansToLane() {
		for(Lane lane: lanes) {
			if(approachingTitans.isEmpty()) 
				refillApproachingTitans();
			
			if(!lane.isLaneLost()) {
				for(int i=0; i<numberOfTitansPerTurn; i++) {
					if(approachingTitans.isEmpty())
						refillApproachingTitans();
					Titan titan= approachingTitans.remove(0);
					lane.addTitan(titan);
				}
				break;
			}
		}
		}
	
	
	private void moveTitans() {
		for(Lane lane: lanes) {
			for(Titan titan: lane.getTitans()) {
				titan.move();
			}
		}
	}
	
	
	private int performWeaponsAttacks() {
		int accumulatedResources=0;
		ArrayList<Lane> Lanes=new ArrayList<Lane>();
		while(!lanes.isEmpty()) {
			Lane lane=lanes.poll();
			if(!lane.isLaneLost()) {
				accumulatedResources+= lane.performLaneWeaponsAttacks();
			Lanes.add(lane);
			}
			}
		resourcesGathered+=accumulatedResources;
		score+=accumulatedResources;
		for(Lane lane: Lanes) 
			lanes.add(lane);
		return accumulatedResources;
	}
	
	

	
	private int performTitansAttacks() {
		int totalResources=0;
		
		ArrayList<Lane> Lanes=new ArrayList<Lane>();
		while(!lanes.isEmpty()) {
			Lane lane=lanes.poll();
			
			if(!lane.isLaneLost()) {
				totalResources+= lane.performLaneTitansAttacks();
				if(!lane.isLaneLost())
				Lanes.add(lane);
			  }
			}
		
		for(Lane L: Lanes) 
			lanes.add(L);
		return totalResources;	
	}
		
		
	
	private void updateLanesDangerLevels() {
		
		ArrayList<Lane> Lanes=new ArrayList<Lane>();
		while(!lanes.isEmpty()) {
			Lane lane=lanes.poll();
			
			if(!lane.isLaneLost()) {
				lane.updateLaneDangerLevel();
				Lanes.add(lane);
			}
			
			}
		for(Lane L: Lanes) 
			lanes.add(L);
		
		
		
		
	}
	
	
	private void finalizeTurns() {
		
		numberOfTurns++;
		
		if(numberOfTurns<15) {
			battlePhase= BattlePhase.EARLY;
		}
			
			else if(numberOfTurns<30) {
				battlePhase= BattlePhase.INTENSE;
			}
		
			else {
				battlePhase= BattlePhase.GRUMBLING;
				if(numberOfTurns>30 && numberOfTurns % 5==0) {
					numberOfTitansPerTurn *=2;//doubles every 5 rounds above 30
				}
			}
		}
	
	
	private void performTurn() {
		moveTitans();
		performWeaponsAttacks();
		performTitansAttacks();
		addTurnTitansToLane();
		updateLanesDangerLevels();
		finalizeTurns();
	}
	
	
	public boolean isGameOver() {
		boolean b=true;
		ArrayList<Lane> Lanes=new ArrayList<Lane>();
		
		while(!lanes.isEmpty()) {
			Lane lane=lanes.poll();
			Lanes.add(lane);
			if(!lane.isLaneLost()) {
				b=false;
			}
			}
		for(Lane L: Lanes) 
			lanes.add(L);
	return b;
	}
	
	
	public int getNumberOfTurns() {
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns) {
		if(numberOfTurns<0) {
			this.numberOfTurns=0;
		}
		else {
		this.numberOfTurns = numberOfTurns;
	}
	}

	public int getResourcesGathered() {
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered) {
		if(resourcesGathered<0) {
			this.resourcesGathered=0;
		}
		else {
		this.resourcesGathered = resourcesGathered;
	}
	}

	public BattlePhase getBattlePhase() {
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase) {
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn() {
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {
		if(numberOfTitansPerTurn<0) {
			this.numberOfTitansPerTurn=0;
		}
		else {
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if(score<0) {
			this.score=0;
		}
		else {
		this.score = score;
	}
	}

	public int getTitanSpawnDistance() {
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance) {
		if(titanSpawnDistance<0) {
			this.titanSpawnDistance=0;
		}
		else {
		this.titanSpawnDistance = titanSpawnDistance;
	}
	}

	public static int[][] getPhasesApproachingTitans() {
		return PHASES_APPROACHING_TITANS;
	}

	public static int getWallBaseHealth() {
		return WALL_BASE_HEALTH;
	}

	public WeaponFactory getWeaponFactory() {
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives() {
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans() {
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes() {
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes() {
		return originalLanes;
	}
	
	
}
package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.Weapon;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HardGameController implements Initializable { 
	
	@FXML
	Pane laneHardI;
	@FXML
	Pane laneHardII;
	@FXML
	Pane laneHardIII;
	@FXML
	Pane laneHardIIII;
	@FXML
	Pane laneHardIIIII;
	@FXML
	VBox wallHardI;
	@FXML
	VBox wallHardII;
	@FXML
	VBox wallHardIII;
	@FXML
	VBox wallHardIIII;
	@FXML
	VBox wallHardIIIII;
	@FXML
	Button buyHardPiercing;
	@FXML
	Button hardReturnToScene;
	@FXML
	Button buyHardSniper;
	@FXML
	Button buyHardVolley;
	@FXML
	Button buyHardTrap;
	@FXML
	ImageView myImage;
	@FXML
	Label wallHardHealthI;
	@FXML
	Label wallHardHealthII;
	@FXML
	Label wallHardHealthIII;
	@FXML
	Label wallHardHealthIIII;
	@FXML
	Label wallHardHealthIIIII;
	@FXML
	Label dangerHardLevelI;
	@FXML
	Label dangerHardLevelII;
	@FXML
	Label dangerHardLevelIII;
	@FXML
	Label dangerHardLevelIIII;
	@FXML
	Label dangerHardLevelIIIII;
	@FXML
    ImageView wallHard1;
	@FXML
    ImageView wallHard2;
	@FXML
    ImageView wallHard3;
	@FXML
    ImageView wallHard4;
	@FXML
    ImageView wallHard5;
	@FXML
	Button passHard;
	@FXML
	Label score;
	@FXML
	Label turn;
	@FXML
	Label phase;
	@FXML
	Label resources;
	@FXML
	Button backGameOver;
	@FXML
	Button hard;
	@FXML
	ImageView image;
	
	
	Wall wall;
	
	Battle battle;
	
	Lane lane;
	
	int hardLaneIndex;
	
	int hardWallIndex;
	
	public static ArrayList <Lane> hardLanes;
	
	
	
	public void handleHardPress(ActionEvent event) throws IOException{
		 battle= new Battle(1, 0, 110, 5, 125);
		 
		for(Lane L:battle.getLanes())
			hardLanes.add(L);
		
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("harddd.fxml"));
		Parent root= loader.load();
		Scene scene = new Scene(root);
		Stage stage=(Stage) hard.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@FXML
	public void handleHardPassTurn(ActionEvent event) {
		
		passHard.setOnAction(e ->{
			BattleLabels();
			handleHardSpawnTitan();
		
				battle.passTurn();
				handleGameOver();
		});
	}
	
	
	@FXML
	public void handleHardLaneindex(MouseEvent event) {
		laneHardI.setOnMouseClicked(e ->{
			hardLaneIndex=0;
		});
		laneHardII.setOnMouseClicked(e -> {
			hardLaneIndex=1;
		});
		laneHardIII.setOnMouseClicked(e -> {
			hardLaneIndex=2;
		});;
		laneHardIIII.setOnMouseClicked(e -> {
			hardLaneIndex=3;
		});;
		laneHardIIIII.setOnMouseClicked(e -> {
			hardLaneIndex=4;
		});;
	}
	
	@FXML
	public void handleHardWallIndex() {
		
		wallHardI.setOnMouseClicked(e->{
			hardWallIndex=0;
		});
		wallHardII.setOnMouseClicked(e->{
			hardWallIndex=1;
		});
		wallHardIII.setOnMouseClicked(e->{
			hardWallIndex=2;
		});
		wallHardIIII.setOnMouseClicked(e->{
			hardWallIndex=3;
		});
		wallHardIIIII.setOnMouseClicked(e->{
			hardWallIndex=4;
		});
		}
	
	
	
	
	@FXML
	public void handleHardBuyPiercingWeapon(ActionEvent event) {
		
		buyHardPiercing.setOnAction(e->{
			BattleLabels();
			handleGameOver();
			try {
				if(battle!=null)
				battle.purchaseWeapon(PiercingCannon.WEAPON_CODE, hardLanes.get(hardLaneIndex));
				handleHardSpawnTitan();
				handleHardSpawnWeapon();
			}
			catch(InsufficientResourcesException ev) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("ERROR");
		        alert.setHeaderText("INSUFFICIENT RESOURCES");
		        alert.setContentText(ev.getMessage()+battle.getResourcesGathered());
		        alert.getDialogPane().setContent(hardReturnToScene);
		        alert.showAndWait();

		        hardReturnToScene.setOnAction(evn -> {
		            alert.close();
		            alert.showAndWait();
		        });
			}
			catch(InvalidLaneException ee) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("ERROR");
		        alert.setHeaderText("INVALID LANE");
		        alert.setContentText(ee.getMessage());
		        alert.getDialogPane().setContent(hardReturnToScene);
		        alert.showAndWait();

		        hardReturnToScene.setOnAction(ev -> {
		            alert.close();
		            alert.showAndWait();
		        });
			}
		});
		
	}
	  
	
	
		
		@FXML
		public void handleHardBuySniperWeapon(ActionEvent event){
			
			buyHardSniper.setOnAction(e->{
				BattleLabels();
				handleGameOver();
				try {
					if(battle!=null)
					battle.purchaseWeapon(SniperCannon.WEAPON_CODE, hardLanes.get(hardLaneIndex));
					handleHardSpawnTitan();
					handleHardSpawnWeapon();
				}
				catch(InsufficientResourcesException ev) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INSUFFICIENT RESOURCES");
			        alert.setContentText(ev.getMessage()+battle.getResourcesGathered());
			        alert.getDialogPane().setContent(hardReturnToScene);
			        alert.showAndWait();

			        hardReturnToScene.setOnAction(evn -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
				catch(InvalidLaneException ee) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INVALID LANE");
			        alert.setContentText(ee.getMessage());
			        alert.getDialogPane().setContent(hardReturnToScene);
			        alert.showAndWait();

			        hardReturnToScene.setOnAction(ev -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
			});
			
		}
		
		
		@FXML
		public void handleHardBuyVolleyWeapon(ActionEvent event){
			
			buyHardVolley.setOnAction(e->{
				BattleLabels();
				handleGameOver();
				try {
					if(battle!=null)
					battle.purchaseWeapon(VolleySpreadCannon.WEAPON_CODE, hardLanes.get(hardLaneIndex));
					handleHardSpawnTitan();
					handleHardSpawnWeapon();
				}
				catch(InsufficientResourcesException ev) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INSUFFICIENT RESOURCES");
			        alert.setContentText(ev.getMessage()+battle.getResourcesGathered());
			        alert.getDialogPane().setContent(hardReturnToScene);
			        alert.showAndWait();

			        hardReturnToScene.setOnAction(evn -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
				catch(InvalidLaneException ee) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INVALID LANE");
			        alert.setContentText(ee.getMessage());
			        alert.getDialogPane().setContent(hardReturnToScene);
			        alert.showAndWait();

			        hardReturnToScene.setOnAction(ev -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
			});
			
		}
		
		
		@FXML
		public void handleHardBuyWallWeapon(ActionEvent event){
			
			buyHardTrap.setOnAction(e->{
				BattleLabels();
				handleGameOver();
				try {
					if(battle!=null)
					battle.purchaseWeapon(WallTrap.WEAPON_CODE, hardLanes.get(hardLaneIndex));
					handleHardSpawnTitan();
					handleHardSpawnWeapon();
				}
				catch(InsufficientResourcesException ev) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INSUFFICIENT RESOURCES");
			        alert.setContentText(ev.getMessage()+battle.getResourcesGathered());
			        alert.getDialogPane().setContent(hardReturnToScene);
			        alert.showAndWait();

			        hardReturnToScene.setOnAction(evn -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
				catch(InvalidLaneException ee) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INVALID LANE");
			        alert.setContentText(ee.getMessage());
			        alert.getDialogPane().setContent(hardReturnToScene);
			        alert.showAndWait();

			        hardReturnToScene.setOnAction(ev -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
			});
			
		}
		
		
		
		@FXML
		public void handleHardSpawnTitan() {
			BattleLabels();
			handleGameOver();
			int count = 0;
			
			laneHardI.getChildren().clear();
		     
	        laneHardII.getChildren().clear();
	       
	        laneHardIII.getChildren().clear();
	        
	        laneHardIIII.getChildren().clear();
	        
	        laneHardIIIII.getChildren().clear();
	        
	        
		    
		    for (Lane L : hardLanes) {
		    for (Titan titan : L.getTitans()) {
		        Circle c = new Circle(15); 
		        
		        int distance = titan.getDistance()*8; 
		      
		        if (titan instanceof PureTitan) {
		            c.setFill(Color.RED);
		            c.setTranslateX(distance);
			        c.setTranslateY(0);
		        } else if (titan instanceof ArmoredTitan) {
		            c.setFill(Color.BLACK);
		            c.setTranslateX(distance);
			        c.setTranslateY(19.5);
		        } else if (titan instanceof ColossalTitan) {
		            c.setFill(Color.GREEN);
		            c.setTranslateX(distance);
			        c.setTranslateY(49);
		        } else if (titan instanceof AbnormalTitan) {
		            c.setFill(Color.YELLOW);
		            c.setTranslateX(distance);
			        c.setTranslateY(60);
		        }

		        
		        
		        
		       
		       switch (count) {
		            case 0:
		                laneHardI.getChildren().add(c);
		                break;
		            case 1:
		                laneHardII.getChildren().add(c);
		                break;
		            case 2:
		                laneHardIII.getChildren().add(c);
		                break;
		            case 3:
		                laneHardIIII.getChildren().add(c);
		                break;
		            case 4:
		                laneHardIIIII.getChildren().add(c);
		                break;
		        }
		        count++;
		    }
		    
		}
		}
	
	
	
	@FXML
	public void handleHardSpawnWeapon() {
		BattleLabels();
		
		int count=0;
		lane= hardLanes.get(hardLaneIndex);
		for(Weapon weapon: lane.getWeapons()) {
			Rectangle c= new Rectangle(15, 15);
			
			if(weapon instanceof PiercingCannon) {
				c.setFill(Color.BLUE);
			}
			if(weapon instanceof SniperCannon) {
				c.setFill(Color.DARKGREY);
			}
			if(weapon instanceof VolleySpreadCannon) {
				c.setFill(Color.YELLOW);
			}
			if(weapon instanceof WallTrap) {
				c.setFill(Color.BROWN);
			}
			
			c.setTranslateX(laneHardI.getLayoutX());
		}
		count++;
		}
	
	
	@FXML
	public void BattleLabels() {
		wallHardHealthI.setText("Wall Health: "+ hardLanes.get(0).getLaneWall().getCurrentHealth());
		wallHardHealthII.setText("Wall Health: "+ hardLanes.get(1).getLaneWall().getCurrentHealth());
		wallHardHealthIII.setText("Wall Health: "+ hardLanes.get(2).getLaneWall().getCurrentHealth());
		wallHardHealthIIII.setText("Wall Health: "+ hardLanes.get(3).getLaneWall().getCurrentHealth());
		wallHardHealthIIIII.setText("Wall Health: "+ hardLanes.get(4).getLaneWall().getCurrentHealth());


		
		dangerHardLevelI.setText("Danger Level:" + hardLanes.get(0).getDangerLevel());
		dangerHardLevelII.setText("Danger Level:" + hardLanes.get(1).getDangerLevel());
		dangerHardLevelIII.setText("Danger Level:" + hardLanes.get(2).getDangerLevel());
		dangerHardLevelIIII.setText("Danger Level:" + hardLanes.get(3).getDangerLevel());
		dangerHardLevelIIIII.setText("Danger Level:" + hardLanes.get(4).getDangerLevel());
		
		score.setText("Score: "+ battle.getScore());
		turn.setText("No. Of Turns: "+ battle.getNumberOfTurns());
		phase.setText("Phase: "+ battle.getBattlePhase());
		resources.setText("Resources: "+ battle.getResourcesGathered());
	}

	
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		TranslateTransition translate= new TranslateTransition();
		 try {
			battle= new Battle(1, 0, 110, 5, 125);
		} catch (IOException e) {
			e.printStackTrace();
		}
		hardLanes = new ArrayList<Lane>();
		 for(Lane L : battle.getLanes()) {
				hardLanes.add(L);
			}
		
		 
		translate.setNode(myImage);
		translate.setByY(250);
		translate.setDuration(Duration.millis(10000));
		translate.setCycleCount(TranslateTransition.INDEFINITE);
		translate.setAutoReverse(true);
		translate.play();
	}
	
	
	

	@FXML
	public void handleGameOver(){
		BattleLabels();
		if(battle.isGameOver()) {
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("Game Over.fxml"));
		Parent root= loader.load();
		Scene scene = new Scene(root);
		Stage stage=(Stage) image.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	}

	
	
	
	
	@FXML
	public void handleBackInGameOverPress(ActionEvent event){
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("StartMenu.fxml"));
		Parent root= loader.load();
		Scene scene = new Scene(root);
		Stage stage=(Stage) backGameOver.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
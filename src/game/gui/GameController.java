package game.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.interfaces.Attackee;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameController implements Initializable {
	
	@FXML
	Button start;
	@FXML
	Button easy;
	@FXML
	Button hard;
	@FXML
	Button instructions;
	@FXML
	Button exit;
	@FXML
	Button back;
	@FXML
	Button backGameOver;
	@FXML
	Button buyPiercing;
	@FXML
	Button returnToScene;
	@FXML
	Button buySniper;
	@FXML
	Button buyVolley;
	@FXML
	Button buyTrap;
	@FXML
	ImageView myImage;
	@FXML
	Label wallHealthI;
	@FXML
	Label wallHealthII;
	@FXML
	Label wallHealthIII;
	@FXML
	Label dangerLevelI;
	@FXML
	Label dangerLevelII;
	@FXML
	Label dangerLevelIII;
	@FXML
	Button passEasy;
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
	Pane laneI;
	@FXML
	Pane laneII;
	@FXML
	Pane laneIII;
	@FXML
	VBox wallI;
	@FXML
	VBox wallII;
	@FXML
	VBox wallIII;
	@FXML
	ImageView image;
	@FXML
	MediaView mediaView;
	@FXML
	MediaView music;
	
	File file;
	Media media;
	MediaPlayer mediaPlayer;
	File fileI;
	Media mediaI;
	MediaPlayer mediaPlayerI;
	Titan titan;
	Battle battle;
	Lane lane;
	Attackee attackee;
    TitanRegistry titanRegistry;
	public static ArrayList <Lane> lanes;
	public static ArrayList <Lane> hardLanes;
	int weaponCode=0;
	int laneIndex=0;
	int wallIndex=0;
	Wall wall;
	
	
		
	
	@FXML
	public void handleStartPress(ActionEvent event){
		
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("difficulty.fxml"));
		Parent root= loader.load();
		Scene scene = new Scene(root);
		Stage stage=(Stage) start.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@FXML
	public void handleInstructionsPress(ActionEvent event){
		mediaPlayerI.pause();
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("instructions.fxml"));
		Parent root= loader.load();
		Scene scene = new Scene(root);
		Stage stage=(Stage) instructions.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
		catch(IOException e) {
			e.printStackTrace();
	
}
	}
	
	
	@FXML
	public void handleExitpress(ActionEvent event) {
		System.exit(0);
	}
	
	@FXML
	public void handleBackPress(ActionEvent event){
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("StartMenu.fxml"));
		Parent root= loader.load();
		Scene scene = new Scene(root);
		Stage stage=(Stage) back.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		mediaPlayer.pause();
	}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	

	
	@FXML
	public void handleEasyPress(ActionEvent event) throws IOException{
		 battle= new Battle(1, 0, 110, 3, 250);
		 
		for(Lane L:battle.getLanes())
			lanes.add(L);
		
		try {
		FXMLLoader loader= new FXMLLoader(getClass().getResource("easyy.fxml"));
		Parent root= loader.load();
		Scene scene = new Scene(root);
		Stage stage=(Stage) easy.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}
		catch(IOException e) {
			e.printStackTrace();
		}
	}	
	
	@FXML
	public void handleHardPress(ActionEvent event) throws IOException{
		 battle= new Battle(1, 0, 110, 5, 125);
		 hardLanes= new ArrayList<>();
		for(Lane L:battle.getLanes()) {
			hardLanes.add(L);
		}
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
	public void handleEasyPassTurn(ActionEvent event) {
		
		passEasy.setOnAction(e ->{
			BattleLabels();
			handleSpawnTitan();
		
				battle.passTurn();
				handleGameOver();
		});
	}
	
	
	
	
	
	@FXML
	public void handleLaneindex(MouseEvent event) {
		laneI.setOnMouseClicked(e ->{
			laneIndex=0;
		});
		laneII.setOnMouseClicked(e -> {
			laneIndex=1;
		});
		laneIII.setOnMouseClicked(e -> {
			laneIndex=2;
		});;
	}
	
	@FXML
	public void handleWallIndex() {
		wallI.setOnMouseClicked(e->{
			wallIndex=0;
		});
		wallII.setOnMouseClicked(e->{
			wallIndex=1;
		});
		wallIII.setOnMouseClicked(e->{
			wallIndex=2;
		});
		}
		
		
		
		
		
	@FXML
	public void handleBuyPiercingWeapon(ActionEvent event) {
		
		buyPiercing.setOnAction(e->{
			BattleLabels();
			handleGameOver();
			try {
				if(battle!=null)
				battle.purchaseWeapon(PiercingCannon.WEAPON_CODE, lanes.get(laneIndex));
				handleSpawnTitan();
				handleSpawnWeapon();
			}
			catch(InsufficientResourcesException ev) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("ERROR");
		        alert.setHeaderText("INSUFFICIENT RESOURCES");
		        alert.setContentText(ev.getMessage()+battle.getResourcesGathered());
		        alert.getDialogPane().setContent(returnToScene);
		        alert.showAndWait();

		        returnToScene.setOnAction(evn -> {
		            alert.close();
		            alert.showAndWait();
		        });
			}
			
			catch(InvalidLaneException ee) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("ERROR");
		        alert.setHeaderText("INVALID LANE");
		        alert.setContentText(ee.getMessage());
		        alert.getDialogPane().setContent(returnToScene);
		        alert.showAndWait();

		        returnToScene.setOnAction(ev -> {
		            alert.close();
		            alert.showAndWait();
		        });
			}
			
		});
	}
	
	  
	
	
		
		@FXML
		public void handleBuySniperWeapon(ActionEvent event){
			
			buySniper.setOnAction(e->{
				BattleLabels();
				handleGameOver();
				try {
					if(battle!=null)
					battle.purchaseWeapon(SniperCannon.WEAPON_CODE, lanes.get(laneIndex));
					handleSpawnTitan();
					handleSpawnWeapon();
				}
				catch(InsufficientResourcesException ev) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INSUFFICIENT RESOURCES");
			        alert.setContentText(ev.getMessage()+battle.getResourcesGathered());
			        alert.getDialogPane().setContent(returnToScene);
			        alert.showAndWait();

			        returnToScene.setOnAction(evn -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
				catch(InvalidLaneException ee) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INVALID LANE");
			        alert.setContentText(ee.getMessage());
			        alert.getDialogPane().setContent(returnToScene);
			        alert.showAndWait();

			        returnToScene.setOnAction(ev -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
			});
			
		}
		
		
		@FXML
		public void handleBuyVolleyWeapon(ActionEvent event){
			
			buyVolley.setOnAction(e->{
				BattleLabels();
				handleGameOver();
				try {
					if(battle!=null)
					battle.purchaseWeapon(VolleySpreadCannon.WEAPON_CODE, lanes.get(laneIndex));
					handleSpawnTitan();
					handleSpawnWeapon();
				}
				catch(InsufficientResourcesException ev) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INSUFFICIENT RESOURCES");
			        alert.setContentText(ev.getMessage()+battle.getResourcesGathered());
			        alert.getDialogPane().setContent(returnToScene);
			        alert.showAndWait();

			        returnToScene.setOnAction(evn -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
				catch(InvalidLaneException ee) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INVALID LANE");
			        alert.setContentText(ee.getMessage());
			        alert.getDialogPane().setContent(returnToScene);
			        alert.showAndWait();

			        returnToScene.setOnAction(ev -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
			});
			
		}
		
		
		@FXML
		public void handleBuyWallWeapon(ActionEvent event){
			
			buyTrap.setOnAction(e->{
				BattleLabels();
				handleGameOver();
				try {
					if(battle!=null)
					battle.purchaseWeapon(WallTrap.WEAPON_CODE, lanes.get(laneIndex));
					handleSpawnTitan();
					handleSpawnWeapon();
				}
				catch(InsufficientResourcesException ev) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INSUFFICIENT RESOURCES");
			        alert.setContentText(ev.getMessage()+battle.getResourcesGathered());
			        alert.getDialogPane().setContent(returnToScene);
			        alert.showAndWait();

			        returnToScene.setOnAction(evn -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
				catch(InvalidLaneException ee) {
					Alert alert = new Alert(AlertType.ERROR);
			        alert.setTitle("ERROR");
			        alert.setHeaderText("INVALID LANE");
			        alert.setContentText(ee.getMessage());
			        alert.getDialogPane().setContent(returnToScene);
			        alert.showAndWait();

			        returnToScene.setOnAction(ev -> {
			            alert.close();
			            alert.showAndWait();
			        });
				}
			});
			
		}
		
		
		
		@FXML
		public void handleSpawnTitan() {
			BattleLabels();
			handleGameOver();
		    int count = 0;
		   
		    laneI.getChildren().clear();
         
            laneII.getChildren().clear();
           
            laneIII.getChildren().clear();
            
		    
		    
		    for (Lane L : lanes) {
		    for (Titan titan : L.getTitans()) {
		        Circle c = new Circle(15); 
		        
		        int distance = titan.getDistance()*8; 
		      
		        if (titan instanceof PureTitan) {
		            c.setFill(Color.RED);
		        } else if (titan instanceof ArmoredTitan) {
		            c.setFill(Color.BLACK);
		        } else if (titan instanceof ColossalTitan) {
		            c.setFill(Color.GREEN);
		        } else if (titan instanceof AbnormalTitan) {
		            c.setFill(Color.YELLOW);
		        }

		        c.setTranslateX(distance);
		        c.setTranslateY(0);
		        
		       
		        switch (count) {
		            case 0:
		                laneI.getChildren().add(c);
		                break;
		            case 1:
		                laneII.getChildren().add(c);
		                break;
		            case 2:
		                laneIII.getChildren().add(c);
		                break;
		        }
		        
		    }
		    count++;
		    }
		}
		    
		
		
		
		
		@FXML
		public void handleSpawnWeapon() {
			BattleLabels();
			handleGameOver();
			
			int count=0;
			lane= lanes.get(laneIndex);
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
				
				c.setTranslateX(laneI.getLayoutX());
				
				
			}
			count++;
			}
		
		
		
		
	
		public void initialize(URL arg0, ResourceBundle arg1) {
			TranslateTransition translate= new TranslateTransition();
			 try {
				battle= new Battle(1, 0, 110, 3, 250);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 lanes = new ArrayList<Lane>();
			 for(Lane l : battle.getLanes()) {
					lanes.add(l);
				}
			 
			translate.setNode(myImage);
			translate.setByY(250);
			translate.setDuration(Duration.millis(10000));
			translate.setCycleCount(TranslateTransition.INDEFINITE);
			translate.setAutoReverse(true);
			translate.play();
			
			
			try {
			file= new File("not.mp4");
			media= new Media(file.toURI().toString());
			mediaPlayer= new MediaPlayer(media);
			mediaView.setMediaPlayer(mediaPlayer);
			mediaPlayer.play();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
			
			try {
				fileI= new File("battle-of-the-dragons-8037.mp3");
				mediaI= new Media(fileI.toURI().toString());
				mediaPlayerI= new MediaPlayer(mediaI);
				music.setMediaPlayer(mediaPlayerI);
				mediaPlayerI.play();
				
			}
			
		catch(Exception e) {
			e.printStackTrace();
		}
		}
		
		
		
		
		@FXML
		public void updateScoreIngameOver() {
			score.setText("Score: "+ battle.getScore());
		}
		
		
		@FXML
		public void BattleLabels() {
			
			wallHealthI.setText("Wall Health: "+ lanes.get(0).getLaneWall().getCurrentHealth());
			wallHealthII.setText("Wall Health: "+ lanes.get(1).getLaneWall().getCurrentHealth());
			wallHealthIII.setText("Wall Health: "+ lanes.get(2).getLaneWall().getCurrentHealth());
			
			dangerLevelI.setText("Danger Level:" + lanes.get(0).getDangerLevel());
			dangerLevelII.setText("Danger Level:" + lanes.get(1).getDangerLevel());
			dangerLevelIII.setText("Danger Level:" + lanes.get(2).getDangerLevel());
			
			score.setText("Score: "+ battle.getScore());
			turn.setText("No. Of Turns: "+ battle.getNumberOfTurns());
			phase.setText("Phase: "+ battle.getBattlePhase());
			resources.setText("Resources: "+ battle.getResourcesGathered());
		
			
		}
			
			@FXML
			public void returnToEasyScene(ActionEvent event) {
				try {
					FXMLLoader loader= new FXMLLoader(getClass().getResource("easy.fxml"));
					Parent root= loader.load();
					Scene scene = new Scene(root);
					Stage stage=(Stage) easy.getScene().getWindow();
					stage.setScene(scene);
					stage.show();
				}
					catch(IOException e) {
						e.printStackTrace();
					}
			}
			
			
			@FXML
			public void returnToHardScene(ActionEvent event) {
				try {
					FXMLLoader loader= new FXMLLoader(getClass().getResource("hard.fxml"));
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
			public void handleGameOver(){
				updateScoreIngameOver();
				if(battle.isGameOver()) {
					updateScoreIngameOver();
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

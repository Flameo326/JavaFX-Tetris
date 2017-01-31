package Scenes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EntryScene {
	
	private int width, height;
	private Stage stage;
	private Button playBtn, helpBtn, exitBtn, settingsBtn;
	
	public EntryScene(Stage s, int width, int height){
		this.width = width;
		this.height = height;
		stage = s;
	}
	
	public void display(){
		BorderPane root = new BorderPane();
		root.setOnMouseClicked(e -> System.out.println(e.getX() + "  " + e.getY()));
		
		// top
		Label title = new Label("Tetris");
		title.setStyle("-fx-padding: " + width/10 + "; -fx-font-size: 48px; -fx-font-family: serif; -fx-font-weight: bold;");
		StackPane centerLabel = new StackPane(title);
		root.setTop(centerLabel);
		
		// bottom
		playBtn = new Button("Play");
		playBtn.setStyle("-fx-padding: 0 2em; -fx-font-size: 2em");
		playBtn.setDefaultButton(true);
		playBtn.setOnMouseClicked(e -> displayGame());
		helpBtn = new Button("Help");
		helpBtn.setStyle("-fx-padding: 0 2em; -fx-font-size: 2em");
		helpBtn.setOnMouseClicked(e -> displayTutorial());
		settingsBtn = new Button("Settings");
		settingsBtn.setStyle("-fx-padding: 0 2em; -fx-font-size: 2em");
		settingsBtn.setOnMouseClicked(e -> displaySettings());
		exitBtn = new Button("Exit");
		exitBtn.setStyle("-fx-margin: 50px; -fx-padding: 0 2em; -fx-font-size: 2em");
		exitBtn.setCancelButton(true);
		exitBtn.setOnMouseClicked(e -> exit());
		
		VBox center = new VBox(playBtn, helpBtn, settingsBtn, exitBtn);
		center.setStyle("-fx-padding: " + height/100 + "px;");
		center.setSpacing(height/100);
		center.setAlignment(Pos.CENTER);
		root.setBottom(new StackPane(center));
		
		
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
		
		//because java is dumb for reason.
		width = (int) stage.getWidth();
		height = (int) stage.getHeight();
	}
	
	public void displayGame(){
		stage.hide();
		GameScene game = new GameScene(stage, width, height, false);
		game.display();
	}
	
	public void displayTutorial(){
		stage.hide();
		GameScene tut = new GameScene(stage, width, height, true);
		tut.display();
	}
	
	public void displaySettings(){
		stage.hide();
		SettingsScene settings = new SettingsScene(stage, width, height);
		settings.display();
	}
	
	public void exit(){
		stage.close();
	}

}

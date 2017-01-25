package Scenes;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SettingsScene {
	
	private Stage stage;
	private StackPane root;
	private int width, height;
	
	public SettingsScene(Stage stage, int width, int height){
		this.stage = stage;
		this.width = width;
		this.height = height;
	}
	
	public void display(){
		root = new StackPane();
		
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.centerOnScreen();
	}
	
	public void setNewScene(){
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
	}

}

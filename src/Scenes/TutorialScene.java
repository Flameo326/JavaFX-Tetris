package Scenes;

import java.util.ArrayList;

import Logic.Game;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TutorialScene {
	
	private Game game;
	private ArrayList<String> keyInput;
	
	private Stage stage;
	private ImageView gameField, nextBlock;
	private int width, height;
	
	public TutorialScene(Stage stage, int width, int height){
		this.stage = stage;
		this.width = width;
		this.height = height;
		keyInput = new ArrayList<>();
	}
	
	public void display(){
		GridPane root = new GridPane();
		root.setGridLinesVisible(true);
		root.setHgap(10);
		root.setVgap(10);
		
		// left side
		gameField = new ImageView();
		gameField.setSmooth(false);
		gameField.setFocusTraversable(true);
		gameField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				String code = event.getCode().toString();
				if(!keyInput.contains(code)){
					keyInput.add(code);
				}
			}
		});

		gameField.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				String code = event.getCode().toString();
				if(keyInput.contains(code)){
					keyInput.remove(code);
				}
			}
		});
		root.add(gameField, 1, 1, 1, 10);
		
		
		// right side
		nextBlock = new ImageView();
		root.add(new StackPane(nextBlock), 2, 1, 2, 1);
		
		Button exitBtn = new Button("Exit");
		exitBtn.setStyle("-fx-padding: 0 2em; -fx-font-size: 2em");
		exitBtn.setOnMouseClicked(e -> exit());
		root.add(exitBtn, 2, 10, 2, 1);
		
		// have a popup display when the uder needs to do something.
		
		Scene scene = new Scene(root, width, height);
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
		
		game = new Game(width*3/5, (int) (height-1.5*scene.getY()),
				gameField, nextBlock, keyInput);
		game.start();
		stage.setOnCloseRequest(e -> game.stop());
	}
	
	public void exit(){
		EntryScene scene = new EntryScene(stage, width, height);
		scene.display();
		game.stop();
	}

}

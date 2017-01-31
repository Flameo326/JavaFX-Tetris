package Scenes;

import java.util.ArrayList;

import Logic.Game;
import Logic.Tutorial;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameScene {
	
	private Tutorial tut;
	private Game game;
	private ArrayList<String> keyInput;
	
	private Stage stage;
	private ImageView gameField, nextBlock;
	private Button pauseBtn;
	private int width, height;
	private boolean tutorialEnabled;
	
	public GameScene(Stage stage, int width, int height, boolean tutorialEnabled){
		this.stage = stage;
		this.width = width;
		this.height = height;
		this.tutorialEnabled = tutorialEnabled;
		keyInput = new ArrayList<>();
	}
	
	public void display(){
		GridPane root = new GridPane();
		//root.setGridLinesVisible(true);
		root.setHgap(10);
		root.setVgap(10);
		
		// left side
		gameField = new ImageView();
		gameField.setFocusTraversable(true);
		root.add(gameField, 1, 1, 1, 10);
		
		
		// right side
		nextBlock = new ImageView();
		root.add(new StackPane(nextBlock), 2, 1, 2, 1);
		
		Button exitBtn = new Button("Exit");
		exitBtn.setStyle("-fx-padding: 0 2em; -fx-font-size: 2em");
		exitBtn.setOnMouseClicked(e -> exit());
		root.add(exitBtn, 2, 10, 1, 1);
		
		pauseBtn = new Button("Pause");
		pauseBtn.setStyle("-fx-padding: 0 2em; -fx-font-size: 2em");
		pauseBtn.setOnMouseClicked(e -> pause());
		root.add(pauseBtn, 3, 10, 2, 1);
		
		Scene scene = new Scene(root, width, height);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				String code = event.getCode().toString();
				if(!keyInput.contains(code)){
					keyInput.add(code);
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				String code = event.getCode().toString();
				if(keyInput.contains(code)){
					keyInput.remove(code);
				}
			}
		});
		
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
		
		game = new Game(width*3/5, (int) (height-1.5*scene.getY()),
				gameField, nextBlock, keyInput);
		stage.setOnCloseRequest(e -> game.stop());
		if(tutorialEnabled){
			tut = new Tutorial(game, keyInput, gameField);
			tut.start();
		} else {
			game.start();
		}
	}
	
	public void exit(){
		EntryScene scene = new EntryScene(stage, width, height);
		scene.display();
		game.stop();
		if(tutorialEnabled) { tut.stop(); }
	}
	
	
	public void pause(){
		game.stop();
		pauseBtn.setText("Resume");
		pauseBtn.setOnMouseClicked(e -> resume());
	}
	
	public void resume(){
		game.start();
		pauseBtn.setText("Pause");
		pauseBtn.setOnMouseClicked(e -> pause());
		gameField.requestFocus();
	}

}

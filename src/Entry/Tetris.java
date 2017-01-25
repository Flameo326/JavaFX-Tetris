package Entry;

import java.awt.Dimension;
import java.awt.Toolkit;

import Scenes.EntryScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Tetris extends Application{
	
	private final int WIDTH, HEIGHT;
	
	public Tetris(){
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = (int) size.getWidth()/4;
		HEIGHT = (int) size.getHeight();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Tetris");
		stage.setResizable(false);

		EntryScene entry = new EntryScene(stage, WIDTH, HEIGHT);
		entry.display();
	}

}

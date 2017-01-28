package Logic;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class Game extends AnimationTimer{

	private ArrayList<String> keyInput;
	private boolean running;
	private int frames = 0;
	
	private Map map;
	private ImageView gameField, nextBlock;
	
	public Game(int width, int height, ImageView gameField, ImageView nextBlock, ArrayList<String> keyInput){
		this.gameField = gameField;
		this.nextBlock = nextBlock;
		this.keyInput = keyInput;
		running = true;
		map = new Map(width, height);
	}
	
	
	@Override
	public void handle(long now) {
//		System.out.println("Frame: " + count);
		if(running){
		// logic
		checkInput();
		if(++frames >= 35) { frames = 0; running = map.updateY(1); }
		
		// graphics...
		gameField.setImage(map.getGameFieldImage());
		nextBlock.setImage(map.getNextBlockImage());
		} else {
			gameField.setImage(map.getGameOverImage());
		}
	}
	
	public void checkInput(){
//		if(keyInput.contains("W")){ keyInput.remove("W"); map.updateY(-1); }
		if(keyInput.contains("S")) { keyInput.remove("S"); running = map.updateY(1);  }
		else if(keyInput.contains("A")) { keyInput.remove("A"); map.updateX(-1); }
		else if(keyInput.contains("D")) { keyInput.remove("D"); map.updateX(1); }
		else if(keyInput.contains("SPACE")) { keyInput.remove("SPACE"); map.rotate(); } 
	}

}

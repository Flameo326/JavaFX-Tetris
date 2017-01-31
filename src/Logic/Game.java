package Logic;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class Game extends AnimationTimer{

	private final int FRAMES_PER_MOVEMENT = 35;
	
	private ArrayList<String> keyInput;
	private int frames = 0;
	
	private Map map;
	private ImageView gameField, nextBlock;
	
	public Game(int width, int height, ImageView gameField, ImageView nextBlock, ArrayList<String> keyInput){
		this.gameField = gameField;
		this.nextBlock = nextBlock;
		this.keyInput = keyInput;
		map = new Map(width, height);
	}
	
	@Override
	public void handle(long now) {
		// logic
		checkInput();
		if(++frames >= FRAMES_PER_MOVEMENT) { frames = 0; map.updateY(1); }
		
		// graphics...
		gameField.setImage(map.getGameFieldImage());
		nextBlock.setImage(map.getNextBlockImage());
		
		if(map.getLoss()){
			gameField.setImage(map.getGameOverImage());
			this.stop();
		}
	}
	
	public void checkInput(){
//		if(keyInput.contains("W")){ keyInput.remove("W"); map.updateY(-1); }
		if(keyInput.contains("S")) { keyInput.remove("S"); map.updateY(1);  }
		else if(keyInput.contains("A")) { keyInput.remove("A"); map.updateX(-1); }
		else if(keyInput.contains("D")) { keyInput.remove("D"); map.updateX(1); }
		else if(keyInput.contains("SPACE")) { keyInput.remove("SPACE"); map.rotate(); } 
	}
	
	public int getFramesPerMovement(){
		return FRAMES_PER_MOVEMENT;
	}

}

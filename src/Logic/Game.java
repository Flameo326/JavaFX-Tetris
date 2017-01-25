package Logic;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class Game extends AnimationTimer{

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
		
//		System.out.println("Frame: " + count);
		
		// logic
		checkInput();
		if(++frames >= 35) { frames = 0; map.updateY(1); }
		
		// graphics...
		gameField.setImage(map.getGameFieldImage());
		nextBlock.setImage(map.getNextBlockImage());
	}
	
//	public void start(){
//		running = true;
//		thread = new Thread(this);
//		thread.start();
//	}
//	
//	public void stop(){
//		running = false;
//		try {
//			thread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void run() {
//		
//	}
	
	public void checkInput(){
		if(keyInput.contains("W")){ keyInput.remove("W"); map.updateY(-1); }
		if(keyInput.contains("S")) { keyInput.remove("S"); map.updateY(1); }
		else if(keyInput.contains("A")) { keyInput.remove("A"); map.updateX(-1); }
		else if(keyInput.contains("D")) { keyInput.remove("D"); map.updateX(1); }
		else if(keyInput.contains("SPACE")) { keyInput.remove("SPACE"); map.rotate(); } 
	}

	

}

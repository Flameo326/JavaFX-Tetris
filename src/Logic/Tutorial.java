package Logic;

import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Tutorial extends AnimationTimer{
	
	private enum Stage{
		STAGE_1, STAGE_2, STAGE_3, STAGE_4, STAGE_5, STAGE_6, STAGE_7, STAGE_8, FINAL_STAGE, COMPLETED;
	}
	private final Game game;
	private final ImageView display;
	private ArrayList<String> input;
	private Stage stage;
	private int count, fps;
	
	public Tutorial(Game _game, ArrayList<String> _input, ImageView _display){
		game = _game;
		display = _display;
		input = _input;
		stage = Stage.STAGE_1;
		fps = game.getFramesPerMovement();
		game.start();
	}

	@Override
	public void handle(long now) {
		++count;
		switch(stage){
		case STAGE_1:
			if(count >= fps*3.3){
				game.stop();
				display("Welcome to Tetris!\n\nTetris is a game about blocks.\n\nThe goal of the game is to stack blocks to fill an entire row.\n\nPress Enter to continue.");
				waitForInput("ENTER", e -> {game.start(); count = 0; stage = Stage.STAGE_2; });
			}
			break;
		case STAGE_2:
			if(count >= fps*5.3){
				game.stop();
				display("Blocks will constantly keep falling down.\n\nYou can move them left or right using 'A' or 'D' respectively.\n\nPress Enter to continue.");
				waitForInput("ENTER", e -> {game.start(); count = 0; stage = Stage.STAGE_3; });
			}
			break;
		case STAGE_3:
			if(count >= fps*12){
				game.stop();
				display("When a block can no longer move down, it will settle into place.\n\nWhen this happens the next block will come into play.\n\nPress Enter to continue.");
				waitForInput("ENTER", e -> {game.start(); count = 0; stage = Stage.STAGE_4; });
			}
			break;
		case STAGE_4:
			if(count >= fps*4.8){
				game.stop();
				display("The top right corner displays the image of the next incoming block.\n\nUse this to your advantage.\n\nPress Enter to continue.");
				waitForInput("ENTER", e -> {game.start(); count = 0; stage = Stage.STAGE_5; });
			}
			break;
		case STAGE_5:
			if(count >= fps*5.7){
				game.stop();
				display("You can rotate the block by pressing space.\n\nBlocks will always rotate around their top left position.\n\nPress Space to continue.");
				waitForInput("SPACE", e -> {game.start(); count = 0; stage = Stage.STAGE_6; });
			}
			break;
		case STAGE_6:
			if(count >= fps*3.5){
				game.stop();
				display("Some blocks are symmetrical and will only rotate twice.\n\nBlocks will never rotate if it will put them out of bounds or on top of another block.\n\nPress Enter to continue.");
				waitForInput("ENTER", e -> {game.start(); count = 0; stage = Stage.STAGE_7; });
			}
			break;
		case STAGE_7:
			if(count >= fps*4.3){
				game.stop();
				display("Pressing 'S' will manually lower the block.\n\nPress 'S' to continue.");
				waitForInput("S", e -> {game.start(); count = 0; stage = Stage.STAGE_8; });
			}
			break;
		case STAGE_8:
			if(count >= fps*10){
				game.stop();
				display("If the block can not be placed at the top of the screen in the center, it will be placed to the side.\n\nIf the block can not be placed to the side, you lose.\n\nPress Enter to continue.");
				waitForInput("ENTER", e -> {game.start(); count = 0; stage = Stage.FINAL_STAGE; });
			}
			break;
		case FINAL_STAGE:
			if(count >= fps*.1){
				game.stop();
				display("Good Luck!.\n\nPress Enter to continue.");
				waitForInput("ENTER", e -> {game.start(); count = 0; stage = Stage.COMPLETED; });
			}
			break;
		case COMPLETED:
			break;
		default:
			break;
		}
	}
	
	private void waitForInput(String s, Consumer c){
		if(input.contains(s)){
			c.accept(null);
		}
	}
	
	private void display(String val){
		WritableImage temp = (WritableImage) display.getImage();
		PixelWriter pw = temp.getPixelWriter();
	
		//Create a center label for score display...
		int height = (int) (temp.getHeight()/2), width = (int) (temp.getWidth()/2);
		Label l = new Label(val);
		l.setWrapText(true);
		l.setPadding(new Insets(width/10));
		l.setFont(Font.font("serif", null, null, width/12));
		l.setTextAlignment(TextAlignment.CENTER);
		Scene s = new Scene(new StackPane(l), width, height);
		WritableImage img = new WritableImage(width, height);
		s.snapshot(img);
		
		pw.setPixels((int)(temp.getWidth()-width)/2, (int)(temp.getHeight()-height)/2, width, height, img.getPixelReader(), 0, 0);
		display.setImage(temp);
	}
	
	// display arrow / Shape?????

}

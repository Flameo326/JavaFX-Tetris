package Logic;

import java.util.Random;

import Block.Block;
import Block.IBlock;
import Block.JBlock;
import Block.LBlock;
import Block.OBlock;
import Block.SBlock;
import Block.TBlock;
import Block.TetrisBlock;
import Block.ZBlock;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Map {

	private Random rand;
	private Block[][] blocks;
	private Block defaultBlock;
	private TetrisBlock currentBlock, nextBlock;
	private int width, height, score;
	private boolean hasLost;
	
	public Map(int _width, int _height){
		this(_width, _height, 20, 10);
	}
	
	public Map(int _width, int _height, int rows, int columns){
		Block.setWidth(_width / columns);
		Block.setHeight(_height / rows);
		width = _width;
		height = _height;
		rand = new Random();
		blocks = new Block[rows][columns];
		// y value then x value...
		defaultBlock = new Block(Color.BLACK);
		for(int i = 0; i < blocks.length; i++){
			for(int y = 0; y < blocks[i].length; y++){
				blocks[i][y] = defaultBlock;
			}
		}
		
		setNextBlock();
		currentBlock = nextBlock;
		currentBlock.changeYPos(1);
		updateBlock();
		setNextBlock();
	}
	
	// update the block to the array.
	private void updateBlock(){
		for(int i = 0; i < currentBlock.getLength(); i++){ // x
			for(int y = 0; y < currentBlock.getLength(i); y++){ // y
				if(currentBlock.getBlock(i, y) != null){
					blocks[currentBlock.getYPos() + y][currentBlock.getXPos() + i] = currentBlock.getBlock(i, y);
				}
			}
		}
	}
	
	// Right now, it checks if the block at a certain point can move, does not check all blocks. 
	// Must change and fix to check all blocks
	public boolean canMove(int x, int y){
 		int dimension = 0;
		Integer position = 0;
		if(x > 0){		
			while((position = currentBlock.getRightBlock(dimension)) != null){
				// check if it's next to edge or if the block is colliding against another
				if((1 + position) >= blocks[dimension + currentBlock.getYPos()].length || 
						blocks[dimension + currentBlock.getYPos()][position + 1] != defaultBlock){
//					System.out.println("Error Found at: " + (position+1) + ", " + (dimension + currentBlock.getYPos()));
//					System.out.println("Dimension: " + dimension + " "  + currentBlock.getRotationAmo());
					return false;
				}
				dimension++;
			}
		} else if(x < 0){
			while((position = currentBlock.getLeftBlock(dimension)) != null){
				// check if it's next to edge or if the block is colliding against another
				if((position - 1) < 0 || 
						blocks[dimension + currentBlock.getYPos()][position - 1] != defaultBlock){
//					System.out.println("Error Found at: " + (position+1) + ", " + (dimension + currentBlock.getYPos()));
//					System.out.println("Dimension: " + dimension + " "  + currentBlock.getRotationAmo());
					return false;
				}
				dimension++;
			}
		} else if(y > 0){
			while((position = currentBlock.getBottomBlock(dimension)) != null){
				// check if it's next to edge or if the block is colliding against another
				if((position + 1) >= blocks.length || 
						blocks[position + 1][dimension + currentBlock.getXPos()] != defaultBlock){
//					System.out.println("Error Found at: " + (position+1) + ", " + (dimension + currentBlock.getYPos()));
//					System.out.println("Dimension: " + dimension + " "  + currentBlock.getRotationAmo());
					return false;
				}
				dimension++;
			}
		} else if(y < 0){
			while((position = currentBlock.getTopBlock(dimension)) != null){
				// check if it's next to edge or if the block is colliding against another
				if((position - 1) < 0 || 
						blocks[position - 1][dimension + currentBlock.getXPos()] != defaultBlock){
//					System.out.println("Error Found at: " + (position-1) + ", " + (dimension + currentBlock.getYPos()));
//					System.out.println("Dimension: " + dimension + " Rot:"  + currentBlock.getRotationAmo());
					return false;
				}
				dimension++;
			}
		}
		return true;
	}
	
	// Make rotation more friendly???
	public boolean canRotate(){
		for(int i = 0; i < currentBlock.getLength(0); i++){
			for(int y = 0; y < currentBlock.getLength(); y++){
				// check if the new block value will move off the map or if it intersects with an existing block that is not itself
				if(y+currentBlock.getYPos() >= blocks.length || i+currentBlock.getXPos() >= blocks[0].length ||
						(blocks[y+currentBlock.getYPos()][i+currentBlock.getXPos()] != defaultBlock &&
						currentBlock.getBlock(i, y) == null)){
					return false;
				}
			}
		}
		return true;
	}
	
	public void updateX(int x){
		if(canMove(x, 0)){
			resetBlock();
			currentBlock.changeXPos(x);
			updateBlock();
		}
	}
	
	public void updateY(int y){
		if(canMove(0, y)){
			resetBlock();
			currentBlock.changeYPos(y);
			updateBlock();
		} else {
			// check for win and adjust the array
			checkWin();
			currentBlock = nextBlock;
			// Will check for loss
			if(checkLoss()){
				// Updates if has not lost
				updateBlock();
				setNextBlock();
			}  else {
				// otherwise they have lost
				setLoss(true);
			}
		}
	}
	
	public void rotate(){
		if(canRotate()){
			resetBlock();
			currentBlock.rotate();
			updateBlock();
		}
	}
	
	private void checkWin(){
		boolean rowWins;
		for(int i = 0; i < currentBlock.getLength(0); i++){
			rowWins = true;
			for(int y = 0; y < blocks[0].length; y++){
				if(blocks[i+currentBlock.getYPos()][y] == defaultBlock){ rowWins = false; break; }
			}
			if(rowWins){
				removeRow(i+currentBlock.getYPos());
			}
		}
	}
	
	private boolean checkLoss(){
		boolean hasNotLost = false;
		if(canMove(0, 1)){
			hasNotLost = true;
			currentBlock.changeYPos(1);
		} else {
			int oldXPos = currentBlock.getXPos();
			currentBlock.changeXPos(-oldXPos);
			for(int i = 0; i <= blocks[0].length-currentBlock.getLength(); i++){
				if(oldXPos != i && canMove(0,  1)){
					currentBlock.changeYPos(1);
					hasNotLost = true;
					break;
				}
				currentBlock.changeXPos(1);
			}
		}
		return hasNotLost;
	}
	
	private void resetBlock(){
		for(int i = 0; i < currentBlock.getLength(); i++){ // x
			for(int e = 0; e < currentBlock.getLength(i); e++){ // y
				if(currentBlock.getBlock(i, e) != null) {
					blocks[currentBlock.getYPos() + e][currentBlock.getXPos() + i] = defaultBlock;
				};
			}
		}
	}
	
	private void removeRow(int row){
		++score;
		for(int i = 0; i < blocks[0].length; i++){
			blocks[row][i] = defaultBlock;
			for(int y = row-1; y >= 0; y--){
				blocks[y+1][i] = blocks[y][i];
				blocks[y][i] = defaultBlock;
			}
		}
	}
	
	// randomly determine next block
	public void setNextBlock(){
		int percent = rand.nextInt(7);
		if(percent == 6){
			nextBlock = new TBlock(blocks[0].length/2 - TBlock.getIntialWidth()/2, -1);
		} else if(percent == 5){
			nextBlock = new IBlock(blocks[0].length/2 - IBlock.getIntialWidth()/2, -1);
		} else if(percent == 4){
			nextBlock = new OBlock(blocks[0].length/2 - OBlock.getIntialWidth()/2, -1);
 		} else if(percent == 3) {
 			nextBlock = new JBlock(blocks[0].length/2 - JBlock.getIntialWidth()/2, -1);
 		} else if(percent == 2){
 			nextBlock = new LBlock(blocks[0].length/2 - LBlock.getIntialWidth()/2, -1);
 		} else if(percent == 1){
 			nextBlock = new SBlock(blocks[0].length/2 - SBlock.getIntialWidth()/2, -1);
 		} else {
 			nextBlock = new ZBlock(blocks[0].length/2 - ZBlock.getIntialWidth()/2, -1);
 		}
	}
	
	public void setLoss(boolean value){
		hasLost = value;
	}
	
	public Image getGameFieldImage(){
		WritableImage temp = new WritableImage(width, height);
		PixelWriter pw = temp.getPixelWriter();
		for(int i = 0; i < blocks.length; i++){
			for(int y = 0; y < blocks[i].length; y++){
				if(blocks[i][y] == null){
					System.err.println("block value is null... Should not happen " + y + " " + i);
				} else{
					pw.setPixels(y * Block.getWidth(), i * Block.getHeight(), Block.getWidth(), Block.getHeight(),
							blocks[i][y].getImage().getPixelReader(), 0, 0);
				}
			}
		}
		return temp;
	}
	
	public Image getNextBlockImage(){
		WritableImage temp = new WritableImage(nextBlock.getLength() * Block.getWidth(), nextBlock.getLength(0) * Block.getHeight());
		PixelWriter pw = temp.getPixelWriter();
		for(int i = 0; i < nextBlock.getLength(); i++){
			for(int y = 0; y < nextBlock.getLength(i); y++){
				Block b = nextBlock.getBlock(i, y);
				if(b != null){
					pw.setPixels(i * Block.getWidth(), y * Block.getHeight(), Block.getWidth(), Block.getHeight(),
						b.getImage().getPixelReader(), 0, 0);
				}
			}
		}
		return temp;
	}
	
	// Make Loss look prettier...
	public Image getGameOverImage(){
		WritableImage temp = (WritableImage) getGameFieldImage();
		PixelWriter pw = temp.getPixelWriter();
	
		//Create a center label for score display...
		int height = (int) (temp.getHeight()*.8), width = (int) (temp.getWidth()*2/3);
		Label l = new Label("Score:\n" + String.valueOf(getScore()));
		l.setMaxWidth(width); l.setMinWidth(width); l.setPrefWidth(width);
		l.setMaxHeight(height); l.setMinHeight(height); l.setPrefHeight(height);
		l.setStyle("-fx-padding: 4.5em; -fx-font: 4em serif; -fx-text-alignment: center; -fx-text-fill: #87cefa;"
				+ "-fx-background-color: #805aec; -fx-border-color: #5f30e6; -fx-border-width: .5em; ");
		Scene s = new Scene(new StackPane(l));
		WritableImage img = new WritableImage(width, height);
		s.snapshot(img);
		
		pw.setPixels((int)(temp.getWidth()-width)/2, (int)(temp.getHeight()-height)/2, (int)img.getWidth(), (int)img.getHeight(), img.getPixelReader(), 0, 0);
		return temp;
	}
	
	public boolean getLoss(){
		return hasLost;
	}
	
	public int getScore(){
		return score;
	}
}

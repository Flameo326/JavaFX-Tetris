package Logic;

import java.util.Random;

import Block.Block;
import Block.TBlock;
import Block.TetrisBlock;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Map {

	private Random rand;
	private Block[][] blocks;
	private Block defaultBlock;
	private TetrisBlock currentBlock, nextBlock;
	private int width, height;
	
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
		
		// set current and next block
		currentBlock = new TBlock(blocks[0].length/2 - TBlock.getIntialWidth()/2, 0);
		setNextBlock();
		updateBlock();
	}
	
	// update the block to the array.
	private void updateBlock(){
//		System.out.println("New Block at: " + currentBlock.getXPos() + ", " + currentBlock.getYPos());
		for(int i = 0; i < currentBlock.getLength(); i++){ // x
			for(int y = 0; y < currentBlock.getLength(i); y++){ // y
				if(currentBlock.getBlock(i, y) != null){
					blocks[currentBlock.getYPos() + y][currentBlock.getXPos() + i] = currentBlock.getBlock(i, y);
				}
			}
		}
	}
	
	// check if a block can move in the given direction
	public boolean canMove(int x, int y){
		int dimension = 0;
		int position = 0;
		if(x > 0){		
			while((position = currentBlock.getRightBlock(dimension)) != -1){
				// check if it's next to edge or if the block is colliding against another
				if((1 + position) >= blocks[dimension + currentBlock.getYPos()].length || 
						blocks[dimension + currentBlock.getYPos()][position + 1] != defaultBlock){
					System.out.println("Error Found at: " + (position+1) + ", " + (dimension + currentBlock.getYPos()));
					System.out.println("Dimension: " + dimension + " "  + currentBlock.getRotationAmo());
					return false;
				}
				dimension++;
			}
		} else if(x < 0){
			while((position = currentBlock.getLeftBlock(dimension)) != -1){
				// check if it's next to edge or if the block is colliding against another
				if((position - 1) < 0 || 
						blocks[dimension + currentBlock.getYPos()][position - 1] != defaultBlock){
					System.out.println("Error Found at: " + (position+1) + ", " + (dimension + currentBlock.getYPos()));
					System.out.println("Dimension: " + dimension + " "  + currentBlock.getRotationAmo());
					return false;
				}
				dimension++;
			}
		} else if(y > 0){
			while((position = currentBlock.getBottomBlock(dimension)) != -1){
				// check if it's next to edge or if the block is colliding against another
				if((position + 1) >= blocks.length || 
						blocks[position + 1][dimension + currentBlock.getXPos()] != defaultBlock){
					System.out.println("Error Found at: " + (position+1) + ", " + (dimension + currentBlock.getYPos()));
					System.out.println("Dimension: " + dimension + " "  + currentBlock.getRotationAmo());
					return false;
				}
				dimension++;
			}
		} else if(y < 0){
			while((position = currentBlock.getTopBlock(dimension)) != -1){
				// check if it's next to edge or if the block is colliding against another
				if((position - 1) < 0 || 
						blocks[position - 1][dimension + currentBlock.getXPos()] != defaultBlock){
					System.out.println("Error Found at: " + (position-1) + ", " + (dimension + currentBlock.getYPos()));
					System.out.println("Dimension: " + dimension + " Rot:"  + currentBlock.getRotationAmo());
					return false;
				}
				dimension++;
			}
		}
		return true;
	}
	
	public boolean canRotate(){
		for(int i = 0; i < currentBlock.getLength(0); i++){
			for(int y = 0; y < currentBlock.getLength(); y++){
				// check if the new block value will move off the map or if it intersects with an existing block that is not itself
				if(y+currentBlock.getYPos() >= blocks.length || i+currentBlock.getXPos() >= blocks[0].length ||
						(blocks[y+currentBlock.getYPos()][i+currentBlock.getXPos()] != defaultBlock &&
						currentBlock.getBlock(i, y) == null)){
					System.out.println("False");
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
	
	// since its a gui we will go down by adding positive...
	public void updateY(int y){
		if(canMove(0, y)){
			resetBlock();
			currentBlock.changeYPos(y);
			updateBlock();
		} else {
			// check for win and adjust the array
			System.out.println("Checking For Win");
			checkWin();
			currentBlock = nextBlock;
			updateBlock();
			setNextBlock();
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
				System.out.println("Block[" + (i+currentBlock.getYPos()) + "][" + y + "] is a Tetris Block");
			}
			if(rowWins){
				removeRow(i+currentBlock.getYPos());
			}
		}
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
		System.out.println("Removing Row " + row);
		for(int i = 0; i < blocks[0].length; i++){
			blocks[row][i] = defaultBlock;
			for(int y = row-1; y >= 0 && blocks[y][i] != defaultBlock; y--){
				blocks[y+1][i] = blocks[y][i];
				blocks[y][i] = defaultBlock;
			}
		}
	}
	
	// randomly determine next block
	public void setNextBlock(){
		int percent = rand.nextInt(7);
		if(percent >= 0){
			nextBlock = new TBlock(blocks[0].length/2 - TBlock.getIntialWidth()/2, 0);
		}
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
		WritableImage temp = new WritableImage(4 * Block.getWidth(), 4 * Block.getHeight());
		PixelWriter pw = temp.getPixelWriter();
		for(int i = 0; i < nextBlock.getLength(); i++){
			for(int y = 0; y < nextBlock.getLength(0); y++){
				Block b = nextBlock.getBlock(i, y);
				if(b != null){
					pw.setPixels(y * Block.getWidth(), i * Block.getHeight(), Block.getWidth(), Block.getHeight(),
						b.getImage().getPixelReader(), 0, 0);
				}
			}
		}
		return temp;
	}


}

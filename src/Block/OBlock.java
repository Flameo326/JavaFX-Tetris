package Block;

import javafx.scene.paint.Color;

public class OBlock extends TetrisBlock{
	
	public OBlock(){
		this(0, 0);
	}
	
	public OBlock(int xPos, int yPos){
		setBlockArray(new Block[][] {{new Block(Color.YELLOW), new Block(Color.YELLOW)}, {new Block(Color.YELLOW), new Block(Color.YELLOW)}});
		changeXPos(xPos);
		changeYPos(yPos);
	}
	
	public static int getIntialWidth(){
		return 2;
	}
	
	public static int getIntialHeight(){
		return 2;
	}

	// will need to adjust for rotation
	// also this will work because the right and left, y dimension can only have 2 blocks with a right side.
	@Override
	public Integer getRightBlock(int dimension) {
		Integer value = getXPos();
		if(dimension == 0 || dimension == 1){
			value += 1;
		} else {
			value = null;
		}
		return value;
	}

	@Override
	public Integer getLeftBlock(int dimension) {
		Integer value = getXPos();
		if(!(dimension == 0 || dimension == 1)){
			value = null;
		}
		return value;
	}

	// this needs to be dimensioned based in the x value
	@Override
	public Integer getBottomBlock(int dimension) {
		Integer value = getYPos();
		if(dimension == 0 || dimension == 1){
			value += 1;
		} else {
			value = null;
		}
		return value;
	}
	
	@Override
	public Integer getTopBlock(int dimension){
		Integer value = getYPos();
		if(!(dimension == 0 || dimension == 1)){
			value = null;
		}
		return value;
	}

}

package Block;

import javafx.scene.paint.Color;

public class JBlock extends TetrisBlock{

	public JBlock(){
		this(0, 0);
	}
	
	public JBlock(int xPos, int yPos){
		setBlockArray(new Block[][] {{new Block(Color.ROYALBLUE), new Block(Color.ROYALBLUE)}, {null, new Block(Color.ROYALBLUE)}, {null, new Block(Color.ROYALBLUE)}});
		changeXPos(xPos);
		changeYPos(yPos);
	}
	
	public static int getIntialWidth(){
		return 3;
	}
	
	public static int getIntialHeight(){
		return 2;
	}

	// will need to adjust for rotation
	// also this will work because the right and left, y dimension can only have 2 blocks with a right side.
	@Override
	public Integer getRightBlock(int dimension) {
		Integer value = getXPos();
		if(dimension == 0){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value += 1; } 
			else if(getRotationAmo() == 2) { value += 2; }
		} else if(dimension == 1){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value += 2; } 
			else if(getRotationAmo() == 3) { value += 1; }
		} else if(dimension == 2){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value = null; } 
			else if(getRotationAmo() == 3) { value += 1; }
		} else {
			value = null;
		}
		return value;
	}

	@Override
	public Integer getLeftBlock(int dimension) {
		Integer value = getXPos();
		if(dimension == 0){
			if(getRotationAmo() == 3) { value += 1; }
		} else if(dimension == 1){
			if(getRotationAmo() == 2) { value += 2; } 
			else if(getRotationAmo() == 3) { value += 1; }
		} else if(dimension == 2){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value = null; } 
		} else {
			value = null;
		}
		return value;
	}

	// this needs to be dimensioned based in the x value
	@Override
	public Integer getBottomBlock(int dimension) {
		Integer value = getYPos();
		if(dimension == 0){
			if(getRotationAmo() == 0) { value += 1; }
			else if(getRotationAmo() == 1 || getRotationAmo() == 3) { value += 2; }
		} else if(dimension == 1){
			if(getRotationAmo() == 0) { value += 1; } 
			else if(getRotationAmo() == 3) { value += 2; }
		} else if(dimension == 2){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value = null; } 
			else { value += 1; }
		} else {
			value = null;
		}
		return value;
	}
	
	@Override
	public Integer getTopBlock(int dimension){
		Integer value = getYPos();
		if(dimension == 0){
			if(getRotationAmo() == 3) { value += 2; }
		} else if(dimension == 1){
			if(getRotationAmo() == 0) { value += 1; } 
		} else if(dimension == 2){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value = null; } 
			else if(getRotationAmo() == 2) { value += 1; } 
		} else {
			value = null;
		}
		return value;
	}
	
}

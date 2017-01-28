package Block;

import javafx.scene.paint.Color;

public class IBlock extends TetrisBlock{
	
	public IBlock(){
		this(0, 0);
	}
	
	public IBlock(int xPos, int yPos){
		setBlockArray(new Block[][] {{new Block(Color.AQUA)}, {new Block(Color.AQUA)}, {new Block(Color.AQUA)}, {new Block(Color.AQUA)}});
		changeXPos(xPos);
		changeYPos(yPos);
	}
	
	public static int getIntialWidth(){
		return 4;
	}
	
	public static int getIntialHeight(){
		return 1;
	}

	@Override
	public Integer getRightBlock(int dimension) {
		Integer value = getXPos();
		if(dimension == 0){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value += 3; } 
		} else if(dimension == 1){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value = null; } 
		} else if(dimension == 2){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value = null; }
		}  else if(dimension == 3){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value = null; }
		} else {
			value = null;
		}
		return value;
	}

	@Override
	public Integer getLeftBlock(int dimension) {
		Integer value = getXPos();
		if(dimension == 0){ } // unnecesary Call due to else statement...
		else if(dimension == 1){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value = null; } 
		} else if(dimension == 2){
			if(getRotationAmo() == 0 || getRotationAmo() == 2) { value = null; }
		}  else if(dimension == 3){
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
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value += 3; } 
		} else if(dimension == 1){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value = null; } 
		} else if(dimension == 2){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value = null; } 
		} else if(dimension == 3){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value = null; } 
		} else {
			value = null;
		}
		return value;
	}
	
	@Override
	public Integer getTopBlock(int dimension){
		Integer value = getYPos();
		if(dimension == 0){ } // unnecesary Call due to else statement...
		else if(dimension == 1){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value = null; } 
		} else if(dimension == 2){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value = null; } 
		} else if(dimension == 3){
			if(getRotationAmo() == 1 || getRotationAmo() == 3) { value = null; } 
		} else {
			value = null;
		}
		return value;
	}

}

package Block;

public abstract class TetrisBlock {
	
	private Block[][] block;
	private int x, y, rotationAmo;
	
	protected void setBlockArray(Block[][] array){
		block = array;
	}
	
	public void changeXPos(int xAmo){
		x += xAmo;
	}
	
	public void changeYPos(int yAmo){
		y += yAmo;
	}
	
	// rotate clockwise
	public void rotate(){
		Block[][] temp = new Block[block[0].length][block.length];
		for(int i = 0; i < temp.length; i++){
			for(int y = 0; y < temp[0].length; y++){
				// 1, 2, 3
				// 4, 5, 6
				// ->
				// 4, 1
				// 5, 2
				// 6, 3
				temp[i][y] = block[y][temp.length - 1 - i];
			}
		}
		block = temp;
		if(++rotationAmo >= 4) { rotationAmo = 0; }
	} 
	
	public Block getBlock(int dim1, int dim2){
		if(dim1 >= block.length || dim2 >= block[0].length){ return null; }
		return block[dim1][dim2];
	}
	
	public abstract int getRightBlock(int dimension);
	
	public abstract int getLeftBlock(int dimension);
	
	public abstract int getBottomBlock(int dimension);
	
	public abstract int getTopBlock(int dimension);
	
	public int getXPos(){
		return x;
	}
	
	public int getYPos(){
		return y;
	}
	
	// width
	public int getLength(){
		return block.length;
	}
	
	// height
	public int getLength(int pos){
		return block[pos].length;
	}
	
	public int getRotationAmo(){
		return rotationAmo;
	}
	
	public static int getIntialWidth(){
		return -1;
	}
	
	public static int getIntialHeight(){
		return -1;
	}

}

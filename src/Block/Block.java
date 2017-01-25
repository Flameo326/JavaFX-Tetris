package Block;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Block {
	
	private static int width = 50, height = 50;
	
	private Color color;
	private Image img;

	public Block(Color c) {
		color = c;
		setImage();
	}
	
	public static void setWidth(int _width){
		width = _width;
	}
	
	public static void setHeight(int _height){
		height = _height;
	}
	
	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}
	
	private void setImage(){
		WritableImage temp = new WritableImage(width, height);
		PixelWriter pw = temp.getPixelWriter();
		for(int i = 0; i < width; i++){
			for(int y = 0; y < height; y++){
				if(i == 0 || y == 0 || i+1 == width || y+1 == height){
					if(color == Color.BLACK) { pw.setColor(i, y, Color.WHITE); }
					else { pw.setColor(i, y, Color.BLACK); }
				} else { pw.setColor(i, y, color); }
			}
		}
		img = temp;
	}
	
	public Image getImage(){
		return img;
	}

	@Override
	public String toString(){
		return color.toString();
	}
}

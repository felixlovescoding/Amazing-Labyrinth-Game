package Model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO; 

public class Player {

	
	//Fields
	private int row;
	private int column;
	private int treasuresFound;
	private BufferedImage image;
	
	
	//Constructor
	public Player(int row, int column, BufferedImage image) {
		
		setRow(row);
		setColumn(column);
		setImage(image);
	}


	//Getters and Setters
	public int getRow() {
		return row;
	}
	public void setRow(int row) {

		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {

		this.column = column;
	}
	public int getTreasuresFound() {
		return treasuresFound;
	}
	public void setTreasuresFound(int treasuresFound) {
		this.treasuresFound = treasuresFound;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	// Gets image as buffered image
	public BufferedImage getImg(String fileName) {
		try {
			return ImageIO.read(new File(fileName));
		} catch (Exception ex) {
			System.out.println("cannot find file " + fileName);
		}
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	} 	
	
}

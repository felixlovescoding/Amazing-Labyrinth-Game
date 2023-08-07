package Model;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Tile {

	
	//Fields
	private String name;	//Name of tile
	private int row;	//Row of tile
	private int column;	//Column of tile
	private char pieceType; //T, L, or I tile
	private int rotation;
	
	
	//Constructor
	public Tile(String name) {
		
		this.name = name; 
	}
	
	//Getters and Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public char getPieceType() {
		return pieceType;
	}
	public void setPieceType(char pieceType) {
		
		this.pieceType = pieceType;
	}
	public int getRotation() {
		return rotation;
	}
	public void setRotation(int rotation) {
		this.rotation = rotation;
	}
	
	
	
	
	//Utility Methods
	//Returns the image in the user's desired rotation
	public String getImage() {
		
		return ("images/"+name+rotation+".png");

	}
	public String fileName(){//returns file name of pic, added by Maurice for some displaying method
		return "images/"+getName()+rotation+".png";
		
	}
}

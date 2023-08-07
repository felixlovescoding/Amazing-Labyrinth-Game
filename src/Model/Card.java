package Model;

public class Card {

	
	//Fields
	private String name;	//Name of the treasure
	private boolean treasure;	//If user has obtained the treasure
	private int row;	//Row location of the current treasure
	private int column;	//Column location of the current treasure
	private int player;	//Player number to determine which player owns this card
		
	
	//Constructor
	public Card(String name, int row, int column) {
		
		this.name = name;
		 
		//No validation for row and column as a treasure can be the free tile or not in play
		this.row = row;
		this.column = column;
		
		//Set default player to -1
		setPlayer(-1);
		
	}


	//Getters and Setters
	public String getFileName() {
		return "images/Card"+name+".png";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isTreasure() {
		return treasure;
	}
	public void setTreasure(boolean treasure) {
		this.treasure = treasure;
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
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
	
}

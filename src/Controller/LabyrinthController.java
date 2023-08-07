package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Model.*;
import View.*;

/*
 * Labyrinth Controller Class
 *
 */

public class LabyrinthController implements ActionListener {

	// Fields
	private LabyrinthStartFrame startFrame; // Start Frame
	private LabyrinthGameFrame gameFrame; // Game Frame
	private boolean moveTile = false; // Keeps track if player has moved tiles
	private int currentPlayer = 0; // Keeps track of the current player
	private Player[] players; // Player array
	private Card[][] playerCards = new Card[4][6]; // Cards of each player
	private int lastButton = -1; // last button user has pressed

	// Constructor
	public LabyrinthController() {

		// Create the start frame
		startFrame = new LabyrinthStartFrame();

		// Add ActionListener to buttons
		startFrame.getPlayGame().addActionListener(this);
		startFrame.getPlaySavedGame().addActionListener(this);
	}

	// Happy
	// Shuffles the cards and deals them out to players
	private void shuffleAndDealCards(int amount) {

		// Get the cards from the startFrame
		ArrayList<Card> cards = startFrame.getCards();

		// Shuffle cards
		Collections.shuffle(cards);

		// Deal cards
		for (int i = 0; i < 6; i++) {

			playerCards[0][i] = cards.get(0);
			playerCards[0][i].setPlayer(0);
			cards.remove(0);
			playerCards[1][i] = cards.get(0);
			playerCards[1][i].setPlayer(1);
			cards.remove(0);
			playerCards[2][i] = cards.get(0);
			playerCards[2][i].setPlayer(2);
			cards.remove(0);
			playerCards[3][i] = cards.get(0);
			playerCards[3][i].setPlayer(3);
			cards.remove(0);

		}

		// Remove treasures from player
		for (int i = 0; i < 6 - amount; i++) {

			playerCards[0][i].setTreasure(true);
			playerCards[1][i].setTreasure(true);
			playerCards[2][i].setTreasure(true);
			playerCards[3][i].setTreasure(true);
		}

		// Set treasures found for each player
		for (int i = 0; i < 4; i++) {
			players[i].setTreasuresFound(6 - amount);
		}
	}

	private void dealCards() {

		ArrayList<Card> cards = startFrame.getCards();
		int counter0 = 0;
		int counter1 = 0;
		int counter2 = 0;
		int counter3 = 0;

		for (int i = 0; i < 24; i++) {
			if (cards.get(i).getPlayer() == 0) {
				playerCards[0][counter0] = cards.get(i);
				counter0++;
			} else if (cards.get(i).getPlayer() == 1) {
				playerCards[1][counter1] = cards.get(i);
				counter1++;
			} else if (cards.get(i).getPlayer() == 2) {
				playerCards[2][counter2] = cards.get(i);
				counter2++;
			} else if (cards.get(i).getPlayer() == 3) {
				playerCards[3][counter3] = cards.get(i);
				counter3++;
			}

		}

	}

	// Frank
	// Sets up all buttons within the game frame
	private void setUpButtons() {

		// ControlPanel
		// Add ActionListener to the control panel buttons
		LabyrinthControlPanel.getEndTurnButton().addActionListener(this);
		LabyrinthControlPanel.getSaveGameButton().addActionListener(this);
		for (int i = 0; i < 4; i++) {
			LabyrinthControlPanel.getMovePlayerButton()[i].addActionListener(this);
		}

		// BoardPanel
		// Add ActionlListener to insert tile buttons
		for (int i = 0; i < 12; i++) {
			LabyrinthBoardPanel.getInsertTileButton().get(i).addActionListener(this);
		}

		// FreeTilePanel
		LabyrinthFreeTilePanel.getRotateTileButtonL().addActionListener(this);
		LabyrinthFreeTilePanel.getRotateTileButtonR().addActionListener(this);
	}

	// Maurice
	// Generates random integer
	public static int randomInt(int min, int max) {
		return ((int) Math.round(Math.random() * (max - min) + min));
	}

	// Frank
	// Checks if the player has landed on a treasure tile
	public boolean checkTreasure(Tile[][] board) {

		int row = players[currentPlayer].getRow();
		int column = players[currentPlayer].getColumn();

		String name = board[row][column].getName();

		for (int i = 0; i < 6; i++) {

			if (playerCards[currentPlayer][i].getName().equals(name)) {

				if (playerCards[currentPlayer][i].isTreasure() == false) {
					JOptionPane.showMessageDialog(new JFrame(""), "YOU FOUND A TREASURE");
				}

				playerCards[currentPlayer][i].setTreasure(true);
				players[currentPlayer].setTreasuresFound(players[currentPlayer].getTreasuresFound() + 1);

				if (players[currentPlayer].getTreasuresFound() == 6) {

					JOptionPane.showMessageDialog(new JFrame(""), "Game Over");

				}
			}
		}
		return false;
	}

	// Frank
	// Checks if the move made by player is valid
	// moves player if the move is valid
	public boolean checkMove(Tile[][] board, Player player, int direction) {

		int dRow = 0;
		int dColumn = 0;

		// Directions
		if (direction == 0) { // Forward
			dRow = -1;
			dColumn = 0;
		} else if (direction == 1) { // Right
			dRow = 0;
			dColumn = 1;
		} else if (direction == 2) { // Down
			dRow = 1;
			dColumn = 0;
		} else { // Left
			dRow = 0;
			dColumn = -1;
		}

		// Check if row or column is off the map
		if ((player.getRow() + dRow) > 6 || (player.getRow() + dRow) < 0 || (player.getColumn() + dColumn) > 6
				|| (player.getColumn() + dColumn) < 0) {
			return false;
		}

		// Check if move is valid
		if (checkNextTile(board, player, direction, dRow, dColumn)) {

			// Move player
			players[currentPlayer].setRow(players[currentPlayer].getRow() + dRow);
			players[currentPlayer].setColumn(players[currentPlayer].getColumn() + dColumn);
			return true;
		}
		return false;
	}

	// Happy
	// Moves the player if player is on a tile being moved
	private void movePlayerOnTile(Player[] player, int direction, int rowcolumn) {

		// Check all players
		for (int i = 0; i < 4; i++) {
			// Move up
			if (direction == 0) {
				if (player[i].getColumn() == rowcolumn) {
					player[i].setRow(player[i].getRow() - 1);
					if (player[i].getRow() == -1) {
						player[i].setRow(6);
					}
				}
			}
			// move left
			else if (direction == 1) {
				if (player[i].getRow() == rowcolumn) {
					player[i].setColumn(player[i].getColumn() - 1);
					if (player[i].getColumn() == -1) {
						player[i].setColumn(6);
					}
				}
			}
			// move down
			else if (direction == 2) {
				if (player[i].getColumn() == rowcolumn) {
					player[i].setRow(player[i].getRow() + 1);
					if (player[i].getRow() == 7) {
						player[i].setRow(0);
					}
				}
			}
			// move right
			else if (direction == 3) {
				if (player[i].getRow() == rowcolumn) {
					player[i].setColumn(player[i].getColumn() + 1);
					if (player[i].getColumn() == 7) {
						player[i].setColumn(0);
					}
				}
			}
		}

	}

	// Happy
	// Moves the tiles in the tile board matrix
	private Tile[][] moveTiles(Tile[][] board, int direction, int rowcolumn) {

		// temporary tile holder
		Tile temp;

		// Move up
		if (direction == 0) {

			temp = board[0][rowcolumn]; // Get the temporary tile

			for (int i = 1; i < 7; i++) { // Shift Tiles
				board[i - 1][rowcolumn] = board[i][rowcolumn];
			}

			board[6][rowcolumn] = startFrame.getFreeTile(); // Put in the free tile
			startFrame.setFreeTile(temp); // Change the free tile
		}
		// Moving down
		else if (direction == 2) {

			temp = board[6][rowcolumn]; // Get the temporary tile
			for (int i = 6; i > 0; i--) { // Shift Tiles
				board[i][rowcolumn] = board[i - 1][rowcolumn];
			}

			board[0][rowcolumn] = startFrame.getFreeTile(); // Put in the free tile
			startFrame.setFreeTile(temp); // Change the free tile
		}

		// Moving left
		else if (direction == 1) {

			temp = board[rowcolumn][0]; // Get the temporary tile
			for (int i = 1; i < 7; i++) { // Shift Tiles
				board[rowcolumn][i - 1] = board[rowcolumn][i];
			}

			board[rowcolumn][6] = startFrame.getFreeTile(); // Put in the free tile
			startFrame.setFreeTile(temp); // Change the free tile
		}

		// Moving right
		else if (direction == 3) {

			temp = board[rowcolumn][6]; // Get the temporary tile
			for (int i = 6; i > 0; i--) { // Shift Tiles
				board[rowcolumn][i] = board[rowcolumn][i - 1];
			}

			board[rowcolumn][0] = startFrame.getFreeTile(); // Put in the free tile
			startFrame.setFreeTile(temp); // Change the free tile
		}

		// Adjust the board
		//startFrame.setTileBoardMatrix(board);
		return board;

	}

	// Frank
	// Checks if the player can move away from its current tile in a specific
	// direection
	private boolean checkCurrentTile(Tile[][] board, Player player, int direction) {
		Tile tile = board[player.getRow()][player.getColumn()];
		char pieceType = tile.getPieceType();
		int rotation = tile.getRotation();

		if (direction == 0) { // Up
			if (pieceType == 'T') {
				if (rotation == 0) {
					return false;
				}
			} else if (pieceType == 'L') {
				if (rotation == 1 || rotation == 2) {
					return false;
				}
			} else if (pieceType == 'I') {
				if (rotation == 1 || rotation == 3) {
					return false;
				}
			}
		} else if (direction == 1) {
			if (pieceType == 'T') {
				if (rotation == 1) {
					return false;
				}
			} else if (pieceType == 'L') {
				if (rotation == 2 || rotation == 3) {
					return false;
				}
			} else if (pieceType == 'I') {
				if (rotation == 0 || rotation == 2) {
					return false;
				}
			}
		} else if (direction == 2) {
			if (pieceType == 'T') {
				if (rotation == 2) {
					return false;
				}
			} else if (pieceType == 'L') {
				if (rotation == 0 || rotation == 3) {
					return false;
				}
			} else if (pieceType == 'I') {
				if (rotation == 1 || rotation == 3) {
					return false;
				}
			}
		} else if (direction == 3) {
			if (pieceType == 'T') {
				if (rotation == 3) {
					return false;
				}
			} else if (pieceType == 'L') {
				if (rotation == 0 || rotation == 1) {
					return false;
				}
			} else if (pieceType == 'I') {
				if (rotation == 0 || rotation == 2) {
					return false;
				}
			}
		}
		return true;
	}

	// Frank
	// Checks if the next tile the player intends on moving to is valid
	private boolean checkNextTile(Tile[][] board, Player player, int direction, int dRow, int dColumn) {

		// Find the next tile
		Tile tile = board[player.getRow() + dRow][player.getColumn() + dColumn];

		char pieceType = tile.getPieceType(); // Get the piece type
		int rotation = tile.getRotation(); // Get the rotation

		// Check the next tile
		if (dColumn == -1) { // Check left
			if (pieceType == 'T') { // Check piece type
				if (rotation == 1) {
					return false;
				}
			} else if (pieceType == 'L') {
				if (rotation == 2 || rotation == 3) {
					return false;
				}
			} else if (pieceType == 'I') {
				if (rotation == 0 || rotation == 2) {
					return false;
				}
			}
		} else if (dColumn == 1) { // Check right
			if (pieceType == 'T') {
				if (rotation == 3) {
					return false;
				}
			} else if (pieceType == 'L') {
				if (rotation == 0 || rotation == 1) {
					return false;
				}
			} else if (pieceType == 'I') {
				if (rotation == 0 || rotation == 2) {
					return false;
				}
			}
		} else if (dRow == -1) { // Check Up
			if (pieceType == 'T') {
				if (rotation == 2) {
					return false;
				}
			} else if (pieceType == 'L') {
				if (rotation == 0 || rotation == 3) {
					return false;
				}
			} else if (pieceType == 'I') {
				if (rotation == 1 || rotation == 3) {
					return false;
				}
			}
		} else if (dRow == 1) { // Check down
			if (pieceType == 'T') {
				if (rotation == 0) {
					return false;
				}
			} else if (pieceType == 'L') {
				if (rotation == 1 || rotation == 2) {
					return false;
				}
			} else if (pieceType == 'I') {
				if (rotation == 1 || rotation == 3) {
					return false;
				}
			}
		}

		// Return true when all checks are passed
		return true;
	}

	// Frank
	// displays the instructions on how to play the game
	private void displayInstructions() {

		// Display instructions
		JOptionPane.showMessageDialog(new JFrame(""),
				"Object of the Game\r\n" + "In this enchanted labyrinth players set out to search\r\n"
						+ "for mysterious objects and creatures. By cleverly sliding\r\n"
						+ "the paths players try to find their way to the coveted\r\n" + "treasure.\r\n"
						+ "The first player to find all their treasures and return to\r\n"
						+ "the starting square is the winner");

		JOptionPane.showMessageDialog(new JFrame(""),
				"How to Play\r\n" + "Each player looks at the first card of his stack of treasure\r\n"
						+ "cards without showing it to the other players. Now you\r\n"
						+ "try to get to the square showing the same treasure as on\r\n" + "your card.\r\n"
						+ "The last player to go on a treasure hunt goes first with\r\n"
						+ "play continuing in a clockwise direction.\r\n" + "A turn is always made up of two steps:\r\n"
						+ "1. Move the maze\r\n" + "2. Move your playing piece\r\n"
						+ "On your turn, try to move your playing piece to the\r\n"
						+ "treasure in the labyrinth showing on your card. First,\r\n"
						+ "insert the path tile lying next to the game board and\r\n"
						+ "then move your piece on the board");

		JOptionPane.showMessageDialog(new JFrame(""),
				"1. Moving the Maze\r\n" + "There are 12 arrows along the edge of the board. They\r\n"
						+ "are marking the rows where you can insert the path tile\r\n" + "into the maze.\r\n"
						+ "On your turn, insert the extra path tile into the game\r\n"
						+ "board where one of the arrows is, until another path tile\r\n"
						+ "is pushed out of the maze on the opposite side.");

		JOptionPane.showMessageDialog(new JFrame(""),
				"2. Moving Your Playing Piece\r\n" + "Once you have moved the maze, you can move your\r\n"
						+ "playing piece. You can occupy any square that you can\r\n"
						+ "move your piece to directly, without interruption. You\r\n"
						+ "can move your playing piece as far as you like. Or, you\r\n"
						+ "can leave your playing piece where it is.");

		JOptionPane.showMessageDialog(new JFrame(""),
				"Ending the Game\n" + "Game ends when one player abtains all their treasures first");

		JOptionPane.showMessageDialog(new JFrame(""), "GOOD LUCK!");
	}

	// Frank
	// Saves the current player and the cards in the game to the save file
	private void saveCards() {

		// Save information about cards
		try {

			// Writes onto the card save file
			PrintWriter output = new PrintWriter("files/SavedCards.csv");

			// Save the current player
			output.write(String.valueOf(currentPlayer + "\n"));

			// Transfer information from the cards array
			for (int i = 0; i < 4; i++) {
				for (int t = 0; t < 6; t++) {

					output.write(String.format("%s,%d,%d,%b,%d\n", playerCards[i][t].getName(),
							playerCards[i][t].getRow(), playerCards[i][t].getColumn(), playerCards[i][t].isTreasure(),
							playerCards[i][t].getPlayer()));
				}
			}

			// Close print writer
			output.close();

		} catch (IOException e) {

			System.out.println("Error Saving Cards");
		}

	}

	// Frank
	// Saves the current player and the cards in the game to the save file
	private void saveBoard() {

		Tile tile;
		String name;
		char pieceType;
		int rotation;
		boolean movable;
		int row;
		int column;
		
		try {
			PrintWriter board = new PrintWriter("files/SavedTreasures.csv");

			for (int i = 0; i < 7; i++) {

				for (int j = 0; j < 7; j++) {

					if (i % 2 == 0 && j % 2 == 0) {
						continue;
					} // skip the static tiles
					tile = gameFrame.getBoardPanel().getTileBoardMatrix()[i][j];
					name = tile.getName();
					pieceType = tile.getPieceType();
					rotation = tile.getRotation();
					movable = true; // no use

					//Save game tile
					board.write(String.format("%s,%c,%d,%b,%d,%d,\n", name, pieceType, rotation, movable, i, j));

				}
			}

			name = startFrame.getFreeTile().getName();
			pieceType = startFrame.getFreeTile().getPieceType();
			rotation = startFrame.getFreeTile().getRotation();
			movable = true;
			row = -2;
			column = -2;

			board.write(String.format("%s,%c,%d,%b,%d,%d,\n", name, pieceType, rotation, movable, row, column));

			for (int i = 0; i < 4; i++) {

				board.write(String.format("Player%d,L,%d,false,%d,%d\n", i, players[i].getTreasuresFound(),
						players[i].getRow(), players[i].getColumn()));
			}

			board.close();
			
		} catch (IOException e) {

			System.out.println("Error Saving Board");
		}
	}

	// Frank
	// Action performed method
	@Override
	public void actionPerformed(ActionEvent event) {

		// Check if input is from the start frame
		if (event.getSource() == startFrame.getPlayGame() || event.getSource() == startFrame.getPlaySavedGame()) {

			// Check if user has decided to play a new game
			if (event.getSource() == startFrame.getPlayGame()) {

				// Create board from treasures and cards files
				startFrame.createBoard("files/Treasures.csv");
				currentPlayer = startFrame.createCards("files/Cards.csv");

			}

			// Check if user has decided to play a saved game
			else {

				// Create board from the saved treasures and saved cards files
				startFrame.createBoard("files/SavedTreasures.csv");
				currentPlayer = startFrame.createCards("files/SavedCards.csv");
			}

			// Start up game
			startFrame.setVisible(false); // Close the start up screen
			// Set up players
			this.players = startFrame.getPlayers();
			// Create game frame
			gameFrame = new LabyrinthGameFrame(startFrame.getTileBoardMatrix(), players);
			setUpButtons(); // Set up buttons
			// Display the free tile
			LabyrinthFreeTilePanel.displayFreeTile(startFrame.getFreeTile());
			// Deal cards
			if (event.getSource() == startFrame.getPlayGame()) {

				shuffleAndDealCards(Integer.parseInt((String) startFrame.getCardsAGame().getSelectedItem()));
			} else {
				dealCards();
			}
			// Display game instructions
			//displayInstructions();

		}

		// Check if user has decided to end game
		if (event.getSource() == LabyrinthControlPanel.getSaveGameButton()) {
			
			// Save cards and treasures
			// Cards in the savedCards file
			// Reads the player cards array
			saveCards();

			// Treasures in the saved treasures file
			// Reads the board
			saveBoard();
			
			return;
		}

		// Check if user has rotated the tile left (clockwise)
		else if (event.getSource() == LabyrinthFreeTilePanel.getRotateTileButtonL()) {

			// Rotate tile
			startFrame.getFreeTile().setRotation(startFrame.getFreeTile().getRotation() + 1);

			// Check if rotate is valid
			if (startFrame.getFreeTile().getRotation() == 4) {

				// Change back to first rotation
				startFrame.getFreeTile().setRotation(0);
			}

			// Display the free tile with new rotation
			LabyrinthFreeTilePanel.displayFreeTile(startFrame.getFreeTile());
		}

		// Check if user has rotated the tile right (anti clockwise)
		else if (event.getSource() == LabyrinthFreeTilePanel.getRotateTileButtonR()) {
			// Rotate tile
			startFrame.getFreeTile().setRotation(startFrame.getFreeTile().getRotation() - 1);

			// Check if rotate is valid
			if (startFrame.getFreeTile().getRotation() == -1) {

				// Change back to first rotation
				startFrame.getFreeTile().setRotation(3);
			}

			// Display the free tile with new rotation
			LabyrinthFreeTilePanel.displayFreeTile(startFrame.getFreeTile());
		}

		// Check if user has decided to end their turn
		else if (event.getSource() == LabyrinthControlPanel.getEndTurnButton()) {

			// Switch to next player
			currentPlayer += 1;

			// Rotate back to player 0
			if (currentPlayer == 4) {
				currentPlayer = 0;
			}

			// Change the current player display
			LabyrinthControlPanel.changePlayer(currentPlayer);

			// Next player has yet to move tile
			moveTile = false;
		}

		// Check if player has moved a tile
		if (moveTile) {

			// Check if user has moved their player
			for (int i = 0; i < 4; i++) {

				// Check which move player button the user has pressed
				if (event.getSource() == LabyrinthControlPanel.getMovePlayerButton()[i]) {

					// Check if move is valid
					if (!checkCurrentTile(startFrame.getTileBoardMatrix(), players[currentPlayer], i)
							|| !checkMove(startFrame.getTileBoardMatrix(), players[currentPlayer], i)) {

						// Display message
						JOptionPane.showMessageDialog(new JFrame(""), "Invalid Move");

					} else {

						// Check for treasures if move was valid
						checkTreasure(startFrame.getTileBoardMatrix());
					}

					// Update the game board GUI
					gameFrame.getBoardPanel().displayGameBoard(startFrame.getTileBoardMatrix(), players);
					// Check if player has landed on a treasure tile

				}
			}
		}

		// Check if tile has been moved
		if (!moveTile) {

			// Check if user has inserted a tile down
			for (int i = 0; i < 3; i++) {
				// Check the move tiles down buttons
				if (event.getSource() == LabyrinthBoardPanel.getInsertTileButton().get(i)) { // (0-2)

					// Move tiles down
					// the board matrix
					//gameFrame.getBoardPanel().displayMovement(i * 2 + 1, 's');
					startFrame.setTileBoardMatrix(moveTiles(startFrame.getTileBoardMatrix(), 2, i * 2 + 1)); // Move tiles in
					movePlayerOnTile(players, 2, i * 2 + 1);
					moveTile = true; // Player has moved tile
				}
				// Check the move tiles left buttons
				else if (event.getSource() == LabyrinthBoardPanel.getInsertTileButton().get(i + 3)) { // (3-5)

					// Move tiles left
					
					//gameFrame.getBoardPanel().displayMovement(i * 2 + 1, 'w',startFrame.getFreeTile());
					
					gameFrame.getBoardPanel().displayMovement(i * 2 + 1, 'w',gameFrame.getBoardPanel().getTileBoardMatrix(),players);
					//startFrame.setTileBoardMatrix(moveTiles(startFrame.getTileBoardMatrix(), 1, i * 2 + 1)); // Move tiles in the board matrix
					moveTile = true;
					movePlayerOnTile(players, 1, i * 2 + 1);
					startFrame.setTileBoardMatrix(moveTiles(startFrame.getTileBoardMatrix(), 1, i * 2 + 1)); // Move tiles in the board matrix
					gameFrame.getBoardPanel().displayGameBoard(startFrame.getTileBoardMatrix(), players);
				}
				// Check the move tiles up button
				else if (event.getSource() == LabyrinthBoardPanel.getInsertTileButton().get(i + 6)) { // (6-8)

					// Move tiles up
					//gameFrame.getBoardPanel().displayMovement(i * 2 + 1, 'n');
					startFrame.setTileBoardMatrix(moveTiles(startFrame.getTileBoardMatrix(), 0, i * 2 + 1)); // Move tiles in the board matrix
					movePlayerOnTile(players, 0, i * 2 + 1);
					moveTile = true;
				}
				// Check the move tiles right button
				else if (event.getSource() == LabyrinthBoardPanel.getInsertTileButton().get(i + 9)) { // (9-11)

					// Move tiles right
					//gameFrame.getBoardPanel().displayMovement(i * 2 + 1, 'e');
					startFrame.setTileBoardMatrix(moveTiles(startFrame.getTileBoardMatrix(), 3, i * 2 + 1)); // Move tiles in the board matrix
					movePlayerOnTile(players, 3, i * 2 + 1);
					moveTile = true;
				}
			}

		}		
		
		for (int j = 0; j < 4; j++) {
			gameFrame.getPlayerPanel()[j].displayCards(playerCards[j]);
		}
		// Display the free tile on the free tile panel
		LabyrinthFreeTilePanel.displayFreeTile(startFrame.getFreeTile());

		// Update the game board GUI
		gameFrame.getBoardPanel().displayGameBoard(startFrame.getTileBoardMatrix(), players);

		// Set the players in the board panel class
		gameFrame.getBoardPanel().setPlayers(players);

	}

}

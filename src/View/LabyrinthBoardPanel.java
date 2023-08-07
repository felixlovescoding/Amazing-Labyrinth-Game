package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Model.*;

public class LabyrinthBoardPanel extends JPanel {

	
	
	
	// Fields
	public static ArrayList<JButton> insertTileButton = new ArrayList<JButton>();

	// Fields
	Tile tileBoardMatrix[][];
	JLabel boardLabel = new JLabel();
	Player players[];
	ScheduledExecutorService exec= Executors.newSingleThreadScheduledExecutor();
	
	
	public Tile[][] getTileBoardMatrix() {
		return tileBoardMatrix;
	}


	public void setTileBoardMatrix(Tile[][] tileBoardMatrix) {
		this.tileBoardMatrix = tileBoardMatrix;
	}


	// Constructor
	public LabyrinthBoardPanel(Tile[][] board, Player[] players) {

		tileBoardMatrix = board;
		setBoard();
		//
		this.players = players;
		displayGameBoard(board, this.players);
		
	}
	
	
	public Player[] getPlayers() {
		return players;
	}


	public void setPlayers(Player[] players) {
		this.players = players;
	}
	
	public static ArrayList<JButton> getInsertTileButton() {
		return insertTileButton;
	}

	public static void setInsertTileButton(ArrayList<JButton> insertTileButton) {
		LabyrinthBoardPanel.insertTileButton = insertTileButton;
	}

	BufferedImage bi_board = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);
	Graphics g = bi_board.getGraphics();

	private void setBoard() {

		this.setLayout(null);
		boardLabel.setPreferredSize(new Dimension(750, 750));
		boardLabel.setBounds(0, 0, 700, 700);
		boardLabel.setVisible(true);

		// Add buttons
		this.add(boardLabel);
		//this.setPreferredSize(new Dimension(1300, 800));
		for (int i = 0; i < 3; i++) {
			insertTileButton.add(new JButton(new ImageIcon(getImg("images/board/down.png"))));
			insertTileButton.get(insertTileButton.size() - 1).setBounds(192 * (i) + 110, 3, 96, 30);
			this.add(insertTileButton.get(insertTileButton.size() - 1));
		}
		for (int i = 0; i < 3; i++) {
			insertTileButton.add(new JButton(new ImageIcon(getImg("images/board/left.png"))));
			insertTileButton.get(insertTileButton.size() - 1).setBounds(670, 192 * (i) + 110, 30, 96);
			this.add(insertTileButton.get(insertTileButton.size() - 1));
		}
		for (int i = 0; i < 3; i++) {
			insertTileButton.add(new JButton(new ImageIcon(getImg("images/board/up.png"))));
			insertTileButton.get(insertTileButton.size() - 1).setBounds(192 * (i) + 110, 670, 96, 30);
			this.add(insertTileButton.get(insertTileButton.size() - 1));
		}
		for (int i = 0; i < 3; i++) {
			insertTileButton.add(new JButton(new ImageIcon(getImg("images/board/right.png"))));
			insertTileButton.get(insertTileButton.size() - 1).setBounds(3, 192 * (i) + 110, 30, 96);
			this.add(insertTileButton.get(insertTileButton.size() - 1));
		}
		this.add(boardLabel);
	}

	// Displays teh game board
	public void displayGameBoard(Tile tiles[][], Player[] players) {// updates game panel
		displayGameBoard(tiles, players, -1);
	}

	// Displays teh game board
	public void displayGameBoard(Tile tiles[][], Player[] players, int ignoreRow) {// internal use only, ignoreRow does

		g.setColor(new Color(7, 55, 99));
		g.fillRect(0, 0, 700, 700);
		g.drawImage(getImg("images/GameBoard.png"), 14, 14, null);
		displayTiles(tiles, ignoreRow);
		displayPlayers(players, ignoreRow);
		// displays players at the center of the tile
		boardLabel.setIcon(new ImageIcon(bi_board));
	}

	// Maurice
	// Display all tiles
	private void displayTiles(Tile tiles[][], int ignoreRow) {
		g.setColor(new Color(7, 55, 99));
		g.fillRect(0, 0, 700, 700);
		g.drawImage(getImg("images/GameBoard.png"), 14, 14, null);
		for (int i = 0; i < 7; i++) { // might have to swap variables i and j
			for (int j = 0; j < 7; j++) {
				if(i%2==0&&j%2==0){continue;}//skip the static tiles
				if (ignoreRow > 6 && i == ignoreRow - 7) {
					continue;
				}
				if (j == ignoreRow) {
					continue;
				}
				g.drawImage(getImg(tiles[j][i].fileName()), 14 + i * 96, j * 96 + 14, null);
			}
		}
		
	}
	// Method to display all 4 players
	public void displayPlayers(Player[] players, int ignoreRow) {
		for (int i = 0; i < 4; i++) {
			if (players[i].getRow() == ignoreRow) {
				continue; 
			}
			if (players[i].getColumn() + 7 == ignoreRow) {
				continue;
			}
			BufferedImage playerImg = players[i].getImage(); // might have to switch columns and rows
			g.drawImage(playerImg, players[i].getColumn() * 96 + 62 - playerImg.getWidth() / 2,
					players[i].getRow() * 96 - playerImg.getHeight() / 2 + 62, null);
		}
	}

	// Method to display the movement
	public void displayMovement(int row, char direction,Tile tileBoardMatrix[][], Player[] players){
		System.out.println("\nhello\n");
		
		Timer t = new Timer(50,new ActionListener(){
			int i=0;
			public void actionPerformed(ActionEvent ae){
			displayMovement(row,direction,tileBoardMatrix,players,i);i++;
			if(i>12){((Timer)ae.getSource()).stop();}
			}
		}
		);
		t.start();
		
	}
	private void displayMovement(int row, char direction,Tile tileBoardMatrix[][], Player[] players,int i) {// use n/e/s/w, count rows from top left (0-6)}
		
		
		
		for (int i1=0; i1<7; i1++) {
			for (int j=0;j<7; j++) {
				
				System.out.print(tileBoardMatrix[i1][j].getName() + " ");
			}
			System.out.println();
		}
			g.setColor(new Color(7,55,99));
			int tmp1 = row;
			if (direction == 'n' || direction == 's') {
				tmp1 += 7;
			}
			displayGameBoard(tileBoardMatrix, players, tmp1);
			g.setColor(new Color(7, 55, 99));
			if (direction == 'n' || direction == 's') {
				g.fillRect(row * 96 + 14, 0, 96, 700);
			} else {
				g.fillRect(0, row * 96 + 14, 700, 96);
			}
			for (int j = 0; j < 7; j++) {

				switch (direction) {
				case 'e':
					g.drawImage(getImg(tileBoardMatrix[row][j].fileName()), 14 + j * 96 + i * 8, row * 96 + 14, null);
					for (int k = 0; k < 4; k++) {
						BufferedImage playerImg = players[k].getImage();
						if (players[k].getRow() == row) {
							g.drawImage(playerImg, i * 8 + players[k].getColumn() * 96 + 62 - playerImg.getWidth() / 2,
									players[k].getRow() * 96 - playerImg.getHeight() / 2 + 62, null);
						}
					}
					break;
				case 'w':
					g.drawImage(getImg(tileBoardMatrix[row][j].fileName()), 14 + j * 96 - i * 8, row * 96 + 14, null);
					for (int k = 0; k < 4; k++) {
						BufferedImage playerImg = players[k].getImage();
						if (players[k].getRow() == row) {
							g.drawImage(playerImg, -i * 8 + players[k].getColumn() * 96 + 62 - playerImg.getWidth() / 2,
									players[k].getRow() * 96 - playerImg.getHeight() / 2 + 62, null);
						}
					}
					break;
				case 'n':
					g.drawImage(getImg(tileBoardMatrix[j][row].fileName()), row * 96 + 14, 14 + j * 96 - i * 8, null);
					for (int k = 0; k < 4; k++) {
						BufferedImage playerImg = players[k].getImage();
						if (players[k].getColumn() == row) {
							g.drawImage(playerImg, players[k].getRow() * 96 - playerImg.getWidth() / 2 + 62,
									-i * 8 + players[k].getColumn() * 96 + 62 - playerImg.getHeight() / 2, null);
						}
					}
					break;
				case 's':
					g.drawImage(getImg(tileBoardMatrix[j][row].fileName()), row * 96 + 14, 14 + j * 96 + i * 8, null);
					for (int k = 0; k < 4; k++) {
						BufferedImage playerImg = players[k].getImage();
						if (players[k].getColumn() == row) {
							g.drawImage(playerImg, players[k].getRow() * 96 - playerImg.getWidth() / 2 + 62,
									i * 8 + players[k].getColumn() * 96 + 62 - playerImg.getHeight() / 2, null);
						}
					}
					break;
				}
				
				displayPlayers(this.players, tmp1);
				
			}
			System.out.println("k");
			boardLabel.setIcon(new ImageIcon(bi_board));
			try {
				long time=java.lang.System.currentTimeMillis();
				while(java.lang.System.currentTimeMillis()-50<time){}
			} catch (Exception ex) {
			}
		}
		
	private void display() {
		for (int i = 0; i < 7; i++) {

			for (int j = 0; j < 7; j++) {

				System.out.print(tileBoardMatrix[i][j].getName() + "  ");

			}
			System.out.println();
		}

	}
	public void movePlayer(Player p, char direction, Tile[][] tiles, Player[] players){
		Timer t = new Timer(50,new ActionListener(){
			int i=0;
			public void actionPerformed(ActionEvent ae){
			movePlayer(p,direction,tiles,players,i);i++;
			if(i>12){((Timer)ae.getSource()).stop();}
			}
		}
		);
		t.start();
	}
	// Moves player
	private void movePlayer(Player p, char direction, Tile[][] tiles, Player[] players,int i) {// input player with original
			displayTiles(tiles, -1);
			for (int j = 0; j < 4; j++) {
				if (players[j].getRow() == p.getRow() && players[j].getColumn() == p.getColumn()) {
					continue;
				}
				System.out.println(p.getRow() + "," + p.getColumn());
				BufferedImage playerImg2 = players[j].getImage();
				g.drawImage(playerImg2, players[j].getColumn() * 96 + 62 - playerImg2.getWidth() / 2,
						players[j].getRow() * 96 - playerImg2.getHeight() / 2 + 62, null);
			}
			BufferedImage playerImg = p.getImage();
			switch (direction) {
			case 'e':
				g.drawImage(playerImg, i * 8 + p.getColumn() * 96 + 62 - playerImg.getWidth() / 2,
						p.getRow() * 96 - playerImg.getHeight() / 2 + 62, null);
				break;
			case 'w':
				g.drawImage(playerImg, -i * 8 + p.getColumn() * 96 + 62 - playerImg.getWidth() / 2,
						p.getRow() * 96 - playerImg.getHeight() / 2 + 62, null);
				break;
			case 'n':
				g.drawImage(playerImg, p.getRow() * 96 - playerImg.getWidth() / 2 + 62,
						-i * 8 + p.getColumn() * 96 + 62 - playerImg.getHeight() / 2, null);
				break;
			case 's':
				g.drawImage(playerImg, p.getRow() * 96 - playerImg.getWidth() / 2 + 62,
						i * 8 + p.getColumn() * 96 + 62 - playerImg.getHeight() / 2, null);
				break;
			}
			boardLabel.setIcon(new ImageIcon(bi_board));
			try {
				long time=java.lang.System.currentTimeMillis();
				while(java.lang.System.currentTimeMillis()-50<time){}
			} catch (Exception ex) {
			}
		}

	// Gets image as buffered image
	public static BufferedImage getImg(String fileName) {
		try {
			return ImageIO.read(new File(fileName));
		} catch (Exception ex) {
			System.out.println("cannot find file " + fileName);
		}
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	}
}

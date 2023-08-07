package View;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

import Model.Card;
import Model.Player;
import Model.Tile;

public class LabyrinthGameFrame extends JFrame {

	LabyrinthBoardPanel boardPanel;
	LabyrinthControlPanel controlPanel;
	LabyrinthPlayerPanel[] playerPanel;
	LabyrinthFreeTilePanel freeTilePanel;

	public LabyrinthGameFrame(Tile[][] board, Player[] players) {
 
		this.setName("Labyrinth Game");
		this.setSize(new Dimension(1300, 745));
		this.setVisible(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		boardPanel = new LabyrinthBoardPanel(board, players);
		controlPanel = new LabyrinthControlPanel();
		playerPanel = new LabyrinthPlayerPanel[4];
		freeTilePanel = new LabyrinthFreeTilePanel();

		displayPanels();
	}

	// Happy
	// Display panels
	private void displayPanels() {

		for (int i = 0; i < 4; i++) {

			playerPanel[i] = new LabyrinthPlayerPanel("Player #" + (i + 1));

			playerPanel[i].setBounds(707, i * 100, 570, 100);
			playerPanel[i].setVisible(true);
			playerPanel[i].setBackground(Color.green);
			this.add(playerPanel[i]);
		}
		
		// Add Control and Board panels
		controlPanel.setBounds(707, 400, 400, 300);
		this.add(controlPanel);
		boardPanel.setBounds(0, 0, 700, 700);
		this.add(boardPanel);
		freeTilePanel.setBounds(1107, 400, 170, 300);
		this.add(freeTilePanel);
	}

	public LabyrinthBoardPanel getBoardPanel() {
		return boardPanel;
	}
	
	public LabyrinthPlayerPanel[] getPlayerPanel() {
		return playerPanel;
	}

}

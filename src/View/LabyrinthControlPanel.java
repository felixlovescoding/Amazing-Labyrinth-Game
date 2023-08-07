package View;

import java.awt.Color;

import javax.swing.*;

public class LabyrinthControlPanel extends JPanel {

	private static JButton[] movePlayerButton;
	private static JButton endTurnButton;
	private static JButton saveGameButton;
	private static JLabel player;

	// Constructor
	public LabyrinthControlPanel() {

		// Set JPanel
		this.setLayout(null);
		this.setBounds(675, 100, 300, 300);
		this.setBackground(Color.blue);

		// Add movement buttons
		displayMovementButtons();

		player = new JLabel("Player : #1");
		player.setBounds(5, 5, 100, 40);
		player.setForeground(Color.orange);
		this.add(player);

		// Set Bounds and add other buttons
		endTurnButton = new JButton("End Turn");
		endTurnButton.setBounds(25, 200, 100, 50);
		this.add(endTurnButton);
		saveGameButton = new JButton("Save Game");
		saveGameButton.setBounds(275, 200, 100, 50);
		this.add(saveGameButton);

		// Set visibility of JPanel
		this.setVisible(true);

	}

	// Utility method
	// Add the movement Button
	private void displayMovementButtons() {

		// Movement buttons
		// Manually set as they have different boundaries
		movePlayerButton = new JButton[4];
		movePlayerButton[0] = new JButton("U");
		movePlayerButton[0].setBounds(150, 25, 100, 25);
		movePlayerButton[1] = new JButton("R");
		movePlayerButton[1].setBounds(262, 62, 25, 100);
		movePlayerButton[2] = new JButton("D");
		movePlayerButton[2].setBounds(150, 175, 100, 25);
		movePlayerButton[3] = new JButton("L");
		movePlayerButton[3].setBounds(112, 62, 25, 100);

		// Add buttons to JPanel
		for (int i = 0; i < 4; i++) {

			this.add(movePlayerButton[i]);
		}

	}

	// Getters and Setters
	public static JButton[] getMovePlayerButton() {
		return movePlayerButton;
	}

	public static void setMovePlayerButton(JButton[] movePlayerButton) {
		LabyrinthControlPanel.movePlayerButton = movePlayerButton;
	}

	public static JButton getEndTurnButton() {
		return endTurnButton;
	}

	public static void setEndTurnButton(JButton endTurnButton) {
		LabyrinthControlPanel.endTurnButton = endTurnButton;
	}
 
	public static JButton getSaveGameButton() {
		return saveGameButton;
	}

	public static void setSaveGameButton(JButton saveGameButton) {
		LabyrinthControlPanel.saveGameButton = saveGameButton;
	}
	
	public static void changePlayer(int player) {
		LabyrinthControlPanel.player.setText("Player : #"+ (player+1));
	}

}
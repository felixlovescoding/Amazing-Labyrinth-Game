package View;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;

import Model.Tile;

public class LabyrinthFreeTilePanel extends JPanel {

	static JLabel freeTileLabel;
	static JButton rotateTileButtonL;
	static JButton rotateTileButtonR;
	BufferedImage bi_freeTile = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);
	Graphics g = bi_freeTile.getGraphics();
	
	public LabyrinthFreeTilePanel() {

		// Set JPanel
		this.setLayout(null);
		this.setBackground(Color.pink);
    
		displayComponents();
		// Set visibility of JPanel 
		this.setVisible(true);
	}

	private void displayComponents() {
		
		rotateTileButtonL = new JButton("Left");
		rotateTileButtonL.setBounds(10,160,75,70);
		this.add(rotateTileButtonL);
		
		rotateTileButtonR = new JButton("Right");
		rotateTileButtonR.setBounds(85,160,75,70);
		this.add(rotateTileButtonR);
		
		freeTileLabel = new JLabel();
		freeTileLabel.setBounds(37, 36, 96, 96);
		this.add(freeTileLabel);
	}
	
	public static void displayFreeTile(Tile freeTile) {
		
		ImageIcon image = new ImageIcon(freeTile.fileName());
		freeTileLabel.setIcon(image);
		
	}
	
	public static JButton getRotateTileButtonL() {
		return rotateTileButtonL;
	}
	public static JButton getRotateTileButtonR() {
		return rotateTileButtonR;
	}

}
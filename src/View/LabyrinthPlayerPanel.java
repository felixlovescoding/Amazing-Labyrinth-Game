package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.*;

public class LabyrinthPlayerPanel extends JPanel {
	
	JLabel l=new JLabel();
	BufferedImage bi=new BufferedImage(400,75,BufferedImage.TYPE_INT_ARGB);
	Graphics g=bi.getGraphics();
	public LabyrinthPlayerPanel(String name){
		super(); 
		setLayout(null);
		JLabel nameLabel=new JLabel(name);
		nameLabel.setVerticalAlignment(JLabel.BOTTOM);
		nameLabel.setBounds(25,0,100,25);
		l.setBounds(120,5,400,100);
		add(l);
		add(nameLabel);
		setPreferredSize(new Dimension(400,75));
	}
	public void displayCards(Card[] cards){
		bi=new BufferedImage(400,100,BufferedImage.TYPE_INT_ARGB);
		g=bi.getGraphics();
		for(int i=0;i<6;i++){
			if(cards[i].isTreasure()){continue;}
			g.drawImage(LabyrinthBoardPanel.getImg(cards[i].getFileName()),i*60,15,56,77,null);
		}
		l.setIcon(new ImageIcon(bi));
	}
	
}

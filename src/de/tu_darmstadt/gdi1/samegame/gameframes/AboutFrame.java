package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutFrame extends JFrame{
	public AboutFrame(){
		super("About");

		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// TODO write something better than this
		this.getContentPane().add(new JLabel("Made by Seb, Tim, Svenja and Sven"));

		this.setVisible(true);
	}
}

package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AskForSaveFrame extends JFrame{
	public AskForSaveFrame(){
		// TODO find a better Window Label
		super("insert your name");

		// TODO fill method stub
		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}
}
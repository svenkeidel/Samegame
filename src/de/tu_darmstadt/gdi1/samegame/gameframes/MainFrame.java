package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;
import javax.swing.JFrame;

public class MainFrame extends JFrame implements Runnable{
	public MainFrame(){
		// TODO find a better Window Label
		super("Main Frame");

		// TODO fill method stub
		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public void run(){
		// TODO time actualization
	}
}

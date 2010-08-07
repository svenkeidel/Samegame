package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;

import java.util.Locale;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class HighscoreFrame extends JFrame{

	private Locale locale;

	public HighscoreFrame(Locale locale){
		super();

		// TODO I18n this file;
		this.locale = locale;

		// TODO fill method stub
		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}
}

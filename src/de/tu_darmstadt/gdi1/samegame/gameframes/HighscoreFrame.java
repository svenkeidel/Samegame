package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.tu_darmstadt.gdi1.samegame.highscore.Highscore;

@SuppressWarnings("serial")
public class HighscoreFrame extends JFrame{

	private Locale locale;
	
	private ResourceBundle messages;
	
	private Highscore scores;
	
	

	public HighscoreFrame(Locale locale){
		super("Highscore");

		this.locale = locale;
		
		messages = 
			ResourceBundle.getBundle("de.tu_darmstadt.gdi1.samegame.gameframes.HighscoreBundle", 
									 locale, 
									 this.getClass().getClassLoader()); 
		this.scores = new Highscore();
		

		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().add(new JLabel(
				messages.getString("HighscoreText")
				+ scores.toString()
				));

		this.setVisible(true);	
	}
}

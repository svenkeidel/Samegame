package de.tu_darmstadt.gdi1.samegame.view.gameframes;

import java.awt.Color;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.tu_darmstadt.gdi1.samegame.view.SameGameViewer;

@SuppressWarnings("serial")
public class AddHighscoreFrame extends JFrame{
	private String eingabe;
	private Locale locale;
	private ResourceBundle messages;
	private SameGameViewer viewer;
	
	public AddHighscoreFrame(Locale locale){
		super();
		
		
	
		
		messages = ResourceBundle.getBundle("de.tu_darmstadt.gdi1.samegame.view.gameframes.HighscoreBundle", 
				 locale, 
				 this.getClass().getClassLoader()); 
		
			
		
		eingabe = JOptionPane.showInputDialog(null,messages.getString("TypeIn_Name"),
                messages.getString("New_Highscore"),
                JOptionPane.PLAIN_MESSAGE);

	}

	public String getPlayersName(){
		return eingabe;
	}
}

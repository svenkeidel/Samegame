package de.tu_darmstadt.gdi1.samegame.view.gameframes;

import java.awt.Color;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.tu_darmstadt.gdi1.samegame.model.Level;

@SuppressWarnings("serial")
public class HighscoreFrame extends JFrame{

	private Locale locale;
	
	private ResourceBundle messages;

	
	

	public HighscoreFrame(Locale locale, Level level){
		super("Highscore");

		this.locale = locale;
		
		messages = 
			ResourceBundle.getBundle("de.tu_darmstadt.gdi1.samegame.view.gameframes.HighscoreBundle", 
									 locale, 
									 this.getClass().getClassLoader()); 
		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		//String[][] scores = level.getHighscore();
		String[][] scores = { 
			      { "Japan", "245","Japan", "245" }, { "USA", "240","USA", "240" }, { "Italien", "220","Italien", "220" }, 
			      { "Spanien", "217","Spanien", "217" }, {"Türkei", "215","Türkei", "215"} ,{ "England", "214","England", "214" }, 
			      { "Frankreich", "190","Frankreich", "190" }, {"Griechenland", "185","Griechenland", "185" }, 
			      { "Deutschland", "180","Deutschland", "180" }, {"Portugal", "170","Portugal", "170" } 
			    }; 
		
		String[] columnNames = {messages.getString("Points"),
				messages.getString("Time"),
				messages.getString("Date"),
				messages.getString("Name")};
		

		JTable table = new JTable(scores, columnNames);

		
		this.add(new JScrollPane( table));

		this.setVisible(true);	
	}
	
}

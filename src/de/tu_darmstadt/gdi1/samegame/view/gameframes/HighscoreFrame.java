package de.tu_darmstadt.gdi1.samegame.view.gameframes;

import java.awt.Color;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.tu_darmstadt.gdi1.samegame.model.Level;

@SuppressWarnings("serial")
public class HighscoreFrame extends JFrame{

	private ResourceBundle messages;

	public HighscoreFrame(Locale locale, Level level){
		super("Highscore");

		messages = 
			ResourceBundle.getBundle("de.tu_darmstadt.gdi1.samegame.view.gameframes.HighscoreBundle", 
									 locale, 
									 this.getClass().getClassLoader()); 
		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		String[][] scores = level.getHighscore();
		
		String[] columnNames = {messages.getString("Points"),
				messages.getString("Time"),
				messages.getString("Date"),
				messages.getString("Name")};
		

		JTable table;
		
		if(scores != null)
			table = new JTable(scores, columnNames);
		else
			table = new JTable(new String[][]{{"","","",""}}, columnNames);
				
		this.add(new JScrollPane( table));
		
		this.setVisible(true);	
	}
}

package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;

//BlankoFrame for the alert window
@SuppressWarnings("serial")
public class AlertFrame extends JFrame{

	private Locale locale;
	private ResourceBundle messages;

	public AlertFrame(Locale locale){
		
        this.locale = locale;

		messages = 
			ResourceBundle.getBundle("de.tu_darmstadt.gdi1.samegame.gameframes.AboutBundle", 
									 locale, 
									 this.getClass().getClassLoader()); 

		setBackground(Color.lightGray);
		setSize(300,100);
		setTitle(messages.getString("Alert"));
		setLocation(200,300);
		setVisible(true);
	}

}
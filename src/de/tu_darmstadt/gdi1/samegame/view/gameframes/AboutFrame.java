package de.tu_darmstadt.gdi1.samegame.view.gameframes;

import java.awt.Color;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AboutFrame extends JFrame{

	private Locale locale;

	private ResourceBundle messages;

	public AboutFrame(Locale locale){
		super("About");

        this.locale = locale;

		messages = 
			ResourceBundle.getBundle("de.tu_darmstadt.gdi1.samegame.view.gameframes.AboutBundle", 
									 locale, 
									 this.getClass().getClassLoader()); 

		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		this.getContentPane().add(new JLabel(
					messages.getString("AboutText")
					+ ": Seb, Tim, Svenja and Sven"));

		this.setVisible(true);
	}
}

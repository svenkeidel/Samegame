package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class AboutFrame extends JFrame{

	private Locale currentLocale;

	private ResourceBundle messages;

	public AboutFrame(){
		super("About");

        currentLocale = new Locale("de", "DE");

		messages = 
			ResourceBundle.getBundle("de.tu_darmstadt.gdi1.samegame.gameframes.AboutBundle", 
									 currentLocale, 
									 this.getClass().getClassLoader()); 

		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.getContentPane().add(new JLabel(
					messages.getString("AboutText")
					+ "Seb, Tim, Svenja and Sven"));

		this.setVisible(true);
	}

	public static void main(String args[]){
		AboutFrame about = new AboutFrame();
	}
}

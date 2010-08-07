package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;
import java.awt.Graphics;

import java.util.Locale;
import javax.swing.JFrame;
import static java.lang.Thread.sleep;

import de.tu_darmstadt.gdi1.samegame.Level;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements Runnable{

	private Level level;
	private Locale locale;

	private int markedRow, markedCol;

	public MainFrame(Level level, Locale locale){
		super("Same Game");

		this.level = level;
		// TODO I18n this file
		this.locale = locale;

		markedRow = markedCol = 0;

		// TODO fill method stub
		setBackground(Color.lightGray);
		this.setSize(300,100);
		this.setLocation(200,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	public void markField(int row, int col){
		if(row >= 0 || row < level.getFieldHeight() ||
		   col >= 0 || col < level.getFieldWidth()){
			markedRow = row;
			markedCol = col;
		}
	}

	public void run(){
		while(true){
			// TODO do something with the time
			level.getElapsedTime();
			try{
				sleep(1000);
			}catch(InterruptedException ignored){}
		}
	}
}

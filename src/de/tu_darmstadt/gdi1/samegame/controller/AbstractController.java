package de.tu_darmstadt.gdi1.samegame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import de.tu_darmstadt.gdi1.samegame.model.Level;
import de.tu_darmstadt.gdi1.samegame.view.SameGameViewer;



public abstract class AbstractController extends KeyAdapter implements ActionListener{
	////////////////////////Class/Attributes//////////////////////////
	SameGameViewer viewer;
	protected Level level;

	////////////////////////Getters//&//Setters///////////////////////
	public void setLevel(Level level){
		this.level = level;
	}

	////////////////////////Class/Operations//////////////////////////
	
	/**
	 * Handle all events in the game
	 * 
	 * @param e
	 * 			the event this was triggered
	 */
	@Override
	public final void actionPerformed(ActionEvent e){
		// a click on a menu entry
		if(e.getSource() instanceof JMenuItem){
			menuClick((JMenuItem) e.getSource());
		// a click on a the gamefield
		}else if(e.getSource() instanceof JButton){
			fieldClick(e, (JButton) e.getSource());
		}	
	}

	/**
	 * describes how a controller reacts on a click on a menu entry
	 * 
	 * @param menuItem
	 * 				the menu entry this was clicked
	 */
	public abstract void menuClick(JMenuItem menuItem);
	
	
	/**
	 * describes how a controller reacts on a click on the gamefield
	 * 
	 * @param e
	 * 			the event this was released
	 * @param b
	 * 			the button this was clicked
	 */
	public abstract void fieldClick(ActionEvent e, JButton b);

	
	/**
	 * describes how a controller reacts on a pressed key
	 * 
	 * @param e
	 * 			the key this was pressed
	 */
	@Override
	public abstract void keyPressed(KeyEvent e);
}

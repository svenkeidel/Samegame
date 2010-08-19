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
	SameGameViewer viewer;
	protected Level level;

	public void setLevel(Level level){
		this.level = level;
	}
	
	@Override
	public final void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof JMenuItem){
			menuClick((JMenuItem) e.getSource());
		}else if(e.getSource() instanceof JButton){
			fieldClick(e, (JButton) e.getSource());
		}	
	}

	public abstract void menuClick(JMenuItem menuItem);
	
	public abstract void fieldClick(ActionEvent e, JButton b);

	@Override
	public abstract void keyPressed(KeyEvent e);
}

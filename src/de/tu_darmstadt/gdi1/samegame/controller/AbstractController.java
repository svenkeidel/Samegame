package de.tu_darmstadt.gdi1.samegame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import de.tu_darmstadt.gdi1.samegame.model.Level;


public abstract class AbstractController extends KeyAdapter implements ActionListener{

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
		}else if (e.getSource() instanceof JFileChooser){
			JFileChooser chooser = (JFileChooser) e.getSource();
            File f = chooser.getSelectedFile();
			fileChoosed(chooser.getName(), f);
		}
	}

	public abstract void menuClick(JMenuItem menuItem);
	
	public abstract void fieldClick(ActionEvent e, JButton b);

	public abstract void fileChoosed(String source, File f);

	@Override
	public abstract void keyPressed(KeyEvent e);
}

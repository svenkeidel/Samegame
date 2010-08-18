package de.tu_darmstadt.gdi1.samegame.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import de.tu_darmstadt.gdi1.samegame.model.Level;

import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;

public abstract class AbstractController extends KeyAdapter implements ActionListener{

	private Level level;

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
            try{
                    System.out.println(f.getCanonicalPath());
                    level.restoreLevel(f);
            }catch(FileNotFoundException ex){
                    // TODO meldung file nicht gefunden
            }catch(WrongLevelFormatException ex){
                    // TODO meldung level nicht im richtigen format
            }catch(IOException ignored){}

		}
	}

	public abstract void menuClick(JMenuItem menuItem);
	
	public abstract void fieldClick(ActionEvent e, JButton b);

	@Override
	public abstract void keyPressed(KeyEvent e);
}

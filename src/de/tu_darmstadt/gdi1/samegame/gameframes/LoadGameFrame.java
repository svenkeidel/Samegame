package de.tu_darmstadt.gdi1.samegame.gameframes;



import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import de.tu_darmstadt.gdi1.samegame.GameController;


@SuppressWarnings("serial")
public class LoadGameFrame extends JFrame{

	private static String pathstring;
	private GameController controller;

	
	
	public LoadGameFrame(GameController controller){		
		

		      
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.addActionListener(controller);
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION)
		        {
		          File selectedFile = fileChooser.getSelectedFile();
		          pathstring = selectedFile.getPath();
		         }
		      }
	
	
	public static String getLoadPath(){
		return pathstring;
		
		
	}


	
}

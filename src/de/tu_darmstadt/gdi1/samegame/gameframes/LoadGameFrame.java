package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;

import java.util.Locale;
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



@SuppressWarnings("serial")
public class LoadGameFrame extends JFrame{

	private Locale locale;

	public LoadGameFrame(Locale locale, Color BColor, Color FColor){
		super("Load Game");

				
		 JFrame.setDefaultLookAndFeelDecorated(true);
		    JDialog.setDefaultLookAndFeelDecorated(true);
		    JFrame frame = new JFrame("JComboBox Test");
		    frame.setLayout(new FlowLayout());
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   
		    {
		       {
		        JFileChooser fileChooser = new JFileChooser();
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION) {
		          File selectedFile = fileChooser.getSelectedFile();
		          System.out.println(selectedFile.getName());
		        }
		      }
		    };


	}
}

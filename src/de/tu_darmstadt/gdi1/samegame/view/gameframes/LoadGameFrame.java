package de.tu_darmstadt.gdi1.samegame.view.gameframes;

import javax.swing.JFrame;

import javax.swing.JFileChooser;

import java.io.File;

import de.tu_darmstadt.gdi1.samegame.controller.AbstractController;


@SuppressWarnings("serial")
public class LoadGameFrame extends JFrame{

	private static String pathstring;
	
	public LoadGameFrame(AbstractController controller){		
		      
		        JFileChooser fileChooser = new JFileChooser();
		        fileChooser.setFileFilter( new javax.swing.filechooser.FileFilter() {
	                public boolean accept(File f) {
	                    return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
	                }
	                public String getDescription() {
	                    return "Level(*.xml)";
	                }
	            });
		        fileChooser.addActionListener(controller);
		        int returnValue = fileChooser.showOpenDialog(null);
		        if (returnValue == JFileChooser.APPROVE_OPTION)
		        {
		        	 pathstring = fileChooser.getSelectedFile().getPath();
		         }
		      }
	
	
	public static String getLoadPath(){
		return pathstring;
		
		
	}
}

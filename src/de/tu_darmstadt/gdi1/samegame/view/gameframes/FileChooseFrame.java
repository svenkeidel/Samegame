package de.tu_darmstadt.gdi1.samegame.view.gameframes;

import javax.swing.JFrame;

import javax.swing.JFileChooser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import de.tu_darmstadt.gdi1.samegame.controller.AbstractController;

@SuppressWarnings("serial")
public class FileChooseFrame extends JFileChooser{

	private static String pathstring;
	
	public FileChooseFrame(String source, AbstractController controller){		
		this.setName(source);

		this.setFileFilter(getFileFilter(source));

		this.addActionListener(controller);

		this.setFileFilter(getFileFilter(source));
		JFrame parentframe = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(false);
		
		if(source.equals("SaveLevel")){
			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(parentframe);
			chooser.setVisible(true);
		}else if(source.equals("SaveGameState")){
			JFileChooser chooser = new JFileChooser();
			chooser.showSaveDialog(parentframe);
			chooser.setVisible(true);
		}else if(source.equals("LoadLevel")){
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(parentframe);
			chooser.setVisible(true);
		}else if(source.equals("LoadGameState")){
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(parentframe);
			chooser.setVisible(true);
		}
	}

	public FileFilter getFileFilter(String source){
		if(source.equals("SaveLevel") || source.equals("SaveGameState")){
			return new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".sve") 
						|| f.getName().toLowerCase().endsWith(".lvl") 
						|| f.isDirectory();
				}
				public String getDescription() {
					return "Level-data(*.lvl, *.sve)";
				}
			};
		}else if(source.equals("LoadLevel")){
			return new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".lvl") 
						|| f.isDirectory();
				}
				public String getDescription() {
					return "Level(*.lvl)";
				}
			};
		}else if(source.equals("LoadGameState")){
			return new javax.swing.filechooser.FileFilter() {
				public boolean accept(File f) {
					return f.getName().toLowerCase().endsWith(".sve") 
						|| f.isDirectory();
				}
				public String getDescription() {
					return "Game State (*.sve)";
				}
			};
		}else return null;
	}
	
	
	public static String getLoadPath(){
		return pathstring;
	}
}

package de.tu_darmstadt.gdi1.samegame.view.gameframes;

import javax.swing.JFileChooser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * A frame to choose between saveLevel, loadLevel, saveGame and loadGame
 */
@SuppressWarnings("serial")
public class FileChooseFrame extends JFileChooser{

	public FileChooseFrame(String source){		
		super(source);
		this.setName(source);

		this.setFileFilter(getFileFilter(source));

		this.setFileFilter(getFileFilter(source));
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
}

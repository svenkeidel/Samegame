
package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.io.File;



import javax.swing.JFileChooser;
import javax.swing.JFrame;

import de.tu_darmstadt.gdi1.samegame.GameController;

@SuppressWarnings("serial")
public class SaveGameFrame extends JFrame{

	
	private static String SavePath;
	private static String SavePathXML;

	public SaveGameFrame(GameController controller){
	
		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setFileFilter( new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".xml") || f.isDirectory();
            }
            public String getDescription() {
                return "Level(*.xml)";
            }
        });
		fc.addActionListener(controller);
		int state = fc.showSaveDialog(null);
		 
		if (state == JFileChooser.APPROVE_OPTION)
		    {
			if (fc.getSelectedFile().getPath().contains(".xml"))
				SavePath = fc.getSelectedFile().getPath();
			else {
				SavePathXML = fc.getSelectedFile().getPath().concat(".xml");
			}
		    }
	}
	
	public static String getSavePath (){
		return SavePath;
	}
}

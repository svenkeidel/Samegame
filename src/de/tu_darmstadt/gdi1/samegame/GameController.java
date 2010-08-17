package de.tu_darmstadt.gdi1.samegame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

import javax.swing.JMenuItem;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

public class GameController extends KeyAdapter implements ActionListener{

	private Level level;
	private SameGameViewer viewer;

	public GameController(Level level){
		this.level = level;
	}

	public GameController(Level level, SameGameViewer viewer){
		this.level = level;
		this.viewer = viewer;
	}

	public void setLevel(Level level){
		this.level = level;
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof JMenuItem){
			JMenuItem source = (JMenuItem) e.getSource();
			if (source.getName().equals("GameMenu_RestartLvl") )
				level.restartLevel();
			if (source.getName().equals("FileMenu_SaveLevel"))
				viewer.showMainFrame();
			if (source.getName().equals("FileMenu_LoadLevel"))
				viewer.showLoadGameFrame();
			if (source.getName().equals("FileMenu_Exit"))
				viewer.closeMainFrame();
			if (source.getName().equals("GameMenu_Undo"))
				level.undo();
			if (source.getName().equals("GameMenu_Redo"))
				level.redo();
			if (source.getName().equals("German"))
				viewer.setLanguage("German");
			if (source.getName().equals("English"))
				viewer.setLanguage("English");
			// TODO add some more
		}
		// TODO Write method stub
	}
	
	
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		int markedRow = 0;
		int markedCol = 0;
		if(viewer!=null){
			markedRow = viewer.getMarkedFieldRow();
			markedCol = viewer.getMarkedFieldCol();
			viewer.markField(markedRow, markedCol);
		}

		switch(key){
			case VK_N:
				level.restartLevel();
				break;
			case VK_BACK_SPACE:
				if (viewer.duringAnimation() != true)
					try{
						level.undo();
					}catch(CannotUndoException ignored){}
				break;
			case VK_ENTER:
				if (viewer.duringAnimation() != true)
					try{
						level.redo();
					}catch(CannotRedoException ignored){}
				break;
			case VK_SPACE:
				if (viewer.duringAnimation() != true)
					try {
						level.removeStone(markedRow, markedCol);
					} catch (ParameterOutOfRangeException e1) {
						e1.printStackTrace();
					}
				break;
			case VK_LEFT:
				if (markedCol >=0){
				markedCol -=1;
				viewer.markField(markedRow,markedCol);
				}
				break;
			case VK_RIGHT:
				if (markedCol <= level.getFieldWidth()){
				markedCol +=1;
				viewer.markField(markedRow,markedCol);
				}
				break;
			case VK_UP:
				if (markedRow >=0){
				markedRow -=1;
				viewer.markField(markedRow, markedCol);
				}
				break;
			case VK_DOWN:
				if (markedRow <= level.getFieldHeight()){
				markedRow +=1;
				viewer.markField(markedRow, markedCol);
				}
				break;
			default:;
		}
	}
}

package de.tu_darmstadt.gdi1.samegame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JMenuItem;

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
			menuClick((JMenuItem) e.getSource());
		}else if(e.getSource() instanceof JButton){
			fieldClick(e, (JButton) e.getSource());
		}
	}

	public void menuClick(JMenuItem menuItem){
		String menuName = menuItem.getName();
		if (menuName.equals("FileMenu_GenerateLevel") )
			level.restartLevel();
		if (menuName.equals("FileMenu_SaveLevel"))
			viewer.showMainFrame();
		if (menuName.equals("FileMenu_LoadLevel"))
			viewer.showLoadGameFrame();
		if (menuName.equals("FileMenu_Exit"))
			viewer.closeMainFrame();
		if (menuName.equals("GameMenu_Undo"))
			level.undo();
		if (menuName.equals("German"))
			viewer.setLanguage(new Locale("de", "DE"));
		if (menuName.equals("English"))
			viewer.setLanguage(new Locale("en", "US"));
	}
	
	public void fieldClick(ActionEvent e, JButton b){
		/**
		TODO refractor this function !!! at the end use mainPanel.entityClicked(...)

		if (!hasEntities())
			return;
		// retrieve first button
		JButton refBtn = entities.get(0);
		
		// iterate buttons until right one was found
		for (int i = 0; i < entities.size(); i++) {
			JButton btn = entities.get(i);
			if (evt.getSource() == btn) {
				// determine x and y position
				int posX = evt.getXOnScreen();
				posX = posX - (int) this.getLocationOnScreen().getX();

				int posY = evt.getYOnScreen();
				posY = posY - (int) this.getLocationOnScreen().getY();

				// pass message along
				entityClicked(posX / refBtn.getWidth(), posY
						/ refBtn.getHeight());
				
				// done!
				evt.consume();
				break;
			}
		}
		*/
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

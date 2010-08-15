package de.tu_darmstadt.gdi1.samegame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;

import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

public class GameController extends KeyAdapter implements ActionListener, MenuListener{

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
		// TODO Write method stub
	}

	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		int markedRow = 0;
		int markedCol = 0;
		viewer.markField(markedRow, markedCol);

		switch(key){
			case VK_N:
				level.restartLevel();
				break;
			case VK_BACK_SPACE:
				if (viewer.duringAnimation() != true)
				level.undo();
				break;
			case VK_ENTER:
				if (viewer.duringAnimation() != true)
				level.redo();
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
				if (markedCol != 0){
				markedCol -=1;
				viewer.markField(markedRow,markedCol);
				}
				break;
			case VK_RIGHT:
				markedCol +=1;
				viewer.markField(markedRow,markedCol);
				break;
			case VK_UP:
				if (markedRow != 0){
					markedRow -=1;
					viewer.markField(markedRow, markedCol);
				}
				break;
			case VK_DOWN:
				markedRow +=1;
				viewer.markField(markedRow, markedCol);
				break;
			default:;
		}
	}

	public void menuCanceled(MenuEvent e){
		// TODO Write method stub
	}

	public void menuDeselected(MenuEvent e){
		// TODO Write method stub
	}

	public void menuSelected(MenuEvent e){
		// TODO Write method stub
	}
}

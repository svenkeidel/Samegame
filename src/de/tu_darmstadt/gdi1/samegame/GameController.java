package de.tu_darmstadt.gdi1.samegame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;

public class GameController extends KeyAdapter implements ActionListener, MenuListener{

	private Level level;
	private SameGameViewer samegameviewer = new SameGameViewer();

	public GameController(Level level){
		this.level = level;
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
		samegameviewer.markField(markedRow, markedCol);

		switch(key){
			case VK_N:
				level.restartLevel();
				break;
			case VK_BACK_SPACE:
				if (samegameviewer.duringAnimation() != true)
				level.undo();
				break;
			case VK_ENTER:
				if (samegameviewer.duringAnimation() != true)
				level.redo();
				break;
			case VK_SPACE:
				if (samegameviewer.duringAnimation() != true)
				level.removeStone(markedRow, markedCol);
				break;
			case VK_LEFT:
				if (markedCol != 0){
				markedCol -=1;
				samegameviewer.markField(markedRow,markedCol);
				}
				break;
			case VK_RIGHT:
				markedCol +=1;
				samegameviewer.markField(markedRow,markedCol);
				break;
			case VK_UP:
				if (markedRow != 0){
				markedRow -=1;
				samegameviewer.markField(markedRow, markedCol);
				}
				break;
			case VK_DOWN:
				markedRow +=1;
				samegameviewer.markField(markedRow, markedCol);
				break;
			// TODO Write more cases like this
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

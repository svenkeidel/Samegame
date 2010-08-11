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

		switch(key){
			case VK_N:
				level.restartLevel();
				break;
			case VK_U:
				level.undo();
				break;
			case VK_R:
				level.redo();
				break;
			case VK_ENTER:
				level.removeStone();
				break;
			case VK_LEFT:
				// TODO
				break;
			case VK_RIGHT:
				// TODO
				break;
			case VK_UP:
				// TODO
				break;
			case VK_DOWN:
				// TODO
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

package de.tu_darmstadt.gdi1.samegame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.MenuListener;
import javax.swing.event.MenuEvent;

public class GameController{
	public class ContrActList implements ActionListener{
		public void actionPerformed(ActionEvent e){
			// TODO Write method stub
		}
	}

	public class ContrKeyAdpt extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			// TODO Write method stub
		}
	}

	public class ContrMenList implements MenuListener{
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
}

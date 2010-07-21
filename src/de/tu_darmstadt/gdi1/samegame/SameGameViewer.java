package de.tu_darmstadt.gdi1.samegame;

import de.tu_darmstadt.gdi1.samegame.gameframes.*;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SameGameViewer implements ChangeListener{
	private Level level;

	private int markedRow, markedCol;

	private MainFrame mainFrame;
	private OptionsFrame optionsFrame;
	private AskForSaveFrame askForSaveFrame;
	private AddHighscoreFrame addHighscoreFrame;
    private HighscoreFrame highscoreFrame;
    private SaveGameFrame saveGameFrame;
    private LoadGameFrame loadGameFrame;
    private AboutFrame aboutFrame;

	public SameGameViewer(){
		super();
	}

	// implements method from interface javax.swing.event.ChangeListener
	public void stateChanged(ChangeEvent e){
		//TODO write method stub
	}

	public void printContent(){
		//TODO write method stub
	}

	public void markField(int row, int col){
		//TODO write method stub
	}

	public void showMainFrame(){
		//TODO write method stub
	}

	public void showOptionsFrame(){
		//TODO write method stub
	}

	public void showAskForSaveFrame(){
		//TODO write method stub
	}

	public void showAddHighscoreFrame(){
		//TODO write method stub
	}

	public void showHighscoreFrame(){
		//TODO write method stub
	}

	public void showSaveGameFrame(){
		//TODO write method stub
	}

	public void showLoadGameFrame(){
		//TODO write method stub
	}

	public void showAboutFrame(){
		//TODO write method stub
	}

	public void closeMainFrame(){
		//TODO write method stub
	}

	public void closeOptionsFrame(){
		//TODO write method stub
	}

	public void closeAskForSaveFrame(){
		//TODO write method stub
	}

	public void closeAddHighscoreFrame(){
		//TODO write method stub
	}

	public void closeHighscoreFrame(){
		//TODO write method stub
	}

	public void closeSaveGameFrame(){
		//TODO write method stub
	}

	public void closeLoadGameFrame(){
		//TODO write method stub
	}

	public void closeAboutFrame(){
		//TODO write method stub
	}
}

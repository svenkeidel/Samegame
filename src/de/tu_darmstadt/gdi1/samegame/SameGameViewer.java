package de.tu_darmstadt.gdi1.samegame;

import java.util.Locale;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.tu_darmstadt.gdi1.samegame.exceptions.InternalFailureException;
import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

import de.tu_darmstadt.gdi1.samegame.gameframes.*;

public class SameGameViewer implements ChangeListener{
	public static final Locale DEFAULT_LOCALE = new Locale("en", "US");
	private Locale currentLocale;

	private Level level;
	private GameController controller;

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
		currentLocale = (Locale) DEFAULT_LOCALE.clone();
	}

	public void setLevel(Level level){
		this.level = level;
	}

	public void setController(GameController controller){
		this.controller = controller;
	}

	// implements method from interface javax.swing.event.ChangeListener
	public void stateChanged(ChangeEvent e){
		if(mainFrame != null)
			mainFrame.redraw();
	}

	public void markField(int row, int col){
		if(mainFrame != null)
			mainFrame.markField(row, col);
	}

	public int getMarkedFieldRow(){
		return mainFrame.getMarkedFieldRow();
	}

	public int getMarkedFieldCol(){
		return mainFrame.getMarkedFieldCol();
	}

	public boolean duringAnimation(){
		if(mainFrame != null)
			return mainFrame.duringAnimation();
		else return false;
	}

	public void startAnimation(int row, int col, long animationSpeed){
		mainFrame.startAnimation(row, col, animationSpeed);
	}

	public void showMainFrame(){
		this.mainFrame = new MainFrame(level, currentLocale, controller);
		this.mainFrame.setVisible(true);
		Thread timeUpdate = new Thread(mainFrame);
		timeUpdate.start();
	}

	public void showOptionsFrame(){
		this.optionsFrame = new OptionsFrame(currentLocale);
	}

	public void showAskForSaveFrame(){
		askForSaveFrame = new AskForSaveFrame(currentLocale);
	}

	public void showAddHighscoreFrame(){
		addHighscoreFrame = new AddHighscoreFrame(currentLocale);
	}

	public void showHighscoreFrame(){
		highscoreFrame = new HighscoreFrame(currentLocale);
	}

	public void showSaveGameFrame(){
		saveGameFrame = new SaveGameFrame(currentLocale);
	}

	public void showLoadGameFrame(){
		loadGameFrame = new LoadGameFrame(currentLocale);
	}

	public void showAboutFrame(){
		aboutFrame = new AboutFrame(currentLocale);
	}

	
	// TODO proof wether the close methods are needed
	public void closeMainFrame(){
		mainFrame = null;
	}

	public void closeOptionsFrame(){
		optionsFrame = null;
	}

	public void closeAskForSaveFrame(){
		askForSaveFrame = null;
	}

	public void closeAddHighscoreFrame(){
		addHighscoreFrame = null;
	}

	public void closeHighscoreFrame(){
		highscoreFrame = null;
	}

	public void closeSaveGameFrame(){
		saveGameFrame = null;
	}

	public void closeLoadGameFrame(){
		loadGameFrame = null;
	}

	public void closeAboutFrame(){
		aboutFrame = null;
	}

	public static void main(String args[]){
		SameGameViewer viewer = new SameGameViewer();
		Level level = new Level(viewer);
		GameController controller = new GameController(level, viewer);
		viewer.setController(controller);
		viewer.setLevel(level);
		level.generateLevel(10, 10, 5, 3);
		viewer.showMainFrame();
		try{
			viewer.mainFrame.notifyLevelLoaded(level.getFieldWidth(), level.getFieldHeight());
		}catch(ParameterOutOfRangeException e){
			e.printStackTrace();
		}catch(InternalFailureException e){
			e.printStackTrace();
		}
	}
}

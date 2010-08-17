package de.tu_darmstadt.gdi1.samegame;

import java.awt.Color;
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
	
	private MainFrame mainFrame;
	private MainPanel mainPanel;
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
	
	public void setSkin(String skin, Color BColor, Color FColor){
		mainFrame.setSkin(skin, BColor, FColor);
	}
	// implements method from interface javax.swing.event.ChangeListener
	public void stateChanged(ChangeEvent e){
		if(mainFrame != null)
			try{
				mainPanel.redraw();
			}catch(InternalFailureException ex){
				ex.printStackTrace();
			}
	}

	public void markField(int row, int col){
		if(mainFrame != null)
			mainPanel.markField(row, col);
	}
	
	public MainPanel getMainPanel(){
		return this.mainPanel;
	}

	public int getMarkedFieldRow(){
		if(mainFrame != null)
			return mainPanel.getMarkedFieldRow();
		else
			return -1;
	}

	public int getMarkedFieldCol(){
		if(mainFrame != null)
			return mainPanel.getMarkedFieldCol();
		else
			return -1;
	}

	public boolean duringAnimation(){
		if(mainFrame != null)
			return mainPanel.duringAnimation();
		else return false;
	}

	public void startAnimation(int row, int col, long animationSpeed){
		mainPanel.startAnimation(row, col, animationSpeed);
	}
	
	void setLanguage(Locale locale){
		mainFrame.setLanguage(locale);
	}
	

	public void showMainFrame(){
		this.mainFrame = new MainFrame(level, controller, currentLocale, "defaultskin", Color.WHITE, Color.BLACK);
		this.mainFrame.setVisible(true);
		this.mainPanel = mainFrame.getMainPanel();
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

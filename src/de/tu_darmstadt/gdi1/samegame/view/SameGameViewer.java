package de.tu_darmstadt.gdi1.samegame.view;

import java.awt.Color;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.tu_darmstadt.gdi1.samegame.exceptions.InternalFailureException;
import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

import de.tu_darmstadt.gdi1.samegame.controller.AbstractController;
import de.tu_darmstadt.gdi1.samegame.controller.GameController;

import de.tu_darmstadt.gdi1.samegame.view.gameframes.*;

import de.tu_darmstadt.gdi1.samegame.model.Level;

public class SameGameViewer implements ChangeListener{
	public static final Locale DEFAULT_LOCALE = new Locale("en", "US");
	private Locale currentLocale;

	private Level level;
	private AbstractController controller;
	
	private MainFrame mainFrame;
	private MainPanel mainPanel;
	private AddHighscoreFrame addHighscoreFrame;
    private HighscoreFrame highscoreFrame;
    private AboutFrame aboutFrame;
    private String current_Skin = "defaultskin";

	private Color current_BColor = Color.black;
	private Color current_FColor = Color.white;
	int returnv;

	public SameGameViewer(){
		currentLocale = (Locale) DEFAULT_LOCALE.clone();
	}

	public void setLevel(Level level){
		this.level = level;
	}

	public void setController(AbstractController controller){
		this.controller = controller;
	}
	
	public void setSkin(String skin, Color BColor, Color FColor, Color MColor){
		mainFrame.setSkin(skin, BColor, FColor);
		mainPanel.setMarkColor(MColor);
		mainPanel.setBGColor(BColor);
		current_BColor = BColor;
		current_FColor = FColor;
		current_Skin = skin;
		
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
	
	public void setLanguage(Locale locale){
		mainFrame.setLanguage(locale);
		currentLocale = locale;
	}
	
	public Locale getLanguage(){
		return mainFrame.getLanguage();
	}
	
	public void notifyLevelLoaded(){
		if(mainFrame != null){
			try{
				mainFrame.notifyLevelLoaded(level.getFieldWidth(), level.getFieldHeight());
			}catch(ParameterOutOfRangeException e){
				e.printStackTrace();
			}catch(InternalFailureException e){
				e.printStackTrace();
			}
		}
	}

	public void showMainFrame(){
		this.mainFrame = new MainFrame(level, controller, currentLocale, current_Skin, current_BColor, current_FColor);
		this.mainFrame.setVisible(true);
		this.mainPanel = mainFrame.getMainPanel();
		Thread timeUpdate = new Thread(mainFrame);
		timeUpdate.start();
	}

	public void showFileChooseDialog(String source){
	
		JFrame parentframe = new JFrame();
		JFrame.setDefaultLookAndFeelDecorated(false);
		
		if (source == "LoadLevel" || source == "LoadGameState")
		{	JFileChooser chooser = new JFileChooser();
			returnv = chooser.showOpenDialog(parentframe);
			chooser.setVisible(true);
		}
		
		if (source == "SaveLevel" || source == "SaveGameState")
		{	JFileChooser chooser2 = new JFileChooser();
			returnv = chooser2.showSaveDialog(parentframe);
			chooser2.setVisible(true);
		}
	}

	public void showAddHighscoreFrame(){
		addHighscoreFrame = new AddHighscoreFrame(currentLocale);
	}

	public int getReturnValue(){
		return returnv;
	}
	
	public void showHighscoreFrame(){
		highscoreFrame = new HighscoreFrame(currentLocale, level);
	}

	public void showAboutFrame(){
		aboutFrame = new AboutFrame(currentLocale);
	}

	public static void showAlertFrame(String alerttitle, String alertstring){
		JFrame alertframe = new JFrame();
		
		int response = JOptionPane.showConfirmDialog(alertframe, alertstring, alerttitle,
		   JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
	
			if (response == JOptionPane.OK_OPTION){
				alertframe.setVisible(false);
			}
			
			if (response == JOptionPane.CANCEL_OPTION){
				alertframe.setVisible(false);
			}
			
			if (response == JOptionPane.CLOSED_OPTION){
				alertframe.setVisible(false);
			}
	}
		

	
	// TODO proof whether the close methods are needed
	public void closeMainFrame(){
		if(mainFrame != null){
			mainFrame.dispose();
			mainFrame = null;
		}
	}

	public void closeAddHighscoreFrame(){
		addHighscoreFrame = null;
	}

	public void closeHighscoreFrame(){
		highscoreFrame = null;
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
		viewer.notifyLevelLoaded();
	}
}

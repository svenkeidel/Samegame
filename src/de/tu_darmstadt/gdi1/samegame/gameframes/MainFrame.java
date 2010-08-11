package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.BorderLayout;
import java.awt.Container;

import static java.lang.Thread.sleep;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.tu_darmstadt.gdi1.samegame.Level;

import de.tu_darmstadt.gdi1.samegame.ui.GamePanel;
import de.tu_darmstadt.gdi1.samegame.ui.GameWindow;

@SuppressWarnings("serial")
public class MainFrame extends GameWindow implements Runnable{

	private Level level;
	private Locale locale;

	private ResourceBundle messages;

	private MainPanel panel;

	public MainFrame(Level level, Locale locale){
		super("Same Game", level, locale);
		this.level = level;
		this.locale = locale;

		try{
			messages = 
				ResourceBundle.getBundle(
						"de.tu_darmstadt.gdi1.samegame.gameframes.MainBundle", 
						locale, 
						this.getClass().getClassLoader()); 
		}catch(MissingResourceException e){
			this.locale = new Locale("de", "DE");

			messages = 
				ResourceBundle.getBundle(
						"de.tu_darmstadt.gdi1.samegame.gameframes.MainBundle", 
						locale,
						this.getClass().getClassLoader()); 
		}

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(messages.getString("FileMenu_Border"));
		fileMenu.add(new JMenuItem(messages.getString("FileMenu_GenerateLevel")));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(messages.getString("FileMenu_SaveLevel")));
		fileMenu.add(new JMenuItem(messages.getString("FileMenu_LoadLevel")));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(messages.getString("FileMenu_SaveGameState")));
		fileMenu.add(new JMenuItem(messages.getString("FileMenu_LoadGameState")));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(messages.getString("FileMenu_Exit")));

		JMenu viewMenu = new JMenu(messages.getString("ViewMenu_Border"));
		
		JMenu optionsMenu = new JMenu(messages.getString("OptionsMenu_Border"));

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		menuBar.add(optionsMenu);

		this.add(menuBar, BorderLayout.NORTH);
	}

	public void markField(int row, int col){
		panel.markField(row, col);
	}

	@Override
	protected GamePanel createGamePanel(Level level) {
		this.panel = new MainPanel(this, level);
		this.add(panel, BorderLayout.CENTER);
		return panel;
	}

	@Override
	public void run(){
		while(true){
			// TODO do something with the time
			level.getElapsedTime();
			try{
				sleep(1000);
			}catch(InterruptedException ignored){}
		}
	}
}

package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import static java.lang.Thread.sleep;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import de.tu_darmstadt.gdi1.samegame.GameController;
import de.tu_darmstadt.gdi1.samegame.Level;

import de.tu_darmstadt.gdi1.samegame.ui.GamePanel;
import de.tu_darmstadt.gdi1.samegame.ui.GameWindow;

@SuppressWarnings("serial")
public class MainFrame extends GameWindow implements Runnable{

	private Level level;
	private Locale locale;

	private ResourceBundle messages;

	private GameController controller;

	private MainPanel panel;

	private JLabel MinStones;
	private JLabel Points;
	private JLabel ElapsedTime;
	private JLabel TargetTime;
	
		
	public MainFrame(Level level, Locale locale, GameController controller){
		super("Same Game", level, locale);
		this.level = level;
		this.controller = controller;

		this.locale = locale;

		this.setFocusable(true);
		this.addKeyListener(this.controller);
		
		try{
			messages = 
				ResourceBundle.getBundle(
						"de.tu_darmstadt.gdi1.samegame.gameframes.MainBundle", 
						this.locale, 
						this.getClass().getClassLoader()); 
		}catch(MissingResourceException e){
			this.locale = new Locale("de", "DE");

			messages = 
				ResourceBundle.getBundle(
						"de.tu_darmstadt.gdi1.samegame.gameframes.MainBundle", 
						this.locale,
						this.getClass().getClassLoader()); 
		}


		// ========= menu =========
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu(messages.getString("FileMenu_Border"));
		
		
		JMenuItem gen_lvl = new JMenuItem(messages.getString("FileMenu_GenerateLevel"));
		gen_lvl.setName("FileMenu_GenerateLevel");
		fileMenu.add(gen_lvl);
		
		fileMenu.addSeparator();
		
		JMenuItem save_lvl = new JMenuItem(messages.getString("FileMenu_SaveLevel"));
		save_lvl.setName("FileMenu_SaveLevel");
		fileMenu.add(save_lvl);
		
		JMenuItem load_lvl = new JMenuItem(messages.getString("FileMenu_LoadLevel"));
		load_lvl.setName("FileMenu_LoadLevel");
		fileMenu.add(load_lvl);
		
		fileMenu.addSeparator();
		
		JMenuItem save_game = new JMenuItem(messages.getString("FileMenu_SaveGameState"));
		save_game.setName("FileMenu_SaveGameState");
		fileMenu.add(save_game);
		
		JMenuItem load_game = new JMenuItem(messages.getString("FileMenu_LoadGameState"));
		load_game.setName("FileMenu_LoadGameState");
		fileMenu.add(load_game);
		
		fileMenu.addSeparator();
		
		JMenuItem exit = new JMenuItem(messages.getString("FileMenu_Exit"));
		exit.setName("FileMenu_Exit");
		fileMenu.add(exit);
				
		fileMenu.addMenuListener(controller);
		
		
		// TODO write some menu entrys
		JMenu viewMenu = new JMenu(messages.getString("ViewMenu_Border"));
		
		viewMenu.addMenuListener(controller);
		
		// TODO write some menu entrys
		JMenu optionsMenu = new JMenu(messages.getString("OptionsMenu_Border"));
		JMenu setLanguage = new JMenu(messages.getString("SetLanguage"));
		
		
		optionsMenu.addMenuListener(controller);
		setLanguage.addMenuListener(controller);
		
		
		JMenuItem ger_lang = new JMenuItem(messages.getString("German"));
		exit.setName("German");
		setLanguage.add(ger_lang);
	
		JMenuItem eng_lang = new JMenuItem(messages.getString("English"));
		exit.setName("English");
		setLanguage.add(eng_lang);		
		
		
		
		optionsMenu.add(setLanguage);

		menuBar.add(fileMenu);
		menuBar.add(viewMenu);
		menuBar.add(optionsMenu);

		this.add(menuBar, BorderLayout.NORTH);

		
		
		// ===== status line =====
		JPanel statusLine = new JPanel(new BorderLayout());
		JPanel statusLineLabels = new JPanel(new GridLayout(4, 1));
		JPanel statusLineValues = new JPanel(new GridLayout(4, 1, 10, 0));

		statusLineLabels.add(new JLabel(messages.getString("StatusLine_MinStones")));
		MinStones = new JLabel(""+level.getMinStones());
		statusLineValues.add(MinStones);
		
		statusLineLabels.add(new JLabel(messages.getString("StatusLine_Points")));
		Points = new JLabel(""+(int)level.getPoints());
		statusLineValues.add(Points);

		statusLineLabels.add(new JLabel(messages.getString("StatusLine_ElapsedTime")));
		ElapsedTime = new JLabel(""+(int)(level.getElapsedTime()/1000.0));
		statusLineValues.add(ElapsedTime);
		
		statusLineLabels.add(new JLabel(messages.getString("StatusLine_TargetTime")));
		TargetTime = new JLabel(""+level.getTargetTime());
		statusLineValues.add(TargetTime);

		statusLine.add(statusLineLabels, BorderLayout.CENTER);
		statusLine.add(statusLineValues, BorderLayout.EAST);

		this.add(statusLine, BorderLayout.SOUTH);
		this.requestFocus();
	}

	public void redraw(){
		if(level.isFinished()){
			// TODO show finished message
			ElapsedTime.setText(""+level.getElapsedTime()/1000.0);
		}else
			ElapsedTime.setText(""+(int)(level.getElapsedTime()/1000.0));


		Points.setText(""+(int)level.getPoints());
		MinStones.setText(""+level.getMinStones());
		TargetTime.setText(""+level.getTargetTime());
	}

	public void markField(int row, int col){
		panel.markField(row, col);
	}

	public int getMarkedFieldRow(){
		return panel.getMarkedFieldRow();
	}

	public int getMarkedFieldCol(){
		return panel.getMarkedFieldCol();
	}

	
	public boolean duringAnimation(){
		return this.panel.duringAnimation();
	}


	public void startAnimation(int row, int col, long animationSpeed){
		this.panel.startAnimation(row, col, animationSpeed);
	}

	@Override
	protected GamePanel createGamePanel(Level level) {
		this.panel = new MainPanel(this, level, controller);
		this.add(panel, BorderLayout.CENTER);
		return panel;
	}

	@Override
	public void run(){
		// Time update
		while(true){
			redraw();
			try{
				sleep(1000);
			}catch(InterruptedException ignored){}
		}
	}
}

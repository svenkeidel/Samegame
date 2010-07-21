/**
 * @opt constructors
 * @opt attributes
 * @opt operations
 * @opt visablitiy
 *
 * @hidden
 */
class UMLOptions{}

/**
 * @note 
 * Controller: procceses on events 
 * sended by the View and call the 
 * specific function in the Model
 *
 * @depend - "updates state" - Level
 * @composed - - - ContrActList
 * @composed - - - ContrKeyAdpt
 * @composed - - - ContrMenList
 */
class GameController{

	/**
	 * ContrActList implements ActionListener
	 * @opt commentname
	 */
	public class ContrActList implements ActionListener{
		public void actionPerformed(ActionEvent e);
	}

	/**
	 * ContrKeyAdpt extends KeyAdapter
	 * @opt commentname
	 */
	public class ContrKeyAdpt extends KeyAdapter{
		public void keyPressed(KeyEvent e);
	}

	/**
	 * ContrMenList implements MenuListener
	 * @opt commentname
	 */
	public class ContrMenList implements MenuListener{
		public void menuCanceled(MenuEvent e);
		void menuDeselected(MenuEvent e);
		void menuSelected(MenuEvent e);
	}
}

/**
 * @note 
 * Model: The Level-data in the game. 
 * When the Model changes its state, 
 * it causes an ChangeEvent to the 
 * View-ChangeListener
 * @composed 1 - 1 Highscore
 * @composed 1 - 1 GameState
 * @navassoc - "causes state change events" - SameGameViewer
 */
class Level {
	private int targetTime;
	private int minStones;
	private GameState ORIGINAL_LEVEL_STATE;
	private GameState currentGameState;
	private CompoundEdit gameStateHistory;
	private ChangeListener changeListener;
	private Highscore highscore;
	public Level(ChangeListener changeListener);
	public Level(File f, ChangeListener changeListener);
	public Level(int targetTime, int minStones, byte[][] field, ChangeListener changeListener);
	private void init(ChangeListener changeListener);
	public int getPoints();
	public byte[][] getFieldState();
	public String[][] getHighscore();
	public void insertHighscore(int points, int remainingTime, String name);
	void setTargetTime(int targetTime);
	void setMinStones(int minStones);
	public void restartLevel();
	public boolean undo();
	public boolean redo();
	public void generateLevel();
	private boolean validateLevelSemantical(byte[][] level, int numOfCol);
	private void parseLevel(File f);
	private String parseLine(String pattern, LineNumberReader line, Scanner s, File f) ;
	private Byte[] parseByteDigits(String s);
	private void parseAdditionalLevelInf(LineNumberReader line, Scanner s, File f);
	public boolean storeLevel(File f);
	public boolean restoreLevel(File f);
	private boolean validateLevel();
	public boolean removeable(int row, int col);
	public boolean removeStone(int row, int col);
	private void moveUp();
	public boolean isFinished();
	private void updatePoints(int stonesRemoved);
	public String toString();
}

/**
 * @note 
 * View: The Presentation Layer. Is
 * updated by changing events caused
 * by the Model
 * @depend - "listen for state change" - Level
 * @navassoc 1 "sends events" 1 GameController
 * @navassoc - "displays" - MainFrame
 * @navassoc - "displays" - OptionsFrame
 * @navassoc - "displays" - AskForSaveFrame
 * @navassoc - "displays" - AddHighscoreFrame
 * @navassoc - "displays" - HighscoreFrame
 * @navassoc - "displays" - SaveGameFrame
 * @navassoc - "displays" - LoadGameFrame
 * @navassoc - "displays" - AboutFrame
 */
class SameGameViewer implements ChangeListener{
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

	public void stateChanged(ChangeEvent e);
	public void printContent();

	public void timePoller();
	public void markField(int row, int col);

	public void showMainFrame();
	public void showOptionsFrame();
	public void showAskForSaveFrame();
	public void showAddHighscoreFrame();
	public void showHighscoreFrame();
	public void showSaveGameFrame();
	public void showLoadGameFrame();
	public void showAboutFrame();

	public void closeMainFrame();
	public void closeOptionsFrame();
	public void closeAskForSaveFrame();
	public void closeAddHighscoreFrame();
	public void closeHighscoreFrame();
	public void closeSaveGameFrame();
	public void closeLoadGameFrame();
	public void closeAboutFrame();
}

/**
 * TimeUpdate extends Thread
 * @opt commentname
 * @navassoc - - - Level
 */
class TimeUpdate extends Thread{
	private StopWatch watch;
	public void run();
}

/** @opt !constructors */
class MainFrame{}
/** @opt !constructors */
class HighscoreFrame{}
/** @opt !constructors */
class AskForSaveFrame{}
/** @opt !constructors */
class AboutFrame{} 
/** @opt !constructors */
class SaveGameFrame{}
/** @opt !constructors */
class OptionsFrame{} 
/** @opt !constructors */
class LoadGameFrame{}
/** @opt !constructors */
class AddHighscoreFrame{}

/**
 * @composed 1 - n HighscoreEntry
 */
class Highscore{
	private Vector <HighscoreEntry> highscoreEntrys;

	Highscore(Vector<HighscoreEntry> highscoreEntrys);
	Highscore(LineNumberReader line, Scanner s, File f);

	void insertHighscore(int points, int remainingTime, String name);
	public String toString();
	public String[][] getHighscoreEntrys();
	private void parseHighscoreEntrys(LineNumberReader line, Scanner s, File f);
}

class HighscoreEntry{
	private int points;
	private int remaingTime;
	private Date date;
	private String name;
	private final static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH;mm;ss");

	HighscoreEntry(int points, int remaingTime, Date date, String name);
	HighscoreEntry(LineNumberReader line, Scanner s, File f);

	public int getPoints();
	public int getRemaingTime();
	public Date getDate();
	public String getName();
	public int compareTo(HighscoreEntry another);
	public String[] toStringArray();
	public String toString();
	private void parseHighscoreEntry(LineNumberReader line, Scanner s, File f);
}

/**
 * GameState extends javax.swing.undo.AbstractUndoableEdit
 * @opt commentname
 */
class GameState extends AbstractUndoableEdit{
	private byte[][] fieldState;
	private int points; 

	public GameState(byte[][] fieldState, int points);
	public Object clone();
}


/**
 * @hidden
 */
class File{}

/**
 * @hidden
 */
class Vector<T>{}

/**
 * @hidden
 */
class AbstractUndoableEdit{}

/**
 * @hidden
 */
class CompoundEdit{}

/**
 * @hidden
 */
interface ChangeListener{}

/**
 * @hidden
 */
class ChangeEvent{}

/**
 * @hidden
 */
class ActionEvent{}

/**
 * @hidden
 */
class KeyEvent{}

/**
 * @hidden
 */
class MenuEvent{}

/**
 * @hidden
 */
interface ActionListener{}

/**
 * @hidden
 */
class KeyAdapter{}

/**
 * @hidden
 */
interface MenuListener{}

/**
 * @hidden
 */
class LineNumberReader{}

/**
 * @hidden
 */
class Scanner{}

/**
 * @hidden
 */
class Date{}

/**
 * @hidden
 */
class SimpleDateFormat{}

/**
 * @hidden
 */
class Thread{}

/**
 * @hidden
 */
class StopWatch{}

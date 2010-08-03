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
 */
class GameController{
	private Level level;
	public GameController(Level level){}
	public void setLevel(Level level){}
	public void actionPerformed(ActionEvent e){}
	public void keyPressed(KeyEvent e){}
	public void menuCanceled(MenuEvent e){}
	public void menuDeselected(MenuEvent e){}
	public void menuSelected(MenuEvent e){}
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
	private ChangeListener changeListener;
	private Highscore highscore;
	private static  String ADDITIONAL_LEVEL_INF;
	private StopWatch watch;
	private File loadedLevel;
	public Level( ChangeListener changeListener);
	public Level( File f,  ChangeListener changeListener);
	public Level( int targetTime,  int minStones,  Byte[][] field,  ChangeListener changeListener);
	public Level( String levelstring,  ChangeListener changeListener);
	private void init( ChangeListener changeListener);
	public double getPoints();
	public Byte[][] getFieldState();
	public String[][] getHighscore();
	public void insertHighscore( String playername,  double remTime,  Date creationDate,  double points);
	public void resetHighscore();
	void setTargetTime( int targetTime);
	public int getTargetTime();
	void setMinStones( int minStones);
	public int getMinStones();
	public String getAdditionalLevelInf();
	public String getLevelStateInf();
	public String getOrigLevelState();
	public String getCurrentLevelState();
	public String getHighscorelist();
	public void restartLevel();
	public void undo();
	public void redo();
	public void generateLevel( int width,  int height,  int numberOfColors,  int minStones);
	public void generateLevel();
	private static boolean validateSemantical( Byte[][] level,  int numOfCol,  int minStones);
	public static void validateSyntactical( String levelString);
	public static void validateAdditionalLevelInf(  String addLevelInf);
	public void loadLevelFromString( String levelString);
	private Byte[] parseByteDigits( String s);
	private void parseAdditionalLevelInf( String additionalLevelInf);
	public static boolean removeable( Byte[][] state,  int minStones,  int row,  int col);
	public boolean removeable( int row,  int col);
	private static void countableFloodFill(Byte[][] state,  int row,  int col,  byte color,  byte newCol);
	private static void removeFloodFill(Byte[][] state,  int row,  int col,  byte color, Integer stonesRemoved);
	public boolean removeStone( int row,  int col);
	public static void moveUp(Byte[][] state);
	public boolean isFinished();
	public static boolean isFinished(Byte[][] state, int minStones);
	void updateState(Byte[][] state, int removedElements);
	public double calculatePoints(Byte[][] state, int removedElements);
	public static double calculatePoints(int removedElements, boolean afterTargetTime);
	public double calculatePointsFinished( Byte[][] state);
	public static double calculatePointsFinished(int elementsLeft, int timeLeft, int initialElements);
	private void store( String inf,  File f,  boolean force);
	public void storeLevelState( File f, boolean force);
	public void storeLevel( File f, boolean force);
	public void restoreLevelState( File f);
	public void restoreLevel( File f);
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
	private Vector<HighscoreEntry> highscoreEntrys;
	public  static String HIGHSCORE_ENTRY = HighscoreEntry.HIGHSCORE_ENTRY;
	public Highscore();
	public Highscore(Vector<HighscoreEntry> highscoreEntrys);
	public Highscore(String entrys);
	public String[][] getHighscoreEntrys();
	public int getHighscoreCount();
	public String getPlayername(int position);
	public double getRemaining(int position);
	public double getPoints(int position);
	public Date getDate(int position);
	private void inRange(int position);
	public void insertHighscore( String playername,  double remTime,  Date creationDate,  double points);
	public static void validate(String highscoreList);
	private void parseHighscoreEntrys(String entrys);
	public String toString();
}

class HighscoreEntry{
	private String name;
	private double remainingTime;
	private double points;
	private Date date;
	private  static SimpleDateFormat df;
	private  static String HIGHSCORE_INFORMATION;
	public static  String HIGHSCORE_ENTRY;
	HighscoreEntry( String playername,  double rem_time,  Date creation_date,  double points);
	HighscoreEntry(String highscoreEntry, int line);
	public double getPoints();
	public double getRemainingTime();
	public Date getDate();
	public String getPlayername();
	public static void validate(String highscoreEntry, int line);
	private void parseHighscoreEntry(String highscoreEntry, int line);
	public int compareTo(HighscoreEntry another);
	public String[] toStringArray();
	public String toString();
}

/**
 * GameState extends javax.swing.undo.AbstractUndoableEdit
 * @opt commentname
 */
class GameState{
	public GameState(Byte[][] fieldState, double points);
	public Byte[][] getFieldState();
	public void setFieldState(Byte[][] fieldState);
	public double getPoints();
	public void setPoints(double points);
	public Object clone();
	public String toString();
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

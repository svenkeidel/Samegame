/**
 * @opt constructors
 * @opt attributes
 * @opt operations
 * @opt visablitiy
 *
 * @hidden
 */
class UMLOptions{}


class GameController extends AbstractController{
	private Level level;
	public GameController(Level level);
	public GameController(Level level, SameGameViewer viewer);

	public void setLevel(Level level);
	public void menuClick(JMenuItem menuItem);

	public void fieldClick(ActionEvent e, JButton b);
	public void fileChoosed(String source, File f);

	public void entityClicked(int positionX, int positionY);
	public void keyPressed(KeyEvent e);
}

/**
 * @note
 * controller
 * @depend - "updates state" - Level
 */
abstract class AbstractController extends KeyAdapter implements ActionListener{
	protected Level level;
	public void setLevel(Level level);
	public final void actionPerformed(ActionEvent e);
	public abstract void menuClick(JMenuItem menuItem);
	public abstract void fieldClick(ActionEvent e, JButton b);
	public abstract void fileChoosed(String source, File f);
	public abstract void keyPressed(KeyEvent e);
}

/**
 * @note
 * model
 * @composed 1 - 1 Highscore
 * @navassoc 1 administrate m GameState
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
 * view
 * @depend - "listen for state change" - Level
 * @navassoc 1 "sends events" 1 AbstractController
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
	public static final Locale DEFAULT_LOCALE;
	private Locale currentLocale;
	private Level level;
	private AbstractController controller;
	private MainFrame mainFrame;
	private MainPanel mainPanel;
	private AddHighscoreFrame addHighscoreFrame;
    private HighscoreFrame highscoreFrame;
    private SaveGameFrame saveGameFrame;
    private LoadGameFrame loadGameFrame;
    private AboutFrame aboutFrame;
	private Color BColor;
	private Color FColor;

	public SameGameViewer();

	public void setLevel(Level level);
	public void setController(AbstractController controller);
	public void setSkin(String skin, Color BColor, Color FColor, Color MColor);
	public void stateChanged(ChangeEvent e);
	public void markField(int row, int col);
	public MainPanel getMainPanel();
	public int getMarkedFieldRow();
	public int getMarkedFieldCol();
	public boolean duringAnimation();
	public void startAnimation(int row, int col, long animationSpeed);
	public void setLanguage(Locale locale);
	public void notifyLevelLoaded();
	public void showMainFrame();
	public void showFileChooseDialog(String source);
	public void showAddHighscoreFrame();
	public void showHighscoreFrame();
	public void showAboutFrame();
	public static void showAlertFrame(String alertstring, String alerttitle);
	public void closeMainFrame();
	public void closeAddHighscoreFrame();
	public void closeHighscoreFrame();
	public void closeAboutFrame();
	public static void main(String args[]);
}

/**
 * @composed 1 - 1 MainPanel
 */
class MainFrame{
	private Level level;
	private Locale locale;
	private ResourceBundle messages;
	private AbstractController controller;
	private MainPanel panel;
	private JMenuBar menuBar;
	private JPanel statusLine;
	private JLabel MinStones;
	private JLabel Points;
	private JLabel ElapsedTime;
	private JLabel TargetTime;
	private JLabel sl_MinStones;
	private JLabel sl_Points;
	private JLabel sl_ElapsedTime;
	private JLabel sl_TargetTime;
	private String skin;
	private Color FColor;
	private Color BColor;
	public MainFrame(Level level, AbstractController controller, Locale locale, String skin, Color FColor, Color BColor);
	public MainPanel getMainPanel();
	public void setLanguage(Locale locale);
	public void setSkin(String skin, Color FColor, Color BColor);
	public void updateContents();
	public void notifyLevelLoaded(int width, int height);
	public void redraw();
	public void run();
}

class MainPanel{
	private Level level;
	private AbstractController controller;
	private int markedRow, markedCol;
	private boolean duringAnimation;
	private Color bgColor = Color.black;
	private Byte[][] field;
	private Color markColor = Color.white;
	private Vector<JButton> entities = null;
	private HashMap<String, ImageIcon> images = null;
	private MainFrame parentWindow = null;
	private int layoutWidth = 0, layoutHeight = 0;
	private boolean autosize = false;
	public MainPanel(MainFrame parentWindow, Level level, AbstractController controller, String skin);
	public int getMarkedFieldRow();
	public void setBGColor(Color color);
	public int getMarkedFieldCol();
	public MainFrame getParentWindow();
	public void setSkin(String skin);
	public void setAutosize (boolean Autosize);
	public boolean hasEntities();
	public void redraw();
	private void clearFrame();
	void notifyLevelLoaded(int width, int height);
	private void resizePanel();
	public void markField(int row, int col);
	public boolean isImageRegistered(String identifier);
	public void registerImage(String identifier, String fileName);
	public void registerImage(String identifier, URL fileName);
	public void unregisterImage(String identifier);
	private JButton placeEntity(String imageIdentifier, boolean marked);
	private JButton placeEntity(Image image, boolean marked);
	public void setMarkColor(Color mcolor);
	private JButton placeEntity(Icon icon, boolean marked);
	public boolean duringAnimation();
	void endAnimation();
	public void startAnimation(int row, int col, long animationSpeed);
	public void setGamePanelContents();
}

/** @opt !constructors */
class HighscoreFrame{}
/** @opt !constructors */
class AskForSaveFrame{}
/** @opt !constructors */
class AboutFrame{} 
/** @opt !constructors */
class SaveGameFrame{}
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

class GameState{
	private Byte[][] fieldState;
	private double points;
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
class JMenuItem{}

/**
 * @hidden
 */
class JMenuBar{}

/**
 * @hidden
 */
class JMenu{}

/**
 * @hidden
 */
class JPanel{}

/**
 * @hidden
 */
class JLabel{}

/**
 * @hidden
 */
class JButton{}

/**
 * @hidden
 */
class Locale{}

/**
 * @hidden
 */
class ResourceBundle{}

/**
 * @hidden
 */
class Color{}

/**
 * @hidden
 */
class URL{}

/**
 * @hidden
 */
class Icon{}

/**
 * @hidden
 */
class ImageIcon{}

/**
 * @hidden
 */
class Image{}

/**
 * @hidden
 */
class Vector<T>{}

/**
 * @hidden
 */
class HashMap<T,E>{}

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

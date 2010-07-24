package de.tu_darmstadt.gdi1.samegame;

import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;
import de.tu_darmstadt.gdi1.samegame.highscore.Highscore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Vector;
import java.util.Random;

import java.util.InputMismatchException;

import javax.swing.undo.CompoundEdit;
import javax.swing.event.ChangeListener;

/**
 * The Model of the MVC Design pattern. It contains the level data and
 * implements it's buissnes logic, wich means that it nows how to
 * change its state. If this state is changed, the Model invokes a
 * ChangeEvent, wich is listend by the View class, so that the View
 * can update it's content
 */
public class Level {

	////////////////////////Class/Attributes//////////////////////////
	// the time to finish the level
	private int targetTime;

	// minimal number of Stones wich can be deleted
	private int minStones;

	/**
	 * the original game state, in order to reset the current state 
	 * to it
	 */
	private GameState ORIGINAL_LEVEL_STATE;

	/**
	 * the current game state. It contains the field state and the
	 * number of points the player reached. Is keept under Undo/Redo
	 * history by the CompoundEdit-Manager
	 */
	private GameState currentGameState;

	/**
	 * a class to manage undo and redo operations on the game state
	 * @see javax.swing.undo.CompoundEdit
	 */
	private CompoundEdit gameStateHistory;

	/**
	 * the listener from the view, wich is used to tell the view to
	 * update
	 */
	private ChangeListener changeListener;

	/**
	 * The Highscore of the Level. It is read from the level file,
	 * during runtime stored in this attribute and can been rewritten 
	 * to the level file after it is updated
	 */
	private Highscore highscore;



	// Pattern for additional level information
	private static final Pattern ADITTIONAL_LEVEL_INF = 
		Pattern.compile("^###(target_time:\\d*|min_stones:\\d*)"
				+"(\\|(target_time:\\d*|min_stones:\\d*))?$", Pattern.MULTILINE);

	// Pattern for highscore entrys
	private static final Pattern HIGHSCORE_ENTRY =
		Pattern.compile(
				// a "###" at the beginning of the line with one of the highscore
				// informations like (name|points|date|remaining time)
				"^###(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)"
				// With a following "|" and another highscore information
				+"(\\|(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)){3}$", 
				Pattern.MULTILINE);

	////////////////////END/Class/Attributes//////////////////////////

	
	////////////////////////Class/Constructors////////////////////////
	/**
	 * Class constructor to instanciate a level and generate a new field
	 * @param changeListener the listener from the view
	 */
	public Level(ChangeListener changeListener){
		generateLevel();

		init(changeListener);
	}

	/**
	 * Class constructor to instanciate a level and read the level date
	 * from a file on the disc
	 * @param f the file wich contains the level date
	 * @param changeListener the listener from the view
	 */
	public Level(File f, ChangeListener changeListener)
		throws FileNotFoundException, WrongLevelFormatException, IOException{

		storeLevel(f);

		init(changeListener);
	}

	/**
	 * Class constructor to instanciate a level and set specific
	 * attributes given as params
	 * @param targetTime the time in which the player should finish the
	 * level
	 * @param minStones the number of stones needed to apply an delete
	 * operation
	 * @param changeListener the listener from the view
	 */
	public Level(int targetTime, 
				 int minStones,
				 Byte[][] field, 
				 ChangeListener changeListener){

		this.targetTime = targetTime;
		this.minStones = minStones;

		currentGameState = new GameState(field, 0);

		init(changeListener);
	}

	/**
	 * Construct a level from a String. The function parses the string.
	 * if the level isn't valide, it throws a WrongLevelFormatException
	 * @param levelstring the string to be load
	 * @throws WrongLevelFormatException
	 */
	public Level(String levelstring) throws WrongLevelFormatException{
		loadLevelFromString(levelstring);
	}

	/**
	 * helping function to initialize a few class attributes
	 */
	private void init(ChangeListener changeListener){
		try{
			ORIGINAL_LEVEL_STATE = (GameState) currentGameState.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
			ORIGINAL_LEVEL_STATE = new GameState(null, 0);
		}

		this.changeListener = changeListener;

		gameStateHistory = new CompoundEdit();
		gameStateHistory.addEdit(currentGameState);
	}
	////////////////////END/Class/Constructors////////////////////////


	////////////////////////Getters//&//Setters///////////////////////
	/**
	 * get the points the player reached at the moment
	 * @return the points the player reached at the moment
	 */
	public int getPoints(){
		return currentGameState.getPoints();
	}

	/**
	 * gets a copy of the current field state
	 * @return a copy of the current field state
	 */
	public Byte[][] getFieldState(){
		return currentGameState.getFieldState();
	}

	/**
	 * Get a highscore list in form of a two dimensional String array.
	 * 1. Column: points<br>
	 * 2. Column: remaining time<br>
	 * 3. Column: date<br>
	 * 4. Column: player's name
	 * @return a String representation in the above described form
	 */
	public String[][] getHighscore(){
		return highscore.getHighscoreEntrys();
	}

	/**
	 * insert a new highscore into the highscore-list of the level
	 * @param points the points the player has reached
	 * @param remainingTime the time remaining
	 * @param name the name of the player
	 */
	public void insertHighscore(int points, int remainingTime, String name){
		highscore.insertHighscore(points, remainingTime, name);
	}

	/**
	 * sets the target time in wich the player has to finish the level
	 * @param targetTime the time the player has to finish the level
	 */
	void setTargetTime(int targetTime){
		this.targetTime = targetTime;
	}

	/**
	 * sets the minimal number of Stones needed to cause remove 
	 * operation
	 * @param minStones the minimal number of Stones for a Remove
	 */
	void setMinStones(int minStones){
		this.minStones = minStones;
	}
	///////////////////End//Getters//&//Setters///////////////////////

	/**
	 * Resets the current level state and delete the undo/redo history
	 */
	public void restartLevel(){
		try{
			currentGameState = (GameState) ORIGINAL_LEVEL_STATE.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
			currentGameState = new GameState(null, 0);
		}

		gameStateHistory = new CompoundEdit();		
		gameStateHistory.addEdit(currentGameState);
	}

	/**
	 * undo to the last game state
	 * @return return true if an undo is possible
	 */
	public boolean undo(){
		if(gameStateHistory.canUndo()){
			gameStateHistory.undo();
			return true;
		}else{
			return false;
		}
	}

	/**
	 * redo to the previous undone action
	 * @return return true if a redo was possible
	 */
	public boolean redo(){
		if(gameStateHistory.canRedo()){
			gameStateHistory.redo();
			return true;
		}else{
			return false;
		}
	}

	/**
	 * generates a random level.
	 * The random values are:<br>
	 * level-width [6, 30]<br>
	 * level-height [5, 20]<br>
	 * number of Colors [2, 5]<br>
	 * minimum number of Stones to remove [2, 5]
	 */
	public void generateLevel(){
		Random r = new Random();
		int rows = 6 + r.nextInt(25);
		int cols = 5 + r.nextInt(16);

		Byte[][] level = new Byte[rows][cols];

		int numOfColors = 2 + r.nextInt(4);
		this.minStones = 2 + r.nextInt(4);

		do{
			for(int i = 0; i<rows; i++)
				for(int j=0; j<cols; j++)
					level[i][j] = new Byte((byte) (1 + r.nextInt(numOfColors+1)));
		}while(!validateLevel(level));

		this.targetTime = rows * cols;

		currentGameState = new GameState(level, 0);
	}
	
	public boolean validateLevelSyntactical(Byte[][] level){
		for(int i = 0; i<rows; i++)
			for(int j=0; j<cols; j++)
				if(level[i][j] < 1 || level[j][])

		return true;
	}

	/**
	 * check a level for semantical correctness, that means that it
	 * returns false if there are not enough stones of each color or 
	 * none removable stonegroup
	 * @return true if it is a semantical correct level
	 */
	public boolean validateLevelSemantical(Byte[][] level){
		int rows = level.length;
		int cols = level[0].length;

		int[] colorSpread = new int[5];
		// check if the level just contains values in range [1, 5]
		for(int i = 0; i<rows; i++)
			for(int j=0; j<cols; j++)
				colorSpread[level[i][j]-1]++;
		
		for(int i = 0; i<colorSpread.length; i++)
			if(colorSpread[i] < minStones && colorSpread[i] != 0)
				return false;

		// check if the level contains minimum one removable element
		boolean removableExists = false;
		for(int i = 0; i<rows; i++)
			for(int j=0; j<cols; j++)
				if(!removableExists && removeable(level, i, j))
					removableExists = true;

		if(!removableExists)
			return false;

		for(int i = 0; i<colorSpread.length; i++)
			if(colorSpread[i] < minStones && colorSpread[i] != 0)
				return false;

		return true;
	}

	/**
	 * function wich parses a level from a String.
	 * the string !!!must!!! have the following format:<br>
	 * a matrix with [6,30] columns and [5,20] rows:<br>
	 * 4232342322123<br>
	 * 6433456532345<br>
	 * 3453461253526<br>
	 * 1423412534142<br>
	 * 1523542431233<br>
	 * then a optional line with additional level informations:<br>
	 * ###target_time:120|min_stones:2<br>
	 * and finally maximal 10 highscore entrys in the form:<br>
	 * ###name:xyz|points:88888|date:23.05.2010 23;59;12|rem_time:123
	 * @param f the file wich should be parsed
	 * @throws WrongLevelFormatException if the level is not in the
	 * described format
	 */
	public void loadLevelFromString(String levelString) 
		throws WrongLevelFormatException{
		Scanner s;
		LineNumberReader line = 
			new LineNumberReader(
					new StringReader(levelString));

		s = new Scanner(line);
		
		Vector<Byte[]> parsedLevel = new Vector<Byte[]>();
		int cols;
		
		final Pattern FIRST_LINE = Pattern.compile("^\\d+$", Pattern.MULTILINE);

		// check the first line if it is the right format
		String parsedLine = parseLine(FIRST_LINE, "Level Informations needed", line, s);
		cols = parsedLine.length();
		parsedLevel.add(parseByteDigits(parsedLine));

		// the following level line must have the length of the first line
		final Pattern LEVEL_LINE = Pattern.compile("^\\d"+cols+"$", Pattern.MULTILINE);

		// get all other level lines
		while(s.hasNext(LEVEL_LINE)){
			parsedLine = 
				parseLine(LEVEL_LINE, 
						  "each Level must have the same length "
						  +"or the line is in an invalid format",
						  line, s);
			parsedLevel.add(parseByteDigits(parsedLine));
		}

		// sets the additional level information to default values which are
		// overwritten if there were scanned others
		this.targetTime = line.getLineNumber()*cols;
		this.minStones = 2;

		// if there are additional level information or highscore entrys
		if(s.hasNextLine()){

			// if there are additional level information
			if(s.hasNext(ADITTIONAL_LEVEL_INF)){
				parseAdditionalLevelInf(line, s);
			}

			// if there are highscore entrys
			else if(s.hasNext(HIGHSCORE_ENTRY)){ 
				this.highscore = new Highscore(line, s);
			}
			else
				throw new WrongLevelFormatException(
						"unexcepted characters while parsing highscoreList from string"
						+" at line "+line.getLineNumber()+1);
		}

		// if everything goes right, set the field to the parsed one
		Byte[][] field = new Byte[line.getLineNumber()+1][cols];
		parsedLevel.toArray(field);
		this.currentGameState = new GameState(field, 0);

		s.close();
	}

	private String parseLine(Pattern p, String message, LineNumberReader line, Scanner s) 
		throws WrongLevelFormatException{
		if(!s.hasNext(p))
			throw new WrongLevelFormatException(
					"wrong level format while parsing from string at line "
					+line.getLineNumber()+": "+ message);
		return s.next();
	}

	private void parseAdditionalLevelInf(LineNumberReader line, Scanner s)
		throws WrongLevelFormatException{

		s.next("^###");

		String[] infos = new String[2];
		int[] values = new int[2];
		
		// scanning informations
		for(int i=0; i<2; i++){
			infos[i] = s.next("(\\w|_)*");
			s.next(":");
			try{
				values[i] = s.nextInt();
			}catch(InputMismatchException e){ // if there is a wrong integer value
				throw new WrongLevelFormatException(
						"wrong level format while parsing additional Level informations "+ 
						"from string in line "+line.getLineNumber()+": " + e.getMessage());
			}
			if(s.hasNext("|"))
				s.next("|");
			else
				break;
		}
		
		// if there are duplicated informations
		if(infos[0].equals(infos[1]))
			throw new WrongLevelFormatException(
					"wrong level format while parsing additional Level informations "+ 
					"from string in line "+line.getLineNumber()+": dublicated level informations");
		
		// set values to class attributes
		for(int i=0; i<2; i++){
			if(infos[i].equals("target_time"))
				this.targetTime = values[i];
			else if(infos[i].equals("min_stones"))
				this.minStones = values[i];
		}
	}

	private Byte[] parseByteDigits(String s) throws NumberFormatException{
		Byte[] parsedArray = new Byte[s.length()];
		for(int i=0; i<s.length(); i++)
			parsedArray[i] = Byte.parseByte(""+s.charAt(i));
		return parsedArray;
	}

	public boolean storeLevel(File f) 
		throws FileNotFoundException, WrongLevelFormatException, IOException{
		return false;
	}

	public boolean restoreLevel(File f)
		throws FileNotFoundException, WrongLevelFormatException, IOException{
		BufferedReader r = new BufferedReader(new FileReader(f));

		char[] levelString = new char[(int)f.length()];
		r.read(levelString, 0, (int)f.length());
		loadLevelFromString(new String(levelString.toString()));
		return false;
	}

	public boolean removeable(Byte[][] state, int row, int col){
		Byte[][] stateCopy = new Byte[state.length][state[0].length];
		for(int i=0; i<state.length; i++)
			System.arraycopy(state[i], 0, stateCopy[i], 0, state[i].length);
		
		int rows = state.length;
		int cols = state[0].length;
		byte color = state[row][col];
		if(row >= 0 && col >= 0 && row < rows && col < cols){
			stateCopy[row][col] = 0;
			floodFill(stateCopy, row -1, col, color);
			floodFill(stateCopy, row +1, col, color);
			floodFill(stateCopy, row, col -1, color);
			floodFill(stateCopy, row, col +1, color);
		}

		int stonesToRemove = 0;
		for(int i=0; i<rows; i++)
			for(int j=0; j<cols; j++)
				if(stateCopy[i][j] == 0)
					stonesToRemove++;
		return stonesToRemove >= minStones;
	}

	private void floodFill(Byte[][] state, int row, int col, int color){

		int rows = state.length;
		int cols = state[0].length;
		if(row >= 0 && col >= 0 && row < rows && col < cols && state[row][col] == color){
			state[row][col] = 0;
			floodFill(state, row -1, col, color);
			floodFill(state, row +1, col, color);
			floodFill(state, row, col -1, color);
			floodFill(state, row, col +1, color);
		}
	}

	public boolean removeStone(int row, int col){
		// TODO Write method stub
		return false;
	}
	private void moveUp(){
		// TODO Write method stub
	}
	public boolean isFinished(){
		// TODO Write method stub
		return false;
	}
	private void updatePoints(int stonesRemoved){
		// TODO Write method stub
	}

	@Override
	public String toString(){
		StringBuffer output = new StringBuffer();
		for(int i=0; i<currentGameState.getFieldState().length; i++){
			for(int j=0; j<currentGameState.getFieldState()[i].length; j++)
				output.append(currentGameState.getFieldState()[i][j]);
			output.append("\n");
		}
		return "";
	}
}

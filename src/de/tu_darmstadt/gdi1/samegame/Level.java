package de.tu_darmstadt.gdi1.samegame;

import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;
import de.tu_darmstadt.gdi1.samegame.highscore.Highscore;
import static de.tu_darmstadt.gdi1.samegame.highscore.Highscore.HIGHSCORE_ENTRY;
import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

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

import javax.swing.undo.CompoundEdit;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


/**
 * The Model of the MVC Design pattern. It contains the level data and
 * implements it's buissnes logic, wich means that it nows how to
 * change its state. If this state is changed, the Model invokes a
 * ChangeEvent, wich is listend by the View class, so that the View
 * can update it's content
 */
public class Level {

	////////////////////////Class/Attributes//////////////////////////
	/**
	 * the time to finish the level
	 */
	private int targetTime;


	/**
	 * minimal number of Stones wich can be deleted
	 */
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


	/**
	 * Pattern for the first line of a levelstring
	 */
	private static final Pattern FIRST_LINE = 
		Pattern.compile("^[1-5]+$", Pattern.MULTILINE);


	/**
	 * Pattern for additional level information
	 */
	private static final String ADDITIONAL_LEVEL_INF = 
		"^###(target_time:\\d*|min_stones:\\d*)"
	   +"(\\|(target_time:\\d*|min_stones:\\d*))?";
	////////////////////END/Class/Attributes//////////////////////////

	
	////////////////////////Class/Constructors////////////////////////
	/**
	 * Class constructor to instanciate a level without a field state
	 * @param changeListener the listener from the view
	 */
	public Level(ChangeListener changeListener){
		currentGameState = new GameState(null, 0);

		init(changeListener);
	}


	/**
	 * Class constructor to instanciate a level and read the level date
	 * from a file on the disc
	 * @param f the file wich contains the level date
	 * @param changeListener the listener from the view
	 */
	public Level(File f, ChangeListener changeListener)
		throws FileNotFoundException, WrongLevelFormatException, 
			   IOException{

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
	public Level(String levelstring, ChangeListener changeListener) throws WrongLevelFormatException{
		loadLevelFromString(levelstring);
		
		init(changeListener);
	}


	/**
	 * helping function to initialize a few class attributes
	 * @param changeListener the listener from the viewer class
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
	public void insertHighscore(int points, 
								int remainingTime, 
								String name){

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


	////////////////////////Class/Operations//////////////////////////
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
					level[i][j] = 
						new Byte((byte) (1 + r.nextInt(numOfColors)));
		}while(!validateSemantical(level, numOfColors, minStones));

		this.targetTime = rows * cols;

		currentGameState = new GameState(level, 0);
	}
	

	/**
	 * check a level for semantical correctness, that means that it
	 * returns false if there are not enough stones of each color or 
	 * none removable stonegroup
	 * @return true if it is a semantical correct level
	 */
	private static boolean validateSemantical(Byte[][] level, 
											  int numOfCol, 
											  int minStones){

		int rows = level.length;
		int cols = level[0].length;

		int[] colorSpread = new int[numOfCol];
		// check if the level just contains values in range [1, 5]
		for(int i = 0; i<rows; i++)
			for(int j=0; j<cols; j++)
				colorSpread[level[i][j]-1]++;
		
		for(int i = 0; i<colorSpread.length; i++)
			if(colorSpread[i] < minStones)
				return false;

		// check if the level contains minimum one removable element
		if(isFinished(level, minStones))
			return false;

		return true;
	}


	/**
	 * validates if a level string is syntactical correct.
	 * @see #loadLevelFromString(String) for the explenation of the 
	 * level format
	 * @param levelString
	 * @throws WrongLevelFormatException if the level is not in the 
	 * described format
	 */
	public static void validateSyntactical(String levelString)
			throws WrongLevelFormatException{
		Scanner s;
		LineNumberReader line = 
			new LineNumberReader(
					new StringReader(levelString));

		s = new Scanner(line);

		// get the length of the first line
		if(!s.hasNextLine())			
			throw new WrongLevelFormatException(
				"Wrong level format while parsing level from "
				+"string: no level informations available");
			
			
		String parsedLine = s.nextLine();
		int cols = parsedLine.length();
		
		if(cols == 0)			
			throw new WrongLevelFormatException(
				"Wrong level format while parsing level from "
				+"string: no level informations available");

		// the following level line must have the length of the 
		// first line
		final String LEVEL_LINE = "^[0-5]{"+cols+"}";

		// check level lines
		while(true){
			
			if(Pattern.matches("^###.*", parsedLine))
				break;
			
			if(!Pattern.matches(LEVEL_LINE, parsedLine))
				throw new WrongLevelFormatException(
						"Wrong level format while parsing level from "
						+"string in line "+line.getLineNumber()+": "
						+parsedLine);
			
			if(s.hasNextLine())
				parsedLine = s.nextLine();
			else break;
		}

		if(Pattern.matches(ADDITIONAL_LEVEL_INF, parsedLine)){
			validateAdditionalLevelInf(parsedLine);

			if(s.hasNextLine())
				Highscore.validate(s.next(".*"));
		}else if(HIGHSCORE_ENTRY.matcher(parsedLine).matches())
			Highscore.validate(parsedLine+"\n"+s.next(".*"));
		else if(!Pattern.matches(LEVEL_LINE, parsedLine)){
			throw new WrongLevelFormatException(
					"Wrong level format while parsing level from "
					+"string: unexcepted characters in line "
					+line.getLineNumber());
		}

		s.close();
	}


	/**
	 * parses a level from a String and stores the informations in the 
	 * object.
	 *
	 * the string !!!must!!! have the following format:<br>
	 * the level field must be a square and must consist of values 
	 * between 1 and 5.<br>
	 *
	 * Example:<br>
	 * 4232342322123<br>
	 * 5433455532345<br>
	 * 3453421253522<br>
	 * 1423412534142<br>
	 * 1523542431233<br>
	 *
	 * The addtional level information must have the following format:<br>
	 * a "###" at the beginning of the line, then one or two of the 
	 * following informations: target_time:888 AND/OR min_stones:2,
	 * seperated with a "|".<br>
	 * Example: ###target_time:888|min_stones:4<br>
	 *
	 * The highscore entrys must have the following format:<br>
	 * a "###" at the beginning of the line, then each of the following
	 * informations:<br>
	 * name:xyz AND points:888 AND date:23.05.2010 23;59;12 AND 
	 * rem_time:123<br>
	 * seperated with a "|".<br>
	 * Example: ###name:xyz|points:888|date:23.05.2010 23;59;12|
	 * rem_time:123<br>
	 *
	 * @param levelString
	 * @throws WrongLevelFormatException if the level is not in the 
	 * described format
	 */
	public void loadLevelFromString(String levelString) 
		throws WrongLevelFormatException{

		// return with an exception if it is an syntactical incorrect
		// Level	
		validateSyntactical(levelString);

		Scanner s;
		LineNumberReader line = 
			new LineNumberReader(
					new StringReader(levelString));

		s = new Scanner(line);
		Vector<Byte[]> parsedLevel = new Vector<Byte[]>();


		// get the length of the first line
		String parsedLine = s.nextLine();
		int cols = parsedLine.length();
		parsedLevel.add(parseByteDigits(parsedLine));

		// get all other level lines
 		while(s.hasNextLine() && s.hasNext("\\d*")){
			parsedLine = s.nextLine();
			parsedLevel.add(parseByteDigits(parsedLine));
		}

		// sets the additional level information to default values 
		// which are overwritten if there were scanned others
		this.targetTime = parsedLevel.size() *cols;
		this.minStones = 2;

		// if there are additional level information or highscore entrys
		if(s.hasNextLine()){

			// if there are additional level information
			if(s.hasNext(ADDITIONAL_LEVEL_INF+".*")){
				parseAdditionalLevelInf(line, s);
			}

			// if there are highscore entrys
			 if(s.hasNext(HIGHSCORE_ENTRY)){ 
				this.highscore = new Highscore(line, s);
			}
		}

		// write the parsed vector to the class attribute
		Byte[][] field = new Byte[parsedLevel.size()][cols];
		parsedLevel.toArray(field);
		
		boolean emptyLevel = true;
		for(int i=0; i<field.length; i++)
			for(int j=0; j<field[i].length; j++)
				if(field[i][j] != 0){
					emptyLevel = false;
					break;
				}
		
		if(emptyLevel)
			throw new WrongLevelFormatException(
					"Wrong level format while parsing level from "
					+"string: empty level");
			
		// let the field collaps
		moveUp(field);
		
		this.currentGameState = new GameState(field, 0);

		s.close();
	}


	/**
	 * parses a Level line.
	 * @param s a line in the level field
	 * @return an byte array with each char of the string at a position
	 * in the array
	 * @throws NumberFormatException if a char isn't parseable
	 */
	private Byte[] parseByteDigits(String s) 
		throws NumberFormatException{

		Byte[] parsedArray = new Byte[s.length()];
		for(int i=0; i<s.length(); i++)
			parsedArray[i] = Byte.parseByte(""+s.charAt(i));
		return parsedArray;
	}


	/**
	 * check if the additional level information have the right format
	 * and if there are no dublicates.
	 * @param addLevelInf the line with the additional level 
	 * informations
	 * @throws WrongLevelFormatException if the addtional level 
	 * informations are not in the excepted format
	 */
	public static void validateAdditionalLevelInf(String addLevelInf)
		throws WrongLevelFormatException{

		Scanner s = new Scanner(addLevelInf);

		s.skip("###");

		String[] infos = new String[2];
		int[] values = new int[2];
		
		// scanning informations
		for(int i=0; i<2; i++){
			infos[i] = s.findInLine("(\\w|_)*");
			s.skip(":");
			try{
				values[i] = Integer.parseInt(s.findInLine("\\d*"));
			}catch(NumberFormatException e){
				throw new WrongLevelFormatException(
						"wrong level format while parsing additional "
						+"Level informations from string :"
						+ e.getMessage());
			}
			if(s.hasNext("|"))
				s.skip("|");
			else
				break;
		}
		
		// if there are duplicated informations
		if(infos[1] != null && infos[0].equals(infos[1]))
			throw new WrongLevelFormatException(
					"wrong level format while parsing additional Level"
				    +" informations from string: dublicated level "
					+"informations");
	}
	

	/**
	 * 
	 */
	private void parseAdditionalLevelInf(LineNumberReader line, Scanner s) 
		throws WrongLevelFormatException{

		String parsedLine = s.nextLine();

		validateAdditionalLevelInf(parsedLine);

		String[] infos = new String[2];
		int[] values = new int[2];
		
		// scanning informations
		for(int i=0; i<2; i++){
			infos[i] = s.next("(\\w|_)*");
			s.next(":");
			values[i] = s.nextInt();
			if(s.hasNext("|"))
				s.next("|");
			else
				break;
		}
		
		for(int i=0; i<2; i++){
			if(infos[i].equals("target_time"))
				this.targetTime = values[i];
			else if(infos[i].equals("min_stones"))
				this.minStones = values[i];
		}
	}


	public boolean storeLevel(File f) 
		throws FileNotFoundException, 
			   WrongLevelFormatException, 
			   IOException{

		return false;
	}

	public boolean restoreLevel(File f)
		throws FileNotFoundException, 
			   WrongLevelFormatException, 
			   IOException{

		BufferedReader r = new BufferedReader(new FileReader(f));

		char[] levelString = new char[(int)f.length()];
		r.read(levelString, 0, (int)f.length());
		loadLevelFromString(new String(levelString.toString()));
		return false;
	}


	public static boolean removeable(Byte[][] state, 
									 int minStones, 
									 int row, 
									 int col) {

		Byte[][] stateCopy = new Byte[state.length][state[0].length];
		for (int i = 0; i < state.length; i++)
			System.arraycopy(state[i], 0, stateCopy[i], 0, state[i].length);

		int rows = state.length;
		int cols = state[0].length;
		byte color = state[row][col];

		if (row >= 0 && col >= 0 && row < rows && col < cols 
			&& color != 0) {

			stateCopy[row][col] = 100;
			countableFloodFill(stateCopy, row - 1, col, color, (byte) 100);
			countableFloodFill(stateCopy, row + 1, col, color, (byte) 100);
			countableFloodFill(stateCopy, row, col - 1, color, (byte) 100);
			countableFloodFill(stateCopy, row, col + 1, color, (byte) 100);
		}

		int stonesToRemove = 0;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				if (stateCopy[i][j] == 100)
					stonesToRemove++;
		return stonesToRemove >= minStones;

	}

	public boolean removeable(int row, int col) {

		Byte[][] field = currentGameState.getFieldState();
		return removeable(field, minStones, row, col);
	}

	private static void countableFloodFill(Byte[][] state, 
								  int row, 
								  int col, 
								  byte color,
								  byte newCol){

		int rows = state.length;
		int cols = state[0].length;
		if (row >= 0 && col >= 0 && row < rows && col < cols
			&& state[row][col] == color && state[row][col] != 0) {

			state[row][col] = newCol;
			countableFloodFill(state, row - 1, col, color, newCol);
			countableFloodFill(state, row + 1, col, color, newCol);
			countableFloodFill(state, row, col - 1, color, newCol);
			countableFloodFill(state, row, col + 1, color, newCol);
		}
	}

	private static void removeFloodFill(Byte[][] state, 
								  int row, 
								  int col, 
								  byte color,
								  Integer stonesRemoved){

		int rows = state.length;
		int cols = state[0].length;
		if (row >= 0 && col >= 0 && row < rows && col < cols
			&& state[row][col] == color && state[row][col] != 0) {

			state[row][col] = 0;
			stonesRemoved++;
			removeFloodFill(state, row - 1, col, color, stonesRemoved);
			removeFloodFill(state, row + 1, col, color, stonesRemoved);
			removeFloodFill(state, row, col - 1, color, stonesRemoved);
			removeFloodFill(state, row, col + 1, color, stonesRemoved);
		}
	}

	public boolean removeStone(int row, int col) 
		throws ParameterOutOfRangeException{

		Byte[][] state = getFieldState();
		int rows = state.length;
		int cols = state[0].length;

		if (row < 0 || row >= rows)
			throw new ParameterOutOfRangeException(
					"the \"row\" parameter is out of possible range"
					+"[0,"+rows+"]: "+row);

		if (col < 0 || col >= cols)
			throw new ParameterOutOfRangeException(
					"the \"col\" parameter is out of possible range"
					+"[0,"+cols+"]: "+col);

		if (state[row][col] == 0)
			return false;

		byte color = state[row][col];

		if (removeable(state, minStones, row, col)) {
			Integer pointsMade = 0;

			removeFloodFill(state, row, col, color, pointsMade);
			moveUp(state);
			
			updateState(state, pointsMade);

			return true;
		}
		return false;
	}


	public static void moveUp(Byte[][] state) {

		int rows = state.length;
		int cols = state[0].length;
		int count = rows;

		while (count > 0) {
			for (int i = rows - 2; i >= 0; i--) {
				for (int j = cols - 1; j >= 0; j--) {
					if (state[i + 1][j] == 0 && state[i][j] != 0) {
						state[i + 1][j] = state[i][j];
						state[i][j] = 0;
					}
				}
			}
			count--;
		}

		for (int i = 0; i <= cols - 2; i++) {
			if (state[rows - 1][i] == 0) {
				for (int j = 0; j < rows; j++) {
					state[j][i] = state[j][i + 1];
					state[j][i + 1] = 0;
				}
			}
		}
	}


	public static boolean isFinished(Byte[][] state, int minStones) {
		int rows = state.length;
		int cols = state[0].length;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (state[i][j] != 0) {
					if (removeable(state, minStones, i, j))
						return false;
				}
			}
		}
		return true;
	}
	

	public boolean isFinished() {
		if(currentGameState.getFieldState() == null)
			return true;
		else{
			Byte[][] state = currentGameState.getFieldState();
			return isFinished(state, minStones);
		}
	}


	void updateState(Byte[][] state, int pointsMade){

		int pointsAllready = currentGameState.getPoints();

		currentGameState = 
			new GameState(state, pointsAllready + pointsMade);

		gameStateHistory.addEdit(currentGameState);

		// notify the viewer that the level state has changed
		changeListener.stateChanged(new ChangeEvent(this));
	}

	@Override
	public String toString(){
		return currentGameState.toString();
	}
	///////////////////End//Class/Operations//////////////////////////
}

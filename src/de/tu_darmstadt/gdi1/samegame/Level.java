package de.tu_darmstadt.gdi1.samegame;

import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;
import de.tu_darmstadt.gdi1.samegame.highscore.Highscore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.BufferedReader;

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
		throws FileNotFoundException, WrongLevelFormatException{

		parseLevel(f);

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
				 byte[][] field, 
				 ChangeListener changeListener){

		this.targetTime = targetTime;
		this.minStones = minStones;

		currentGameState = new GameState(field, 0);

		init(changeListener);
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
	public byte[][] getFieldState(){
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

		byte[][] level = new byte[rows][cols];

		int numOfColors = 2 + r.nextInt(4);
		this.minStones = 2 + r.nextInt(4);

		do{
			for(int i = 0; i<rows; i++)
				for(int j=0; j<cols; j++)
					level[i][j] = (byte) (1 + r.nextInt(numOfColors+1));
		}while(!validateLevelSemantical(level, numOfColors));

		this.targetTime = rows * cols;

		currentGameState = new GameState(level, 0);
	}
	
	/**
	 * check a level for semantical correctness, that means that it
	 * returns false if there are not enough stones of each color or 
	 * none removable stonegroup
	 * @return true if it is a semantical correct level
	 */
	private boolean validateLevelSemantical(byte[][] level, int numOfCol){
		int[] colorSpread = new int[numOfCol];

		int rows = level.length;
		int cols = level[0].length;

		boolean removableExists = false;
		for(int i = 0; i<rows; i++)
			for(int j=0; j<cols; j++){
				colorSpread[level[i][j]-1]++;
				if(!removableExists && removeable(i, j))
					removableExists = true;
			}

		if(!removableExists)
			return false;

		for(int i = 0; i<colorSpread.length; i++)
			if(colorSpread[i] < minStones)
				return false;

		return true;
	}

	/**
	 * function wich parses a level from a file.
	 * the File !!!must!!! have the following format:<br>
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
	 * @throws FileNotFoundException if the given file don't exists
	 * @throws WrongLevelFormatException if the level is not in the
	 * described format
	 */
	private void parseLevel(File f) 
		throws FileNotFoundException, WrongLevelFormatException{
		Scanner s;
		try{
			LineNumberReader line = 
				new LineNumberReader(
						new BufferedReader(
							new FileReader(f)));

			s = new Scanner(line);
			
			Vector<Byte[]> parsedLevel = new Vector<Byte[]>();

			int cols;
			
			// check the first line if it is the right format
			String parsedLine = parseLine("^\\d{6,30}$", line, s, f);
			cols = parsedLine.length();
			parsedLevel.add(parseByteDigits(parsedLine));

			// there must be a minimum of 5 lines
			while(line.getLineNumber() < 5){
				parsedLine = parseLine("^\\d{"+cols+"}$", line, s, f);
				parsedLevel.add(parseByteDigits(parsedLine));
			}

			// the field may have maximal 20 lines
			while(line.getLineNumber() < 20 && s.hasNext("^\\d{"+cols+"}$")){
				parsedLine = s.nextLine();
				parsedLevel.add(parseByteDigits(parsedLine));
			}
			
			byte[][] field = new byte[line.getLineNumber()][cols];
			parsedLevel.toArray(field);
			
			this.currentGameState = new GameState(field, 0);

			// sets the additional level information to default values which are
			// overwritten if there were scanned others
			this.targetTime = line.getLineNumber()*cols;
			this.minStones = 2;

			// if there are additional level information or highscore entrys
			if(s.hasNextLine()){
				parsedLine = s.nextLine();

				// if there are additional level information
				if(s.hasNext("^###(target_time:\\d*|min_stones:\\d*)"
						   +"(\\|(target_time:\\d*|min_stones:\\d*))?$")){
					parseAdditionalLevelInf(line, s, f);
				}

				// if there are highscore entrys
				else if(s.hasNext(
						"^###(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)"
						+"(\\|(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)){3}$")){ 
					this.highscore = new Highscore(line, s, f);
				}
				else
					throw new WrongLevelFormatException(
							"wrong level format while parsing " +f.toString()+" in line "
							+line.getLineNumber()+": "+parsedLine);

			}

			s.close();
		}catch(FileNotFoundException e){
			throw e;
		}
	}

	private String parseLine(String pattern, LineNumberReader line, Scanner s, File f) 
		throws WrongLevelFormatException{
		if(!s.hasNextLine())
			throw new WrongLevelFormatException(
					"wrong level format while parsing "+f.toString()+" at line " 
					+line.getLineNumber()+": missing Level informations");
		String parsedLine = s.nextLine();
		if(!Pattern.matches(pattern, parsedLine))
			throw new WrongLevelFormatException(
					"wrong level format while parsing "+f.toString()+" at line "
					+line.getLineNumber()+": "+ parsedLine);
		return parsedLine;
	}

	private void parseAdditionalLevelInf(LineNumberReader line, Scanner s, File f)
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
						"in File "+f.toString()+" in line "+line.getLineNumber()+": " + e.getMessage());
			}
			if(s.hasNext("|"))
				s.next("|");
			else
				break;
		}
		
		// if there are duplicated informations
		if(infos[0].equals(infos[1]))
			throw new WrongLevelFormatException(
					"wrong level format while parsing additional Level informations in File "
					+f.toString()+" in line "+line.getLineNumber()+": dublicated level informations");
		
		// set values to class attributes
		for(int i=0; i<2; i++){
			if(infos[i].equals("target_time"))
				this.targetTime = values[i];
			else if(infos[i].equals("min_stones"))
				this.minStones = values[i];
		}
	}

	private Byte[] parseByteDigits(String s){
		Byte[] parsedArray = new Byte[s.length()];
		for(int i=0; i<s.length(); i++)
			parsedArray[i] = Byte.parseByte(""+s.charAt(i));
		return parsedArray;
	}

	public boolean storeLevel(File f){
		// TODO Write method stub
		return false;
	}
	public boolean restoreLevel(File f){
		// TODO Write method stub
		return false;
	}
	private boolean validateLevel(){
		// TODO Write method stub
		return false;
	}

	public boolean removeable(int row, int col){
		return false;
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
		// TODO Write method stub
		return "";
	}
}

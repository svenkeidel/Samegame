package de.tu_darmstadt.gdi1.samegame.tests.adapter;

import de.tu_darmstadt.gdi1.samegame.controller.GameController;
import de.tu_darmstadt.gdi1.samegame.model.Level;
import de.tu_darmstadt.gdi1.samegame.view.SameGameViewer;
import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;
import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

/**
 * This is the test adapter for the minimal stage of completion. You <b>must</b> implement the method stubs and match
 * them to your concrete implementation. Please read all the Javadoc of a method before implementing it. <br>
 * <strong>Important:</strong> this class should not contain any real game logic, you should rather only match the
 * method stubs to your game. <br>
 * Example: in {@link #isCorrectLevel()} you may return the value <i>myGame.isCorrectlevel()</i>, if you have a variable
 * <i>myGame</i> and a level has before been loaded via {@link #loadLevelFromString(String)}. What you mustn't do is to
 * implement the actual logic of the method in this class. <br>
 * <br>
 * If you have implemented the minimal stage of completion, you should be able to implement all method stubs. The public
 * and private JUnit tests for the minimal stage of completion will be run on this test adapter. The other test adapters
 * will inherit from this class, because they need the basic methods (like loading a level), too. <br>
 * <br>
 * The methods of all test adapters need to function without any kind of user interaction.</br>
 * 
 * <i>Note:</i> All other test adapters will inherit from this class.
 * 
 * @see SameGameTestAdapterExtended1
 * @see SameGameTestAdapterExtended2
 * @see SameGameTestAdapterExtended3
 * 
 * @author Jonas Marczona
 * @author Manuel Pütz
 */
public class SameGameTestAdapterMinimal {
	
	protected Level level;
	protected SameGameViewer viewer;
	protected GameController contr;
	protected boolean correctLevel;

	/**
	 * Use this constructor to initialize everything you need.
	 */
	public SameGameTestAdapterMinimal() {
		viewer = new SameGameViewer();
		level = new Level(viewer);
		contr  = new GameController(level, viewer);
		viewer.setLevel(level);
		viewer.setController(contr);
		correctLevel = false;
	}
	
	
	/** 
	 * Construct a level from a string. You will be given a string 
	 * representation of a level and you need to load this into your game.
	 * Your game should hold the level until a new one is loaded; the other 
	 * methods will assume that this very level is the current one and that 
	 * the actions (like removing elements) will run on the specified level.
	 * If the given string is not valid keep the last loaded level.
	 * 
	 * @param levelstring a string representation of the level to load
	 * @see #isCorrectLevel()
	 */
	public void loadLevelFromString(String levelstring) {
		try{
			level = new Level(levelstring, viewer);
			contr.setLevel(level);
			viewer.setLevel(level);
			correctLevel = true;
		}catch (WrongLevelFormatException e){
			correctLevel = false;
		}
	}
	
	/** 
	 * Return the string representation of the current level.
	 * This string should <b>not</b> contain any additional information-lines (starting with ###).
	 * 
	 * A level loaded with the method {@link #loadLevelFromString()} 
	 * should be the same (except the lines with additional informations) as the result of 
	 * {@link #getLevelAsStringWithoutExtraInfo()}
	 * <u>as long as no actions has been performed</u> in the meantime. <br>
	 * But if there were (valid) actions in the meantime this has to be visible in the level representation.<br>
	 * The level format is the same
	 * as the one specified for loading levels (except of additional lines!).
	 * 
	 * @return string representation of the current level
	 * 		or null if no valid level was loaded
	 * 
	 * @see #loadLevelFromString(String)
	 * @see #isCorrectLevel()
	 */
	public String getLevelAsStringWithoutExtraInfo() {
		return level.toString();
	}
	
	/** 
	 * Is the last(!) loaded level a syntactically correct level?
	 * See the specification in the task assignment for a definition of
	 * 'syntactically correct'.<br>
	 * You don't need to implement a solvability evaluation here.  
	 *  
	 * @return if the last loaded level is syntactically correct return true, 
	 * otherwise return false
	 * 
	 * @see #loadLevelFromString(String)
	 * @see #getLevelAsStringWithoutExtraInfo()
	 */
	public boolean isCorrectLevel() {
		return correctLevel;
	}
	
	/**
	 * Is the current level in a state where no action is possible?
	 * 
	 * @return true, if in the current level no further move can be done
	 */
	public boolean isFinished() {
		return level.isFinished();
	}
	
	/**
	 * Selects the cell at the given coordinate and tries to remove stones there.<br>
	 * <br>
	 * Remember: A JUnit testcase should not open a visible gui.<br>
	 * 
	 * <p>
	 * <b>Important:</b> In a normal use (a human person plays) you should show a gui and you should animate sliding and
	 * dropping of stones.<br>
	 * In a testcase you should <b>not</b> open a gui. Also you should <b>not</b> need time for sliding and dropping of
	 * stones. <br>
	 * <b>Very important</b> for the testcases is, that the 'animation' is over before this method returns.<br>
	 * </p>
	 * 
	 * <p>
	 * <b>Hint/Remember:</b> The cell on the upper left is 0|0, on the lower left is 0|max where max is the maximum on
	 * the y-axis of the current board.<br>
	 * </p>
	 * 
	 * 
	 * @param x
	 *            the x-axis part of the coordinate
	 * @param y
	 *            the y-axis part of the coordinate
	 * @return true if it was possible to remove stones at the given point.<br>
	 *         false in all other cases (not enough stones to remove at given coordinate; no stone at this point; no
	 *         valid point; etc)
	 */
	public boolean cellSelected(int x, int y) {
		try{
			return level.removeStone(y, x);
		}catch(ParameterOutOfRangeException e){
			return false;
		}
	}
}

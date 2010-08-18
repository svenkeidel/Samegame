package de.tu_darmstadt.gdi1.samegame.tests.adapter;

import de.tu_darmstadt.gdi1.samegame.model.Level;

import java.awt.Component;
import java.awt.event.KeyEvent;

/**
 * This is the test adapter for the first extended stage of completion.
 * Implement all method stubs in order for the tests to work.
 * <br><br>
 * <i>Note:</i> This test adapter inherits from the minimal test adapter
 * 
 * @see SameGameTestAdapterMinimal
 * 
 * @author Jonas Marczona
 * @author Manuel Pütz
 */
public class SameGameTestAdapterExtended1 extends SameGameTestAdapterMinimal {

	

	/**
	 * Use this constructor to initialize everything you need.
	 */
	public SameGameTestAdapterExtended1() {
		super();
	}
	
	
	/**
	 * The same as 
	 * {@link SameGameTestAdapterMinimal#getLevelAsStringWithoutExtraInfo()} 
	 * but now <b>with</b> additional information.
	 * 
	 * @return a string representing the current level, including the 
	 * extra information like target_time, min_stones and the highscore
	 * entries.
	 * @see SameGameTestAdapterMinimal#getLevelAsStringWithoutExtraInfo()
	 */
	public String getLevelAsStringWithExtraInfo() {
		if(level.getHighscorelist() == null)
			return level.toString() + "\n" 
				+ level.getAdditionalLevelInf();
		else
			return level.toString() + "\n" 
				+ level.getAdditionalLevelInf() + "\n"
				+ level.getHighscorelist();
	}

	/**
	 * return the "target_time" specified in the first line, after the 
	 * level-informations, of the level.<br>
	 * If the level string does not contain additional lines or this 
	 * value is missing this method should return the default value 
	 * (specified in the documentation).
	 * 
	 * @return the target_time of the current level, in seconds.
	 */
	public double getTargetTime() {
		return level.getTargetTime();
	}

	/**
	 * return the "min_stones" specified in the first line, after the 
	 * level-informations, of the level.<br>
	 * If the level string does not contain additional lines or this 
	 * value is missing this method should return the default value 
	 * (specified in the documentation).
	 * 
	 * @return the min_stones of the current level.
	 */
	public int getMinStones() {
		return level.getMinStones();
	}

	
	/**
	 * Calculates the points for removing the given number of 
	 * elements.<br>
	 * Notice: This method has to work independent of the currently 
	 * loaded level.<br> 
	 * The return-value has only(!) to dependent from the 
	 * method-arguments. Reached points so far, the current loaded 
	 * level (or state of game) should not affect the return value.
	 * 
	 * @param equalElements number of elements which have been 
	 * removed
	 * @param afterTargetTime is set to true when the player is over 
	 * the target time
	 * @return the number of points to add for this number of removed 
	 * stones (depending on before or after the target time).
	 */
	public int calculatePoints(final int equalElements, 
							   final boolean afterTargetTime) {
		return (int) Level.calculatePoints(equalElements, afterTargetTime);
	}

	/**
	 * Calculates the additional points at the end of a game.<br>
	 * Notice: This method has to work independent of the currently 
	 * loaded level.<br>
	 * The return-value has only(!) to dependent from the 
	 * method-arguments. Reached points so far, the current loaded
	 * level (or state of game) should not affect the return value.
	 * 
	 * @param elementsLeft number of elements left on the board, 
	 * will be something between zero and infinity.
	 * @param timeLeft number of seconds left of the target time
	 * @param initialElements number of elements on the board when 
	 * the game began, will be something bigger zero. 
	 * @return the number of points to add for finishing a level 
	 * with the given values.
	 */
	public int calculatePointsFinished(final int elementsLeft, 
									   final int timeLeft, 
									   final int initialElements) {

		return (int) Level.calculatePointsFinished(elementsLeft,
											 timeLeft,
											 initialElements);
	}


	
	/**
	 * This method should have the same effect like pressing the key 'N'.  
	 * Call the method that handles the key event when the N key is 
	 * pressed (to restart the current level). Remember: The methods of
	 * all test adapters need to function without any kind of user 
	 * interaction. Remember also that your game must work without this 
	 * TestAdapter.
	 */
	@SuppressWarnings("serial")
	public void handleKeyPressedNew() {
		contr.keyPressed(new KeyEvent(new Component(){},
									  KeyEvent.KEY_PRESSED,
									  System.currentTimeMillis(),
									  0,
									  KeyEvent.VK_N,
									  'n'));
	}

}

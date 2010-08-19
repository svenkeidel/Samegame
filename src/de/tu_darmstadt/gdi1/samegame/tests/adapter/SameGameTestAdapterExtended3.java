package de.tu_darmstadt.gdi1.samegame.tests.adapter;

import java.awt.Component;

import java.awt.event.KeyEvent;

/**
 * This is the test adapter for the third extended stage of completion.
 * Implement all method stubs in order for the tests to work.
 * <br><br>
 * <i>Note:</i> This test adapter inherits from the second extended test adapter
 * 
 * @see SameGameTestAdapterMinimal
 * @see SameGameTestAdapterExtended1
 * @see SameGameTestAdapterExtended2
 * 
 * @author Jonas Marczona
 * @author Manuel Pütz
 */
@SuppressWarnings("serial")
public class SameGameTestAdapterExtended3 extends SameGameTestAdapterExtended2 {

	/**
	 * Use this constructor to initialize everything you need.
	 */
	public SameGameTestAdapterExtended3() {
		super();
	}

	/**
	 * Generate a level with the given width and height. It should be returned as string representation of a valid level
	 * as described in the documentation <b>without additional information</b>. <br>
	 * Counting starts at 1, that means a 3x3 board has a width and height of three.<br>
	 * 
	 * <b>This method must throw an exception when called with illegal parameters.</b>
	 * 
	 * @param width
	 *            the width of the generated level
	 * @param height
	 *            the height of the generated level
	 * @param numberOfColors
	 *            the exact number of different colors for the stones in this level
	 * @param minStones
	 *            the minimum number of adjacent stones for removing
	 * @return string representation of the generated level
	 * 
	 * @throws Exception
	 *             when called with illegal parameters. The exception thrown is implementation dependent. An
	 *             {@link IllegalArgumentException}, something inheriting from it or an exception-class of your own could be a good choice.
	 */
	public String generateLevel(int width, int height, int numberOfColors, int minStones) throws Exception {
		level.generateLevel(width, height, numberOfColors, minStones);
		return level.toString();
	}
	
	/**
	 * Like {@link GameWindow#keySpacePressed()}.
	 * 
	 * @see {@link GameWindow#keySpacePressed()}
	 * @see SameGameTestAdapterExtended2#handleKeyPressedNew()
	 */
	public void handleKeyPressedSpace() {
		contr.keyPressed(new KeyEvent(new Component(){},
									  KeyEvent.KEY_PRESSED,
									  System.currentTimeMillis(),
									  0,
									  KeyEvent.VK_SPACE,
									  ' '));
	}
	
	/**
	 * Like {@link GameWindow#keyUpPressed()}.
	 */
	public void handleKeyPressedUp() {
		contr.keyPressed(new KeyEvent(new Component(){},
									  KeyEvent.KEY_PRESSED,
									  System.currentTimeMillis(),
									  0,
									  KeyEvent.VK_UP,
									  ' '));
	}
	
	/**
	 * Like {@link GameWindow#keyDownPressed()}.
	 */
	public void handleKeyPressedDown() {
		contr.keyPressed(new KeyEvent(new Component(){},
									  KeyEvent.KEY_PRESSED,
									  System.currentTimeMillis(),
									  0,
									  KeyEvent.VK_DOWN,
									  ' '));
	}
	
	/**
	 * Like {@link GameWindow#keyLeftPressed()}.
	 */
	public void handleKeyPressedLeft() {
		contr.keyPressed(new KeyEvent(new Component(){},
									  KeyEvent.KEY_PRESSED,
									  System.currentTimeMillis(),
									  0,
									  KeyEvent.VK_LEFT,
									  ' '));
	}
	
	/**
	 * Like {@link GameWindow#keyRightPressed()}.
	 */
	public void handleKeyPressedRight() {
		contr.keyPressed(new KeyEvent(new Component(){},
									  KeyEvent.KEY_PRESSED,
									  System.currentTimeMillis(),
									  0,
									  KeyEvent.VK_RIGHT,
									  ' '));
	}
}

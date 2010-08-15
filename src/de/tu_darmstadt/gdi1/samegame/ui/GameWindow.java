/*============================================================================*/

package de.tu_darmstadt.gdi1.samegame.ui;

/*============================================================================*/

import java.util.Locale;

import javax.swing.JFrame;

import de.tu_darmstadt.gdi1.samegame.Level;
import de.tu_darmstadt.gdi1.samegame.exceptions.InternalFailureException;
import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

/*============================================================================*/

/**
 * Base class for the main game window. Derive from this class for implementing
 * your custom solution.
 * 
 * @author Steven Arzt, Oren Avni, Jonas Marczona
 * @version 1.1
 */
public abstract class GameWindow extends JFrame {

	/* ======================================================================== */

	private static final long serialVersionUID = -2646785578035515024L;

	/* ======================================================================== */

	protected GamePanel gamePanel = null;
	
	/* ======================================================================== */

	/**
	 * Creates a new instance of the GameWindow class
	 * 
	 * @param windowTitle The title of the game window
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	public GameWindow(String windowTitle, Level level, Locale locale) {
		super(windowTitle);

		// We may need to correct the window title
		if (windowTitle == null || windowTitle.equals(""))
			setTitle("SameGame Student Implementation");

		// Create the game panel
		gamePanel = createGamePanel(level);
		if (gamePanel == null)
			throw new RuntimeException("The game panel may not be null");

		// Configure the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}

	/* ======================================================================== */

	/**
	 * Override this method to create your own custom game panel.
	 * 
	 * @return An instance of the GamePanel you want to use
	 */
	protected abstract GamePanel createGamePanel (Level level);

	/* ======================================================================== */

	/**
	 * Returns the game panel used by this game window
	 * 
	 * @return The game panel used by this game window
	 */
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	/* ======================================================================== */

	/**
	 * Notifies the game window that a new level has been loaded
	 * 
	 * @param width
	 *            The width of the level just loaded
	 * @param height
	 *            The height of the level just loaded
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	public void notifyLevelLoaded(int width, int height)
			throws ParameterOutOfRangeException, InternalFailureException {
		// Check the parameters
		if (width <= 0)
			throw new ParameterOutOfRangeException("Game Window width invalid");
		if (height <= 0)
			throw new ParameterOutOfRangeException("Game Window height invalid");

		// Notify the panel
		gamePanel.notifyLevelLoaded(width, height);
	}

	/* ======================================================================== */

}

/* ============================================================================ */

package de.tu_darmstadt.gdi1.samegame.gameframes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.InvocationTargetException;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import javax.swing.border.LineBorder;

import javax.swing.plaf.basic.BasicBorders;

import de.tu_darmstadt.gdi1.samegame.GameController;
import de.tu_darmstadt.gdi1.samegame.Level;

import de.tu_darmstadt.gdi1.samegame.exceptions.InternalFailureException;
import de.tu_darmstadt.gdi1.samegame.exceptions.InvalidOperationException;
import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

import de.tu_darmstadt.gdi1.samegame.gameframes.MainPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel{

	////////////////////////Class/Attributes//////////////////////////
	private Level level;
	private GameController controller;

	private int markedRow, markedCol;

	private boolean duringAnimation;

	private Byte[][] field;

	private Color markColor;
	// the list of entities
	private Vector<JButton> entities = null;
	
	// the loaded images, accessed by name
	private HashMap<String, ImageIcon> images = null;
	
	// the parent window
	private MainFrame parentWindow = null;
	
	// The current layout width and height based on the level
	private int layoutWidth = 0, layoutHeight = 0;
	
	// switch for enabling or disabling automatic sizing
	private boolean autosize = false;


	////////////////////////Class/Constructors////////////////////////
	public MainPanel(MainFrame parentWindow, 
					 Level level, 
					 GameController controller,
					 String skin){

		super();

		this.parentWindow = parentWindow;

		this.level = level;
		this.controller = controller;

		this.field = level.getFieldState();
		this.entities = new Vector<JButton>();
		this.images = new HashMap<String, ImageIcon>();

		markedRow = markedCol = 0;

		setAutosize(true);

		duringAnimation = false;

		setSkin(skin);
	}


	////////////////////////Getters//&//Setters///////////////////////
	public int getMarkedFieldRow(){
		return markedRow;
	}

	public int getMarkedFieldCol(){
		return markedCol;
	}

	public void setSkin(String skin){
		try{
			for(int i=0; i<=5; i++)
				if(isImageRegistered(""+i))
					unregisterImage(""+i);	

			String path = this.getClass().
				getResource("../../resources/images/"+skin).toString();
			
			registerImage("1", new URL(path+"/colorone.png"));
			registerImage("2", new URL(path+"/colortwo.png"));
			registerImage("3", new URL(path+"/colorthree.png"));
			registerImage("4", new URL(path+"/colorfour.png"));
			registerImage("5", new URL(path+"/colorfive.png"));
			registerImage("0", new URL(path+"/empty.png"));

			this.redraw();
		}catch(ParameterOutOfRangeException e){
			e.printStackTrace();
		}catch(InvalidOperationException e){
			e.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch(InternalFailureException e){
			e.printStackTrace();
		}
	}

	/**
	 * Enables or disables automatic sizing
	 * @param Autosize True if automatic sizing shall be enabled, otherwise
	 * false
	 */
	public void setAutosize (boolean Autosize){
		autosize = Autosize;
		resizePanel ();
	}


	/**
	 * Returns whether there are already entities on this game panel
	 * 
	 * @return True if there are already entities on this game panel, otherwise
	 *         false
	 */
	public boolean hasEntities() {
		return entities.size() > 0;
	}


	////////////////////////Class/Operations//////////////////////////
	/**
	 * Draws the game field once again and updates its graphical representation
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	public void redraw() throws InternalFailureException {
		if (Thread.currentThread ().getName ().contains ("AWT-EventQueue"))
		{
			clearFrame();
			setGamePanelContents();
			resizePanel ();
		}
		else
			try
			{
				SwingUtilities.invokeAndWait (new Runnable ()
				{
					@Override
					public void run (){
						clearFrame();
						setGamePanelContents();
						resizePanel ();
					}	
				});
			}
			catch (InterruptedException ex){
				ex.printStackTrace();
				throw new InternalFailureException (ex);
			}catch(InvocationTargetException ex){
				ex.getTargetException().printStackTrace();
				throw new InternalFailureException (ex);
			}
	}


	/**
	 * Clears the game frame by removing all entity buttons and recreating the
	 * corresponding internal data structures. This method can also be used for
	 * initialization purposes.
	 */
	private void clearFrame() {
		for (int i = 0; i < entities.size(); i++) {
			JButton btn = entities.get(i);
			btn.setVisible(false);
			remove(btn);
			synchronized (entities)
			{
				entities.remove(btn);
			}
			clearFrame();
			return;
		}			
	}


	/**
	 * Notifies the game panel that a new level has been loaded
	 * 
	 * @param width
	 *            The width of the level just loaded
	 * @param height
	 *            The height if the level just loaded
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	void notifyLevelLoaded(int width, int height)
			throws ParameterOutOfRangeException, InternalFailureException {
		// Check the parameters
		if (width <= 0)
			throw new ParameterOutOfRangeException("Game Panel width negative");
		if (height <= 0)
			throw new ParameterOutOfRangeException("Game Panel height negative");

		// Initialize the layout
		layoutWidth = width;
		layoutHeight = height;
		setLayout(new GridLayout(height, width));
		redraw();
	}


	/**
	 * Resizes the game panel to match the current contents
	 */
	private void resizePanel (){
		int oldWidth = getWidth (), oldHeight = getHeight (); 
		int width = 0, height = 0;
		for (int i = 0; i < entities.size (); i++)
		{
			JButton btn = entities.get (i);
			int icoWidth = btn.getIcon ().getIconWidth () + 2;
			int icoHeight = btn.getIcon ().getIconHeight () + 2;
			
			width = Math.max (width, icoWidth);
			height = Math.max (height, icoHeight);
			
			// +2px border size (one pixel per side)
			if (autosize)
				btn.setPreferredSize (new Dimension (icoWidth, icoHeight));
			else
				btn.setSize (icoWidth, icoHeight);
		}
		
		width = layoutWidth * width;
		height = layoutHeight * height;
		setSize (width, height);
		
		if (oldWidth != width || oldHeight != height)
		{
			if (autosize)
				parentWindow.pack ();
		}
	}


	public void markField(int row, int col){
		if(row >= 0 || row < level.getFieldHeight() ||
		   col >= 0 || col < level.getFieldWidth()){
			markedRow = row;
			markedCol = col;
			
			
		}
	}



	/**
	 * Checks whether a specific image has already been registered with the game
	 * panel
	 * 
	 * @param identifier
	 *            The unique image identifier
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 */
	public boolean isImageRegistered(String identifier)
			throws ParameterOutOfRangeException {
		// Check the parameter
		if (identifier == null || identifier.equals(""))
			throw new ParameterOutOfRangeException("Identifier invalid");
		return images.containsKey(identifier);
	}


	/**
	 * Registers a new image in this game panel. Please note that the identifier
	 * must be unique, so the operation will fail if an image with the specified
	 * identifier has already been registered.
	 * 
	 * @param identifier
	 *            The new image's unique identifier
	 * @param fileName
	 *            The file name from which to load the image file
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InvalidOperationException
	 *             Thrown if this operation is not permitted due to the object's
	 *             current state
	 * @throws InternalFailureException
	 * 			   Thrown if an uncorrectable internal error occurs
	 */
	public void registerImage(String identifier, String fileName)
			throws ParameterOutOfRangeException,
				   InvalidOperationException,
				   InternalFailureException {
		try
		{
			// Check for file existence
			File f = new File (fileName);
			if (!f.exists ())
				throw new InvalidOperationException
					("File " + fileName + " not found");
			
			StringBuilder builder = new StringBuilder ();
			builder.append("file://");
			builder.append(f.getCanonicalPath ());
			registerImage(identifier, new URL (builder.toString ()));
		}
		catch (MalformedURLException ex)
		{
			throw new ParameterOutOfRangeException ("fileName", ex);
		}
		catch (IOException ex)
		{
			throw new InternalFailureException (ex);
		}
	}
	

	/**
	 * Registers a new image in this game panel. Please note that the identifier
	 * must be unique, so the operation will fail if an image with the specified
	 * identifier has already been registered.
	 * 
	 * @param identifier
	 *            The new image's unique identifier
	 * @param fileName
	 *            The URL from which to load the image file
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InvalidOperationException
	 *             Thrown if this operation is not permitted due to the object's
	 *             current state
	 */
	public void registerImage(String identifier, URL fileName)
			throws ParameterOutOfRangeException, InvalidOperationException {
		// Check the parameters
		if (identifier == null || identifier.equals(""))
			throw new ParameterOutOfRangeException("Identifier invalid");
		if (fileName == null || fileName.equals(""))
			throw new ParameterOutOfRangeException("FileName invalid");

		if (isImageRegistered(identifier))
			throw new InvalidOperationException(
					"An image with this identifier "
							+ "has already been registered");
		
		images.put(identifier, new ImageIcon(fileName));
	}

	
	/**
	 * Unregisters a previously registered image from this game panel. If the
	 * specified identifier does not exist, an exception is thrown.
	 * @param identifier
	 *            The image's unique identifier
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @throws InvalidOperationException
	 *             Thrown if this operation is not permitted due to the object's
	 *             current state
	 */
	public void unregisterImage(String identifier)
			throws ParameterOutOfRangeException, InvalidOperationException {
		// Check the parameters
		if (identifier == null || identifier.equals(""))
			throw new ParameterOutOfRangeException("Identifier invalid");

		if (!isImageRegistered(identifier))
			throw new InvalidOperationException(
					"An image with this identifier "
							+ "has not been registered");
		images.remove (identifier);
	}
	

	/**
	 * Places a graphical entity on the game panel.
	 * 
	 * @param imageIdentifier
	 *            The identifier of a previously registered image that will be
	 *            used for rendering the entity
	 * @throws ParameterOutOfRangeException
	 *             Thrown if one of the parameters falls out of the range of
	 *             acceptable values
	 * @return The placed entity (JButton).
	 */
	private JButton placeEntity(String imageIdentifier, boolean marked)
			throws ParameterOutOfRangeException {

		// Check the parameters
		if (imageIdentifier == null || imageIdentifier.equals(""))
			throw new ParameterOutOfRangeException("ImageIdentifier invalid");

		// Get the image icon
		ImageIcon img = images.get(imageIdentifier);
		if (img == null)
			throw new RuntimeException("An image with the identifier "
					+ imageIdentifier + " could not be found");

		return(placeEntity(img, marked));
	}

	/**
	 * Places a graphical entity on the game panel.
	 * 
	 * @param image
	 *            An image which will be used for the entity.
	 * @return The placed entity (JButton).
	 */
	private JButton placeEntity(Image image, boolean marked){
		return( placeEntity( new ImageIcon( image), marked));
	}

	
public void setMarkColor(Color mcolor){
	markColor = mcolor;
		}

	/**
	 * Places a graphical entity on the game panel.
	 * 
	 * @param icon
	 *            An icon which will be used for the entity.
	 * @return The placed entity (JButton).
	 */
	private JButton placeEntity(Icon icon, boolean marked){
		// Create the visual entity
		JButton btn = new JButton();

		btn.setMargin( new Insets( 0 , 0 , 0 , 0));

		synchronized(entities){
			entities.add(btn);
		}

		btn.addActionListener(controller);
		btn.setIcon(icon);

		if(marked)
			btn.setBorder(new LineBorder(markColor, 2));

		// add it
		this.add(btn);

		return(btn);
	}

	

	public boolean duringAnimation(){
		return this.duringAnimation;
	}

	void endAnimation(){
		duringAnimation = false;
		this.field = level.getFieldState();
		try{
			this.redraw();
		}catch(InternalFailureException e){
			e.printStackTrace();
		}
	}

	public void startAnimation(int row, int col, long animationSpeed){
		duringAnimation = true;

		Level.removeFloodFill(this.field, row, col, field[row][col]);

		AnimationThread animation = new AnimationThread(this.field, animationSpeed, this);
		animation.start();
	}


	public void setGamePanelContents(){
		// if an animation is performed, let the AnimationThread handle the
		// field state
		if(!duringAnimation)
			this.field = level.getFieldState();
	
		int width = level.getFieldWidth();
		int height = level.getFieldHeight();
		for(int i=0; i<height; i++)
			for(int j=0; j<width; j++)
				try{
					if(markedRow == i && markedCol == j)
						placeEntity(""+field[i][j], true);
					else
						placeEntity(""+field[i][j], false);
				}catch(ParameterOutOfRangeException e){
					System.err.println(e.getMessage());
					e.printStackTrace();
				}
	}


	public void entityClicked(int positionX, int positionY){
		parentWindow.requestFocus();

		if(!duringAnimation && level.removeable(positionY, positionX)){
			try{
				startAnimation(positionY, positionX, 500);
				level.removeStone(positionY, positionX);
				this.redraw();
			}catch(ParameterOutOfRangeException e){
				e.printStackTrace();
			}catch(InternalFailureException e){
				e.printStackTrace();
			}
		}
	}
}

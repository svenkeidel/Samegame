package de.tu_darmstadt.gdi1.samegame;

import javax.swing.undo.AbstractUndoableEdit;

class GameState extends AbstractUndoableEdit implements Cloneable{

	private byte[][] fieldState;
	private int points; 

	/**
	 * class constructor, sets the field-state attribute 
	 * and the points for the current gameState
	 * @param fieldState the current field state 
	 * @param points the reached points for the current state
	 */
	public GameState(byte[][] fieldState, int points){
		this.fieldState = new byte[fieldState.length][fieldState[0].length];
		for(int i=0; i<fieldState.length; i++)
			System.arraycopy(fieldState, 0, this.fieldState, 0, fieldState[i].length);
		this.points = points;
	}

	/**
	 * Gets a copy of the fieldState
	 *
	 * @return The field state.
	 */
	public byte[][] getFieldState(){
		byte[][] fieldCopy = new byte[fieldState.length][fieldState[0].length];
		for(int i=0; i<fieldState.length; i++)
			System.arraycopy(fieldState, 0, fieldCopy, 0, fieldState[i].length);
		return fieldCopy;
	}

	/**
	 * Sets the fieldState for this instance.
	 *
	 * @param fieldState The fieldState.
	 */
	public void setFieldState(byte[][] fieldState){
		this.fieldState = fieldState;
	}

	/**
	 * Gets the points for this instance.
	 *
	 * @return The points.
	 */
	public int getPoints(){
		return this.points;
	}

	/**
	 * Sets the points for this instance.
	 *
	 * @param points The points.
	 */
	public void setPoints(int points){
		this.points = points;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

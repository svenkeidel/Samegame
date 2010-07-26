package de.tu_darmstadt.gdi1.samegame;

import javax.swing.undo.AbstractUndoableEdit;

class GameState extends AbstractUndoableEdit implements Cloneable{

	private Byte[][] fieldState;
	private int points; 

	/**
	 * class constructor, sets the field-state attribute 
	 * and the points for the current gameState
	 * @param fieldState the current field state 
	 * @param points the reached points for the current state
	 */
	public GameState(Byte[][] fieldState, int points){
		if(fieldState != null){
		this.fieldState = new Byte[fieldState.length][fieldState[0].length];
		for(int i=0; i<fieldState.length; i++)
			System.arraycopy(fieldState[i], 0, this.fieldState[i], 0, fieldState[i].length);
		}
		this.points = points;
	}

	/**
	 * Gets a copy of the fieldState
	 *
	 * @return The field state.
	 */
	public Byte[][] getFieldState(){
		if(fieldState == null)
			return null;
		else{
			Byte[][] fieldCopy = new Byte[fieldState.length][fieldState[0].length];
			for(int i=0; i<fieldState.length; i++)
				System.arraycopy(fieldState[i], 0, fieldCopy[i], 0, fieldState[i].length);
			return fieldCopy;
		}
	}

	/**
	 * Sets the fieldState for this instance.
	 *
	 * @param fieldState The fieldState.
	 */
	public void setFieldState(Byte[][] fieldState){
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
	
	public String toString(){
		if(fieldState == null)
			return null;

		StringBuffer out = new StringBuffer();
		for(int i = 0; i<fieldState.length; i++){
			for(int j = 0; j<fieldState[i].length; j++)
				out.append(fieldState[i][j]);
			out.append("\n");
		}
		out.deleteCharAt(out.length()-1);
		return out.toString();
	}
	
}

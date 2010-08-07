package de.tu_darmstadt.gdi1.samegame.exceptions;

@SuppressWarnings("serial")
public class WrongLevelFormatException extends Exception{

	public WrongLevelFormatException(){
		super();
	}

	public WrongLevelFormatException(String Message){
		super(Message);
	}
}

package de.tu_darmstadt.gdi1.samegame.exceptions;

@SuppressWarnings("serial")
public class LevelNotLoadedFromFileException extends Exception{

	public LevelNotLoadedFromFileException(){
		super();
	}

	public LevelNotLoadedFromFileException(String Message){
		super(Message);
	}
}

package de.tu_darmstadt.gdi1.samegame.exceptions;

public class LevelNotLoadedFromFileException extends Exception{

	public LevelNotLoadedFromFileException(){
		super();
	}

	public LevelNotLoadedFromFileException(String Message){
		super(Message);
	}
}

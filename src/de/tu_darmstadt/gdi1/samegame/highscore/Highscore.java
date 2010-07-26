package de.tu_darmstadt.gdi1.samegame.highscore;

import static java.util.Collections.*;
import java.util.Vector;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.LineNumberReader;
import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;

/**
 * class wich represents a highscore list.
 */
public class Highscore{

	////////////////////////Class/Attributes//////////////////////////
	/**
	 * the list of highscore entrys
	 */
	private Vector<HighscoreEntry> highscoreEntrys;

	/**
	 * a pattern wich matches a valide highscore entry
	 */
	public final static Pattern HIGHSCORE_ENTRY = HighscoreEntry.HIGHSCORE_ENTRY;
	////////////////////END/Class/Attributes//////////////////////////


	////////////////////////Class/Constructors////////////////////////
	/**
	 *
	 */
	public Highscore(Vector<HighscoreEntry> highscoreEntrys){
		sort(highscoreEntrys);
		this.highscoreEntrys = highscoreEntrys;
	}


	public Highscore(LineNumberReader line, Scanner s) 
		throws WrongLevelFormatException{
		highscoreEntrys = new Vector<HighscoreEntry>();
		parseHighscoreEntrys(line, s);
	}
	////////////////////END/Class/Constructors////////////////////////
	
	
	////////////////////////Getters//&//Setters///////////////////////
	public String[][] getHighscoreEntrys(){
		String[][] entrys = new String[highscoreEntrys.size()][4];
		for(int i=0; i<entrys.length; i++)
			entrys[i] = highscoreEntrys.get(i).toStringArray();
		return highscoreEntrys.toArray(entrys);
	}
	///////////////////End//Getters//&//Setters///////////////////////


	////////////////////////Class/Operations//////////////////////////
	public void insertHighscore(int points, int remainingTime, String name){
		 highscoreEntrys.add(
				 new HighscoreEntry(points, 
									remainingTime, 
									new Date(),
									name));
		sort(highscoreEntrys);
	}

	public static void validate(String highscoreList)
		throws WrongLevelFormatException{

		Scanner s = new Scanner(highscoreList);

		String highscoreEntry;
		while(s.hasNextLine()){
			highscoreEntry = s.nextLine();
			HighscoreEntry.validate(highscoreEntry);
		}
	}

	private void parseHighscoreEntrys(LineNumberReader line, Scanner s)
		throws WrongLevelFormatException{

		String highscoreList = s.next(".*");

		validate(highscoreList);

		while(s.hasNextLine()){
			highscoreEntrys.add(new HighscoreEntry(line, s));
		}
	}

	@Override
	public String toString(){
		StringBuffer out = new StringBuffer();
		for(int i=0; i<highscoreEntrys.size(); i++){
			out.append(highscoreEntrys.toString()).append("\n");
		}
		return out.toString();
	}
	///////////////////End//Class/Operations//////////////////////////
}

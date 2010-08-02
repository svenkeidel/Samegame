package de.tu_darmstadt.gdi1.samegame.highscore;

import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;
import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;

import static java.util.Collections.*;
import java.util.Vector;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.LineNumberReader;

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
	public final static String HIGHSCORE_ENTRY = HighscoreEntry.HIGHSCORE_ENTRY;



	////////////////////////Class/Constructors////////////////////////
	/**
	 *
	 */

	public Highscore(){
		this.highscoreEntrys = new Vector<HighscoreEntry>();
	}

	public Highscore(Vector<HighscoreEntry> highscoreEntrys){
		sort(highscoreEntrys);
		this.highscoreEntrys = highscoreEntrys;
	}


	public Highscore(LineNumberReader line, Scanner s) 
		throws WrongLevelFormatException{
		highscoreEntrys = new Vector<HighscoreEntry>();
		parseHighscoreEntrys(line, s);
	}
	

	
	////////////////////////Getters//&//Setters///////////////////////
	public String[][] getHighscoreEntrys(){
		String[][] entrys = new String[highscoreEntrys.size()][4];
		for(int i=0; i<entrys.length; i++)
			entrys[i] = highscoreEntrys.get(i).toStringArray();
		return highscoreEntrys.toArray(entrys);
	}

	public int getHighscoreCount(){
		return highscoreEntrys.size();
	}
	
	public String getPlayername(int position) 
		throws ParameterOutOfRangeException{
		inRange(position);
		return highscoreEntrys.get(position).getPlayername();
	}

	public double getRemaining(int position)
		throws ParameterOutOfRangeException{
		inRange(position);
		return highscoreEntrys.get(position).getRemainingTime();
	}

	public double getPoints(int position)
		throws ParameterOutOfRangeException{
		inRange(position);
		return highscoreEntrys.get(position).getPoints();
	}

	public Date getDate(int position)
		throws ParameterOutOfRangeException{
		inRange(position);
		return highscoreEntrys.get(position).getDate();
	}

	private void inRange(int position)
		throws ParameterOutOfRangeException{
		if(position < 0 || position >= highscoreEntrys.size())
			throw new ParameterOutOfRangeException(
					"The position lies outer the highscore array. "
					+"It must have a value in "
					+"[0,"+(highscoreEntrys.size()-1)+"], "
					+"but was: "+ position);
	}
	////////////////////////Class/Operations//////////////////////////
	public void insertHighscore(final String playername, 
								final double remTime, 
								final Date creationDate, 
					 			final double points)
		throws Invali{
		if(highscoreEntrys.size() > 10)
			throw 
		 highscoreEntrys.add(
				 new HighscoreEntry(playername,
									remTime, 
									creationDate,
									points));
		sort(highscoreEntrys);
	}

	public void resetHighscore(){
		highscoreEntrys.clear();
	}

	public static void validate(String highscoreList)
		throws WrongLevelFormatException{

		Scanner s = new Scanner(highscoreList);

		String highscoreEntry;

		for(int i=1; s.hasNextLine(); i++){
			highscoreEntry = s.nextLine();
			HighscoreEntry.validate(highscoreEntry, i);
		}
	}

	private void parseHighscoreEntrys(LineNumberReader line, Scanner s)
		throws WrongLevelFormatException{

		String highscoreList = s.next(".*");

		validate(highscoreList);

		for(int i=1; s.hasNextLine(); i++){
			highscoreEntrys.add(new HighscoreEntry(s.nextLine(), i));
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
}

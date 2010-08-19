package de.tu_darmstadt.gdi1.samegame.model.highscore;

import static java.util.Collections.*;
import java.util.Date;
import java.util.Scanner;
import java.util.Vector;

import de.tu_darmstadt.gdi1.samegame.exceptions.ParameterOutOfRangeException;
import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;

/**
 * class which represents a highscore list.
 * The class provides functions for parsing and validating a whole 
 * highscore list. It contains a vector of highscore entries. 
 * The list can't contain more then 10 entries
 */
public class Highscore{
	////////////////////////Class/Attributes//////////////////////////
	/**
	 * the list of highscore entries
	 */
	private Vector<HighscoreEntry> highscoreEntrys;

	/**
	 * a pattern which matches a valid highscore entry
	 */
	public final static String HIGHSCORE_ENTRY = HighscoreEntry.HIGHSCORE_ENTRY;



	////////////////////////Class/Constructors////////////////////////
	/**
	 * constructor which sets a empty highscorelist.
	 */
	public Highscore(){
		this.highscoreEntrys = new Vector<HighscoreEntry>();
	}

	/**
	 * constructor which sets a vector of highscore entries.
	 *
	 * @param highscoreEntrys the highscore entries
	 */
	public Highscore(Vector<HighscoreEntry> highscoreEntrys){
		sort(highscoreEntrys);
		this.highscoreEntrys = highscoreEntrys;
	}


	/**
	 * constructor which parses a highscore list string and validates it.
	 *
	 * @param entrys the highscore list string representation
	 *
	 * @throws WrongLevelFormatException if the highscore is not in the
	 * excepted format
	 */
	public Highscore(String entrys) 
		throws WrongLevelFormatException{
		highscoreEntrys = new Vector<HighscoreEntry>();
		parseHighscoreEntrys(entrys);
	}
	

	
	////////////////////////Getters//&//Setters///////////////////////
	/**
	 * returns the highscore entries in form of a two dimensional array.
	 * @return the 2d array. Each subarray has the form:
	 * <ol>
	 * 	<li>cell: points</li>
	 * 	<li>cell: remaining time</li>
	 * 	<li>cell: date</li>
	 * 	<li>cell: player's name</li>
	 * </ol>
	 *
	 * @return a String representation in the above described form
	 */
	public String[][] getHighscoreEntrys(){
		String[][] entrys = new String[highscoreEntrys.size()][4];
		for(int i=0; i<entrys.length; i++)
			entrys[i] = highscoreEntrys.get(i).toStringArray();
		return entrys;
	}

	/**
	 * get the number of highscore entries the instance contains.
	 * 
	 * @return the number of highscore entries
	 */
	public int getHighscoreCount(){
		return highscoreEntrys.size();
	}
	
	/**
	 * get the player name of a specific highscore entry.
	 *
	 * @param position the number of the highscore entry.
	 *
	 * @return the name of the player.
	 *
	 * @throws ParameterOutOfRangeException if the position is not in
	 * the range of numbers of highscore entries the list contains
	 */
	public String getPlayername(int position) 
		throws ParameterOutOfRangeException{
		inRange(position);
		return highscoreEntrys.get(position).getPlayername();
	}

	/**
	 * get the remaining time of a specific highscore entry.
	 * 
	 * @param position the number of the highscore entry.
	 *
	 * @return the remaining time until the target time runs out
	 *
	 * @throws ParameterOutOfRangeException if the position is not in
	 * the range of numbers of highscore entries the list contains
	 */
	public double getRemaining(int position)
		throws ParameterOutOfRangeException{
		inRange(position);
		return highscoreEntrys.get(position).getRemainingTime();
	}

	/**
	 * get the points of a specific highscore entry.
	 *
	 * @param position the number of the highscore entry.
	 *
	 * @return the points a player made
	 *
	 * @throws ParameterOutOfRangeException if the position is not in
	 * the range of numbers of highscore entries the list contains
	 */
	public double getPoints(int position)
		throws ParameterOutOfRangeException{
		inRange(position);
		return highscoreEntrys.get(position).getPoints();
	}

	/**
	 * get the date of a specific highscore entry.
	 *
	 * @param position the number of the highscore entry.
	 *
	 * @return the date when the player hit the highscore.
	 *
	 * @throws ParameterOutOfRangeException if the position is not in
	 * the range of numbers of highscore entries the list contains
	 */
	public Date getDate(int position)
		throws ParameterOutOfRangeException{
		inRange(position);
		return highscoreEntrys.get(position).getDate();
	}

	/**
	 * tests if the given position is in the range of the number of
	 * highscore entries.
	 *
	 * @param position the number of the highscore entry.
	 *
	 * @throws ParameterOutOfRangeException if the position is not in
	 * the range of numbers of highscore entries the list contains
	 */
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
	/**
	 * add highscore to the list and sorts it if needed.
	 *
	 * @param playername the name of the player
	 * @param remTime the remaining time until the target time runs out
	 * @param creationDate the date when the player hits the highscore
	 * @param points the points the player reached
	 */
	public void insertHighscore(final String playername, 
								final double remTime, 
								final Date creationDate, 
					 			final double points){

		 highscoreEntrys.add(
				 new HighscoreEntry(playername,
									remTime, 
									creationDate,
									points));

		sort(highscoreEntrys);

		if(highscoreEntrys.size() > 10)
			highscoreEntrys.remove(10);
	}


	/**
	 * resets the highscore list and clear all entries
	 */
	public void resetHighscore(){
		highscoreEntrys.clear();
	}


	/**
	 * validates if the string representing a highscore list is in the
	 * right format.
	 *
	 * @param highscoreList the String representing the highscore list
	 *
	 * @throws WrongLevelFormatException if the given string is not in 
	 * the right format
	 */
	public static void validate(String highscoreList)
		throws WrongLevelFormatException{

		Scanner s = new Scanner(highscoreList);

		String highscoreEntry;

		for(int i=1; s.hasNextLine(); i++){
			highscoreEntry = s.nextLine();
			HighscoreEntry.validate(highscoreEntry, i);
		}
	}

	/**
	 * parses a string representing a highscore list, sets the vector of
	 * entries and sorts it.
	 *
	 * @param highscoreList the String representing the highscore list
	 *
	 * @throws WrongLevelFormatException if the given string is not in 
	 * the right format
	 */
	private void parseHighscoreEntrys(String entrys)
		throws WrongLevelFormatException{

		Scanner s = new Scanner(entrys);

		validate(entrys);

		for(int i=1; s.hasNextLine(); i++){
			highscoreEntrys.add(new HighscoreEntry(s.nextLine(), i));
		}
		
		sort(highscoreEntrys);
	}

	/**
	 * returns a string representation in the same format as it is parsed.
	 */
	@Override
	public String toString(){
		StringBuffer out = new StringBuffer();
		for(int i=0; i<highscoreEntrys.size(); i++){
			out.append(highscoreEntrys.get(i).toString()).append("\n");
		}
		out.deleteCharAt(out.length()-1);
		return out.toString();
	}
}

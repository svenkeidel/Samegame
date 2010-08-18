package de.tu_darmstadt.gdi1.samegame.highscore;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Scanner;

import java.util.regex.Pattern;

import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;


class HighscoreEntry implements Comparable<HighscoreEntry>{

	private String name;
	private double remainingTime;
	private double points;
	private Date date;
	private final static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH;mm;ss");
	
	private final static String HIGHSCORE_INFORMATION = 
		// informations like (name|points|date|remaining time)
		"(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)";

	// Pattern for highscore entrys
	public static final String HIGHSCORE_ENTRY =
		"###"+HIGHSCORE_INFORMATION+"(\\|"+HIGHSCORE_INFORMATION+"){3}";
	
	HighscoreEntry(final String playername, 
				   final double rem_time,
				   final Date creation_date, 
				   final double points){

		this.name = playername;
		this.remainingTime = rem_time;
		this.date = creation_date;
		this.points = points;
	}

	HighscoreEntry(String highscoreEntry, int line)
		throws WrongLevelFormatException{
		this.points = 0;
		this.remainingTime = 0;
		this.date = new Date();
		this.name = "";
		parseHighscoreEntry(highscoreEntry, line);
	}


	/**
	 * Gets the points the player made.
	 *
	 * @return The points the player made.
	 */
	public double getPoints(){
		return this.points;
	}

	/**
	 * Gets the remaining time the player had
	 *
	 * @return The remainingTime the player had
	 */
	public double getRemainingTime(){
		return this.remainingTime;
	}

	/**
	 * Gets the date when the player hit the highscore
	 *
	 * @return The date when the player hit the highscore
	 */
	public Date getDate(){
		return this.date;
	}

	/**
	 * Gets the player name.
	 *
	 * @return The players name.
	 */
	public String getPlayername(){
		return this.name;
	}


	/**
	 * validates if the highscore entry string is in the right format.
	 *
	 * @throws WrongLevelFormatException if the entry is not in the 
	 * right format
	 */
	public static void validate(String highscoreEntry, int line)
		throws WrongLevelFormatException{

		if(!Pattern.matches(HIGHSCORE_ENTRY, highscoreEntry))
			throw new WrongLevelFormatException(
					"Wrong level format, the given "+line+".highscore entry isn't"
					+ " valide: " + highscoreEntry);

		Scanner s = new Scanner(highscoreEntry);

		s.skip("###");
		
		String[] infos = new String[4];
		String[] values = new String[4];
		
		// scanning informations
		for(int i=0; i<4; i++){
			infos[i] = s.findInLine("(\\w|_)*");
			s.skip(":");

			values[i] = s.findInLine("[^\\|]*");

			if(s.hasNext("\\|.*"))
				s.skip("\\|");
		}
		
		// if there are duplicated informations
		for(int i=0; i<4; i++)
			for(int j=i+1; j<4; j++)
				if(infos[i].equals(infos[j]))
					throw new WrongLevelFormatException(
							"wrong level format while parsing HighscoreList " 
							+"from string at line "+line+": dublicated "
							+"level informations");

		for(int i=0; i<4; i++){
			try{
				if(infos[i].equals("points")){
					Double.parseDouble(values[i]);
				}else if(infos[i].equals("date")){
					df.parse(values[i]);
				}else if(infos[i].equals("rem_time")){
					Double.parseDouble(values[i]);
				}
			}catch(NumberFormatException e){
				throw new WrongLevelFormatException(
						"wrong level format while parsing HighscoreList from string "
						+"at line "+line+": " 
						+ e.getMessage());
			}catch(ParseException e){
				throw new WrongLevelFormatException(
						"wrong level format while parsing HighscoreList from string "
						+"at line "+line+": " 
						+ e.getMessage());
			}
		}
	}


	/**
	 * parses a highscore entry.
	 *
	 * @param line the current line in the parsing string
	 * @param sc the scanner which parses the string
	 *
	 * @throws WrongLevelFormatException if the highscore entry is not 
	 * in the right format
	 */
	private void parseHighscoreEntry(String highscoreEntry, int line)
		throws WrongLevelFormatException{

		validate(highscoreEntry, line);
		
		Scanner s = new Scanner(highscoreEntry);

		s.skip("###");
		
		String[] infos = new String[4];
		String[] values = new String[4];
		
		// scanning informations
		for(int i=0; i<4; i++){
			infos[i] = s.findInLine("(\\w|_)*");
			s.skip(":");

			values[i] = s.findInLine("[^\\|]*");

			if(s.hasNext("\\|.*"))
				s.skip("\\|");
		}
		
		for(int i=0; i<4; i++){
			try{
				if(infos[i].equals("name")){
					name = values[i];
				}else if(infos[i].equals("points")){
					points = Double.parseDouble(values[i]);
				}else if(infos[i].equals("date")){
					date = df.parse(values[i]);
				}else if(infos[i].equals("rem_time")){
					remainingTime = Double.parseDouble(values[i]);
				}
			}catch(NumberFormatException e){
				throw new WrongLevelFormatException(
						"wrong level format while parsing HighscoreList from string "
						+"at line "+line+": " 
						+ e.getMessage());
			}catch(ParseException e){
				throw new WrongLevelFormatException(
						"wrong level format while parsing HighscoreList from string "
						+"at line "+line+": " 
						+ e.getMessage());
			}
		}
	}
	/**
	 * compare two highscore entries with identical comparison
	 *
	 * @param another another highscore entry
	 *
	 * @return 0 if the entries are identical
	 */
	public int compareTo(HighscoreEntry another){
		if(this.points != another.points)
			return (int) (another.points - this.points);
		else if(this.remainingTime != another.remainingTime)
			return (int) (another.remainingTime - this.remainingTime);
		else if(!this.date.equals(another.date))
			return this.date.compareTo(another.date);
		else
			return this.name.compareTo(another.name);
	}


	/**
	 * Get a String array representation of the entry
	 * <ol>
	 * 	<li>cell: points</li>
	 * 	<li>cell: remaining time</li>
	 * 	<li>cell: date</li>
	 * 	<li>cell: player's name</li>
	 * </ol>
	 *
	 * @return a String representation in the above described form
	 */
	public String[] toStringArray(){
		String[] entry = new String[4];
		entry[0] = ""+points;
		entry[1] = ""+remainingTime;
		entry[2] = df.format(date);
		entry[3] = name;
		return entry;
	}

	/**
	 * get a string representation of the entry.<br>
	 * The highscore entry has the following format:<br>
	 * ###name:[string]|points:[number]|date:dd.mm.yy HH;MM;SS|
	 * rem_time:[number]
	 *
	 * @return a String representation in the above described form
	 */
	@Override
	public String toString(){
		return "###name:"+name+"|points:"+(long)points+"|date:"+df.format(date)+"|rem_time:"+(long)remainingTime;
	}
}

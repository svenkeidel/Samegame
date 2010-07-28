package de.tu_darmstadt.gdi1.samegame.highscore;

import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;

import java.util.Scanner;
import java.util.Date;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.io.LineNumberReader;

class HighscoreEntry implements Comparable<HighscoreEntry>{
	private String name;
	private double remaingTime;
	private double points;
	private Date date;
	private final static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH;mm;ss");

	// Pattern for highscore entrys
	public static final Pattern HIGHSCORE_ENTRY =
		Pattern.compile(
				// a "###" at the beginning of the line with one of the highscore
				// informations like (name|points|date|remaining time)
				"###(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)"
				// With a following "|" and another highscore information
				+"(\\|(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)){3}$", 
				Pattern.MULTILINE);
	
	HighscoreEntry(final String playername, 
				   final double rem_time,
				   final Date creation_date, 
				   final double points){

		this.name = playername;
		this.remaingTime = rem_time;
		this.date = creation_date;
		this.points = points;
	}

	HighscoreEntry(LineNumberReader line, Scanner s)
		throws WrongLevelFormatException{
		this.points = 0;
		this.remaingTime = 0;
		this.date = new Date();
		this.name = "";
		parseHighscoreEntry(line, s);
	}


	/**
	 * Gets the points for this instance.
	 * @return The points.
	 */
	public double getPoints(){
		return this.points;
	}

	/**
	 * Gets the remaingTime for this instance.
	 * @return The remaingTime.
	 */
	public double getRemaingTime(){
		return this.remaingTime;
	}

	/**
	 * Gets the date for this instance.
	 * @return The date.
	 */
	public Date getDate(){
		return this.date;
	}

	/**
	 * Gets the name for this instance.
	 * @return The name.
	 */
	public String getPlayername(){
		return this.name;
	}
	
	public int compareTo(HighscoreEntry another){
		if(this.points != another.points)
			return (int) (this.points - another.points);
		else if(this.remaingTime != another.remaingTime)
			return (int) (this.remaingTime - another.remaingTime);
		else if(!this.date.equals(another.date))
			return this.date.compareTo(another.date);
		else
			return this.name.compareTo(another.name);
	}

	public String[] toStringArray(){
		String[] entry = new String[4];
		entry[0] = ""+points;
		entry[1] = ""+remaingTime;
		entry[2] = df.format(date);
		entry[3] = name;
		return entry;
	}

	@Override
	public String toString(){
		return "###name:"+name+"|points:"+points+"|date:"+df.format(date)+"|rem_time:12";
	}

	public static void validate(String highscoreEntry)
		throws WrongLevelFormatException{

		Scanner s = new Scanner(highscoreEntry);

		s.skip("###");
		
		String[] infos = new String[5];
		String[] values = new String[5];
		
		// scanning informations
		for(int i=0; i<5; i++){
			infos[i] = s.findInLine("(\\w|_)*");
			s.skip(":");

			values[i] = s.findInLine("[^\\|]*");

			if(s.hasNext("\\|"))
				s.skip("\\|");
		}
	}


	private void parseHighscoreEntry(LineNumberReader line, Scanner s)
		throws WrongLevelFormatException{

		s.skip("###");
		
		String[] infos = new String[5];
		String[] values = new String[5];
		
		// scanning informations
		for(int i=0; i<5; i++){
			infos[i] = s.findInLine("(\\w|_)*");
			s.skip(":");

			values[i] = s.findInLine("[^\\|]*");

			if(s.hasNext("\\|"))
				s.skip("\\|");
		}
		
		// if there are duplicated informations
		for(int i=0; i<5; i++)
			for(int j=i+1; j<5; j++)
				if(infos[i].equals(infos[j]))
					throw new WrongLevelFormatException(
							"wrong level format while parsing HighscoreList " 
							+"from string at line "+line.getLineNumber()+": dublicated "
							+"level informations");
		
		for(int i=0; i<5; i++){
			try{
				if(infos[i].equals("name")){
					name = values[i];
				}else if(infos[i].equals("points")){
					points = Double.parseDouble(values[i]);
				}else if(infos[i].equals("date")){
					date = df.parse(values[i]);
				}else if(infos[i].equals("rem_time")){
					remaingTime = Double.parseDouble(values[i]);
				}
			}catch(NumberFormatException e){
				throw new WrongLevelFormatException(
						"wrong level format while parsing HighscoreList from string "
						+"at line "+line.getLineNumber()+": " 
						+ e.getMessage());
			}catch(ParseException e){
				throw new WrongLevelFormatException(
						"wrong level format while parsing HighscoreList from string "
						+"at line "+line.getLineNumber()+": " 
						+ e.getMessage());
			}
		}
	}
}

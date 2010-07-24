package de.tu_darmstadt.gdi1.samegame.highscore;

import static java.util.Collections.*;
import java.util.Vector;
import java.util.Date;
import java.util.Scanner;
import java.io.LineNumberReader;
import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;

public class Highscore{

	private Vector<HighscoreEntry> highscoreEntrys;

	public Highscore(Vector<HighscoreEntry> highscoreEntrys){
		sort(highscoreEntrys);
		this.highscoreEntrys = highscoreEntrys;
	}

	public Highscore(LineNumberReader line, Scanner s) 
		throws WrongLevelFormatException{
		highscoreEntrys = new Vector<HighscoreEntry>();
		parseHighscoreEntrys(line, s);
	}

	public void insertHighscore(int points, int remainingTime, String name){
		 highscoreEntrys.add(
				 new HighscoreEntry(points, 
									remainingTime, 
									new Date(),
									name));
		sort(highscoreEntrys);
	}

	public String[][] getHighscoreEntrys(){
		String[][] entrys = new String[highscoreEntrys.size()][4];
		for(int i=0; i<entrys.length; i++)
			entrys[i] = highscoreEntrys.get(i).toStringArray();
		return highscoreEntrys.toArray(entrys);
	}

	private void parseHighscoreEntrys(LineNumberReader line, Scanner s)
		throws WrongLevelFormatException{
		while(s.hasNextLine()){
			if(!s.hasNext(
				"^###(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)"
				+"(\\|(name:(\\w|\\s)*|points:\\d*|date:\\d\\d\\.\\d\\d\\.\\d{4} \\d\\d;\\d\\d;\\d\\d|rem_time:\\d*)){3}$"))
				throw new WrongLevelFormatException(
						"wrong level format while parsing HighscoreList "+ 
						"from String at line "+line.getLineNumber());
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
}

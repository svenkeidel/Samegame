package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;



import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended2;
import static org.junit.Assert.*;

/**
 *
 * Is the highscore-store in the level-string correct?
 * Remember: input could be unsorted - but your output have to be sorted always. 
 */
public class HighscoreInLevelString {
	
	
	SameGameTestAdapterExtended2 adapter;
	
	private final static String level_1 = 
		"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" +
		"###target_time:25|min_stones:2\n" +
		"###name:Max M|points:200|date:01.04.2010 23;59;12|rem_time:12"; 
	
	private final static String level_2_unsorted = 
		"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" +
		"###target_time:25|min_stones:2\n" +
		"###name:Max M|points:120|date:28.05.2010 12;03;12|rem_time:14\n" +
		"###name:Max M|points:200|date:01.04.2010 23;59;12|rem_time:12\n"; //newline at the end should be ok for input
	private final static String level_2_sorted = 
		"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" +
		"###target_time:25|min_stones:2\n" +
		"###name:Max M|points:200|date:01.04.2010 23;59;12|rem_time:12\n" +
		"###name:Max M|points:120|date:28.05.2010 12;03;12|rem_time:14";
	private final static String level_2_without = 
		"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212";
	
	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended2();
		adapter.resetHighscore();
	}
	
	@Test
	public void HighscoreInLevelStringIsOk() {
		adapter.loadLevelFromString(level_1);
		assertTrue("A Level with some highscoreentrys in the level-string is valid!", adapter.isCorrectLevel());
		assertEquals("The highscoreentrys should also be given back in 'getLevelAsStringWithExtraInfo'", level_1, adapter.getLevelAsStringWithExtraInfo());
	}
	
	@Test
	public void UnsortedHighscoreInLevelStringIsOk() {
		adapter.loadLevelFromString(level_2_unsorted);
		assertTrue(
				"A Level with some highscoreentrys in the level-string is valid! Also the newline at the end is - for input - valid.",
				adapter.isCorrectLevel());
		assertEquals(
				"'getLevelAsStringWithoutExtraInfo' should return only the level-informations, without the highscore (and without any newline at the end).",
				level_2_without, adapter.getLevelAsStringWithoutExtraInfo());
		assertEquals(
				"'getLevelAsStringWithExtraInfo' should return also the highscore - but sorted! (and without any newline at the end)",
				level_2_sorted, adapter.getLevelAsStringWithExtraInfo());
	}
	
}

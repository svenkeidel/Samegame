package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended2;

/**
 * Tests whether the highscore-entries were correctly handled and sorted. 
 */
public class HighscoreTest {
	

	private final Date date1 = new Date(1234567890000l);
	private final Date date2 = new Date(1234567880000l);
	
	
	private final String player1 = "player1";
	private final String player2 = "player2";
	private final String player3 = "player3";
	private final String player4 = "player4";
	
	SameGameTestAdapterExtended2 adapter;
	
	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended2();
		adapter.resetHighscore();
	}
	
	
	@Test
	public void testHighscoreOneEntry() {
		assertEquals("After resetHighscore the highscore-count should be zero.", 0, adapter.getHighscoreCount());
		
		adapter.addHighscoreEntry(player1, 10, date1, 55);
		
		assertEquals("After addHighscoreEntry the highscore should contain exactly one entry.", 1, adapter.getHighscoreCount());
		assertEquals("The date representation of the sole entry is wrong", date1, adapter.getDateAtHighscorePosition(0));
		assertEquals("The name of the sole entry is wrong", player1, adapter.getPlayernameAtHighscorePosition(0));
		assertEquals("The remaining time (to target-time) of the sole entry is wrong", 10, adapter.getTimeAtHighscorePosition(0), 0);
		assertEquals("The points of the sole entry are wrong", 55, adapter.getPointsAtHighscorePosition(0), 0);
	}

	@Test
	public void testHighscoreIllegalAccess() {
		assertEquals("After resetHighscore the highscore-count should be zero.", 0, adapter.getHighscoreCount());
		adapter.addHighscoreEntry(player1, 10, date1, 55);

		for (int i = -20; i < 0; i++) {
			assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i));
			assertNull("The date of a non-existing entry should be null.", adapter.getDateAtHighscorePosition(i));
			assertEquals("The points of a non-existing entry should be -1.", -1, adapter
					.getPointsAtHighscorePosition(i), 0);
			assertEquals("The remaining time of a non-existing entry should be -1.", -1, adapter
					.getTimeAtHighscorePosition(i), 0);
		}

		assertNotNull("The name of an existing entry should not be null.", adapter.getPlayernameAtHighscorePosition(0));
		assertNotNull("The date of an existing entry should not be null.", adapter.getDateAtHighscorePosition(0));
		assertEquals("The points of the existing entry should be 55.", 55, adapter.getPointsAtHighscorePosition(0), 0);
		assertEquals("The remaining time of the existing entry should be 10.", 10, adapter
				.getTimeAtHighscorePosition(0), 0);

		for (int i = 1; i < 20; i++) {
			assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i));
			assertNull("The date of a non-existing entry should be null.", adapter.getDateAtHighscorePosition(i));
			assertEquals("The points of a non-existing entry should be -1.", -1, adapter
					.getPointsAtHighscorePosition(i), 0);
			assertEquals("The remaining time of a non-existing entry should be -1.", -1, adapter
					.getTimeAtHighscorePosition(i), 0);
		}
	}
	
	
	@Test
	public void testHighscoreMaxCount1() {
		adapter.addHighscoreEntry("playerA", -5, date1, 17);
		adapter.addHighscoreEntry("playerB", -5, date1, 17);
		adapter.addHighscoreEntry("playerC", -5, date1, 17);
		adapter.addHighscoreEntry("playerD", -5, date1, 17);
		adapter.addHighscoreEntry("playerE", -5, date1, 17);
		adapter.addHighscoreEntry("playerF", -5, date1, 17);
		adapter.addHighscoreEntry("playerG", -5, date1, 17);
		adapter.addHighscoreEntry("playerH", -5, date1, 17);
		adapter.addHighscoreEntry("playerI", -5, date1, 17);
		adapter.addHighscoreEntry("player10", -5, date1, 17);
		adapter.addHighscoreEntry("player11", -5, date1, 17);
		adapter.addHighscoreEntry("player12", -5, date1, 17);
		adapter.addHighscoreEntry("playerJ", -5, date1, 17);
		adapter.addHighscoreEntry("player0a", -5, date1, 17);
		adapter.addHighscoreEntry("player15", -5, date1, 17);
		adapter.addHighscoreEntry("player16", -5, date1, 17);
		adapter.addHighscoreEntry("player17", -5, date1, 17);
		adapter.addHighscoreEntry("player18", -5, date1, 17);
		adapter.addHighscoreEntry("player19", -5, date1, 17);
		adapter.addHighscoreEntry("player80", -5, date1, 17);
		
		assertEquals("You should save by maximum 10 entries.", 10, adapter.getHighscoreCount());
		
		int i = -4;
		
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player0a", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player10", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player11", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player12", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player15", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player16", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player17", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player18", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player19", adapter.getPlayernameAtHighscorePosition(i++));
		assertEquals("The name of an existing entry is wrong. Maybe wrongly sorted?", "player80", adapter.getPlayernameAtHighscorePosition(i++));
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
		assertNull("The name of a non-existing entry should be null.", adapter.getPlayernameAtHighscorePosition(i++));
	}

	
	@Test
	public void testHighscoreSortAfterPoints() {
		adapter.addHighscoreEntry(player2, 10, date1, 56);
		adapter.addHighscoreEntry(player3, 10, date1, 58);
		adapter.addHighscoreEntry(player4, 10, date1, 57);
		adapter.addHighscoreEntry(player1, 10, date1, 55);
		
		assertEquals(4, adapter.getHighscoreCount());

		assertEquals(player3, adapter.getPlayernameAtHighscorePosition(0));
		assertEquals(58, adapter.getPointsAtHighscorePosition(0), 0);
		assertEquals(player4, adapter.getPlayernameAtHighscorePosition(1));
		assertEquals(57, adapter.getPointsAtHighscorePosition(1), 0);
		assertEquals(player2, adapter.getPlayernameAtHighscorePosition(2));
		assertEquals(56, adapter.getPointsAtHighscorePosition(2), 0);
		assertEquals(player1, adapter.getPlayernameAtHighscorePosition(3));
		assertEquals(55, adapter.getPointsAtHighscorePosition(3), 0);
		
		for (int i = 0; i <= 3; i++) {
			assertEquals(date1, adapter.getDateAtHighscorePosition(i));
			assertEquals( 10, adapter.getTimeAtHighscorePosition(i), 0);
		}
	}
	
	
	@Test
	public void testHighscoreSortAfterRemTime() {
		adapter.addHighscoreEntry(player4, -8, date1, 55);
		adapter.addHighscoreEntry(player3, -100, date1, 58);
		adapter.addHighscoreEntry(player1, -10, date1, 55);
		adapter.addHighscoreEntry(player2, 8, date1, 55);
		
		assertEquals(4, adapter.getHighscoreCount());

		assertEquals(player3, adapter.getPlayernameAtHighscorePosition(0));
		assertEquals(58, adapter.getPointsAtHighscorePosition(0), 0);
		assertEquals(-100, adapter.getTimeAtHighscorePosition(0), 0);
		
		assertEquals(player2, adapter.getPlayernameAtHighscorePosition(1));
		assertEquals(55, adapter.getPointsAtHighscorePosition(1), 0);
		assertEquals(8, adapter.getTimeAtHighscorePosition(1), 0);

		assertEquals(player4, adapter.getPlayernameAtHighscorePosition(2));
		assertEquals(55, adapter.getPointsAtHighscorePosition(2), 0);
		assertEquals(-8, adapter.getTimeAtHighscorePosition(2), 0);

		assertEquals(player1, adapter.getPlayernameAtHighscorePosition(3));
		assertEquals(55, adapter.getPointsAtHighscorePosition(3), 0);
		assertEquals(-10, adapter.getTimeAtHighscorePosition(3), 0);

		
		for (int i = 0; i <= 3; i++) {
			assertEquals(date1, adapter.getDateAtHighscorePosition(i));
		}
	}
	
	@Test
	public void testHighscoreSortAfterDate() {
		
		adapter.addHighscoreEntry(player2, 8, date1, 55);
		adapter.addHighscoreEntry(player1, -1, date1, 50);
		adapter.addHighscoreEntry(player4, 8, date2, 55);
		adapter.addHighscoreEntry(player3, -7, date1, 55);
		
		assertEquals(4, adapter.getHighscoreCount());

		assertEquals(player4, adapter.getPlayernameAtHighscorePosition(0));
		assertEquals(55, adapter.getPointsAtHighscorePosition(0), 0);
		assertEquals(8, adapter.getTimeAtHighscorePosition(0), 0);
		assertEquals(date2, adapter.getDateAtHighscorePosition(0));
		
		
		assertEquals(player2, adapter.getPlayernameAtHighscorePosition(1));
		assertEquals(55, adapter.getPointsAtHighscorePosition(1), 0);
		assertEquals(8, adapter.getTimeAtHighscorePosition(1), 0);

		assertEquals(player3, adapter.getPlayernameAtHighscorePosition(2));
		assertEquals(55, adapter.getPointsAtHighscorePosition(2), 0);
		assertEquals(-7, adapter.getTimeAtHighscorePosition(2), 0);

		assertEquals(player1, adapter.getPlayernameAtHighscorePosition(3));
		assertEquals(50, adapter.getPointsAtHighscorePosition(3), 0);
		assertEquals(-1, adapter.getTimeAtHighscorePosition(3), 0);

		
		for (int i = 1; i <= 3; i++) {
			assertEquals(date1, adapter.getDateAtHighscorePosition(i));
		}
	}
	
	
	@Test
	public void testHighscoreSortAfterName() {
		
		adapter.addHighscoreEntry(player2, -1, date1, 49);
		adapter.addHighscoreEntry(player1, -1, date1, 49);
		
		adapter.addHighscoreEntry(player3, 1, date2, 49);
		adapter.addHighscoreEntry(player4, 1, date2, 49);
		
		assertEquals(4, adapter.getHighscoreCount());

		assertEquals(player3, adapter.getPlayernameAtHighscorePosition(0));
		assertEquals(49, adapter.getPointsAtHighscorePosition(0), 0);
		assertEquals(1, adapter.getTimeAtHighscorePosition(0), 0);
		assertEquals(date2, adapter.getDateAtHighscorePosition(0));
		
		
		assertEquals(player4, adapter.getPlayernameAtHighscorePosition(1));
		assertEquals(49, adapter.getPointsAtHighscorePosition(1), 0);
		assertEquals(1, adapter.getTimeAtHighscorePosition(1), 0);
		assertEquals(date2, adapter.getDateAtHighscorePosition(1));

		assertEquals(player1, adapter.getPlayernameAtHighscorePosition(2));
		assertEquals(49, adapter.getPointsAtHighscorePosition(2), 0);
		assertEquals(-1, adapter.getTimeAtHighscorePosition(2), 0);
		assertEquals(date1, adapter.getDateAtHighscorePosition(2));
		
		assertEquals(player2, adapter.getPlayernameAtHighscorePosition(3));
		assertEquals(49, adapter.getPointsAtHighscorePosition(3), 0);
		assertEquals(-1, adapter.getTimeAtHighscorePosition(3), 0);
		assertEquals(date1, adapter.getDateAtHighscorePosition(3));
	}
	
	
	
}

package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended1;


/**
 * Tests whether the point calculation is correct.
 *  
 * @author jonas
 *
 */
public class CalcPointsTest {

	
	SameGameTestAdapterExtended1 adapter;
	
	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended1();
	}
	
	@Test
	public void basicPointsTest() {
		assertEquals("You should return the points you get for the given step - not the points the player has in total.", 100, adapter.calculatePoints(10, false));
		assertEquals("You should return the points you get for the given step - not the points the player has in total.", 25, adapter.calculatePoints(5, false));
		assertEquals("You should return the points you get for the given step - not the points the player has in total.", 50, adapter.calculatePoints(10, true));
		assertEquals("You should return the points you get for the given step - not the points the player has in total.", 12, adapter.calculatePoints(5, true));
		assertEquals(-10, adapter.calculatePointsFinished(20, 10, 25));
		assertEquals(35, adapter.calculatePointsFinished(0, 10, 25));
	}

}

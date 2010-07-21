package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended1;


/**
 * Tests whether the stones were removed correctly.
 * This test includes levels with additional informations.
 */
public class RemoveStoneExtendedTest {
	
	String level_L =
		"12121212121233\n"+
		"12121212121313\n"+
		"12131212521332\n"+
		"12131215521213\n"+
		"12133242521213\n"+
		"12124442121232\n"+
		"12121212121233\n"+
		"###min_stones:4";
	
	String level_L_nothing =
		"12121212121233\n"+
		"12121212121313\n"+
		"12131212521332\n"+
		"12131215521213\n"+
		"12133242521213\n"+
		"12124442121232\n"+
		"12121212121233";

		String level_L_3_fin = //3|2
		"12100212121233\n"+
		"12101212121313\n"+
		"12101212521332\n"+
		"12121215521213\n"+
		"12121242521213\n"+
		"12124442121232\n"+
		"12121212121233";


		String level_L_4_fin = //4|5
		"12120002121233\n"+
		"12121202121313\n"+
		"12131212521332\n"+
		"12131215521213\n"+
		"12131212521213\n"+
		"12123212121232\n"+
		"12121212121233";

		String level_L_5_fin = //7|3
			"12121210021233\n"+
			"12121212021313\n"+
			"12131212021332\n"+
			"12131212121213\n"+
			"12133242121213\n"+
			"12124442121232\n"+
			"12121212121233";
	
		
	private SameGameTestAdapterExtended1 adapter;

	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended1();
	}

	
	@Test
	public void testToLessStones() {
		adapter.loadLevelFromString(level_L);
		assertTrue(adapter.isCorrectLevel());
		assertFalse(adapter.cellSelected(12, 0));
		assertEquals("This Stone should not be possible to remove.", level_L_nothing, adapter.getLevelAsStringWithoutExtraInfo());

		assertFalse(adapter.cellSelected(13, 1));
		assertEquals("This Stone should not be possible to remove.", level_L_nothing, adapter.getLevelAsStringWithoutExtraInfo());
		
		assertFalse(adapter.cellSelected(11, 1));
		assertEquals("This Stone should not be possible to remove.", level_L_nothing, adapter.getLevelAsStringWithoutExtraInfo());
		
		assertFalse(adapter.cellSelected(12, 3));
		assertEquals("This Stone should not be possible to remove.", level_L_nothing, adapter.getLevelAsStringWithoutExtraInfo());

		assertFalse(adapter.cellSelected(12, 5));
		assertEquals("This Stone should not be possible to remove.", level_L_nothing, adapter.getLevelAsStringWithoutExtraInfo());
	}

		
	@Test
	public void testRemoveBigs() {
		adapter.loadLevelFromString(level_L);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(3, 2));
		assertEquals("After removing stones the level is wrong.", level_L_3_fin, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.loadLevelFromString(level_L);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(4, 5));
		assertEquals("After removing stones the level is wrong.", level_L_4_fin, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_L);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(7, 3));
		assertEquals("After removing stones the level is wrong.", level_L_5_fin, adapter.getLevelAsStringWithoutExtraInfo());
	}
	
}

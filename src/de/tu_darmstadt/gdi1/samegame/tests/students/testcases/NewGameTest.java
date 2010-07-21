package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended1;


/**
 * Tests whether the action "new-game" is handled correct.
 */
public class NewGameTest {

	SameGameTestAdapterExtended1 adapter;
	String level_1 = "1212\n" + "1212\n" + "1212\n" + "1212";

	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended1();
	}

	@Test
	public void testHandleKeyPressedNew() {
		adapter.loadLevelFromString(level_1);

		adapter.cellSelected(0, 0);
		assertFalse("The level should change after a click to 0|0.", adapter.getLevelAsStringWithoutExtraInfo().equals(level_1));
		adapter.handleKeyPressedNew();
		assertTrue("After 'newgame-key' was pressed the level should be in the initial state. ", adapter.getLevelAsStringWithoutExtraInfo().equals(level_1));
	}

}

package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended2;

/**
 * Tests whether the undo-redo functionality work as specified. 
 */
public class UndoRedoTest {
	
	String level_1_0 = "12345\n" + "12345\n" + "12345\n" + "12345";
	String level_1_1 = "12340\n" + "12340\n" + "12340\n" + "12340";
	String level_1_2 = "12300\n" + "12300\n" + "12300\n" + "12300";
	String level_1_3 = "23450\n" + "23450\n" + "23450\n" + "23450";

	SameGameTestAdapterExtended2 adapter;
	
	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended2();
	}
	
	@Test
	public void testUndoOne() {
		adapter.loadLevelFromString(level_1_0);
		adapter.cellSelected(4, 1); //clear last column
		assertEquals("A click to 4|1 should clear the last column.", level_1_1, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.undo();
		assertEquals("After undo the level should be in initial state.", level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.cellSelected(4, 1); //clear last column
		adapter.cellSelected(3, 1); //clear (new) last column
		assertEquals("Two columns should be cleared.", level_1_2, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.undo();
		assertEquals("After undo only the last column should be cleared.", level_1_1, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.undo();
		assertEquals("After second undo level should be in initial state.", level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.undo();
		assertEquals("More undo's than actions should not change something.", level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.undo();
		assertEquals("More undo's than actions should not change something.", level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.undo();
		assertEquals("More undo's than actions should not change something.", level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.undo();
		assertEquals("More undo's than actions should not change something.", level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
	}
	
	
	@Test
	public void testUndoAndRedoOne() {
		adapter.loadLevelFromString(level_1_0);
		adapter.cellSelected(4, 1); //clear last column
		assertEquals("A click to 4|1 should clear the last column.", level_1_1, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.undo();
		assertEquals("After undo the level should be in initial state.", level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.redo();
		assertEquals("After redo the last column should be empty.", level_1_1, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.cellSelected(3, 1); //clear (new) last column
		assertEquals("Two columns should be cleared.", level_1_2, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.undo();
		assertEquals("After undo only the last column should be cleared.", level_1_1, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.redo();
		assertEquals("Two columns should be cleared.", level_1_2, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.redo();
		assertEquals("More redo's than undos should not change something.", level_1_2, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.redo();
		assertEquals("More redo's than undos should not change something.", level_1_2, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.redo();
		assertEquals("More redo's than undos should not change something.", level_1_2, adapter.getLevelAsStringWithoutExtraInfo());
	}
	

	@Test
	public void testUndoImpossible() throws Exception {
		adapter.loadLevelFromString(level_1_0);
		adapter.cellSelected(4, 1); // clear last column
		assertEquals("A click to 4|1 should clear the last column.", level_1_1, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.undo();
		assertEquals("After undo the level should be in initial state.", level_1_0, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.cellSelected(0, 1); // clear first column
		assertEquals("A click to 0|1 should clear the first column.", level_1_3, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.redo();
		assertEquals("Redo impossible if some action has been taken after an undo. Therefore, redo may not change anything.",
				level_1_3, adapter.getLevelAsStringWithoutExtraInfo());
	}
}

package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;



import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterMinimal;
import static org.junit.Assert.*;


/**
 * Tests whether the implementation detect levels correctly as finished.
 * This tests use only {@link SameGameTestAdapterMinimal}
 * and let only load levels without additional informations.
 * So you should pass this test if you had implemented the minimal stage.
 *
 */
public class FinishDetectionTest {
	
	String level_initial = "123\n" + "312";
	
	String level_trivial = "1111\n" + "3232";
	String level_trivial_1 = "0000\n" + "3232";

	String level_0 = "0000\n" + "1212\n" + "2121\n" + "1212";
	
	String level_1_1 = "1212\n" + "1212\n" + "1212\n" + "1212";
	
	String level_2_1 = "1511\n" + "1511\n" + "3432\n" + "3432";
	String level_2_2 = "0000\n" + "0000\n" + "0000\n" + "0000";
	
	String level_3_1 = "1212\n" + "1212\n" + "1212\n" + "3333";

	
	SameGameTestAdapterMinimal adapter;
	

	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterMinimal();
	}
	@Test
	public void initialFinish() {
		adapter.loadLevelFromString(level_initial);
		assertTrue("This loaded level is already finished, but you do not detect this.", adapter.isFinished());
	}

		
	@Test
	public void trivialFinish() {
		adapter.loadLevelFromString(level_trivial);
		assertFalse("This loaded level is not finished!", adapter.isFinished());
		adapter.cellSelected(0, 0);
		assertEquals("Level should be cleared after one click.", level_trivial_1, adapter.getLevelAsStringWithoutExtraInfo());
		assertTrue("Empty level should be detected as finished.", adapter.isFinished());
	}
	
	@Test
	public void zeroClickFinish() {
		adapter.loadLevelFromString(level_0);
		assertTrue("This level is finished already.", adapter.isFinished());
	}
	
	@Test 
	public void fourClickFinish() {
		adapter.loadLevelFromString(level_1_1);
		assertFalse("This loaded level is not finished!", adapter.isFinished());
		
		adapter.cellSelected(3, 3);
		adapter.cellSelected(2, 2);
		assertFalse("This level is not finished yet!", adapter.isFinished());
		adapter.cellSelected(1, 2);
		assertFalse("This level is not finished yet!", adapter.isFinished());
		adapter.cellSelected(0, 3);
		
		assertTrue("This level should be detected as finished now!", adapter.isFinished());
		
	}
	
	
	@Test 
	public void fourClickFinishTwo() {
		adapter.loadLevelFromString(level_3_1);
		assertFalse("This loaded level is not finished!", adapter.isFinished());
		
		adapter.cellSelected(3, 0); //clear last column, except of last row
		adapter.cellSelected(2, 1); //clear next-to-last column, except of last row
		assertFalse("This level is not finished yet!", adapter.isFinished());
		adapter.cellSelected(1, 2);
		assertFalse("This level is not finished yet!", adapter.isFinished());
		adapter.cellSelected(0, 0); //clear first column, except of last row
		assertFalse("This level is not finished yet!", adapter.isFinished());
		adapter.cellSelected(3, 3); //clear lowest (last) row.
		
		assertTrue("This level should be detected as finished now!", adapter.isFinished());
		
	}
	
	@Test 
	public void eightClickFinishWithSlide() {
		adapter.loadLevelFromString(level_2_1);
		assertFalse("This loaded level is not finished!", adapter.isFinished());
		adapter.cellSelected(2, 2); 
		adapter.cellSelected(2, 2); //now third column (id=2) is cleared, but fourth will slide
		adapter.cellSelected(2, 2); //(new) third column: lower half is cleared, upper half will drop.
		adapter.cellSelected(2, 3); //third column is cleared at all.
		assertFalse("This level is not finished yet!", adapter.isFinished());
		
		adapter.cellSelected(0, 0); //upper half of first column is cleared
		assertFalse("This level is not finished yet!", adapter.isFinished());
		adapter.cellSelected(1, 0); //upper half of second column is cleared
		assertFalse("This level is not finished yet!", adapter.isFinished());
		
		adapter.cellSelected(0, 2); //first column is cleared at all, second will slide
		assertFalse("This level is not finished yet!", adapter.isFinished());
		adapter.cellSelected(0, 2); //(new) first column is cleared at all
		
		assertEquals("This should the cleared representation of this level.", level_2_2, adapter.getLevelAsStringWithoutExtraInfo());
		assertTrue("This level should be detected as finished now!", adapter.isFinished());
	}

}


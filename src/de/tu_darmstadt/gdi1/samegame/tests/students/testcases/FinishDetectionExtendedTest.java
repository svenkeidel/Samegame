package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended1;


/**
 * Tests whether the implementation detect levels correctly as finished
 * depending on the number of <b>min_stones specified in the additional informations</b>. 
 * 
 */
public class FinishDetectionExtendedTest {
	
	
	String level_initial_0 = "223344\n" + "112233\n" + "###target_time:25|min_stones:3";
	
	String level_one_1 = "012203\n" + "112245\n" + "###target_time:25|min_stones:4";
	String level_one_2 = "010300\n" + "114500\n" + "###target_time:25|min_stones:4";
	
	SameGameTestAdapterExtended1 adapter;
	

	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended1();
	}
	
	/**
	 * Tests whether the implementation detect a new loaded level 
	 * <b>with additional informations</b> correctly as already finished.
	 */
	@Test
	public void initialFinish() {
		adapter.loadLevelFromString(level_initial_0);
		assertTrue("This loaded level is already finished, because the number of min_stones is set to 3. But the student does not detect this.", adapter.isFinished());
	}

		
	/**
	 * Tests whether the implementation detect during the game if a level  
	 * <b>with additional informations</b> is detected correctly as finished.
	 */
	@Test
	public void oneClickFinish() {
		adapter.loadLevelFromString(level_one_1);
		assertFalse("This loaded level is not finished!", adapter.isFinished());
		adapter.cellSelected(2, 0);
		assertEquals("Level should be cleared after one click.", level_one_2, adapter.getLevelAsStringWithExtraInfo());
		assertTrue("Empty level should be detected as finished, because the number of min_stones was set to 4.", adapter.isFinished());
	}
}

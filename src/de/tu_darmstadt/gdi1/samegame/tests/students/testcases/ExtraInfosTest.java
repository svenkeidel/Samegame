package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.model.Level;
import de.tu_darmstadt.gdi1.samegame.view.SameGameViewer;

import de.tu_darmstadt.gdi1.samegame.exceptions.WrongLevelFormatException;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended1;

/**
 * Tests whether the 'additional informations' were handled correctly.
 * 
 */
public class ExtraInfosTest {
	
	String level_1_with = 
	"21212\n" + 
	"21212\n" + 
	"12345\n" + 
	"21212\n" + 
	"12121\n" + 
	"###target_time:400|min_stones:4" ;

	String level_1_without = 
	"21212\n" + 
	"21212\n" + 
	"12345\n" + 
	"21212\n" + 
	"12121";

	String level_1_withDefault = 
	"21212\n" + 
	"21212\n" + 
	"12345\n" + 
	"21212\n" + 
	"12121\n" + 
	"###target_time:25|min_stones:2";

	String level_2_0_with =  
	"22222\n" + 
	"11111\n" + 
	"12345\n" + 
	"21212\n" + 
	"11121\n" + 
	"###target_time:400|min_stones:5";

	String level_2_0_without = 
	"22222\n" + 
	"11111\n" + 
	"12345\n" + 
	"21212\n" + 
	"11121";

	String level_2_1_with = 
	"00000\n" + 
	"11111\n" + 
	"12345\n" + 
	"21212\n" + 
	"11121\n" + 
	"###target_time:400|min_stones:5" ;

	String level_2_1_without = 
	"00000\n" + 
	"11111\n" + 
	"12345\n" + 
	"21212\n" + 
	"11121";

	String level_2_2_with = 
	"00000\n" + 
	"00000\n" + 
	"02345\n" + 
	"21212\n" + 
	"11121\n" + 
	"###target_time:400|min_stones:5";

	String level_2_2_without = 
	"00000\n" + 
	"00000\n" + 
	"02345\n" + 
	"21212\n" + 
	"11121";

	String level_3_0_with = 	
	"1234512345\n"+
	"1133552244\n"+
	"2133552244\n"+
	"2133552244\n" +
	"###target_time:400|min_stones:5";
	
	String level_3_1_with =
	"1234512300\n"+
	"1133552200\n"+
	"2133552200\n"+
	"2133552250\n" +
	"###target_time:400|min_stones:5";
	
	String level_3_2_with = 
	"1234510000\n"+
	"1133550000\n"+
	"2133550000\n"+
	"2133553500\n" +
	"###target_time:400|min_stones:5";
	String level_3_3_with = 
	"1234000000\n"+
	"1133000000\n"+
	"2133000000\n"+
	"2133135000\n"+
	"###target_time:400|min_stones:5";
	
	String level_3_4_with = 
	"1200000000\n"+
	"1100000000\n"+
	"2100000000\n"+
	"2141350000\n" +
	"###target_time:400|min_stones:5";
	
	String level_3_5_with_finished = 
	"0000000000\n"+
	"0000000000\n"+
	"2000000000\n"+
	"2241350000\n"+
	"###target_time:400|min_stones:5";

	String[] wrongLevels = new String[]{
	// two times additional informations
	"21212\n" +
	"21212\n" + 
	"###target_time:400|min_stones:4\n" + 
	"###target_time:200|min_stones:4"
	,
	// level with additional information at the beginning
	"###target_time:400|min_stones:4\n" +
	"21212\n" +
	"21212\n"
	,
	// level with some unexpected chars
	"21212\n" +
	"21212\n" + 
	"###target_time:4|00|min_stones:4\n"
	,
	// level with one empty line between the field and the extra info
	"21212\n" +
	"21212\n\n" + 
	"###target_time:4|00|min_stones:4\n"
	,
	// highscore informations over level extra informations
	"21212\n" +
	"21212\n" + 
	"###name:Max M|points:120|date:28.05.2010 12;03;12|rem_time:14\n" +
	"###target_time:4|min_stones:4\n"
	,
	// level with dublicated level informations
	"21212\n" +
	"21212\n" + 
	"###target_time:4|target_time:5"
	,
	// level with no value for sth
	"21212\n" +
	"21212\n" + 
	"###target_time:|"
	,
	// other wrong string
	"21212\n" +
	"21212\n" + 
	"###target_time:abc|min_stonesasdf"} ;
	
	SameGameTestAdapterExtended1 adapter;

	Level level;
	SameGameViewer viewer;
	
	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended1();
		viewer = new SameGameViewer();
		level = new Level(viewer);
	}
	
	// own test
	@Test
	public void extraInfoIsWrong(){
		for(int i=0; i<wrongLevels.length; i++)
			try{
				level.loadLevelFromString(wrongLevels[i]);
				fail("the loaded level is wrong, the function " +
					 "loadLevelfromString(String) should throw an " +
					 "exception. Loaded Level: " + wrongLevels[i]);
			}catch(WrongLevelFormatException e){
				//every thing is all right because these are wrong levels
			}
	}
	
	@Test
	public void extraInfoIsCorrect() {
		adapter.loadLevelFromString(level_1_with);
		assertTrue("Level with additional information is correct.", adapter.isCorrectLevel());
		
		assertEquals("Number of min-Stones is wrong.", 4, adapter.getMinStones());
		assertEquals("Target-Time is wrong.", 400, adapter.getTargetTime(), 0);
		
		String tmp1 = adapter.getLevelAsStringWithExtraInfo();
		String tmp2 = adapter.getLevelAsStringWithoutExtraInfo();
		
		assertNotNull("getLevelAsStringWithExtraInfo should never return null after a legal level was loaded." , tmp1);
		assertNotNull("getLevelAsStringWithoutExtraInfo should never return null after a legal level was loaded." , tmp2);
		
		if (level_1_without.equals(tmp1)) {
			fail("getLevelAsStringWithExtraInfo should return the level WITH extra infos.");
		}
		assertEquals("Level with additonal-information is returned wrong.", level_1_with, tmp1);
		
	}

	@Test
	public void rightDefaulValues() {
		adapter.loadLevelFromString(level_1_without);
		assertTrue("A Level without additional information is still correct.", adapter.isCorrectLevel());
		assertEquals("Default value for 'min-stones' should be 2.", 2, adapter.getMinStones());
		assertEquals("Default 'target-time' should be the number of stones in seconds.", 5*5, adapter.getTargetTime() ,0);
		
		String tmp1 = adapter.getLevelAsStringWithExtraInfo();
		String tmp2 = adapter.getLevelAsStringWithoutExtraInfo();
		
		assertNotNull("getLevelAsStringWithExtraInfo should never return null after a legal level was loaded." , tmp1);
		assertNotNull("getLevelAsStringWithoutExtraInfo should never return null after a legal level was loaded." , tmp2);
		
		if (level_1_without.equals(tmp1)) {
			fail("getLevelAsStringWithExtraInfo should return the level WITH extra infos.");
		}
		assertEquals("Level without additonal-information is returned incorrectly.", level_1_without, tmp2);
		assertEquals("Level with additonal-information is returned incorrectly.", level_1_withDefault, tmp1);
	}

	
	@Test
	public void valuesCorrectLoaded() {
		adapter.loadLevelFromString(level_1_with);
		assertTrue("Level with additional information is correct.", adapter.isCorrectLevel());
		
		assertEquals("Number of min-Stones is wrong.", 4, adapter.getMinStones());
		assertEquals("Target-Time is wrong.", 400, adapter.getTargetTime(), 0);
	}
	
	@Test
	public void valuesCorrectLoadedAndUsed() {
		adapter.loadLevelFromString(level_2_0_with);
		assertTrue("Level with additional information is correct.", adapter.isCorrectLevel());
		
		assertEquals("Without any selection the level should stay in the initial state.", level_2_0_with, adapter.getLevelAsStringWithExtraInfo());
		assertEquals("Without any selection the level should stay in the initial state.", level_2_0_without, adapter.getLevelAsStringWithoutExtraInfo());
		
		assertEquals("Number of min-Stones is loaded wrongly.", 5, adapter.getMinStones());
		assertEquals("Target-Time is loaded wrongly.", 400, adapter.getTargetTime(), 0);
		
		assertFalse("With min-stones of 5 this click should not remove something.", adapter.cellSelected(5, 5));
		assertEquals("Without any legal selection the level should stay in the initial state.", level_2_0_with, adapter.getLevelAsStringWithExtraInfo());
		assertEquals("Without any legal selection the level should stay in the initial state.", level_2_0_without, adapter.getLevelAsStringWithoutExtraInfo());
		
		assertTrue("With min-stones of 5 this click should remove a row.", adapter.cellSelected(3, 0));
		assertEquals("The first row should be removed after click.", level_2_1_with, adapter.getLevelAsStringWithExtraInfo());
		assertEquals("The first row should be removed after click.", level_2_1_without, adapter.getLevelAsStringWithoutExtraInfo());
		
		assertTrue("With min-stones of 5 this click should remove a row and one filed.", adapter.cellSelected(3, 1));
		assertEquals("The first and second row and one field should be removed after second click.", level_2_2_with, adapter.getLevelAsStringWithExtraInfo());
		assertEquals("The first and second row and one field should be removed after second click.", level_2_2_without, adapter.getLevelAsStringWithoutExtraInfo());
	}
	
	@Test
	public void extraInfoIsFinished() {
		adapter.loadLevelFromString(level_3_0_with);
		assertTrue(adapter.isCorrectLevel());
		assertEquals(level_3_0_with, adapter.getLevelAsStringWithExtraInfo());
		
		assertTrue(adapter.cellSelected(9, 1));
		assertFalse(adapter.isFinished());
		assertEquals(level_3_1_with, adapter.getLevelAsStringWithExtraInfo());
		
		assertTrue(adapter.cellSelected(7, 2));
		assertFalse(adapter.isFinished());
		assertEquals(level_3_2_with, adapter.getLevelAsStringWithExtraInfo());
		
		assertTrue(adapter.cellSelected(5, 3));
		assertFalse(adapter.isFinished());
		assertEquals(level_3_3_with, adapter.getLevelAsStringWithExtraInfo());
		
		assertTrue(adapter.cellSelected(3, 2));
		assertFalse(adapter.isFinished());
		assertEquals(level_3_4_with, adapter.getLevelAsStringWithExtraInfo());

		assertTrue(adapter.cellSelected(0, 0));
		assertEquals(level_3_5_with_finished, adapter.getLevelAsStringWithExtraInfo());
		assertTrue("Because of min-stones of 5 this level should be detected as finished:\n" + level_3_5_with_finished, adapter.isFinished());
	}
}

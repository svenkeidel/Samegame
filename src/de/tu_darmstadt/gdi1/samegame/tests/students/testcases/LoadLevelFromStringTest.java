package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterMinimal;

/**
 * Tests whether the implementation detect levels in the right way as correct 
 * or incorrect.  
 */
public class LoadLevelFromStringTest {
	
	SameGameTestAdapterMinimal adapter;

	String[] correctLevels = {
			"12111\n" + "11111\n" + "11111\n" + "11111\n" + "11111\n" + "11111",
			"12321\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" + "12121",
			"12321\n" + "21212\n" + "12121\n" + "21212\n" + "21212",
			"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212",
			"54321\n" + "21212\n" + "12121\n" + "21212\n" + "21212",
			"21212\n" + "54321\n" + "12121\n" + "21212\n" + "21212",
	};
	
	String[] correctLevelsButOutputCouldDiffer = {
			"54321\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n", //closing line-break is allowed!
			"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" + "###min_stones:4", //only one info is ok!
			"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" + "###target_time:400",
			"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" + "###min_stones:4|target_time:400",
			};
	
	String level_zeroInside = 
		"50321\n" + 
		"21012\n" + 
		"21202"; //zero inside is allowed!
	String level_droppedZeroInside = 
		"50001\n" + 
		"21322\n" + 
		"21212";
	
	String[] incorrectLevels = {
			"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212" + "###", //empty info-lines are not allowed!
			"12311\n" + "11111\n" + "11\n" +    "11111\n" + "11111\n" + "11111",
			"12321\n" + "21212\n" + "12121\n"+  "21A12\n" + "21212\n" + "12121",
			"12345\n" + "54321\n" + "11111\n"+  "22222\n" + "33333\n" + "1111",
			"1234\n" + "54321\n" + "11111\n"+  "22222\n" + "33333\n" + "11111",
			"12321\n" + "21212\n" + "\n" + "21212\n" + "21212",
		    "123456\n" + "121212\n" + "112121\n" + "121212\n" + "121212", //6 is not legal
			"\n",
			"0000\n" + "0000\n" + "0000\n" + "0000",
			"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" + "###min_stones:-4|target_time:400", // no negative values
			"12345\n" + "21212\n" + "12121\n" + "21212\n" + "21212\n" + "###min_stones:4.5|target_time:400", // only integers
			};

	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterMinimal();
	}

	@Test
	public final void testLoadLevelFromString() {
		for (String level : correctLevels) {
			adapter.loadLevelFromString(level);
			assertTrue("A correct level is wrongly detected as incorrect. Levelstring: \n" + level, adapter.isCorrectLevel());
		}
		for (String level : correctLevelsButOutputCouldDiffer) {
			adapter.loadLevelFromString(level);
			assertTrue("A correct level is wrongly detected as incorrect. Levelstring: " + level, adapter.isCorrectLevel());
		}
	}

	@Test
	public final void testIsCorrectLevel() {
		for (String level : incorrectLevels) {
			adapter.loadLevelFromString(level);
			assertFalse("A incorrect level is wrongly detected as correct. Levelstring: " + level, adapter.isCorrectLevel());
		}
	}
	
	@Test
	public final void testGetStringRepresentationOfLevel() {
		
		for (String level : correctLevels) {
			adapter.loadLevelFromString(level);
			String retLevel = adapter.getLevelAsStringWithoutExtraInfo();
			
			assertNotNull("After loading a correct level getStringRepresentationOfLevel should never return null.", retLevel);
			
			if (retLevel.indexOf("#") != -1) {
				fail("The method 'SameGameTestAdapterMinimal.getLevelAsStringWithoutExtraInfo' should always(!) return a string without any information line." +
						"If you implement the task aboud addtional informations, please see 'SameGameTestAdapterExtended1.getLevelAsStringWithExtraInfo'.");
			}
			
			assertEquals("Without any action between load and get getStringRepresentationOfLevel returns an invalid string.\n" +
					" Remember: for the output ONLY \\n is allowed as line break!", level, retLevel);
		}
	}
	
	@Test
	public void testNullLevel() throws Exception {
		assertNull(adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(incorrectLevels[0]);
		assertNull(adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(correctLevels[0]);
		assertNotNull(adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(incorrectLevels[0]);
		assertNotNull(adapter.getLevelAsStringWithoutExtraInfo());
	}

	@Test
	public final void testZeroInside() {
		adapter.loadLevelFromString(level_zeroInside);
		assertTrue("A level with zeros inside is correct.", adapter.isCorrectLevel());
		assertFalse("While loading a level stones should drop down, if there is any space!", level_zeroInside.equals(adapter.getLevelAsStringWithoutExtraInfo()));
		assertEquals("While loading a level stones should drop down, if there is any space!", level_droppedZeroInside , adapter.getLevelAsStringWithoutExtraInfo());
	}



}

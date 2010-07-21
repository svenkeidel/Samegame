package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended3;

public class KeyboardTest {

	SameGameTestAdapterExtended3 adapter;

	
	private static String level_1_0 = 
		"12345\n" +
		"54322\n" +
		"12245"; 
	private static String level_1_1 = 
		"12045\n" +
		"54022\n" +
		"12245"; 
	private static String level_1_2 = 
		"12000\n" +
		"54045\n" +
		"12245"; 
	private static String level_1_3 = 
		"10000\n" +
		"52450\n" +
		"14450"; 
	
	
	private static String level_2_0 = 
		"22544\n" +
		"22344\n" +
		"22344\n" +
		"22444"; 
	private static String level_2_1 = 
		"22044\n" +
		"22044\n" +
		"22544\n" +
		"22444"; 
	
	
	
	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended3();
	}
	
	@Test
	public void testInitialFocus() {
		adapter.loadLevelFromString("1111\n" + "2222");
		assertEquals("1111\n" + "2222", adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.handleKeyPressedSpace();
		assertEquals("Your initial focus seems to be wrong.", "0000\n" + "2222", adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString("1222\n" + "1222\n" + "1222\n" + "3222");
		adapter.handleKeyPressedSpace();
		assertEquals("Your initial focus seems to be wrong.", "0222\n" + "0222\n" + "0222\n" + "3222", adapter.getLevelAsStringWithoutExtraInfo());
	}

	@Test
	public void testRightDownMove() {
		adapter.loadLevelFromString(level_2_0);
		assertEquals(level_2_0, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedDown();
		adapter.handleKeyPressedSpace();
		assertEquals("If your initial focus is correct you maybe confused the axes.", level_2_1, adapter.getLevelAsStringWithoutExtraInfo());
	}
	
	@Test
	public void testFocusMovement() {
		adapter.loadLevelFromString(level_1_0);
		assertEquals(level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.handleKeyPressedLeft(); //should have no effect, the focus should be at a border
		adapter.handleKeyPressedUp();  //should have no effect, the focus should be at a border
		adapter.handleKeyPressedSpace(); //should take no effect, the focus is at the initial position (upper left) where no stones can be removed (to less of the same color).
		assertEquals(
				"The focus movements should have no effect - and the selection (space pressing) should take no effect because there were not enough stones of the same color.",
				level_1_0, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.handleKeyPressedDown();
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedSpace();
		assertEquals(level_1_1, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.handleKeyPressedLeft();
		adapter.handleKeyPressedLeft();
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedRight();
		adapter.handleKeyPressedRight(); //should have no effect, the focus should be at a border
		adapter.handleKeyPressedSpace();
		assertEquals(level_1_2, adapter.getLevelAsStringWithoutExtraInfo());
		adapter.handleKeyPressedDown();
		adapter.handleKeyPressedDown(); //should have no effect, the focus should be at a border
		adapter.handleKeyPressedLeft();
		adapter.handleKeyPressedLeft();
		adapter.handleKeyPressedSpace();
		assertEquals(level_1_3, adapter.getLevelAsStringWithoutExtraInfo());
	}

}

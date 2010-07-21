package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterMinimal;


/**
 * Tests whether the stones were removed correctly.
 */
public class RemoveStoneTest {
	
	//TODO mehr Varianten testen, Kombinationen von entleerter Zeile und Spalte
	
	
	//some levels:
	String level0_1 = 
		"124452\n" + 
		"231512\n" + 
		"345123\n" + 
		"352234";
	String level0_wrong1_xy = 
		"120052\n" + 
		"231512\n" + 
		"345123\n" + 
		"352234";
	String level0_wrong2_drop = 
		"124452\n" + 
		"231512\n" + 
		"045123\n" + 
		"052234";
	String level0_wrong3_base = 
		"124450\n" + 
		"231510\n" + 
		"345123\n" + 
		"352234";
	String level0_wrong4_base_xy_drop = 
		"124452\n" + 
		"231512\n" + 
		"345123\n" + 
		"350034";
	String level0_wrong5_base_xy = 
		"120052\n" + 
		"234412\n" + 
		"341523\n" + 
		"355134";
	String level0_2 = 
		"024452\n" + 
		"031512\n" + 
		"145123\n" + 
		"252234";


	String level1_1 = "11111\n" + "22222\n" + "12121\n" + "21212\n" + "21212";
	String level1_2 = "00000\n" + "22222\n" + "12121\n" + "21212\n" + "21212";
	String level1_wrong = "22222\n" + "12121\n" + "21212\n" + "21212";
	String level1_wrong2 = "\n" + "22222\n" + "12121\n" + "21212\n" + "21212";

	String level2_1 = "12111\n" + "12222\n" + "12121\n" + "12212\n" + "22212";
	String level2_2 = "02111\n" + "02222\n" + "02121\n" + "02212\n" + "22212";

	String level3_1 = "12355134\n" + "32155432\n" + "32111432\n" + "43232111";
	String level3_2 = "12300134\n" + "32100432\n" + "32111432\n" + "43232111";

	String level4_1 = "12312134\n" + "32155432\n" + "32155432\n" + "43232111";
	String level4_2 = "12300134\n" + "32100432\n" + "32112432\n" + "43232111";
	String level4_2_wrong = "12312134\n" + "32100432\n" + "32100432\n" + "43232111";

	String level5_1 = 
		"13111\n" + 
		"14222\n" + 
		"15121\n" + 
		"15232\n" + 
		"15242";
	String level5_wrong = 
		"03111\n" + 
		"04222\n" + 
		"05121\n" + 
		"05232\n" + 
		"05242";	
	String level5_wrong2 =
		"3111\n" + 
		"4222\n" + 
		"5121\n" + 
		"5232\n" + 
		"5242";
	String level5_2 = 
		"31110\n" + 
		"42220\n" + 
		"51210\n" + 
		"52320\n" + 
		"52420";

	//Edge
	String level_edge1 = // 2|2
		"1212121212\n" + 
		"1213121212\n" + 
		"1233121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_edge2 = // 3|2
		"1212121212\n" + 
		"1213121212\n" + 
		"1213321212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_edge3 = // 3|2
		"1212121212\n" + 
		"1212121212\n" + 
		"1213321212\n" +
		"1213121212\n" +  
		"1212121212";

	String level_edge4 = // 2|2
		"1212121212\n" + 
		"1212121212\n" + 
		"1233121212\n" +
		"1213121212\n" +  
		"1212121212";

	String level_edge5 = // 2|2
		"1212121212\n" + 
		"1213121212\n" + 
		"1233121212\n" +
		"1212121212\n" +  
		"1212121212";
	String level_edge6 = // 8|4
		"1212121212\n" + 
		"1212121212\n" + 
		"1212121212\n" +
		"1212121213\n" +  
		"1212121233";
	String level_edge7 = // 8|0
		"1212121232\n" + 
		"1212121233\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";
	String level_edge8 = // 8|0
		"1212121233\n" + 
		"1212121213\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	//Edge-finished
	String level_fin_edge1 =
		"1200121212\n" + 
		"1210121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";


	String level_fin_edge2 =
		"1210021212\n" + 
		"1210121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";


	String level_fin_edge3 =
		"1210021212\n" + 
		"1210121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";


	String level_fin_edge4 =
		"1200121212\n" + 
		"1210121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";



	String level_fin_edge5 =
		"1200121212\n" + 
		"1210121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";
	String level_fin_edge6 = // 8|4
		"1212121200\n" + 
		"1212121210\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";
	String level_fin_edge7 = // 8|0
		"1212121200\n" + 
		"1212121202\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";
	String level_fin_edge8 = // 8|0
		"1212121200\n" + 
		"1212121210\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";
	
	/// Block
	String level_block = 
		"1212121212\n" + 
		"1233121212\n" + 
		"1233121212\n" +
		"1212121212\n" +  
		"1212121212";


	String level_block_border1 = 
		"1233121212\n" + 
		"1233121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_block_border2 = 
		"1212121212\n" + 
		"1212121212\n" + 
		"1212121212\n" +
		"1213321212\n" +  
		"1213321212";

	String level_block_edge1 =
		"1212121233\n" + 
		"1212121233\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_block_edge2 = 
		"1212121212\n" + 
		"1212121212\n" + 
		"1212121212\n" +
		"1212121233\n" +  
		"1212121233";

	String level_block_edge3 =
		"1212121212\n" + 
		"1212121212\n" + 
		"1212121212\n" +
		"3312121212\n" +  
		"3312121212";

	/// Block-Finished
	String level_block_fin =
		"1200121212\n" + 
		"1200121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";


	String level_block_fin_border1 =
		"1200121212\n" + 
		"1200121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_block_fin_border2 = 
		"1210021212\n" + 
		"1210021212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_block_fin_edge1 =
		"1212121200\n" + 
		"1212121200\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_block_fin_edge2 =
		"1212121200\n" + 
		"1212121200\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_block_fin_edge3 =
		"0012121212\n" + 
		"0012121212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";



	// T-form
	String level_tform1 = // 4|1
		"1212121212\n" + 
		"1212333212\n" + 
		"1212131212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_tform2 = // 4|2
		"1212121212\n" + 
		"1212131212\n" + 
		"1212333212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_tform3 = //5|1
		"1212121212\n" +  
		"1212131212\n" + 
		"1212133212\n" +
		"1212131212\n" +  
		"1212121212";


	// T-form-finished
	String level_tform1_fin = 
		"1212000212\n" + 
		"1212101212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_tform2_fin =
		"1212000212\n" + 
		"1212101212\n" + 
		"1212121212\n" +
		"1212121212\n" +  
		"1212121212";

	String level_tform3_fin =
		"1212100212\n" +  
		"1212101212\n" + 
		"1212101212\n" +
		"1212121212\n" +  
		"1212121212";


	//row and column
	String level_rowcol_1 =
		"1212121212\n" + 
		"1212121212\n" + 
		"1212121212\n" +
		"2222122222\n" +  
		"1111111111\n" + 
		"2222122222";
	
	String level_rowcol_fin_1 =
		"0000000000\n" +
		"1212212120\n" + 
		"1212212120\n" + 
		"1212212120\n" +
		"2222222220\n" +  
		"2222222220";

	String level_rowcol_2 =
		"1111111111\n" + 
		"2222122222\n" + 
		"1212121212\n" + 
		"1212121212\n" +
		"1212121212\n" +
		"1212121212";

	String level_rowcol_fin_2 =
		"0000000000\n" +
		"2222222220\n" +
		"1212212120\n" + 
		"1212212120\n" +
		"1212212120\n" + 
		"1212212120";

	
	String level_rowcol_3 =
		"1111111111\n" + 
		"1222222222\n" + 
		"1222222222\n" + 
		"1222222222\n" +
		"1222222222\n" +  
		"1222222222\n" + 
		"1111111111";

	String level_rowcol_fin_3 =
		"0000000000\n" + 
		"0000000000\n" + 
		"2222222220\n" + 
		"2222222220\n" + 
		"2222222220\n" +
		"2222222220\n" +  
		"2222222220";
	
	String level_form_o_1 = 
		"2222222222\n" + 
		"2222222222\n" + 
		"2221112222\n" + 
		"2221212222\n" +
		"2221112222\n" +  
		"2222222222\n" + 
		"2222222222";
	String level_form_o_1_fin =
		"2220002222\n" + 
		"2220002222\n" + 
		"2220202222\n" + 
		"2222222222\n" +
		"2222222222\n" +  
		"2222222222\n" + 
		"2222222222";
	String level_form_o_2 = 
		"1111111111\n" + 
		"1222222221\n" + 
		"1222222221\n" + 
		"1222222221\n" +
		"1222222221\n" +  
		"1222222221\n" + 
		"1111111111";
	String level_form_o_2_fin =
		"0000000000\n" + 
		"0000000000\n" + 
		"2222222200\n" + 
		"2222222200\n" + 
		"2222222200\n" +
		"2222222200\n" +  
		"2222222200";

	
	private SameGameTestAdapterMinimal adapter;

	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterMinimal();
	}

	@Test 
	public void testCellSelectedHitsCorrectCell() {
		adapter.loadLevelFromString(level0_1);
		
		assertTrue("The loaded level is detected wrongly as incorrect.", adapter.isCorrectLevel());
		assertTrue("A selection to 0|2 is legal.", adapter.cellSelected(0, 2));
		
		String retLevel = adapter.getLevelAsStringWithoutExtraInfo();
		assertNotNull("After loading a correct level getStringRepresentationOfLevel should never return null.", retLevel);
		if (level0_wrong1_xy.equals(retLevel)) {
			fail("You mistake the parts of the coordinate in the method. " +
					"The first parameter of the method cellSelected is the x-part, the second the y-part.");
		} 
		if (level0_wrong2_drop.equals(retLevel)) {
			fail("Stones above empty fields have to drop down!");
		}
		if (level0_wrong3_base.equals(retLevel)) {
			fail("You mistake the base. " +
					"The coordinate 0|0 is the upper left cell! (Your 0|0 seems to be in the lowest row.)");
		}
		if (level0_wrong4_base_xy_drop.equals(retLevel)) {
			fail("You mistake the base and the order of the x-y. " +
					"And Stones above empty fields have to drop down!" +
					"The first parameter of the method cellSelected is the x-part, the second the y-part." +
					"The coordinate 0|0 is the upper left cell! (Your 0|0 seems to be in the lowest row.)");
		}
		if (level0_wrong5_base_xy.equals(retLevel)) {
			fail("You mistake the base and the order of the x-y. " +
					"The first parameter of the method cellSelected is the x-part, the second the y-part." +
					"The coordinate 0|0 is the upper left cell! (Your 0|0 seems to be in the lowest row.)");
		}
		if (level0_1.equals(retLevel)) {
			fail("A selection to 0|2 sould clear some fields.");
		}
		assertEquals("The selection of 0|2 should clear something!", level0_2, retLevel);
		
	}
	
	@Test 
	public void testIllegalSelections() {
		adapter.loadLevelFromString(level0_1);
		assertTrue("The loaded level is detected wrongly as incorrect.", adapter.isCorrectLevel());
		assertFalse("A selection to 1|1 should not remove stones.", adapter.cellSelected(1, 1));
		assertFalse("A selection to 6|1 is not on the board.", adapter.cellSelected(6, 1));
		assertFalse("A selection to 1|4 is not on the board.", adapter.cellSelected(1, 4));
		assertFalse("A selection to 6|4 is not on the board.", adapter.cellSelected(6, 4));
		assertFalse("A selection to 6|4 is not on the board.", adapter.cellSelected(6, 4));
		assertFalse("A selection to 2|1 should not remove stones.", adapter.cellSelected(2, 1));
		assertFalse("A selection to 5|2 should not remove stones.", adapter.cellSelected(5, 2));
		String retLevel = adapter.getLevelAsStringWithoutExtraInfo();
		assertEquals("Much illegal selections should change nothing." ,level0_1, retLevel);
	}
	
	@Test
	public void testCellSelectedClearRow() {
		adapter.loadLevelFromString(level1_1);
		adapter.cellSelected(0, 0);

		String output = adapter.getLevelAsStringWithoutExtraInfo();
		assertNotNull("getLevelAsStringWithoutExtraInfo should (after loading an (valid) level never return null!",
				output);
		if (level1_wrong.equals(output) || level1_wrong2.equals(output)) {
			fail("You have to fill cleared fields (also if the whole row is cleared) with zeros in the output!");
		}
		assertEquals("After selecting the upper left stone the first row should be cleared.", level1_2, adapter
				.getLevelAsStringWithoutExtraInfo());
	}

	@Test
	public void testCellSelectedClearPartsOfColumn() {
		adapter.loadLevelFromString(level2_1);
		adapter.cellSelected(0, 0);
		assertEquals(
				"After selecting the upper left stone the first column some stones of this coloum (but not the column at all) should be cleared.",
				level2_2, adapter.getLevelAsStringWithoutExtraInfo());
	}

	@Test
	public void testCellSelectedClearUpperSquare() {
		adapter.loadLevelFromString(level3_1);
		adapter.cellSelected(3, 0);
		assertEquals("After selecting a stone of an square of eqaul stones this square should be cleared.", level3_2,
				adapter.getLevelAsStringWithoutExtraInfo());
	}

	@Test
	public void testCellSelectedClearInnerSquare() {
		adapter.loadLevelFromString(level4_1);
		adapter.cellSelected(3, 2);

		String output = adapter.getLevelAsStringWithoutExtraInfo();
		assertNotNull("getLevelAsStringWithoutExtraInfo should (after loading an (valid) level never return null!",
				output);

		if (level4_2_wrong.equals(output)) {
			fail("Stones over removed stones have to drop down!");
		}
		assertEquals("After selecting a stone of an inner square of eqaul stones:"
				+ " this square should be cleared and the stones above should drop down.", level4_2, adapter
				.getLevelAsStringWithoutExtraInfo());
	}

	@Test
	public void testCellSelectedClearWholeColumn() {
		adapter.loadLevelFromString(level5_1);
		adapter.cellSelected(0, 0);

		String output = adapter.getLevelAsStringWithoutExtraInfo();
		assertNotNull("getLevelAsStringWithoutExtraInfo should (after loading an (valid) level never return null!",
				output);

		if (level5_wrong.equals(output)) {
			fail("Non empty columns should exchange their place with empty columns on their right side.");
		}
		if (level5_wrong2.equals(output)) {
			fail("Empty columns should be shown at the right end of the level.");
		}
		assertEquals("After selecting a stone in the left column the first column should be cleared at all. "
				+ "Completly cleard columns have to exchange their place with not empty ones on the right.", level5_2,
				output);

	}
	
	
	@Test
	public void testRemoveColAndRow() {
		adapter.loadLevelFromString(level_rowcol_1);
		assertTrue("The loaded level is correct.", adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(4, 3));
		assertEquals("Removing a column and a row at the same time is wrong. ", level_rowcol_fin_1, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.loadLevelFromString(level_rowcol_2);
		assertTrue("The loaded level is correct.", adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(4, 3));
		assertEquals("Removing a column and a row at the same time is wrong. ", level_rowcol_fin_2, adapter.getLevelAsStringWithoutExtraInfo());

		
		adapter.loadLevelFromString(level_rowcol_3);
		assertTrue("The loaded level is correct.", adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(3, 6));
		assertEquals("Removing a column and a row at the same time is wrong. ", level_rowcol_fin_3, adapter.getLevelAsStringWithoutExtraInfo());
	}
	
	@Test
	public void testSpecialForms() {
		adapter.loadLevelFromString(level_form_o_1);
		assertTrue("The loaded level is correct.", adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(4, 2));
		assertEquals("Removing a circle is wrong. ", level_form_o_1_fin, adapter.getLevelAsStringWithoutExtraInfo());

		
		adapter.loadLevelFromString(level_form_o_2);
		assertTrue("The loaded level is correct.", adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(4, 0));
		assertEquals("Removing a big circle is wrong. ", level_form_o_2_fin, adapter.getLevelAsStringWithoutExtraInfo());
	}

		
		@Test
	public void testRemoveBlock() {
		adapter.loadLevelFromString(level_block);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(2, 2));
		assertEquals("After removing a square the level is wrong.", level_block_fin, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_block_border1);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(2, 0));
		assertEquals("After removing a square the level is wrong.", level_block_fin_border1, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.loadLevelFromString(level_block_border2);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(3, 4));
		assertEquals("After removing a square the level is wrong.", level_block_fin_border2, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_block_edge1);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(8, 1));
		assertEquals("After removing a square the level is wrong.", level_block_fin_edge1, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_block_edge2);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(8, 3));
		assertEquals("After removing a square the level is wrong.", level_block_fin_edge2, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_block_edge3);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(0, 4));
		assertEquals("After removing a square the level is wrong.", level_block_fin_edge3, adapter.getLevelAsStringWithoutExtraInfo());
	}
	
	@Test
	public void testRemoveEdge() {
		adapter.loadLevelFromString(level_edge1);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(2, 2));
		assertEquals("After removing stones the level is wrong.", level_fin_edge1, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_edge2);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(3, 2));
		assertEquals("After removing stones the level is wrong.", level_fin_edge2, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.loadLevelFromString(level_edge3);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(3, 2));
		assertEquals("After removing stones the level is wrong.", level_fin_edge3, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_edge4);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(2, 2));
		assertEquals("After removing stones the level is wrong.", level_fin_edge4, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_edge5);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(2, 2));
		assertEquals("After removing stones the level is wrong.", level_fin_edge5, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_edge6);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(8, 4));
		assertEquals("After removing stones the level is wrong.", level_fin_edge6, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_edge7);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(8, 0));
		assertEquals("After removing stones the level is wrong.", level_fin_edge7, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.loadLevelFromString(level_edge8);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(8, 0));
		assertEquals("After removing stones the level is wrong.", level_fin_edge8, adapter.getLevelAsStringWithoutExtraInfo());
	}
	
	@Test
	public void testRemoveTForm() {
		adapter.loadLevelFromString(level_tform1);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(4, 1));
		assertEquals("After removing stones the level is wrong.", level_tform1_fin, adapter.getLevelAsStringWithoutExtraInfo());
		
		adapter.loadLevelFromString(level_tform2);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(4, 2));
		assertEquals("After removing stones the level is wrong.", level_tform2_fin, adapter.getLevelAsStringWithoutExtraInfo());

		adapter.loadLevelFromString(level_tform3);
		assertTrue(adapter.isCorrectLevel());
		assertTrue(adapter.cellSelected(5, 1));
		assertEquals("After removing stones the level is wrong.", level_tform3_fin, adapter.getLevelAsStringWithoutExtraInfo());
	}

}

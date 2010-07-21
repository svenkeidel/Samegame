package de.tu_darmstadt.gdi1.samegame.tests.students.testcases;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import de.tu_darmstadt.gdi1.samegame.tests.adapter.SameGameTestAdapterExtended3;

/**
 * Tests whether the generator work as specified. 
 */
public class TestGenerator {
	
	SameGameTestAdapterExtended3 adapter;

	
	@Before
	public void setUp() throws Exception {
		adapter = new SameGameTestAdapterExtended3();
	}
	

	@Test
	public void testGenerateLevel() {
		List<String> levels = new ArrayList<String>();
		Random r = new Random(Long.parseLong("101010", 2));
		for (int i = 0; i < 100; i++) {
			// Generate set of parameters:
			int width = 6 + r.nextInt(25); // 6 <= width <= 30
			int height = 5 + r.nextInt(16); // 5 <= width <= 20
			int numberOfColors = 2 + r.nextInt(4); // 2 <= width <= 5
			int minStones = 2 + r.nextInt(4); // 2 <= width <= 5

			// use each set ten times, there may not be any duplicate
			for (int j = 0; j < 10; j++) {
				String level = "";
				try {
					level = adapter.generateLevel(width, height, numberOfColors, minStones);
				} catch (Exception e) {
					fail("Valid parameters rejected.");
				}
				if (levels.contains(level)) {
					fail("Same level generated twice");
				} else {
					levels.add(level);
				}

				if (!playable(level, width, height)) {
					fail("No stones can be removed in this level.");
				}

				if (!rightSize(level, width, height)) {
					fail("Gameboard has a wrong size.");
				}

				if (!numberOfColors(level, numberOfColors, minStones)) {
					fail("Level does not contain at least minimum number of stones of every color.");
				}
			}
		}
	}
	
	@Test
	public void testInvalidParameters() throws Exception {
		int[][] parameters = {{5, 5, 2, 2}, {31, 20, 5, 5},
				{6, 4, 2, 2}, {30, 21, 5, 5},
				{6, 5, 1, 2}, {30, 20, 6, 5},
				{6, 5, 2, 1}, {30, 20, 5, 6},
				{-10, -10, -10, -10}};
		int count = 0;
		for (int i = 0; i < parameters.length; i++) {
			try {
				adapter.generateLevel(parameters[i][0], parameters[i][1], parameters[i][2], parameters[i][3]);
			} catch (Exception e) {
				count++;
			}
		}
		if (count != parameters.length) {
			fail("Invalid parameters for level generating are accepted instead of throwing an exception.");
		}
	}

	private boolean numberOfColors(String level, int numberOfColors, int minStones) {
		for (int i = 1; i <= numberOfColors; i++) {
			String regex = "[^0]*";
			for (int j = 0; j < minStones; j++) {
				regex += i + "[^0]*";
			}
			if (!level.matches(regex)) {
				return false;
			}
		}
		return true;
	}

	private boolean playable(String level, int width, int height) {
		adapter.loadLevelFromString(level);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (adapter.cellSelected(x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean rightSize(String level, int width, int height) {
		String[] lines = level.split("\n");
		if (lines.length != height) {
			return false;
		}
		for (String string : lines) {
			if (string.length() != width) {
				return false;
			}
		}
		return true;
	}

}

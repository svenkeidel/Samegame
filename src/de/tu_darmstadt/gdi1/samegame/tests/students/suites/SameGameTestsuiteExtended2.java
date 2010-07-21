package de.tu_darmstadt.gdi1.samegame.tests.students.suites;

import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.HighscoreInLevelString;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.HighscoreTest;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.UndoRedoTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SameGameTestsuiteExtended2 {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for SameGame - Extended 2");
		//$JUnit-BEGIN$

		suite.addTest(new JUnit4TestAdapter(HighscoreTest.class));
		suite.addTest(new JUnit4TestAdapter(HighscoreInLevelString.class));
		suite.addTest(new JUnit4TestAdapter(UndoRedoTest.class));
		
		//$JUnit-END$
		return suite;
	}

}

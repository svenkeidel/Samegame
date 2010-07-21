package de.tu_darmstadt.gdi1.samegame.tests.students.suites;

import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.FinishDetectionTest;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.LoadLevelFromStringTest;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.RemoveStoneTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;

public class SameGameTestsuiteMinimal {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for SameGame - Minimal");
		//$JUnit-BEGIN$
		suite.addTest(new JUnit4TestAdapter(LoadLevelFromStringTest.class));
		suite.addTest(new JUnit4TestAdapter(RemoveStoneTest.class));
		suite.addTest(new JUnit4TestAdapter(FinishDetectionTest.class));
		//$JUnit-END$
		return suite;
	}

}

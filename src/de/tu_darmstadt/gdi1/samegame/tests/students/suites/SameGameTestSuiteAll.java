package de.tu_darmstadt.gdi1.samegame.tests.students.suites;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SameGameTestSuiteAll {
	
	public static Test suite() {
		TestSuite suite = new TestSuite("All student tests for SameGame");
		//$JUnit-BEGIN$
		
		//Tutor suites include the student suites
		
		suite.addTest(de.tu_darmstadt.gdi1.samegame.tests.students.suites.SameGameTestsuiteMinimal.suite());
		suite.addTest(de.tu_darmstadt.gdi1.samegame.tests.students.suites.SameGameTestsuiteExtended1.suite());
		suite.addTest(de.tu_darmstadt.gdi1.samegame.tests.students.suites.SameGameTestsuiteExtended2.suite());
		suite.addTest(de.tu_darmstadt.gdi1.samegame.tests.students.suites.SameGameTestsuiteExtended3.suite());
		//$JUnit-END$
		return suite;
	}

}

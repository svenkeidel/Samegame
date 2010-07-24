package de.tu_darmstadt.gdi1.samegame.tests.students.suites;

import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.KeyboardTest;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.TestGenerator;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class SameGameTestsuiteExtended3 {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for SameGame - Extended 3");
		//$JUnit-BEGIN$
		
		suite.addTest(new JUnit4TestAdapter(KeyboardTest.class));
		suite.addTest(new JUnit4TestAdapter(TestGenerator.class));

		//$JUnit-END$
		return suite;
	}

	public static void main(String[] args){
		TestRunner.runAndWait(suite());
	}
}

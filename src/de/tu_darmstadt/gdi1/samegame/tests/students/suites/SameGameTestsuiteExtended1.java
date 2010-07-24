package de.tu_darmstadt.gdi1.samegame.tests.students.suites;

import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.CalcPointsTest;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.ExtraInfosTest;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.FinishDetectionExtendedTest;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.NewGameTest;
import de.tu_darmstadt.gdi1.samegame.tests.students.testcases.RemoveStoneExtendedTest;
import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class SameGameTestsuiteExtended1 {

	public static Test suite() {
		TestSuite suite = new TestSuite("Student tests for SameGame - Extended 1");
		//$JUnit-BEGIN$
		
		suite.addTest(new JUnit4TestAdapter(ExtraInfosTest.class));
		suite.addTest(new JUnit4TestAdapter(CalcPointsTest.class));
		suite.addTest(new JUnit4TestAdapter(NewGameTest.class));
		suite.addTest(new JUnit4TestAdapter(FinishDetectionExtendedTest.class));
		suite.addTest(new JUnit4TestAdapter(RemoveStoneExtendedTest.class));
		
		//$JUnit-END$
		return suite;
	}

	public static void main(String[] args){
		TestRunner.runAndWait(suite());
	}

}

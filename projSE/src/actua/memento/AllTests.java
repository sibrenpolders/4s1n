package actua.memento;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for actua.memento");
		//$JUnit-BEGIN$
		suite.addTestSuite(StatusBijhouderTest.class);
		//$JUnit-END$
		return suite;
	}

}
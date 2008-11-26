package actua;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for actua");
		//$JUnit-BEGIN$
		suite.addTestSuite(JunitTestSpel.class);
		//$JUnit-END$
		return suite;
	}

}

package actua;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for actua.spelDelen");
		//$JUnit-BEGIN$
		suite.addTestSuite(JunitCameraTest.class);
		//$JUnit-END$
		return suite;
	}

}

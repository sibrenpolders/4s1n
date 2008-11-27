package actua.ai;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for actua.ai");
		//$JUnit-BEGIN$
		suite.addTestSuite(HardTest.class);
		suite.addTestSuite(EasyTest.class);
		//$JUnit-END$
		return suite;
	}

}

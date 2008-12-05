package testcases;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for testcases");
		//$JUnit-BEGIN$
		suite.addTestSuite(JunitAISpelerTest.class);
		suite.addTestSuite(JunitCameraTest.class);
		suite.addTestSuite(JunitTegelTest.class);
		suite.addTestSuite(JunitTafelVerwerkerTest.class);
		suite.addTestSuite(JunitTestSpel.class);
		suite.addTestSuite(HardTest.class);
		suite.addTestSuite(EasyTest.class);
		suite.addTestSuite(JunitSpelerVerwerkerTest.class);
		suite.addTestSuite(JunitTafelTest.class);
		suite.addTestSuite(JunitBestandTest.class);
		suite.addTestSuite(JunitOptiesTest.class);
		//$JUnit-END$
		return suite;
	}

}

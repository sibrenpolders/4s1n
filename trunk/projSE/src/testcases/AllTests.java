package testcases;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for testcases");
		//$JUnit-BEGIN$
		suite.addTestSuite(JunitAISpelerTest.class);
		suite.addTestSuite(JunitAITest.class);
		suite.addTestSuite(JunitBestandTest.class);
		suite.addTestSuite(JunitCameraTest.class);
		suite.addTestSuite(JunitEasyTest.class);
		suite.addTestSuite(JunitHardTest.class);
		suite.addTestSuite(JunitHelpTest.class);
		suite.addTestSuite(JunitOptiesTest.class);
		suite.addTestSuite(JunitTegelTest.class);
		suite.addTestSuite(JunitSpelerVerwerkerTest.class);
		suite.addTestSuite(JunitStatusBijhouderTest.class);
		suite.addTestSuite(JunitTafelTest.class);
		suite.addTestSuite(JunitTafelVerwerkerTest.class);
		suite.addTestSuite(JunitTegelTest.class);		
		//$JUnit-END$
		return suite;
	}
}

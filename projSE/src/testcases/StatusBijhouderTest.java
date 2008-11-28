package testcases;

import actua.Memento;
import actua.StatusBijhouder;
import junit.framework.TestCase;

class TestObject {
	int testValueInt;
	boolean testValueBool;
	Object testValueObject;
	
	public TestObject(int testValueInt, boolean testValueBool,  Object testValueObject) {
		this.testValueInt = testValueInt;
		this.testValueBool = testValueBool;
		this.testValueObject = testValueObject;
	}
	
	public boolean equals(Object obj) {
		TestObject andereTestObject = (TestObject)obj;
		// 2 TestObjecten zijn gelijk a.s.a:
		// * Beide testValueInt's gelijk zijn
		// * Beide testValueBool's gelijk zijn
		// * Beide testValueObject null zijn OF beide testValueObject gelijk zijn
		return testValueInt == andereTestObject.testValueInt &&
			testValueBool == andereTestObject.testValueBool &&
			((testValueObject == null && andereTestObject.testValueObject == null) ||
					testValueObject.equals(andereTestObject.testValueObject));
	}
}

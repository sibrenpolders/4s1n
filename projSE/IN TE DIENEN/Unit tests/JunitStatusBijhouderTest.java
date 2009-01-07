package UnitTests;

import Core.Memento;
import Core.StatusBijhouder;
import junit.framework.TestCase;

public class JunitStatusBijhouderTest extends TestCase {
	StatusBijhouder statusBijhouder;

	protected void setUp() throws Exception {
		super.setUp();
		statusBijhouder = new StatusBijhouder();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPop_Push_undo() {
		// testObjecten aanmaken
		TestObject t1 = new TestObject(0, true, null);
		TestObject t2 = new TestObject(-50, false, t1);
		TestObject t3 = new TestObject(50, true && false, t2);

		Memento m1 = new Memento(t3, 'a', true);
		Memento m2 = new Memento(t2, 'b', true);
		Memento m3 = new Memento(t1, 'a', false);
		Memento m4 = new Memento(null, 'b', true);

		assertEquals("Geen elementen faalt.", null, statusBijhouder.pop_undo());

		// testObjecten pushen
		statusBijhouder.push_undo(m1);
		assertEquals(1, statusBijhouder.getUndoSize());
		statusBijhouder.push_undo(m2);
		assertEquals(2, statusBijhouder.getUndoSize());
		statusBijhouder.push_undo(m3);
		assertEquals(3, statusBijhouder.getUndoSize());
		statusBijhouder.push_undo(m4);
		assertEquals(4, statusBijhouder.getUndoSize());

		// testObjecten poppen, faalt wanneer het gepopte element niet het
		// gepushte element was
		assertEquals("Push/Pop faalt.", true, m4.equals(statusBijhouder
				.pop_undo()));
		assertEquals(3, statusBijhouder.getUndoSize());
		assertEquals(1, statusBijhouder.getRedoSize());
		
		assertEquals("Push/Pop faalt.", true, m3.equals(statusBijhouder
				.pop_undo()));
		assertEquals(2, statusBijhouder.getUndoSize());
		assertEquals(2, statusBijhouder.getRedoSize());
		
		assertEquals("Push/Pop faalt.", true, m2.equals(statusBijhouder
				.pop_undo()));
		assertEquals(1, statusBijhouder.getUndoSize());
		assertEquals(3, statusBijhouder.getRedoSize());
		
		assertEquals("Push/Pop faalt.", false, m4.equals(statusBijhouder
				.pop_undo()));
		assertEquals(0, statusBijhouder.getUndoSize());
		assertEquals(4, statusBijhouder.getRedoSize());
		
		assertEquals("Push/Pop faalt.", true, m1.equals(statusBijhouder
				.pop_redo()));
		assertEquals(3, statusBijhouder.getRedoSize());
		
		assertEquals("Push/Pop faalt.", true, m2.equals(statusBijhouder
				.pop_redo()));
		assertEquals(2, statusBijhouder.getRedoSize());
		
		assertEquals("Push/Pop faalt.", true, m3.equals(statusBijhouder
				.pop_redo()));
		assertEquals(1, statusBijhouder.getRedoSize());
		
		assertEquals("Push/Pop faalt.", true, m4.equals(statusBijhouder
				.pop_redo()));

		assertEquals("Geen elementen faalt.", 0, statusBijhouder.getRedoSize());
	}

	public void testPop_Push_redo() {
		// testObjecten aanmaken
		TestObject t1 = new TestObject(0, true, null);
		TestObject t2 = new TestObject(-50, false, t1);
		TestObject t3 = new TestObject(50, true && false, t2);

		Memento m1 = new Memento(t3, 'a', true);
		Memento m2 = new Memento(t2, 'b', true);
		Memento m3 = new Memento(t1, 'a', false);
		Memento m4 = new Memento(null, 'b', true);

		assertNull("Geen elementen faalt.", statusBijhouder.pop_redo());

		// testObjecten pushen
		statusBijhouder.push_redo(m1);
		assertEquals(1, statusBijhouder.getRedoSize());
		statusBijhouder.push_redo(m2);
		assertEquals(2, statusBijhouder.getRedoSize());
		statusBijhouder.push_redo(m3);
		assertEquals(3, statusBijhouder.getRedoSize());
		statusBijhouder.push_redo(m4);
		assertEquals(4, statusBijhouder.getRedoSize());

		// testObjecten poppen, faalt wanneer het gepopte element niet het
		// gepushte element was
		assertEquals("Push/Pop faalt.", true, m4.equals(statusBijhouder
				.pop_redo()));
		assertEquals(3, statusBijhouder.getRedoSize());
		assertEquals(1, statusBijhouder.getUndoSize());
		
		assertEquals("Push/Pop faalt.", true, m3.equals(statusBijhouder
				.pop_redo()));
		assertEquals(2, statusBijhouder.getRedoSize());
		assertEquals(2, statusBijhouder.getUndoSize());
		
		assertEquals("Push/Pop faalt.", true, m2.equals(statusBijhouder
				.pop_redo()));
		assertEquals(1, statusBijhouder.getRedoSize());
		assertEquals(3, statusBijhouder.getUndoSize());
		
		assertEquals("Push/Pop faalt.", false, m4.equals(statusBijhouder
				.pop_redo()));
		assertEquals(0, statusBijhouder.getRedoSize());
		assertEquals(4, statusBijhouder.getUndoSize());
		
		assertEquals("Push/Pop faalt.", true, m1.equals(statusBijhouder
				.pop_undo()));
		assertEquals(3, statusBijhouder.getUndoSize());
		
		assertEquals("Push/Pop faalt.", true, m2.equals(statusBijhouder
				.pop_undo()));
		assertEquals(2, statusBijhouder.getUndoSize());
		
		assertEquals("Push/Pop faalt.", true, m3.equals(statusBijhouder
				.pop_undo()));
		assertEquals(1, statusBijhouder.getUndoSize());
		
		assertEquals("Push/Pop faalt.", true, m4.equals(statusBijhouder
				.pop_undo()));
		
		assertEquals("Geen elementen faalt.", 0, statusBijhouder.getUndoSize());
	}
}

class TestObject {
	int testValueInt;
	boolean testValueBool;
	Object testValueObject;

	public TestObject(int testValueInt, boolean testValueBool,
			Object testValueObject) {
		this.testValueInt = testValueInt;
		this.testValueBool = testValueBool;
		this.testValueObject = testValueObject;
	}

	public boolean equals(Object obj) {
		TestObject andereTestObject = (TestObject) obj;
		// 2 TestObjecten zijn gelijk a.s.a:
		// * Beide testValueInt's gelijk zijn
		// * Beide testValueBool's gelijk zijn
		// * Beide testValueObject null zijn OF beide testValueObject gelijk
		// zijn
		return testValueInt == andereTestObject.testValueInt
				&& testValueBool == andereTestObject.testValueBool
				&& ((testValueObject == null && andereTestObject.testValueObject == null) || testValueObject
						.equals(andereTestObject.testValueObject));
	}
}

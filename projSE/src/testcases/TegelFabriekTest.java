package testcases;

import java.util.ArrayDeque;
import java.util.ArrayList;

import actua.Tegel;
import actua.TegelFabriek;
import junit.framework.TestCase;

public class TegelFabriekTest extends TestCase {
	public TegelFabriekTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testMaakTegels() {
		TegelFabriek tf = new TegelFabriek();
		
		ArrayDeque<Tegel> stapel = tf.maakTegelDeque(72);
		assertEquals(72, stapel.size());
		
		for (int i = 0; i < stapel.size(); ++i) {
			assertNotNull(stapel.pop());
		}
	}

}

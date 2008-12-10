package testcases;

import java.util.ArrayList;
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
		
		ArrayList<char[]> stapel = tf.maakTegelStack(72);
		assertEquals(72, stapel.size());
	}

}

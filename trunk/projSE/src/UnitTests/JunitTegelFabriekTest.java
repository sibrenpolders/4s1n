package UnitTests;

import java.util.ArrayDeque;
import Tafel.TegelFabriek;
import junit.framework.TestCase;

public class JunitTegelFabriekTest extends TestCase {
	public JunitTegelFabriekTest(String name) {
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

		ArrayDeque<String[]> stapel = tf.maakTegelDeque(72);
		assertEquals(72, stapel.size());

		for (int i = 0; i < stapel.size(); ++i) {
			assertEquals("0", stapel.peekFirst()[2]);
			assertNotNull(stapel.pop());
		}

	}

}

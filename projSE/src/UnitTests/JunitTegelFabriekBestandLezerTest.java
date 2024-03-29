package UnitTests;

import Tafel.TegelFabriekBestandLezer;
import junit.framework.TestCase;

public class JunitTegelFabriekBestandLezerTest extends TestCase {
	private TegelFabriekBestandLezer tfbl;

	public JunitTegelFabriekBestandLezerTest() {
		super();
	}

	protected void setUp() throws Exception {
		super.setUp();
		tfbl = new TegelFabriekBestandLezer("src/xml/Tegels.xml");
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// ook nog testen met corrupte files
	public void testGetAantalTegels() {
		assertEquals("Aantal tegels", 19, tfbl.getAantalTegels());
	}

	// ook nog testen met corrupte files
	public void testGetTegelStrings() {
		String[] string = tfbl.getTegelStrings(0);
		assertEquals("wsssssswwwwww", string[0]);
	}
}

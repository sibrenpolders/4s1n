package UnitTests;

import Tafel.TegelFabriekBestandLezer;
import junit.framework.TestCase;

public class TegelFabriekBestandLezerTest extends TestCase {
	private TegelFabriekBestandLezer tfbl;
	private static final char WEI = 'w';
	private static final char WEG = 'g';
	private static final char KLOOSTER = 'k';
	private static final char STAD = 's';
	
	public TegelFabriekBestandLezerTest() {
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

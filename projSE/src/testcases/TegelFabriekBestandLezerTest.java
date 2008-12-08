package testcases;

import actua.TegelFabriekBestandLezer;
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
	public void testGetLandsdeelMatrix() {
		char[] landsdelen = tfbl.getLandsdeelMatrix(0);
				
		assertEquals(WEI, landsdelen[0]);
		assertEquals(STAD, landsdelen[1]);
		assertEquals(STAD, landsdelen[2]);
		assertEquals(STAD, landsdelen[3]);
		assertEquals(STAD, landsdelen[4]);
		assertEquals(WEI, landsdelen[5]);
		assertEquals(WEI, landsdelen[6]);
		assertEquals(STAD, landsdelen[7]);
		assertEquals(WEI, landsdelen[8]);
		assertEquals(WEI, landsdelen[9]);
		assertEquals(WEI, landsdelen[10]);
		assertEquals(WEI, landsdelen[11]);
		assertEquals(STAD, landsdelen[12]);		
	}

}

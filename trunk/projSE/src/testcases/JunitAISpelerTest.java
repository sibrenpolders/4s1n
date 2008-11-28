package testcases;

import junit.framework.TestCase;
import actua.Tafel;
import actua.Tegel;
import actua.AI;

public class JunitAISpelerTest extends TestCase {
	private AI ai;

	protected void setUp() throws Exception {
		super.setUp();
		
		ai = new AI();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testNeemPion() {
		int prevPionnen=ai.getPionnen();
		
		ai.neemPion();
		
		assertEquals(prevPionnen-1,ai.getPionnen());
	}
	
	public void testPlaatsPion() {
		Tafel tafel = new Tafel();
		boolean geplaatst;
		
		geplaatst=ai.plaatsPion(tafel);
		
		assertTrue(geplaatst);
	}
	
	public void testNeemPionTerug() {
		boolean terug;
		long prevScore = ai.getScore();
		
		terug = ai.neemPionTerug();
		
		if (terug)
			assertNotSame(prevScore,ai.getScore());
	}
	
	public void testPlaatsTegel() {
		Tafel tafel = new Tafel();
		Tegel tegel = new Tegel();
		boolean geplaatst;
		
		geplaatst=ai.plaatsTegel(tegel,tafel);
		
		assertTrue(geplaatst);
	}
}

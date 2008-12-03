package testcases;

import junit.framework.TestCase;
import actua.Easy;
import actua.Tafel;
import actua.Tegel;
import actua.AI;
import actua.Pion;

public class JunitAISpelerTest extends TestCase {
	private AI ai;

	protected void setUp() throws Exception {
		super.setUp();
		
		ai = new AI();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPlaatsPion() {
		Tafel tafel = new Tafel();
		Pion p;
		boolean geplaatst;
		
		p = ai.neemPion(tafel);
		
		if (p!=null) {
			geplaatst=ai.plaatsPion(p);
			assertTrue(geplaatst);
		}
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

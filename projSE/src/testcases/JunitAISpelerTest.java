package testcases;

import junit.framework.TestCase;
import actua.Easy;
import actua.Spel;
import actua.Tafel;
import actua.Tegel;
import actua.AI;
import actua.Pion;
import actua.Speler;
import actua.Vector2D;

public class JunitAISpelerTest extends TestCase {
	Spel spel;

	protected void setUp() throws Exception {
		spel = new Spel();
		spel.getSpelerVerwerker().voegSpelerToe(AI.EASY, "test",
				Speler.SPELER_GEEL, 0, spel.getTafelVerwerker());
	}

	public void testPlaatsPion() {
		Tegel tegel = spel.getTafelVerwerker().neemTegelVanStapel();
		Vector2D v = spel.getSpelerVerwerker().geefHuidigeSpeler().

		p = ai.neemPion(tafel);

		if (p != null) {
			geplaatst = ai.setPion(p);
			assertTrue(geplaatst);
		}
	}

	public void testNeemPionTerug() {
		boolean terug;
		long prevScore = ai.getScore();

		terug = ai.neemPionTerug();

		if (terug)
			assertNotSame(prevScore, ai.getScore());
	}

	public void testPlaatsTegel() {
		Tafel tafel = new Tafel();
		Tegel tegel = new Tegel();
		boolean geplaatst;

		geplaatst = ai.plaatsTegel(tegel, tafel);

		assertTrue(geplaatst);
	}
}

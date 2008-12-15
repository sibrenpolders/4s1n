package testcases;

import java.util.ArrayList;

import actua.Pion;
import actua.Score;
import actua.Speler;
import actua.Tafel;
import actua.Tegel;
import actua.Vector2D;
import junit.framework.TestCase;

public class JunitScoreTest extends TestCase {

	public JunitScoreTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testUpdateScore() {
		ArrayList<Tegel> tegels = maakTegels();
		ArrayList<Vector2D> coords = maakCoord();
		Score score = new Score();
		Tafel tafel = new Tafel(score);
		
		for (int i = 0; i < 4; ++i) {
			if(!tafel.plaatsTegel(tegels.get(i), coords.get(i))) {
				System.err.println("TEGEL_PLAATSING");
			}
		}
		// nog geen pion geplaatst
		tafel.updateScore(coords.get(4));		
		assertEquals(0, score.getSpelerRood());
		
		Pion pion = new Pion(Speler.SPELER_ROOD);
		pion.zetGeplaatst(true);
		if (!tafel.plaatsPion(coords.get(3), Tegel.NOORD, pion)) {
			System.err.println("PION_PLAATSING");
		}
		
		// veld is niet volledig
		tafel.updateScore(coords.get(4));		
		assertEquals(0, score.getSpelerRood());
		
		tafel.plaatsTegel(tegels.get(4), coords.get(4));
		// score kan berekend worden
		tafel.updateScore(coords.get(4));		
		assertEquals(10, score.getSpelerRood());
	}

	private ArrayList<Vector2D> maakCoord() {
		ArrayList<Vector2D> coords = new ArrayList<Vector2D>();
		coords.add(new Vector2D(0, 0));
		coords.add(new Vector2D(-1, 0));
		coords.add(new Vector2D(0, 1));
		coords.add(new Vector2D(1, 0));
		coords.add(new Vector2D(0, -1));
		
		return coords;
	}

	private ArrayList<Tegel> maakTegels() {
		ArrayList<Tegel> tegels = new ArrayList<Tegel>();
							
		tegels.add(new Tegel("sssssssssssss", "0000000000000"));
		tegels.add(new Tegel("wwwwwwwwwsssw", "0000000001110"));
		tegels.add(new Tegel("swwwwswwswwww", "0111101101111"));
		tegels.add(new Tegel("wssswwwwwwwww", "0111000000000"));
		tegels.add(new Tegel("wwwwswwswwwws", "0000100100001"));
		
		return tegels;
	}

}

package testcases;

import java.util.ArrayList;

import actua.Pion;
import actua.PuntenVerwerker;
import actua.Spel;
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
		PuntenVerwerker puntenVerwerker = new PuntenVerwerker();
		Tafel tafel = new Tafel();
		
		for (int i = 0; i < 4; ++i) {
			if(!tafel.plaatsTegel(tegels.get(i).getTegelString(), coords.get(i))) {
				System.err.println("TEGEL_PLAATSING: " + i);
			}
		}
		// nog geen pion geplaatst
		tafel.updateScore(coords.get(4),puntenVerwerker);		
		assertEquals(0, puntenVerwerker.getSpelerRood());
		
		Pion pion = new Pion(Spel.ROOD);
		pion.zetGeplaatst(true);
		if (!tafel.plaatsPion(coords.get(3), Tegel.NOORD, pion.getKleur())) {
			System.err.println("PION_PLAATSING");
		}
		
		// veld is niet volledig
		tafel.updateScore(coords.get(4),puntenVerwerker);		
		assertEquals(0, puntenVerwerker.getSpelerRood());
		
		tafel.plaatsTegel(tegels.get(4).getTegelString(), coords.get(4));
		// score kan berekend worden
		tafel.updateScore(coords.get(4),puntenVerwerker);		
		assertEquals(10, puntenVerwerker.getSpelerRood());
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
							
		tegels.add(new Tegel("sssssssssssss", "0000000000000",(short) 0));
		tegels.add(new Tegel("wwwwwwwssswww", "0000000111000",(short) 0));
		tegels.add(new Tegel("swwwwwwwwwssw", "1000000000110",(short) 0));
		tegels.add(new Tegel("wssswwwwwwwww", "0111000000000",(short) 0));
		tegels.add(new Tegel("wwwwssswwwwww", "0000111000000",(short) 0));
		
		return tegels;
	}

}

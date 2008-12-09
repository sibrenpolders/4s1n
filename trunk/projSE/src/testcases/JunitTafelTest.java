package testcases;

import java.util.Vector;

import actua.Landsdeel;
import actua.Pion;
import actua.Tafel;
import actua.Tegel;
import actua.Vector2D;
import actua.Vector3D;
import junit.framework.TestCase;

public class JunitTafelTest extends TestCase {
	Tafel tafel;
	Vector<Tegel> tegels;
	Vector<Vector2D> coords;
	
	protected void setUp() throws Exception {
		super.setUp();
		tafel = new Tafel();
		tegels = new Vector<Tegel>();
		coords = new Vector<Vector2D>();
		maaktestTegels();
		
		tafel.plaatsTegel(tegels.get(0), coords.get(0));
	}

	protected void tearDown() throws Exception {
	}

	public void testBeweegCamera() {
		Vector3D v = new Vector3D(3, 3, 3);

		tafel.beweegCamera(v);
		assertEquals(v, tafel.getOogpunt().getHuidigeVector());
	}

	public void testPlaatsTegel() {
//		long startMemory = getMemoryUse();
//		System.out.println(startMemory);
		tafel.clear();
		
		// testen van plaatsen van starttegels, en de tegels daarond
		assertTrue("Plaatsing tegel faalt", tafel.plaatsTegel(tegels.get(0), coords.get(0)));
		Vector2D coord;
		for (int i = 1; i < 5; ++i) {
			coord = coords.get(i);
			assertTrue("Plaatsing tegel " + (i+1) + " faalt. coord: (" + coord.getX() + ", " + coord.getY() + ")", 
					tafel.plaatsTegel(tegels.get(i), coord));
		}
		assertFalse("Plaatsing zelfde tegel faalt", tafel.plaatsTegel(tegels.get(0), coords.get(0)));		
				
		Tegel t;
		// testen of de tegels goed zijn opgeslagen
		for (int i = 0; i < 5; ++i) {
			t = tafel.bepaalTegel(coords.get(i));
			if (t != null) {
				assertTrue("Verkeerde tegel opslag: tegel " + i, t == tegels.get(i));
			}
		}	
		
		// testen of een niet bestaande tegel kan opgevraagd worden
		assertNull(tafel.bepaalTegel(new Vector2D(-100, 100)));
//		long endMemory = getMemoryUse();
//		System.out.println(endMemory);
//		System.err.println(Math.round((((float)endMemory - startMemory)/100f)));		
	}

	private void maaktestTegels() {
		Vector<char[]> landsdelen = maakLandsDelen();
		
		for (int i = 0; i < 5; ++i) {
			tegels.add(new Tegel(landsdelen.get(i)));
		}
		
		coords.add(new Vector2D(0, 0)); // startpos (0)
		coords.add(new Vector2D(-1, 0)); // boven (1)
		coords.add(new Vector2D(0, 1)); // rechts (2)
		coords.add(new Vector2D(1, 0)); // onder (3)
		coords.add(new Vector2D(0, -1)); // links (4)
		
	}

	private Vector<char[]> maakLandsDelen() {
		Vector<char[]> landsdelen = new Vector<char[]>();
		
		char[] ld = new char[13];
		ld[6] = ld[0] = ld[5] = ld[8] = ld[4] = ld[7] = ld[12] = Landsdeel.STAD;
		ld[1] = ld[2] = ld[3] = ld[9] = ld[10] = ld[11] = Landsdeel.WEI;
		landsdelen.add(ld);
		
		// landsdeel voor de 2de tegel
		char[] ld2 = new char[13];		
		ld2[1] = ld2[2] = ld2[3] = ld2[4] = ld2[7] = ld2[12] = Landsdeel.STAD;
		ld2[0] = ld2[5] = ld2[8] = ld2[9] = ld2[6] = ld2[10] = ld2[11] = Landsdeel.WEI;
		landsdelen.add(ld2);
		
		// landsdeel voor de 3de tegel
		char[] ld3 = new char[13];
		ld3[0] = ld3[5] = ld3[8] = ld3[4] = ld3[7] = ld3[12] = Landsdeel.STAD;
		ld3[1] = ld3[2] = ld3[3] = ld3[7] = ld3[9] = ld3[10] = ld3[11] = Landsdeel.WEI;		
		landsdelen.add(ld3);
		
		landsdelen.add(ld3);
		landsdelen.add(ld2);
		
		return landsdelen;
	}

	public void testPlaatsPion() {
		tafel.clear();
		Vector2D v = new Vector2D(1, 1);
		Pion pion=new Pion();
		 
		 // probleem tegel coordinaten
		 tafel.plaatsPion(v, pion);
		 
		 
	}

	public void testIsPlaatsingGeldig() {
		tafel.clear();
		
		tafel.plaatsTegel(tegels.get(0), coords.get(0));
		
		// op dezelfde plek opnieuw zetten mag niet
		assertFalse(tafel.isTegelPlaatsingGeldig(tegels.get(0), coords.get(0)));
		
		// in het wilde weg zetten mag niet (tegels moeten aansluiten)
		assertFalse(tafel.isTegelPlaatsingGeldig(tegels.get(0), new Vector2D(10, 10)));
		
		// landsdelen komen niet overeen
		assertFalse(tafel.isTegelPlaatsingGeldig(tegels.get(1), coords.get(2)));
	
		// en natuurlijk nog een juiste plaatsing testen
		assertTrue(tafel.isTegelPlaatsingGeldig(tegels.get(1), coords.get(1)));
	}

	public void testIsLaatste() {
		tafel.clear();
				
		tafel.plaatsTegel(tegels.get(0), coords.get(0));
		assertTrue(tafel.isLaatste(tegels.get(0)));
		
		tafel.plaatsTegel(tegels.get(1), coords.get(1));
		assertTrue(tafel.isLaatste(tegels.get(1)));
	}

	public void testBepaalTegel() {
		tafel.clear();
		
		// een niet geldige positie moet null geven.
		assertNull(tafel.bepaalTegel(new Vector2D(0, 0)));
		
		// tegel plaatsen en nagaan dat hij werkelijk geplaatst is
		tafel.plaatsTegel(tegels.get(0), coords.get(0));
		// een niet geldige positie moet null geven.
		assertTrue(tegels.get(0) == tafel.bepaalTegel(new Vector2D(0, 0)));
	}

	public void testIsGebiedGeldig() {
		fail("Not yet implemented");
	}

	public void testIsTegelPlaatsingGeldig() {
//		Vector2D v = new Vector2D(1, 0);
//		Tegel tegel = new Tegel();
//		
//		tafel.plaatsTegel(tegel, new Vector2D(0, 0));
//		assertTrue(tafel.isTegelPlaatsingGeldig(tegel, v));
//		tafel.plaatsTegel(tegel, v);		
//		assertFalse(tafel.isTegelPlaatsingGeldig(tegel, v));
	}

	public void testUndo() {
		fail("Not yet implemented");
	}

	public void testRedo() {
		fail("Not yet implemented");
	}
}

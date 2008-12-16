package testcases;

import java.util.ArrayList;

import actua.Pion;
import actua.Tafel;
import actua.Tegel;
import actua.Vector2D;
import actua.Vector3D;
import junit.framework.TestCase;

public class JunitTafelTest extends TestCase {
	Tafel tafel;
	ArrayList<Tegel> tegels;
	ArrayList<Vector2D> coords;
	
	protected void setUp() throws Exception {
		super.setUp();
		tafel = new Tafel();
		tegels = new ArrayList<Tegel>();
		coords = new ArrayList<Vector2D>();
		maaktestTegels();		
	}

	protected void tearDown() throws Exception {
	}

	public void testBeweegCamera() {
		Vector3D v = new Vector3D(3, 3, 3);

		tafel.beweegCamera(v);
		assertEquals(v, tafel.getOogpunt().getHuidigeVector());
	}

	public void testPlaatsTegel() {
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
				assertTrue("Verkeerde tegel opslag: tegel " + i, t.equals(tegels.get(i)));
			}
		}	
		
		// testen of een niet bestaande tegel kan opgevraagd worden
		assertNull(tafel.bepaalTegel(new Vector2D(-100, 100)));
	}

	private void maaktestTegels() {
		maakLandsDelen();		
		coords.add(new Vector2D(0, 0)); // startpos (0)
		coords.add(new Vector2D(-1, 0)); // boven (1)
		coords.add(new Vector2D(0, 1)); // rechts (2)
		coords.add(new Vector2D(1, 0)); // onder (3)
		coords.add(new Vector2D(0, -1)); // links (4)
		
	}

	private void maakLandsDelen() {
		tegels = new ArrayList<Tegel>();
		
		tegels.add(new Tegel("swwwssswwwssw", "0111222111001"));
		tegels.add(new Tegel("wsssssswwwwww", "0111111000000"));
		tegels.add(new Tegel("swwwssssswwws", "0111000222000"));
		tegels.add(new Tegel("wwwwwwwwwwwwk", "0000000000001"));
		tegels.add(new Tegel("swwwssswwwsss", "0111000222000"));
	}

	public void testPlaatsPion() {
		tafel.clear();
		Pion p = new Pion();
		// testen van plaatsen van starttegels, en de tegels daarond
		tafel.plaatsTegel(tegels.get(0), coords.get(0));
		assertTrue(tafel.plaatsPion(coords.get(0), Tegel.NOORD, p));
		tafel.plaatsTegel(tegels.get(1), coords.get(1));
		assertFalse(tafel.plaatsPion(coords.get(1), Tegel.ZUID, p));
		
		for (int i = 2; i < 4; ++i) {
			tafel.plaatsTegel(tegels.get(i), coords.get(i));
		}
						
		assertFalse(tafel.plaatsPion(coords.get(1), Tegel.OOST, p));
		assertFalse(tafel.plaatsPion(coords.get(3), Tegel.OOST, p));
	}

	public void testIsTegelPlaatsingGeldig() {
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
				
		Vector2D coord = coords.get(0);
		tafel.plaatsTegel(tegels.get(0), coord);
		assertTrue(tafel.isLaatste(coord.getX(), coord.getY()));
		
		coord = coords.get(1);
		tafel.plaatsTegel(tegels.get(1), coord);
		assertTrue(tafel.isLaatste(coord.getX(), coord.getY()));
	}

	public void testBepaalTegel() {
//		tafel.clear();
//		
//		// een niet geldige positie moet null geven.
//		assertNull(tafel.bepaalTegel(new Vector2D(0, 0)));
//		
//		// tegel plaatsen en nagaan dat hij werkelijk geplaatst is
//		tafel.plaatsTegel(tegels.get(0), coords.get(0));
//		// een niet geldige positie moet null geven.
//		assertTrue(tegels.get(0) == tafel.bepaalTegel(new Vector2D(0, 0)));
	}

	public void testIsGebiedGeldig() {
		fail("Not yet implemented");
	}

	public void testUndo() {
		tafel.clear();
		
		
	}

	public void testRedo() {
		fail("Not yet implemented");
	}
}

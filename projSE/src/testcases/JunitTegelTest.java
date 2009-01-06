package testcases;

import actua.Landsdeel;
import actua.Pion;
import actua.Tegel;
import actua.Vector2D;
import junit.framework.TestCase;

public class JunitTegelTest extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();

	}

	protected void tearDown() throws Exception {
		
	}

	public void testBepaalLandsdeel() {
		Tegel tegel = new Tegel("wsssssswwwwww", "0111111000000",(short)0);
		
		assertEquals("getLandsDeel 0", Landsdeel.WEI, tegel.bepaalLandsdeel(0).getType());
		assertEquals("getLandsDeel 1", Landsdeel.STAD, tegel.bepaalLandsdeel(1).getType());
		assertEquals("getLandsDeel 2", Landsdeel.STAD, tegel.bepaalLandsdeel(2).getType());
		assertEquals("getLandsDeel 3", Landsdeel.STAD, tegel.bepaalLandsdeel(3).getType());
		assertEquals("getLandsDeel 4", Landsdeel.STAD, tegel.bepaalLandsdeel(4).getType());
		assertEquals("getLandsDeel 5", Landsdeel.STAD, tegel.bepaalLandsdeel(5).getType());
		assertEquals("getLandsDeel 6", Landsdeel.STAD, tegel.bepaalLandsdeel(6).getType());
		assertEquals("getLandsDeel 7", Landsdeel.WEI, tegel.bepaalLandsdeel(7).getType());
		assertEquals("getLandsDeel 8", Landsdeel.WEI, tegel.bepaalLandsdeel(8).getType());
		assertEquals("getLandsDeel 9", Landsdeel.WEI, tegel.bepaalLandsdeel(9).getType());
		assertEquals("getLandsDeel 10", Landsdeel.WEI, tegel.bepaalLandsdeel(10).getType());
		assertEquals("getLandsDeel 11", Landsdeel.WEI, tegel.bepaalLandsdeel(11).getType());
		assertEquals("getLandsDeel 12", Landsdeel.WEI, tegel.bepaalLandsdeel(12).getType());
	}

	public void testPlaatsPion() {
//		Tegel tegel = new Tegel("wsssswwswwwws");
		Tegel tegel = new Tegel("wssswgwwwwwgg", "0111020000022",(short)0);
		Landsdeel landsDeel;
		
		// een eerste pion plaatsen op een stadsdeel
		assertTrue("Plaatsting eerste pion", tegel.plaatsPion(0, 'r'));
		
		// een tweede pion plaatsten op hetzelfde stadsdeel?
		landsDeel = tegel.bepaalLandsdeel(4);
		assertTrue("Staat er al een pion?", landsDeel.isPionGeplaatst());
		
		// een tweede pion plaatsen op het andere landsdeel?
		landsDeel = tegel.bepaalLandsdeel(2);
		assertFalse("Staat er al een pion?", landsDeel.isPionGeplaatst());
		
		// mag blijkbaar wel
		assertTrue("Plaatsting tweede pion", tegel.plaatsPion(2, 'r'));		
		// staat hij er wel?
		assertTrue("Staat er al een pion?", landsDeel.isPionGeplaatst());
	}

	public void testDraaiTegel() {
		Tegel tegel = new Tegel(); 
		// begin opstelling testen
		assertEquals("Begin opstelling tegel", 0, tegel.getOrientatie());
		
		// "beweegt" de tegel wel?
		tegel.draaiTegel(true); // 1 maal rechtsom kantellen
		assertEquals("Rechts draaien", 1, tegel.getOrientatie());
		tegel.draaiTegel(false); // 1 maal linksom kantellen
		assertEquals("Links draaien", 0, tegel.getOrientatie());
		
		// volledig draaien van de tegels testen
		draaiTegel360(tegel, true); // met de wijzers mee
		assertEquals("Volledig draaien van de tegel (rechtsom)", 0, tegel.getOrientatie());
		// na volledige draaiing nog 1 stap verder gaan
		tegel.draaiTegel(true);
		assertEquals("Volledig rond + 1 (rechts)", 1, tegel.getOrientatie());
		
		// tegel terug op de beginpositie zetten.
		tegel.draaiTegel(false);
		draaiTegel360(tegel, false); // tegen de wijzers in
		assertEquals("Volledig draaien van de tegel (linksom)", 0, tegel.getOrientatie());
				// na volledige draaiing nog 1 stap verder gaan
		tegel.draaiTegel(false);
		assertEquals("Volledig rond + 1 (linksom)", 3, tegel.getOrientatie());
		
		draaiTegel90();
		draaiTegel180();
		draaiTegel270();
	}

	private void draaiTegel270() {
		Tegel tegel = new Tegel("0123456789abc", "0123456789abc",(short)0);
		
		for (int i = 0; i < 3; ++i) {
			tegel.draaiTegel(true);
		}
		
		System.err.println(tegel.bepaalLandsdeel(Tegel.WEST_NOORD).getType());
		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST_NOORD).getType(), '3');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD_WEST).getType(), '4');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD).getType(), '5');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD_OOST).getType(), '6');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST_NOORD).getType(), '7');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST).getType(), '8');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST_ZUID).getType(), '9');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID_OOST).getType(), 'a');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID).getType(), 'b');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID_WEST).getType(), '0');
		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST_ZUID).getType(), '1');
		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST).getType(), '2');
		assertEquals(tegel.bepaalLandsdeel(Tegel.MIDDEN).getType(), 'c');
	}

	private void draaiTegel180() {
		Tegel tegel = new Tegel("0123456789abc", "0123456789abc",(short)0);

		for (int i = 0; i < 2; ++i) {
			tegel.draaiTegel(true);
		}

		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST_NOORD).getType(), '6');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD_WEST).getType(), '7');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD).getType(), '8');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD_OOST).getType(), '9');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST_NOORD).getType(), 'a');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST).getType(), 'b');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST_ZUID).getType(), '0');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID_OOST).getType(), '1');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID).getType(), '2');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID_WEST).getType(), '3');
		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST_ZUID).getType(), '4');
		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST).getType(), '5');
		assertEquals(tegel.bepaalLandsdeel(Tegel.MIDDEN).getType(), 'c');
	}

	private void draaiTegel90() {
		Tegel tegel = new Tegel("0123456789abc", "0123456789abc",(short)0);
		tegel.draaiTegel(true);

		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST_NOORD).getType(), '9');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD_WEST).getType(), 'a');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD).getType(), 'b');
		assertEquals(tegel.bepaalLandsdeel(Tegel.NOORD_OOST).getType(), '0');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST_NOORD).getType(), '1');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST).getType(), '2');
		assertEquals(tegel.bepaalLandsdeel(Tegel.OOST_ZUID).getType(), '3');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID_OOST).getType(), '4');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID).getType(), '5');
		assertEquals(tegel.bepaalLandsdeel(Tegel.ZUID_WEST).getType(), '6');
		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST_ZUID).getType(), '7');
		assertEquals(tegel.bepaalLandsdeel(Tegel.WEST).getType(), '8');
		assertEquals(tegel.bepaalLandsdeel(Tegel.MIDDEN).getType(), 'c');
		
	}

	private void draaiTegel360(Tegel tegel, boolean richting) {
		for (int i = 0; i < 4; ++i) {
			tegel.draaiTegel(true);
		}
	}
}

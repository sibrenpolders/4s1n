package testcases;

import actua.Memento;
import actua.Spel;
import actua.Bestand;
import actua.TafelVerwerker;
import actua.Tegel;
import actua.Vector2D;
import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class JunitBestandTest extends TestCase {
	private Bestand bestand;

	protected void setUp() throws Exception {
		super.setUp();
		bestand = new Bestand();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSchrijfNaarBestand() {
		Spel spel = new Spel();
		Spel spel2=null;

		bestand.schrijfNaarBestand(spel,"test");
		
		bestand.leesVanBestand(spel2, "test");
		
		assertNotNull(spel2);
		assertEquals(spel.geefAantalSpelers(), spel2.geefAantalSpelers());
		assertEquals(spel.geefHuidigeSpeler(), spel2.geefHuidigeSpeler());
	}
	
	public void testLeesVanBestand() {
	}
}

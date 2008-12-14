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
		TafelVerwerker t = spel.getTafelVerwerker();
		Tegel start = t.getLaatstGeplaatsteTegel();

		bestand.schrijfNaarBestand(spel, null, "test");
		Spel spel2 = new Spel();
		Memento me2 = new Memento();
		
		bestand.leesVanBestand(spel2, me2, "test");
		
		assertTrue(start == spel2.getTafelVerwerker().getLaatstGeplaatsteTegel());
	}
	
	public void testLeesVanBestand() {
	}
}

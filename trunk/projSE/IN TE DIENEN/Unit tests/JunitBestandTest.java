package UnitTests;

import Core.Bestand;
import Core.Spel;
import junit.framework.TestCase;

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
		spel2=bestand.getSpel();
		
		assertNotNull(spel2);
		assertEquals(spel.geefAantalSpelers(), spel2.geefAantalSpelers());
	}
	
	public void testLeesVanBestand() {
	}
}

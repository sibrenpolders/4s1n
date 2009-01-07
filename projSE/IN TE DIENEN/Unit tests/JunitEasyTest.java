package UnitTests;

import Core.Spel;
import Core.Vector2D;
import Spelers.Easy;

public class JunitEasyTest extends JunitAITest {
	Spel spel;

	protected void setUp() throws Exception {
		super.setUp();
		super.ai = new Easy();
		spel = new Spel();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBerekenTegel() {
		// Easy AI plaatst willekeurig een tegel op een geldige positie
		// 3 mogelijkheden: 1. Plaatsting van een tegel bij het startVeld.
		// 2. Plaatsing van een tegel is niet mogelijk.
		// 3. Plaatsing van een tegel is overvloedig mogelijk.
		// 4. Plaatsing van een tegel is maar op 1 plaats mogelijk.
		startVeldTest();
		ongeldigVeldTest();
		overvloedigVeldTest();
		enkelVeldTest();
	}

	public void testBerekenPion() {
		// Easy AI gaat een pion plaatsen als deze te plaatsen is
		// 2 cases:
		// -- 1: Er kan een pion geplaatst worden.
		// -- 2: Er kan geen pion geplaatst worden.

		geenPionPlaatsingTest();
		pionPlaatsingTest();
	}

	// 1: Er kan een pion geplaatst worden.
	protected void geenPionPlaatsingTest() {
		int berekendePositie;
		spel = new Spel();

		maakVeldGeenPion(spel.getTafelVerwerker());
		gewenstePionPos = -1;
		Vector2D laatstGeplaatst = new Vector2D(0, 0);
		berekendePositie = ai.berekenPlaatsPion(laatstGeplaatst, spel
				.getTafelVerwerker());
		assertEquals("Fout bij geen pion plaatsing mogelijk", gewenstePionPos,
				berekendePositie);
	}

	// 2: Er kan geen pion geplaatst worden.
	protected void pionPlaatsingTest() {
		int berekendePositie;
		spel = new Spel();

		maakVeldPion(spel.getTafelVerwerker());
		gewenstePionPos = 0;
		Vector2D laatstGeplaatst = new Vector2D(0, 0);
		berekendePositie = ai.berekenPlaatsPion(laatstGeplaatst, spel
				.getTafelVerwerker());
		assertEquals("Fout bij geen pion plaatsing mogelijk", gewenstePionPos,
				berekendePositie);
	}

	protected void enkelVeldTest() {
	}

	protected void overvloedigVeldTest() {
		// * veld met x tegels en de huidige tegel kan op meerdere plaatsen
		// gezet worden
		spel = new Spel();
		maakOvervloedigVeld(spel.getTafelVerwerker());
		Vector2D berekendePositie = ai.berekenPlaatsTegel(gewensteTegel, spel
				.getTafelVerwerker());
		assertEquals(berekendePositie.getX(), gewenstePositie.getX());
		assertEquals(berekendePositie.getY(), gewenstePositie.getY());
	}

	protected void ongeldigVeldTest() {
		// voor een veld met x tegels waarbij de huidige tegel niet geplaatst
		// kan worden de functie BerekenTegel geeft null terug als er geen
		// geldige positie is gevonden
		spel = new Spel();
		maakOngeldigVeld(spel.getTafelVerwerker());
		gewensteTegel = new String[3];
		gewensteTegel[0] = "wwwwwwwwwwwww";
		gewensteTegel[1] = "0000000000000";
		gewensteTegel[2] = "0";
		Vector2D berekendePositie = ai.berekenPlaatsTegel(gewensteTegel, spel
				.getTafelVerwerker());
		assertNull("Fout bij ongeldige plaatsing", berekendePositie);

	}

	protected void startVeldTest() {
		// voor een veld met 1 tegel
		spel = new Spel();
		maakStartVeld(spel.getTafelVerwerker());
		gewensteTegel = new String[3];
		gewensteTegel[0] = "kkkkwwwkkkkkk";
		gewensteTegel[1] = "0000111000000";
		gewensteTegel[2] = "0";

		gewenstePositie = new Vector2D(0, -1);
		Vector2D berekendePositie = ai.berekenPlaatsTegel(gewensteTegel, spel
				.getTafelVerwerker());
		assertEquals(berekendePositie.getX(), gewenstePositie.getX());
		assertEquals(berekendePositie.getY(), gewenstePositie.getY());
	}
}

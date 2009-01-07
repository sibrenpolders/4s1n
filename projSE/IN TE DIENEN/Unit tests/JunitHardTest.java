package UnitTests;

import Core.Spel;
import Core.Vector2D;
import Spelers.Hard;

public class JunitHardTest extends JunitAITest {
	Spel spel;

	protected void setUp() throws Exception {
		super.setUp();
		super.ai = new Hard();
		spel = new Spel();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBerekenTegel() {
		// Maak een voorbeeld veld vector aan.
		// Maak een voorbeeld stapel vector aan.
		// Maak een te plaatsen tegel aan.
		// Roep de functie berekenTegel aan en kijk of de verkregen Vector2D
		// overeenkomt met wat je verwacht.
		// Doe dit voor 4 voorbeelden:
		// * veld met 1 tegel
		// * veld met x tegels en de huidige tegel kan niet geplaatst worden
		// * veld met x tegels en de huidige tegel kan op meerdere plaatsen
		// gezet worden
		// * veld met x tegels en de huidige tegel kan op 1 plaats gezet worden

		startVeldTest();
		ongeldigVeldTest();
		overvloedigVeldTest();
		enkelVeldTest();
	}

	protected void enkelVeldTest() {
		// * veld met x tegels en de huidige tegel kan op 1 plaats gezet worden
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
		// Voor een veld met x tegels waarbij de huidige tegel niet geplaatst
		// kan worden. De functie BerekenTegel geeft null terug als er geen
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

	public void testBerekenPion() {
		geenPionPlaatsingTest();
		pionPlaatsingTest();
	}

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
}

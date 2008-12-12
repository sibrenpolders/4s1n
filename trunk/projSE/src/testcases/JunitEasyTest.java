package testcases;

import java.util.Vector;

import actua.Easy;
import actua.Tegel;
import actua.Vector2D;

public class JunitEasyTest extends JunitAITest {
	protected void setUp() throws Exception {
		super.setUp();
		super.ai = new Easy();
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
		// 2 cases 1: Er kan een pion geplaatst worden.
		// 2: Er kan geen pion geplaatst worden.

		geenPionPlaatsingTest();
		pionPlaatsingTest();
	}

	// 1: Er kan een pion geplaatst worden.
	protected void geenPionPlaatsingTest() {
		Vector<Vector<Tegel>> veld = new Vector<Vector<Tegel>>();
		Vector2D laatstGeplaatst = new Vector2D();
		Vector2D berekendePositie;

		maakVeldGeenPion(veld, laatstGeplaatst);
		berekendePositie = ai.BerekenPion(veld, laatstGeplaatst);
		assertEquals("Fout bij geen pion plaatsing mogelijk", null,
				berekendePositie);
	}

	// 2: Er kan geen pion geplaatst worden.
	protected void pionPlaatsingTest() {
		Vector<Vector<Tegel>> veld = new Vector<Vector<Tegel>>();
		Vector2D laatstGeplaatst = new Vector2D();
		Vector2D gewenstePositie = new Vector2D();
		Vector2D berekendePositie;

		maakVeldPion(veld, laatstGeplaatst, gewenstePositie);
		berekendePositie = ai.BerekenPion(veld, laatstGeplaatst);
		assertEquals("Fout bij pion plaatsing mogelijk", gewenstePositie,
				berekendePositie);
	}

	protected void enkelVeldTest() {
		// * veld met x tegels en de huidige tegel kan op 1 plaats gezet worden
		Vector<Vector<Tegel>> veld = new Vector<Vector<Tegel>>();
		Vector<Tegel> stapel = new Vector<Tegel>();
		Tegel plaatsTegel = new Tegel();
		Vector2D gewensteTegelPositie = new Vector2D();
		Vector2D berekendePositie;

		maakEnkelVeld(veld, plaatsTegel, gewensteTegelPositie);
		berekendePositie = ai.BerekenTegel(veld, stapel, plaatsTegel);
		// TODO functie moet nog ergens bijkomen?
		// assert("Fout bij 1 mogelijke plaatsing",
		// geldigeZet(berekendePositie));

	}

	protected void overvloedigVeldTest() {
		// * veld met x tegels en de huidige tegel kan op meerdere plaatsen
		// gezet worden
		Vector<Vector<Tegel>> veld = new Vector<Vector<Tegel>>();
		Vector<Tegel> stapel = new Vector<Tegel>();
		Tegel plaatsTegel = new Tegel();
		Vector2D gewensteTegelPositie = new Vector2D();
		Vector2D berekendePositie;

		maakOvervloedigVeld(veld, plaatsTegel, gewensteTegelPositie);
		berekendePositie = ai.BerekenTegel(veld, stapel, plaatsTegel);

		// TODO functie moet nog ergens bijkomen?
		// assert("Fout bij 1 mogelijke plaatsing",
		// geldigeZet(berekendePositie));
	}

	protected void ongeldigVeldTest() {
		// voor een veld met x tegels waarbij de huidige tegel niet geplaatst
		// kan worden
		// de functie BerekenTegel geeft null terug als er geen geldige positie
		// is gevonden
		Vector<Vector<Tegel>> veld = new Vector<Vector<Tegel>>();
		Vector<Tegel> stapel = new Vector<Tegel>();
		Tegel plaatsTegel = new Tegel();
		Vector2D berekendePositie;

		maakOngeldigVeld(veld, plaatsTegel);
		berekendePositie = ai.BerekenTegel(veld, stapel, plaatsTegel);
		assertEquals("Fout bij ongeldige plaatsing", null, berekendePositie);

	}

	protected void startVeldTest() {
		// voor een veld met 1 tegel
		Vector<Vector<Tegel>> veld = new Vector<Vector<Tegel>>();
		Vector<Tegel> stapel = new Vector<Tegel>();
		Tegel plaatsTegel = new Tegel();
		Vector2D gewensteTegelPositie = new Vector2D();
		Vector2D berekendePositie =  null;

		maakStartVeld(veld, plaatsTegel, gewensteTegelPositie);
		maakStapelTegels(stapel);
		berekendePositie = ai.berekenPlaatsTegel(plaatsTegel);
		// TODO functie moet nog ergens bijkomen?
		// assert("Fout bij 1 mogelijke plaatsing",
		// geldigeZet(berekendePositie));
	}

}

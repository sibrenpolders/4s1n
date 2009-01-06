package testcases;

import java.util.Vector;

import actua.Hard;
import actua.Spel;
import actua.Tegel;
import actua.Vector2D;


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
		// Maak een te plaatsen tegen aan
		// Roep de functie berekenTegel aan en kijk of de verkregen Vector2D overeenkomt met wat je verwacht.
		// doe dit voor 4 voorbeelden
		// * veld met 1 tegel
		// * veld met x tegels en de huidige tegel kan niet geplaatst worden
		// * veld met x tegels en de huidige tegel kan op meerdere plaatsen gezet worden
		// * veld met x tegels en de huidige tegel kan op 1 plaats gezet worden
		
		startVeldTest();
		ongeldigVeldTest();
		overvloedigVeldTest();
		enkelVeldTest();		
	}

	protected void enkelVeldTest() {
		// * veld met x tegels en de huidige tegel kan op 1 plaats gezet worden
		Vector<Vector<Tegel>> veld =  new Vector<Vector<Tegel>>();
		Vector<Tegel> stapel = new Vector<Tegel>();
		Tegel plaatsTegel = new Tegel();
		Vector2D gewensteTegelPositie = new Vector2D();
		Vector2D berekendePositie;
		String[] t = new String[3];
		
		maakEnkelVeld(veld, plaatsTegel, gewensteTegelPositie);
		berekendePositie = ai.berekenPlaatsTegel(t, spel.getTafelVerwerker());
		assertEquals("Fout bij 1 mogelijke plaatsing", gewensteTegelPositie, berekendePositie);
		
	}

	protected void overvloedigVeldTest() {		
		// * veld met x tegels en de huidige tegel kan op meerdere plaatsen gezet worden
		Vector<Vector<Tegel>> veld =  new Vector<Vector<Tegel>>();
		Vector<Tegel> stapel = new Vector<Tegel>();
		Tegel plaatsTegel = new Tegel();
		Vector2D gewensteTegelPositie = new Vector2D();
		Vector2D berekendePositie;
		String[] t = new String[3];
		
		maakOvervloedigVeld(veld, plaatsTegel, gewensteTegelPositie);
		berekendePositie = ai.berekenPlaatsTegel(t, spel.getTafelVerwerker());
		assertEquals("Fout bij meerdere mogelijke plaatsingen", gewensteTegelPositie, berekendePositie);
		
	}

	protected void ongeldigVeldTest() {
		// voor een veld met x tegels waarbij de huidige tegel niet geplaatst kan worden
		// de functie BerekenTegel geeft null terug als er geen geldige positie is gevonden
		Vector<Vector<Tegel>> veld =  new Vector<Vector<Tegel>>();
		Vector<Tegel> stapel = new Vector<Tegel>();
		Tegel plaatsTegel = new Tegel();		
		Vector2D berekendePositie;
		String[] t = new String[3];
		
		maakOngeldigVeld(veld, plaatsTegel);
		berekendePositie = ai.berekenPlaatsTegel(t, spel.getTafelVerwerker());
		assertEquals("Fout bij ongeldige plaatsing", null, berekendePositie);
		
	}

	protected void startVeldTest() {
		// voor een veld met 1 tegel
		Vector<Vector<Tegel>> veld =  new Vector<Vector<Tegel>>();
		Vector<Tegel> stapel = new Vector<Tegel>();
		Tegel plaatsTegel = new Tegel();
		Vector2D gewensteTegelPositie = new Vector2D();
		Vector2D berekendePositie;
		String[] t = new String[3];
		
		maakStartVeld(veld, plaatsTegel, gewensteTegelPositie);				
		maakStapelTegels(stapel);		
		berekendePositie = ai.berekenPlaatsTegel(t, spel.getTafelVerwerker());
		assertEquals("Fout bij plaatsing met enkel starttegel", gewensteTegelPositie, berekendePositie);						
	}	

	// TODO functie invullen
	public void testBerekenPion() {
		fail("Not yet implemented");
	}
	@Override
	protected void geenPionPlaatsingTest() {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void pionPlaatsingTest() {
		// TODO Auto-generated method stub
		
	}

}

package UnitTests;

import java.util.Vector;

import Core.Vector2D;
import Spelers.Strategy;
import Tafel.Tafel;
import Tafel.TafelVerwerker;
import Tafel.Tegel;

import junit.framework.TestCase;

abstract public class JunitAITest extends TestCase {
	protected Strategy ai;
	protected String[] gewensteTegel;
	protected Vector2D gewenstePositie;
	protected int gewenstePionPos;
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	abstract protected void startVeldTest();
	abstract protected void ongeldigVeldTest();
	abstract protected void overvloedigVeldTest();
	abstract protected void enkelVeldTest();
	abstract protected void geenPionPlaatsingTest();
	abstract protected void pionPlaatsingTest();
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	// Deze methode zal een veld genereren zodat de te plaatsen tegel (plaatsTegel) maar op 1 positie
	// geplaatst kan worden
	// gewenstPositie is deze positie
	// gewensteTegel is de tegel die daar moet komen.
	protected void maakEnkelVeld(TafelVerwerker tafel) {		
	}

	// Deze methode zal een veld genereren zodat de te plaatsen tegel (plaatsTegel) maar op meerder posities
	// geplaatst kan worden. GewensteTegelPositie is de positie waarnaar onze voorkeur gaat.
	protected void maakOvervloedigVeld(TafelVerwerker tafel) {
		gewensteTegel = new String[3];
		gewensteTegel[0] = "sssssssssssss";
		gewensteTegel[1] = "0000000000000";
		gewensteTegel[2] = "0";
		
		tafel.plaatsTegel(gewensteTegel, new Vector2D(0, 0));
		gewenstePositie = new Vector2D(-1, 0);
				
	}

	// Deze methode zal een veld genereren zodat de te plaatsen tegel (plaatsTegel) niet op het veld
	// geplaatst kan worden. GewensteTegelPositie is dus null.
	protected void maakOngeldigVeld(TafelVerwerker tafel) {
		String[] tegel = new String[3];
		tegel[0] = "sssssssssssss";
		tegel[1] = "0000000000000";
		tegel[2] = "0";
		
		tafel.plaatsTegel(tegel, new Vector2D(0, 0));
	}

	// Deze methode zal een stapel slim gekozen tegels aanmaken.
	protected void maakStapelTegels(Vector<Tegel> stapel) {
		// TODO Auto-generated method stub
		
	}

	// Deze methode maakt een startveld aan (alleen de start tegel is al geplaatst) 
	protected void maakStartVeld(TafelVerwerker tafel) {
		String[] tegel = new String[3];
		tegel[0] = "wssssssssswws";
		tegel[1] = "1000000000110";
		tegel[2] = "0";
		
		tafel.plaatsTegel(tegel, 
				new Vector2D(0, 0));
	}
	
	// maak een veld aan waarbij de pion niet op de laatste tegel mag staan
	protected void maakVeldGeenPion(TafelVerwerker tafel) {
		String[] tegel = new String[3];
		tegel[0] = "sssssssssssss";
		tegel[1] = "0000000000000";
		tegel[2] = "0";
		
		tafel.plaatsTegel(tegel, 
				new Vector2D(0, 0));
		tafel.plaatsPion(new Vector2D(0, 0), 0, 'r');
		
	}
	
	// maakt een veld aan waarbij een pion op de laatste tegel mag staan
	protected void maakVeldPion(TafelVerwerker tafel) {
		String[] tegel = new String[3];
		tegel[0] = "sssssssssssss";
		tegel[1] = "0000000000000";
		tegel[2] = "0";
		
		tafel.plaatsTegel(tegel, 
				new Vector2D(0, 0));
	}

}

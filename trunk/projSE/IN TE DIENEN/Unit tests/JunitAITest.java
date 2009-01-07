package UnitTests;

import java.util.Vector;

import Core.Vector2D;
import Spelers.Strategy;
import Tafel.Tegel;

import junit.framework.TestCase;

abstract public class JunitAITest extends TestCase {
	protected Strategy ai;
	
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

	// Deze methode zal een veld enereren zodat de te plaatsen tegel (plaatsTegel) maar op 1 positie
	// geplaatst kan worden. GewensteTegelPositie is deze positie.
	protected void maakEnkelVeld(Vector<Vector<Tegel>> veld,
			Tegel plaatsTegel, Vector2D gewensteTegelPositie) {
		
	}

	// Deze methode zal een veld genereren zodat de te plaatsen tegel (plaatsTegel) maar op meerder posities
	// geplaatst kan worden. GewensteTegelPositie is de positie waarnaar onze voorkeur gaat.
	protected void maakOvervloedigVeld(Vector<Vector<Tegel>> veld,
			Tegel plaatsTegel, Vector2D gewensteTegelPositie) {
				
	}

	// Deze methode zal een veld genereren zodat de te plaatsen tegel (plaatsTegel) niet op het veld
	// geplaatst kan worden. GewensteTegelPositie is dus null.
	protected void maakOngeldigVeld(Vector<Vector<Tegel>> veld,
			Tegel plaatsTegel) {
	}

	// Deze methode zal een stapel slim gekozen tegels aanmaken.
	protected void maakStapelTegels(Vector<Tegel> stapel) {
		// TODO Auto-generated method stub
		
	}

	// Deze methode maakt een startveld aan (alleen de start tegel is al geplaatst) 
	protected void maakStartVeld(Vector<Vector<Tegel>> veld, Tegel plaatsTegel,
			Vector2D gewensteTegelPositie) {
		
	}
	
	// maak een veld aan waarbij de pion niet op de laatste tegel mag staan
	protected void maakVeldGeenPion(Vector<Vector<Tegel>> veld,
			Vector2D laatstGeplaatst) {
		// TODO Auto-generated method stub
		
	}
	
	// maakt een veld aan waarbij een pion op de laatste tegel mag staan
	protected void maakVeldPion(Vector<Vector<Tegel>> veld,
			Vector2D laatstGeplaatst, Vector2D gewenstePositie) {
		// TODO Auto-generated method stub
		
	}

}

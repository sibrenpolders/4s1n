package actua;
import java.util.Vector;

import actua.Pion;
import actua.Tafel;
import actua.Tegel;
import actua.Vector2D;

public abstract class Speler {
	private static short DEFAULT_AANTALPIONNEN = 7;
	private String naam;
	private long score;
	private char kleur;
	private Vector<Pion> pionnen;

	public Speler(String naam, char kleur, long score) {
		this.naam = naam;
		this.score = score;
		this.kleur = kleur;
		pionnen = new Vector<Pion>();
		for(int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			pionnen.add(new Pion(kleur));		
	}

	public Pion neemPion (Tafel tafel) {
		return null;
	}

	public Vector2D vraagPositie () {
		return null;
	}

	public abstract boolean plaatsPion (Pion p);

	public void pasScoreAan () {
		
	}

	public boolean neemPionTerug () {
		return false;
	}

	public void draaiTegel (Tegel tegel,boolean richting) {
		
	}

	public boolean vraagRichting () {
		return false;
	}

	public Vector2D vraagCoord () {
		return new Vector2D();
	}

	public void neemTegel (Tegel tegel, Tafel tafel) {
		
	}

	public abstract boolean plaatsTegel (Tegel tegel, Tafel tafel);

	public String getNaam () {
		return "";
	}

	public char getKleur () {
		return 0;
	}

	public long getScore () {
		return 0;
	}
	
	public void setNaam (String naam) {
		
	}

	public void setKleur (char kleur) {
		
	}

	public void setScore (long score) {
		
	}
}

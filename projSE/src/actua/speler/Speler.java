package actua.speler;
import actua.spelDelen.Pion;
import actua.spelDelen.Tafel;
import actua.spelDelen.Tegel;
import actua.types.Vector2D;

public abstract class Speler {
	private String naam;
	private long score;
	private char kleur;

	public Speler() {
		
	}

	public Speler(String naam, char kleur, long score) {
		
	}

	public Pion neemPion () {
		return null;
	}

	public Vector2D vraagPositie () {
		return null;
	}

	public abstract void plaatsPion ();

	public void pasScoreAan () {
		
	}

	public void neemPionTerug () {
		
	}

	public void draaiTegel (boolean richting) {
		
	}

	public boolean vraagRichting () {
		return false;
	}

	public Vector2D vraagCoord () {
		return new Vector2D();
	}

	public void neemTegel (Tegel tegel, Tafel tafel) {
		
	}

	public abstract boolean plaatsTegel (Tegel tegel);

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

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
		for (int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			pionnen.add(new Pion(kleur));
	}

	public Speler() {
		// TODO Auto-generated constructor stub
	}

	public Pion neemPion(Tafel tafel) {
		return null;
	}

	public Vector2D vraagPositie() {
		return null;
	}

	public abstract boolean plaatsPion(Pion p);

	public void pasScoreAan() {

	}

	public boolean neemPionTerug() {
		return false;
	}

	public void draaiTegel(Tegel tegel, boolean richting) {

	}

	public boolean vraagRichting() {
		return false;
	}

	public Vector2D vraagCoord() {
		return new Vector2D();
	}

	public void neemTegel(Tegel tegel, Tafel tafel) {

	}

	public abstract boolean plaatsTegel(Tegel tegel, Tafel tafel);

	public String getNaam() {
		return naam;
	}

	public char getKleur() {
		return kleur;
	}

	public long getScore() {
		return score;
	}

	public void setNaam(String naam) {
		if (naam != null)
			this.naam = naam;
	}

	public void setKleur(char kleur) {
		this.kleur = kleur;
		if (pionnen != null)
			for (int i = 0; i < pionnen.size(); ++i)
				pionnen.get(i).setKleur(kleur);
	}

	public void setScore(long score) {
		this.score = score;
	}

	public void verhoogScoreMet(long nb) {
		score += nb;
	}
}

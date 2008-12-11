package actua;

import java.util.Vector;
import actua.Pion;

public class Speler {
	protected static short DEFAULT_AANTALPIONNEN = 7;
	protected static char INACTIVE_COLOR = 'x';
	protected String naam;
	protected long score;
	protected char kleur;
	protected Vector<Pion> pionnen;

	public Speler(String naam, char kleur, long score) {
		this.naam = naam;
		this.score = score;
		this.kleur = kleur;
		pionnen = new Vector<Pion>();
		for (int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			pionnen.add(new Pion(kleur));
	}

	public Speler(String naam, char kleur) {
		this(naam, kleur, 0);
	}

	public Speler() {
		this("unnamed", INACTIVE_COLOR);
	}

	public boolean isEigenaarVan(Pion p) {
		return p != null && p.getKleur() == this.getKleur();
	}

	public void haalVanTafel(Pion p) {
		if (isEigenaarVan(p))
			p.zetGeplaatst(false);
	}

	public void zetOpTafel(Pion p) {
		if (isEigenaarVan(p))
			p.zetGeplaatst(true);
	}

	public Pion getOngeplaatstePion() {
		for (int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			if (pionnen.get(i).getGeplaatst() == false)
				return pionnen.get(i);
		return null;
	}

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

	protected Vector<Pion> getPionnen() {
		return pionnen;
	}

	public void verwijder() {
		setKleur(INACTIVE_COLOR);
	}
}

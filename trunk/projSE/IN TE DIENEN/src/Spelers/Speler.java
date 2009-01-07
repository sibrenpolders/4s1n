package Spelers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Speler implements Serializable {
	private static final char INACTIVE_COLOR = 'x';
	private static final long serialVersionUID = -5478932661892837977L;
	protected static short DEFAULT_AANTALPIONNEN = 7;
	protected String naam;
	protected long score;
	protected char kleur;
	protected ArrayList<Pion> pionnen;

	public Speler(String naam, char kleur, long score) {
		this.naam = naam;
		this.score = score;
		this.kleur = kleur;
		pionnen = new ArrayList<Pion>();
		for (int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			pionnen.add(new Pion(kleur));
	}

	public Speler(String naam, char kleur) {
		this(naam, kleur, 0);
	}

	// GETTERS en SETTERS

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

	public void verwijder() {
		setKleur(INACTIVE_COLOR);
	}

	// PIONNEN

	public boolean isEigenaarVan(Pion p) {
		return p != null && p.getKleur() == this.getKleur();
	}

	public boolean ongeplaatstePionAanwezig() {
		for (int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			if (pionnen.get(i).getGeplaatst() == false)
				return true;
		return false;
	}

	public boolean plaatsOngeplaatstePion() {
		for (int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			if (pionnen.get(i).getGeplaatst() == false) {
				pionnen.get(i).toggleGeplaatst();
				return true;
			}
		return false;
	}

	public boolean neemGeplaatstePionTerug() {
		for (int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			if (pionnen.get(i).getGeplaatst() == true) {
				pionnen.get(i).toggleGeplaatst();
				return true;
			}
		return false;
	}

	public short getAantalOngeplaatstePion() {
		short result = 0;
		for (int i = 0; i < DEFAULT_AANTALPIONNEN; ++i)
			if (pionnen.get(i).getGeplaatst() == false)
				result++;
		return result;
	}

	protected ArrayList<Pion> getPionnen() {
		return pionnen;
	}

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(naam);
		out.writeLong(score);
		out.writeChar(kleur);
		out.writeObject(pionnen);
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		naam = (String) in.readObject();
		score = in.readLong();
		kleur = in.readChar();
		pionnen = (ArrayList<Pion>) in.readObject();
	}
}

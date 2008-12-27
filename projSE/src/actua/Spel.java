package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Spel extends Observable implements Serializable {
	private static final long serialVersionUID = 2689906234458876556L;
	private TafelVerwerker tafelVerwerker;
	private SpelerVerwerker spelerVerwerker;
	private boolean pionGeplaatst = false;

	public static final char ROOD = 'r';
	public static final char BLAUW = 'b';
	public static final char WIT = 'w';
	public static final char ORANJE = 'o';
	public static final char GEEL = 'g';
	public static final short MAXAANTALSPELERS = 5;
	public static final short MINAANTALSPELERS = 2;

	// OBSERVERBERICHTEN

	public static final String SPELERVERANDERD = "Speler veranderd";
	public static final String HUIDIGESPELERVERANDERD = "Huidige speler veranderd";
	public static final String SPELERVERWIJDERD = "Speler verwijderd";
	public static final String SPELERTOEGEVOEGD = "Speler toegevoegd";
	public static final String TEGELGEPLAATST = "Tegel toegevoegd";
	public static final String PIONGEPLAATST = "Pion toegevoegd";
	public static final String PIONTERUGGENOMEN = "Pion verwijderd";

	public Spel() {
		pionGeplaatst = false;
		tafelVerwerker = new TafelVerwerker();
		spelerVerwerker = new SpelerVerwerker();
	}

	public void restart() {
		pionGeplaatst = false;
		spelerVerwerker.verwijderSpelers();
		tafelVerwerker.restart();
		setChanged();
		notifyObservers(SPELERVERWIJDERD);
	}

	// SPELERSGROEP

	public void volgendeSpeler() {
		pionGeplaatst = false;
		spelerVerwerker.volgendeSpeler();
		notifyObservers(HUIDIGESPELERVERANDERD);
	}

	public void voegSpelerToe(short s, String naam, char kleur, int i) {
		spelerVerwerker.voegSpelerToe(s, naam, kleur, i, tafelVerwerker);
		setChanged();
		notifyObservers(SPELERTOEGEVOEGD);
	}

	public int geefAantalSpelers() {
		return spelerVerwerker.geefAantalSpelers();
	}

	public char geefKleurVanSpeler(int i) {
		return spelerVerwerker.geefKleurVanSpeler(i);
	}

	public char geefHuidigeSpeler() {
		return spelerVerwerker.geefHuidigeSpeler();
	}

	// SPELER

	public String geefSpelerNaam(char kleur) {
		return spelerVerwerker.geefSpelerNaam(kleur);
	}

	public long geefSpelerScore(char kleur) {
		return spelerVerwerker.geefSpelerScore(kleur);
	}

	public short geefAantalOngeplaatstePionnen(char kleur) {
		return spelerVerwerker.geefAantalOngeplaatstePionnen(kleur);
	}

	// STAPEL

	public String[] geefStartTegel() {
		return tafelVerwerker.geefStartTegel();
	}

	public String[] vraagNieuweTegel() {
		return tafelVerwerker.vraagNieuweTegel();
	}

	public String[] neemTegelVanStapel() {
		return tafelVerwerker.neemTegelVanStapel();
	}

	public void legTerugEinde(String[] tegel) {
		tafelVerwerker.legTerugEinde(tegel);
	}

	public int getStapelSize() {
		return tafelVerwerker.getStapelSize();
	}

	// TEGEL + TEGELPLAATSING

	public ArrayList<Vector2D> geefGeldigeMogelijkheden(String[] tegel) {
		return tafelVerwerker.geefGeldigeMogelijkheden(tegel);
	}

	public boolean plaatsTegel(String[] tegel, Vector2D coord) {
		return tafelVerwerker.plaatsTegel(tegel, coord);
	}

	public boolean isTegelPlaatsingGeldig(String[] tegel, Vector2D coord) {
		return tafelVerwerker.isTegelPlaatsingGeldig(tegel, coord);
	}

	public Vector2D getStartTegelPos() {
		return tafelVerwerker.getStartTegelPositie();
	}

	// PION + PIONPLAATSING

	public boolean pionBijBeurtReedsGeplaatst() {
		return pionGeplaatst;
	}

	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord) {
		return plaatsPion(tegelCoord, pionCoord, spelerVerwerker
				.geefHuidigeSpeler());
	}

	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord, char speler) {
		if (spelerVerwerker.isHuidigeSpeler(speler) && !pionGeplaatst) {
			boolean pion = spelerVerwerker.neemPionVan(speler);
			if (pion != false) {
				pionGeplaatst = tafelVerwerker.plaatsPion(tegelCoord,
						pionCoord, speler);
				if (pionGeplaatst)
					spelerVerwerker.plaatsPionVan(speler);
				return pionGeplaatst;
			}
		}

		return false;
	}
	
	public boolean undo() {
		return false;
	}
	
	public boolean redo() {
		return false;
	}
	
	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(tafelVerwerker);
		out.writeObject(spelerVerwerker);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		tafelVerwerker = (TafelVerwerker) in.readObject();
		spelerVerwerker = (SpelerVerwerker) in.readObject();
	}

	private void readObjectNoData() throws ObjectStreamException {

	}	
}

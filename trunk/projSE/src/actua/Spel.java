package actua;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

public class Spel extends Observable implements Serializable {
	private static final long serialVersionUID = 2689906234458876556L;
	private TafelVerwerker tafelVerwerker;
	private SpelerVerwerker spelerVerwerker;
	private PuntenVerwerker puntenVerwerker;
	private StatusBijhouder statusBijhouder;
	private boolean pionGeplaatst = false;
	private Vector2D tegelGeplaatst;
	private boolean isUitgespeeld;
	private ArrayList<SpelBeurtResultaat> laatsteAIZet;

	public static final char ROOD = 'r';
	public static final char BLAUW = 'b';
	public static final char WIT = 'w';
	public static final char ORANJE = 'o';
	public static final char GEEL = 'g';
	public static final short MAXAANTALSPELERS = 5;
	public static final short MINAANTALSPELERS = 2;
	public static final int TEGEL_PRESENTATIE = TafelVerwerker.TEGEL_PRESENTATIE;
	public static final int ID_PRESENTATIE = TafelVerwerker.ID_PRESENTATIE;
	public static final int ORIENTATIE = TafelVerwerker.ORIENTATIE;

	// OBSERVERBERICHTEN

	public static final String HUIDIGESPELERVERANDERD = "Huidige speler veranderd";
	public static final String SPELERVERWIJDERD = "Speler verwijderd";
	public static final String SPELERTOEGEVOEGD = "Speler toegevoegd";
	public static final String SPELGEDAAN = "Spel is gedaan";

	public Spel() {
		pionGeplaatst = false;
		isUitgespeeld = false;
		tegelGeplaatst = null;
		tafelVerwerker = new TafelVerwerker();
		puntenVerwerker = new PuntenVerwerker();
		spelerVerwerker = new SpelerVerwerker();
		statusBijhouder = new StatusBijhouder();
		laatsteAIZet = new ArrayList<SpelBeurtResultaat>();
	}

	public void restart(int aantalTegels) {
		pionGeplaatst = false;
		isUitgespeeld = false;
		tegelGeplaatst = null;
		laatsteAIZet.clear();
		spelerVerwerker.verwijderSpelers();
		tafelVerwerker.restart(aantalTegels);
		puntenVerwerker = new PuntenVerwerker();
		statusBijhouder = new StatusBijhouder();
		setChanged();
		notifyObservers(SPELERVERWIJDERD);
	}

	public boolean isUitgespeeld() {
		return isUitgespeeld;
	}

	// SPELERSGROEP

	// Alle AI-spelers doen hun zet, om dan bij de eerste
	// menselijke speler te stoppen.
	public void volgendeSpeler() {
		updateScore(tegelGeplaatst);

		if (tafelVerwerker.getStapelSize() > 0) {

			pionGeplaatst = false;
			tegelGeplaatst = null;
			laatsteAIZet.clear();
			spelerVerwerker.gaNaarVolgendeSpeler();
			while (spelerVerwerker.isHuidigeSpelerAI() && !isUitgespeeld) {
				laatsteAIZet.add(spelerVerwerker
						.geefResultaatAI(tafelVerwerker));
				if (tafelVerwerker.getStapelSize() == 0)
					isUitgespeeld = true;

				spelerVerwerker.gaNaarVolgendeSpeler();
			}

			for (int i = 0; i < laatsteAIZet.size(); ++i)
				updateScore(laatsteAIZet.get(i).getPlaatsTegel());
		} else {
			isUitgespeeld = true;
		}

		if (isUitgespeeld) {
			this.setChanged();
			notifyObservers(SPELGEDAAN);
		} else {
			this.setChanged();
			notifyObservers(HUIDIGESPELERVERANDERD);
		}
	}

	public void updateScore(Vector2D coord) {
		if (coord != null) {
			ArrayList<Character> updates = tafelVerwerker.updateScore(coord,
					puntenVerwerker);
			for (int i = 0; i < updates.size(); ++i)
				spelerVerwerker.neemPionTerugVan(updates.get(i));

			spelerVerwerker.setSpelerScore(BLAUW, puntenVerwerker
					.getSpelerBlauw());
			spelerVerwerker.setSpelerScore(GEEL, puntenVerwerker
					.getSpelerGeel());
			spelerVerwerker.setSpelerScore(WIT, puntenVerwerker.getSpelerWit());
			spelerVerwerker.setSpelerScore(ORANJE, puntenVerwerker
					.getSpelerOranje());
			spelerVerwerker.setSpelerScore(ROOD, puntenVerwerker
					.getSpelerRood());
		}
	}

	public char geefWinnaar() {
		return spelerVerwerker.geefWinnaar();
	}

	public void voegSpelerToe(short s, String naam, char kleur, int score) {
		spelerVerwerker.voegSpelerToe(s, naam, kleur, score);
		setChanged();
		notifyObservers(SPELERTOEGEVOEGD);
	}

	public void verwijderHuidigeSpeler(short niveau) {
		spelerVerwerker.maakAI(spelerVerwerker.geefHuidigeSpeler(), niveau);
	}

	public boolean zijnKleurenVoorNieuweSpelersGeldig(Vector<Character> kleur) {
		return spelerVerwerker.zijnKleurenVoorNieuweSpelersGeldig(kleur);
	}

	public boolean zijnNamenVoorNieuweSpelersGeldig(Vector<String> naam) {
		return spelerVerwerker.zijnNamenVoorNieuweSpelersGeldig(naam);
	}

	public boolean isAantalSpelersGeldig(Vector<String> naam) {
		return naam.size() >= MINAANTALSPELERS
				&& naam.size() <= MAXAANTALSPELERS;
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

	public boolean isHuidigeSpelerAI() {
		return spelerVerwerker.isHuidigeSpelerAI();
	}

	public ArrayList<SpelBeurtResultaat> geefResultaatAI() {
		return laatsteAIZet;
	}

	// SPELER

	public String geefSpelerNaam(char kleur) {
		return spelerVerwerker.geefSpelerNaam(kleur);
	}

	public long geefSpelerScore(char kleur) {
		return spelerVerwerker.geefSpelerScore(kleur);
	}

	public short geefAantalOngeplaatstePionnenVanSpeler(char kleur) {
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

	public void legTegelTerugEindeStapel(String[] tegel) {
		tafelVerwerker.legTerugEinde(tegel);
	}

	public int getStapelSize() {
		return tafelVerwerker.getStapelSize();
	}

	// TEGEL + TEGELPLAATSING

	public boolean heeftHuidigeSpelerTegelGeplaatst() {
		return tegelGeplaatst != null;
	}

	public boolean plaatsTegel(String[] tegel, Vector2D coord) {
		if (tafelVerwerker.plaatsTegel(tegel, coord) == true) {
			tegelGeplaatst = new Vector2D(coord.getX(), coord.getY());
			return true;
		}
		return false;
	}

	public boolean isTegelPlaatsbaar(String[] tegel) {
		return tafelVerwerker.isTegelPlaatsbaar(tegel);
	}

	public boolean isTegelPlaatsingGeldig(String[] tegel, Vector2D coord) {
		return tafelVerwerker.isTegelPlaatsingGeldig(tegel, coord);
	}

	public ArrayList<Vector2D> geefMogelijkeZettenVoorTegel() {
		return tafelVerwerker.geefMogelijkeZettenVoorHuidigeTegel();
	}

	// PION + PIONPLAATSING

	public boolean pionBijBeurtReedsGeplaatst() {
		return pionGeplaatst;
	}

	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord) {
		char speler = spelerVerwerker.geefHuidigeSpeler();
		if (spelerVerwerker.isHuidigeSpeler(speler) && !pionGeplaatst
				&& tegelGeplaatst != null
				&& tegelGeplaatst.getX() == tegelCoord.getX()
				&& tegelGeplaatst.getY() == tegelCoord.getY()) {
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

	public boolean isPionPlaatsingGeldig(String[] t, Vector2D tegelCoord,
			int pionCoord) {
		return tafelVerwerker.isPionPlaatsingGeldig(tegelCoord, pionCoord);
	}

	public boolean isPionGeplaatst(Vector2D tegelCoord, int pionCoord) {
		return tafelVerwerker.isPionGeplaatst(tegelCoord, pionCoord);
	}

	public char geefPionKleur(Vector2D tegelCoord, int pionCoord) {
		return tafelVerwerker.geefPionKleur(tegelCoord, pionCoord);
	}

	public void verwijderPion(Vector2D tegelCoord, int pionCoord) {
		tafelVerwerker.verwijderPion(tegelCoord, pionCoord);
	}

	public boolean[] getUniekeLandsdeelPosities(Vector2D tegelCoord) {
		return tafelVerwerker.getUniekeLandsdeelPosities(tegelCoord);
	}

	// UNDO

	public boolean undo() {
		if (statusBijhouder.pop_undo() == null) {
			return false;
		} else {
			// TODO Spel.undo
			return true;
		}
	}

	public boolean redo() {
		if (statusBijhouder.pop_redo() == null) {
			return false;
		} else {
			// TODO Spel.redo
			return true;
		}
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

	// elke voor unittesten
	public TafelVerwerker getTafelVerwerker() {
		return tafelVerwerker;
	}
	
	
}

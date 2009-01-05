package actua;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class TafelVerwerker implements Serializable {
	public static final int TEGEL_PRESENTATIE = Tafel.TEGEL_PRESENTATIE;
	public static final int ID_PRESENTATIE = Tafel.ID_PRESENTATIE;
	public static final int ORIENTATIE = Tafel.ORIENTATIE;

	private static final long serialVersionUID = -6431108242978335095L;
	private static final int AANTAL_TEGELS = 72;
	private int aantalTegels;
	private ArrayDeque<String[]> stapel;
	private Tafel tafel;
	private String[] startTegel;

	public TafelVerwerker() {
		this(AANTAL_TEGELS);
	}

	public TafelVerwerker(int aantalTegels) {
		this.aantalTegels = aantalTegels;
		restart(aantalTegels);
	}

	public void restart() {
		restart(AANTAL_TEGELS);
	}

	public void restart(int aantalTegels) {
		TegelFabriek tfb = new TegelFabriek();
		stapel = tfb.maakTegelDeque(aantalTegels - 1);
		tafel = new Tafel();
		startTegel = tfb.geefStartTegel();
		tafel.setStartTegel(startTegel);
	}

	public String[] geefStartTegel() {
		return startTegel;
	}

	public int geefAantalTegels() {
		return aantalTegels;
	}

	// STAPEL

	public ArrayDeque<String[]> getStapel() {
		return stapel;
	}

	public int getStapelSize() {
		return stapel.size();
	}

	public String[] vraagNieuweTegel() {
		return stapel.peekFirst();
	}

	public String[] neemLaatsteTegel() {
		return stapel.pollFirst();
	}

	public String[] neemTegelVanStapel() {
		return stapel.pop();
	}

	public void legTerugEinde(String[] tegel) {
		stapel.addLast(tegel);
	}

	public void legTerugTop(String[] tegel) {
		stapel.addFirst(tegel);
	}

	// TEGELVELD

	public int getHoogte() {
		return tafel.getHoogte();
	}

	public int getBreedte() {
		return tafel.getBreedte();
	}

	public int aantalTegels() {
		return stapel.size();
	}

	// TEGELPLAATSING

	/**
	 * Zal de method aanroepen die een tegel op het veld plaatst
	 * 
	 * @param tegel
	 *            De tegel die geplaatst moet worden
	 * @param coord
	 *            De positie op het veld waar tegel geplaatst moet worden
	 *            (relatief t.o.v. de starttegel)
	 * @return True indien de tegel geplaatst kon worden False anders
	 */
	public boolean plaatsTegel(String[] tegel, Vector2D coord) {
		return tafel.plaatsTegel(tegel, coord);
	}

	public boolean isTegelPlaatsingGeldig(String[] t, Vector2D coord) {
		return tafel.isTegelPlaatsingGeldig(t, coord);
	}

	public boolean isTegelPlaatsbaar(String[] t) {
		Tegel tegel = new Tegel(t[0], t[1], Short.parseShort(t[2]));

		for (int i = 0; i < 4; ++i) {
			tegel.draaiTegel(true);
			if (geefMogelijkeZettenVoorGegevenTegel(tegel.getTegelString())
					.size() != 0)
				return true;
		}

		return false;
	}

	public ArrayList<Vector2D> geefMogelijkeZettenVoorGegevenTegel(
			String[] tegel) {
		return tafel.geefMogelijkeZetten(tegel);
	}

	public ArrayList<Vector2D> geefMogelijkeZettenVoorHuidigeTegel() {
		String[] tegel = vraagNieuweTegel();
		return tafel.geefMogelijkeZetten(tegel);
	}

	// PIONPLAATSING

	public boolean isPionPlaatsingGeldig(Vector2D tegelCoord, int pionCoord) {
		return tafel.isPionPlaatsingGeldig(tegelCoord, pionCoord);
	}

	/**
	 * @param tegelCoord
	 *            De tegel waarop de pion geplaatst moet worden (coordinaten
	 *            zijn relatief t.o.v. de starttegel
	 * @param pionCoord
	 *            plaats op de tegel waarop de pion geplaatst moet worden.
	 * @param pion
	 *            pion die geplaatst moet worden.
	 */
	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord, char pion) {
		return tafel.plaatsPion(tegelCoord, pionCoord, pion);
	}

	public boolean isPionGeplaatst(Vector2D tegelCoord, int pionCoord) {
		return tafel.isPionGeplaatst(tegelCoord, pionCoord);
	}

	public char geefPionKleur(Vector2D tegelCoord, int pionCoord) {
		return tafel.geefPionKleur(tegelCoord, pionCoord);
	}

	public void verwijderPion(Vector2D tegelCoord, int pionCoord) {
		tafel.verwijderPion(tegelCoord, pionCoord);
	}

	public boolean[] getUniekeLandsdeelPosities(Vector2D tegelCoord) {
		return tafel.getUniekeLandsdeelPosities(tegelCoord);
	}

	public ArrayList<Character> updateScore(Vector2D coord,
			PuntenVerwerker puntenVerwerker) {
		if (puntenVerwerker != null && tafel != null) {
			ArrayList<Character> result = tafel.updateScore(coord,
					puntenVerwerker);
			return result;
		} else
			return null;
	}

	public void undo() {
		tafel.undo();
	}

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(stapel);
		out.writeObject(tafel);
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		stapel = (ArrayDeque<String[]>) in.readObject();
		tafel = (Tafel) in.readObject();
	}
}
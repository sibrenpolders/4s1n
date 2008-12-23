package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class TafelVerwerker implements Serializable {
	private static final long serialVersionUID = -6431108242978335095L;
	private static final int AANTAL_TEGELS = 72;
	private ArrayDeque<String[]> stapel;
	private Tafel tafel;
	private String[] startTegel;

	public TafelVerwerker() {
		this(AANTAL_TEGELS);
	}

	public TafelVerwerker(int aantalTegels) {
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

	public Vector2D getBeginPositie() {
		return tafel.getBeginPositie();
	}

	public int getHoogte() {
		return tafel.getHoogte();
	}

	public int getBreedte() {
		return tafel.getBreedte();
	}

	public int aantalTegels() {
		return stapel.size();
	}

	public Tegel getLaatstGeplaatsteTegel() {
		return tafel.getLaatstGeplaatsteTegel();
	}

	public Vector2D getStartTegelPositie() {
		return tafel.getStartTegel();
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

	public boolean isTegelPlaatsBaar(Tegel tegel) {

		return true;
	}

	public ArrayList<Vector2D> geefGeldigeMogelijkheden(String[] tegel) {
		return null;
	}

	// PIONPLAATSING

	public boolean isPionPlaatsingGeldig(String[] t, Vector2D tegelCoord,
			int pionCoord) {
		return tafel.isPionPlaatsingGeldig(t, tegelCoord, pionCoord);
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
	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord, Pion pion) {
		return tafel.plaatsPion(tegelCoord, pionCoord, pion);
	}

	public boolean plaatsingPionMogelijk(String[] t, Vector2D tegelCoord,
			int pionCoord) {
		return tafel.isPionPlaatsingGeldig(t, tegelCoord, pionCoord);
	}

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(stapel);
		out.writeObject(tafel);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		stapel = (ArrayDeque<String[]>) in.readObject();
		tafel = (Tafel) in.readObject();
	}

	private void readObjectNoData() throws ObjectStreamException {

	}
}
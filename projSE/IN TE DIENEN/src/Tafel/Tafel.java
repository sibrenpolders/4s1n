package Tafel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import Core.PuntenVerwerker;
import Core.Vector2D;

public class Tafel implements Serializable {
	public static final int TEGEL_PRESENTATIE = TegelVeld.TEGEL_PRESENTATIE;
	public static final int ID_PRESENTATIE = TegelVeld.ID_PRESENTATIE;
	public static final int ORIENTATIE = TegelVeld.ORIENTATIE;
	private static final long serialVersionUID = -1767380269715221020L;
	private static final int NOORD = 0;
	private static final int OOST = 1;
	private static final int ZUID = 2;
	private static final int WEST = 3;

	private TegelVeld veld;

	public Tafel() {
		clear();
		veld = new TegelVeld();
	}

	public void clear() {
		veld = null;
	}

	// GETTERS en SETTERS

	/**
	 * Geeft de positie van de start tegel terug
	 * 
	 * @return
	 */
	public Vector2D getStartTegel() {
		return veld.getStartTegel();
	}

	public void setStartTegel(String[] tegel) {
		plaatsTegel(tegel, new Vector2D(0, 0));
	}

	private Vector2D getBeginPositie() {
		Vector2D startTegel = veld.getStartTegel();
		return new Vector2D(-startTegel.getX(), -startTegel.getY());
	}

	public int getHoogte() {
		return veld.getSize();
	}

	public int getBreedte() {
		return veld.getBreedte();
	}

	/**
	 * Deze functie zal nagaan of de tegel met coördinaten (rij, kolom) de
	 * laatst geplaatste tegel is.
	 * 
	 * @param rij
	 *            Het rijnummer van de tegel.
	 * @param kolom
	 *            Het kolomnummer van de tegel.
	 * @return True als dit de coördinaten zijn van de laatstgeplaatste tegel,
	 *         False anders.
	 */
	public boolean isLaatstGeplaatsteTegel(int rij, int kolom) {
		return veld.isLaatstGeplaatsteTegel(rij, kolom);
	}

	public String[] getTegelPresentatie(Vector2D coord) {
		if (coord == null) {
			return null;
		}

		Tegel tegel = (Tegel) veld.getTegelAtRelativeCoords(coord);

		if (tegel != null) {
			String[] retString = new String[2];
			retString[0] = tegel.getTegelPresentatie();
			retString[1] = tegel.getIdPresentatie();
			return retString;
		}

		return null;
	}

	@SuppressWarnings( { "unused" })
	public Tegel bepaalTegel(Vector2D coord) {
		return (Tegel) veld.getTegelAtRelativeCoords(coord);
	}

	// TEGELPLAATSING

	public ArrayList<Vector2D> geefMogelijkeZetten(String[] t) {
		ArrayList<Vector2D> mogelijkeZetten = new ArrayList<Vector2D>();

		Vector2D coordsStartTegel = getBeginPositie();
		int rijMin = coordsStartTegel.getX() - 1;
		int kolomMin = coordsStartTegel.getY() - 1;
		int rijMax = getHoogte() + coordsStartTegel.getX();
		int kolomMax = getBreedte() + coordsStartTegel.getY();

		for (int i = rijMin; i <= rijMax; ++i) {
			for (int j = kolomMin; j <= kolomMax; ++j) {
				Vector2D tmp = new Vector2D(i, j);
				if (isTegelPlaatsingGeldig(t, tmp)) {
					mogelijkeZetten.add(tmp);
				}
			}
		}

		return mogelijkeZetten;
	}

	/**
	 * Zal een tegel op het speelveld plaatsen op de coördinaten gegeven door
	 * coord.
	 * 
	 * @param t
	 *            De tegel die op de tafel gelegd moet worden.
	 * @param coord
	 *            De coördinaten van de tegel.
	 * @return Geeft true als de tegel geplaatst is. False als de tegel niet
	 *         geplaatst kan worden
	 */
	public boolean plaatsTegel(String[] t, Vector2D coord) {
		// startTegel wordt gezet
		// coord maken niet uit startTegel staat op (0, 0)
		Tegel tegel = new Tegel(t[TEGEL_PRESENTATIE], t[ID_PRESENTATIE], Short
				.parseShort(t[ORIENTATIE]));

		return veld.plaatsTegel(tegel, coord);
	}

	/**
	 * Deze functie zal nagaan of tegel op positie coord geplaatst kan worden.
	 * De tegel wordt hierbij niet echt op het veld gezet.
	 * 
	 * @param tegel
	 *            De tegel die geplaatst moet worden.
	 * @param coord
	 *            De coördinaten waar de tegel geplaatst moet worden.
	 * @return True indien de tegelplaatsing mogelijk is, False anders.
	 */
	public boolean isTegelPlaatsingGeldig(String[] t, Vector2D coord) {
		Tegel tegel = new Tegel(t[TEGEL_PRESENTATIE], t[ID_PRESENTATIE], Short
				.parseShort(t[ORIENTATIE]));
		return veld.isTegelPlaatsingGeldig(tegel, coord);
	}

	private boolean alGechecked(ArrayList<Tegel> checked, Tegel tegel) {
		for (int i = 0; i < checked.size(); ++i) {
			if (checked.get(i) == tegel) {
				return true;
			}
		}

		return false;
	}

	// PIONPLAATSING

	/**
	 * Zal indien mogelijk pion plaatsen op de tegel met coï¿½rdinaten
	 * tegelCoord. pionCoord zal bepalen op welk deel de pion zal staan.
	 * 
	 * @param tegelCoord
	 *            coordinaten van de tegel waar een pion op geplaatst zal
	 *            worden.
	 * @param pionCoord
	 *            coordinaten van het landsdeel op de tegel waar de pion zal
	 *            geplaatst worden.
	 * @param pion
	 *            de pion die zal geplaatst worden.
	 * @return True als de pion geplaatst is, False als hij niet geplaatst kan
	 *         worden.
	 */
	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord, char pion) {
		Tegel t = veld.getTegelAtRelativeCoords(tegelCoord);

		if (t != (Tegel) veld.getLaatstGeplaatsteTegel()) {
			return false;
		}

		return t != null && isPionPlaatsingGeldig(t, tegelCoord, pionCoord)
				&& t.plaatsPion(pionCoord, pion);
	}

	public boolean isPionGeplaatst(Vector2D tegelCoord, int pionCoord) {
		Tegel t = bepaalTegel(tegelCoord);
		if (t != null)
			return t.isPionGeplaatst(pionCoord);
		else
			return false;
	}

	public char geefPionKleur(Vector2D tegelCoord, int pionCoord) {
		Tegel t = bepaalTegel(tegelCoord);
		return t.geefPionKleur(pionCoord);
	}

	public void verwijderPion(Vector2D tegelCoord, int pionCoord) {
		Tegel t = bepaalTegel(tegelCoord);
		t.verwijderPion(pionCoord);
	}

	public boolean[] getUniekeLandsdeelPosities(Vector2D tegelCoord) {
		Tegel t = bepaalTegel(tegelCoord);
		return t.getUniekeLandsdeelPosities();
	}

	/**
	 * Deze functie zal nagaan of een pion plaatsing kan gebeuren op tegel met
	 * coördinaten tegelCoord op het landsdeel met coördinaten pionCoord. Deze
	 * functie zal niet de pion echt gaan plaatsen op de tegel.
	 * 
	 * @param t
	 *            De tegel waarop de pion geplaatst moet worden.
	 * @param tegelCoord
	 *            De coördinaten van de tegel waarop de pion geplaatst moet
	 *            worden.
	 * @param pionCoord
	 *            De coördinaten van het landsdeel waarop de pion geplaatst moet
	 *            worden.
	 * @return True als de pion geplaatst kan worden, False anders.
	 */
	private boolean isPionPlaatsingGeldig(Tegel tegel, Vector2D tegelCoord,
			int pionCoord) {
		ArrayList<Tegel> checked = new ArrayList<Tegel>();
		return isPionPlaatsingGeldig(pionCoord, tegel, tegelCoord, checked);
	}

	public boolean isPionPlaatsingGeldig(Vector2D tegelCoord, int pionCoord) {
		ArrayList<Tegel> checked = new ArrayList<Tegel>();
		Tegel tegel = (Tegel) veld.getTegelAtRelativeCoords(tegelCoord);
		return isPionPlaatsingGeldig(pionCoord, tegel, tegelCoord, checked);
	}

	private boolean isPionPlaatsingGeldig(int pionCoord, Tegel tegel,
			Vector2D veldCoord, ArrayList<Tegel> checked) {
		if (tegel == null || alGechecked(checked, tegel)) {
			return true;
		}
		if (pionCoord < 0 || pionCoord >= Tegel.MAX_GROOTTE
				|| tegel.isPionGeplaatst(pionCoord)) {
			return false;
		}

		checked.add(tegel);

		Tegel[] buren = (Tegel[]) veld.getBuren(veldCoord);
		Landsdeel matchLandsdeel = tegel.bepaalLandsdeel(pionCoord);

		boolean pionPlaatsingGeldig = !matchLandsdeel.isPionGeplaatst();

		// de pion coordinaat van de buur waarmee er vergeleken zal worden
		int buurPionCoord;
		int buurNr;
		Vector2D buurCoord;

		for (int i = 0; pionPlaatsingGeldig && i < Tegel.MAX_GROOTTE; ++i) {
			if (tegel.bepaalLandsdeel(i) == matchLandsdeel) {
				buurPionCoord = getBuurPionCoord(i);
				buurNr = getBuurNr(i);
				buurCoord = getBuurCoord(buurNr, veldCoord);
				if (buurNr != -1) {
					// Tegel.MIDDEN moet niet verder gecontroleerd worden
					pionPlaatsingGeldig = isPionPlaatsingGeldig(buurPionCoord,
							buren[buurNr], buurCoord, checked);
				}

				if (tegel.isPionGeplaatst(i)) {
					pionPlaatsingGeldig = false;
				}
			}
		}

		return pionPlaatsingGeldig;
	}

	private Vector2D getBuurCoord(int buurNr, Vector2D veldCoord) {
		Vector2D buurCoord = null;

		switch (buurNr) {
		case NOORD: // NOORD BUUR
			buurCoord = new Vector2D(veldCoord.getX() - 1, veldCoord.getY());
			break;
		case OOST: // OOST BUUR
			buurCoord = new Vector2D(veldCoord.getX(), veldCoord.getY() + 1);
			break;
		case ZUID: // ZUID BUUR
			buurCoord = new Vector2D(veldCoord.getX() + 1, veldCoord.getY());
			break;
		case WEST: // WEST BUUR
			buurCoord = new Vector2D(veldCoord.getX(), veldCoord.getY() - 1);
			break;
		}

		return buurCoord;
	}

	private int getBuurNr(int i) {
		int buurNr = -1;

		switch (i) {
		case Tegel.NOORD_WEST:
		case Tegel.NOORD:
		case Tegel.NOORD_OOST:
			buurNr = NOORD;
			break;
		case Tegel.OOST_NOORD:
		case Tegel.OOST:
		case Tegel.OOST_ZUID:
			buurNr = OOST;
			break;
		case Tegel.ZUID_OOST:
		case Tegel.ZUID:
		case Tegel.ZUID_WEST:
			buurNr = ZUID;
			break;
		case Tegel.WEST_NOORD:
		case Tegel.WEST:
		case Tegel.WEST_ZUID:
			buurNr = WEST;
			break;
		}

		return buurNr;
	}

	private int getBuurPionCoord(int i) {
		int buurPionCoord = -1;

		switch (i) {
		case Tegel.NOORD_WEST:
			buurPionCoord = Tegel.ZUID_WEST;
			break;
		case Tegel.NOORD:
			buurPionCoord = Tegel.ZUID;
			break;
		case Tegel.NOORD_OOST:
			buurPionCoord = Tegel.ZUID_OOST;
			break;
		case Tegel.OOST_NOORD:
			buurPionCoord = Tegel.WEST_NOORD;
			break;
		case Tegel.OOST:
			buurPionCoord = Tegel.WEST;
			break;
		case Tegel.OOST_ZUID:
			buurPionCoord = Tegel.WEST_ZUID;
			break;
		case Tegel.ZUID_OOST:
			buurPionCoord = Tegel.NOORD_OOST;
			break;
		case Tegel.ZUID:
			buurPionCoord = Tegel.NOORD;
			break;
		case Tegel.ZUID_WEST:
			buurPionCoord = Tegel.NOORD_WEST;
			break;
		case Tegel.WEST_NOORD:
			buurPionCoord = Tegel.OOST_NOORD;
			break;
		case Tegel.WEST:
			buurPionCoord = Tegel.OOST;
			break;
		case Tegel.WEST_ZUID:
			buurPionCoord = Tegel.OOST_ZUID;
			break;
		}
		return buurPionCoord;
	}

	public ArrayList<Character> updateScore(Vector2D coord,
			PuntenVerwerker puntenVerwerker) {
		if (puntenVerwerker != null && veld != null)
			return puntenVerwerker.updateScore(coord, veld);
		else
			return null;
	}

	/**
	 * Deze functie zal het veld 1 zet terugdoen. Hiervoor heeft hij een
	 * referentie nodig naar de vorige laatst geplaatste tegel.
	 * 
	 * @param laatstGeplaatsteTegel
	 *            Referentie naar de vorige laatst geplaatste tegel.
	 */
	public void undo() {
		veld.undo();
	}

	public void redo() {

	}

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(veld);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		veld = (TegelVeld) in.readObject();
	}
}

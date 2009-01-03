package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;

public class TegelVeld implements Serializable {
	private static final int NOORD = 0;
	private static final int OOST = 1;
	private static final int ZUID = 2;
	private static final int WEST = 3;
	private static final long serialVersionUID = 6148474637558416899L;
	private Tegel laatstGeplaatsteTegel;
	private ArrayList<ArrayList<Tegel>> veld;
	private static final int TEGEL_PRESENTATIE = 0;
	private static final int ID_PRESENTATIE = 1;
	private static final int ORIENTATIE = 2;
	private Vector2D startTegel;

	public TegelVeld() {
		veld = new ArrayList<ArrayList<Tegel>>();
	}

	// GETTERS en SETTERS

	private void setLaatstGeplaatsteTegel(Tegel laatstGeplaatsteTegel) {
		this.laatstGeplaatsteTegel = laatstGeplaatsteTegel;
	}

	/**
	 * Zal de laatstgeplaatste tegel teruggeven.
	 * 
	 * @return laatstGeplaatste tegel
	 */
	public Tegel getLaatstGeplaatsteTegel() {
		return laatstGeplaatsteTegel;
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
	public boolean isLaatste(int rij, int kolom) {
		return get(new Vector2D(rij, kolom)) == laatstGeplaatsteTegel;
	}

	public Vector2D getStartTegel() {
		return startTegel;
	}

	public void setStartTegel(Tegel tegel) {
		veld.add(new ArrayList<Tegel>());
		Tegel startT = tegel;
		veld.get(0).add(startT);
		setLaatstGeplaatsteTegel(startT);
		this.startTegel = new Vector2D(0, 0);
	}

	public int size() {
		return veld.size();
	}

	public int getBreedte() {
		int hoogte = 0;

		if (veld.size() != 0) {
			hoogte = veld.get(0).size();

			for (int i = 1; i < veld.size(); ++i) {
				if (hoogte < veld.get(i).size()) {
					hoogte = veld.get(i).size();
				}
			}
		}

		return hoogte;
	}

	private int getNegativeSize(ArrayList<Tegel> kolomVector) {
		int aantal = 0;
		for (int i = 0; i <= startTegel.getY(); ++i) {
			++aantal;
		}
		return aantal;
	}

	private Vector2D zetOmInVeldCoord(Vector2D coord) {
		int x = startTegel.getX() + coord.getX();
		int y = startTegel.getY() + coord.getY();

		return new Vector2D(x, y);
	}

	public Tegel get(Vector2D coord) {
		Vector2D veldCoord = zetOmInVeldCoord(coord);
		return tegelAt(veldCoord);
	}

	private Tegel tegelAt(Vector2D coord) {
		int x = coord.getX();
		int y = coord.getY();

		// ongedefinieerde positie
		if (x < 0 || x >= veld.size() || y < 0 || y >= veld.get(x).size()) {
			return null;
		}

		return veld.get(x).get(y);
	}

	// TEGELPLAATSING

	/**
	 * Zal een tegel op het speelveld plaatsen op de co�rdinaten gegeven door
	 * coord.
	 * 
	 * @param t
	 *            De tegel die op de tafel gelegd moet worden.
	 * @param coord
	 *            De co�rdinaten van de tegel.
	 * @return Geeft true als de tegel geplaatst is. False als de tegel niet
	 *         geplaatst kan worden
	 */
	public boolean plaatsTegel(Tegel tegel, Vector2D coord) {
		if (veld == null || veld.size() == 0) {
			setStartTegel(tegel);
			return true;
		}

		Vector2D veldCoord = zetOmInVeldCoord(coord);
		int rij = veldCoord.getX();
		int kolom = veldCoord.getY();
		veldCoord = null;

		if (!tegelKanGeplaatstWorden(tegel, rij, kolom)) {
			return false;
		}

		ArrayList<Tegel> kolomVector;

		// boven of onder de starttegel
		if (rij == -1) {
			rij = 0;
			kolomVector = addRij(rij);
			startTegel.setX(startTegel.getX() + 1);
		} else if (rij == veld.size()) {
			kolomVector = addRij(rij);
		} else { // toevoegen in een bestaande rij
			kolomVector = veld.get(rij);
		}

		// links of rechts van de starttegel
		if (kolom == -1) {
			adjustAll(rij, kolom);
			startTegel.setY(startTegel.getY() + 1);
			kolom = (kolom < 0) ? 0 : kolom;
		} else if (kolom > veld.get(rij).size()) {
			addSpacers(rij, kolom);
		}

		if (kolom < kolomVector.size() && kolomVector.get(kolom) == null) {
			kolomVector.remove(kolom);
		}

		kolomVector.add(kolom, tegel);
		setLaatstGeplaatsteTegel(tegel);

		return true;
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
	public boolean isTegelPlaatsingGeldig(Tegel tegel, Vector2D coord) {
		int rij = startTegel.getX() + coord.getX();
		int kolom = startTegel.getY() + coord.getY();

		return tegelKanGeplaatstWorden(tegel, rij, kolom);
	}

	/**
	 * @param rij
	 *            De rij waarop we een tegel willen plaatsen.
	 * @param kolom
	 *            De kolom waarop we een tegel willen plaatsen.
	 * @return True als de tegel geplaatst kan worden. False als de tegel niet
	 *         geplaatst kan worden.
	 */
	private boolean tegelKanGeplaatstWorden(Tegel tegel, int rij, int kolom) {
		// De tegel zijn 4 buren zijn geldig en
		// er is nog geen tegel geplaatst op deze positie
		return !isGeplaatst(rij, kolom)
				&& geldigeBuren((Tegel) tegel, rij, kolom);
	}

	/**
	 * @param rij
	 * @param kolom
	 * @return
	 */
	private boolean isGeplaatst(int rij, int kolom) {
		ArrayList<Tegel> rijV = null;

		// zoek de juist rij geeft null indien deze niet bestaat
		if (veld != null && rij > 0 && rij < veld.size()) {
			rijV = veld.get(rij);
		}

		// ga na of er al een Tegel op positie (rij, kolom) staat/
		if (rijV != null && kolom > 0 && kolom < rijV.size()) {
			if (rijV.get(kolom) != null) {
				return true;
			}
		}

		return false;
	}

	private ArrayList<Tegel> addRij(int rij) {
		ArrayList<Tegel> kolomVector = new ArrayList<Tegel>();
		veld.add(rij, kolomVector);
		kolomVector = veld.get(rij);

		return kolomVector;
	}

	/**
	 * Als de Tegel links van de starttegel wordt toegevoegd moet in elke rij de
	 * nieuwe positie van de starttegel aangepast. Alle element coördinaten
	 * zijn hier immer relatief mee. Bij rechtse plaatsing dient enkel de rij
	 * waarin de tegel gezet wordt, opgevuld te worden Boven en Onder geven geen
	 * probleem.
	 * 
	 * @param kolom
	 *            Dit getal geeft aan tot waar er bijgevuld moet worden.
	 * @param kolomVector
	 *            De vector die opgevuld moet worden.
	 */
	private void addSpacers(int rij, int kolom) {
		ArrayList<Tegel> kolomVector = veld.get(rij);
		for (int i = kolomVector.size(); i < kolom; ++i) {
			kolomVector.add(null);
		}
	}

	/**
	 * Als er een tegel links van de starttegel gezet wordt moeten alle rijen
	 * aangepast worden. Alle elementen zijn immers t.o.v. de positie van de
	 * starttegel opgeslaan.
	 * 
	 * @param rij
	 *            De rij waarin een tegel gezet zal worden.
	 * @param kolom
	 *            De kolom waarin een tegel gezet zal worden.
	 */
	private void adjustAll(int rij, int kolom) {
		ArrayList<Tegel> kolomVector;
		int aantal = (int) Math.abs(kolom - startTegel.getY());
		int offset;

		for (int i = 0; i < veld.size(); ++i) {
			kolomVector = veld.get(i);
			offset = getNegativeSize(kolomVector);
			for (int j = 0; j < aantal + 1 - offset; ++j) {
				kolomVector.add(0, null);
			}
		}
	}

	// ALGORITMISCH STRUCTUREN UIT DE ADT HALEN

	public Tegel[] getBuren(Vector2D coord) {
		Vector2D veldCoord = zetOmInVeldCoord(coord);
		return getBurenHulp(veldCoord);
	}

	private Tegel[] getBurenHulp(Vector2D coord) {
		Tegel[] buren = new Tegel[4];
		Vector2D veldCoord = new Vector2D(coord);

		// noord buur
		veldCoord.setX(veldCoord.getX() - 1);
		buren[0] = tegelAt(veldCoord);

		// oost buur
		veldCoord.setX(veldCoord.getX() + 1);
		veldCoord.setY(veldCoord.getY() + 1);
		buren[1] = tegelAt(veldCoord);

		// zuid buur
		veldCoord.setX(veldCoord.getX() + 1);
		veldCoord.setY(veldCoord.getY() - 1);
		buren[2] = tegelAt(veldCoord);

		// oost buur
		veldCoord.setX(veldCoord.getX() - 1);
		veldCoord.setY(veldCoord.getY() - 1);
		buren[3] = tegelAt(veldCoord);

		return buren;
	}

	private boolean geldigeBuren(Tegel tegel, int rij, int kolom) {
		boolean burenGeldig = true;
		boolean buurGevonden = false; // als er geen buur gevonden wordt dan
		// is
		// de zet ook ongeldig
		Tegel t;
		Vector2D positie = new Vector2D();

		for (int i = 0; burenGeldig && i < 4; ++i) {
			switch (i) {
			case 0:
				positie.setXY(rij - 1, kolom);
				break;
			case 1:
				positie.setXY(rij, kolom + 1);
				break;
			case 2:
				positie.setXY(rij + 1, kolom);
				break;
			case 3:
				positie.setXY(rij, kolom - 1);
				break;
			}

			if ((t = (Tegel) tegelAt(positie)) != null) {
				buurGevonden = true;
				burenGeldig = gelijkeLandsDelen(i, tegel, t);
			}
		}

		return buurGevonden && burenGeldig;
	}

	private boolean gelijkeLandsDelen(int i, Tegel tegel, Tegel t) {
		boolean isLandsdeelGelijk = true;

		switch (i) {
		case NOORD:
			isLandsdeelGelijk = vergelijkLandsdelen(tegel, t, Tegel.NOORD_WEST,
					Tegel.ZUID_WEST, Tegel.NOORD, Tegel.ZUID, Tegel.NOORD_OOST,
					Tegel.ZUID_OOST);
			break;
		case OOST:
			isLandsdeelGelijk = vergelijkLandsdelen(tegel, t, Tegel.OOST_NOORD,
					Tegel.WEST_NOORD, Tegel.OOST, Tegel.WEST, Tegel.OOST_ZUID,
					Tegel.WEST_ZUID);
			break;
		case ZUID:
			isLandsdeelGelijk = vergelijkLandsdelen(tegel, t, Tegel.ZUID_OOST,
					Tegel.NOORD_OOST, Tegel.ZUID, Tegel.NOORD, Tegel.ZUID_WEST,
					Tegel.NOORD_WEST);
			break;
		case WEST:
			isLandsdeelGelijk = vergelijkLandsdelen(tegel, t, Tegel.WEST_ZUID,
					Tegel.OOST_ZUID, Tegel.WEST, Tegel.OOST, Tegel.WEST_NOORD,
					Tegel.OOST_NOORD);
			break;
		}

		return isLandsdeelGelijk;
	}

	private boolean vergelijkLandsdelen(Tegel tegel, Tegel buur,
			int landsdeelA, int landsdeelABuur, int landsdeelB,
			int landsdeelBBuur, int landsdeelC, int landsdeelCBuur) {
		return tegel.bepaalLandsdeel(landsdeelA).getType() == buur
				.bepaalLandsdeel(landsdeelABuur).getType()
				&& tegel.bepaalLandsdeel(landsdeelB).getType() == buur
						.bepaalLandsdeel(landsdeelBBuur).getType()
				&& tegel.bepaalLandsdeel(landsdeelC).getType() == buur
						.bepaalLandsdeel(landsdeelCBuur).getType();
	}

	/**
	 * Deze functie zal het veld 1 zet terugdoen. Hiervoor heeft hij een
	 * referentie nodig naar de vorige laatst geplaatste tegel.
	 * 
	 * @param laatstGeplaatsteTegel
	 *            Referentie naar de vorige laatst geplaatste tegel.
	 */
	public void undo(Tegel laatstGeplaatsteTegel) {
		Vector2D coordVerwijderdeTegel = null;
		boolean gevonden = false;

		for (int i = 0; !gevonden && i < veld.size(); ++i) {
			ArrayList<Tegel> kolomVector = veld.get(i);
			for (int j = 0; !gevonden && j < kolomVector.size(); ++i) {
				if (kolomVector.get(j) == this.laatstGeplaatsteTegel) {
					kolomVector.remove(j);
					coordVerwijderdeTegel = new Vector2D(i, j);
				}
			}
			if (kolomVector.size() == 0) {
				veld.remove(i);
			}
		}

		if (gevonden && coordVerwijderdeTegel.getX() < startTegel.getX()) {
			startTegel.setX(startTegel.getX() - 1);
		}

		if (gevonden && coordVerwijderdeTegel.getY() < startTegel.getY()) {
			startTegel.setY(startTegel.getY() - 1);
		}

		this.laatstGeplaatsteTegel = laatstGeplaatsteTegel;
	}
	
	public void undo()
	{
		if(laatstGeplaatsteTegel != null)
			undo(laatstGeplaatsteTegel);
	}

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		boolean gevonden = false;
		ArrayList<Tegel> kolomVector;
		Vector2D coordLaatstGeplaatsteTegel = null;

		for (int i = 0; !gevonden && i < veld.size(); ++i) {
			kolomVector = veld.get(i);
			for (int j = 0; j < kolomVector.size(); ++j) {
				if (kolomVector.get(j) == laatstGeplaatsteTegel) {
					gevonden = true;
					coordLaatstGeplaatsteTegel = new Vector2D(i, j);
				}
			}
		}
		out.writeObject(coordLaatstGeplaatsteTegel);
		out.writeObject(startTegel);
		out.writeObject(veld);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		Vector2D coordLaatstGeplaatsteTegel = (Vector2D) in.readObject();
		startTegel = (Vector2D) in.readObject();
		veld = (ArrayList<ArrayList<Tegel>>) in.readObject();

		int x = coordLaatstGeplaatsteTegel.getX();
		int y = coordLaatstGeplaatsteTegel.getY();

		if (coordLaatstGeplaatsteTegel != null && x >= 0 && x < veld.size()
				&& y >= 0 && y < veld.get(x).size()) {
			laatstGeplaatsteTegel = veld.get(x).get(y);
		}
	}

	private void readObjectNoData() throws ObjectStreamException {
		// TODO invullen??
	}
}

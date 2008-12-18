// TODO nakijken of veld niet beter een ADT wordt
package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author school
 * 
 */
public class Tafel implements Serializable {
	private static final long serialVersionUID = -1767380269715221020L;
	private static final int NOORD = 0;
	private static final int OOST = 1;
	private static final int ZUID = 2;
	private static final int WEST = 3;

	private Tegel laatstGeplaatsteTegel;
	private ArrayList<ArrayList<Tegel>> veld;
	private Vector2D startTegel;
	private PuntenVerwerker puntenVerwerker;
	
	public Tafel() {
		clear();
	}

	public Tafel(PuntenVerwerker puntenVerwerker) {
		this.puntenVerwerker = puntenVerwerker;
		clear();
	}
	
	/** 
	 * Zal de de laatst geplaatste tegel teruggeven.
	 * 
	 * @return
	 * 	laatstGeplaatste tegel
	 */
	public Tegel getLaatstGeplaatsteTegel() {
		return laatstGeplaatsteTegel;
	}

	private void setLaatstGeplaatsteTegel(Tegel laatstGeplaatsteTegel) {
		this.laatstGeplaatsteTegel = laatstGeplaatsteTegel;
	}

	/**
	 * Geeft de positie van de start tegel terug /
	 * @return
	 */
	public Vector2D getStartTegel() {
		return startTegel;
	}
	
	/**
	 * Zal een tegel op het speelveld plaatsen op de coördinaten gegeven door coord.
	 * @param tegel
	 *            De tegel die op de tafel gelegd moet worden.
	 * @param coord
	 *            De coördinaten van de tegel.
	 * @return Geeft true als de tegel geplaatst is. False als de tegel niet
	 *         geplaatst kan worden
	 */
	public boolean plaatsTegel(Tegel tegel, Vector2D coord) {
		// startTegel wordt gezet
		// coord maken niet uit startTegel staat op (0, 0)
		if (veld == null) {
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

	private Tegel[] getBuren(Vector2D coord) {
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

	private ArrayList<Tegel> addRij(int rij) {
		ArrayList<Tegel> kolomVector = new ArrayList<Tegel>();
		veld.add(rij, kolomVector);
		kolomVector = veld.get(rij);

		return kolomVector;
	}

	/**
	 * Als de Tegel links van de starttegel wordt toegevoegd moet in elke rij de
	 * nieuwe positie van de starttegel aangepast. Alle element coördinaten zijn
	 * hier immer relatief mee. Bij rechtse plaatsing dient enkel de rij waarin
	 * de tegel gezet wordt, opgevuld te worden Boven en Onder geven geen
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
			for (int j = 0; j < aantal+1 - offset; ++j) {
				kolomVector.add(0, null);
			}
		}
	}

	private int getNegativeSize(ArrayList<Tegel> kolomVector) {
		int aantal = 0;
		for (int i = 0; i <= startTegel.getY(); ++i) {
			++aantal;
		}
		
		return aantal;
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
		return !isGeplaatst(rij, kolom) && geldigeBuren(tegel, rij, kolom);
	}

	/**
	 * @param rij
	 *            De rij waarop we een tegel willen plaatsen.
	 * @param kolom
	 *            De kolom waarop we een tegel willen plaatsen.
	 * @return True als de buren geldig zijn False als 1 van de buren ongeldig
	 *         is of als er geen buren zijn
	 */
	private boolean geldigeBuren(Tegel tegel, int rij, int kolom) {
		boolean burenGeldig = true;
		boolean buurGevonden = false; // als er geen buur gevonden wordt dan is
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

			if ((t = tegelAt(positie)) != null) {
				buurGevonden = true;
				burenGeldig = gelijkeLandsDelen(i,tegel, t);
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
		return tegel.bepaalLandsdeel(landsdeelA).getType() == buur.bepaalLandsdeel(landsdeelABuur).getType() &&
			   tegel.bepaalLandsdeel(landsdeelB).getType() == buur.bepaalLandsdeel(landsdeelBBuur).getType() &&
			   tegel.bepaalLandsdeel(landsdeelC).getType() == buur.bepaalLandsdeel(landsdeelCBuur).getType();
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

	/**
	 * Zal indien mogelijk pion plaatsen op de tegel met coördinaten tegelCoord. 
	 * pionCoord zal bepalen op welk landsdeel de pion zal staan.
	 * vb: Tegel.NOORD_OOST
	 * 
	 * @param tegelCoord
	 * 	coördinaten van de tegel waar een pion op geplaatst zal worden.
	 * @param pionCoord
	 * 	coördinaten van het landsdeel op de tegel waar de pion zal geplaatst worden.
	 * @param pion
	 * 	de pion die zal geplaatst worden.
	 * @return
	 * 	True als de pion geplaatst is, False als hij niet geplaatst kan worden.
	 */
	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord, Pion pion) {
		Tegel t = bepaalTegel(tegelCoord);

		if (t != laatstGeplaatsteTegel) {
			return false;
		}
		
		return t != null && isPionPlaatsingGeldig(t, tegelCoord, pionCoord)
				&& t.plaatsPion(pionCoord, pion);
	}

	/**
	 * Deze functie zal nagaan of tegel op positie coord geplaatst kan worden.
	 * De tegel wordt hierbij niet echt op het veld gezet.
	 * @param tegel
	 * 	De tegel die geplaatst moet worden.
	 * @param coord
	 * 	De coördinaten waar de tegel geplaatst moet worden.
	 * @return
	 * 	True indien de tegelplaatsing mogelijk is, False anders.
	 */
	public boolean isTegelPlaatsingGeldig(Tegel tegel, Vector2D coord) {
		int rij = startTegel.getX() + coord.getX();
		int kolom = startTegel.getY() + coord.getY();

		return tegelKanGeplaatstWorden(tegel, rij, kolom);
	}

	/**
	 * Deze functie zal nagaan of een pion plaatsing kan gebeuren op tegel met coördinaten tegelCoord op het
	 * landsdeel met coördinaten pionCoord. Deze functie zal niet de pion echt gaan plaatsen op de tegel.
	 * @param tegel
	 * 	De tegel waarop de pion geplaatst moet worden.
	 * @param tegelCoord
	 * 	De coördinaten van de tegel waarop de pion geplaatst moet worden.
	 * @param pionCoord
	 * 	De coördinaten van het lansdeel waarop de pion geplaatst moet worden.
	 * @return
	 * 	True als de pion geplaatst kan worden, False anders.
	 */
	public boolean isPionPlaatsingGeldig(Tegel tegel, Vector2D tegelCoord,
			int pionCoord) {
		Vector2D  veldCoord = zetOmInVeldCoord(tegelCoord);
		ArrayList<Tegel> checked = new ArrayList<Tegel>();
		return isPionPlaatsingGeldig(pionCoord, tegel, veldCoord, checked);
	}
	
	private boolean isPionPlaatsingGeldig(int pionCoord, Tegel tegel, Vector2D veldCoord, 
			ArrayList<Tegel> checked) {
		if (pionCoord < 0 || pionCoord >= Tegel.MAX_GROOTTE || 
				tegel.isPionGeplaatst(pionCoord)) {
			return false;
		}
		
		checked.add(tegel);
		
		Tegel[] buren = getBuren(veldCoord);
		char matchLandsdeel = tegel.bepaalLandsdeel(pionCoord).getType();
		
		boolean pionPlaatsingGeldig = true;
		
		if (buren[0] != null && !alGechecked(checked, buren[0])) {
			veldCoord.setX(veldCoord.getX()-1);
			pionPlaatsingGeldig = isPionPlaatsingGeldig(NOORD, 
					matchLandsdeel, buren[0], veldCoord, checked);
		} 
		
		if (buren[1] != null && !alGechecked(checked, buren[1]) && pionPlaatsingGeldig) {
			veldCoord.setY(veldCoord.getY()+1);
			pionPlaatsingGeldig = isPionPlaatsingGeldig(OOST, 
					matchLandsdeel, buren[1], veldCoord, checked);
		} 
		
		if (buren[2] != null && !alGechecked(checked, buren[2]) && pionPlaatsingGeldig) {
			veldCoord.setX(veldCoord.getX()+1);
			pionPlaatsingGeldig = isPionPlaatsingGeldig(ZUID, 
					matchLandsdeel, buren[2], veldCoord, checked);
		} 
		
		if (buren[3] != null && !alGechecked(checked, buren[3]) && pionPlaatsingGeldig) {
			veldCoord.setY(veldCoord.getY()-1);
			pionPlaatsingGeldig = isPionPlaatsingGeldig(WEST, 
					matchLandsdeel, buren[3], veldCoord, checked);
		}

		return pionPlaatsingGeldig;
	}

	private boolean alGechecked(ArrayList<Tegel> checked, Tegel tegel) {
		for (int i = 0; i < checked.size(); ++i) {
			if (checked.get(i) == tegel) {
				return true;
			}
		}
		
		return false;
	}

	private boolean isPionPlaatsingGeldig(int windrichting, char matchLandsdeel,
			Tegel tegel, Vector2D tegelCoord, ArrayList<Tegel> checked) {
		boolean pionPlaatsingGeldig = true;

		switch(windrichting) {
		case NOORD:
			pionPlaatsingGeldig = pionPlaatsingGeldig(tegel, tegelCoord, matchLandsdeel, Tegel.ZUID_WEST,
					Tegel.ZUID, Tegel.ZUID_OOST, checked);
			break;
		case OOST:
			pionPlaatsingGeldig = pionPlaatsingGeldig(tegel, tegelCoord, matchLandsdeel, Tegel.WEST_NOORD,
					Tegel.WEST, Tegel.WEST_ZUID, checked);
			break;
		case ZUID:
			pionPlaatsingGeldig = pionPlaatsingGeldig(tegel, tegelCoord, matchLandsdeel, Tegel.NOORD_OOST,
					Tegel.NOORD, Tegel.NOORD_WEST, checked);
			break;
		case WEST:
			pionPlaatsingGeldig = pionPlaatsingGeldig(tegel, tegelCoord, matchLandsdeel, Tegel.OOST_ZUID,
					Tegel.OOST, Tegel.OOST_NOORD, checked);
			break;
		}
		
		return pionPlaatsingGeldig;		
	}

	private boolean pionPlaatsingGeldig(Tegel tegel, Vector2D tegelCoord,
			char matchLandsdeel, int windrichting, int windrichting2, 
			int windrichting3, ArrayList<Tegel> checked) {
		boolean pionPlaatsingGeldig = true;
		Landsdeel vorigeLd = null;
		Landsdeel huidigeLd;
		huidigeLd = tegel.bepaalLandsdeel(windrichting);
		
		if (huidigeLd.getType() == matchLandsdeel) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(windrichting, tegel, tegelCoord, checked);
		}
		
		vorigeLd = huidigeLd;
		huidigeLd = tegel.bepaalLandsdeel(windrichting2);
		if (pionPlaatsingGeldig && vorigeLd != huidigeLd && huidigeLd.getType() == matchLandsdeel) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(windrichting2, tegel, tegelCoord, checked);
		}
		
		vorigeLd = huidigeLd;
		huidigeLd = tegel.bepaalLandsdeel(windrichting3);
		if (pionPlaatsingGeldig && vorigeLd != huidigeLd && huidigeLd.getType() == matchLandsdeel) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(windrichting3, tegel, tegelCoord, checked);
		}
		
		return pionPlaatsingGeldig;
	}

	/**
	 * Deze functie zal nagaan of de tegel met coördinaten (rij, kolom) de laatst geplaatste tegel is.
	 * 
	 * @param rij
	 * 	Het rijnummer van de tegel.
	 * @param kolom
	 * 	Het kolomnummer van de tegel.
	 * @return
	 * 	True als dit de coördinaten zijn van de laatstgeplaatste tegel, False anders.
	 */
	public boolean isLaatste(int rij, int kolom) {
		return bepaalTegel(new Vector2D(rij, kolom)) == laatstGeplaatsteTegel;
	}

	// TODO Functie is er enkel voor de unit test __NIET__ gebruiken voor andere
	// doeleinde. Hogere lagen moeten de tegel niet kunnen bepalen???
	public Tegel bepaalTegel(Vector2D coord) {
		// er is nog geen tegel gelegd
		if (startTegel == null) {
			return null;
		}

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

	public boolean neemPionTerug(Pion pion) {
		return false;
	}	

	/**
	 * Deze functie zal het veld 1 zet terugdoen.
	 * Hiervoor heeft hij een referentie nodig naar de vorige laatst 
	 * geplaatste tegel.
	 * 
	 * @param laatstGeplaatsteTegel
	 * Referentie naar de vorige laatst geplaatste tegel.
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

	public void redo() {

	}

	

	public void clear() {
		startTegel = null;
		laatstGeplaatsteTegel = null;
		veld = null;
	}

	private void setStartTegel(Tegel tegel) {
		veld = new ArrayList<ArrayList<Tegel>>();
		veld.add(new ArrayList<Tegel>());
		Tegel startT = tegel;
		veld.get(0).add(startT);
		setLaatstGeplaatsteTegel(startT);
		this.startTegel = new Vector2D(0, 0);
	}

	/**
	 * Zal voor elk niet null element van het veld een x printen. Voor null
	 * elementen een n. Handig om te kijken of het veld goed is opgeslaan.
	 */
	public void printVeld() {
		for (int i = 0; i < veld.size(); ++i) {
			for (int j = 0; j < veld.get(i).size(); ++j) {
				if (veld.get(i).get(j) == null) {
					System.out.print(" n ");
				} else
					System.out.print(" x ");
			}
			System.out.println();
		}
	}
	
	public Vector2D getBeginPositie() {
		return new Vector2D(-startTegel.getX(), -startTegel.getY());
	}
	
	public int getHoogte() {
		return veld.size();
	}
	
	public int getBreedte() {
		int hoogte = 0;
		
		if (veld.size() != 0) {
			hoogte = veld.get(0).size();
			
			for(int i = 1; i < veld.size(); ++i) {
				if (hoogte < veld.get(i).size()) {
					hoogte = veld.get(i).size();
				}
			}
		}
		
		return hoogte;
	}
	
	public String[] getTegelPresentatie(Vector2D coord) {
		if (coord == null) {
			return null;
		}
		
		Vector2D veldCoord = zetOmInVeldCoord(coord);	
		int x = veldCoord.getX();
		int y = veldCoord.getY();
		
		if (veldCoord != null && x >= 0 && x < veld.size()
				&& y >= 0 && y < veld.get(x).size()) {
			Tegel t = veld.get(x).get(y);
			String[] retString = new String[2];
			retString[0] = t.getTegelPresentatie();
			retString[1] = t.getIdPresentatie();
			return retString;
		}
		
		return null;
	}

	private Vector2D zetOmInVeldCoord(Vector2D coord) {
		int x = startTegel.getX() + coord.getX();
		int y = startTegel.getY() + coord.getY();
		
		return new Vector2D(x, y);
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		boolean gevonden = false;
		ArrayList<Tegel> kolomVector;
		Vector2D coordLaatstGeplaatsteTegel = null;
		
		for (int i = 0; !gevonden && i < veld.size(); ++i) {
			kolomVector = veld.get(i);
			for(int j = 0; j < kolomVector.size(); ++j) {
				if (kolomVector.get(j) == laatstGeplaatsteTegel) {
					gevonden = true;
					coordLaatstGeplaatsteTegel = new Vector2D(i, j);
				}
			}
		}
		
		out.writeObject(coordLaatstGeplaatsteTegel);
		out.writeObject(veld);
		out.writeObject(startTegel);
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		Vector2D coordLaatstGeplaatsteTegel = (Vector2D)in.readObject();
		veld = (ArrayList<ArrayList<Tegel>>) in.readObject();
		startTegel = (Vector2D) in.readObject();
		
		int x = coordLaatstGeplaatsteTegel.getX();
		int y = coordLaatstGeplaatsteTegel.getY();
		
		if (coordLaatstGeplaatsteTegel != null && x >= 0 
				&& x < veld.size() && y >= 0 && y < veld.get(x).size()) {
			laatstGeplaatsteTegel = veld.get(x).get(y);
		}
	}

	private void readObjectNoData() throws ObjectStreamException {
		// TODO invullen??
	}

	public void updateScore(Vector2D coord) {
		Vector2D veldCoord = zetOmInVeldCoord(coord);
		
		puntenVerwerker.updateScore(veldCoord, veld);
	}
}

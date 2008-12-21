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
	
	private TegelVeld veld;
	
	private PuntenVerwerker puntenVerwerker;
	private static final int TEGEL_PRESENTATIE = 0;
	private static final int ID_PRESENTATIE = 1;
	private static final int ORIENTATIE = 2;
	
	public Tafel() {
		clear();
	}

	public Tafel(PuntenVerwerker puntenVerwerker) {
		this.puntenVerwerker = puntenVerwerker;
		clear();
	}
	
	/**
	 * Zal een tegel op het speelveld plaatsen op de coördinaten gegeven door coord.
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
		Tegel tegel = new Tegel(t[TEGEL_PRESENTATIE ], t[ID_PRESENTATIE], 
				Short.parseShort(t[ORIENTATIE]));
	
		return veld.plaatsTegel(tegel, coord);
	}
	/**
	 * Geeft de positie van de start tegel terug /
	 * @return
	 */
	public Vector2D getStartTegel() {
		return veld.getStartTegel();
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
		String[] stringRepresentatie = new String[2];
		stringRepresentatie[0] = t.getTegelPresentatie();
		stringRepresentatie[1] = t.getIdPresentatie();
		
		if (t != (Tegel) veld.getLaatstGeplaatsteTegel()) {
			return false;
		}
		
		return t != null && isPionPlaatsingGeldig(stringRepresentatie, tegelCoord, pionCoord)
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
	public boolean isTegelPlaatsingGeldig(String[] t, Vector2D coord) {
		Tegel tegel = new Tegel(t[TEGEL_PRESENTATIE], t[ID_PRESENTATIE],
				Short.parseShort(t[ORIENTATIE]));
		return veld.isTegelPlaatsingGeldig(tegel, coord);
	}

	/**
	 * Deze functie zal nagaan of een pion plaatsing kan gebeuren op tegel met coördinaten tegelCoord op het
	 * landsdeel met coördinaten pionCoord. Deze functie zal niet de pion echt gaan plaatsen op de tegel.
	 * @param t
	 * 	De tegel waarop de pion geplaatst moet worden.
	 * @param tegelCoord
	 * 	De coördinaten van de tegel waarop de pion geplaatst moet worden.
	 * @param pionCoord
	 * 	De coördinaten van het lansdeel waarop de pion geplaatst moet worden.
	 * @return
	 * 	True als de pion geplaatst kan worden, False anders.
	 */
	public boolean isPionPlaatsingGeldig(String[] t, Vector2D tegelCoord,
			int pionCoord) {
		ArrayList<Tegel> checked = new ArrayList<Tegel>();
		Tegel tegel = new Tegel(t[TEGEL_PRESENTATIE], t[ID_PRESENTATIE],
				Short.parseShort(t[ORIENTATIE]));
		return isPionPlaatsingGeldig(pionCoord, tegel, tegelCoord, checked);
	}
	
	private boolean isPionPlaatsingGeldig(int pionCoord, Tegel tegel, Vector2D veldCoord, 
			ArrayList<Tegel> checked) {
		if (pionCoord < 0 || pionCoord >= Tegel.MAX_GROOTTE || 
				tegel.isPionGeplaatst(pionCoord)) {
			return false;
		}
		
		checked.add(tegel);
		
		Tegel[] buren = (Tegel[])veld.getBuren(veldCoord, true);
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
		return veld.isLaatste(rij, kolom);
	}

	// TODO Functie is er enkel voor de unit test __NIET__ gebruiken voor andere
	// doeleinde. Hogere lagen moeten de tegel niet kunnen bepalen???
	public Tegel bepaalTegel(Vector2D coord) {
		return (Tegel)veld.get(coord);
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
		veld.undo(laatstGeplaatsteTegel);
	}

	public void redo() {

	}

	

	public void clear() {
		veld = null;
	}

	public Vector2D getBeginPositie() {
		Vector2D startTegel = veld.getStartTegel();
		return new Vector2D(-startTegel.getX(), -startTegel.getY());
	}
	
	public int getHoogte() {
		return veld.size();
	}
	
	public int getBreedte() {
		return veld.getBreedte();
	}
	
	public String[] getTegelPresentatie(Vector2D coord) {
		if (coord == null) {
			return null;
		}
		
		Tegel tegel = (Tegel)veld.get(coord);
		
		if (tegel != null) {
			String[] retString = new String[2];
			retString[0] = tegel.getTegelPresentatie();
			retString[1] = tegel.getIdPresentatie();
			return retString;
		}
		
		return null;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(veld);		
	}
	
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		veld = (TegelVeld)in.readObject();
	}

	private void readObjectNoData() throws ObjectStreamException {
		// TODO invullen??
	}

	public void updateScore(Vector2D coord) {
//		puntenVerwerker.updateScore(coord, veld);
	}

	public Tegel getLaatstGeplaatsteTegel() {
		return veld.getLaatstGeplaatsteTegel();
	}
}

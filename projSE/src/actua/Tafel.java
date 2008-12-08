package actua;

import java.util.Vector;


public class Tafel {
	private static final int NOORD = 0;
	private static final int OOST = 1;
	private static final int ZUID = 2;
	private static final int WEST = 3;

	private Tegel laatstGeplaatsteTegel;
	private Vector<Vector<Tegel>> veld;
	private Camera oogpunt;
	private Vector2D startTegel;
	
	public Tafel() {
		clear();
	}

	public Tegel getLaatstGeplaatsteTegel() {
		return laatstGeplaatsteTegel;
	}

	private void setLaatstGeplaatsteTegel(Tegel laatstGeplaatsteTegel) {
		this.laatstGeplaatsteTegel = laatstGeplaatsteTegel;
	}

	public Vector2D getStartTegel() {
		return startTegel;
	}
	
	public void beweegCamera(Vector3D nieuwePositie) {
		getOogpunt().veranderStandpunt(nieuwePositie);		
	}

	/**
	 * @param tegel
	 * 			De tegel die op de tafel gelegd moet worden.
	 * @param coord
	 * 			De coördinaten van de tegel.
	 * @return
	 * 			Geeft true als de tegel geplaatst is. False als de tegel niet geplaatst kan worden
	 */
	public boolean plaatsTegel(Tegel tegel, Vector2D coord) {
		// startTegel wordt gezet
		// coord maken niet uit startTegel staat op (0, 0)
		if (veld == null) {			
			setStartTegel(tegel);			
			return true;
		}
		
		int rij = startTegel.getX() + coord.getX();
		int kolom = startTegel.getY() + coord.getY();		
		// mag de tegel hier gezet worden? M.a.w. zijn zijn buren geldig?
		if (!tegelKanGeplaatstWorden(tegel, rij, kolom)) {
			return false;
		}

		Vector<Tegel> kolomVector;
		
		// boven of onder de starttegel
		if (rij == -1 || rij == veld.size()) {
			rij = (rij == -1)? 0 : rij;
			kolomVector = new Vector<Tegel>();
			veld.add(rij, kolomVector);
			kolomVector = veld.get(rij);
			startTegel.setX(startTegel.getX() + 1);
		} else { // toevoegen in een bestaande rij
			kolomVector = veld.get(rij);
		}
		
		// links of rechts van de starttegel
		if (kolom < 0 || kolom > veld.get(rij).size()) {
			addSpacers(kolom, kolomVector);
			kolom = (kolom < 0)? 0 : kolom;
			startTegel.setY(startTegel.getY() + 1);
		}
		
		kolomVector.add(kolom, tegel);
		setLaatstGeplaatsteTegel(tegel);
		
		return true;
	}

	private void addSpacers(int kolom, Vector<Tegel> kolomVector) {
		int start = 0, end = 0;
		
		if (kolom < -1) {
			start = kolom;
			end = kolomVector.size();
		} else if (kolom > 1){
			start = kolomVector.size();
			end = kolom;
		}
		
		for(; start < end; ++start) {
			kolomVector.add(null);
		}
	}

	/**
	 * @param rij
	 * 			De rij waarop we een tegel willen plaatsen.
	 * @param kolom
	 * 			De kolom waarop we een tegel willen plaatsen.
	 * @return
	 * 			True als de tegel geplaatst kan worden.
	 * 			False als de tegel niet geplaatst kan worden.
	 */
	private boolean tegelKanGeplaatstWorden(Tegel tegel, int rij, int kolom) {
		// De tegel zijn 4 buren zijn geldig en 
		// er is nog geen tegel geplaatst op deze positie
		return !isGeplaatst(rij, kolom) && geldigeBuren(tegel, rij, kolom);
	}

	/**
	 * @param rij
	 * 			De rij waarop we een tegel willen plaatsen.
	 * @param kolom
	 * 			De kolom waarop we een tegel willen plaatsen.
	 * @return
	 * 		True als de buren geldig zijn
	 * 		False als 1 van de buren ongeldig is of als er geen buren zijn
	 */
	private boolean geldigeBuren(Tegel tegel, int rij, int kolom) {
		boolean burenGeldig = true;
		boolean buurGevonden = false; // als er geen buur gevonden wordt dan is de zet ook ongeldig
		Tegel t;
		Vector2D positie = new Vector2D();
		int startTegelX = startTegel.getX();
		int startTegelY = startTegel.getY();
		
		for (int i = 0; burenGeldig && i < 4; ++i) {
			switch(i) {
			case 0:
				positie.setXY(rij-1 - startTegelX, kolom - startTegelY);
				break;
			case 1:
				positie.setXY(rij - startTegelX, kolom+1 - startTegelY);
				break;
			case 2:
				positie.setXY(rij+1 - startTegelX, kolom - startTegelY);
				break;
			case 3:
				positie.setXY(rij - startTegelX, kolom-1 - startTegelY);
				break;
			}
			
			if( (t = bepaalTegel(positie)) != null) {
				buurGevonden = true;
				burenGeldig = gelijkeLandsDelen(i, tegel, t);
			}		
		}
		
	return buurGevonden && burenGeldig;
	}

	private boolean gelijkeLandsDelen(int i, Tegel tegel, Tegel t) {
		boolean isLandsdeelGelijk = true;

		switch(i) {
		case NOORD:
			isLandsdeelGelijk = vergelijkLandsdelen(tegel, t, Tegel.NOORD, Tegel.ZUID, 
					Tegel.MIDDEN_NOORD, Tegel.MIDDEN_ZUID);
			break;
		case OOST:
			isLandsdeelGelijk = vergelijkLandsdelen(tegel, t, Tegel.OOST, Tegel.WEST,
					Tegel.MIDDEN_OOST, Tegel.MIDDEN_WEST);
			break;
		case ZUID:
			isLandsdeelGelijk = vergelijkLandsdelen(tegel, t, Tegel.ZUID, Tegel.NOORD, 
					Tegel.MIDDEN_ZUID, Tegel.MIDDEN_NOORD);
			break;
		case WEST:
			isLandsdeelGelijk = vergelijkLandsdelen(tegel, t, Tegel.WEST, Tegel.OOST, 
					Tegel.MIDDEN_WEST, Tegel.MIDDEN_OOST);
			break;
		}
		
		return isLandsdeelGelijk;
	}

	/**
	 * @param tegel
	 * 			Dit is tegel die geplaatst moet worden
	 * @param buur
	 * 			Dit is de buur van de tegel die geplaatst moet worden
	 * @param windrichting
	 * 			Dit is de kant van de tegel die we willen vergelijken (Noord - OOST - ZUID - WEST)
	 * @param windrichtingAndereTegel
	 * 			Dit is de kan van de buur waarmee we vergelijken (Noord - OOST - ZUID - WEST)
	 * @param windrichtingMidden
	 * 			Deze parameter gaat eventuele wegen met elkaar vergelijken (wegen lopen altijd in het midden).
	 * @param windrichtingMiddenAndereTegel
	 * 			Idem met boven
	 * @return
	 * 			True indien beide eenzelfde type landsdeel hebben en ze dus naast elkaar mogen liggen.
	 * 			False anders.
	 */
	private boolean vergelijkLandsdelen(Tegel tegel, Tegel buur, int windrichting, int windrichtingAndereTegel,
			int windrichtingMidden, int windrichtingMiddenAndereTegel) {
		return tegel.bepaalLandsdeel(windrichting).getType() == buur.bepaalLandsdeel(windrichtingAndereTegel).getType() &&
				tegel.bepaalLandsdeel(windrichtingMidden).getType() == buur.bepaalLandsdeel(windrichtingMiddenAndereTegel).getType();
	}

	/**
	 * @param rij
	 * @param kolom
	 * @return
	 */
	private boolean isGeplaatst(int rij, int kolom) {
		Vector<Tegel> rijV = null;
		
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

	public boolean plaatsPion(Vector2D coord, Pion pion) {
		return false;
	}

	public boolean isTegelPlaatsingGeldig(Tegel tegel, Vector2D coord) {
		int rij = startTegel.getX() + coord.getX();
		int kolom = startTegel.getY() + coord.getY();
		
		return tegelKanGeplaatstWorden(tegel, rij, kolom);
	}

	public boolean isPionPlaatsingGeldig(Tegel tegel, int tegelPositie) {
		return false;
	}
	
	public boolean isLaatste(Tegel tegel) {
		return tegel == laatstGeplaatsteTegel;
	}

	public Tegel bepaalTegel(Vector2D coord) {
		// er is nog geen tegel gelegd
		if (startTegel == null) {
			return null;
		}
		
		int x = startTegel.getX() + coord.getX();
		int y = startTegel.getY() + coord.getY();
		
		// ongedefinieerde positie
		if ( x < 0 || x >= veld.size() || y < 0 || y >= veld.get(x).size()) {
			return null;
		}
		
		return veld.get(x).get(y);
	}

	public boolean isBoer(Pion pion) {
		return false;
	}

	public void isGebiedGeldig(Pion pion) {

	}

	public boolean neemPionTerug(Pion pion) {
		return false;
	}

	public void herstelOverzicht() {

	}

	public boolean valideerTegelPlaatsing(Tegel tegel, Vector2D coord) {
		return false;
	}

	public void undo() {

	}

	public void redo() {

	}

	public void setOogpunt(Camera oogpunt) {
		this.oogpunt = oogpunt;
	}

	public Camera getOogpunt() {
		return oogpunt;
	}

	public void clear() {
		startTegel = null;
		oogpunt = new Camera();		
		laatstGeplaatsteTegel = null;
		veld = null;		
		// TODO
//		oogpunt.reset();
	}

	private void setStartTegel(Tegel startTegel) {
		veld = new Vector<Vector<Tegel>>();
		veld.add(new Vector<Tegel>());
		veld.get(0).add(startTegel);
		setLaatstGeplaatsteTegel(startTegel);
		this.startTegel = new Vector2D(0, 0);
	}
}

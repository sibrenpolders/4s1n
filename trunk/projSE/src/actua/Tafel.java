package actua;

import java.util.ArrayList;

/**
 * @author school
 * 
 */
public class Tafel {
	private static final int NOORD = 0;
	private static final int OOST = 1;
	private static final int ZUID = 2;
	private static final int WEST = 3;

	private Tegel laatstGeplaatsteTegel;
	private ArrayList<ArrayList<Tegel>> veld;
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

	// TODO Snap het nut van deze functie niet.
	public Vector2D getStartTegel() {
		return startTegel;
	}

	public void beweegCamera(Vector3D nieuwePositie) {
		getOogpunt().veranderStandpunt(nieuwePositie);
	}

	public boolean cameraBewegingGeldig(Vector3D nieuwePositie) {
		return getOogpunt().bewegingGeldig(nieuwePositie);
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

		int rij = startTegel.getX() + coord.getX();
		int kolom = startTegel.getY() + coord.getY();
		// mag de tegel hier gezet worden? M.a.w. zijn zijn buren geldig?
		// TODO
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
//		// TODO functie update landsdelen schrijven
//		updateLandsdelen(rij, kolom);
		
		return true;
	}

//	private void updateLandsdelen(int rij, int kolom) {
//		if ( rij < 0 || rij >= veld.size() || kolom < 0 || 
//				kolom >= veld.get(rij).size()) {
//			return;
//		}
//		
//		Tegel[] buren = getBuren(rij, kolom);
//		Tegel nieuweTegel = bepaalTegel(new Vector2D(rij - startTegel.getX(), 
//				kolom - startTegel.getY()));
//		boolean[] changed = new boolean[13];
//		
//		if (buren[0] != null) {
//			nieuweTegel.updateLandsdeel(
//					buren[0].bepaalLandsdeel(Tegel.ZUID_WEST), Tegel.NOORD_WEST, 
//					changed);
//		}
//		
//		
//			if (!changed[ZUID]) {
//				nieuweTegel.updateLandsdeel(buren[0].bepaalLandsdeel(ZUID), 
//						Tegel.NOORD, changed);
//			} 
//		}
//	}

	private Tegel[] getBuren(int rij, int kolom) {
		Tegel[] buren = new Tegel[4];
		int x = startTegel.getX();
		int y = startTegel.getY();
		
		Vector2D coord = new Vector2D(rij-1 - x, kolom - y);
		
		// noord buur
		buren[0] = bepaalTegel(coord);
		
		// west buur
		coord.setXY(rij - x, kolom+1 - y);
		buren[1] = bepaalTegel(coord);
		
		// zuid buur
		coord.setXY(rij+1 -x, kolom - y);
		buren[2] = bepaalTegel(coord);
		
		// oost buur
		coord.setXY(rij - x, kolom-1 - y);
		buren[3] = bepaalTegel(coord);
			
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

		System.out.println(aantal);
		for (int i = 0; i < veld.size(); ++i) {
			kolomVector = veld.get(i);

			for (int j = 0; j < aantal; ++j) {
				kolomVector.add(0, null);
			}
		}
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
		int startTegelX = startTegel.getX();
		int startTegelY = startTegel.getY();

		for (int i = 0; burenGeldig && i < 4; ++i) {
			switch (i) {
			case 0:
				positie.setXY(rij - 1 - startTegelX, kolom - startTegelY);
				break;
			case 1:
				positie.setXY(rij - startTegelX, kolom + 1 - startTegelY);
				break;
			case 2:
				positie.setXY(rij + 1 - startTegelX, kolom - startTegelY);
				break;
			case 3:
				positie.setXY(rij - startTegelX, kolom - 1 - startTegelY);
				break;
			}

			if ((t = bepaalTegel(positie)) != null) {
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

	/** TODO commentaar aanpassen
	 * @param tegel
	 *            Dit is tegel die geplaatst moet worden
	 * @param buur
	 *            Dit is de buur van de tegel die geplaatst moet worden
	 * @param windrichting
	 *            Dit is de kant van de tegel die we willen vergelijken (Noord -
	 *            OOST - ZUID - WEST)
	 * @param windrichtingAndereTegel
	 *            Dit is de kan van de buur waarmee we vergelijken (Noord - OOST
	 *            - ZUID - WEST)
	 * @param windrichtingMidden
	 *            Deze parameter gaat eventuele wegen met elkaar vergelijken
	 *            (wegen lopen altijd in het midden).
	 * @param windrichtingMiddenAndereTegel
	 *            Idem met boven
	 * @param j
	 * @param i
	 * @return True indien beide eenzelfde type landsdeel hebben en ze dus naast
	 *         elkaar mogen liggen. False anders.
	 */
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

	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord, Pion pion) {
		Tegel t = bepaalTegel(tegelCoord);

		return t != null && isPionPlaatsingGeldig(t, tegelCoord, pionCoord)
				&& t.plaatsPion(pionCoord, pion);
	}

	public boolean isTegelPlaatsingGeldig(Tegel tegel, Vector2D coord) {
		int rij = startTegel.getX() + coord.getX();
		int kolom = startTegel.getY() + coord.getY();

		return tegelKanGeplaatstWorden(tegel, rij, kolom);
	}

	public boolean isPionPlaatsingGeldig(Tegel tegel, Vector2D tegelCoord,
			int pionCoord) {
		if (pionCoord < 0 || pionCoord >= Tegel.MAX_GROOTTE || 
				tegel.isPionGeplaatst(pionCoord)) {
			return false;
		}
		
		int rij = tegelCoord.getX() - startTegel.getX();
		int kolom = tegelCoord.getY() - startTegel.getY();
		
		Tegel[] buren = getBuren(rij, kolom);
		char matchLandsdeel = tegel.bepaalLandsdeel(pionCoord).getType();
		
		boolean pionPlaatsingGeldig = true;
		
		if (buren[0] != null) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(NOORD, 
					matchLandsdeel, buren[0], tegelCoord);
		} else if (buren[1] != null && pionPlaatsingGeldig) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(OOST, 
					matchLandsdeel, buren[1], tegelCoord);
		} else if (buren[2] != null && pionPlaatsingGeldig) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(ZUID, 
					matchLandsdeel, buren[2], tegelCoord);
		} else if (buren[3] != null && pionPlaatsingGeldig) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(WEST, 
					matchLandsdeel, buren[3], tegelCoord);
		}
		
		return pionPlaatsingGeldig;
	}

	private boolean isPionPlaatsingGeldig(int windrichting, char matchLandsdeel,
			Tegel tegel, Vector2D tegelCoord) {
		boolean pionPlaatsingGeldig = true;

		switch(windrichting) {
		case NOORD:
			pionPlaatsingGeldig = pionPlaatsingGeldig(tegel, tegelCoord, matchLandsdeel, Tegel.ZUID_WEST,
					Tegel.ZUID, Tegel.ZUID_OOST);
			break;
		case OOST:
			pionPlaatsingGeldig = pionPlaatsingGeldig(tegel, tegelCoord, matchLandsdeel, Tegel.WEST_NOORD,
					Tegel.WEST, Tegel.WEST_ZUID);
			break;
		case ZUID:
			pionPlaatsingGeldig = pionPlaatsingGeldig(tegel, tegelCoord, matchLandsdeel, Tegel.NOORD_OOST,
					Tegel.NOORD, Tegel.NOORD_WEST);
			break;
		case WEST:
			pionPlaatsingGeldig = pionPlaatsingGeldig(tegel, tegelCoord, matchLandsdeel, Tegel.OOST_ZUID,
					Tegel.OOST, Tegel.OOST_NOORD);
			break;
		}
		
		return pionPlaatsingGeldig;		
	}

	private boolean pionPlaatsingGeldig(Tegel tegel, Vector2D tegelCoord,
			char matchLandsdeel, int windrichting, int windrichting2, 
			int windrichting3) {
		boolean pionPlaatsingGeldig = true;
		Landsdeel vorigeLd = null;
		Landsdeel huidigeLd;
		huidigeLd = tegel.bepaalLandsdeel(windrichting);
		
		if (huidigeLd.getType() == matchLandsdeel) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(tegel, tegelCoord,
					windrichting);
		}
		
		vorigeLd = huidigeLd;
		huidigeLd = tegel.bepaalLandsdeel(windrichting2);
		if (pionPlaatsingGeldig && vorigeLd != huidigeLd && huidigeLd.getType() == matchLandsdeel) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(tegel, tegelCoord,
					windrichting2);
		}
		
		vorigeLd = huidigeLd;
		huidigeLd = tegel.bepaalLandsdeel(windrichting3);
		if (pionPlaatsingGeldig && vorigeLd != huidigeLd && huidigeLd.getType() == matchLandsdeel) {
			pionPlaatsingGeldig = isPionPlaatsingGeldig(tegel, tegelCoord,
					windrichting3);
		}
		
		return pionPlaatsingGeldig;
	}

	// TODO Moeten hier niet de coordinaten weer meegegeven worden?
	// hogere lagen hebben toch geen nood aan het opslaan van de Tegel objecten?
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

		int x = startTegel.getX() + coord.getX();
		int y = startTegel.getY() + coord.getY();

		// ongedefinieerde positie
		if (x < 0 || x >= veld.size() || y < 0 || y >= veld.get(x).size()) {
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
		// oogpunt.reset();
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
}

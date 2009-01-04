// TODO WEG berekenen!!!!
package actua;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.Coordinator;

public class PuntenVerwerker {
	private static final int WEG_PUNTEN = 1;
	private static final int STAD_PUNTEN = 2;
	private static final int KLOOSTER_PUNTEN = 9;
	private static final int WEI_PUNTEN = 4;
	private long spelerRood;
	private long spelerBlauw;
	private long spelerWit;
	private long spelerGeel;
	private long spelerOranje;
	private int aantalTegels;
	
	public PuntenVerwerker() {
		spelerRood = 0;
		spelerBlauw = 0;
		spelerWit = 0;
		spelerGeel = 0;
		spelerOranje = 0;
	}
	
	public long getSpelerRood() {
		return spelerRood;
	}
	
	public long getSpelerBlauw() {
		return spelerBlauw;
	}
	
	public long getSpelerWit() {
		return spelerWit;
	}
	
	public long getSpelerGeel() {
		return spelerGeel;
	}
	
	public long getSpelerOranje() {
		return spelerOranje;
	}
	
	// de return is een array met alle pionnen (kleuren)
	// de functie die deze method aanroept moet het zo maken dat
	// de pionnen terug gegeven worden aan de juiste spelers
	public ArrayList<Character> updateScore(Vector2D coord, TegelVeld veld) {
		aantalTegels = 0;
		ArrayList<Character> pionnen = new ArrayList<Character>();
		
		Tegel geplaatsteTegel = veld.get(coord);
		if (geplaatsteTegel != null) {
			boolean checked[] = new boolean[Tegel.MAX_GROOTTE];

			for (int i = 0; i < Tegel.MAX_GROOTTE; ++i) {
				if (!checked[i]) {
					pionnen.addAll(updateScore(coord, veld, geplaatsteTegel, i));
					updateChecked(checked, geplaatsteTegel, i);
				}
			}
		}
		
		return pionnen;
	}

	private void updateChecked(boolean[] checked, Tegel geplaatsteTegel, int i) {
		Landsdeel ld = geplaatsteTegel.bepaalLandsdeel(i);
		
		for (int j = 0; j < Tegel.MAX_GROOTTE; ++j) {
			if (ld == geplaatsteTegel.bepaalLandsdeel(j)) {
				checked[j] = true;
			}
		}
	}

	private ArrayList<Character> updateScore(Vector2D coord, TegelVeld veld,
			Tegel geplaatsteTegel, int i) {
		Landsdeel ld = geplaatsteTegel.bepaalLandsdeel(i);
		ArrayList<Character> pionnen = new ArrayList<Character>();	
		
		if (ld.getType() == Landsdeel.KLOOSTER && ld.isPionGeplaatst()) {
			pionnen = updateScoreKlooster(veld, coord, i, ld);
		} else if (ld.getType() == Landsdeel.STAD) {
			ArrayList<Tegel> checked = new ArrayList<Tegel>();
			if(updateScoreStad(veld, coord, ld, pionnen, checked)) { 
				updateScore(pionnen, STAD_PUNTEN);
			}
		} else if (ld.getType() == Landsdeel.WEG) {
			if (updateScoreWeg(veld, coord, i, ld, pionnen)){
				updateScore(pionnen, WEG_PUNTEN);
			}
		}
		
		return pionnen;
	}

	private boolean updateScoreWeg(TegelVeld veld,
			Vector2D coord, int inGaandePos, Landsdeel ld, ArrayList<Character> pionnen) {
		aantalTegels = 1;
		
		if (ld.isPionGeplaatst()) {
			pionnen.add(new Character(ld.neemPionnenTerug()));
		}
		
		int uitgaandePos = -1; // hier gaat de weg over van deze tegel naar zijn buur
		Tegel t = veld.get(coord);		
		if (t.bepaalLandsdeel(Tegel.MIDDEN) == ld) { // is het midden ook een weg?
			uitgaandePos = vindUitgaanWeg(t, inGaandePos, ld);  // het midden is een weg -> 
																// zoek waar de weg uit de tegel gaat.
		}
		
		boolean gebiedCompleet = vindBuren(inGaandePos, pionnen, veld, ld, coord);
		
		if (uitgaandePos != -1 && gebiedCompleet) {
			gebiedCompleet = vindBuren(uitgaandePos, pionnen, veld, ld, coord);
		}		
		
		return gebiedCompleet;
	}

	private void updateScore(ArrayList<Character> pionnen, int punten) {
		int score = punten*aantalTegels;
		int[] aantalPionnen = new int[5];
		int maximum = -1;
		
		for (int i = 0; i < pionnen.size(); ++i) {
			switch(pionnen.get(i).charValue()) {
			case Speler.SPELER_ROOD:
				++aantalPionnen[0];
				maximum = (aantalPionnen[0] > maximum)? aantalPionnen[0] : maximum;
				break;
			case Speler.SPELER_BLAUW:
				++aantalPionnen[1];
				maximum = (aantalPionnen[1] > maximum)? aantalPionnen[1] : maximum;
				break;
			case Speler.SPELER_WIT:
				++aantalPionnen[2];
				maximum = (aantalPionnen[2] > maximum)? aantalPionnen[2] : maximum;
				break;
			case Speler.SPELER_GEEL:
				++aantalPionnen[3];
				maximum = (aantalPionnen[3] > maximum)? aantalPionnen[3] : maximum;
				break;
			case Speler.SPELER_ORANJE:
				++aantalPionnen[4];
				maximum = (aantalPionnen[4] > maximum)? aantalPionnen[4] : maximum;
				break;
			}
		}
		
		if(maximum == aantalPionnen[0]) {
			spelerRood += score;
		}
		
		if(maximum == aantalPionnen[1]) {
			spelerBlauw += score;
		}
		
		if(maximum == aantalPionnen[2]) {
			spelerWit+= score;
		}
		
		if(maximum == aantalPionnen[3]) {
			spelerGeel+= score;
		}
		
		if(maximum == aantalPionnen[4]) {
			spelerOranje += score;
		}
	}

	private boolean vindBuren(int wegPos, ArrayList<Character> pionnen,
			TegelVeld veld, Landsdeel ld, Vector2D coord) {
		int x = coord.getX();
		int y = coord.getY();
		Tegel t;
		boolean eindeGevonden = true;
		
		switch(wegPos) {
		case Tegel.NOORD:
			if (null != (t = veld.get(new Vector2D(x-1, y)))) {
				++aantalTegels;
				eindeGevonden = updateScoreWeg(veld, coord, Tegel.ZUID, 
						t.bepaalLandsdeel(Tegel.ZUID), pionnen);
			}
			break;
		case Tegel.OOST:
			if (null != (t = veld.get(new Vector2D(x, y+1)))) {
				++aantalTegels;
				eindeGevonden = updateScoreWeg(veld, coord, Tegel.WEST, 
						t.bepaalLandsdeel(Tegel.WEST), pionnen);				
			}
			break;
		case Tegel.ZUID:
			if (null != (t = veld.get(new Vector2D(x+1, y)))) {
				++aantalTegels;
				eindeGevonden = updateScoreWeg(veld, coord, Tegel.NOORD, 
						t.bepaalLandsdeel(Tegel.NOORD), pionnen);
			}
			break;
		case Tegel.WEST:
			if (null != (t = veld.get(new Vector2D(x, y-1)))) {
				++aantalTegels;
				eindeGevonden = updateScoreWeg(veld, coord, Tegel.OOST, 
						t.bepaalLandsdeel(Tegel.WEST), pionnen);
			}
			break;
		}
		return eindeGevonden;
	}

	private int vindUitgaanWeg(Tegel t, int inGaandePos, Landsdeel ld) {
		int uitGaandeWeg = -1;
		
		if (Tegel.NOORD != inGaandePos && t.bepaalLandsdeel(Tegel.NOORD) == ld) {
			uitGaandeWeg = Tegel.NOORD;
		}
		
		if (uitGaandeWeg == -1 && Tegel.OOST!= inGaandePos &&
				t.bepaalLandsdeel(Tegel.OOST) == ld) {
			uitGaandeWeg = Tegel.OOST;
		}
		
		if (uitGaandeWeg == -1 && Tegel.ZUID!= inGaandePos &&
				t.bepaalLandsdeel(Tegel.ZUID) == ld) {
			uitGaandeWeg = Tegel.ZUID;
		}
		
		if (uitGaandeWeg == -1 && Tegel.WEST!= inGaandePos &&
				t.bepaalLandsdeel(Tegel.WEST) == ld) {
			uitGaandeWeg = Tegel.WEST;
		}
		
		return uitGaandeWeg;
	}

	private boolean updateScoreStad(TegelVeld veld,
			Vector2D coord, Landsdeel ld, ArrayList<Character> pionnen, ArrayList<Tegel> checked) {
		if (ld.isPionGeplaatst()) {
			pionnen.add(ld.neemPionnenTerug());
		}
		
		++aantalTegels;
		
		Tegel t = veld.get(coord);
		checked.add(t);
		int x = coord.getX();
		int y = coord.getY();
		Vector2D coordBuur = new Vector2D();
		
		boolean eindeGevonden = true;
		
		if (t.bepaalLandsdeel(Tegel.NOORD) == ld) {
			coordBuur.setXY(x-1, y);
			eindeGevonden = updateScoreStad(veld, coordBuur, ld, Tegel.ZUID, pionnen, checked);
		}

		if (eindeGevonden && t.bepaalLandsdeel(Tegel.OOST) == ld) {
			coordBuur.setXY(x, y+1);
			eindeGevonden = updateScoreStad(veld, coordBuur, ld, Tegel.WEST, pionnen, checked);
		}

		if (eindeGevonden && t.bepaalLandsdeel(Tegel.ZUID) == ld) {
			coordBuur.setXY(x+1, y);
			eindeGevonden = updateScoreStad(veld, coordBuur, ld, Tegel.NOORD, pionnen, checked);
		}

		if (eindeGevonden && t.bepaalLandsdeel(Tegel.WEST) == ld) {
			coordBuur.setXY(x, y-1);
			eindeGevonden = updateScoreStad(veld, coordBuur, ld, Tegel.OOST, pionnen, checked);
		}
		
		return eindeGevonden;
	}

	private boolean updateScoreStad(TegelVeld veld,
			Vector2D coordBuur, Landsdeel ld, int windrichting, ArrayList<Character> pionnen,
			ArrayList<Tegel> checked) {
		int x = coordBuur.getX();
		int y = coordBuur.getY();
		Tegel tegel = veld.get(coordBuur);
		if (tegel == null) {
			return false;
		}
		
		Landsdeel ldWindrichting = tegel.bepaalLandsdeel(windrichting);
		
		boolean eindeGevonden = true;
		
		if (ld.getType() == ldWindrichting.getType() && !alGechecked(checked, tegel)) {
			eindeGevonden = updateScoreStad(veld, coordBuur, ldWindrichting, pionnen, checked);
		}
		
		return eindeGevonden;
	}

	private boolean alGechecked(ArrayList<Tegel> checked, Tegel tegel) {
		for (int i = 0; i < checked.size(); ++i) {
			if (tegel == checked.get(i)) {
				return true;
			}
		}
		
		return false;
	}

	private ArrayList<Character> updateScoreKlooster(TegelVeld veld,
			Vector2D coord, int i, Landsdeel ld) {
		ArrayList<Character> pionnen = new ArrayList<Character>();
		if (telBuren(veld, coord) == 4) {
			char pion = ld.neemPionnenTerug();
			pionnen.add(new Character(pion));
			switch(pion) {
			case Speler.SPELER_BLAUW:
				spelerBlauw += KLOOSTER_PUNTEN;
				break;
			case Speler.SPELER_ROOD:
				spelerRood += KLOOSTER_PUNTEN;
				break;
			case Speler.SPELER_WIT:
				spelerWit += KLOOSTER_PUNTEN;
				break;
			case Speler.SPELER_GEEL:
				spelerGeel += KLOOSTER_PUNTEN;
				break;
			case Speler.SPELER_ORANJE:
				spelerOranje += KLOOSTER_PUNTEN;
				break;
			}
		}
		
		return pionnen;
	}

	private int telBuren(TegelVeld veld, Vector2D coord) {
		int aantalBuren = 0;
		int x = coord.getX();
		int y = coord.getY();
		
		// NOORD buur
		if (null != veld.get(new Vector2D(x-1, y))) {
			++aantalBuren;
		}
		
		// OOST buur
		if (null != veld.get(new Vector2D(x, y+1))) {
			++aantalBuren;
		}
	
		// ZUID buur
		if (null != veld.get(new Vector2D(x+1, y))) {
			++aantalBuren;
		}
		
		// WEST buur
		if (null != veld.get(new Vector2D(x, y-1))) {
			++aantalBuren;			
		}
		
		return aantalBuren;
	}

}
package actua;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.Coordinator;

public class Score {
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
	
	public Score() {
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
	
	public void updateScore(Vector2D coord, ArrayList<ArrayList<Tegel>> veld) {
		int x = coord.getX();
		int y = coord.getY();
		aantalTegels = 0;
		
		if (coord == null || veld == null || veld.size() <= x || x < 0 || y < 0 ||
				veld.get(x).size() <= y) {
			return;
		}
		
		Tegel geplaatsteTegel = veld.get(x).get(y);
		boolean checked[] = new boolean[Tegel.MAX_GROOTTE];

		for (int i = 0; i < Tegel.MAX_GROOTTE; ++i) {
			if (!checked[i]) {
				updateScore(coord, veld, geplaatsteTegel, i);
				updateChecked(checked, geplaatsteTegel, i);
			}
		}
	}

	private void updateChecked(boolean[] checked, Tegel geplaatsteTegel, int i) {
		Landsdeel ld = geplaatsteTegel.bepaalLandsdeel(i);
		
		for (int j = 0; j < Tegel.MAX_GROOTTE; ++j) {
			if (ld == geplaatsteTegel.bepaalLandsdeel(j)) {
				checked[j] = true;
			}
		}
	}

	private void updateScore(Vector2D coord, ArrayList<ArrayList<Tegel>> veld,
			Tegel geplaatsteTegel, int i) {
		Landsdeel ld = geplaatsteTegel.bepaalLandsdeel(i);
		ArrayList<Pion> pionnen = new ArrayList<Pion>();	
		
		if (ld.getType() == Landsdeel.KLOOSTER && ld.isPionGeplaatst()) {
			updateScoreKlooster(veld, coord, i, ld);
		} else if (ld.getType() == Landsdeel.STAD) {
			ArrayList<Tegel> checked = new ArrayList<Tegel>();
			if(updateScoreStad(veld, coord, ld, pionnen, checked)) { 
				updateScore(pionnen, STAD_PUNTEN);
			}
		} else if (ld.getType() == Landsdeel.WEG) {
			updateScoreWeg(veld, coord, i, ld, pionnen);
		}
	}

	private void updateScoreWeg(ArrayList<ArrayList<Tegel>> veld,
			Vector2D coord, int inGaandePos, Landsdeel ld, ArrayList<Pion> pionnen) {
		aantalTegels = 1;
		
		if (ld.isPionGeplaatst()) {
			pionnen.add(ld.neemPionnenTerug());
		}
		
		int uitgaandePos = -1; // hier gaat de weg over van deze tegel naar zijn buur
		Tegel t = veld.get(coord.getX()).get(coord.getY());		
		if (t.bepaalLandsdeel(Tegel.MIDDEN) == ld) {
			uitgaandePos = vindUitgaanWeg(t, inGaandePos, ld);
		}
		
		boolean gebiedCompleet = vindBuren(inGaandePos, pionnen, veld, ld, coord);
		
		if (uitgaandePos != -1 && gebiedCompleet) {
			gebiedCompleet = vindBuren(uitgaandePos, pionnen, veld, ld, coord);
		}
		
		if (gebiedCompleet) {
			updateScore(pionnen, WEG_PUNTEN);
		}
	}

	private void updateScore(ArrayList<Pion> pionnen, int wegPunten) {
		int score = wegPunten*aantalTegels;
		int[] aantalPionnen = new int[5];
		int maximum = -1;
		
		for (int i = 0; i < pionnen.size(); ++i) {
			switch(pionnen.get(i).getKleur()) {
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

	private boolean vindBuren(int wegPos, ArrayList<Pion> pionnen,
			ArrayList<ArrayList<Tegel>> veld, Landsdeel ld, Vector2D coord) {
		int x = coord.getX();
		int y = coord.getY();
		Tegel t;		
		switch(wegPos) {
		case Tegel.NOORD:
			if (x-1 >= 0) {
				++aantalTegels;
				t = veld.get(x-1).get(y);
				if (t.isPionGeplaatst(Tegel.ZUID)) {
					pionnen.add(t.neemPionTerug(Tegel.ZUID));
				}
				updateScoreWeg(veld, coord, Tegel.ZUID, ld, pionnen);
			}
			break;
		case Tegel.OOST:
			if (y+1 < veld.get(x).size()) {
				++aantalTegels;
				t = veld.get(x).get(y+1);
				if (t.isPionGeplaatst(Tegel.WEST)) {
					pionnen.add(t.neemPionTerug(Tegel.WEST));
				}
				updateScoreWeg(veld, coord, Tegel.ZUID, ld, pionnen);				
			}
			break;
		case Tegel.ZUID:
			if (x+1 < veld.size()) {
				++aantalTegels;
				t = veld.get(x+1).get(y);
				if (t.isPionGeplaatst(Tegel.NOORD)) {
					pionnen.add(t.neemPionTerug(Tegel.NOORD));
				}
				updateScoreWeg(veld, coord, Tegel.ZUID, ld, pionnen);
			}
			break;
		case Tegel.WEST:
			if (y-1 >= 0) {
				++aantalTegels;
				t = veld.get(x).get(y-1);
				if (t.isPionGeplaatst(Tegel.OOST)) {
					pionnen.add(t.neemPionTerug(Tegel.OOST));
				}
				updateScoreWeg(veld, coord, Tegel.OOST, ld, pionnen);
			}
			break;
		}
		return false;
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

	private boolean updateScoreStad(ArrayList<ArrayList<Tegel>> veld,
			Vector2D coord, Landsdeel ld, ArrayList<Pion> pionnen, ArrayList<Tegel> checked) {
		if (ld.isPionGeplaatst()) {
			pionnen.add(ld.neemPionnenTerug());
		}
		
		++aantalTegels;
		
		Tegel t = veld.get(coord.getX()).get(coord.getY());
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

	private boolean updateScoreStad(ArrayList<ArrayList<Tegel>> veld,
			Vector2D coordBuur, Landsdeel ld, int windrichting, ArrayList<Pion> pionnen,
			ArrayList<Tegel> checked) {
		int x = coordBuur.getX();
		int y = coordBuur.getY();
		Tegel tegel = veld.get(x).get(y);
		Landsdeel ldWindrichting = tegel.bepaalLandsdeel(windrichting);
		
		boolean eindeGevonden = true;
		
		if (x >= 0 && x < veld.size() && y >= 0 && y < veld.get(x).size() && 
				ld.getType() == ldWindrichting.getType() && !alGechecked(checked, tegel)) {
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

	private void updateScoreKlooster(ArrayList<ArrayList<Tegel>> veld,
			Vector2D coord, int i, Landsdeel ld) {
		if (telBuren(veld, coord) == 4) {
			Pion pion = ld.neemPionnenTerug();
			pion.zetGeplaatst(false);
			switch(pion.getKleur()) {
			case Speler.SPELER_BLAUW:
				spelerBlauw += 8;
				break;
			case Speler.SPELER_ROOD:
				spelerRood += 8;
				break;
			case Speler.SPELER_WIT:
				spelerWit += 8;
				break;
			case Speler.SPELER_GEEL:
				spelerGeel += 8;
				break;
			case Speler.SPELER_ORANJE:
				spelerOranje += 8;
				break;
			}
		}
	}

	private int telBuren(ArrayList<ArrayList<Tegel>> veld, Vector2D coord) {
		int aantalBuren = 0;
		int x = coord.getX();
		int y = coord.getY();
		
		// NOORD buur
		if (x-1 >= 0) {
			++aantalBuren;
		}
		
		// OOST buur
		if (veld.get(x).size() > y+1) {
			++aantalBuren;
		}
	
		// ZUID buur
		if (veld.size() > x) {
			++aantalBuren;
		}
		
		// WEST buur
		if (y-1 > 0) {
			++aantalBuren;			
		}
		
		return aantalBuren;
	}

}

package actua;

/**
 * @author Sam
 * 
 */
/*
 *  _______________
 * |0\1 | 2  |3/4  |
 * |----|----|-----|
 * | 5  | 6  | 7   |
 * |---------------|
 * |8/9 | 10 |11\12|
 * |_______________|
 * 
 */
public class Tegel {
	// een tegel is verdeeld in een 3x3 matrix van landsdelen
	private static final int MAX_GROOTTE = 13;
	private static final short MAX_DRAAIING = 4;
	
	// Deze constanten geven andere klassen de mogelijkheid om zijkanten van de tegel aan te duiden,
	// zonder te weten hoe ze worden voorgesteld.
	public static final int NOORD = 1;
	public static final int OOST = 4;
	public static final int ZUID = 9;
	public static final int WEST = 0;		
	public static final int MIDDEN_NOORD = 2;
	public static final int MIDDEN_OOST = 7;
	public static final int MIDDEN_ZUID = 10;
	public static final int MIDDEN_WEST = 5;
	private static int teller = 0;
	private static final int MIDDEN = 6;	
	
	private short orientatie;
//	private Landsdeel[] landsdelen;
	private Landsdeel[] landsdelen;
	
	public Tegel() {
		
	}

	// TODO verwijderen??
	public Tegel(Landsdeel[] landsdelen) {
		orientatie = 0; // beginopstelling van de tegel
		setLandsdelen(landsdelen);
	}
	
	public Tegel(char[] landsdelen) {
		++teller;
		orientatie = 0;
		setLandsdelen(landsdelen);
	}

	private void setLandsdelen(char[] landsdelenChar) {
		landsdelen = new Landsdeel[landsdelenChar.length];
		
		vulLandsdelen(WEST, landsdelenChar);
//		vulLandsdelen(NOORD, landsdelenChar);
//		vulLandsdelen(OOST, landsdelenChar);
//		vulLandsdelen(ZUID, landsdelenChar);
//		vulLandsdelen(MIDDEN, landsdelenChar);
		
		for (int i = 0; i < 13; ++i) {
			if (landsdelen[i] == null) {
				System.err.println("SHIT: " + teller + ", " + i);
			}
		}
	}

	
	private void vulLandsdelen(int beginPos, char[] landsdelenChar) {
		// dit kan enkel en alleen als het midden een klooster is
		if (landsdelen[beginPos] == null) {
			landsdelen[beginPos] = new Landsdeel(landsdelenChar[beginPos]);
			vindGelijkeLandsdelen(beginPos, landsdelenChar);
		}
	}

	private void vindGelijkeLandsdelen(int i, char[] landsdelenChar) {
		/**
		 * Het volstaat om het landsdeel onder en rechts van landsdeel i na te kijken. Diegene erboven zijn al
		 * een bekeken
		 */
		
		// rechts
		if (i+1 < landsdelenChar.length && landsdelen[i+1] == null && landsdelenChar[i] == landsdelenChar[i+1]) {
			landsdelen[i+1] = landsdelen[i];
			vindGelijkeLandsdelen(i+1, landsdelenChar);
		} else if (i+1 < landsdelenChar.length && landsdelen[i+1] == null){
			landsdelen[i+1] = new Landsdeel(landsdelenChar[i+1]);
			vindGelijkeLandsdelen(i+1, landsdelenChar);
		}
		
		// onder
		int volgende = -1;
		
		if (i == 2 || i == 6) {
			volgende = i+4;
		} else if (i == 0 || i == 7) {
			volgende = i+5;
		} else if (i == 3 || i == 8) {
			volgende = i+1;
		} else if (i == 12) {
			volgende = 11;
		} else if (i == 4 || i == 5) {			
			volgende = i+3;
		}
		
		if (volgende != -1 && landsdelen[volgende] == null && landsdelenChar[i] == landsdelenChar[volgende]) {
			landsdelen[volgende] = landsdelen[i];
			vindGelijkeLandsdelen(volgende, landsdelenChar);
		} else if (volgende != -1 && landsdelen[volgende] == null) {
			landsdelen[volgende] = new Landsdeel(landsdelenChar[volgende]);
			vindGelijkeLandsdelen(volgende, landsdelenChar);
		}
	}
	
	// TODO verwijderen???
	private void setLandsdelen(Landsdeel[] landsdelen) {
		if (landsdelen != null && landsdelen.length != MAX_GROOTTE) {
			System.err.println("Ongeldige matrix van landsdelen");
		}
		
		this.landsdelen = landsdelen;
	}

	public short getOrientatie() {
		return orientatie;
	}

	public void getSoortTegel() {
	}

//	public void setSoortTegel(String soortTegel) {
//		this.soortTegel = soortTegel;
//	}

	public Landsdeel bepaalLandsdeel(int pos) {
		// er zijn foute coördinaten zijn doorgegeven.
		if (!coordinatenOk(pos)) {
			System.err.println("Foute landsdeel coördinaten");
			return null;
		}
		
		return landsdelen[pos];
	}

	public boolean plaatsPion(int pos) {
		// er zijn foute coördinaten zijn doorgegeven.
		if (!coordinatenOk(pos)) {
			return false;
		}
		
		// Op dit moment is pos zeker een geldige pion plaatsing
		landsdelen[pos].plaatsPion();
		
		return true;
	}

//	public boolean isGeldigePlaatsing(Vector2D pos) {
//		int doelPos = pos.getX()*3 + pos.getY();
//		
//		if (isPionGeplaatst(doelPos)) {
//			return false;
//		} 
//		
////		return !isPionGeplaatst(NOORD, doelPos) && 
////			   !isPionGeplaatst(OOST, doelPos) &&
////			   !isPionGeplaatst(ZUID, doelPos) && 
////			   !isPionGeplaatst(WEST, doelPos);
//		return false;
//	}

//	public int isPionGeplaatst(char windrichting, int doelPos) {
//		char matchLandsdeel = landsdelen[doelPos];
//		
//		boolean isAnderLandsdeel = false;
//		boolean pionGevonden= false;
//		boolean einde;
//		boolean zuidOfOost = windrichting == ZUID || windrichting == OOST;
//		int i;
//
//		if (zuidOfOost) {
//			i = doelPos + 1;
//			einde = i >= MAX_RIJ_GROOTTE;		
//		} else {
//			i = doelPos - 1;
//			einde = i < 0;
//		}
//		
//		while(!einde && !isAnderLandsdeel && !pionGevonden) {
//			if (landsdelen[i] == matchLandsdeel + 'a' - 'A') {
//				pionGevonden = true;
//			} else if (landsdelen[i] != matchLandsdeel) {
//				isAnderLandsdeel = true;
//			}
//			
//			if(zuidOfOost) {
//				++i;
//				einde = i >= MAX_RIJ_GROOTTE;
//			} else {
//				--i;
//				einde = i < 0;
//			}
//		}
//		
//		if (pionGevonden) {
//			return TRUE;
//		} else if (isAnderLandsdeel) {
//			return ANDER_LANDSDEEL_GEVONDEN;
//		} else {
//			return FALSE;
//		}
//	}

	// als het doelLandsdeel een hoofdletter is dan staat er al een pion
	// op deze positie
	public boolean isPionGeplaatst(int pos) { 
		// bij foute coordinaten mag er zeker geen pion gezet worden!
		if (!coordinatenOk(pos)) {
			return true;
		}
		
		return landsdelen[pos].isPionGeplaatst();
	}

	/**
	 * @param richting
	 *            True wijzerzin draaien. 
	 *            False tegenwijzerzin draaien.
	 */
	public void draaiTegel(boolean richting) {
		if (richting) {
			orientatie = (short)((orientatie + 1) % MAX_DRAAIING); 
		} else {
			orientatie = (short)((MAX_DRAAIING + orientatie -1) % MAX_DRAAIING);
		}
	}
	
	private boolean coordinatenOk(int pos) {
		// de foute coördinaten zijn doorgegeven.
		if (pos < 0 || pos > MAX_GROOTTE) {
			return false;
		}
		
		return true;
	}

	public Tegel clone() {
		return null;
	}
	
	public boolean equals(Tegel t) {
		boolean orientatieB, soortTegelB = true;

		orientatieB = orientatie == t.orientatie;
		
		return orientatieB && soortTegelB;
	}
}

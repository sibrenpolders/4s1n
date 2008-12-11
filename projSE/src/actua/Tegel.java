package actua;

/**
 * 
 */
/*
 * _______________ |0\1 | 2 |3/4 | |----|----|-----| | 5 | 6 | 7 |
 * |---------------| |8/9 | 10 |11\12| |_______________|
 */
public class Tegel {
	// een tegel is verdeeld in een 3x3 matrix van landsdelen
	public static final int MAX_GROOTTE = 13;
	private static final short MAX_DRAAIING = 4;
	// Deze constanten geven andere klassen de mogelijkheid om zijkanten van de
	// tegel aan te duiden,
	// zonder te weten hoe ze worden voorgesteld.
	public static final int NOORD_WEST = 1;
	public static final int NOORD = 2;	
	public static final int NOORD_OOST = 3;
	public static final int OOST_NOORD = 4;
	public static final int OOST = 7;
	public static final int OOST_ZUID = 12;
	public static final int ZUID_OOST = 11;
	public static final int ZUID = 10;
	public static final int ZUID_WEST = 9;
	public static final int WEST_ZUID = 8;
	public static final int WEST = 5;
	public static final int WEST_NOORD = 0;
	
	// TODO nog nakijken maar dacht dat dit een testvariabele was -> verwijderen
	// dus
	private static int teller = 0;
	private static final int MIDDEN = 6;

	private short orientatie;
	// private Landsdeel[] landsdelen;
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
		setLandsdelen(landsdelen, null, null, null, null);
	}

	public Tegel(char[] landsdelen, Tegel noord, Tegel oost, Tegel zuid,
			Tegel west) {
		++teller;
		orientatie = 0;
		setLandsdelen(landsdelen, noord, oost, zuid, west);
	}

	private void setLandsdelen(char[] landsdelenChar, Tegel noord, Tegel oost,
			Tegel zuid, Tegel west) {
		landsdelen = new Landsdeel[landsdelenChar.length];

		vulLandsdelen(WEST, landsdelenChar, noord, oost, zuid, west);
		// vulLandsdelen(NOORD, landsdelenChar);
		// vulLandsdelen(OOST, landsdelenChar);
		// vulLandsdelen(ZUID, landsdelenChar);
		// vulLandsdelen(MIDDEN, landsdelenChar);

		for (int i = 0; i < 13; ++i) {
			if (landsdelen[i] == null) {
				System.err.println("SHIT: " + teller + ", " + i);
			}
		}
	}

	private void vulLandsdelen(int beginPos, char[] landsdelenChar,
			Tegel noord, Tegel oost, Tegel zuid, Tegel west) {
		if (west == null) {
			landsdelen[beginPos] = new Landsdeel(landsdelenChar[beginPos]);
			// west tegel is niet null dus deze tegel heeft aan zijn west-zijde
			// dezelfde landsdelen als de west buur in het oosten heeft
		} else {
			landsdelen[beginPos] = west.bepaalLandsdeel(OOST);
		}

		vindGelijkeLandsdelen(beginPos, landsdelenChar, noord, oost, zuid, west);
	}

	private void vindGelijkeLandsdelen(int i, char[] landsdelenChar,
			Tegel noord, Tegel oost, Tegel zuid, Tegel west) {
		/**
		 * Het volstaat om het landsdeel onder en rechts van landsdeel i na te
		 * kijken. Diegene erboven zijn al een bekeken
		 */

		// rechts heeft het zelfde lansdeel
		if (i + 1 < landsdelenChar.length && landsdelen[i + 1] == null
				&& landsdelenChar[i] == landsdelenChar[i + 1]) {
			landsdelen[i + 1] = landsdelen[i];
			vindGelijkeLandsdelen(i + 1, landsdelenChar, noord, oost, zuid,
					west);
		} else if (i + 1 < landsdelenChar.length && landsdelen[i + 1] == null) {
			landsdelen[i + 1] = getLandsDeel(i + 1, landsdelenChar, noord,
					oost, zuid, west);
			vindGelijkeLandsdelen(i + 1, landsdelenChar, noord, oost, zuid,
					west);
		}

		// onder
		int volgende = -1;

		if (i == 2 || i == 6) {
			volgende = i + 4;
		} else if (i == 0 || i == 7) {
			volgende = i + 5;
		} else if (i == 3 || i == 8) {
			volgende = i + 1;
		} else if (i == 12) {
			volgende = 11;
		} else if (i == 4 || i == 5) {
			volgende = i + 3;
		}

		// onder heeft een gelijk landsdeel
		if (volgende != -1 && landsdelen[volgende] == null
				&& landsdelenChar[i] == landsdelenChar[volgende]) {
			landsdelen[volgende] = landsdelen[i];
			vindGelijkeLandsdelen(volgende, landsdelenChar, noord, oost, zuid,
					west);
		} else if (volgende != -1 && landsdelen[volgende] == null) {
			landsdelen[volgende] = getLandsDeel(volgende, landsdelenChar,
					noord, oost, zuid, west);
			vindGelijkeLandsdelen(volgende, landsdelenChar, noord, oost, zuid,
					west);
		}
	}

	private Landsdeel getLandsDeel(int i, char[] landsdelenChar, Tegel noord,
			Tegel oost, Tegel zuid, Tegel west) {

		Landsdeel nieuwLandsdeel;
		
		switch (i) {
		// noord kant van de nieuwe tegel heeft dezelfde landsdelen als de zuid
		// kant van de noorder buur tegel
		case NOORD_WEST:
			nieuwLandsdeel = noord.bepaalLandsdeel(ZUID_WEST);
			break;
		case NOORD:
			nieuwLandsdeel = noord.bepaalLandsdeel(ZUID);
			break;
		case NOORD_OOST:
			nieuwLandsdeel = noord.bepaalLandsdeel(ZUID_OOST);
			break;
		// oost kant van de nieuwe tegel heeft dezelfde landsdelen als de west
		// kant van de oost buur tegel
		case OOST_NOORD:
			nieuwLandsdeel = noord.bepaalLandsdeel(WEST_NOORD);
			break;
		case OOST:
			nieuwLandsdeel = noord.bepaalLandsdeel(WEST);
			break;
		case OOST_ZUID:
			nieuwLandsdeel = noord.bepaalLandsdeel(WEST_ZUID);
			break;
		// zuid kant van de nieuwe tegel heeft dezelfde landsdelen als de noord
		// kant van de zuid buur tegel
		case ZUID_OOST:
			nieuwLandsdeel = noord.bepaalLandsdeel(NOORD_OOST);
			break;
		case ZUID:
			nieuwLandsdeel = noord.bepaalLandsdeel(NOORD);
			break;
		case ZUID_WEST:
			nieuwLandsdeel = noord.bepaalLandsdeel(NOORD_WEST);
			break;
		// west kant van de nieuwe tegel heeft dezelfde landsdelen als de oost
		// kant van de west buur tegel			
		case WEST_ZUID:
			nieuwLandsdeel = noord.bepaalLandsdeel(OOST_ZUID);
			break;
		case WEST:
			nieuwLandsdeel = noord.bepaalLandsdeel(OOST);
			break;
		case WEST_NOORD:
			nieuwLandsdeel = noord.bepaalLandsdeel(OOST_NOORD);
			break;
		default:
			nieuwLandsdeel = new Landsdeel(landsdelenChar[i]);
		}

		return nieuwLandsdeel;
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

	// public void setSoortTegel(String soortTegel) {
	// this.soortTegel = soortTegel;
	// }

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
//		landsdelen[pos].plaatsPion();

		return true;
	}

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
	 *            True wijzerzin draaien. False tegenwijzerzin draaien.
	 */
	public void draaiTegel(boolean richting) {
		if (richting) {
			orientatie = (short) ((orientatie + 1) % MAX_DRAAIING);
		} else {
			orientatie = (short) ((MAX_DRAAIING + orientatie - 1) % MAX_DRAAIING);
		}
	}

	private boolean coordinatenOk(int pos) {
		// de foute coördinaten zijn doorgegeven.
		if (pos < 0 || pos > MAX_GROOTTE) {
			return false;
		}

		return true;
	}

	// TODO invullen!!!!
	public Tegel clone() {
		return null;
	}

	public boolean equals(Tegel t) {
		boolean orientatieB, soortTegelB = true;

		orientatieB = orientatie == t.orientatie;

		return orientatieB && soortTegelB;
	}

	public void updateLandsdeel(int zijde, Tegel nieuweTegel) {
		switch(zijde) {
		case NOORD:
			updateLd(landsdelen[NOORD_WEST], nieuweTegel.landsdelen[ZUID_WEST]);
			updateLd(landsdelen[NOORD], nieuweTegel.landsdelen[ZUID]);
			updateLd(landsdelen[NOORD_OOST], nieuweTegel.landsdelen[ZUID_OOST]);
			break;
		case OOST:
			updateLd(landsdelen[OOST_NOORD], nieuweTegel.landsdelen[WEST_NOORD]);
			updateLd(landsdelen[OOST], nieuweTegel.landsdelen[WEST]);
			updateLd(landsdelen[OOST_ZUID], nieuweTegel.landsdelen[WEST_ZUID]);
			break;
		case ZUID:
			updateLd(landsdelen[ZUID_OOST], nieuweTegel.landsdelen[NOORD_OOST]);
			updateLd(landsdelen[ZUID], nieuweTegel.landsdelen[NOORD]);
			updateLd(landsdelen[ZUID_WEST], nieuweTegel.landsdelen[NOORD_WEST]);
			break;
		case WEST:
			updateLd(landsdelen[WEST_ZUID], nieuweTegel.landsdelen[OOST_ZUID]);
			updateLd(landsdelen[WEST], nieuweTegel.landsdelen[OOST]);
			updateLd(landsdelen[WEST_NOORD], nieuweTegel.landsdelen[OOST_NOORD]);
			break;
		}
	}

	private void updateLd(Landsdeel huidigLd, Landsdeel nieuwLd) {
		for (int i = 0; i < MAX_GROOTTE; ++i) {
			if (landsdelen[i] == huidigLd) {
				landsdelen[i] = nieuwLd;
			}
		}
	}
}

package actua;

/**
 * 
 */
/*
 * ________________ 
 * |0\1 | 2  |3/4  | 
 * |----|----|-----| 
 * | 5  | 6  | 7   |
 * |---------------| 
 * |8/9 | 10 |11\12| 
 * |_______________|
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
	
	private static final char WEG = 'g';
	
	// TODO nog nakijken maar dacht dat dit een testvariabele was -> verwijderen
	// dus
	private static int teller = 0;
	private short orientatie;
	private Landsdeel[] landsdelen;
	private String tegelPresentatie;
	
	public Tegel() {

	}

	// TODO verwijderen??
	public Tegel(Landsdeel[] landsdelen) {
		orientatie = 0; // beginopstelling van de tegel
		setLandsdelen(landsdelen);
	}

	public Tegel(String tegelPresentatie) {
		++teller;
		orientatie = 0;
		this.tegelPresentatie = tegelPresentatie;
		setLandsdelen(null, null, null, null);
	}

	public Tegel(String tegelPresentatie, Tegel noord, Tegel oost, Tegel zuid,
			Tegel west) {
		++teller;
		orientatie = 0;
		this.tegelPresentatie = tegelPresentatie;
		setLandsdelen(noord, oost, zuid, west);
	}

	private void setLandsdelen(Tegel noord, Tegel oost,
			Tegel zuid, Tegel west) {
		landsdelen = new Landsdeel[tegelPresentatie.length()];

		vulLandsdelen(WEST_NOORD, noord, oost, zuid, west);
		corrigeerWegInvloeden();
		
		for (int i = 0; i < 13; ++i) {
			if (landsdelen[i] == null) {
				System.err.println("SHIT: " + teller + ", " + i);
			}
		}
	}

	private void corrigeerWegInvloeden() {
		if (tegelPresentatie.charAt(NOORD) == WEG && 
				tegelPresentatie.charAt(OOST) == WEG && 
				tegelPresentatie.charAt(WEST) != WEG && 
				tegelPresentatie.charAt(ZUID) != WEG) {
			updateLd(landsdelen[NOORD_WEST],landsdelen[OOST_ZUID]);
		}
		
		if (tegelPresentatie.charAt(NOORD) == WEG && 
				tegelPresentatie.charAt(WEST) == WEG && 
				tegelPresentatie.charAt(OOST) != WEG &&
				tegelPresentatie.charAt(ZUID) != WEG) {
			updateLd(landsdelen[NOORD_OOST],landsdelen[WEST_ZUID]);
		}
		
		if (tegelPresentatie.charAt(ZUID) == WEG && 
				tegelPresentatie.charAt(OOST) == WEG && 
				tegelPresentatie.charAt(NOORD) != WEG &&
				tegelPresentatie.charAt(WEST) != WEG) {
			updateLd(landsdelen[ZUID_WEST],landsdelen[WEST_ZUID]);
		}
		
		if (tegelPresentatie.charAt(ZUID) == WEG && 
				tegelPresentatie.charAt(WEST) == WEG && 
				tegelPresentatie.charAt(OOST) != WEG &&
				tegelPresentatie.charAt(NOORD) != WEG) {
			updateLd(landsdelen[NOORD_WEST],landsdelen[OOST_ZUID]);
		}
	}

	private void vulLandsdelen(int beginPos, Tegel noord, Tegel oost, Tegel zuid, Tegel west) {
		if (west == null) {
			landsdelen[beginPos] = new Landsdeel(tegelPresentatie.charAt(beginPos));
			// west tegel is niet null dus deze tegel heeft aan zijn west-zijde
			// dezelfde landsdelen als de west buur in het oosten heeft
		} else {
			landsdelen[beginPos] = west.bepaalLandsdeel(OOST);
		}

		vindGelijkeLandsdelen(beginPos, noord, oost, zuid, west);
		
		while (!isLandsdelenGevuld()) {
			beginPos = volgendeNietGevuldeLandsdeel();
			landsdelen[beginPos] = getLandsDeel(beginPos, noord, oost, zuid, west);
			vindGelijkeLandsdelen(beginPos, noord, oost, zuid, west);
		}
	}

	private int volgendeNietGevuldeLandsdeel() {
		for (int i = 0; i < landsdelen.length; ++i) {
			if (landsdelen[i] == null) {
				return i;
			}
		}
		
		return -1;
	}

	private boolean isLandsdelenGevuld() {
		for (int i = 0; i < landsdelen.length; ++i) {
			if (landsdelen[i] == null) {
				return false;
			}
		}
		
		return true;
	}

	private void vindGelijkeLandsdelen(int i, 
			Tegel noord, Tegel oost, Tegel zuid, Tegel west) {
		/**
		 * Het volstaat om het landsdeel onder en rechts van landsdeel i na te
		 * kijken. Diegene erboven zijn al een bekeken
		 */

		// rechts heeft het zelfde lansdeel
		if (i + 1 < tegelPresentatie.length() && landsdelen[i + 1] == null
				&& tegelPresentatie.charAt(i) == tegelPresentatie.charAt(i + 1)) {
			landsdelen[i + 1] = landsdelen[i];
			vindGelijkeLandsdelen(i + 1, noord, oost, zuid,
					west);
		}
//		} else if (i + 1 < tegelPresentatie.length() && landsdelen[i + 1] == null) {
//			landsdelen[i + 1] = getLandsDeel(i + 1, noord,
//					oost, zuid, west);
//			vindGelijkeLandsdelen(i + 1, noord, oost, zuid,
//					west);
//		}

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
				&& tegelPresentatie.charAt(i) == tegelPresentatie.charAt(volgende)) {
			landsdelen[volgende] = landsdelen[i];
			vindGelijkeLandsdelen(volgende, noord, oost, zuid,
					west);
		}
//		} else if (volgende != -1 && landsdelen[volgende] == null) {
//			landsdelen[volgende] = getLandsDeel(volgende,
//					noord, oost, zuid, west);
//			vindGelijkeLandsdelen(volgende, noord, oost, zuid,
//					west);
//		}
	}

	private Landsdeel getLandsDeel(int i, Tegel noord,
			Tegel oost, Tegel zuid, Tegel west) {

		Landsdeel nieuwLandsdeel;
		
		switch (i) {
		// noord kant van de nieuwe tegel heeft dezelfde landsdelen als de zuid
		// kant van de noorder buur tegel
		case NOORD_WEST:
			nieuwLandsdeel = getLd(noord, NOORD_WEST, ZUID_WEST);
			break;
		case NOORD:
			nieuwLandsdeel = getLd(noord, NOORD, ZUID);
			break;
		case NOORD_OOST:
			nieuwLandsdeel = getLd(noord, NOORD_OOST, ZUID_OOST);
			break;
		// oost kant van de nieuwe tegel heeft dezelfde landsdelen als de west
		// kant van de oost buur tegel
		case OOST_NOORD:
			nieuwLandsdeel = getLd(oost, OOST_NOORD, WEST_NOORD);
			break;
		case OOST:
			nieuwLandsdeel = getLd(oost, OOST, WEST);
			break;
		case OOST_ZUID:
			nieuwLandsdeel = getLd(oost, OOST_ZUID, WEST_ZUID);
			break;
		// zuid kant van de nieuwe tegel heeft dezelfde landsdelen als de noord
		// kant van de zuid buur tegel
		case ZUID_OOST:
			nieuwLandsdeel = getLd(zuid, ZUID_OOST, NOORD_OOST);
			break;
		case ZUID:
			nieuwLandsdeel = getLd(zuid, ZUID, NOORD);
			break;
		case ZUID_WEST:
			nieuwLandsdeel = getLd(zuid, ZUID_WEST, NOORD_WEST);
			break;
		// west kant van de nieuwe tegel heeft dezelfde landsdelen als de oost
		// kant van de west buur tegel			
		case WEST_ZUID:
			nieuwLandsdeel = getLd(west, WEST_ZUID, OOST_ZUID);
			break;
		case WEST:
			nieuwLandsdeel = getLd(west, WEST, OOST);
			break;
		case WEST_NOORD:
			nieuwLandsdeel = getLd(west, WEST_NOORD, OOST_NOORD);
			break;
		default:
			nieuwLandsdeel = new Landsdeel(tegelPresentatie.charAt(i));
		}

		return nieuwLandsdeel;
	}

	private Landsdeel getLd(Tegel buur, int zijkant, int zijkantBuur) {
		if (buur == null) {
			return new Landsdeel(tegelPresentatie.charAt(zijkant));
		}
		
		return buur.bepaalLandsdeel(zijkantBuur);
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

	public boolean plaatsPion(int pos, Pion pion) {
		// er zijn foute coördinaten zijn doorgegeven.
		if (!coordinatenOk(pos)) {
			return false;
		}

		// Op dit moment is pos zeker een geldige pion plaatsing
		landsdelen[pos].plaatsPion(pion);

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

	/**
	 * Deze functie zal elk voorkomen van huidigLd vervangen door nieuwLd.
	 * 
	 * @param huidigLd
	 * Het landsdeel dat vervangen moet worden.
	 * @param nieuwLd
	 * Het landsdeel dat we willen hebben.
	 */
	private void updateLd(Landsdeel huidigLd, Landsdeel nieuwLd) {
		for (int i = 0; i < MAX_GROOTTE; ++i) {
			if (landsdelen[i] == huidigLd) {
				landsdelen[i] = nieuwLd;
			}
		}
	}
	
	public boolean equals(char[] landsdelen) {
		boolean gelijk  = true;
		
		if (landsdelen.length != MAX_GROOTTE) {
			return false;
		}
		
		for(int i = 0; gelijk && i < MAX_GROOTTE; ++i) {
			gelijk = landsdelen[i] == this.landsdelen[i].getType();
		}
		
		return gelijk;
	}
	
	public String getTegelPresentatie() {
		return tegelPresentatie;
	}

	public void vulLandsdelen(Tegel noord, Tegel oost, Tegel zuid,
			Tegel west) {
		setLandsdelen(noord, oost, zuid, west);
	}
}

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
	private short orientatie;
	private Landsdeel[] landsdelen;
	private String tegelPresentatie;
	private String idPresentatie;
	
	public Tegel() {

	}

	public Tegel(String tegelPresentatie, String idPresentatie) {
		orientatie = 0;
		this.tegelPresentatie = tegelPresentatie;
		setLandsdelen(new String(idPresentatie));
		this.idPresentatie = idPresentatie;
	}

	public String getIdPresentatie() {
		return idPresentatie;
	}
	
	private void setLandsdelen(String idPresentatie) {
		if (idPresentatie == null || idPresentatie.length() != MAX_GROOTTE) {
			System.err.println("Foutieve string idPresentatie: " + idPresentatie);
			return;
		}
		
		landsdelen = new Landsdeel[tegelPresentatie.length()];
		
		for (int i = 0; i < MAX_GROOTTE; ++i) {
			if (landsdelen[i] == null) {
				landsdelen[i] = new Landsdeel(tegelPresentatie.charAt(i));
				
				for (int j = i; j < MAX_GROOTTE; ++j) {
					if (idPresentatie.charAt(i) == idPresentatie.charAt(j)) {
						landsdelen[j] = landsdelen[i];
					}
				}
			}
		}
		
		for(int j = 0; j < 10; ++j) {
			;
		}
	}

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

//	// TODO invullen!!!!
//	public Tegel clone() {
//		Tegel t = new Tegel();
//		t.orientatie = orientatie;
//		t.landsdelen = new Landsdeel[landsdelen.length];
//		
//		for (int i = 0; i < landsdelen.length; ++i) {
//			if (t.landsdelen[i] == null) {
//				t.landsdelen[i] = landsdelen[i].clone();
//				for (int j = i; j < landsdelen.length; ++j) {
//					if (landsdelen[i] == landsdelen[j]){
//						t.landsdelen[j] = t.landsdelen[i];
//					}
//				}
//			}
//		}
//		
//		t.landsdelen = landsdelen.clone();
//		t.tegelPresentatie = new String(tegelPresentatie);
//
//		return t;
//	}

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
	
	public String getTegelPresentatie() {
		return tegelPresentatie;
	}
	
	public short getOrientatie() {
		return orientatie;
	}
}

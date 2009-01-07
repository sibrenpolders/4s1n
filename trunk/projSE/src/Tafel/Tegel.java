package Tafel;

import java.io.IOException;
import java.io.Serializable;


/*
 * _______________
 * |0\1 | 2 |3/4 | 
 * |----|---|----| 
 * | 11 | 12| 5  |
 * |-------------| 
 * |10/9| 8 |7 \6| 
 * |_____________|
 */
public class Tegel implements Serializable {
	private static final long serialVersionUID = -6031518212157821335L;
	public static final int MAX_GROOTTE = 13;
	private static final short MAX_DRAAIING = 4;
	// Deze constanten geven andere klassen de mogelijkheid om zijkanten van de
	// tegel aan te duiden, zonder te weten hoe ze worden voorgesteld.
	public static final int NOORD_WEST = 1;
	public static final int NOORD = 2;
	public static final int NOORD_OOST = 3;
	public static final int OOST_NOORD = 4;
	public static final int OOST = 5;
	public static final int OOST_ZUID = 6;
	public static final int ZUID_OOST = 7;
	public static final int ZUID = 8;
	public static final int ZUID_WEST = 9;
	public static final int WEST_ZUID = 10;
	public static final int WEST = 11;
	public static final int WEST_NOORD = 0;
	public static final int MIDDEN = 12;
	public static final int TEGEL_PRESENTATIE = 0;
	public static final int ID_PRESENTATIE = 1;
	public static final int ORIENTATIE = 2;

	private short orientatie;
	private Landsdeel[] landsdelen;
	private String tegelPresentatie;
	private String idPresentatie;

	public Tegel() {
		orientatie = 0;
	}

	public Tegel(String tegelPresentatie, String idPresentatie, short orientatie) {
		this.tegelPresentatie = new String(tegelPresentatie);
		setLandsdelen(new String(idPresentatie));
		this.idPresentatie = new String(idPresentatie);
		this.orientatie = orientatie;
	}

	public Tegel(String tegelPresentatie, String idPresentatie,
			String orientatie) {
		this(tegelPresentatie, idPresentatie, Short.parseShort(orientatie));
	}

	// GETTERS en SETTERS

	public String getIdPresentatie() {
		return idPresentatie;
	}

	public String getTegelPresentatie() {
		return tegelPresentatie;
	}

	public short getOrientatie() {
		return orientatie;
	}

	public void setOrientatie(short orientatie) {
		this.orientatie = orientatie;
	}

	public String[] getTegelString() {
		String[] tmp = new String[3];

		tmp[TEGEL_PRESENTATIE] = new String(tegelPresentatie);
		tmp[ID_PRESENTATIE] = new String(idPresentatie);
		tmp[ORIENTATIE] = new String("" + orientatie);

		return tmp;
	}

	/**
	 * @param richting
	 *            True: wijzerzin draaien. False: tegenwijzerzin draaien.
	 */
	public void draaiTegel(boolean richting) {
		if (richting) {
			orientatie = (short) ((orientatie + 1) % MAX_DRAAIING);
		} else {
			orientatie = (short) ((MAX_DRAAIING + orientatie - 1) % MAX_DRAAIING);
		}
	}

	// LANDSDELEN

	private void setLandsdelen(String idPresentatie) {
		if (idPresentatie == null || idPresentatie.length() != MAX_GROOTTE) {
			System.err.println("Foutieve string idPresentatie: "
					+ idPresentatie);
			return;
		}

		landsdelen = new Landsdeel[tegelPresentatie.length()];

		for (int i = 0; i < MAX_GROOTTE; ++i) {
			if (landsdelen[i] == null) {
				landsdelen[i] = new Landsdeel(tegelPresentatie.charAt(i));

				for (int j = i + 1; j < MAX_GROOTTE; ++j) {
					if (idPresentatie.charAt(i) == idPresentatie.charAt(j)) {
						landsdelen[j] = landsdelen[i];
					}
				}
			}
		}
	}

	public Landsdeel bepaalLandsdeel(int zone) {
		// er zijn foute coördinaten doorgegeven.
		if (!zoneCoordinatenOk(zone)) {
			System.err.println("Foute landsdeelcoördinaten");
			return null;
		}

		return landsdelen[getZoneAfterRotation(zone)];
	}

	private int getZoneAfterRotation(int zone) {
		if (zone == MIDDEN || orientatie == 0) {
			return zone;
		}

		int beginPos = 1;

		if (orientatie == 1) {
			beginPos = 9;
		} else if (orientatie == 2) {
			beginPos = 6;
		} else if (orientatie == 3) {
			beginPos = 3;
		}

		return (beginPos + zone) % 12;
	}

	private boolean zoneCoordinatenOk(int pos) {
		// de foute coördinaten zijn doorgegeven.
		if (pos < 0 || pos >= MAX_GROOTTE) {
			return false;
		}

		return true;
	}

	public boolean plaatsPion(int pos, char kleur) {
		if (pos < 0 || pos >= MAX_GROOTTE || landsdelen[pos].isKruispunt()
				|| bepaalLandsdeel(pos).isPionGeplaatst()) {
			return false;
		} else {
			landsdelen[pos].setPion(kleur);
			return true;
		}
	}

	public boolean isPionGeplaatst(int pos) {
		if (pos < 0 || pos >= MAX_GROOTTE) {
			return false;
		}

		return landsdelen[pos].isPionGeplaatst();
	}

	public char geefPionKleur(int pos) {
		if (pos < 0 || pos >= MAX_GROOTTE) {
			return 0;
		}

		return landsdelen[pos].getPion();
	}

	public void verwijderPion(int pos) {
		if (pos < 0 || pos >= MAX_GROOTTE
				|| !bepaalLandsdeel(pos).isPionGeplaatst()) {
			;
		} else {
			landsdelen[pos].verwijderPion();
		}
	}
	
	public boolean[] getUniekeLandsdeelPosities(){
		boolean[] result = new boolean[MAX_GROOTTE];
		
		for(int i = 0; i < MAX_GROOTTE; ++i)
		{
			boolean uniek = true;
			for(int j = 0; j < i; ++j)
				if(landsdelen[i] == landsdelen[j])
					uniek = false;
			
			result[i] = uniek;
		}
		
		return result;
	}

	// TODO Tegel.clone(): OK ?
	public Tegel clone() {
		Tegel t = new Tegel();
		t.orientatie = orientatie;
		t.landsdelen = new Landsdeel[landsdelen.length];

		for (int i = 0; i < landsdelen.length; ++i) {
			if (t.landsdelen[i] == null) {
				t.landsdelen[i] = landsdelen[i].clone();
				for (int j = i; j < landsdelen.length; ++j) {
					if (landsdelen[i] == landsdelen[j]) {
						t.landsdelen[j] = t.landsdelen[i];
					}
				}
			}
		}

		t.landsdelen = landsdelen.clone();
		t.tegelPresentatie = new String(tegelPresentatie);

		return t;
	}

	public boolean equals(Tegel t) {
		boolean orientatieB, soortTegelB = true;

		orientatieB = orientatie == t.orientatie;

		return orientatieB && soortTegelB;
	}

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeShort(orientatie);
		out.writeObject(idPresentatie);
		out.writeObject(tegelPresentatie);
		// TODO Tegel.writeObject: pion plaatsing opslaan
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		orientatie = in.readShort();
		idPresentatie = (String) in.readObject();
		tegelPresentatie = (String) in.readObject();
		setLandsdelen(new String(idPresentatie));
	}
}

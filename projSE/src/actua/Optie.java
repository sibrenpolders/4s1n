package actua;

public class Optie {
	public enum TYPE {
		BOOL, TEXT, NUM
	};

	public static boolean checkGeldigheidWaarde(TYPE t, String waarde) {
		switch (t) {
		case BOOL:
			return waarde != null
					&& (waarde.compareTo("false") == 0 || waarde
							.compareTo("true") == 0);
		case TEXT:
			return waarde != null;
		case NUM:
			if (waarde == null)
				return false;
			try {
				Integer.parseInt(waarde);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		default:
			return false;
		}
	}

	public static TYPE checkGeldigheidType(String type) {
		if (type == null)
			return null;
		else if (type.compareTo("BOOL") == 0)
			return TYPE.BOOL;
		else if (type.compareTo("TEXT") == 0)
			return TYPE.TEXT;
		else if (type.compareTo("NUM") == 0)
			return TYPE.NUM;
		else
			return null;
	}

	private String naam;
	private String waarde;
	private final TYPE type;
	private boolean isSet;

	/**
	 * @pre checkGeldigheid(type, obj) == true
	 * @param naam
	 * @param obj
	 * @param type
	 */
	public Optie(String naam, TYPE type, String obj) {
		this(naam, type);
		setWaarde(obj);
	}

	public Optie(String naam, TYPE type) {
		isSet = false;
		waarde = null;
		this.type = type;
		setNaam(naam);
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getWaarde() {
		return waarde;
	}

	/**
	 * @pre checkGeldigheid(this.getType(), waarde) == true
	 */
	public void setWaarde(String waarde) {
		if (checkGeldigheidWaarde(this.getType(), waarde)) {
			this.waarde = waarde;
			isSet = true;
		}
	}

	public TYPE getType() {
		return type;
	}

	public boolean isSet() {
		return isSet;
	}
}
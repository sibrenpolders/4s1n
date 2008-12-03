package actua;

public class Optie {
	private String naam;
	private String waarde;

	public Optie(String naam,  String obj) {
		setNaam(naam);
		setWaarde(obj);
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

	public void setWaarde(String waarde) {
		this.waarde = waarde;
	}

}

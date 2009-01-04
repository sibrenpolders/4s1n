package actua;

import java.io.File;

public class BestandsVerwerker {
	public static final String extensie = ".carca";
	private Bestand bestand;

	public BestandsVerwerker() {
	}

	// GETTERS en SETTERS

	public Spel getSpel() {
		return bestand.getSpel();
	}

	public String getNaam() {
		return bestand.getNaam();
	}

	// CONTROLEFUNCTIES

	public boolean checkBestaan(String naam) {
		File f = new File(naam);
		return f.exists();
	}

	public boolean heeftExtensie(String naam, String extensie) {
		return naam.endsWith(extensie);
	}

	// OPSLAAN

	public boolean slaSpelToestandOp(Spel spel) {
		return slaSpelToestandOp(bestand.getNaam(), spel, false);
	}

	public boolean slaSpelToestandOp(String naam, Spel spel) {
		return slaSpelToestandOp(naam, spel, false);
	}

	public boolean slaSpelToestandOp(String naam, Spel spel, boolean confirm) {
		if (!checkBestaan(naam) || confirm) {
			Bestand bestand = new Bestand();
			bestand.schrijfNaarBestand(spel, naam);
			return true;
		}
		return false;
	}

	// INLADEN

	public boolean startLaden(Spel spel, String naam) {
		if (heeftExtensie(naam, extensie) && checkBestaan(naam)) {
			Bestand bestand = new Bestand();
			bestand.leesVanBestand(spel, naam);
			return true;
		}
		return false;
	}
}
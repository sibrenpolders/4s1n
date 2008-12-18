package actua;

import java.io.File;

public class BestandsVerwerker {
	public static final String extensie = ".carca";
	private Bestand bestand;

	public BestandsVerwerker() {
	}

	public boolean checkBestaan(String naam) {
		File f = new File(naam);
		return f.exists();
	}

	public boolean heeftExtensie(String naam, String extensie) {
		return naam.endsWith(extensie);
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

	public boolean startLaden(Spel spel, String naam) {		
		if (heeftExtensie(naam, extensie) && checkBestaan(naam)) {
			Bestand bestand = new Bestand();
			bestand.leesVanBestand(spel, naam);
			return true;
		}
		return false;
	}

	public Spel getSpel() {
		return bestand.getSpel();
	}
}
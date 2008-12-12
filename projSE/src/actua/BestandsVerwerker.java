package actua;

import java.io.File;

public class BestandsVerwerker {
	public static final String extensie = ".carca";
	private Bestand bestand;

	public BestandsVerwerker() {
		bestand = null;
	}

	public boolean bestandBestaat(String naam) {
		File f = new File(naam);
		return f.exists();
	}

	public boolean heeftExtensie(String naam, String extensie) {
		return naam.endsWith(extensie);
	}

	public boolean slaSpelToestandOp(String naam, Spel spel, Memento me,
			boolean confirm) {
		if (heeftExtensie(naam, extensie)
				&& (!bestandBestaat(naam) || (bestandBestaat(naam) && confirm))) {
			(bestand = new Bestand(naam, spel, me)).schrijfNaarBestand();
			return true;
		}

		return false;
	}

	public boolean laadSpelToestandIn(String naam, boolean confirm) {
		if (heeftExtensie(naam, extensie) && bestandBestaat(naam) && confirm) {
			(bestand = new Bestand()).leesVanBestand(naam);
			return true;
		}
		return false;
	}

	public Spel getSpel() {
		return bestand.getSpel();
	}

	public Memento getMemento() {
		return bestand.getMemento();
	}
}
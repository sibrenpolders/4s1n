package actua;

import java.io.File;

public class BestandsVerwerker {
	public static final String extensie = ".carca";
	private Bestand bestand;

	public BestandsVerwerker() {
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
		Bestand bestand = new Bestand();
		if (heeftExtensie(naam, extensie)
				&& (!bestandBestaat(naam) || (bestandBestaat(naam) && confirm))) {
			bestand.schrijfNaarBestand(spel, me, naam);
			return true;
		}

		return false;
	}

	public boolean laadSpelToestandIn(Spel spel, Memento me, String naam, boolean confirm) {
		Bestand bestand = new Bestand();
		if (heeftExtensie(naam, extensie) && bestandBestaat(naam) && confirm) {
			bestand.leesVanBestand(spel, me, naam);
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
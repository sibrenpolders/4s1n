package actua;

public class Help {
	private static String HELP_BESTAND = "help.txt";
	private String helpBestand;

	public Help() {
		helpBestand = HELP_BESTAND;
	}

	public Help(String bestand) {
		helpBestand = bestand;
	}

	/**
	 * @param zoekterm
	 *            term waarnaar gezocht wordt vb. 'punten'
	 * @param limiter
	 *            aantal karakters van het resultaat dat voor een match mag
	 *            worden teruggegeven
	 * @return dim 1 = matches vb. 'punten', 'puntentelling', ... dim 2 =
	 *         resultaten voor die matches
	 */
	public String[][] geefResultaat(String zoekterm, int limiter) {
		return null;
	}

	public String getHelpBestand() {
		return helpBestand;
	}

	public void setHelpBestand(String helpBestand) {
		this.helpBestand = helpBestand;
	}
}

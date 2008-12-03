package actua;

public class HelpVerwerker {
	private Help help;

	public HelpVerwerker() {
		help = new Help();
	}

	public void veranderHelpBronBestandMetBehulpVanGegevenString(String naam) {
		help.setHelpBestand(naam);
	}

	public String[][] geefResultaat(String zoekterm, int limiter) {
		return help.geefResultaat(zoekterm, limiter);
	}
}
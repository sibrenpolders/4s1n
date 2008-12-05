package actua;

public class HelpVerwerker {
	private Help help;

	public HelpVerwerker() {
		help = new Help();
	}

	public String[][] geefBeknopteMatchingResultaten(String zoekterm,
			int limiter) {
		return help.geefResultaat(zoekterm, limiter);
	}

	public String geefVolledigResultaat(String zoekterm) {
		return help.geefVolledigResultaat(zoekterm);
	}
}
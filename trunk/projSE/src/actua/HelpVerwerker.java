package actua;

public class HelpVerwerker {
	private Help help;

	public HelpVerwerker() {
		this.help = new Help("src/xml/Help.xml");
	}

	public String[][] geefBeknopteMatchingResultaten(String zoekterm,
			int limiter) {
		return help.geefMogelijkeResultaten(zoekterm, limiter);
	}

	public String geefVolledigResultaat(String itemId) {
		return help.geefVolledigResultaat(itemId);
	}
}
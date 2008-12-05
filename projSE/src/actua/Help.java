package actua;

import java.util.Vector;

public class Help {
	private Vector<HelpItem> items;
	private HelpParser parser;

	public Help() {
		items = new Vector<HelpItem>();
		parser = new HelpParser();
		parser.parseHelpDocument(items);
	}

	public Help(String bestand) {
		items = new Vector<HelpItem>();
		parser = new HelpParser(bestand);
		parser.parseHelpDocument(items);
	}

	/**
	 * @param zoekterm
	 *            term waarnaar gezocht wordt vb. 'punten'
	 * @param limiter
	 *            aantal karakters van het resultaat dat voor een match mag
	 *            worden teruggegeven
	 * @return dim 1 = matches vb. 'punten', 'puntentelling', ... ; dim 2 =
	 *         resultaten voor die matches
	 */
	public String[][] geefResultaat(String zoekterm, int limiter) {
		return null;
	}

	public String geefVolledigResultaat(String zoekterm) {
		return null;
	}
}

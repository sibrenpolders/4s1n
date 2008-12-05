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
	public String[][] geefMogelijkeResultaten(String zoekterm, int limiter) {
		Vector<String[]> result = new Vector<String[]>();

		if (zoekterm != null && limiter > 0) {
			for (int i = 0; i < items.size(); ++i) {
				HelpItem item = items.get(i);
				if (item.bevatTag(zoekterm)) {
					String uitleg = item.getUitleg().substring(0, limiter);
					result.add(new String[] { item.getId(), uitleg });
				}
			}
		}

		return (String[][]) result.toArray();
	}

	public String geefVolledigResultaat(String id) {
		if (id != null) {
			for (int i = 0; i < items.size(); ++i) {
				HelpItem item = items.get(i);
				if (item.getId().compareTo(id) == 0) {
					return item.getUitleg();
				}
			}
		}

		return "";
	}
}

package GUI;

import Help.HelpVerwerker;

public abstract class GHelp {
	protected HelpVerwerker helpVerwerker;
	protected static int LIMIET = 100;

	public GHelp(HelpVerwerker helpVerwerker) {
		this.helpVerwerker = helpVerwerker;
	}

	protected abstract String vraagZoekterm();

	protected abstract String vraagId();

	protected abstract void geefInfoWeer(String[][] zoektermen);

	protected abstract void geefDetailWeer(String info);

	protected void updateDetailResult() {
		String id = vraagId();

		if (id == null)
			throw new NullPointerException();

		if (id.compareTo("") == 0)
			;
		else {
			String info = helpVerwerker.geefVolledigResultaat(id);
			geefDetailWeer(info);
		}
	}

	protected void updateResults() {
		String zoekterm = vraagZoekterm();
		if (zoekterm == null)
			throw new NullPointerException();

		if (zoekterm.compareTo("") == 0)
			;
		else {
			String[][] output = helpVerwerker.geefBeknopteMatchingResultaten(
					zoekterm, LIMIET);
			geefInfoWeer(output);
		}
	}

	public abstract void show();

	public abstract void hide();
}
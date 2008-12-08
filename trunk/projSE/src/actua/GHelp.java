package actua;

import actua.HelpVerwerker;

public abstract class GHelp {
	protected HelpVerwerker helpVerwerker;
	protected static int LIMIET = 100;

	public GHelp(HelpVerwerker helpVerwerker) {
		this.helpVerwerker = helpVerwerker;
	}

	protected abstract String vraagZoekterm();

	protected abstract void geefInfoWeer(String[][] zoektermen);

	protected abstract String vraagId();

	protected abstract void geefDetailWeer(String info);

	public abstract void show();

	public abstract void hide();

	protected void update() {
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

	protected void detailId() {
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
}
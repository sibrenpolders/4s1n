package actua;

import actua.HelpVerwerker;

/**
 * @author Sibrand
 * 
 */
public abstract class GHelp {
	protected HelpVerwerker helpVerwerker;
	protected static int LIMIET = 100;

	public GHelp() {

	}

	protected abstract String vraagZoekterm();

	protected abstract void geefInfoWeer(String[][] zoektermen);

	public abstract void show();

	public abstract void hide();

	protected void update() {
		String zoekTerm = vraagZoekterm();
		if (zoekTerm == null)
			throw new NullPointerException();

		if (zoekTerm.compareTo("") == 0)
			;
		else {
			String[][] output = helpVerwerker.geefResultaat(zoekTerm, LIMIET);
			geefInfoWeer(output);
		}
	}
}
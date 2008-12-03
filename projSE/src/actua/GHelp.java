package actua;

import actua.HelpVerwerker;

public abstract class GHelp {
	protected HelpVerwerker helpVerwerker;
	
	public GHelp() {
		
	}

	public abstract String vraagZoekterm ();

	public abstract void geefInfoWeer (String zoektermen);

	public void geefHelpWeer () {
		
	}

	public void sluitHelpAf () {
		
	}
}
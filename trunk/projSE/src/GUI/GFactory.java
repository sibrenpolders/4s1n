package GUI;

import Core.BestandsVerwerker;
import Core.Spel;
import Help.HelpVerwerker;
import Opties.OptieVerwerker;

public interface GFactory {
	public GWindow getWindow(Spel spel, OptieVerwerker opties,
			HelpVerwerker help, BestandsVerwerker bestand);
}
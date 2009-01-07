package GUI;

import Core.BestandsVerwerker;
import Core.Spel;
import Help.HelpVerwerker;
import Opties.OptieVerwerker;

public class QTFactory implements GFactory {

	public QTFactory() {
		super();
	}

	public GWindow getWindow(Spel spel, OptieVerwerker opties,
			HelpVerwerker help, BestandsVerwerker bestand) {
		return new QTWindow(spel, opties, help, bestand);
	}
}

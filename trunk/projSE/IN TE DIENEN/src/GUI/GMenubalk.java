package GUI;

import Core.BestandsVerwerker;
import Core.Spel;
import Help.HelpVerwerker;
import Opties.OptieVerwerker;

public abstract class GMenubalk {
	protected Spel spel;
	protected BestandsVerwerker bestand;
	protected OptieVerwerker opties;
	protected HelpVerwerker help;
	protected GHelp gHelp;
	protected GOptie gOptie;
	protected GInitSpel gInitSpel;

	public GMenubalk(Spel spel, OptieVerwerker opties, HelpVerwerker help,
			BestandsVerwerker bestand, GInitSpel ginitSpel, GHelp ghelp,
			GOptie goptie) {
		gHelp = ghelp;
		gOptie = goptie;
		gInitSpel = ginitSpel;
		this.spel = spel;
		this.opties = opties;
		this.help = help;
		this.bestand = bestand;
	}

	// GETTERS en SETTERS

	protected GHelp getGHelp() {
		return gHelp;
	}

	protected void setGHelp(GHelp help) {
		gHelp = help;
	}

	protected GOptie getGOptie() {
		return gOptie;
	}

	protected void setGOptie(GOptie optie) {
		gOptie = optie;
	}

	protected GInitSpel getGInitSpel() {
		return gInitSpel;
	}

	protected void setGInitSpel(GInitSpel initSpel) {
		gInitSpel = initSpel;
	}

	protected void setBestand(BestandsVerwerker bestand) {
		this.bestand = bestand;
	}

	public abstract void startLaden();

	public abstract void startOpslaan();
	
	public abstract void verwijderHuidigeSpeler();

	public abstract void show();

	public abstract void hide();
}
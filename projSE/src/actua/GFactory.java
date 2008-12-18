package actua;

public interface GFactory {
	// TODO Dit is niet de taak van een Factory!!! Factory != manipulator
    //	public GWindow showGWindow(Spel spel, OptieVerwerker opties,
    //			HelpVerwerker help);
	public GWindow getWindow(Spel spel, OptieVerwerker opties, HelpVerwerker help,BestandsVerwerker bestand);
}
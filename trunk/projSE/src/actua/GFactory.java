package actua;

public interface GFactory {
	public GWindow getWindow(Spel spel, OptieVerwerker opties,
			HelpVerwerker help, BestandsVerwerker bestand);
}
package actua.UI;
import actua.spelDelen.Tegel;
import actua.speler.Speler;

public abstract class GMens extends Speler {

	public GMens() {
		
	}

	public abstract boolean vraagBevestiging();

	public abstract boolean plaatsTegel (Tegel tegel);
}
package Spelers;

import Tafel.TafelVerwerker;
import Core.Vector2D;

public interface Strategy {

	// @param
	// stringvoorstelling van een tegel
	public abstract Vector2D berekenPlaatsTegel(String[] t, TafelVerwerker tafelVerwerker);

	// @pre
	// een tegel moet geplaatst zijn op tegelCoord
	// @return
	// relatieve coords t.o.v. de starttegel (row, column) worden teruggegeven
	public abstract int berekenPlaatsPion(Vector2D tegelCoord, TafelVerwerker tafelVerwerker);
}
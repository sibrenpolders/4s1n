package actua.ai;
import java.util.Vector;

import actua.spelDelen.Tegel;
import actua.types.Vector2D;

public interface Strategy {

	public abstract Vector2D BerekenTegel (Vector<Vector<Tegel>> geplaatsteTegels,
											Vector<Tegel> stapel, Tegel tegel);

	public abstract Vector2D BerekenPion (Vector<Vector<Tegel>> geplaatsteTegels,
											Vector2D laatstGeplaatst);

}
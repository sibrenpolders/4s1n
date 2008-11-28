package actua;
import java.util.Vector;


public interface Strategy {

	public abstract Vector2D BerekenTegel (Vector<Vector<Tegel>> geplaatsteTegels,
											Vector<Tegel> stapel, Tegel tegel);

	public abstract Vector2D BerekenPion (Vector<Vector<Tegel>> geplaatsteTegels,
											Vector2D laatstGeplaatst);

}
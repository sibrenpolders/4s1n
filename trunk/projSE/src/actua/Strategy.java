package actua;

public interface Strategy {

	public abstract Vector2D berekenPlaatsTegel(Tegel t);

	public abstract int berekenPlaatsPion(Pion p, Tegel t, Vector2D tegelCoord);

}
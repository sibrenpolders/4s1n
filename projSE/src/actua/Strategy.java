package actua;

public interface Strategy {

	public abstract Vector2D berekenPlaatsTegel(String[] t);

	public abstract int berekenPlaatsPion(Pion p, String[] t, Vector2D tegelCoord);

}
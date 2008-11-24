package actua.ai;
import actua.types.Vector2D;

public interface Strategy {

	public abstract Vector2D BerekenTegel ();

	public abstract Vector2D BerekenPion ();

}
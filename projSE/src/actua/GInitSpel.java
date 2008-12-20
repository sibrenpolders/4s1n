package actua;

import java.util.Observable;

public abstract class GInitSpel extends Observable{
	protected Spel spel;

	public GInitSpel(Spel spel) {
		this.spel = spel;
	}

	public abstract void show();

	public abstract void hide();

	public abstract void begin();
}

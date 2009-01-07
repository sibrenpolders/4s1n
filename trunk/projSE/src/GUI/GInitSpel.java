package GUI;

import java.util.Observable;

import Core.Spel;

public abstract class GInitSpel extends Observable {
	protected Spel spel;

	public GInitSpel(Spel spel) {
		this.spel = spel;
	}

	// Deze functie gaat spel-instantie instellen (spelers, aantal tegels, ...)
	// a.h.v. de gebruiker heeft ingegeven.
	public abstract void begin();

	public abstract void show();

	public abstract void hide();
}

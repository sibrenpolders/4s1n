package actua;

import java.util.Observer;

public abstract class GSpelerInfo implements Observer {
	protected Spel spel;
	protected char kleur;

	public GSpelerInfo(Spel spel, char kleur) {
		spel.addObserver(this);
		this.spel = spel;
		this.kleur = kleur;
	}

	public abstract void updateSpeler();
}

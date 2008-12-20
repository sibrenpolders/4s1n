package actua;

import java.util.Observer;

public abstract class GSpelerInfo implements Observer {
	protected Spel spel;
	protected char kleur;

	public GSpelerInfo(Spel spel, char kleur) {
		this.spel = spel;
		this.kleur = kleur;
		spel.addObserver(this);
	}

	public abstract void updateSpeler();

}

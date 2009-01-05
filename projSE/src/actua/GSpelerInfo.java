package actua;

public abstract class GSpelerInfo {
	protected Spel spel;
	protected char kleur;

	public GSpelerInfo(Spel spel, char kleur) {
		this.spel = spel;
		this.kleur = kleur;
	}

	public abstract void updateSpeler();
}

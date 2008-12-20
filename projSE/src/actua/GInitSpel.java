package actua;

public abstract class GInitSpel {
	protected Spel spel;

	public GInitSpel(Spel spel) {
		this.spel = spel;
	}

	public abstract void show();

	public abstract void hide();

	public abstract void begin();
}

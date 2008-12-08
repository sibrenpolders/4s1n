package actua;

public abstract class GSpelerInfo {
	private Speler speler;

	public GSpelerInfo(Speler speler) {
		this.speler = speler;
	}

	protected Speler getSpeler() {
		return speler;
	}

	protected void setSpeler(Speler speler) {
		this.speler = speler;
	}

	public abstract void toonSpeler();

	public abstract void updateSpeler();

}

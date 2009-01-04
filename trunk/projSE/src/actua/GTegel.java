package actua;

public abstract class GTegel {
	protected String[] tegel;
	protected int orientatie;
	protected Spel spel;

	public GTegel() {
		orientatie = 0;
	}

	public GTegel(Spel spel) {
		orientatie = 0;
		this.spel = spel;
	}

	public GTegel(String[] tegel_, Spel spel) {
		this.spel = spel;
		this.tegel = tegel_;
		if (tegel_.length == 3) {
			orientatie = Integer.parseInt(tegel_[2]);
		} else {
			orientatie = 0;
		}
	}

	protected String[] getTegel() {
		return tegel;
	}

	protected void setTegel(String[] tegel) {
		this.tegel = tegel;
	}

	public int getOrientatie() {
		return orientatie;
	}

	public abstract void roteer(boolean richting);
}

package actua;

public abstract class GTegel {
	protected String[] tegel;
	int orientatie;
	Spel spel;

	public GTegel() {
		orientatie = 0;
	}

	public GTegel(Spel spel) {
		orientatie = 0;
		this.spel = spel;
	}

	public GTegel(String[] tegel2, Spel spel) {
		this.spel = spel;
		this.tegel = tegel2;
		if (tegel2.length == 3) {
			orientatie = Integer.parseInt(tegel2[2]);
		} else {
			orientatie = 0;
		}
	}

	public abstract void roteer(boolean richting);

	protected String[] getTegel() {
		return tegel;
	}

	protected void setTegel(String[] tegel) {
		this.tegel = tegel;
	}

	public int getOrientatie() {
		return orientatie;
	}
}

package actua;

public abstract class GTegel {
	protected String[] tegel;
	int orientatie;
	
	public GTegel() {
		orientatie = 0;
	}
	
	public GTegel(String[] tegel2) {
		this.tegel=tegel2;
		orientatie = 0;
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


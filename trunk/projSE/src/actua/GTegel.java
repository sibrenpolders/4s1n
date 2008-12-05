package actua;

public abstract class GTegel {
	protected Tegel tegel;

	public GTegel() {
		
	}
	
	public GTegel(Tegel tegel) {
		this.tegel=tegel;
	}
	
	public abstract void show();
	public abstract void hide();
	public abstract void roteer(boolean richting);

	protected Tegel getTegel() {
		return tegel;
	}

	protected void setTegel(Tegel tegel) {
		this.tegel = tegel;
	}
	
}


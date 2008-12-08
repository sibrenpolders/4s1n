package actua;

public abstract class GOptie {
	protected OptieVerwerker optieVerwerker;

	public GOptie(OptieVerwerker ov) {
		this.optieVerwerker = ov;
	}

	public abstract void geefOptiesWeer();

	public abstract void sluitOptiesAf();

	public abstract void show();

	public abstract void hide();

	protected void update() {
	}
}

package actua;

public abstract class GOptie {
	protected OptieVerwerker optieVerwerker;

	public GOptie(OptieVerwerker ov) {
		this.optieVerwerker = ov;
	}

	public abstract void show();

	public abstract void hide();

	protected abstract void save();
	protected abstract void cancel();
}

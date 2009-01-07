package GUI;

import Opties.OptieVerwerker;

public abstract class GOptie {
	protected OptieVerwerker optieVerwerker;

	public GOptie(OptieVerwerker ov) {
		this.optieVerwerker = ov;
	}

	public abstract void show();

	public abstract void hide();
}
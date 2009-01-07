package GUI;

import java.util.Observer;


import Core.Spel;
import Opties.OptieVerwerker;

public abstract class GInfo implements Observer {
	protected Spel mSpel;
	protected OptieVerwerker mOptie;

	public GInfo(Spel spel, OptieVerwerker opties) {
		this.mSpel = spel;
		this.mOptie = opties;
		this.mSpel.addObserver(this);
	}

	public abstract void updateSpelers();

	public abstract void updateInfo();
}

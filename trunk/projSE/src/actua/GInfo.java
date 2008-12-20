package actua;

import java.util.ArrayList;
import java.util.Observer;

public abstract class GInfo implements Observer {
	protected ArrayList<GSpelerInfo> gSpelers;
	protected Spel mSpel;
	protected OptieVerwerker mOptie;

	public GInfo(Spel spel, OptieVerwerker opties) {
		mSpel = spel;
		mOptie = opties;
		mSpel.addObserver(this);
	}

	public abstract void updateSpelers();

	public abstract void updateInfo();
}

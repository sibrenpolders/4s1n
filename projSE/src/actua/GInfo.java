package actua;

import java.util.ArrayList;

public abstract class GInfo {
	private ArrayList<GSpelerInfo> gSpelers;
	protected Spel mSpel;
	protected OptieVerwerker mOptie;

	public GInfo(Spel spel, OptieVerwerker opties) {
		mSpel = spel;
		mOptie = opties;
		gSpelers = new ArrayList<GSpelerInfo>();
	}

	protected void addGSpeler(GSpelerInfo gsi) {
		gSpelers.add(gsi);
	}

	protected void removeGSpeler(GSpelerInfo gsi) {
		gSpelers.remove(gsi);
	}

	public abstract void addSpeler(Speler speler);

	public abstract void toonInfo();

	public abstract void updateInfo();

	protected abstract void toonSpelers();

	protected abstract void toonTegelStapel();

}

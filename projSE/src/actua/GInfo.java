package actua;
import java.util.ArrayList;

public abstract class GInfo {
	private ArrayList<GSpelerInfo> gSpelers;
	protected Spel mSpel; 
	//protected Optieverwerker mOptie;

	public GInfo(Spel spel, OptieVerwerker opties) {
		mSpel = spel;
		//mOptie = opties;
	}

	public void toonInfo () {
		
	}

	public void updateInfo () {
		
	}

	protected void toonSpelers () {
		
	}

	protected void toonTegelStapel () {
		
	}

}


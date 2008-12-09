package actua;

public abstract class GMenubalk {
	protected GHelp gHelp;
	protected GOptie gOptie;
	protected GInitSpel gInitSpel;

	public GMenubalk() {
		gHelp = null;
		gOptie = null;
		gInitSpel = null;
	}

	public abstract void show();

	public abstract void hide();

	protected GHelp getGHelp() {
		return gHelp;
	}

	protected void setGHelp(GHelp help) {
		gHelp = help;
	}

	protected GOptie getGOptie() {
		return gOptie;
	}

	protected void setGOptie(GOptie optie) {
		gOptie = optie;
	}

	protected GInitSpel getGInitSpel() {
		return gInitSpel;
	}

	protected void setGInitSpel(GInitSpel initSpel) {
		gInitSpel = initSpel;
	}
	
	
}
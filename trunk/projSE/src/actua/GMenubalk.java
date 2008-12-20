package actua;

public abstract class GMenubalk {
	protected GHelp gHelp;
	protected GOptie gOptie;
	protected GInitSpel gInitSpel;
	protected GWindow gParent;
	protected Spel spel;

	public GMenubalk(Spel spel) {
		gHelp = null;
		gOptie = null;
		gInitSpel = null;
		this.spel=spel;
	}
	
	public GMenubalk(Spel spel, GInitSpel gInitSpel) {
		gHelp = null;
		gOptie = null;
		this.gInitSpel = gInitSpel;
		this.spel=spel;
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
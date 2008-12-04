package actua;

public abstract class GWindow {
	protected GInfo info;
	protected GMenubalk menubalk;
	protected GSpeelveld speelveld;

	public GWindow() {

	}

	public GWindow(Spel spel, OptieVerwerker opties, HelpVerwerker help) {

	}

	public final GInfo getInfo() {
		return info;
	}

	protected final void setInfo(GInfo info) {
		this.info = info;
	}

	public final GMenubalk getMenubalk() {
		return menubalk;
	}

	protected final void setMenubalk(GMenubalk menubalk) {
		this.menubalk = menubalk;
	}

	public final GSpeelveld getSpeelveld() {
		return speelveld;
	}

	protected final void setSpeelveld(GSpeelveld speelveld) {
		this.speelveld = speelveld;
	}
}
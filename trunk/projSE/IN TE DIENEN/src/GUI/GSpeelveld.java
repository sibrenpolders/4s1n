package GUI;

import java.util.Observer;


import Core.Spel;
import Core.Vector2D;

public abstract class GSpeelveld implements Observer {
	protected static String DEFAULT_BACKGROUND = "src/icons/background.xpm";
	protected String achtergrond;
	protected Spel spel;
	protected Vector2D startGTegel;
	protected Camera camera;

	public GSpeelveld(Spel spel) {
		this.spel = spel;
		camera = new Camera();
		spel.addObserver(this);
		achtergrond = DEFAULT_BACKGROUND;
	}

	public String getAchtergrond() {
		return achtergrond;
	}

	public void setAchtergrond(String achtergrond) {
		this.achtergrond = achtergrond;
	}

	protected abstract void clearSpeelveld();

	protected abstract void initialiseerSpeelveld();

	protected abstract void updateSpeelveld();

	protected abstract void veranderZicht();
}
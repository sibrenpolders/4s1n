package actua;

public class SpelBeurtResultaat {
	private Vector2D plaatsingTegel;
	private short plaatsingPion;
	private Pion pion;
	private Tegel tegel;

	public SpelBeurtResultaat(Tegel t, Vector2D pT, Pion p, short pP) {
		tegel = t;
		plaatsingPion = pP;
		pion = p;
		plaatsingTegel = pT;
	}

	public Pion getPion() {
		return pion;
	}

	public Tegel getTegel() {
		return tegel;
	}

	public Vector2D getPlaatsTegel() {
		return plaatsingTegel;
	}

	public short getPlaatsPion() {
		return plaatsingPion;
	}
}

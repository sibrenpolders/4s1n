package actua;

public class SpelBeurtResultaat {
	private Vector2D plaatsingTegel;
	private short plaatsingPion;
	private Pion pion;
	private String[] tegel;

	public SpelBeurtResultaat(String[] t, Vector2D pT, Pion p, short pP) {
		tegel = t;
		plaatsingPion = pP;
		pion = p;
		plaatsingTegel = pT;
	}

	public Pion getPion() {
		return pion;
	}

	public String[] getTegel() {
		return tegel;
	}

	public Vector2D getPlaatsTegel() {
		return plaatsingTegel;
	}

	public short getPlaatsPion() {
		return plaatsingPion;
	}
}

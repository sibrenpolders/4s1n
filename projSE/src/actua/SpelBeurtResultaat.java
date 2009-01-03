package actua;

public class SpelBeurtResultaat {
	private Vector2D plaatsingTegel;
	private short plaatsingPion;
	private char pion;
	private String[] tegel;
	private boolean processed;

	public SpelBeurtResultaat(String[] t, Vector2D pT, char p, short pP) {
		tegel = t;
		plaatsingPion = pP;
		pion = p;
		plaatsingTegel = pT;
		processed = false;
	}

	public void toggleProcessed() {
		processed = !processed;
	}

	public boolean getProcessed() {
		return processed;
	}

	public char getPion() {
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

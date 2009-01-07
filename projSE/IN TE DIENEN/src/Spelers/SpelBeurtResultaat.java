package Spelers;

import Core.Vector2D;

public class SpelBeurtResultaat {
	private Vector2D plaatsingTegel;
	private short plaatsingPion;
	private char pion;
	private String[] tegel;
	private boolean processed;

	public SpelBeurtResultaat(String[] t, Vector2D plaatsTegel, char pion_,
			short plaatsPion) {
		tegel = t;
		plaatsingPion = plaatsPion;
		pion = pion_;
		plaatsingTegel = plaatsTegel;
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

package actua;

public class Hard implements Strategy {
	private TafelVerwerker tafelVerwerker;

	public Hard(TafelVerwerker tv) {
		this.tafelVerwerker = tv;
	}

	public int berekenPlaatsPion(char kleur, String[] t, Vector2D tegelCoord) {
		for (int i = 0; i < Tegel.MAX_GROOTTE; ++i) {
			if (tafelVerwerker.isPionPlaatsingGeldig(t, tegelCoord, i))
				return i;
		}

		return -1;
	}

	// indien mogelijk, wordt een plaats teruggegeven waar dan een pion kan
	// geplaatst worden
	public Vector2D berekenPlaatsTegel(String[] t) {
		int breedte = tafelVerwerker.getBreedte();
		int hoogte = tafelVerwerker.getHoogte();
		Vector2D coordsStartTegel = tafelVerwerker.getBeginPositie();
		int xMin = coordsStartTegel.getX() - 1;
		int yMin = coordsStartTegel.getY() - 1;
		int xMax = xMin + breedte + 2;
		int yMax = yMin + hoogte + 2;

		Vector2D backup = null;
		for (int x = xMin; x <= xMax; ++x)
			for (int y = yMin; y <= yMax; ++y) {
				Vector2D temp = new Vector2D(x, y);
				if (tafelVerwerker.isTegelPlaatsingGeldig(t, temp)) {
					backup = temp;
					if (plaatsingPionMogelijk(t, temp))
						return temp;
				}
			}

		return backup;
	}

	private boolean plaatsingPionMogelijk(String[] t, Vector2D plaats) {
		for (int i = 0; i < Tegel.MAX_GROOTTE; ++i) {
			if (tafelVerwerker.plaatsingPionMogelijk(t, plaats, i))
				return true;
		}

		return false;
	}
}

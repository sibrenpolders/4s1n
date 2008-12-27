package actua;

public class Easy implements Strategy {
	private TafelVerwerker tafelVerwerker;

	public Easy(TafelVerwerker tv) {
		this.tafelVerwerker = tv;
	}

	// eerste geldige plaats wordt gebruikt
	public int berekenPlaatsPion(char kleur, String[] t, Vector2D tegelCoord) {
		for (int i = 0; i < Tegel.MAX_GROOTTE; ++i) {
			if (tafelVerwerker.isPionPlaatsingGeldig(t, tegelCoord, i))
				return i;
		}

		return -1;
	}

	// eerste geldige plaats wordt teruggegeven, ongeacht of men er een pion op
	// kan plaatsen of niet
	public Vector2D berekenPlaatsTegel(String[] t) {
		int breedte = tafelVerwerker.getBreedte();
		int hoogte = tafelVerwerker.getHoogte();
		Vector2D coordsStartTegel = tafelVerwerker.getBeginPositie();
		int xMin = coordsStartTegel.getX() - 1;
		int yMin = coordsStartTegel.getY() - 1;
		int xMax = xMin + breedte + 2;
		int yMax = yMin + hoogte + 2;

		for (int x = xMin; x <= xMax; ++x)
			for (int y = yMin; y <= yMax; ++y) {
				Vector2D temp = new Vector2D(x, y);
				if (tafelVerwerker.isTegelPlaatsingGeldig(t, temp)) {
					return temp;
				}
			}

		return null;
	}
}
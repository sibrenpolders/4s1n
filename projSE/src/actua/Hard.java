package actua;

public class Hard implements Strategy {
	private TafelVerwerker tv;

	public Hard(TafelVerwerker tv) {
		this.tv = tv;
	}

	@Override
	public int berekenPlaatsPion(Pion p, Tegel t, Vector2D tegelCoord) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Vector2D berekenPlaatsTegel(Tegel t) {
		// TODO Auto-generated method stub
		return null;
	}
}

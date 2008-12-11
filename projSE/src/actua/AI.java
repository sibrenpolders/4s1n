package actua;

public class AI extends Speler {
	private short niveau;
	private TafelVerwerker tafelVerwerker;

	public AI(String naam, char kleur, short niveau, TafelVerwerker tv,
			long score) {
		super(naam, kleur, score);
		this.niveau = niveau;
		this.tafelVerwerker = tv;
	}

	public AI(Speler speler, short niveau, TafelVerwerker tv) {
		this(speler.getNaam(), speler.getKleur(), niveau, tv, speler.getScore());
		this.pionnen = speler.getPionnen();
	}

	public AI() {
		super();
		this.niveau = 0;
		this.tafelVerwerker = null;
	}

	public short getNiveau() {
		return niveau;
	}

	public void setNiveau(short niveau) {
		this.niveau = niveau;
	}

	public Vector2D plaatsTegel(Tegel t) {		
		int breedte = tafelVerwerker.getBreedte();
		int hoogte = tafelVerwerker.getHoogte();
		Vector2D coordsStartTegel = tafelVerwerker.getBeginPositie();
		int xMin = coordsStartTegel.getX() - 1;
		int yMin = coordsStartTegel.getY() - 1;
		int xMax = xMin + breedte + 2;
		int yMax = yMin + hoogte + 2;
		
		
		for(int x = xMin; x <= xMax; ++x)
			for(int y = yMin; y <= yMax; ++y)
			{ 
				Vector2D	temp = new Vector2D(x,y);			
				if(tafelVerwerker.isTegelPlaatsingGeldig(t, new Vector2D(x,y)) {
					int rij = startTegel.getX() + coord.getX();
					int kolom = startTegel.getY() + coord.getY();
				}
			}
)
	}

	public boolean plaatsPion(Pion p) {
		return false;
	}
}

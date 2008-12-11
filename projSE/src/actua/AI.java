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

	public boolean plaatsTegel(Tegel tegel, Tafel tafel) {
		return false;
	}

	public boolean plaatsPion(Pion p) {
		return false;
	}
}

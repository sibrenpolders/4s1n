package actua;

public class AI extends Speler {
	private short niveau;

	public AI(String naam, char kleur, long score, short niveau)
	{
		super(naam, kleur, score);
		this.niveau = niveau;
	}

	public AI(Mens speler, short niveau) {
		super(speler.getNaam(), speler.getKleur(), speler.getScore());
		this.niveau = niveau;
	}

	public short getNiveau () {
		return niveau;
	}

	public void setNiveau (short niveau) {
		this.niveau = niveau;
	}

	public boolean plaatsTegel (Tegel tegel,Tafel tafel) {
		return false;
	}

	public boolean plaatsPion(Pion p) {
		return false;
	}
}


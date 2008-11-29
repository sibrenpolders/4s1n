package actua;

public class Mens extends Speler {
	public Mens(String naam, char kleur, long score)
	{
		super(naam, kleur, score);
	}

	@Override
	public boolean plaatsPion(Pion p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean plaatsTegel(Tegel tegel, Tafel tafel) {
		// TODO Auto-generated method stub
		return false;
	}
}
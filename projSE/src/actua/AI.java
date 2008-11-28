package actua;

public class AI extends Speler {
	protected short niveau;

	public AI() {
	 super();
	}

	public AI(Speler speler) {
		
	}

	public short getNiveau () {
		return 0;
	}

	public void setNiveau (short niveau) {
		
	}

	public boolean plaatsTegel (Tegel tegel,Tafel tafel) {
		return false;
	}

	public boolean plaatsPion(Tafel tafel) {
		return false;
	}
}


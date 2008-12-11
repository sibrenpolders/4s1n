package actua;

public class Spel {
	private TafelVerwerker tafelVerwerker;
	private BestandsVerwerker bestandVerwerker;
	private SpelerVerwerker spelerVerwerker;

	public Spel() {
		tafelVerwerker = new TafelVerwerker();
		bestandVerwerker = new BestandsVerwerker();
		spelerVerwerker = new SpelerVerwerker();
	}

	public TafelVerwerker getTafelVerwerker () {
		return tafelVerwerker;
	}

	public SpelerVerwerker getSpelerVerwerker () {
		return spelerVerwerker;
	}

	public BestandsVerwerker getBestandVerwerker () {
		return bestandVerwerker;
	}
	
}

package actua;

public class Spel {
	private TafelVerwerker tafelVerwerker;
	private BestandsVerwerker bestandVerwerker;
	private SpelerVerwerker spelerVerwerker;

	public Spel() {
		
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

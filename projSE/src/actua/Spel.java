package actua;
import actua.verwerkers.BestandsVerwerker;
import actua.verwerkers.HelpVerwerker;
import actua.verwerkers.OptieVerwerker;
import actua.verwerkers.SpelerVerwerker;
import actua.verwerkers.TafelVerwerker;

public class Spel {
	private OptieVerwerker optieVerwerker;
	private TafelVerwerker tafelVerwerker;
	private BestandsVerwerker bestandVerwerker;
	private SpelerVerwerker spelerVerwerker;
	 HelpVerwerker helpVerwerker;

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

	public OptieVerwerker getOptiesVerwerker () {
		return optieVerwerker;
	}

	public HelpVerwerker getHelpVerwerker () {
		return helpVerwerker;
	}

}

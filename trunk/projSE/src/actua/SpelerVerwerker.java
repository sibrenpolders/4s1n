package actua;

import java.util.ArrayList;

public class SpelerVerwerker {

	private ArrayList<Speler> spelers;
	private int huidigeSpelerIndex;
	

	public SpelerVerwerker() {
		spelers = new ArrayList<Speler>();
	}

	public Speler geefHuidigeSpeler() {
		return spelers.get(huidigeSpelerIndex);
	}

	public void gaNaarVolgendeSpeler() {
		huidigeSpelerIndex = (huidigeSpelerIndex + 1) % spelers.size();
	}

	public void maakAI(Speler mens, short niveau, TafelVerwerker tv) {
		for (int i = 0; i < spelers.size(); ++i)
			if (spelers.get(i) == mens) {
				spelers.remove(i);
				spelers.add(i, new AI(mens, niveau, tv));
				return;
			}
	}

	// niveau = -1 voor Mens
	public void voegSpelerToe(short niveau, String naam, char kleur,
			long score, TafelVerwerker tv) {
		spelers.add(SpelerFactory.maakSpeler(naam, kleur, score, niveau, tv));
	}

	public void verwijderHuidigeSpeler() {
		if (huidigeSpelerIndex < spelers.size())
			spelers.remove(huidigeSpelerIndex);
		huidigeSpelerIndex = huidigeSpelerIndex % spelers.size();
	}

	public void verwijderSpeler(Speler speler) {
		for (int i = 0; i < spelers.size(); ++i)
			if (spelers.get(i) == speler) {
				spelers.remove(i);
				return;
			}
	}
}
package actua;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SpelerVerwerker implements Observer {

	private List<Speler> spelers;
	private Speler huidigeSpeler;

	public SpelerVerwerker() {

	}

	public Speler geefHuidigeSpeler() {
		return huidigeSpeler;
	}

	public void maakAI(Mens mens) {

	}

	// niveau = -1 voor Mens
	public void voegSpelerToe(short niveau, String naam, char kleur, long score) {
//		if (niveau < 0)
//			spelers.add(SpelerFactory.maakSpeler(SpelerFactory.MENS, naam,
//					kleur, score, niveau));
//		else
//			spelers.add(SpelerFactory.maakSpeler(SpelerFactory.AI, naam, kleur,
//					score, niveau));
		
		spelers.add(SpelerFactory.maakSpeler(naam,kleur, score, niveau));
	}

	public void verwijderHuidigeSpeler() {

	}

	public void verwijderSpeler(Mens mens) {

	}

	public short vraagNiveau() {
		return 0;
	}

	public void addObserver(Observer o) {

	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class SpelerVerwerker implements Serializable {
	private static final long serialVersionUID = 6790524642848337268L;
	private ArrayList<Speler> spelers;
	private int huidigeSpelerIndex;

	public SpelerVerwerker() {
		spelers = new ArrayList<Speler>();
	}

	public char geefHuidigeSpeler() {
		return spelers.get(huidigeSpelerIndex).getKleur();
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

	public String geefSpelerNaam(char kleur) {
		for (int i = 0; i < spelers.size(); ++i)
			if (spelers.get(i).getKleur() == kleur) {
				return spelers.get(i).getNaam();
			}
		return "";
	}

	public long geefSpelerScore(char kleur) {
		for (int i = 0; i < spelers.size(); ++i)
			if (spelers.get(i).getKleur() == kleur) {
				return spelers.get(i).getScore();
			}
		return 0;
	}

	public short geefAantalOngeplaatstePionnen(char kleur) {
		for (int i = 0; i < spelers.size(); ++i)
			if (spelers.get(i).getKleur() == kleur) {
				return spelers.get(i).getAantalOngeplaatstePion();
			}
		return 0;
	}

	// niveau = -1 voor Mens
	public void voegSpelerToe(short niveau, String naam, char kleur,
			long score, TafelVerwerker tv) {
		spelers.add(SpelerFactory.maakSpeler(naam, kleur, score, niveau, tv));
	}

	public void verwijderHuidigeSpeler() {
		if (huidigeSpelerIndex < spelers.size() && huidigeSpelerIndex >= 0)
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

	public void volgendeSpeler() {
		huidigeSpelerIndex = (huidigeSpelerIndex + 1) % spelers.size();
	}

	public void verwijderSpelers() {
		spelers.clear();
		huidigeSpelerIndex = -1;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(spelers);
		out.writeInt(huidigeSpelerIndex);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		spelers = (ArrayList<Speler>) in.readObject();
		huidigeSpelerIndex = in.readInt();
	}

	private void readObjectNoData() throws ObjectStreamException {

	}
}
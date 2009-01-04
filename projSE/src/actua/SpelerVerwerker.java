package actua;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class SpelerVerwerker implements Serializable {
	private static final long serialVersionUID = 6790524642848337268L;
	private ArrayList<Speler> spelers;
	private int huidigeSpelerIndex;

	public SpelerVerwerker() {
		spelers = new ArrayList<Speler>();
	}

	public void verwijderSpelers() {
		spelers.clear();
		huidigeSpelerIndex = -1;
	}

	// HUIDIGE SPELER

	public char geefHuidigeSpeler() {
		return spelers.get(huidigeSpelerIndex).getKleur();
	}

	public boolean isHuidigeSpeler(char speler) {
		return speler == geefHuidigeSpeler();
	}

	public void verwijderHuidigeSpeler() {
		if (huidigeSpelerIndex < spelers.size() && huidigeSpelerIndex >= 0)
			spelers.remove(huidigeSpelerIndex);
		huidigeSpelerIndex = huidigeSpelerIndex % spelers.size();
	}

	public void gaNaarVolgendeSpeler() {
		huidigeSpelerIndex = (huidigeSpelerIndex + 1) % spelers.size();
	}

	public boolean isHuidigeSpelerAI() {
		return spelers.get(huidigeSpelerIndex) instanceof AI;
	}

	public SpelBeurtResultaat geefResultaatAI(TafelVerwerker t) {
		if (isHuidigeSpelerAI())
			return ((AI) spelers.get(huidigeSpelerIndex)).doeZet(t);
		else
			return null;
	}

	// SPELERSGROEP

	public int geefAantalSpelers() {
		return spelers.size();
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

	public boolean neemPionVan(char speler) {
		Speler s = geefSpeler(speler);

		if (s != null) {
			return s.ongeplaatstePionAanwezig();
		}

		return false;
	}

	public boolean plaatsPionVan(char speler) {
		Speler s = geefSpeler(speler);

		if (s != null) {
			return s.plaatsOngeplaatstePion();
		}

		return false;
	}

	public boolean neemPionTerugVan(char speler) {
		Speler s = geefSpeler(speler);

		if (s != null) {
			return s.neemGeplaatstePionTerug();
		}

		return false;
	}

	public char geefKleurVanSpeler(int i) {
		return spelers.get(i).getKleur();
	}

	private Speler geefSpeler(char kleur) {
		for (int i = 0; i < spelers.size(); ++i) {
			if (spelers.get(i).getKleur() == kleur) {
				return spelers.get(i);
			}
		}

		return null;
	}

	// VERANDER SPELERS

	public void verwijderSpeler(Speler speler) {
		for (int i = 0; i < spelers.size(); ++i)
			if (spelers.get(i) == speler) {
				spelers.remove(i);
				return;
			}
	}

	public void maakAI(Speler mens, short niveau) {
		for (int i = 0; i < spelers.size(); ++i)
			if (spelers.get(i) == mens) {
				spelers.remove(i);
				spelers.add(i, new AI(mens, niveau));
				return;
			}
	}

	// niveau = -1 voor Mens
	public void voegSpelerToe(short niveau, String naam, char kleur, long score) {
		if (huidigeSpelerIndex == -1)
			huidigeSpelerIndex = 0;
		spelers.add(SpelerFactory.maakSpeler(naam, kleur, score, niveau));
	}

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(spelers);
		out.writeInt(huidigeSpelerIndex);
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		spelers = (ArrayList<Speler>) in.readObject();
		huidigeSpelerIndex = in.readInt();
	}
}
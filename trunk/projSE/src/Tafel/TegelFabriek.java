package Tafel;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TegelFabriek {
	private static final String TEGELS_BESTAND = "src/xml/Tegels.xml";
	private String tegelsBestand;
	private String[][] startTegels = {
			{ "wssswgwwwwwgg", "0111023333322", "0" },
			{ "wssswwwwgwwgg", "0111000023322", "0" },
			{ "wssswgwwgwwwg", "0111023320002", "0" } };

	public TegelFabriek() {
		this(TEGELS_BESTAND);
	}

	public TegelFabriek(String tegelsBestand) {
		this.tegelsBestand = tegelsBestand;
	}

	public String[] geefStartTegel() {
		int i = (int) (Math.floor(Math.random() * 3));

		return startTegels[i];
	}

	/**
	 * Deze functie zal een Dequeue van Tegels aanmaken. Dit is de stapel tegels
	 * waarmee het spel gespeeld wordt.
	 * 
	 * @param aantal
	 *            aantal Tegel instanties die de vector moet bevatten
	 * @return De tegelStapel
	 */
	// TODO maakTegelDeque bekijken op randomness
	public ArrayDeque<String[]> maakTegelDeque(int aantal) {
		TegelFabriekBestandLezer tfbl = new TegelFabriekBestandLezer(
				tegelsBestand);
		ArrayList<String[]> stapel = new ArrayList<String[]>();

		int aantalVerschillendeTegels = tfbl.getAantalTegels();
		int aantalTegelsTeMaken = aantal;
		int aantalTegels = 0;
		int x = 8;

		int i;
		int som = 0;
		for (i = 0; i < aantalVerschillendeTegels - 1; ++i) {
			if (aantalTegelsTeMaken < x) {
				x = aantalTegelsTeMaken;
			}
			aantalTegels = (int) (Math
					.floor((Math.random() * aantalTegelsTeMaken)
							/ (aantalTegelsTeMaken / x)));
			if (aantalTegels != 0) {
				voegTegelsToe(stapel, i, aantalTegels, tfbl);
			}

			aantalTegelsTeMaken -= aantalTegels;
			som += aantalTegels;
		}

		voegTegelsToe(stapel, i, aantalTegelsTeMaken, tfbl);

		ArrayDeque<String[]> queue = shakeNotStir(stapel);

		return queue;
	}

	private void voegTegelsToe(ArrayList<String[]> stapel, int tegelNummer,
			int aantalTegels, TegelFabriekBestandLezer tfbl) {
		String[] tegelStrings = tfbl.getTegelStrings(tegelNummer);
		String[] nieuweTegel = new String[3];
		nieuweTegel[0] = new String(tegelStrings[0]);
		nieuweTegel[1] = new String(tegelStrings[1]);
		nieuweTegel[2] = "0";
		tegelStrings = null;

		stapel.add(nieuweTegel);

		for (int i = 1; i < aantalTegels; ++i) {
			stapel.add(nieuweTegel);

		}
	}

	private ArrayDeque<String[]> shakeNotStir(ArrayList<String[]> stapel) {
		ArrayDeque<String[]> queue = new ArrayDeque<String[]>();

		int next;
		for (int i = 0; i < stapel.size(); ++i) {
			next = (int) (Math.floor(Math.random() * stapel.size()));
			queue.push(stapel.get(next));
		}

		return queue;
	}
}

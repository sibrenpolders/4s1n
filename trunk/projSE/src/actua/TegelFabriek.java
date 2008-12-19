package actua;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class TegelFabriek {
	private static final String TEGELS_BESTAND = "src/xml/Tegels.xml";
	private String tegelsBestand;
	private String[][] startTegels = { {"wssswgwwwwwgg", "0111023333322"}, 
			{"wssswwwwgwwgg", "0111000023322"}, {"wssswgwwgwwwg", "0111023320002"}};
	public TegelFabriek() {
		tegelsBestand = TEGELS_BESTAND;
	}
	
	public TegelFabriek(String tegelsBestand) {
		this.tegelsBestand = tegelsBestand;
	}
	
	/**
	 * Deze functie zal een Dequeue van Tegels aanmaken.
	 * Dit is de stapel tegels waarmee het spel gespeeld wordt. 
	 * @param aantal
	 * 		aantal Tegel instanties die de vector moet bevatten
	 * @return
	 * 		De tegelStapel
	 */
	// TODO random functie belijken
	public ArrayDeque<String[]> maakTegelDeque(int aantal) {
		TegelFabriekBestandLezer tfbl = new TegelFabriekBestandLezer(tegelsBestand);
		ArrayList<String[]> stapel = new ArrayList<String[]>();
		
		int aantalVerschillendeTegels = tfbl.getAantalTegels();
		int aantalTegelsTeMaken = aantal;
		int aantalTegels = 0;
		int x = 8;
		
		int i;
		int som = 0;
		for (i = 0; i < aantalVerschillendeTegels-1; ++i) {
			if (aantalTegelsTeMaken < x) {
				x = aantalTegelsTeMaken;
			}
			aantalTegels = (int)(Math.floor((Math.random()*aantalTegelsTeMaken)/(aantalTegelsTeMaken/x)));
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

	public String[] geefStartTegel() {
		int i = (int) (Math.floor(Math.random()*3));
		
		return startTegels[i];
	}
	
	private ArrayDeque<String[]> shakeNotStir(ArrayList<String[]> stapel) {
		ArrayDeque<String[]> queue = new ArrayDeque<String[]>();

		int next;
		for (int i = 0; i < stapel.size(); ++i) {
			next = (int) (Math.floor(Math.random()*stapel.size()));
			queue.push(stapel.get(next));
		}
		
		return queue;
	}

	private void voegTegelsToe(ArrayList<String[]> stapel, int tegelNummer, int aantalTegels, TegelFabriekBestandLezer tfbl) {
		String[] tegelStrings = tfbl.getTegelStrings(tegelNummer);
		stapel.add(tegelStrings);

		for (int i = 1; i < aantalTegels; ++i) {
			stapel.add(tegelStrings);
			
		}
	}	
}

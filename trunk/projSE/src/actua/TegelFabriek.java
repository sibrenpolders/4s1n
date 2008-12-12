package actua;

import java.util.ArrayDeque;

public class TegelFabriek {
	private static final String TEGELS_BESTAND = "src/xml/Tegels.xml";
	private String tegelsBestand;
	
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
	public ArrayDeque<Tegel> maakTegelDeque(int aantal) {
		TegelFabriekBestandLezer tfbl = new TegelFabriekBestandLezer(tegelsBestand);
		ArrayDeque<Tegel> stapel = new ArrayDeque<Tegel>();
		
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
		
		return stapel;
	}

	private void voegTegelsToe(ArrayDeque<Tegel> stapel, int tegelNummer, int aantalTegels, TegelFabriekBestandLezer tfbl) {
		String[] tegelStrings = tfbl.getTegelStrings(tegelNummer);
		Tegel tegel = new Tegel(tegelStrings[0], tegelStrings[1]);
		stapel.push(tegel);

		for (int i = 1; i < aantalTegels; ++i) {
			stapel.add(new Tegel(tegel.getTegelPresentatie(), 
					tegel.getIdPresentatie()));
		}
	}	
}

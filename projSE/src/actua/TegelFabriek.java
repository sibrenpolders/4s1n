package actua;

import java.util.Vector;

import actua.Tegel;

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
	 * @param aantal
	 * 		aantal Tegel instanties die de vector moet bevatten
	 * @return
	 * 		Een stapel van tegel
	 */
	// TODO random functie belijken
	public Vector<Tegel> maakTegels(int aantal) {
		TegelFabriekBestandLezer tfbl = new TegelFabriekBestandLezer(tegelsBestand);
		Vector<Tegel> stapel = new Vector<Tegel>();
		
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

	private void voegTegelsToe(Vector<Tegel> stapel, int tegelNummer, int aantalTegels, TegelFabriekBestandLezer tfbl) {
		char[] landsdelen = tfbl.getLandsdeelMatrix(tegelNummer);
		
		Tegel tegel = new Tegel(landsdelen);
		stapel.add(tegel);

		for (int i = 1; i < aantalTegels; ++i) {
			stapel.add(tegel.clone());
		}
	}
}

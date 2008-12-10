package actua;

import java.util.ArrayList;
import java.util.Vector;

import com.sun.org.apache.bcel.internal.generic.LADD;

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
	 * Deze functie zal een ArrayList van char[] aanmaken.
	 * Dit is de stapel tegels waarmee het spel gespeeld wordt. 
	 * @param aantal
	 * 		aantal Tegel instanties die de vector moet bevatten
	 * @return
	 * 		Een stapel van tegel
	 */
	// TODO random functie belijken
	public ArrayList<char[]> maakTegelStack(int aantal) {
		TegelFabriekBestandLezer tfbl = new TegelFabriekBestandLezer(tegelsBestand);
		ArrayList<char[]> stapel = new ArrayList<char[]>();
		
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

	private void voegTegelsToe(ArrayList<char[]> stapel, int tegelNummer, int aantalTegels, TegelFabriekBestandLezer tfbl) {
		char[] tegel = tfbl.getLandsdeelMatrix(tegelNummer);		
		stapel.add(tegel);

		for (int i = 1; i < aantalTegels; ++i) {
			stapel.add(tegel.clone());
		}
	}	
}

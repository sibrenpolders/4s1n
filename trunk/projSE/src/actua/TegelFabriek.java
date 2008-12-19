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
	public ArrayDeque<Tegel> maakTegelDeque(int aantal) {
		TegelFabriekBestandLezer tfbl = new TegelFabriekBestandLezer(tegelsBestand);
		ArrayList<Tegel> stapel = new ArrayList<Tegel>();
		
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
		
		ArrayDeque<Tegel> queue = shakeNotStir(stapel);
		
		return queue;
		
//		ArrayDeque<Tegel> tegels = new ArrayDeque<Tegel>();
//		
////		tegels.add(new Tegel("wssswgwwgwwwg", "0111023320002"));
//		tegels.add(new Tegel("wwwwwgwwgwwgr", "0000012234456"));
//		Tegel t = new Tegel("sssssssssssss", "0000000000000");
//		tegels.add(t);
//		t = new Tegel("wwwwwgwwgwwgr", "0000012234456");
//		tegels.add(t);
//		t = new Tegel("wwgwwwwwgwwwg", "0012222210002");
//		tegels.add(t);
//		t = new Tegel("swwwssswwwsss", "0111000222000");
//		tegels.add(t);
//		t = new Tegel("sssssssssssss", "0000000000000");
//		tegels.add(t);
//		t = new Tegel("ssssssswgwsss", "0000000123000");
//		tegels.add(t);
//		t = new Tegel("swwwssswwwsss", "0111000222000");
//		tegels.add(t);
//		t = new Tegel("wssswgwwgwwgr", "0111023345567");
//		tegels.add(t);
//		t = new Tegel("wwwwwwwwwwwwk", "0000000000001");
//		tegels.add(t);
//		t = new Tegel("wwwwwgwwgwwgr", "0000012234456");
//		tegels.add(t);
//		
//		return tegels;
	}

	public Tegel geefStartTegel() {
		int i = (int) (Math.floor(Math.random()*3));
		
		return new Tegel(startTegels[i][0], startTegels[i][1]);
	}
	
	private ArrayDeque<Tegel> shakeNotStir(ArrayList<Tegel> stapel) {
		ArrayDeque<Tegel> queue = new ArrayDeque<Tegel>();

		int next;
		for (int i = 0; i < stapel.size(); ++i) {
			next = (int) (Math.floor(Math.random()*stapel.size()));
			queue.push(stapel.get(next));
		}
		
		return queue;
	}

	private void voegTegelsToe(ArrayList<Tegel> stapel, int tegelNummer, int aantalTegels, TegelFabriekBestandLezer tfbl) {
		String[] tegelStrings = tfbl.getTegelStrings(tegelNummer);
		Tegel tegel = new Tegel(tegelStrings[0], tegelStrings[1]);
		stapel.add(tegel);

		assert(tegel.getOrientatie() != 0);
		for (int i = 1; i < aantalTegels; ++i) {
			Tegel nieuweTegel = new Tegel(tegel.getTegelPresentatie(), 
					tegel.getIdPresentatie());
			assert(tegel.getOrientatie() != 0);
			stapel.add(nieuweTegel);
			
		}
	}	
}

package actua;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class Bestand {
	private String naam;
	private Spel spel;

	public Bestand(String naam, Spel spel) {
		this.naam = naam;
		this.spel = spel;
	}

	public Bestand() {

	}

	public Spel getSpel() {
		return spel;
	}

	public String getNaam() {
		return naam;
	}

	public void leesVanBestand(Spel spel) {
		leesVanBestand(spel, naam);
	}

	public void leesVanBestand(Spel spel, String naam) {
		FileInputStream fis;
		ObjectInputStream in;

		try {
			fis = new FileInputStream(naam);
			in = new ObjectInputStream(fis);

			spel = (Spel) in.readObject();

			in.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void schrijfNaarBestand(Spel spel) {
		schrijfNaarBestand(spel, naam);
	}

	public void schrijfNaarBestand(Spel spel, String naam) {
		try {
			FileOutputStream fos = new FileOutputStream(naam);
			ObjectOutputStream out = new ObjectOutputStream(fos);

			out.writeObject(spel);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
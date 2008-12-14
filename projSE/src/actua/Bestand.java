package actua;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Bestand {
	private String naam;
	private Spel spel;
	private Memento memento;

	public Bestand(String naam, Spel spel, Memento me) {
		this.naam = naam;
		this.spel = spel;
		this.memento = me;
	}

	public Bestand() {

	}

	public Spel getSpel() {
		return spel;
	}

	public Memento getMemento() {
		return memento;
	}

	public String getNaam() {
		return naam;
	}

	public void leesVanBestand(Spel spel, Memento memento) {
		leesVanBestand(spel, memento, naam);
	}

	public void leesVanBestand(Spel spel, Memento memento, String naam) {
		FileInputStream fis;
		ObjectInputStream in;

		try {
			fis = new FileInputStream(naam);
			in = new ObjectInputStream(fis);

			spel = (Spel) in.readObject();
//			memento = (Memento) ois.readObject();

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

	public void schrijfNaarBestand(Spel spel, Memento memento) {
		schrijfNaarBestand(spel, memento, naam);
	}

	public void schrijfNaarBestand(Spel spel, Memento memento, String naam) {
		try {
			FileOutputStream fos = new FileOutputStream(naam);
			ObjectOutputStream out = new ObjectOutputStream(fos);
			
			out.writeObject(spel);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
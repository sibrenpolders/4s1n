package actua;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Bestand implements Serializable {
	private String naam;
	private Spel spel;
	private Memento memento;

	public Bestand() {
		
	}
	
	public Spel getSpel() {
		return spel;
	}
	
	public Memento getMemento() {
		return memento;
	}

	public void leesVanBestand (String naam) {
		FileInputStream fis;
		ObjectInputStream ois;
		
		try {
			fis = new FileInputStream(naam);
			ois = new ObjectInputStream(fis);
			
			spel = (Spel)ois.readObject();
			memento = (Memento)ois.readObject();
			
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void schrijfNaarBestand (String naam) {
		FileOutputStream fos;
		ObjectOutputStream oos;
		
		try {
			fos = new FileOutputStream(naam);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(spel);
			oos.writeObject(memento);
			
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
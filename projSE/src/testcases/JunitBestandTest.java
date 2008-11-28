package testcases;

import actua.Memento;
import actua.Spel;
import actua.Bestand;
import junit.framework.TestCase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class JunitBestandTest extends TestCase {
	private Bestand bestand;

	protected void setUp() throws Exception {
		super.setUp();
		bestand = new Bestand();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testLeesVanBestand() {
		String naam = "bla.sam";
		
		bestand.leesVanBestand(naam);
		
		assertNotNull(bestand.getSpel());
		assertNotNull(bestand.getMemento());
	}

	public void testSchrijfNaarBestand() {
		Memento memento=null,mTest=null;
		Spel spel=null,spelTest=null;
		String naam = "bla.sam";
		FileInputStream fis;
		ObjectInputStream ois;
		
		bestand.schrijfNaarBestand(naam);
		
		try {
			fis = new FileInputStream(naam);
			ois = new ObjectInputStream(fis);
			
			mTest = (Memento)ois.readObject();
			spelTest = (Spel)ois.readObject();
			
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		assertEquals("Memento juist opgeslagen",memento,mTest);
		assertEquals("Spel juist opgeslagen",spel,spelTest); 
	}
}

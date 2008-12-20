package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;

public class Spel implements Serializable{
	private static final long serialVersionUID = 2689906234458876556L;
	private TafelVerwerker tafelVerwerker;
	private SpelerVerwerker spelerVerwerker;
	
	public static final char ROOD = 'r';
	public static final char BLAUW = 'b';
	public static final char WIT = 'w';
	public static final char ORANJE = 'o';
	public static final char GEEL = 'g';
	public static final short MAXAANTALSPELERS = 5; //cfr. het aantal beschikbare kleuren
	public static final short MINAANTALSPELERS = 2;
	
	public Spel() {
		tafelVerwerker = new TafelVerwerker();
		spelerVerwerker = new SpelerVerwerker();
	}

	public void volgendeSpeler() {
		spelerVerwerker.volgendeSpeler();
	}
	
	public char geefHuidigeSpeler() {
		return spelerVerwerker.geefHuidigeSpeler();
	}

	public ArrayList<Vector2D> geefGeldigeMogenlijkheden(String[] tegel) {
		return tafelVerwerker.geefGeldigeMogenlijkheden(tegel);
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeObject(tafelVerwerker);
		out.writeObject(spelerVerwerker);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		tafelVerwerker = (TafelVerwerker) in.readObject();
		spelerVerwerker = (SpelerVerwerker) in.readObject();
	}
	 
	 private void readObjectNoData() throws ObjectStreamException {
		 
	 }

	public void voegSpelerToe(short s, String naam, char kleur, int i) {
		spelerVerwerker.voegSpelerToe(s, naam, kleur, i, tafelVerwerker);
	}

	public void vulStapel(int aantal) {
		tafelVerwerker.vulStapel(aantal);
	}

	public String[] vraagNieuweTegel() {
		return tafelVerwerker.vraagNieuweTegel();
	}

	public String[] neemTegelVanStapel() {
		return tafelVerwerker.neemTegelVanStapel();
	}

	public void legTerugEinde(String[] tegel) {
		tafelVerwerker.legTerugEinde(tegel);
	}

	public int getStapelSize() {
		return tafelVerwerker.getStapelSize();
	}

	public boolean plaatsTegel(String[] tegel, Vector2D coord) {
		return tafelVerwerker.plaatsTegel(tegel, coord);
	}

	public boolean isTegelPlaatsingGeldig(String[] tegel, Vector2D coord) {
		return tafelVerwerker.isTegelPlaatsingGeldig(tegel, coord);
	}

	public Vector2D getStartTegelPos() {
		return tafelVerwerker.getStartTegelPos();
	}
}

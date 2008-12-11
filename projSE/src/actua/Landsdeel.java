package actua;

import java.util.ArrayList;

public class Landsdeel {
	public static final char STAD = 's';
	public static final char WEI = 'w';
	public static final char WEG = 'g';
	public static final char KLOOSTER= 'k';
	
	private ArrayList<Pion> pionnen;
	private char type;

	public Landsdeel() {
		
	}

	public Landsdeel(char type) {
		this.type = type;
	}
	
	public char getType() {
		return type;
	}

	public void plaatsPion(Pion pion) {
		pionnen.add(pion);
	}

	public boolean isPionGeplaatst() {
		return pionnen.size() != 0;
	}

}


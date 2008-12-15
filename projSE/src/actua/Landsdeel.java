package actua;

import java.util.ArrayList;

public class Landsdeel {
	public static final char STAD = 's';
	public static final char WEI = 'w';
	public static final char WEG = 'g';
	public static final char KLOOSTER= 'k';
	
	private Pion pion;
	private char type;

	public Landsdeel() {
		pion= null;
	}

	public Landsdeel(char type) {
		this.type = type;
		pion= null;
	}
	
	public char getType() {
		return type;
	}

	public void plaatsPion(Pion pion) {
		if (pion != null) {
			this.pion = pion;
		}
	}

	public boolean isPionGeplaatst() {
		return pion != null;
	}
	
	public Landsdeel clone() {
		Landsdeel ld = new Landsdeel(type);	
		return ld;
	}
	
	public Pion neemPionnenTerug() {
		Pion returnPion = pion;
		this.pion = null;
		return returnPion;
	}
}


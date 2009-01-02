package actua;

import java.util.ArrayList;

public class Landsdeel {
	public static final char STAD = 's';
	public static final char WEI = 'w';
	public static final char WEG = 'g';
	public static final char KLOOSTER= 'k';
	public static final char KRUISPUNT = 'r';
	
	private char pion;
	private char type;

	public Landsdeel() {
		pion = 0;
	}

	public Landsdeel(char type) {
		this.type = type;
		pion = 0;
	}
	
	public char getType() {
		return type;
	}
	
	public boolean isKruispunt()
	{
		return type == KRUISPUNT;
	}

	public void plaatsPion(char pion) {
		this.pion = pion;		
	}

	public boolean isPionGeplaatst() {
		return pion != 0;
	}
	
	public Landsdeel clone() {
		Landsdeel ld = new Landsdeel(type);	
		return ld;
	}
	
	public char neemPionnenTerug() {
//		Pion returnPion = pion;
//		this.pion = ;
		return pion;
	}
}


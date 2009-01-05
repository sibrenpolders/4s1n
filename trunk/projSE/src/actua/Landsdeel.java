package actua;

public class Landsdeel {
	public static final char STAD = 's';
	public static final char WEI = 'w';
	public static final char WEG = 'g';
	public static final char KLOOSTER = 'k';
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

	public char getPion() {
		return pion;
	}

	public void setPion(char pion) {
		this.pion = pion;
	}

	public char verwijderPion() {
		char result = pion;
		this.pion = 0;
		return result;
	}

	public boolean isPionGeplaatst() {
		return pion != 0;
	}

	public boolean isKruispunt() {
		return type == KRUISPUNT;
	}

	public Landsdeel clone() {
		Landsdeel ld = new Landsdeel(type);
		return ld;
	}
}

package actua;
public class Landsdeel {
	public static final char STAD = 's';
	public static final char WEI = 'w';
	public static final char WEG = 'g';
	public static final char KLOOSTER= 'k';
	
	private boolean pion;
	private char type;

	public Landsdeel() {
		
	}

	public Landsdeel(char type) {
		this.type = type;
	}
	
	public char getType() {
		return type;
	}

	public void plaatsPion() {
		pion = true;
	}

	public boolean isPionGeplaatst() {
		return pion;
	}

}


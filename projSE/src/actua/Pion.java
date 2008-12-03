package actua;

public class Pion {
	private Vector2D positie;
	private char type;
	private boolean geplaatst;
	private char kleur;

	public Pion(){
		kleur='0';	}
	
	public Pion(char kleur) {
		this.kleur = kleur;		
	}

	public void setPositie(Vector2D positie) {
		this.positie=positie;
	}

	public void setType(char type) {
		this.type=type;
	}

	public Vector2D getPositie() {
		return null;
	}

	public char getType() {
		return type;
	}

	public char getKleur() {
		return kleur;
	}

	public void setKleur(char kleur) {
		this.kleur = kleur;
	}
}
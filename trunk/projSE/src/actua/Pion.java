package actua;

public class Pion {
	private boolean geplaatst;
	private char kleur;

	public Pion(){
		kleur='0';	}
	
	public Pion(char kleur) {
		this.kleur = kleur;		
	}

	public Vector2D getPositie() {
		return null;
	}

	public char getKleur() {
		return kleur;
	}

	public void setKleur(char kleur) {
		this.kleur = kleur;
	}
}
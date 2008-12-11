package actua;

public class Pion {
	private boolean geplaatst;
	private char kleur;

	public Pion() {
		this((char) 0);
	}

	public Pion(char kleur) {
		this.kleur = kleur;
		geplaatst = false;
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

	public void toggleGeplaatst() {
		geplaatst = !geplaatst;
	}

	public void zetGeplaatst(boolean b) {
		geplaatst = b;
	}

	public boolean getGeplaatst() {
		return geplaatst;
	}
}
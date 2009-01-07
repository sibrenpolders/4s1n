package Spelers;

import java.io.IOException;
import java.io.Serializable;

public class Pion implements Serializable {
	private static final long serialVersionUID = -8119438347413483304L;
	private boolean geplaatst;
	private char kleur;

	public Pion() {
		this((char) 0);
	}

	public Pion(char kleur) {
		this.kleur = kleur;
		geplaatst = false;
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

	// FILE I/O

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeBoolean(geplaatst);
		out.writeChar(kleur);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		geplaatst = in.readBoolean();
		kleur = in.readChar();
	}
}
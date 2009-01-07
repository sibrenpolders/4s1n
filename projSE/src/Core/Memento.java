package Core;

public class Memento {
	public Object status;
	public char speler;
	public boolean type;

	public Memento(Object status, char speler, boolean type) {
		this.status = status;
		this.speler = speler;
		this.type = type;
	}

	public Object getStatus() {
		return status;
	}

	public char getSpeler() {
		return speler;
	}

	public boolean getType() {
		return type;
	}

	public void setStatus(Object status) {
		this.status = status;
	}

	public void setSpeler(char speler) {
		this.speler = speler;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	@SuppressWarnings( { "unused" })
	// deze functie is er enkel om de unit test grondiger te kunnen doen.
	public boolean equals(Memento andereMemento) {
		// een Memento instantie is gelijk aan een andere Memento instantie
		// a.s.a.:
		// * Beide speler's gelijk zijn
		// * Beide types gelijk zijn
		// * Beide status objecten null zijn of gelijk zijn.
		return speler == andereMemento.speler
				&& type == andereMemento.type
				&& ((status == null && andereMemento.status == null) || (status
						.equals(andereMemento.status)));
	}
}

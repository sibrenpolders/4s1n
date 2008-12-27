package actua;

public class AI extends Speler {
	public final static short EASY = 1;
	public final static short HARD = 2;
	private short niveau;
	private Strategy strategy;
	private TafelVerwerker tafelVerwerker;

	public AI(String naam, char kleur, short niveau, TafelVerwerker tv,
			long score) {
		super(naam, kleur, score);
		this.tafelVerwerker = tv;
		setNiveau(niveau);
	}

	public AI(Speler speler, short niveau, TafelVerwerker tv) {
		this(speler.getNaam(), speler.getKleur(), niveau, tv, speler.getScore());
		this.pionnen = speler.getPionnen();
	}

	public AI() {
		super();
		this.tafelVerwerker = null;
		setNiveau((short) 0);
	}

	public short getNiveau() {
		return niveau;
	}

	public void setNiveau(short niveau) {
		switch (niveau) {
		case EASY:
			strategy = new Easy(tafelVerwerker);
			this.niveau = niveau;
			break;
		case HARD:
			strategy = new Hard(tafelVerwerker);
			this.niveau = niveau;
			break;
		default:
			this.niveau = 0;
			strategy = null;
		}
	}

	public SpelBeurtResultaat doeZet() {
		boolean gedaan = false;
		SpelBeurtResultaat result = null;

		while (!gedaan) {
			String[] t = tafelVerwerker.neemTegelVanStapel();
			Vector2D plaats = null;
			if ((plaats = berekenPlaatsTegel(t)) != null) {
				gedaan = true;
				short plaatsPion = -1;
				if (this.ongeplaatstePionAanwezig())
					plaatsPion = (short) berekenPlaatsPion(kleur, t, plaats);
				result = new SpelBeurtResultaat(t, plaats, kleur, plaatsPion);
			}
		}

		return result;
	}

	private Vector2D berekenPlaatsTegel(String[] t) {
		return strategy.berekenPlaatsTegel(t);
	}

	private int berekenPlaatsPion(char kleur, String[] t, Vector2D tegelCoord) {
		return strategy.berekenPlaatsPion(kleur, t, tegelCoord);
	}
}

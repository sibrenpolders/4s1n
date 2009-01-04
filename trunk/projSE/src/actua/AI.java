package actua;

public class AI extends Speler {
	private static final long serialVersionUID = 5271140789401358626L;
	public final static short EASY = 1;
	public final static short HARD = 2;
	private short niveau;
	private Strategy strategy;

	public AI(String naam, char kleur, short niveau, long score) {
		super(naam, kleur, score);
		setNiveau(niveau);
	}

	public AI(Speler speler, short niveau) {
		this(speler.getNaam(), speler.getKleur(), niveau, speler.getScore());
		this.pionnen = speler.getPionnen();
	}

	public short getNiveau() {
		return niveau;
	}

	public void setNiveau(short niveau) {
		switch (niveau) {
		case EASY:
			strategy = new Easy();
			this.niveau = niveau;
			break;
		case HARD:
			strategy = new Hard();
			this.niveau = niveau;
			break;
		default:
			this.niveau = 0;
			strategy = null;
		}
	}

	public SpelBeurtResultaat doeZet(TafelVerwerker tafelVerwerker) {
		boolean gedaan = false;
		SpelBeurtResultaat result = null;

		while (!gedaan) {
			String[] t = tafelVerwerker.neemTegelVanStapel();
			Vector2D plaats = null;
			if ((plaats = strategy.berekenPlaatsTegel(t, tafelVerwerker)) != null) {
				tafelVerwerker.plaatsTegel(t, plaats);
				gedaan = true;
				short plaatsPion = -1;
				if (this.ongeplaatstePionAanwezig()) {
					plaatsPion = (short) strategy.berekenPlaatsPion(plaats,
							tafelVerwerker);
					if (plaatsPion != -1) {
						tafelVerwerker.plaatsPion(plaats, plaatsPion, kleur);
						this.plaatsOngeplaatstePion();
					}
				}
				result = new SpelBeurtResultaat(t, plaats, kleur, plaatsPion);
			} else {
				tafelVerwerker.legTerugEinde(t);
			}
		}

		return result;
	}
}

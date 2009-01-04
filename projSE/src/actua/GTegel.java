package actua;

public abstract class GTegel {
	protected static final int TEGEL_PRESENTATIE = 0;
	protected static final int MAX_DRAAIING = 4;
	protected static final int NB_ROWS = 5;
	protected static final int NB_COLS = 3;

	protected String[] tegel;
	protected Vector2D tegelCoord;
	protected char pion[][];
	protected int orientatie;
	protected Spel spel;

	public GTegel(String[] tegel_, Spel spel, Vector2D tegelCoord) {
		this.spel = spel;
		this.tegel = tegel_;
		if (tegel_.length == 3) {
			orientatie = Integer.parseInt(tegel_[Spel.ORIENTATIE]);
		} else {
			orientatie = 0;
		}
		this.tegelCoord = new Vector2D(tegelCoord);
		pion = new char[5][3];
	}

	// COORDS

	public Vector2D getCoordsRelativeToStartTegel() {
		return tegelCoord;
	}

	public int getRow() {
		if (tegelCoord != null) {
			return this.tegelCoord.getY();
		}

		return -1;
	}

	public int getCol() {
		if (tegelCoord != null) {
			return this.tegelCoord.getX();
		}

		return -1;
	}

	// LANDSDEELZONES <-> RASTER van CHARS

	// return == Vector2D(col, row)
	protected Vector2D getColRowVoorLandsdeelZone(short zone) {
		switch (zone) {
		case 0:
			return new Vector2D(0, 1);
		case 1:
			return new Vector2D(0, 0);
		case 2:
			return new Vector2D(1, 0);
		case 3:
			return new Vector2D(2, 0);
		case 4:
			return new Vector2D(2, 1);
		case 5:
			return new Vector2D(2, 2);
		case 6:
			return new Vector2D(2, 3);
		case 7:
			return new Vector2D(2, 4);
		case 8:
			return new Vector2D(1, 4);
		case 9:
			return new Vector2D(0, 4);
		case 10:
			return new Vector2D(0, 3);
		case 11:
			return new Vector2D(0, 2);
		case 12:
			return new Vector2D(1, 2);
		}
		return null;
	}

	// TODO Gebruik gemaakt van de klasse Tegel: niet echt ok.
	protected short getLandsdeelZoneVoorRowCol(int row, int col) {
		if (row == 0 && col == 0) {
			return Tegel.NOORD_WEST;
		} else if ((row == 0 || row == 1) && col == 1) {
			return Tegel.NOORD;
		} else if (row == 0 && col == 2) {
			return Tegel.NOORD_OOST;
		} else if (row == 1 && col == 0) {
			return Tegel.WEST_NOORD;
		} else if (row == 1 && col == 2) {
			return Tegel.OOST_NOORD;
		} else if (row == 2 && col == 0) {
			return Tegel.WEST;
		} else if (row == 2 && col == 1) {
			return Tegel.MIDDEN;
		} else if (row == 2 && col == 2) {
			return Tegel.OOST;
		} else if (row == 3 && col == 0) {
			return Tegel.WEST_ZUID;
		} else if (row == 3 && col == 2) {
			return Tegel.OOST_ZUID;
		} else if (row == 4 && col == 0) {
			return Tegel.ZUID_WEST;
		} else if (row == 4 && col == 2) {
			return Tegel.ZUID_OOST;
		} else if ((row == 3 || row == 4) && col == 1) {
			return Tegel.ZUID;
		} else {
			return (short) -1;
		}
	}

	// PIONNEN

	protected boolean plaatsPionInSectie(int row, int col, char pionKleur) {
		if (pion[row][col] != 0)
			return false;
		else {
			pion[row][col] = pionKleur;
			return true;
		}
	}

	protected char geefPionInSectie(int row, int col) {
		return pion[row][col];
	}

	protected void verwijderPionInSectie(int row, int col) {
		pion[row][col] = 0;
	}

	protected void updatePionnen() {
		for (int i = 0; i < NB_ROWS; ++i)
			for (int j = 0; j < NB_COLS; ++j) {
				verwijderPionInSectie(i, j);
				int zone = getLandsdeelZoneVoorRowCol(i, j);
				Vector2D lompeCoord = new Vector2D(tegelCoord.getY(),
						tegelCoord.getX());
				if (spel.isPionGeplaatst(lompeCoord, zone))
					plaatsPionInSectie(i, j, spel.geefPionKleur(lompeCoord,
							zone));
			}
	}

	public abstract void roteer(boolean richting);

	public abstract void update();
}

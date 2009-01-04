package actua;

import java.util.ArrayList;

public class Hard implements Strategy {
	public Hard() {
	}

	public int berekenPlaatsPion(Vector2D tegelCoord,
			TafelVerwerker tafelVerwerker) {
		for (int i = 0; i < Tegel.MAX_GROOTTE; ++i) {
			if (tafelVerwerker.isPionPlaatsingGeldig(tegelCoord, i))
				return i;
		}

		return -1; // geen geldige plaatsing gevonden
	}

	// indien mogelijk, wordt een plaats teruggegeven waar dan een pion kan
	// geplaatst worden
	public Vector2D berekenPlaatsTegel(String[] t, TafelVerwerker tafelVerwerker) {
		String tempOri = t[2];
		Tegel tegel = new Tegel(t[0], t[1], Short.parseShort(t[2]));

		for (int i = 0; i < 4; ++i) {
			t[2] = Short.toString(tegel.getOrientatie());
			ArrayList<Vector2D> list = tafelVerwerker
					.geefMogelijkeZettenVoorGegevenTegel(tegel.getTegelString());

			for (int j = 0; j < list.size(); ++j) {
				tafelVerwerker.plaatsTegel(tegel.getTegelString(), list.get(j));
				if (berekenPlaatsPion(list.get(j), tafelVerwerker) != -1) {
					tafelVerwerker.undo();
					return list.get(j);
				}
				tafelVerwerker.undo();
			}
			tegel.draaiTegel(true);
		}

		// Easy search
		t[2] = tempOri;
		tegel = new Tegel(t[0], t[1], Short.parseShort(t[2]));
		for (int i = 0; i < 4; ++i) {
			t[2] = Short.toString(tegel.getOrientatie());
			ArrayList<Vector2D> list = tafelVerwerker
					.geefMogelijkeZettenVoorGegevenTegel(tegel.getTegelString());
			if (list.size() > 0)
				return list.get(0);
			tegel.draaiTegel(true);
		}

		t[2] = tempOri;
		return null;
	}
}

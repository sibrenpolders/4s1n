package actua;

import java.util.ArrayList;

public class Easy implements Strategy {
	private TafelVerwerker tafelVerwerker;

	public Easy(TafelVerwerker tv) {
		this.tafelVerwerker = tv;
	}

	// tegel moet al geplaatst zijn op tegelCoord
	public int berekenPlaatsPion(char kleur, String[] t, Vector2D tegelCoord) {
		for (int i = 0; i < Tegel.MAX_GROOTTE; ++i) {
			if (tafelVerwerker.isPionPlaatsingGeldig(t, tegelCoord, i))
				return i;
		}

		return -1;
	}

	// eerste geldige plaats wordt teruggegeven, ongeacht of men er een pion op
	// kan plaatsen of niet
	// relatieve coords (row, column) worden teruggegeven
	public Vector2D berekenPlaatsTegel(String[] t) {
		String tempOri = t[2];
		Tegel tegel = new Tegel(t[0], t[1], Short.parseShort(t[2]));

		for (int i = 0; i < 4; ++i) {
			tegel.draaiTegel(true);
			t[2] = Short.toString(tegel.getOrientatie());
			ArrayList<Vector2D> list = tafelVerwerker.geefGeldigeMogelijkheden(tegel.getTegelString());
			
			if(list.size() > 0)
				return list.get(0);
		}
		
		t[2] = tempOri;
		return null;
	}
}
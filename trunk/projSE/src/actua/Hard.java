package actua;

import java.util.ArrayList;

public class Hard implements Strategy {
	private TafelVerwerker tafelVerwerker;

	public Hard(TafelVerwerker tv) {
		this.tafelVerwerker = tv;
	}

	// tegel moet al geplaatst zijn op tegelCoord
	// kan erna terug weggenomen worden
	public int berekenPlaatsPion(char kleur, String[] t, Vector2D tegelCoord) {
		for (int i = 0; i < Tegel.MAX_GROOTTE; ++i) {
			if (tafelVerwerker.isPionPlaatsingGeldig(t, tegelCoord, i))
				return i;
		}

		return -1;
	}

	// indien mogelijk, wordt een plaats teruggegeven waar dan een pion kan
	// geplaatst worden
	public Vector2D berekenPlaatsTegel(String[] t) {
		String tempOri = t[2];
		Tegel tegel = new Tegel(t[0], t[1], Short.parseShort(t[2]));

		for (int i = 0; i < 4; ++i) {
			tegel.draaiTegel(true);
			t[2] = Short.toString(tegel.getOrientatie());
			ArrayList<Vector2D> list = tafelVerwerker.geefGeldigeMogelijkheden(tegel.getTegelString());
			
			for(int j = 0; j < list.size(); ++j)
			{
				tafelVerwerker.plaatsTegel(tegel.getTegelString(), list.get(j));
				if(berekenPlaatsPion(Speler.INACTIVE_COLOR, tegel.getTegelString(), list.get(j)) != -1)
				{
					tafelVerwerker.undo();				
					return list.get(j);
				}
				tafelVerwerker.undo();
			}
		}
		
		

		// Easy search
		t[2] = tempOri;
		tegel = new Tegel(t[0], t[1], Short.parseShort(t[2]));
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

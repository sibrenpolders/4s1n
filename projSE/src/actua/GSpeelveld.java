package actua;
import java.util.ArrayList;

import com.trolltech.qt.gui.QPixmap;

public abstract class GSpeelveld {
	private static String DEFAULT_BACKGROUND;
	protected ArrayList<ArrayList<GTegel>> gTegels;
	private String achtergrond;
	private Spel spel;
	private Vector2D startGTegel;
	protected Camera camera;
	
	public GSpeelveld() {
		camera = new Camera();
	}
	
	public GSpeelveld(Spel spel) {
		this.spel=spel;
		camera = new Camera();
	}

	public ArrayList<ArrayList<GTegel>> getGTegels() {
		return gTegels;
	}

	public void setGTegels(ArrayList<ArrayList<GTegel>> tegels) {
		gTegels = tegels;
	}

	public String getAchtergrond() {
		return achtergrond;
	}

	public void setAchtergrond(String achtergrond) {
		this.achtergrond = achtergrond;
	}
	

	public Spel getSpel() {
		return spel;
	}

	public void setSpel(Spel spel) {
		this.spel = spel;
	}

	public void toonSpeelveld () {
		
	}

	public void updateSpeelveld () {
		
	}
	
	public void voegTegelToe() {
		
	}
	
	public abstract void hide();
	public abstract void show();
	
	
	
	/**
	 * Zal de het veldoverzicht wijzigen.
	 * @param nieuwePositie
	 * 	De nieuwe positie van het centrum van het veld (Vector3D)
	 */
	protected boolean beweegCamera(Vector3D nieuwePositie) {
		if (cameraBewegingGeldig(nieuwePositie)) {
			camera.veranderStandpunt(nieuwePositie);
			return true;
		}
		
		return false;
	}

	/**
	 * Zal nagaan of het veldoverzicht kan gewijzigd worden naar
	 * de ingegeven positie.
	 * 
	 * @param nieuwePositie
	 * 	De positie naar waar de veldwijziging gedaan zal worden.
	 * @return
	 * 	True als de veldwijziging geldig is, Fals anders.
	 */
	protected boolean cameraBewegingGeldig(Vector3D nieuwePositie) {
		return camera.bewegingGeldig(nieuwePositie);
	}
	
	/**
	 * Deze functie zal het veld overzicht weer op de standaard waarden 
	 * zetten.
	 */
	protected void herstelOverzicht() {
		camera.herstelOverzicht();
	}
	
	protected void setOogpunt(Camera camera) {
		this.camera= camera;
	}

	protected Camera getOogpunt() {
		return camera;
	}
	
	//functies van tafel m.b.t. dubbele arraylist 
	//werken hier op een qt dubbele arraylist
	//misschien tijdelijk
	
	public boolean  voegTegelToeAanGrafischeLijst(Tegel tegel, Vector2D coord,QPixmap pixmap) {
		// startTegel wordt gezet
		// coord maken niet uit startTegel staat op (0, 0)
		if (gTegels == null) {
			gTegels = new ArrayList<ArrayList<GTegel>>();
			gTegels.add(new ArrayList<GTegel>());
			gTegels.get(0).add(new QTTegel(tegel));
			startGTegel = new Vector2D(0,0);
			return true;
		}

		int rij = startGTegel.getX() + coord.getX();
		int kolom = startGTegel.getY() + coord.getY();
		// mag de tegel hier gezet worden? M.a.w. zijn zijn buren geldig?
		// TODO

		ArrayList<GTegel> kolomVector;

		// boven of onder de starttegel
		if (rij == -1) {
			rij = 0;
			kolomVector = addRij(rij);
			startGTegel.setX(startGTegel.getX() + 1);
		} else if (rij == gTegels.size()) {
			kolomVector = addRij(rij);
		} else { // toevoegen in een bestaande rij
			kolomVector = gTegels.get(rij);
		}

		// links of rechts van de starttegel
		if (kolom == -1) {
			adjustAll(rij, kolom);
			startGTegel.setY(startGTegel.getY() + 1);
			kolom = (kolom < 0) ? 0 : kolom;
		} else if (kolom > gTegels.get(rij).size()) {
			addSpacers(rij, kolom);
		}

		if (kolom < kolomVector.size() && kolomVector.get(kolom) == null) {
			kolomVector.remove(kolom);
		}


		kolomVector.add(kolom, new QTTegel(tegel,pixmap));
//		// TODO functie update landsdelen schrijven
//		updateLandsdelen(rij, kolom);
		
		return true;
	}
	
	private ArrayList<GTegel> addRij(int rij) {
		ArrayList<GTegel> kolomVector = new ArrayList<GTegel>();
		gTegels.add(rij, kolomVector);
		kolomVector = gTegels.get(rij);

		return kolomVector;
	}

	private void adjustAll(int rij, int kolom) {
		ArrayList<GTegel> kolomVector;
		int aantal = (int) Math.abs(kolom - startGTegel.getY());

		System.out.println(aantal);
		for (int i = 0; i < gTegels.size(); ++i) {
			kolomVector = gTegels.get(i);

			for (int j = 0; j < aantal - kolomVector.size()+1; ++j) {
				kolomVector.add(0, null);
			}
		}
	}

	private void addSpacers(int rij, int kolom) {
		ArrayList<GTegel> kolomVector = gTegels.get(rij);
		for (int i = kolomVector.size(); i < kolom; ++i) {
			kolomVector.add(null);
		}
	}

	
}
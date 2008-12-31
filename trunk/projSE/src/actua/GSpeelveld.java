package actua;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public abstract class GSpeelveld extends Observable implements Observer {
	protected static String DEFAULT_BACKGROUND;
	protected ArrayList<ArrayList<GTegel>> gTegels;
	protected String achtergrond;
	protected Spel spel;
	protected Vector2D startGTegel;
	protected Camera camera;

	public GSpeelveld() {
		camera = new Camera();
		gTegels = new ArrayList<ArrayList<GTegel>>();
		this.addObserver(this);
	}

	public GSpeelveld(Spel spel) {
		this.spel = spel;
		camera = new Camera();
		gTegels = new ArrayList<ArrayList<GTegel>>();
	}

	protected ArrayList<ArrayList<GTegel>> getGTegels() {
		return gTegels;
	}

	protected String getAchtergrond() {
		return achtergrond;
	}

	protected void setAchtergrond(String achtergrond) {
		this.achtergrond = achtergrond;
	}

	protected Spel getSpel() {
		return spel;
	}

	protected void setSpel(Spel spel) {
		this.spel = spel;
	}

	protected abstract void clearSpeelveld();

	protected abstract void initialiseerSpeelveld();

	protected abstract void updateSpeelveld();

	protected abstract void voegTegelToe(String[] tegel, Vector2D coord);

	//
	// functies voor gTegels
	//

	protected ArrayList<GTegel> addRij(int rij) {
		ArrayList<GTegel> kolomVector = new ArrayList<GTegel>();
		gTegels.add(rij, kolomVector);
		kolomVector = gTegels.get(rij);

		return kolomVector;
	}

	protected void adjustAll(int rij, int kolom) {
		ArrayList<GTegel> kolomVector;
		int aantal = (int) Math.abs(kolom - startGTegel.getY());
		int offset;

		for (int i = 0; i < gTegels.size(); ++i) {
			kolomVector = gTegels.get(i);
			offset = getNegativeSize(kolomVector);
			for (int j = 0; j < aantal + 1 - offset; ++j) {
				kolomVector.add(0, null);
			}
		}
	}

	protected int getNegativeSize(ArrayList<GTegel> kolomVector) {
		int aantal = 0;
		for (int i = 0; i <= startGTegel.getY(); ++i) {
			++aantal;
		}

		return aantal;
	}

	protected void addSpacers(int rij, int kolom) {
		ArrayList<GTegel> kolomVector = gTegels.get(rij);
		for (int i = kolomVector.size(); i < kolom; ++i) {
			kolomVector.add(null);
		}
	}

	public int getBiggestSize() {
		int size = gTegels.get(0).size();

		for (int i = 1; i < gTegels.size(); i++)
			if (gTegels.get(i).size() > size)
				size = gTegels.get(i).size();

		return size;
	}
}
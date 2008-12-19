package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class TafelVerwerker implements Serializable{
	private static final long serialVersionUID = -6431108242978335095L;
	private static final int AANTAL_TEGELS = 72;
	private ArrayDeque<String[]> stapel;
	private Tafel tafel;

	public TafelVerwerker() {
		TegelFabriek tfb = new TegelFabriek();
		stapel = tfb.maakTegelDeque(AANTAL_TEGELS-1);
		tafel = new Tafel();
	}

	public TafelVerwerker(int aantalTegels) {
		TegelFabriek tfb = new TegelFabriek();
		stapel = tfb.maakTegelDeque(aantalTegels-1);
		tafel = new Tafel();
	}
	
	public ArrayDeque<String[]> getStapel() {
		return stapel;
	}

	public String[] neemLaatsteTegel(){
		return stapel.pollFirst();
	}
	
	public String[] vraagNieuweTegel() {
		return stapel.peekFirst();
	}

	public String[] neemTegelVanStapel() {
		return stapel.pop();
	}
	
	public void legTerugEinde(String[] tegel){
		stapel.addLast(tegel);
	}
	
	public void legTerugTop(String[] tegel){
		stapel.addFirst(tegel);
	}
	
	public void verwerkTegel() {

	}

	public Vector2D vraagCoord() {
		return null;
	}

	/**
	 * Zal de method aanroepen die een tegel op het veld plaatst
	 * 
	 * @param tegel
	 *            De tegel die geplaatst moet worden
	 * @param coord
	 *            De positie op het veld waar tegel geplaatst moet worden
	 *            (relatief t.o.v. de starttegel)
	 * @return True indien de tegel geplaatst kon worden False anders
	 */
	public boolean plaatsTegel(String[] tegel, Vector2D coord) {
		return tafel.plaatsTegel(tegel, coord);
	}

	public boolean isTegelPlaatsingGeldig(String[] t, Vector2D coord) {
		return tafel.isTegelPlaatsingGeldig(t, coord);
	}
	public boolean isPionPlaatsingGeldig(String[] t, Vector2D tegelCoord, int pionCoord) {
		return tafel.isPionPlaatsingGeldig(t, tegelCoord, pionCoord);
	}
	
	/**
	 * @param tegelCoord
	 *            De tegel waarop de pion geplaatst moet worden (coordinaten
	 *            zijn relatief t.o.v. de starttegel
	 * @param pionCoord
	 *            plaats op de tegel waarop de pion geplaatst moet worden.
	 * @param pion
	 *            pion die geplaatst moet worden.
	 */
	public boolean plaatsPion(Vector2D tegelCoord, int pionCoord, Pion pion) {
		return tafel.plaatsPion(tegelCoord, pionCoord, pion);
	}

	public Vector2D getBeginPositie() {
		return tafel.getBeginPositie();
	}

	public int getHoogte() {
		return tafel.getHoogte();
	}

	public int getBreedte() {
		return tafel.getBreedte();
	}
	
	public void vulStapel(int aantal){
		TegelFabriek tfb = new TegelFabriek();
		
		stapel = tfb.maakTegelDeque(aantal-1);
	}
	
	public int aantalTegels(){
		return stapel.size();
	}

	public Tegel getLaatstGeplaatsteTegel() {
		return tafel.getLaatstGeplaatsteTegel();
	}
	
	public Vector2D getStartTegelPos() {
		return tafel.getStartTegel();
	}

	public boolean plaatsingPionMogelijk(String[] t, Vector2D tegelCoord, int pionCoord) {
		return tafel.isPionPlaatsingGeldig(t, tegelCoord, pionCoord);
	}
	
	 private void writeObject(java.io.ObjectOutputStream out) throws IOException {
			out.writeObject(stapel);
			out.writeObject(tafel);
	 }
	 
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		 stapel = (ArrayDeque<String[]>) in.readObject();
		 tafel = (Tafel) in.readObject();
	 }
	 
	 private void readObjectNoData() throws ObjectStreamException {
		 
	 }
	 
	 public boolean isTegelPlaatsBaar(Tegel tegel) {
		 
		 return true;
	 }

	public int getStapelSize() {
		return stapel.size();
	}

	public ArrayList<Vector2D> geefGeldigeMogenlijkheden(String[] tegel) {
		return null;
	}
}
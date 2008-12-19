package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayDeque;

public class TafelVerwerker implements Serializable{
	private static final long serialVersionUID = -6431108242978335095L;
	private static final int AANTAL_TEGELS = 72;
	private ArrayDeque<Tegel> stapel;
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
	
	public ArrayDeque<Tegel> getStapel() {
		return stapel;
	}

	public Tegel neemLaatsteTegel(){
		return stapel.pollFirst();
	}
	
	public Tegel vraagNieuweTegel() {
		return stapel.peekFirst();
	}

	public Tegel neemTegelVanStapel() {
		return stapel.pop();
	}
	
	public void legTerugEinde(Tegel tegel){
		stapel.addLast(tegel);
	}
	
	public void legTerugTop(Tegel tegel){
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
	public boolean plaatsTegel(Tegel tegel, Vector2D coord) {
		return tafel.plaatsTegel(tegel, coord);
	}

	public boolean isTegelPlaatsingGeldig(Tegel tegel, Vector2D coord) {
		return tafel.isTegelPlaatsingGeldig(tegel, coord);
	}
	public boolean isPionPlaatsingGeldig(Tegel tegel, Vector2D tegelCoord, int pionCoord) {
		return tafel.isPionPlaatsingGeldig(tegel, tegelCoord, pionCoord);
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

	public boolean plaatsingPionMogelijk(Tegel t, Vector2D tegelCoord, int pionCoord) {
		return tafel.isPionPlaatsingGeldig(t, tegelCoord, pionCoord);
	}
	
	 private void writeObject(java.io.ObjectOutputStream out) throws IOException {
			out.writeObject(stapel);
			out.writeObject(tafel);
	 }
	 
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		 stapel = (ArrayDeque<Tegel>) in.readObject();
		 tafel = (Tafel) in.readObject();
	 }
	 
	 private void readObjectNoData() throws ObjectStreamException {
		 
	 }
	 
	 public boolean isTegelPlaatsBaar(Tegel tegel) {
		 
		 return true;
	 }
}
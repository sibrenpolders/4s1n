package actua;

import java.util.ArrayDeque;

public class TafelVerwerker {
	private static final int AANTAL_TEGELS = 72;
	private ArrayDeque<Tegel> stapel;
	private TegelFabriek tfb;
	private Tafel tafel;

	public TafelVerwerker() {
		tfb = new TegelFabriek();
		stapel = tfb.maakTegelDeque(AANTAL_TEGELS);
		tafel = new Tafel();
	}

	public TafelVerwerker(int aantalTegels) {
		tfb = new TegelFabriek();
		stapel = tfb.maakTegelDeque(aantalTegels);
		tafel = new Tafel();
	}
	
	public void herstelOverzicht() {

	}

	// TODO kan iets anders zijn
	public Tegel vraagNieuweTegel() {
		return stapel.getFirst();
	}

	public Tegel neemTegelVanStapel() {
		return stapel.getFirst();
	}
	
	public void legTerug(Tegel tegel){
		stapel.addLast(tegel);
	}
	
	public void verwerkTegel() {

	}

	public Vector2D vraagCoord() {
		return null;
	}

	public void wijzigOverzicht(Vector3D nieuwePositie) {
		tafel.beweegCamera(nieuwePositie);
	}

	public Vector3D getOverzicht() {
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
	public boolean plaatstPion(Vector2D tegelCoord, int pionCoord, Pion pion) {
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
		tfb = new TegelFabriek();
		
		stapel = tfb.maakTegelDeque(aantal);
	}
	
	public int aantalTegels(){
		return stapel.size();
	}
	
	public Camera getCamera() {
		return tafel.getOogpunt();
	}
	
	public Tegel getLaatstGeplaatsteTegel() {
		return tafel.getLaatstGeplaatsteTegel();
	}
	
	public boolean verplaatsCamera(Vector3D nieuwePositie) {
		if (tafel.cameraBewegingGeldig(nieuwePositie)) {
			tafel.beweegCamera(nieuwePositie);
			return true;
		}
		else
			return false;
	}
	
	public Vector2D getStartTegelPos() {
		return tafel.getStartTegel();
	}
}
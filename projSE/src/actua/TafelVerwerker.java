package actua;

import java.util.Queue;

public class TafelVerwerker {
	private Queue<Tegel> stapel;
	public Tafel tafel;

	public TafelVerwerker() {
		tafel = new Tafel();
	}

	public void herstelOverzicht() {

	}

	public Tegel vraagNieuweTegel() {
		return null;
	}

	public Tegel neemTegelVanStapel() {
		return null;
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

	public boolean isPionPlaatsingGeldig(Pion pion, Vector2D tegelCoord,
			int pionCoord) {
		return tafel.isPionPlaatsingGeldig(pion, tegelCoord, tegelCoord);
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
		
	}
}
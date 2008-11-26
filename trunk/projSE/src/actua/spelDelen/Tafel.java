package actua.spelDelen;
import actua.types.Vector2D;
import actua.types.Vector3D;

public class Tafel {
	private Tegel laatstGeplaatsteTegel;
	private Tegel[][] veld;
	private Camera oogpunt;

	public Tafel() {
		
	}

	public void beweegCamera (Vector3D nieuwePositie) {
		oogpunt.veranderStandpunt(nieuwePositie);
	}

	public boolean plaatsTegel (Tegel tegel, Vector2D coord) {
		return false;
	}

	public boolean plaatsPion (Vector2D coord, Pion pion) {
		return false;
	}

	public boolean isPlaatsingGeldig (Tegel tegel, Landsdeel LD) {
		return false;
	}

	public boolean isLaatste (Tegel tegel) {
		return false;
	}

	public Tegel bepaalTegel (Vector2D coord) {
		return null;
	}

	public boolean isBoer (Pion pion) {
		return false;
	}

	protected void isGebiedGeldig (Pion pion) {
		
	}

	protected boolean neemPionTerug (Pion pion) {
		return false;
	}

	public void herstelOverzicht () {
		
	}

	public boolean valideerTegelPlaatsing (Tegel tegel, Vector2D coord) {
		return false;
	}

	public void undo () {
		
	}

	public void redo () {
		
	}

}

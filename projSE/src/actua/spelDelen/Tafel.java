package actua.spelDelen;
import actua.types.Vector2D;
import actua.types.Vector3D;

public class Tafel {
	protected Tegel laatstGeplaatsteTegel;
	protected Tegel[][] veld;
	protected Camera oogpunt;

	public Tafel() {
		
	}

	protected Vector3D beweegCamera () {
		return null;
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

	protected boolean isBoer (Pion pion) {
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

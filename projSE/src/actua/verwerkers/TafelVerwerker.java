package actua.verwerkers;

import java.util.Queue;

import actua.spelDelen.Tafel;
import actua.spelDelen.Tegel;
import actua.types.Vector2D;
import actua.types.Vector3D;


public class TafelVerwerker {
	private Queue<Tegel> stapel;
	private Tafel tafel;
	
	public TafelVerwerker() {
		tafel = new Tafel();
	}

	public void herstelOverzicht () {
		
	}

	public Tegel vraagNieuweTegel () {
		return null;
	}

	public Tegel neemTegelVanStapel () {
		return null;
	}

	public void verwerkTegel () {
		
	}

	public Vector2D vraagCoord () {
		return null;
	}
	
	public void wijzigOverzicht(Vector3D nieuwePositie) {
			tafel.beweegCamera(nieuwePositie);
	}	
	
	public Vector3D getOverzicht(){
		
	}
}
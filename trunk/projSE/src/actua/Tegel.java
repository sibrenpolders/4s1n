package actua;

/**
 * @author Sam
 * 
 */
public class Tegel {
	private Vector2D orientatie;
	private String soortTegel; // noord-oost-zuid-west

	public Tegel() {

	}
	
	public Tegel(String soortTegel) {
		this.soortTegel=soortTegel;
	}

	public void setOrientatie(Vector2D orientatie) {
		this.orientatie = orientatie;
	}

	public Vector2D getOrientatie() {
		return orientatie;
	}

	public String getSoortTegel() {
		return soortTegel;
	}

	public void setSoortTegel(String soortTegel) {
		this.soortTegel = soortTegel;
	}

	public Landsdeel bepaalLandsdeel(Vector2D coord) {
		return null;
	}

	public void plaatsPion(Landsdeel LD, Pion pion) {

	}

	/**
	 * @param richting = true met wijzerzin 
	 *    			   = false met tegenwijzerzin
	 */
	public void draaiTegel(boolean richting) {
		char windrichting;
		int element;
		
		if(richting){
			for(int i=0;i<4;++i){
				element=0;
			}
		}else{
			
		}
	}
	
	public boolean equals(Tegel t){
		return this.soortTegel.equals(t.soortTegel) && this.orientatie.equals(t.orientatie);
	}
}

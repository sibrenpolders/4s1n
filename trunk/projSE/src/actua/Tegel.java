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
		int element,richt=1;
		String nieuw="";
		
		if(richting)
			richt*=-1;
		
		for(int i=0;i<4;++i){
			element = mod(i+richt,4);
			windrichting = soortTegel.charAt(element);
			nieuw+=windrichting;	
		}
		
		soortTegel=nieuw;
	}
	
	private int mod(int waarde,int mod){
		if(waarde==-1)
			return 3;
		else
			return waarde%mod;
	}
	
	public boolean equals(Tegel t){
		boolean orientatieB, soortTegelB;
		
		if (orientatie != null) {
			orientatieB = orientatie.equals(t.orientatie); 
		} else {
			orientatieB = t.orientatie == null;
		}
	
		if (soortTegel!= null) {
			soortTegelB = soortTegel.equals(t.soortTegel); 
		} else {
			soortTegelB = t.soortTegel== null;
		}
		
		return orientatieB && soortTegelB;
	}
}

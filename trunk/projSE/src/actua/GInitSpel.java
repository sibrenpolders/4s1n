package actua;

public abstract class GInitSpel {
	private Spel spel;
	
	public GInitSpel(Spel spel){
		this.spel=spel;
	}

	protected Spel getSpel() {
		return spel;
	}

	protected void setSpel(Spel spel) {
		this.spel = spel;
	}
	
	public abstract void show();
	public abstract void hide();
	public abstract void begin();
	
	public void voegSpelerToe(String naam,char kleur,int niveau){
		//spel.voegSpelerToe(SpelerFactory(naam,kleur,0,niveau));
	}
	
	public void aantalTegels(int aantal){
		//spel.maakStapelAan(aantal);
	}
}

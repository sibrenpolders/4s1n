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
	
	public void voegSpelerToe(String naam,char kleur,String niveau){
		if(niveau.equals("Mens")){
			spel.voegSpelerToe((short) -1,naam,kleur,0);
		}else if(niveau.equals("Easy")){
			spel.voegSpelerToe((short) 0,naam,kleur,0);
		}else if(niveau.equals("Hard")){
			spel.voegSpelerToe((short) 1,naam,kleur,0);
		}
	}	
}

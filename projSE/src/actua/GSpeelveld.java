package actua;
import java.util.ArrayList;

public abstract class GSpeelveld {
	private static String DEFAULT_BACKGROUND;
	protected ArrayList<GTegel> gTegels;
	private String achtergrond;
	private Spel spel;

	public GSpeelveld() {
		
	}
	
	public GSpeelveld(Spel spel) {
		this.spel=spel;
	}

	public ArrayList<GTegel> getGTegels() {
		return gTegels;
	}

	public void setGTegels(ArrayList<GTegel> tegels) {
		gTegels = tegels;
	}

	public String getAchtergrond() {
		return achtergrond;
	}

	public void setAchtergrond(String achtergrond) {
		this.achtergrond = achtergrond;
	}

	public void toonSpeelveld () {
		
	}

	public void updateSpeelveld () {
		
	}
	
	public void voegTegelToe() {
		
	}

	public abstract void hide();
	public abstract void show();
}
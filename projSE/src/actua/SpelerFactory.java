package actua;

public class SpelerFactory {
	public static char MENS = 'm';
	public static char AI = 'v';

	public static Speler maakSpeler (char type, String naam, char kleur, long score, short niveau) {
		if(type == MENS)
			return new Mens(naam, kleur, score);
		else if(type == AI)
			return new AI(naam, kleur, score, niveau);
		else return null;
	}

}
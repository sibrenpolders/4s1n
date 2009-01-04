package actua;

public class SpelerFactory {

	public static Speler maakSpeler(String naam, char kleur, long score, short niveau) {
		if (niveau == -1)
			return new Speler(naam, kleur, score);
		else
			return new AI(naam, kleur, niveau, score);
	}
}
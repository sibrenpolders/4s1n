package testcases;

import junit.framework.TestCase;
import actua.Easy;
import actua.Spel;
import actua.Strategy;
import actua.Tafel;
import actua.Tegel;
import actua.AI;
import actua.Pion;
import actua.Speler;
import actua.Vector2D;

public class JunitAISpelerTest extends TestCase {
	Spel spel;
	Strategy ai;

	protected void setUp() throws Exception {
		spel = new Spel();
		spel.voegSpelerToe(AI.EASY, "test",Spel.GEEL, 0);
	}

	// moet nog een unit test komen van doeZet() maar ik heb geen idee hoe die functie werkt
}

package UnitTests;

import junit.framework.TestCase;
import Core.Spel;
import Spelers.AI;
import Spelers.Strategy;

public class JunitAISpelerTest extends TestCase {
	Spel spel;
	Strategy ai;

	protected void setUp() throws Exception {
		spel = new Spel();
		spel.voegSpelerToe(AI.EASY, "test",Spel.GEEL, 0);
	}

	// moet nog een unit test komen van doeZet() maar ik heb geen idee hoe die functie werkt
}

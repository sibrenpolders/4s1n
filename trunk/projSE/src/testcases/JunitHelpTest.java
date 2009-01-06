package testcases;

import actua.Help;
import junit.framework.TestCase;

public class JunitHelpTest extends TestCase {

	public void testGeefMogelijkeResultaten() {
		Help h = new Help("JUnit_Hulpbestanden/JunitHelp.xml");
		String[][] result = h.geefMogelijkeResultaten("spel", 11);
		assertEquals(2, result.length);
		assertEquals("1", result[0][0]);
		assertEquals("2", result[1][0]);
		assertEquals("Dit is heel", result[0][1]);
		assertEquals("Dit is zeer", result[1][1]);

		result = h.geefMogelijkeResultaten("speler", 11);
		assertEquals(3, result.length);
		assertEquals("3", result[2][0]);
		assertEquals("Dit is enor", result[2][1]);
		assertEquals("Dit is zeer", result[1][1]);
	}

	public void testGeefVolledigResultaat() {
		Help h = new Help("JUnit_Hulpbestanden/JunitHelp.xml");
		String result = h.geefVolledigResultaat("1");
		assertEquals(
				"Dit is heel tof allemaal. Ja, je kent er dus niks van. Ik moet hier 100 tekens gaan neerzetten, om 't allemaal te testen. Hoeveel zijn dit er al ?\n		Ff checken... hmm, nog ff verdertypen blijkbaar. Nog ff... en... ja... we hebben er nu... 100 ! Meer dan 100 zelfs.",
				result);
		assertEquals(h.geefVolledigResultaat("4"), "");
	}

}

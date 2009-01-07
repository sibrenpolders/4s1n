package UnitTests;

import Opties.Optie;
import Opties.OptieVerwerker;
import junit.framework.TestCase;

public class JunitOptiesTest extends TestCase {

	public void testOptieVerwerker() {
		OptieVerwerker op = new OptieVerwerker();
		assertNotNull(op.getOptieBestand());
		assertEquals(OptieVerwerker.getDefaultOptieBestand(), op
				.getOptieBestand());
	}

	public void testOptieVerwerkerString() {
		OptieVerwerker op = new OptieVerwerker(
				"JUnit_Hulpbestanden/JunitOpties.txt");
		assertEquals("JUnit_Hulpbestanden/JunitOpties.txt", op
				.getOptieBestand());
		assertNull(op.getWaarde("random"));
		op = new OptieVerwerker(null);
		assertEquals(OptieVerwerker.getDefaultOptieBestand(), op
				.getOptieBestand());
	}

	public void testGetWaardeString() throws Exception {
		OptieVerwerker op = new OptieVerwerker("empty");
		op.addOptie("e1", Optie.TYPE.BOOL, "false");
		op.addOptie("e2", Optie.TYPE.BOOL, "FAlSe");
		op.addOptie("e3", Optie.TYPE.BOOL, "INVALID");
		op.addOptie("e4", Optie.TYPE.NUM, "INVALID");

		assertEquals("false", op.getWaarde("e1"));
		assertEquals(null, op.getWaarde("e2"));
		assertNull(op.getWaarde("e3"));
		assertNull(op.getWaarde("e4"));

		op.addOptie("e4", Optie.TYPE.NUM, "12");
		assertEquals("12", op.getWaarde("e4"));

		op.addOptie(null, Optie.TYPE.BOOL, "false");
		assertEquals(null, op.getWaarde(null));
		op.addOptie("bla", null);
		assertEquals(null, op.getWaarde("bla"));
	}

	public void testGetNaam() throws Exception {
		OptieVerwerker op = new OptieVerwerker("empty");
		op.addOptie(null, Optie.TYPE.NUM, "test");
		assertNull(op.getWaarde(null));
		op.addOptie("bla", null);
		assertEquals(null, op.getNaam(0));
		op.addOptie("bla", Optie.TYPE.BOOL, "false");
		assertEquals("bla", op.getNaam(0));
	}

	public void testGetWaardeInt() throws Exception {
		OptieVerwerker op = new OptieVerwerker("empty");
		op.addOptie("e1", Optie.TYPE.BOOL, "false");
		op.addOptie("e2", Optie.TYPE.BOOL, "FAlSe");
		assertEquals("false", op.getWaarde(0));
		assertEquals(null, op.getWaarde(1));
	}

	public void testVeranderOptie() throws Exception {
		OptieVerwerker op = new OptieVerwerker("empty");
		op.addOptie("e1", Optie.TYPE.BOOL, "false");
		op.addOptie("e2", Optie.TYPE.BOOL, "FAlSe");
		op.addOptie("e3", Optie.TYPE.TEXT, "hg");
		op.addOptie("e4", Optie.TYPE.TEXT, "gh");
		op.addOptie("e5", Optie.TYPE.NUM, "11");
		op.addOptie("e6", Optie.TYPE.NUM, "234");
		op.veranderOptie(new String[] { "e1", "true" });
		assertEquals("true", op.getWaarde("e1"));
		op.veranderOptie(new String[] { "e5", "45" });
		assertEquals("45", op.getWaarde("e5"));
		op.veranderOptie(new String[] { "e4", "4g" });
		assertEquals("4g", op.getWaarde("e4"));
	}

	public void testSchrijfNaarBestand() throws Exception {
		OptieVerwerker op = new OptieVerwerker(
				"JUnit_Hulpbestanden/JunitOpties.txt");
		op.addOptie("e1", Optie.TYPE.BOOL, "false");
		op.addOptie("e2", Optie.TYPE.BOOL, "FAlSe");
		op.addOptie("e3", Optie.TYPE.TEXT, "hg");
		op.addOptie("e4", Optie.TYPE.TEXT, "gh");
		op.addOptie("e5", Optie.TYPE.NUM, "11");
		op.addOptie("e6", Optie.TYPE.NUM, "234");
		op.veranderOptie(new String[] { "e1", "true" });
		assertEquals("true", op.getWaarde("e1"));
		op.veranderOptie(new String[] { "e5", "45" });
		assertEquals("45", op.getWaarde("e5"));
		op.veranderOptie(new String[] { "e4", "4g" });
		assertEquals("4g", op.getWaarde("e4"));
		op.schrijfNaarBestand();

		op = new OptieVerwerker("JUnit_Hulpbestanden/JunitOpties.txt");
		assertEquals("true", op.getWaarde("e1"));
		assertEquals("45", op.getWaarde("e5"));
		assertEquals("4g", op.getWaarde("e4"));
		assertEquals(null, op.getWaarde("e2"));
		assertEquals("234", op.getWaarde("e6"));
		assertNull(op.getWaarde(10));
	}
}

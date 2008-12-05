package testcases;

import actua.OptieVerwerker;
import junit.framework.TestCase;

public class JunitOptiesTest extends TestCase {
	OptieVerwerker op;

	public JunitOptiesTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		op = new OptieVerwerker();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOptieVerwerker() {
		assertNotNull(op.getOptieBestand());
		assertEquals(OptieVerwerker.getDefaultOptieBestand(), op
				.getOptieBestand());
	}

	public void testOptieVerwerkerString() {
		op = new OptieVerwerker("testtest");
		assertEquals("testtest", op.getOptieBestand());
		assertNull(op.getWaarde("random"));
		op = new OptieVerwerker(null);
		assertEquals(OptieVerwerker.getDefaultOptieBestand(), op
				.getOptieBestand());
	}

	public void testGetWaardeString() throws Exception {
		op.addOptie("back", "test");
		op.addOptie("back2", "test");
		assertEquals("test", op.getWaarde("back"));
		assertEquals("test", op.getWaarde("back2"));
		op.addOptie(null, "test");
		assertEquals(null, op.getWaarde(null));
		op.addOptie("bla", null);
		assertEquals(null, op.getWaarde("bla"));
	}

	public void testGetNaam() throws Exception {
		op = new OptieVerwerker("empty");
		op.addOptie(null, "test");
		assertNull(op.getWaarde(null));
		op.addOptie("bla", null);
		assertEquals(null, op.getNaam(0));
		op.addOptie("bla", "test");
		assertEquals("bla", op.getNaam(0));
	}

	public void testGetWaardeInt() throws Exception {
		op = new OptieVerwerker("empty");
		op.addOptie("test", "back");
		op.addOptie("test", "back2");
		assertEquals("back2", op.getWaarde(0));
		assertEquals(null, op.getWaarde(1));
		op.addOptie(null, "test");
		assertEquals(null, op.getWaarde(1));
		op.addOptie("bla", null);
		assertEquals(null, op.getWaarde("bla"));
	}

	public void testVeranderOptie() throws Exception {
		op.addOptie("back", "test");
		op.addOptie("back2", "test");
		op.veranderOptie(new String[] { "back", "test2" });
		assertEquals("test2", op.getWaarde("back"));
		assertEquals("test", op.getWaarde("back2"));
		op.veranderOptie(new String[] { "back", null });
		assertEquals("test2", op.getWaarde("back"));
	}

	public void testSchrijfNaarBestand() throws Exception {
		op.addOptie("back", "test1");
		op.addOptie("back2", "test2");
		op.addOptie("back3", "test3");
		op.addOptie("back4", "test4");
		op.schrijfNaarBestand();

		OptieVerwerker op2 = new OptieVerwerker();
		assertEquals("test2", op2.getWaarde(1));
		assertEquals("test1", op2.getWaarde(0));
		assertEquals("back", op2.getNaam(0));
		assertEquals("back3", op2.getNaam(2));
		assertEquals("test4", op2.getWaarde("back4"));
	}
}

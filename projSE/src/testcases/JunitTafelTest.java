package testcases;

import actua.Pion;
import actua.Tafel;
import actua.Tegel;
import actua.Vector2D;
import actua.Vector3D;
import junit.framework.TestCase;

public class JunitTafelTest extends TestCase {
	Tafel tafel;
	Tegel tegel;
	Vector2D v;

	protected void setUp() throws Exception {
		super.setUp();

		tafel = new Tafel();
		tegel = new Tegel();
	}

	protected void tearDown() throws Exception {
	}

	public void testBeweegCamera() {
		Vector3D v = new Vector3D(3, 3, 3);

		tafel.beweegCamera(v);
		assertEquals(v, tafel.getOogpunt().getHuidigeVector());
	}

	public void testPlaatsTegel() {
		v = new Vector2D(0, 0);
		Tegel t;

		tafel.plaatsTegel(tegel, v);
		t = tafel.bepaalTegel(v);

		assertEquals(tegel, t);
	}

	public void testPlaatsPion() {
		 v = new Vector2D(1, 1);
		 Pion pion=new Pion();
		 
		 // probleem tegel coordinaten
		 tafel.plaatsPion(v, pion);
	}

	public void testIsPlaatsingGeldig() {
		fail("Not yet implemented");
	}

	public void testIsLaatste() {
		v = new Vector2D(0, 0);

		tafel.plaatsTegel(tegel, v);
		assertTrue(tafel.isLaatste(tegel));
	}

	public void testBepaalTegel() {
		fail("Not yet implemented");
	}

	public void testIsGebiedGeldig() {
		fail("Not yet implemented");
	}

	public void testValideerTegelPlaatsing() {
		Vector2D v = new Vector2D(0, 0);
		boolean bool;

		tafel.plaatsTegel(tegel, v);
		bool = tafel.valideerTegelPlaatsing(tegel, v);

		assertFalse(bool);
	}

	public void testUndo() {
		fail("Not yet implemented");
	}

	public void testRedo() {
		fail("Not yet implemented");
	}

}

package testcases;

import actua.Landsdeel;
import actua.Pion;
import actua.Tegel;
import actua.Vector2D;
import junit.framework.TestCase;

public class JunitTegelTest extends TestCase {
	Tegel tegel;

	protected void setUp() throws Exception {
		super.setUp();

		tegel = new Tegel();
	}

	protected void tearDown() throws Exception {
	}

	public void testBepaalLandsdeel() {
		fail("Not yet implemented");
	}

	public void testPlaatsPion() {
		Pion pion = new Pion();
		Vector2D v = new Vector2D(0, 0);
		Landsdeel ld;

		ld = tegel.bepaalLandsdeel(v);
		tegel.plaatsPion(ld, pion);

		assertEquals(v, pion.getPositie());
	}

	public void testDraaiTegel() {
		Tegel t=new Tegel("wccc");
		tegel.setSoortTegel("cccw");
		
		tegel.draaiTegel(true);
		
		assertEquals(t, tegel); // werkt niet zogoed
	}

}

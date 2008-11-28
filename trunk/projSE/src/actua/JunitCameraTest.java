package actua;

import junit.framework.TestCase;

public class JunitCameraTest extends TestCase {
	private Camera camera;
	
	protected void setUp() throws Exception {
		super.setUp();
		Vector3D max = new Vector3D(100, 100, 100);
		Vector3D min = new Vector3D(-100, -100, -100);
		
		camera = new Camera(min, max);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBewegingGeldig() {
		Vector3D v3 = new Vector3D(5, 6, 7);
		assertTrue(camera.bewegingGeldig(v3));
		
		v3 = new Vector3D(2555, 6666, 77777);
		assertFalse(camera.bewegingGeldig(v3));
	}

	public void testVeranderStandpunt() {
		Vector3D v3 = new Vector3D(5,6,7);
		camera.veranderStandpunt(v3);
		Vector3D vTest = camera.getHuidigeVector();
		
		assertEquals("Standpunt juist gewijzigd", v3, vTest);
	}

	public void testHerstelOverzicht() {
		fail("Not yet implemented");
	}

}

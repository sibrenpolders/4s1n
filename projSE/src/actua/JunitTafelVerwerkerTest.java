package actua;

import junit.framework.TestCase;

public class JunitTafelVerwerkerTest extends TestCase {
	private TafelVerwerker tafelverwerker;

	protected void setUp() throws Exception {
		super.setUp();
		tafelverwerker = new TafelVerwerker();
	}
	
	public void herstelOverzicht() {
		Vector3D herstel = new Vector3D(0,0,0);
		
		tafelverwerker.herstelOverzicht();
		assertEquals("Overzicht hersteld", herstel, tafelverwerker.getOverzicht());
	}
	
	public void testwijzigOverzicht() {
		
		Vector3D nieuwoverzicht = new Vector3D(5,6,7);
		
		tafelverwerker.wijzigOverzicht(nieuwoverzicht);
		assertEquals("Overzicht gewijzigd", nieuwoverzicht, tafelverwerker.getOverzicht());
	}
}

package UnitTests;

import Tafel.TafelVerwerker;
import junit.framework.TestCase;

public class JunitTafelVerwerkerTest extends TestCase {
	private TafelVerwerker tafelverwerker;

	protected void setUp() throws Exception {
		super.setUp();
		tafelverwerker = new TafelVerwerker();
	}

}

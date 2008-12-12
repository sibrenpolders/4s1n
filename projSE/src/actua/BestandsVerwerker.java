package actua;

import java.io.File;

public class BestandsVerwerker {

	public BestandsVerwerker() {

	}

	public boolean bestandBestaat(String naam) {
		File f = new File(naam);
		return f.exists();
	}

	public boolean heeftExtensie(String naam, String extensie) {
		return naam.endsWith(extensie);
	}
}
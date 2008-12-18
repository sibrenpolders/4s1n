package actua;

public class Applicatie {
	public Applicatie(GFactory f) {
		if (f == null) {
			System.err.println("No graphical toolkit found.");
			System.exit(1);
		}

		GWindow window = f.getWindow(new Spel(), new OptieVerwerker("src/xml/opties.txt"),
				new HelpVerwerker(),new BestandsVerwerker());
		window.show();
	}

	public static void main(String[] args) {
		String guiType = null;

		if (args.length == 2) {
			guiType = args[0];
		}

		new Applicatie(createGUIFactory(guiType));
	}

	private static GFactory createGUIFactory(String guiType) {
		if (guiType == null || guiType.equals("qt")) {
			return new QTFactory();
		} else {
			System.err.println(guiType
					+ " as a graphical toolkit is NOT supported.");
			return null;
		}
	}
}

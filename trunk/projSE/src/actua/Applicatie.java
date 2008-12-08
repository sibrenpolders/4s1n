package actua;

public class Applicatie {
	// TODO
	// Hebben deze globale variabelen ook maar enige nut??
	private GFactory gFactory;
	private Spel spel;
	private HelpVerwerker help;
	private OptieVerwerker opties;

	public Applicatie(GFactory f) {
		if (f == null) {
			System.err.println("No graphical toolkit found.");
			System.exit(1);
		}
		// TODO niet nodig??
//		setGFactory(f);
//		setHelp(new HelpVerwerker());
//		setOpties(new OptieVerwerker());
//		setSpel(new Spel());

//		gFactory.showGWindow(getSpel(), getOpties(), getHelp());
		
		GWindow window = f.getWindow(new Spel(), new OptieVerwerker(), new HelpVerwerker());
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

	private final Spel getSpel() {
		return spel;
	}

	private final void setSpel(Spel spel) {
		this.spel = spel;
	}

	private final HelpVerwerker getHelp() {
		return help;
	}

	private final void setHelp(HelpVerwerker help) {
		this.help = help;
	}

	private final OptieVerwerker getOpties() {
		return opties;
	}

	private final void setOpties(OptieVerwerker opties) {
		this.opties = opties;
	}

	private final GFactory getGFactory() {
		return gFactory;
	}

	private final void setGFactory(GFactory factory) {
		gFactory = factory;
	}
}

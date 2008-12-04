package actua;

public class Applicatie {
	private GFactory gFactory;
	private Spel spel;
	private HelpVerwerker help;
	private OptieVerwerker opties;

	public Applicatie(GFactory f) {
		setGFactory(f);
		setHelp(new HelpVerwerker());
		setOpties(new OptieVerwerker());
		setSpel(new Spel());

		f.showGWindow(getSpel(), getOpties(), getHelp());
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

	public static void main(String[] args) {
		Applicatie app;
		GFactory gui;

		if (args.length < 2 || args[1].compareTo("qt") == 0) {
			gui = new QTFactory();
			app = new Applicatie(gui);
		} else
			System.out.println(args[1]
					+ " as a graphical toolkit is NOT supported.");
	}
}

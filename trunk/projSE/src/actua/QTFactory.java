package actua;

public class QTFactory implements GFactory {
//	private QTWindow window;

	public QTFactory() {
		super();
	}

//	private final QTWindow getWindow() {
//		return window;
//	}
//
//	private final void setWindow(QTWindow window) {
//		this.window = window;
//	}
// TODO Dit is niet de taak van een Factory!!! Factory != manipulator
//	public GWindow showGWindow(Spel spel, OptieVerwerker opties,
//			HelpVerwerker help) {
//		window = new QTWindow(spel, opties, help);
//		window.show();
//		return window;
//	}

	public GWindow getWindow(Spel spel, OptieVerwerker opties,
			HelpVerwerker help) {
		return new QTWindow(spel, opties, help);
	}
}

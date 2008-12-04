package actua;

public class QTFactory implements GFactory {
	private QTWindow window;

	public QTFactory() {
		super();
	}

	private final QTWindow getWindow() {
		return window;
	}

	private final void setWindow(QTWindow window) {
		this.window = window;
	}

	public GWindow showGWindow(Spel spel, OptieVerwerker opties,
			HelpVerwerker help) {
		window = new QTWindow(spel, opties, help);
		window.show();
		return window;
	}
}

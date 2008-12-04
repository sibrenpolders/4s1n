package actua;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;

public class QTWindow extends GWindow {
	private QMainWindow mainWindow;

	public QTWindow(Spel spel, OptieVerwerker opties, HelpVerwerker help) {
		super(spel, opties, help);
		QApplication.initialize(new String[0]);
		setMainWindow(new QMainWindow());
		mainWindow.setWindowTitle("Actua Tungrorum");
		setInfo(new QTInfo());
		setMenubalk(new QTMenubalk(mainWindow));
		setSpeelveld(new QTSpeelveld());
	}

	public void show() {
		mainWindow.show();
		QApplication.exec();
	}

	private final QMainWindow getMainWindow() {
		return mainWindow;
	}

	private final void setMainWindow(QMainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
}
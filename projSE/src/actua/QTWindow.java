package actua;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QWidget;

public class QTWindow extends GWindow {
	private QMainWindow mainWindow;

	public QTWindow(Spel spel, OptieVerwerker opties, HelpVerwerker help,
			BestandsVerwerker bestand) {
		super();
		QApplication.initialize(new String[0]);
		mainWindow = new QMainWindow();
		mainWindow.setWindowTitle("Actua Tungrorum");
		initMainWindow(spel, opties, help, bestand);
	}

	private void initMainWindow(Spel spel, OptieVerwerker opties,
			HelpVerwerker help, BestandsVerwerker bestand) {
		QTInitSpel qtInitSpel = new QTInitSpel(spel);
		QTInfo qInfo = new QTInfo(spel, opties);

		QTMenubalk qMenubalk = new QTMenubalk(spel, opties, help, bestand,
				qtInitSpel);
		mainWindow.setMenuBar(qMenubalk.getMenubar());

		QWidget container = new QWidget(mainWindow);
		QHBoxLayout hbox = new QHBoxLayout();

		QTSpeelveld qSpeelveld = new QTSpeelveld(spel, opties);
		hbox.addWidget(qSpeelveld.getGridWidget());
		hbox.addWidget(qInfo.getQtInfo());

		qtInitSpel.addObserver(qInfo);
		qtInitSpel.addObserver(qSpeelveld);

		container.setLayout(hbox);
		container.show();

		mainWindow.setCentralWidget(container);
	}

	public void show() {
		mainWindow.show();
		QApplication.exec();
	}
}
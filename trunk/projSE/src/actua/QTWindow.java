package actua;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QWidget;

public class QTWindow implements GWindow {
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
		QTMenubalk qMenubalk = new QTMenubalk(spel, opties, help, bestand);
		mainWindow.setMenuBar(qMenubalk.getMenubar());

		QWidget container = new QWidget(mainWindow);
		QHBoxLayout hbox = new QHBoxLayout();

		QTSpeelveld qSpeelveld = new QTSpeelveld(spel, opties);
		hbox.addWidget(qSpeelveld.getGridWidget());

		QTInfo qInfo = new QTInfo(spel, opties);
		hbox.addWidget(qInfo.getQtInfo());

		container.setLayout(hbox);
		container.show();

		mainWindow.setCentralWidget(container);
	}

	public void show() {
		mainWindow.show();
		QApplication.exec();
	}
}
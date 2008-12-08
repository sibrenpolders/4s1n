package actua;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QWidget;

public class QTWindow implements GWindow {
	private QMainWindow mainWindow;

	public QTWindow(Spel spel, OptieVerwerker opties, HelpVerwerker help) {
		super();		
		QApplication.initialize(new String[0]);
		setMainWindow(new QMainWindow());
		mainWindow.setWindowTitle("Actua Tungrorum");
		initMainWindow(spel, opties, help);
	}

	private void initMainWindow(Spel spel, OptieVerwerker opties, HelpVerwerker help) {
		QTMenubalk qMenubalk = new QTMenubalk(spel, opties, help);
		mainWindow.setMenuBar(qMenubalk.getMenubar());
		
		QWidget container = new QWidget(mainWindow);
		QHBoxLayout hbox = new QHBoxLayout();
		
		QTSpeelveld qSpeelveld = new QTSpeelveld(spel, opties);		
		hbox.addWidget(qSpeelveld.getSpeelveld());
		
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

	private final QMainWindow getMainWindow() {
		return mainWindow;
	}

	private final void setMainWindow(QMainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}
}
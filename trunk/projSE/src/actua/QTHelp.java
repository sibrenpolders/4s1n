package actua;

import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QWidget;

public class QTHelp extends GHelp {
	private QMainWindow help;
	private QTextBrowser veld;
	private QLineEdit zoekveld;

	public QTHelp(HelpVerwerker helpVerwerker) {
		super(helpVerwerker);
		help = new QMainWindow();
		veld = new QTextBrowser(help);
		zoekveld = new QLineEdit("Typ hier ...", help);

		help.setWindowTitle("Help");
		venster();
	}

	private void venster() {
		QWidget venster = new QWidget();
		QGridLayout layout = new QGridLayout(venster);
		QPushButton zoek = new QPushButton("Zoek");
	

		layout.addWidget(zoekveld,0,0);
		layout.addWidget(zoek,0,1);
		layout.addWidget(veld, 1, 0, 5, 2);
		
		zoek.clicked.connect(this, "update()");

		venster.setLayout(layout);
		help.setCentralWidget(venster);
	}

	@Override
	protected void geefInfoWeer(String[][] zoektermen) {
		int size=zoektermen.length;
		veld.append(size+"");
	}

	@Override
	public void hide() {
		help.hide();
	}

	@Override
	public void show() {
		help.show();

	}

	@Override
	protected String vraagZoekterm() {
		String zoekterm = zoekveld.text();
		
		return zoekterm;
	}

}

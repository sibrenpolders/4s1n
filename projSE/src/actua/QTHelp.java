package actua;

import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QWidget;

public class QTHelp extends GHelp {
	private QMainWindow help;
	private QTextBrowser veld, detailveld;
	private QLineEdit zoekveld, IDveld;

	public QTHelp(HelpVerwerker helpVerwerker) {
		super(helpVerwerker);
		help = new QMainWindow();
		veld = new QTextBrowser(help);
		detailveld = new QTextBrowser(help);
		zoekveld = new QLineEdit("Typ hier ...", help);
		IDveld = new QLineEdit("ID", help);

		help.setWindowTitle("Help");
		init();
	}

	private void init() {
		QWidget venster = new QWidget();
		QGridLayout layout = new QGridLayout(venster);
		QPushButton zoek = new QPushButton("Zoek");
		QPushButton detail = new QPushButton("Detail");

		layout.addWidget(zoekveld, 0, 0);
		layout.addWidget(zoek, 0, 1);
		layout.addWidget(IDveld, 0, 2);
		layout.addWidget(detail, 0, 3);
		layout.addWidget(veld, 1, 0, 5, 4);
		layout.addWidget(detailveld, 4, 0, 5, 4);

		zoek.clicked.connect(this, "updateResults()");
		detail.clicked.connect(this, "updateDetailResult()");

		venster.setLayout(layout);
		help.setCentralWidget(venster);
	}

	protected String vraagZoekterm() {
		String zoekterm = zoekveld.text();

		return zoekterm;
	}

	protected String vraagId() {
		String zoekterm = IDveld.text();

		detailveld.clear();

		return zoekterm;
	}

	protected void geefInfoWeer(String[][] zoektermen) {
		veld.clear();

		for (int i = 0; i < zoektermen.length; ++i) {
			veld.append("ID:" + zoektermen[i][0]);
			veld.append(zoektermen[i][1]);
			veld.append("\n");
		}
	}

	protected void geefDetailWeer(String info) {
		detailveld.append(info);
	}

	public void hide() {
		help.hide();
	}

	public void show() {
		help.show();

	}

}

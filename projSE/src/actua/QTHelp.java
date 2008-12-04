package actua;

import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QWidget;

public class QTHelp extends GHelp {
	QMainWindow help;

	public QTHelp() {
		super();
		help = new QMainWindow();

		help.setWindowTitle("Help");
		venster();
	}

	private void venster() {
		QWidget venster = new QWidget();
		QGridLayout layout = new QGridLayout(venster);
		QLineEdit zoekveld = new QLineEdit("Typ hier ...", help);
		QPushButton zoek=new QPushButton("Zoek");
		QTextBrowser veld=new QTextBrowser(help);

		layout.addWidget(zoekveld,0,0);
		layout.addWidget(zoek,0,1);
		layout.addWidget(veld, 1, 0, 5, 2);

		venster.setLayout(layout);
		help.setCentralWidget(venster);
	}

	@Override
	protected void geefInfoWeer(String[][] zoektermen) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return null;
	}

}

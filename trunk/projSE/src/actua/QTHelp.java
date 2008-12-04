package actua;

import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QTextEdit;
import com.trolltech.qt.gui.QWidget;

public class QTHelp extends GHelp {
	QMainWindow help;

	public QTHelp() {
		super();
		help=new QMainWindow();
		
		help.setWindowTitle("Help");
		zoekVeld();
	}

	private void zoekVeld(){
		QTextEdit zoekveld = new QTextEdit("Type hier ...",help);
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

package actua;

import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QWidget;

public class QTOptie extends GOptie {
	QMainWindow venster;

	public QTOptie(OptieVerwerker ov) {
		super(ov);
		QWidget widget = new QWidget(venster);
		venster = new QMainWindow();
		QGridLayout layout = new QGridLayout();
		
		// Geluid
		// Animaties
		// Achtergrond
		
		venster.setCentralWidget(widget);
		venster.setWindowTitle("Opties");
	}

	@Override
	public void geefOptiesWeer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sluitOptiesAf() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		venster.hide();	
	}

	@Override
	public void show() {
		venster.show();
	}
}

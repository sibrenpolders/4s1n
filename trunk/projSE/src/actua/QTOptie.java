package actua;

import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QDoubleSpinBox;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSpinBox;
import com.trolltech.qt.gui.QTextBrowser;
import com.trolltech.qt.gui.QWidget;

public class QTOptie extends GOptie {
	private QMainWindow opties;

	public QTOptie(OptieVerwerker ov) {
		super(ov);
		opties = new QMainWindow();
		opties.setWindowTitle("Opties");
		venster();
	}

	private void venster() {
		int nbOpties = optieVerwerker.getNbOpties();

		QWidget venster = new QWidget();
		QGridLayout layout = new QGridLayout(venster);

		for (int i = 0; i < nbOpties; ++i) {
			maakOptieveld(i, layout);
		}

		venster.setLayout(layout);
		opties.setCentralWidget(venster);
	}

	private void maakOptieveld(int i, QGridLayout layout) {
		String naam = optieVerwerker.getNaam(i);
		Optie.TYPE type = optieVerwerker.getType(i);

		QLabel label = new QLabel();
		label.setText(naam + ":");
		layout.addWidget(label, i, 0);

		if (type == Optie.TYPE.BOOL)
			booleanVeld(i, layout);
		else if (type == Optie.TYPE.NUM)
			numVeld(i, layout);
		else if (type == Optie.TYPE.TEXT)
			textVeld(i, layout);
	}

	private void booleanVeld(int i, QGridLayout layout) {
		QCheckBox check = new QCheckBox();
		check.setChecked(optieVerwerker.getWaarde(i).equals("true"));
		layout.addWidget(check, i , 1);
	}

	private void numVeld(int i, QGridLayout layout) {
		QSpinBox spin = new QSpinBox();
		spin.setValue(Integer.parseInt(optieVerwerker.getWaarde(i)));
		layout.addWidget(spin, i, 1);
	}

	private void textVeld(int i, QGridLayout layout) {

	}

	protected void update() {

	}

	@Override
	public void geefOptiesWeer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sluitOptiesAf() {
		// TODO Auto-generated method stub

	}

	public void hide() {
		opties.hide();
	}

	public void show() {
		opties.show();
	}
}

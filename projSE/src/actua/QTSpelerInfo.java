package actua;

import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class QTSpelerInfo extends GSpelerInfo {
	QWidget spelerInfoveld;
	QLabel punten;

	public QTSpelerInfo(Speler speler, QWidget parent) {
		super(speler);
		spelerInfoveld = new QWidget(parent);
		QLabel naam = new QLabel(speler.getNaam());
		punten = new QLabel("0");
		QLabel kleur = new QLabel();
		QLabel score = new QLabel("Score");
		QGridLayout layout = new QGridLayout(spelerInfoveld);

		kleur.setPixmap(spelerKleur());
		layout.addWidget(kleur, 0, 1);
		kleur.update();
		layout.addWidget(naam, 0, 0);
		layout.addWidget(score, 1, 0);
		layout.addWidget(punten, 1, 1);

		spelerInfoveld.setLayout(layout);
	}

	public QWidget getSpelerInfoveld() {
		return spelerInfoveld;
	}

	public void setSpelerInfoveld(QWidget spelerInfoveld) {
		this.spelerInfoveld = spelerInfoveld;
	}

	@Override
	public void toonSpeler() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSpeler() {
		punten.setNum(getSpeler().getScore());
	}

	private QPixmap spelerKleur() {
		QPixmap pixmap = new QPixmap(32,32); // moet altijd een h en w bij

		switch (getSpeler().getKleur()) {
		default:
			pixmap.fill(new QColor(255, 0, 0));
		}

		return pixmap;
	}

}

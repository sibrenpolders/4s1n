package actua;

import java.util.Observable;

import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWidget;

public class QTSpelerInfo extends GSpelerInfo {
	private QWidget spelerInfoveld;
	private QLabel punten;
	private QLabel naam;
	private QLabel color;
	private QLabel score;
	private QPushButton button;

	public QTSpelerInfo(Spel spel, char kleur, QWidget parent) {
		super(spel, kleur);

		spelerInfoveld = new QWidget(parent);
		naam = new QLabel(spel.geefSpelerNaam(kleur));
		punten = new QLabel();
		color = new QLabel();
		score = new QLabel("Score");
		button = new QPushButton("Neem pion");
		QGridLayout layout = new QGridLayout(spelerInfoveld);

		color.setPixmap(spelerKleur());
		layout.addWidget(color, 0, 0);
		layout.addWidget(naam, 0, 1);
		layout.addWidget(score, 1, 0);
		layout.addWidget(punten, 1, 1);
		layout.addWidget(button, 2, 0, 1, 2);

		updateSpeler();
		spelerInfoveld.setLayout(layout);
	}

	public void updateSpeler() {
		String naam_ = spel.geefSpelerNaam(kleur);
		long score_ = spel.geefSpelerScore(kleur);
		short aantalOngeplaatstePionnen = spel
				.geefAantalOngeplaatstePionnen(kleur);
		naam.setText(naam_);
		punten.setText(String.valueOf(score_));

		if (aantalOngeplaatstePionnen > 0)
			button.setEnabled(true);
		else
			button.setEnabled(false);
	}

	private QPixmap spelerKleur() {
		QPixmap pixmap = new QPixmap(32, 32);

		switch (kleur) {
		case Spel.ROOD:
			pixmap.fill(new QColor(255, 0, 0));
			break;
		case Spel.BLAUW:
			pixmap.fill(new QColor(0, 0, 255));
			break;
		case Spel.GEEL:
			pixmap.fill(new QColor(255, 255, 0));
			break;
		case Spel.WIT:
			pixmap.fill(new QColor(255, 255, 255));
			break;
		case Spel.ORANJE:
			pixmap.fill(new QColor(255, 160, 0));
			break;
		default:
			pixmap.fill(new QColor(236, 233, 216));
			break;
		}

		return pixmap;
	}

	public void update(Observable o, Object arg) {
		if (o.equals(spel) && arg.equals(spel.SPELERVERANDERD)) {
			updateSpeler();
		}
	}
}

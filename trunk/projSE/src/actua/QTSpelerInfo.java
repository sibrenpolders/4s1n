package actua;

import java.util.Observable;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QStyle;
import com.trolltech.qt.gui.QStyleOptionFocusRect;
import com.trolltech.qt.gui.QWidget;

public class QTSpelerInfo extends GSpelerInfo {
	private QWidget spelerInfoveld;
	private QLabel punten;
	private QLabel naam;
	private QLabel color;
	private QLabel score;

	public QTSpelerInfo(Spel spel, char kleur, QWidget parent) {
		super(spel, kleur);

		spelerInfoveld = new QWidget(parent);
		naam = new QLabel(spel.geefSpelerNaam(kleur));
		punten = new QLabel();
		color = new QLabel();
		score = new QLabel("Score:");
		QGridLayout layout = new QGridLayout(spelerInfoveld);

		color.setPixmap(spelerKleur());
		layout.addWidget(color, 0, 0);
		layout.addWidget(naam, 0, 1);
		layout.addWidget(score, 1, 0);
		layout.addWidget(punten, 1, 1);

		updateSpeler();
		spelerInfoveld.setLayout(layout);
	}

	public QWidget getSpelerInfoveld() {
		return spelerInfoveld;
	}

	public void updateSpeler() {
		String naam_ = spel.geefSpelerNaam(kleur);
		long score_ = spel.geefSpelerScore(kleur);
		naam.setText(naam_);
		punten.setNum(score_);
		if (kleur == spel.geefHuidigeSpeler())
			naam.setText(naam_.toUpperCase());
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
		if (o.equals(spel) && arg.equals(Spel.SPELERVERANDERD)) {
			updateSpeler();
		}
	}
}

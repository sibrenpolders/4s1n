package GUI;

import Core.Spel;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QFont;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class QTSpelerInfo extends GSpelerInfo {
	private QWidget spelerInfoveld;
	private QLabel punten;
	private QLabel naam;
	private QLabel color;
	private QLabel score;
	private QGridLayout layout;
	private static final int TEXT_HOOGTE = 15;

	public QTSpelerInfo(Spel spel, char kleur, QWidget parent) {
		super(spel, kleur);
		create(parent);
		resize();
		setFont();
		add();
		addPionnen();
	}

	public QWidget getSpelerInfoveld() {
		return spelerInfoveld;
	}

	private void create(QWidget parent) {
		spelerInfoveld = new QWidget(parent);
		naam = new QLabel(spel.geefSpelerNaam(kleur));
		punten = new QLabel("0");
		color = new QLabel();
		score = new QLabel("Score:");

		layout = new QGridLayout(spelerInfoveld);
		layout.setVerticalSpacing(3);
	}

	private void setSize(QWidget widget, int w, int h) {
		QSize size = new QSize(w, h);
		widget.setMaximumSize(size);
		widget.setMinimumSize(size);
	}

	private void resize() {
		setSize(naam, 150, TEXT_HOOGTE);
		setSize(punten, 40, TEXT_HOOGTE);
		setSize(color, 40, TEXT_HOOGTE);
		setSize(score, 40, TEXT_HOOGTE);
		setSize(spelerInfoveld, 150, 75);
	}

	private void setFont() {
		QFont qFont = new QFont("Arial", 10, QFont.Weight.Normal.value());
		naam.setFont(qFont);
		punten.setFont(qFont);
		color.setPixmap(getPixmapSpelerKleur());
		score.setFont(qFont);
	}

	private void add() {
		layout.addWidget(color, 0, 0);
		layout.addWidget(naam, 0, 1);
		layout.addWidget(score, 1, 0);
		layout.addWidget(punten, 1, 1);

		spelerInfoveld.setLayout(layout);
		updateSpeler();
	}

	private void addPionnen() {
		QHBoxLayout hBox = new QHBoxLayout();
		boolean flag = spel.geefHuidigeSpeler() == this.kleur && !spel.isUitgespeeld();

		for (int i = 0; i < spel.geefAantalOngeplaatstePionnenVanSpeler(kleur); ++i)
			hBox.addWidget(new QTPion(kleur, flag));

		layout.addLayout(hBox, 2, 0, 1, 2);
	}

	private QPixmap getPixmapSpelerKleur() {
		QPixmap pixmap = new QPixmap(40, 10);

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

	public void updateSpeler() {
		String naam_ = spel.geefSpelerNaam(kleur);
		long score_ = spel.geefSpelerScore(kleur);
		naam.setText(naam_);
		punten.setNum(score_);
		if (kleur == spel.geefHuidigeSpeler())
			naam.setText(naam_.toUpperCase());
		addPionnen();
	}
}

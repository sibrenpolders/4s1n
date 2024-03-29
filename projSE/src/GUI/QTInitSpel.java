package GUI;

import java.util.Vector;

import Core.Spel;
import Spelers.AI;

import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSpinBox;
import com.trolltech.qt.gui.QWidget;

public class QTInitSpel extends GInitSpel {
	private QMainWindow venster;
	private Vector<QLineEdit> naam = new Vector<QLineEdit>();
	private Vector<QComboBox> kleur = new Vector<QComboBox>();
	private Vector<QComboBox> soort = new Vector<QComboBox>();
	private QGridLayout layout;
	private QWidget widget;
	private QSpinBox tegels;
	private short nbPlayersVisible = 0;

	public QTInitSpel(Spel spel) {
		super(spel);
	}

	public void hide() {
		venster.hide();
	}

	public void show() {
		naam = new Vector<QLineEdit>();
		kleur = new Vector<QComboBox>();
		soort = new Vector<QComboBox>();
		nbPlayersVisible = 0;

		venster = new QMainWindow();
		widget = new QWidget(venster);
		layout = new QGridLayout(widget);

		QPushButton begin = new QPushButton("Start", widget);
		QPushButton annuleer = new QPushButton("Annuleer", widget);
		QPushButton extraSpeler = new QPushButton("Nog een speler toevoegen",
				widget);
		QPushButton laatsteSpelerVerwijderen = new QPushButton(
				"Laatste speler verwijderen", widget);
		QLabel aantal = new QLabel("Aantal tegels:");
		tegels = new QSpinBox(widget);

		layout.addWidget(extraSpeler, 0, 0);
		layout.addWidget(laatsteSpelerVerwijderen, 0, 1);
		layout.addWidget(begin, 0, 4);
		layout.addWidget(annuleer, 0, 5);
		layout.addWidget(aantal, 1, 0);
		tegels.setValue(72);
		layout.addWidget(tegels, 1, 1);

		annuleer.clicked.connect(this.venster, "close()");
		begin.clicked.connect(this, "begin()");
		extraSpeler.clicked.connect(this, "voegSpelerOptieVeldToe()");
		laatsteSpelerVerwijderen.clicked.connect(this,
				"verwijderLaatsteSpelerOptieVeld()");

		voegSpelerOptieVeldToe(); // nbPlayersVisible is nu 1
		voegSpelerOptieVeldToe();

		venster.setCentralWidget(widget);
		venster.setWindowTitle("Begin een nieuw Spel");
		venster.show();
	}

	public void begin() {
		if (!spel.isAantalSpelersGeldig(getNamenIngevoerdeSpelers())) {
			foutDialoog("Te weinig spelers !");
			return;
		} else if (!spel
				.zijnNamenVoorNieuweSpelersGeldig(getNamenIngevoerdeSpelers())) {
			foutDialoog("Twee of meerdere spelers hebben dezelfde naam !");
			return;
		} else if (!spel
				.zijnKleurenVoorNieuweSpelersGeldig(getKleurenIngevoerdeSpelers())) {
			foutDialoog("Twee of meerdere spelers hebben dezelfde kleur !");
			return;
		}

		spel.restart(Integer.parseInt(tegels.text()));

		for (int i = 0; i < soort.size(); ++i) {
			int niveau = 0;
			if (soort.get(i).currentText().compareTo("Mens") == 0) {
				niveau = -1;
			} else if (soort.get(i).currentText().compareTo("Easy AI") == 0) {
				niveau = AI.EASY;
			} else if (soort.get(i).currentText().compareTo("Hard AI") == 0) {
				niveau = AI.EASY;
			}

			spel.voegSpelerToe((short) niveau, naam.get(i).text(),
					(char) (kleur.get(i).currentText().charAt(0) + 'a' - 'A'),
					0);
		}

		setChanged();
		notifyObservers(true);

		venster.close();
	}

	private Vector<Character> getKleurenIngevoerdeSpelers() {
		Vector<Character> result = new Vector<Character>();

		for (int i = 0; i < kleur.size(); ++i) {
			result.add(new Character((char) (kleur.get(i).currentText().charAt(
					0) + 'a' - 'A')));
		}

		return result;
	}

	private Vector<String> getNamenIngevoerdeSpelers() {
		Vector<String> result = new Vector<String>();

		for (int i = 0; i < naam.size(); ++i) {
			result.add(new String(naam.get(i).text()));
		}

		return result;
	}

	private void foutDialoog(String fout) {
		QMessageBox box = new QMessageBox();
		box.setWindowTitle("Bericht");
		box.setText(fout);
		box.show();
	}

	private void voegSpelerOptieVeldToe() {
		if (nbPlayersVisible >= Spel.MAXAANTALSPELERS) {
			foutDialoog("Maximum aantal spelers is bereikt !");
		} else {
			QLabel labelNaam = new QLabel("Naam:");
			QLabel labelKleur = new QLabel("Kleur:");
			QLabel labelSoort = new QLabel("Type:");
			QPixmap pixmap = new QPixmap(16, 16);
			naam.add(new QLineEdit("Speler" + (++nbPlayersVisible)));
			kleur.add(new QComboBox());
			soort.add(new QComboBox());
			QIcon kleurIcon;

			pixmap.fill(new QColor(255, 0, 0));
			kleurIcon = new QIcon(pixmap);
			kleur.lastElement().addItem(kleurIcon, "Rood");
			// ----------
			pixmap.fill(new QColor(0, 0, 255));
			kleurIcon = new QIcon(pixmap);
			kleur.lastElement().addItem(kleurIcon, "Blauw");
			// ----------
			pixmap.fill(new QColor(255, 255, 0));
			kleurIcon = new QIcon(pixmap);
			kleur.lastElement().addItem(kleurIcon, "Geel");
			// ----------
			pixmap.fill(new QColor(255, 255, 255));
			kleurIcon = new QIcon(pixmap);
			kleur.lastElement().addItem(kleurIcon, "Wit");
			// ----------
			pixmap.fill(new QColor(255, 160, 0));
			kleurIcon = new QIcon(pixmap);
			kleur.lastElement().addItem(kleurIcon, "Oranje");

			soort.lastElement().addItem("Mens");
			if (nbPlayersVisible > 1) {
				soort.lastElement().addItem("Easy AI");
				soort.lastElement().addItem("Hard AI");
			}

			layout.addWidget(labelNaam, nbPlayersVisible + 1, 0);
			layout.addWidget(naam.lastElement(), nbPlayersVisible + 1, 1);
			layout.addWidget(labelKleur, nbPlayersVisible + 1, 2);
			layout.addWidget(kleur.lastElement(), nbPlayersVisible + 1, 3);
			layout.addWidget(labelSoort, nbPlayersVisible + 1, 4);
			layout.addWidget(soort.lastElement(), nbPlayersVisible + 1, 5);
		}
	}

	@SuppressWarnings("unused")
	private void verwijderLaatsteSpelerOptieVeld() {
		if (nbPlayersVisible == Spel.MINAANTALSPELERS) {
			foutDialoog("Minimum aantal spelers is bereikt !");
		} else {
			QLineEdit line = naam.lastElement();
			naam.remove(line);
			QComboBox combo = kleur.lastElement();
			kleur.remove(combo);
			QComboBox combo2 = soort.lastElement();
			soort.remove(combo2);

			layout.removeWidget(line);
			layout.removeWidget(combo);
			layout.removeWidget(combo2);
			line.dispose();
			combo.dispose();
			combo2.dispose();

			QWidget one = layout.itemAtPosition(nbPlayersVisible + 1, 0)
					.widget();
			QWidget two = layout.itemAtPosition(nbPlayersVisible + 1, 2)
					.widget();
			QWidget three = layout.itemAtPosition(nbPlayersVisible + 1, 4)
					.widget();
			layout.removeWidget(one);
			layout.removeWidget(two);
			layout.removeWidget(three);
			one.dispose();
			two.dispose();
			three.dispose();

			nbPlayersVisible--;
		}
	}
}

package actua;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QFileDialog;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPixmap;

public class QTMenubalk extends GMenubalk {
	private QMenuBar menubar;

	public QTMenubalk(Spel spel, OptieVerwerker opties, HelpVerwerker help,
			BestandsVerwerker bestand, QTInitSpel qtInitSpel) {
		super(spel, opties, help, bestand, qtInitSpel, new QTHelp(help),
				new QTOptie(opties));

		menubar = new QMenuBar();
		menubar.setMaximumWidth(1024);
		menubar.setMinimumWidth(1024);
		addItems();
	}

	public QMenuBar getMenubar() {
		return menubar;
	}

	public void setMenubar(QMenuBar menubar) {
		this.menubar = menubar;
	}

	private QMenu addMenuItem(String titel) {
		return menubar.addMenu("&" + titel);
	}

	private QAction addActionItem(QMenu menu, String titel) {
		return menu.addAction(new QIcon(new QPixmap("src/icons/" + titel
				+ ".png")), titel);
	}

	private void addItems() {
		if (menubar == null)
			throw new NullPointerException();
		createSpelMenu();
		createSpelerMenu();
		createBewerkenMenu();
		createOptiesMenu();
		createHelpMenu();
	}

	private void createSpelMenu() {
		QMenu spel = addMenuItem("Spel");
		QAction nSpel = addActionItem(spel, "Nieuw spel");

		spel.addSeparator();

		QAction opslaan = addActionItem(spel, "Opslaan");
		QAction opslaanAls = addActionItem(spel, "Opslaan als");

		QAction laden = addActionItem(spel, "Laden");

		spel.addSeparator();
		QAction afsluiten = addActionItem(spel, "Afsluiten");

		nSpel.triggered.connect(this.gInitSpel, "show()");
		opslaan.triggered.connect(this, "opslaan()");
		opslaanAls.triggered.connect(this, "opslaanAls()");
		laden.triggered.connect(this, "laden()");
		afsluiten.triggered.connect(QApplication.instance(), "quit()");
	}

	private void createSpelerMenu() {
		QMenu spel = addMenuItem("Spelers");
		addActionItem(spel, "Huidige speler verwijderen");
		addActionItem(spel, "Huidige speler naar AI omzetten");
	}

	private void createBewerkenMenu() {
		QMenu bewerken;
		QAction undo, redo;

		bewerken = addMenuItem("Bewerken");
		undo = addActionItem(bewerken, "Ongedaan maken");
		redo = addActionItem(bewerken, "Opnieuw uitvoeren");

		undo.triggered.connect(spel, "undo()");
		redo.triggered.connect(spel, "redo()");
	}

	private void createOptiesMenu() {
		QMenu opties;
		QAction optie;

		opties = addMenuItem("Opties");
		optie = addActionItem(opties, "Instellingen");
		optie.triggered.connect(this.gOptie, "show()");
	}

	private void createHelpMenu() {
		QMenu helpMenu;
		QAction help, about;

		helpMenu = addMenuItem("Help");
		help = addActionItem(helpMenu, "Help en ondersteuning");
		about = addActionItem(helpMenu, "Info");
		help.triggered.connect(this.gHelp, "show()");
		about.triggered.connect(this, "infoVenster()");
	}

	public void hide() {
		menubar.hide();
	}

	public void show() {
		menubar.show();
	}

	@SuppressWarnings("unused")
	private void infoVenster() {
		QMessageBox.about(menubar, "Over applicatie",
				"<b>Actua Tungrorum</b> is mogelijk gemaakt door "
						+ "Sam, Sam, Sibrand en Sibren");
	}

	@SuppressWarnings("unused")
	private void opslaan() {
		if (bestand.getNaam() != null) {
			bestand.slaSpelToestandOp(spel);
		} else {
			opslaanAls();
		}

	}

	@SuppressWarnings("unused")
	private void opslaanAls() {
		String naam = QFileDialog.getSaveFileName();
		bestand.slaSpelToestandOp(naam, spel);
	}

	@SuppressWarnings("unused")
	private void laden() {
		String naam = QFileDialog.getOpenFileName();
		bestand.startLaden(spel, naam);
	}
}

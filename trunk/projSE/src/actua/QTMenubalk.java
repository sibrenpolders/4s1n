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

	public QTMenubalk(Spel spel, OptieVerwerker opties, HelpVerwerker help,BestandsVerwerker bestand, QTInitSpel qtInitSpel) {
		super(spel, qtInitSpel);
		setGHelp(new QTHelp(help));
		setGOptie(new QTOptie(opties));
		setBestand(bestand);
		
		menubar = new QMenuBar();
		menubar.setMaximumWidth(1024);
		menubar.setMinimumWidth(1024);
		addItems();
	}

	private void addItems() {
		if (menubar == null)
			throw new NullPointerException();
		spel();
		speler();
		bewerken();
		opties();
		help();
	}

	private void spel() {
		QMenu spel = addMenuItem("Spel");
		QAction nSpel = addActionItem(spel, "Nieuw Spel");
		
		spel.addSeparator();
		
		QAction opslaan = addActionItem(spel, "Opslaan");
		QAction opslaanAls = addActionItem(spel, "Opslaan Als");
		
		QAction laden = addActionItem(spel, "Laden");
		
		spel.addSeparator();
		QAction afsluiten = addActionItem(spel, "Afsluiten");
		
		nSpel.triggered.connect(this.gInitSpel, "show()");
		opslaan.triggered.connect(this, "opslaan()");
		opslaanAls.triggered.connect(this, "opslaanAls()");
		laden.triggered.connect(this, "laden()");
		afsluiten.triggered.connect(QApplication.instance(), "quit()");
	}
	
	private void speler() {
		QMenu spel;
		QAction nSpel, opslaan, laden;

		spel = addMenuItem("Huidige Speler");
		nSpel = addActionItem(spel, "Verwijderen");
		opslaan = addActionItem(spel, "Naar AI omzetten");
	}

	private void bewerken() {
		QMenu bewerken;
		QAction undo, redo;

		bewerken = addMenuItem("Bewerken");
		undo = addActionItem(bewerken, "Ongedaan maken");
		redo = addActionItem(bewerken, "Opnieuw uitvoeren");
		
		undo.triggered.connect(spel, "undo()");
		redo.triggered.connect(spel, "redo()");
	}

	private void opties() {
		QMenu opties;
		QAction optie;

		opties = addMenuItem("Opties");
		optie = addActionItem(opties, "Instellingen");
		optie.triggered.connect(this.gOptie, "show()");
	}

	private void help() {
		QMenu helpMenu;
		QAction help, about;

		helpMenu = addMenuItem("Help");
		help = addActionItem(helpMenu, "Help en Ondersteuning");
		about = addActionItem(helpMenu, "Info");
		help.triggered.connect(this.gHelp, "show()");
		about.triggered.connect(this, "infoVenster()");
	}

	@SuppressWarnings("unused")
	private void infoVenster() {
		QMessageBox.about(menubar,"Over Applicatie","Deze <b>Applicatie</b> is mogelijk gemaakt door "
								+ "Niels, Sam, Sam, Sibrand, Sibren en Bart Peeters ");
	}

	private QMenu addMenuItem(String titel) {
		return menubar.addMenu("&" + titel);
	}

	private QAction addActionItem(QMenu menu, String titel) {
		return menu.addAction(new QIcon(new QPixmap("src/icons/" + titel + ".png")), 
				titel);
	}

	public void hide() {
		menubar.hide();
	}

	public void show() {
		menubar.show();
	}

	public QMenuBar getMenubar() {
		return menubar;
	}

	public void setMenubar(QMenuBar menubar) {
		this.menubar = menubar;
	}
	
	private void opslaanAls() {
		String naam = QFileDialog.getSaveFileName();
		bestand.slaSpelToestandOp(naam, spel);
	}
	
	private void opslaan() {
		if (bestand.getNaam() != null) {
			bestand.slaSpelToestandOp(spel);
		} else {
			opslaanAls();
		}
		
	}
	private void laden() {
		String naam = QFileDialog.getOpenFileName();
		bestand.startLaden(spel, naam);
	}
}

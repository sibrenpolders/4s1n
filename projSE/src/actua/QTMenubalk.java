package actua;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QMessageBox;

public class QTMenubalk extends GMenubalk {
	private QMenuBar menubar;

	public QTMenubalk(Spel spel, OptieVerwerker opties, HelpVerwerker help) {
		super();
		setGHelp(new QTHelp(help));
		setGOptie(new QTOptie(opties));
		setGInitSpel(new QTInitSpel(spel));

		menubar = new QMenuBar();
		menubar.setMaximumWidth(1024);
		menubar.setMinimumWidth(1024);
		addItems();
	}

	private void addItems() {
		if (menubar == null)
			throw new NullPointerException();
		spel();
		bewerken();
		opties();
		help();
	}

	private void spel() {
		QMenu spel;
		QAction nSpel, opslaan, laden;

		spel = addMenuItem("Spel");
		nSpel = addActionItem(spel, "Nieuw Spel");
		opslaan = addActionItem(spel, "Opslaan");
		laden = addActionItem(spel, "Laden");

		nSpel.triggered.connect(this.gInitSpel, "show()");
	}

	private void bewerken() {
		QMenu bewerken;
		QAction undo, redo;

		bewerken = addMenuItem("Bewerken");
		undo = addActionItem(bewerken, "Ongedaan maken");
		redo = addActionItem(bewerken, "Opnieuw uitvoeren");
	}

	private void opties() {
		QMenu opties;
		QAction optie;

		opties = addMenuItem("Opties");
		optie = addActionItem(opties, "Instellingen");
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

	private void infoVenster() {
		QMessageBox.about(menubar,"Over Applicatie","Deze <b>Applicatie</b> is mogelijk gemaakt door "
								+ "Niels, Sam, Sam, <s>Mr. Sibrand</s>, Sibren en Bart Peeters ");
	}

	private QMenu addMenuItem(String titel) {
		return menubar.addMenu("&" + titel);
	}

	private QAction addActionItem(QMenu menu, String titel) {
		return menu.addAction(titel);
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
}

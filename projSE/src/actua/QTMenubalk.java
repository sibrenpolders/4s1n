package actua;

import com.trolltech.qt.gui.QAction;
import com.trolltech.qt.gui.QMenu;
import com.trolltech.qt.gui.QMenuBar;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QWidget;

public class QTMenubalk extends GMenubalk {
	private QMenuBar menubar;

	public QTMenubalk(QWidget parent) {
		super();
		setGHelp(new QTHelp());
		setGOptie(new QTOptie());
		menubar = new QMenuBar(parent);
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
	
	private void spel(){
		QMenu spel;
		QAction nSpel, opslaan,laden;

		spel = addMenuItem("Spel");
		nSpel = addActionItem(spel, "Nieuw Spel");
		opslaan = addActionItem(spel, "Opslaan");
		laden = addActionItem(spel, "Laden");
	}
	
	private void bewerken(){
		QMenu bewerken;
		QAction undo,redo;
		
		bewerken = addMenuItem("Bewerken");
		undo = addActionItem(bewerken, "Ongedaan maken");
		redo = addActionItem(bewerken, "Opnieuw uitvoeren");
	}
	
	private void opties(){
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
		help.triggered.connect(this.gHelp,"show()");
		about.triggered.connect(this,"infoVenster()");
	}
	
	private void infoVenster(){
		QMessageBox.about(menubar,"About Application",
				"The <b>Application</b> example demonstrates how to " +
				"write modern GUI applications using Qt, with a menu bar, " +
                "toolbars, and a status bar.");
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
}

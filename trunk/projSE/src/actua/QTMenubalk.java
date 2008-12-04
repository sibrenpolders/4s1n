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
		addItems();
	}

	private void addItems() {
		if (menubar == null)
			throw new NullPointerException();
		help();
		menubar.addMenu("Opties");
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

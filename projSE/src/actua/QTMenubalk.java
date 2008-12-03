package actua;
import com.trolltech.qt.gui.QMenuBar;
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
	
	private void addItems()
	{
		if(menubar == null)
			throw new NullPointerException();
		menubar.addMenu("Help");
		menubar.addMenu("Opties");
	}

	public void hide() {
		menubar.hide();		
	}

	public void show() {
		menubar.show();
	}
}

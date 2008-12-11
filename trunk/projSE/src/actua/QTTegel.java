package actua;

import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPixmap;

public class QTTegel extends GTegel {
	private QPixmap pixmap;

	public QTTegel(Tegel tegel) {
		super(tegel);
		pixmap = new QPixmap();
	}

	public QPixmap getPixmap() {
		return pixmap;
	}

	public void setPixmap(QPixmap pixmap) {
		this.pixmap = pixmap;
	}

	private void laadAfbeeldingIn(String bestandsnaam) {
		pixmap.load(bestandsnaam);
	}

	private void kiesAfbeelding() throws Exception {
		// if(tegel.getSoortTegel().equals("wwww"))
		// pixmap.load("cloister.png");
		// else if(tegel.getSoortTegel().equals("wwrw"))
		// pixmap.load("cloisterr.png");
		// else if(tegel.getSoortTegel().equals("rrrr"))
		// pixmap.load("road4.png");
		// else if(tegel.getSoortTegel().equals("cwww"))
		// pixmap.load("city1.png");
		// else if(tegel.getSoortTegel().equals("ccww"))
		// pixmap.load("city11ne.png");
		// else if(tegel.getSoortTegel().equals("wcwc"))
		// pixmap.load("city11we.png");
		// else if(tegel.getSoortTegel().equals("crrw"))
		// pixmap.load("city1rse.png");
		// else if(tegel.getSoortTegel().equals("cwrr"))
		// pixmap.load("city1rsw.png");
		// else if(tegel.getSoortTegel().equals("crrr"))
		// pixmap.load("city1rswe.png");
		// else if(tegel.getSoortTegel().equals("crwr"))
		// pixmap.load("city1rwe.png");
		// else if(tegel.getSoortTegel().equals("cwwc"))
		// pixmap.load("city2nw.png");
		// else if(tegel.getSoortTegel().equals("crrc"))
		// pixmap.load("city2nwr.png");
		// else if(tegel.getSoortTegel().equals("wcwc"))
		// pixmap.load("city2we.png");
		// else if(tegel.getSoortTegel().equals("wwww"))
		// pixmap.load("cloister.png");
		// else if(tegel.getSoortTegel().equals("wwww"))
		// pixmap.load("cloister.png");
		// else if(tegel.getSoortTegel().equals("wwww"))
		// pixmap.load("cloister.png");
		// else if(tegel.getSoortTegel().equals("wwww"))
		// pixmap.load("cloister.png");
		// else if(tegel.getSoortTegel().equals("wwww"))
		// pixmap.load("cloister.png");
	}

	@Override
	public void hide() {

	}

	@Override
	public void show() {

	}

	@Override
	public void roteer(boolean richting) {
		QMatrix matrix = new QMatrix();
		QImage afbeelding = new QImage();

		if (richting)
			matrix.rotate(90.0);
		else
			matrix.rotate(-90.0);

		tegel.draaiTegel(richting);
		afbeelding = pixmap.toImage();
		afbeelding.transformed(matrix);
		pixmap = QPixmap.fromImage(afbeelding);
	}

}

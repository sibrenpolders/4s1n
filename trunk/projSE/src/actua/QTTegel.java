package actua;

import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPixmap;

public class QTTegel extends GTegel {
	private QPixmap pixmap;

	public QTTegel(Tegel tegel) {
		super(tegel);
		pixmap = new QPixmap(90,90);
		kiesAfbeelding();
	}
	
	public Tegel getTegel(){
		return super.getTegel();
	}

	public QPixmap getPixmap() {
		return pixmap;
	}

	public void setPixmap(QPixmap pixmap) {
		this.pixmap = pixmap;
	}
	
	public void wijzigGroottePixmap(int pixels){
		pixmap = pixmap.scaled(pixels,pixels);
	}

	/**
	 * Stad = s
	 * Wei = w
	 * Weg = g
	 * Klooster = k
	 * Kruispunt = r
	*/
	private void kiesAfbeelding(){
		pixmap.load(getTegel().getTegelPresentatie()+".png");
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

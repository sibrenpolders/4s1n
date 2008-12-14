package actua;

import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPixmap;

public class QTTegel extends GTegel {
	private QPixmap pixmap;

	public QTTegel() {
		super();
		pixmap = new QPixmap(90,90);
	}
	
	public QTTegel(Tegel tegel) {
		super(tegel);
		pixmap = new QPixmap(90,90);
		kiesAfbeelding();
	}
	
	public QTTegel(Tegel tegel,QPixmap pixmap) {
		super(tegel);
		this.pixmap = new QPixmap(pixmap);
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
		pixmap.load("src/icons/"+getTegel().getTegelPresentatie()+".png");
	}

	public void roteer(boolean richting) {
		QMatrix matrix = new QMatrix();
		
		tegel.draaiTegel(richting);
		matrix = matrix.rotate(90.0*tegel.getOrientatie());
		pixmap = new QPixmap(pixmap.transformed(matrix, TransformationMode.FastTransformation));
	}

}

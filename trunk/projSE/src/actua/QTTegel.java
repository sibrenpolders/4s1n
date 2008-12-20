package actua;

import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPixmap;

public class QTTegel extends GTegel {
	private static final int TEGEL_PRESENTATIE = 0;
	private static final int MAX_DRAAIING = 4;
	private QPixmap pixmap;

	public QTTegel() {
		super();
		pixmap = new QPixmap(90,90);
	}
	
	public QTTegel(String[] tegel) {
		super(tegel);
		pixmap = new QPixmap(90,90);
		kiesAfbeelding();
	}
	
	public QTTegel(String[] tegel,QPixmap pixmap) {
		super(tegel);
		this.pixmap = new QPixmap(pixmap);
	}
	
	public String[] getTegel(){
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
		pixmap.load("src/icons/"+ tegel[TEGEL_PRESENTATIE] + ".png");
	}

	public void roteer(boolean richting) {
		QMatrix matrix = new QMatrix();
		
		if (richting) {
			orientatie = (orientatie + 1)%MAX_DRAAIING;
		} else {
			orientatie = (MAX_DRAAIING + orientatie - 1)%MAX_DRAAIING;
		}
		matrix = matrix.rotate(90.0*(double)orientatie);
		tegel[2] = new String("" + orientatie);
		pixmap = new QPixmap(pixmap.transformed(matrix, TransformationMode.FastTransformation));
	}

}

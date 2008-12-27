package actua;

import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPixmap;

public class QTTegel extends GTegel {
	private static final int TEGEL_PRESENTATIE = 0;
	private static final int MAX_DRAAIING = 4;
	private QPixmap pixmap;
	private QTPion pion[];
	private Vector2D tegelCoord;

	public QTTegel() {
		super();
		pixmap = new QPixmap(90, 90);
		pion = new QTPion[9];
	}

	public QTTegel(String[] tegel, Spel spel) {
		super(tegel, spel);
		pixmap = new QPixmap(90, 90);
		pion = new QTPion[9];
		kiesAfbeelding();
	}

	public QTTegel(String[] tegel, Spel spel, QPixmap pixmap) {
		super(tegel, spel);
		this.pixmap = new QPixmap(pixmap);
	}

	public boolean plaatsPionInSectie(int pionCoord, QTPion p) {
		if (pion[pionCoord] != null)
			return false;
		else {
			pion[pionCoord] = p;
			return true;
		}
	}

	public QTPion geefPionInSectie(int pionCoord) {
		return pion[pionCoord];
	}

	public void verwijderPionInSectie(int pionCoord) {
		pion[pionCoord] = null;
	}

	public String[] getTegel() {
		return super.getTegel();
	}

	public QPixmap getPixmap() {
		return pixmap;
	}

	public void setPixmap(QPixmap pixmap) {
		this.pixmap = pixmap;
	}

	public void wijzigGroottePixmap(int pixels) {
		pixmap = pixmap.scaled(pixels, pixels);
	}

	/**
	 * Stad = s Wei = w Weg = g Klooster = k Kruispunt = r
	 */
	private void kiesAfbeelding() {
		pixmap.load("src/icons/" + tegel[TEGEL_PRESENTATIE] + ".png");
	}

	public void roteer(boolean richting) {
		QMatrix matrix = new QMatrix();

		if (richting) {
			orientatie = (orientatie + 1) % MAX_DRAAIING;
		} else {
			orientatie = (MAX_DRAAIING + orientatie - 1) % MAX_DRAAIING;
		}
		matrix = matrix.rotate(90.0 * (double) orientatie);
		tegel[2] = new String("" + orientatie);
		pixmap = new QPixmap(pixmap.transformed(matrix,
				TransformationMode.FastTransformation));
	}

}

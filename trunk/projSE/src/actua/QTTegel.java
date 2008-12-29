package actua;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragLeaveEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class QTTegel extends GTegel {
	private static final int TEGEL_PRESENTATIE = 0;
	private static final int MAX_DRAAIING = 4;
	private QPixmap pixmap;
	private QTPion pion[];
	private Vector2D tegelCoord;
	private QGraphicsView qGraphicsView;

	private class QtGraphicsView extends QGraphicsView {
		public QtGraphicsView(QGraphicsScene parent) {
			super(parent);
			init();
			setAcceptDrops(true);
		}

		private void init() {
			setMaximumSize(90, 90);
			setMinimumSize(90, 90);
		}

		protected void dragEnterEvent(QDragEnterEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")) {
				if (event.source().equals(this)) {
					event.setDropAction(Qt.DropAction.MoveAction);
					event.accept();
				} else {
					event.acceptProposedAction();
				}
			} else if (event.mimeData().hasFormat("application/x-pionitemdata")) {
				if (event.source().equals(this)) {
					event.setDropAction(Qt.DropAction.MoveAction);
					event.accept();
				} else {
					event.acceptProposedAction();
				}
			} else {
				event.ignore();
			}
		}

		protected void dragMoveEvent(QDragMoveEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")
					|| event.mimeData().hasFormat("application/x-pionitemdata")) {
				if (event.source().equals(this)) {
					event.setDropAction(Qt.DropAction.MoveAction);
					event.accept();

				} else {
					event.acceptProposedAction();
				}
			} else {
				event.ignore();
			}
		}

		protected void dropEvent(QDropEvent event) {
			System.err.println("ERR");
		}

		protected void dragLeaveEvent(QDragLeaveEvent event) {
		}

		protected void drawBackground(QPainter painter, QRectF rect) {
			QPixmap pixmap = backgroundBrush().texture();
			painter.drawPixmap(-45, -45, 90, 90, pixmap);
		}

		public void setPixmap(QPixmap pixmap) {
			setBackgroundBrush(new QBrush(pixmap));
			backgroundBrush().transform().reset();
		}
	}

	public QTTegel() {
		super();
		pixmap = new QPixmap(90, 90);
		pion = new QTPion[9];
	}

	public QTTegel(int row, int col) {
		super();
		tegelCoord = new Vector2D(col, row);
		pixmap = new QPixmap(90, 90);
		pion = new QTPion[9];
	}

	public QTTegel(String[] tegel, Spel spel, int row, int col) {
		super(tegel, spel);
		tegelCoord = new Vector2D(col, row);
		pixmap = new QPixmap(90, 90);
		pion = new QTPion[9];
		kiesAfbeelding();
		QtGraphicsView g = new QtGraphicsView(new QGraphicsScene());
		g.setPixmap(pixmap);
		qGraphicsView = g;
	}

	public QTTegel(String[] tegel, Spel spel) {
		super(tegel, spel);
		pixmap = new QPixmap(90, 90);
		pion = new QTPion[9];
		kiesAfbeelding();
		QtGraphicsView g = new QtGraphicsView(new QGraphicsScene());
		g.setPixmap(pixmap);
		qGraphicsView = g;
	}

	public QTTegel(String[] tegel, Spel spel, QPixmap pixmap) {
		super(tegel, spel);
		this.pixmap = new QPixmap(pixmap);
		QtGraphicsView g = new QtGraphicsView(new QGraphicsScene());
		g.setPixmap(pixmap);
		qGraphicsView = g;
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

	public int getRow() {
		return this.tegelCoord.getY();
	}

	public int getCol() {
		return this.tegelCoord.getX();
	}

	public void setCoords(int row, int col) {
		this.tegelCoord = new Vector2D(col, row);
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

	public QGraphicsView getGraphicsView() {
		return qGraphicsView;
	}
}

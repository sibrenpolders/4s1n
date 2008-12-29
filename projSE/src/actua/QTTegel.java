package actua;

import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragLeaveEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class QTTegel extends GTegel {

	private class tegelView extends QGraphicsView {
		private QTTegel parent;

		public tegelView(QTTegel parent, QWidget p) {
			super(p);
			this.parent = parent;
			init();
			setBackgroundBrush(new QBrush(pixmap));
			setAcceptDrops(true);
		}

		public QTTegel getParent() {
			return parent;
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
			if (event.mimeData().hasFormat("application/x-dnditemdata"))
				; // doe niks... QTSpeelveld handelt dit al af
			else if (event.mimeData().hasFormat("application/x-pionitemdata")) {
				short zone = getLandsdeel(event.pos().x(), event.pos().y());
				int row = getRow(event.pos().x(), event.pos().y());
				int col = getCol(event.pos().x(), event.pos().y());
				parent.voegPionToe(zone, row, col);
				System.out.println("Pion dropped at row: " + row + ", col: "
						+ col);
			}
		}

		protected void dragLeaveEvent(QDragLeaveEvent event) {
			; // doe niks
		}

		protected void drawBackground(QPainter painter, QRectF rect) {
			painter.drawPixmap(-45, -45, 90, 90, pixmap);
		}

		// TODO Verdeling is dezelfde als in Tegel
		// Niet ideaal voor low coupling, maar 'k weet niet hoe anders een
		// eenduidige voorstelling te bekomen die door zowel Spel als door
		// QTTegel/Speelveld kan gebruikt worden.
		private short getLandsdeel(int localX, int localY) {
			int width = width() - 5;
			int height = height() - 5;
			int x = localX + 5;
			int y = localY + 5;

			// eerste rij
			if (y >= 0 && y < height / 3) {
				if (x >= 0 && x < width / 3) {
					return 0;
				} else if (x < 2 * (width / 3)) {
					return 2;
				} else {
					return 4;
				}
			}
			// tweede rij
			else if (y < 2 * (height / 3)) {
				if (x >= 0 && x < width / 3) {
					return 11;
				} else if (x < 2 * (width / 3)) {
					return 12;
				} else {
					return 5;
				}
			}
			// laatste rij
			else if (y <= height) {
				if (x >= 0 && x < width / 3) {
					return 10;
				} else if (x < 2 * (width / 3)) {
					return 8;
				} else {
					return 6;
				}
			}

			return -1; // unknown
		}

		private short getCol(int localX, int localY) {
			int width = width() - 5;
			int x = localX + 5;

			if (x >= 0 && x < width / 3) {
				return 0;
			} else if (x < 2 * (width / 3)) {
				return 1;
			} else {
				return 2;
			}
		}

		private short getRow(int localX, int localY) {
			int height = height() - 5;
			int y = localY + 5;

			// eerste rij
			if (y >= 0 && y < height / 3) {
				return 0;
			}
			// tweede rij
			else if (y < 2 * (height / 3)) {
				return 1;
			}
			// laatste rij
			else
				return 2;
		}
	}

	private static final int TEGEL_PRESENTATIE = 0;
	private static final int MAX_DRAAIING = 4;
	private QPixmap pixmap;
	private char pion[][];
	private Vector2D tegelCoord;
	private boolean isBackground;
	public tegelView view;

	// default constructor die een background image bevat
	public QTTegel(Spel spel, int row, int col) {
		super(spel);
		isBackground = true;
		tegelCoord = new Vector2D(col, row);
		pion = new char[3][3];
		pixmap = new QPixmap(90, 90);
		pixmap.load("src/icons/background.xpm");
	}

	public QTTegel(String[] tegel, Spel spel, int row, int col) {
		super(tegel, spel);
		isBackground = false;
		tegelCoord = new Vector2D(col, row);
		pion = new char[3][3];
		pixmap = new QPixmap(90, 90);
		kiesAfbeelding();
	}

	public QTTegel(String[] tegel, Spel spel) {
		super(tegel, spel);
		isBackground = false;
		pixmap = new QPixmap(90, 90);
		pion = new char[3][3];
		pixmap = new QPixmap(90, 90);
		kiesAfbeelding();
	}

	/**
	 * Stad = s Wei = w Weg = g Klooster = k Kruispunt = r
	 */
	private void kiesAfbeelding() {
		pixmap.load("src/icons/" + tegel[TEGEL_PRESENTATIE] + ".png");
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

	public int getRow() {
		return this.tegelCoord.getY();
	}

	public int getCol() {
		return this.tegelCoord.getX();
	}

	public void setCoords(int row, int col) {
		this.tegelCoord = new Vector2D(col, row);
	}

	public boolean plaatsPionInSectie(int row, int col, char pionKleur) {
		if (pion[row][col] != 0)
			return false;
		else {
			pion[row][col] = pionKleur;
			return true;
		}
	}

	private void voegPionToe(short zone, int row, int col) {
		if (!isBackground && spel.plaatsPion(this.tegelCoord, zone)) {
			char kleur = spel.geefHuidigeSpeler();
			verwijderPionInSectie(row, col);
			plaatsPionInSectie(row, col, kleur);
		}
	}

	public char geefPionInSectie(int row, int col) {
		return pion[row][col];
	}

	public void verwijderPionInSectie(int row, int col) {
		pion[row][col] = 0;
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

	public QGraphicsView getGraphicsView(QWidget parent) {
		if (view != null)
			view.dispose();

		view = new tegelView(this, parent);
		return view;
	}

	public QGraphicsView getPrevGraphicsView() {
		return view;
	}
}

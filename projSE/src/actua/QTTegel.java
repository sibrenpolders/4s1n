package actua;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
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
import com.trolltech.qt.gui.QSpacerItem;

public class QTTegel extends GTegel {

	public class tegelView extends QGraphicsView {
		private QGridLayout gridLayout;

		public tegelView() {
			setScene(new QGraphicsScene());
			init();
			scene().setForegroundBrush(null);
			update();

		}

		private void init() {
			setGeometry(0, 0, 90, 90);
			setMaximumSize(90, 90);
			setMinimumSize(90, 90);
			gridLayout = new QGridLayout();
			gridLayout.setSpacing(0);
			setLayout(gridLayout);
			setAcceptDrops(true);
			createEmpty();
		}

		private void createEmpty() {
//			this.setBackgroundBrush(new QBrush(pixmap));

			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					gridLayout.addItem(new QSpacerItem(30, 30), i, j, 1, 1);
				}
			}
		}

		protected void drawBackground(QPainter painter, QRectF rect) {
			painter.drawPixmap(-width() / 2 + 1, -height() / 2 + 1, width(),
					height(), pixmap);
		}

		private void setPixmap(QPixmap mpixmap) {
			pixmap = mpixmap;
			pixmap = pixmap.scaled(90, 90);
			scene().clear();
			this.setBackgroundBrush(null);
			update();
			isBackground = false;
		}

		protected void dragEnterEvent(QDragEnterEvent event) {
			if (event.mimeData().hasFormat("application/x-pionitemdata")) {
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
			if (event.mimeData().hasFormat("application/x-pionitemdata")) {
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
			if (event.mimeData().hasFormat("application/x-pionitemdata")) {
				int row = event.pos().y() / 30;
				int col = event.pos().x() / 30;
				short zone = getZone(row, col);

				QByteArray itemData = event.mimeData().data(
						"application/x-pionitemdata");
				QDataStream dataStream = new QDataStream(itemData,
						QIODevice.OpenModeFlag.ReadOnly);
				QPixmap pionpixmap = new QPixmap();
				pionpixmap.readFrom(dataStream);

				System.out.println("Z: " + zone);
				if (spel.plaatsPion(tegelCoord, zone)) {
					QLabel label = new QLabel();
					label.setPixmap(new QPixmap(pionpixmap));
					gridLayout.addWidget(label, row, col, 1, 1);
				}
			} else {
				event.ignore();
			}
		}

		protected void dragLeaveEvent(QDragLeaveEvent event) {
			; // TODO iets doen hier?
		}

		// TODO Verdeling is dezelfde als in Tegel
		// Niet ideaal voor low coupling, maar 'k weet niet hoe anders een
		// eenduidige voorstelling te bekomen die door zowel Spel als door
		// QTTegel/Speelveld kan gebruikt worden.
		private short getZone(int row, int col) {
			if (row == 0 && col == 1) {
				return (short) 2;
			} else if (row == 1 && col == 0) {
				return (short) 11;
			} else if (row == 1 && col == 1) {
				return (short) 12;
			} else if (row == 1 && col == 2) {
				return (short) 5;
			} else if (row == 2 && col == 1) {
				return (short) 8;
			} else {
				return (short) -1;
			}
		}
	}

	private static final int TEGEL_PRESENTATIE = 0;
	private static final int MAX_DRAAIING = 4;
	private QPixmap pixmap;
	private Vector2D tegelCoord;
	private char pion[][];
	private boolean isBackground;
	public tegelView view;

	// TODO nutteloze constructor?
	public QTTegel(Spel spel, Vector2D tegelCoord) {
		super(spel);
		isBackground = true;
		this.tegelCoord = new Vector2D(tegelCoord);
		pion = new char[3][3];
		pixmap = new QPixmap(90, 90);
		pixmap.load("src/icons/background.xpm");
		view = new tegelView();
	}

	public QTTegel(String[] tegel, Spel spel,
			Vector2D tegelCoord) {
		super(tegel, spel);
		isBackground = false;
		this.tegelCoord = new Vector2D(tegelCoord);
		pion = new char[3][3];
		pixmap = new QPixmap(90, 90);
		kiesAfbeelding();
		view = new tegelView();
	}

	public QTTegel(String[] tegel, Spel spel) {
		super(tegel, spel);
		isBackground = false;
		pixmap = new QPixmap(90, 90);
		pion = new char[3][3];
		kiesAfbeelding();
		view = new tegelView();
	}

	public String[] getTegel() {
		return super.getTegel();
	}

	/**
	 * Stad = s Wei = w Weg = g Klooster = k Kruispunt = r
	 */
	private void kiesAfbeelding() {
		pixmap.load("src/icons/" + tegel[TEGEL_PRESENTATIE] + ".png");
		pixmap = pixmap.scaled(90, 90);
	}

	public QPixmap getPixmap() {
		return pixmap;
	}

	public void setPixmap(QPixmap pixmap) {
		this.pixmap = pixmap;
		isBackground = false;
		view.setPixmap(pixmap);
	}

	public void setPixmap(String[] tegel) {
		this.tegel = tegel;
		kiesAfbeelding();
		isBackground = false;
		view.setPixmap(pixmap);
	}

	public int getRow() {
		if (tegelCoord != null) {
			return this.tegelCoord.getY();
		}

		return -1;
	}

	public int getCol() {
		if (tegelCoord != null) {
			return this.tegelCoord.getX();
		}

		return -1;
	}

	public Vector2D getCoordsRelativeToStartTegel() {
		return tegelCoord;
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
		if (!isBackground && spel.plaatsPion(tegelCoord, zone)) {
			char kleur = spel.geefHuidigeSpeler();
			verwijderPionInSectie(row, col); // waarom ? ik zou dit weglate
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
		setPixmap(new QPixmap(pixmap.transformed(matrix,TransformationMode.FastTransformation)));
	}

	public tegelView getTegelView() {
		return view;
	}

//	public tegelView getTegelView(QTSpeelveld cont) {
//		return (view = new tegelView(new QGraphicsScene(), cont));
//	}

//	public tegelView getCurrTegelView(QTSpeelveld cont) {
//		return view;
//	}
}

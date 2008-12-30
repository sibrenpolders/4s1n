package actua;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragLeaveEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QFrame;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QSpacerItem;
import com.trolltech.qt.gui.QWidget;

public class QTTegel extends GTegel {

	private class tegelView extends QLabel {
		private QGridLayout gridLayout;

		public tegelView() {
			init();
			setAcceptDrops(true);
		}

		private void init() {
//			QGraphicsScene scene = new QGraphicsScene();
//			setScene(scene);
			setMaximumSize(90, 90);
			setMinimumSize(90, 90);
			gridLayout = new QGridLayout();
			gridLayout.setSpacing(0);
			setLayout(gridLayout);
			createEmpty();
		}

		private void createEmpty() {
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					gridLayout.addItem(new QSpacerItem(30, 30),
							i, j, 1, 1);
				}
			}
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
			if (event.mimeData().hasFormat("application/x-pionitemdata")) {
				int row = event.pos().y()/30;
				int col = event.pos().x()/30;
				short zone = getZone(row, col);
				
				QByteArray itemData = event.mimeData().data("application/x-pionitemdata");
	            QDataStream dataStream = new QDataStream(itemData, QIODevice.OpenModeFlag.ReadOnly);
				QPixmap pixmap = new QPixmap();
				pixmap.readFrom(dataStream);
				
				System.out.println("Z: " + zone);
				if (spel.plaatsPion(tegelCoord, zone)) {
					QLabel label = new QLabel();
					label.setPixmap(new QPixmap(pixmap));
					gridLayout.addWidget(label, col, row, 1, 1);
				}
			}
		}

		protected void dragLeaveEvent(QDragLeaveEvent event) {
			; // doe niks
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

//		private short getCol(int localX, int localY) {
//			int width = width() - 5;
//			int x = localX + 5;
//
//			if (x >= 0 && x < width / 3) {
//				return 0;
//			} else if (x < 2 * (width / 3)) {
//				return 1;
//			} else {
//				return 2;
//			}
//		}
//
//		private short getRow(int localX, int localY) {
//			int height = height() - 5;
//			int y = localY + 5;
//
//			// eerste rij
//			if (y >= 0 && y < height / 3) {
//				return 0;
//			}
//			// tweede rij
//			else if (y < 2 * (height / 3)) {
//				return 1;
//			}
//			// laatste rij
//			else
//				return 2;
//		}

		protected void drawBackground(QPainter painter,
                QRectF rect) {
			painter.drawPixmap(-width()/2+1, -height()/2+1, width(), height(), 
					pixmap);
		}
		
//		public void setPixmap(QPixmap pixmap) {
////			setBackgroundBrush(new QBrush(pixmap));
//		}
	}

	private static final int TEGEL_PRESENTATIE = 0;
	private static final int MAX_DRAAIING = 4;
	private QPixmap pixmap;
	private char pion[][];
	private Vector2D tegelCoord;
	private boolean isBackground;
	public tegelView view;

	// default constructor die een background image bevat
	public QTTegel(Spel spel, Vector2D tegelCoord) {
		super(spel);
		isBackground = true;
		this.tegelCoord = new Vector2D(tegelCoord);
		pion = new char[3][3];
		pixmap = new QPixmap(90, 90);
		pixmap.load("src/icons/background.xpm");
	}

	public QTTegel(String[] tegel, Spel spel, Vector2D tegelCoord) {
		super(tegel, spel);
		isBackground = false;
		this.tegelCoord = new Vector2D(tegelCoord);
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
		if (!isBackground && spel.plaatsPion(tegelCoord, zone)) {
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

	public QLabel getGraphicsView() {
		view = new tegelView();		
		view.setPixmap(pixmap);
		return view;
	}

	public QLabel getPrevGraphicsView() {
		return view;
	}
}

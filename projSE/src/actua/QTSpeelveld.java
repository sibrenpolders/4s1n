package actua;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QRect;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragLeaveEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLayoutItemInterface;
import com.trolltech.qt.gui.QPaintEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QShortcut;
import com.trolltech.qt.gui.QSpacerItem;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.gui.QPalette.ColorRole;

public class QTSpeelveld extends GSpeelveld {

	private class VeldWidget extends QGraphicsView {
		private QGridLayout gridLayout;
//		private QTSpeelveld parent;

		// TODO er is geen aanvaardbare reden waarom deze private inner class een QTSpeelveld parent zou
		// mogen meekrijgen. A. Hij kan aan alles van zijn parent
		//                   B. Hij is nooit zichtbaar buiten deze klasse.
		public VeldWidget(/*QTSpeelveld parent*/) {
			gridLayout = new QGridLayout();
			gridLayout.setSpacing(0);
			setLayout(gridLayout);
			QGraphicsScene scene = new QGraphicsScene();
			setScene(scene);
//			this.parent = parent;
			setAcceptDrops(true);
		}

		public void init() {
			clear();
			setBackgroundBrush(new QBrush(getBackgroundTexture()));
			setUpdatesEnabled(true);
			
			// elke tegel is 90x90... zodus
			// waarschijnlijk een stomme naam. Maar ik dacht dat _ voor een functie betekende dat het een
			// member functie was ofzo...
			_resize(810, 630);
			createEmptyTegels();						
		}
		
		public void clear() {
			if (layout() != null) {
				List<QObject> list = children();
				int start = list.size() - gelegdeTegels.size();
				int size = list.size();
				for (int i = start; i < size; ++i) {
					list.get(start).dispose();
					list.remove(start);
				}
			}
		}
		
		public void insertTegel(QTTegel tegel, int gridRow, int gridCol) {
//			 TODO niet van toepassing
//			if (getTegel(gridRow, gridCol) != null) {
//				QTTegel tegelPrev = getTegel(upperLeftRow + gridRow,
//						upperLeftCol + gridCol);
//				gridLayout.removeWidget(tegelPrev.getPrevGraphicsView());
//			}

			// tegel.getGraphicsView() maakt altijd een nieuwe instantie aan
			gridLayout.addWidget(tegel.getGraphicsView(), gridRow, gridCol, 1, 1);

		}

		public void setGroen(/*int gridRow, int gridCol*/) {
			update();
//			QTTegel tegel = getTegel(upperLeftRow + gridRow,
//					upperLeftCol + gridCol);
//			if (tegel != null) {
//				QLabel view = tegel.getPrevGraphicsView();
////				view.setForegroundBrush(new QBrush(new QColor(0, 255, 0, 127)));
//			}
		}

		public void clearGroen() {
//			for (int i = 0; i < rows; i++) {
//				for (int j = 0; j < columns; j++) {
//
//					QTTegel tegel = getTegel(upperLeftRow + i,
//							upperLeftCol + j);
//					if (tegel != null) {
////						QGraphicsView view = tegel.getPrevGraphicsView();
////						view.setForegroundBrush(null);
//					}
//				}
//			}
		}

		protected void dragEnterEvent(QDragEnterEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")) {
				kleurMogelijkhedenGroen();
				if (event.source().equals(this)) {
					event.setDropAction(Qt.DropAction.MoveAction);
					event.accept();
				} else {
					event.acceptProposedAction();
				}
				// TODO dit moet gefixed worden in QTTegel
//			} else if (event.mimeData().hasFormat("application/x-pionitemdata")) {
//				if (event.source().equals(this)) {
//					event.setDropAction(Qt.DropAction.MoveAction);
//					event.accept();
//				} else {
//					event.acceptProposedAction();
//				}
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

		protected void dragLeaveEvent(QDragLeaveEvent event) {
			clearGroen();
		}

		protected void dropEvent(QDropEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")) {
				int y = event.pos().x();
				int x = event.pos().y();
				x = x / 90 - startTegelPos.getX();
				y = y / 90 - startTegelPos.getY();
				
				QByteArray itemData = event.mimeData().data("application/x-dnditemdata");
	            QDataStream dataStream = new QDataStream(itemData, QIODevice.OpenModeFlag.ReadOnly);
				QPixmap pixmap = new QPixmap();
				pixmap.readFrom(dataStream);
				
				voegTegelToe(new Vector2D(x, y), pixmap);
				clearGroen();

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
		
		private void createEmptyTegels() {
			int rasterrow = upperLeftRow;
			for (int i = 0; i < rows; i++) {
				int rastercol = upperLeftCol;
				for (int j = 0; j < columns; j++) {
					gridLayout.addItem(new QSpacerItem(90, 90), i, j, 1, 1);
//				}
//					QTTegel tegel = new QTTegel(spel, rasterrow, rastercol);
//					insertTegel(tegel, i, j);
					rastercol++;
				}
				rasterrow++;
			}			
		}
		
		private void _resize(int width, int height) {
			setGeometry(0, 0, width, height);
			setMaximumSize(new QSize(width, height));
			setMinimumSize(new QSize(width, height));
		}
		
		// Kan misschien handig zijn moest de veldgrootte ooit wijzigen?
		private QPixmap getBackgroundTexture() {
			QPixmap pixmap = new QPixmap("src/icons/background.xpm");
			pixmap.scaled(width(), height());
			
			return pixmap;
		}

		public void addEmpty(QTTegel tegel, int i, int j) {
			gridLayout.addItem(new QSpacerItem(90, 90), i, j, 1, 1);
		}
		
		protected void drawForeground(QPainter painter,
                QRectF rect) {
//			Vector2D tmp;
//			for (int i = 0; i < mogelijkeZetten.size(); ++i) {
//				tmp = mogelijkeZetten.get(i);
//				QLabel label = new QLabel();
//				label.setPixmap(new QPixmap("src/icons/pion.png"));
//				label.setMouseTracking(false);
//				gridLayout.addWidget(label, tmp.getX(), tmp.getY(), 1, 1);
//			}
		}
	}

	private ArrayList<QTTegel> gelegdeTegels;
	private VeldWidget mainWidget;
	private static int DEFAULT_ROWS = 7;
	private static int DEFAULT_COLS = 9;
	private static int DEFAULT_ULROW = -3;
	private static int DEFAULT_ULCOL = -4;
	private int rows = DEFAULT_ROWS;
	private int columns = DEFAULT_COLS;
	private int upperLeftRow = DEFAULT_ULROW;
	private int upperLeftCol = DEFAULT_ULCOL;
	private Vector2D startTegelPos;
	private ArrayList<Vector2D> mogelijkeZetten;

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super(spel);
		mainWidget = new VeldWidget();
		startTegelPos = new Vector2D((rows-1)/2, (columns-1)/2);
		clearSpeelveld();
	}

	public QWidget getGridWidget() {
		return mainWidget;
	}

	private QTTegel getTegel(int row, int col) {
		for (int i = 0; i < gelegdeTegels.size(); ++i) {
			QTTegel result = (QTTegel) ((gelegdeTegels.get(i)));
			if (result.getCol() == col && result.getRow() == row)
				return result;
		}
		return null;
	}

	// Clear héél het speelveld -> vul het met default tegels.
	protected void clearSpeelveld() {
		upperLeftRow = DEFAULT_ULROW;
		upperLeftCol = DEFAULT_ULCOL;
		gelegdeTegels = new ArrayList<QTTegel>();
		mainWidget.init();

		// camera dizzle
		camera.setMinVector(new Vector3D(-spel.getStapelSize(), -spel
				.getStapelSize(), 0));
		camera.setMaxVector(new Vector3D(spel.getStapelSize(), spel
				.getStapelSize(), 6));
		camera.setHuidigeVector(new Vector3D(-3, -4, 3));

		QPushButton buttonUp = new QPushButton("^", mainWidget);
		buttonUp.setGeometry(mainWidget.width() / 2 - 18, 0, 35, 25);
		buttonUp.clicked.connect(this, "cameraUp()");
		QPushButton buttonDown = new QPushButton("v", mainWidget);
		buttonDown.setGeometry(mainWidget.width() / 2 - 18,
				mainWidget.height() - 26, 35, 25);
		buttonDown.clicked.connect(this, "cameraDown()");
		QPushButton buttonLeft = new QPushButton("<", mainWidget);
		buttonLeft.setGeometry(0, mainWidget.height() / 2 - 13, 25, 35);
		buttonLeft.clicked.connect(this, "cameraLeft()");
		QPushButton buttonRight = new QPushButton(">", mainWidget);
		buttonRight.setGeometry(mainWidget.width() - 26,
				mainWidget.height() / 2 - 13, 25, 35);
		buttonRight.clicked.connect(this, "cameraRight()");

		QShortcut shortcut = new QShortcut(new QKeySequence("Up"), mainWidget);
		shortcut.activated.connect(this, "cameraUp()");
		shortcut = new QShortcut(new QKeySequence("Down"), mainWidget);
		shortcut.activated.connect(this, "cameraDown()");
		shortcut = new QShortcut(new QKeySequence("Left"), mainWidget);
		shortcut.activated.connect(this, "cameraLeft()");
		shortcut = new QShortcut(new QKeySequence("Right"), mainWidget);
		shortcut.activated.connect(this, "cameraRight()");
	}

	protected void initialiseerSpeelveld() {
		gelegdeTegels = new ArrayList<QTTegel>();
		QTTegel startTegel = new QTTegel(spel.geefStartTegel(), spel, new Vector2D(0, 0));
		mainWidget.insertTegel(startTegel, startTegelPos.getX(), startTegelPos.getY());
		gelegdeTegels.add(startTegel);
	}

	private void cameraUp() {
		upperLeftRow--;
		startTegelPos.setX(startTegelPos.getX()-1);
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() - 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraDown() {
		upperLeftRow++;
		startTegelPos.setX(startTegelPos.getX()+1);
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() + 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraLeft() {
		upperLeftCol--;
		startTegelPos.setY(startTegelPos.getY()-1);
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() - 1, camera.getHuidigeVector()
						.getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraRight() {
		upperLeftCol++;
		startTegelPos.setY(startTegelPos.getY()+1);
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() + 1, camera.getHuidigeVector()
						.getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void veranderZicht() {
		mainWidget.clear();
		mainWidget.createEmptyTegels();
		for (int i = 0; i < gelegdeTegels.size(); ++i) {
			QTTegel tegel = gelegdeTegels.get(i);
			int col = startTegelPos.getX() + tegel.getCol();
			int row = startTegelPos.getY() + tegel.getRow();
			
			if (col >= 0 && col < columns && row >= 0 && row < rows) {
				mainWidget.insertTegel(tegel, col, row);
			}
		}
		
//		for (int i = upperLeftCol; i < upperLeftCol + columns; ++i)
//			for (int j = upperLeftRow; j < upperLeftRow + rows; ++j) {
//				QTTegel tegel = getTegel(j, i);
//				if (tegel != null) {
//					mainWidget.insertTegel(tegel, j - upperLeftRow, i
//							- upperLeftCol);
//				}
//				else {
//					mainWidget.addEmpty(tegel, j - upperLeftRow, i
//							- upperLeftCol);
//				}
////					mainWidget.insertTegel(new QTTegel(spel, j, i), j
////							- upperLeftRow, i - upperLeftCol);
//			}

		this.mainWidget.show();
	}

	private boolean voegTegelToe(Vector2D coord, QPixmap pixmap) {
		String[] tegel = spel.vraagNieuweTegel();
		boolean isTegelGeplaatst = false;

		if (spel.isTegelPlaatsingGeldig(tegel, coord)) {
			tegel = spel.neemTegelVanStapel();
			spel.plaatsTegel(tegel, coord);
			QTTegel qTegel = new QTTegel(tegel, spel, coord);
			qTegel.setPixmap(pixmap);
			
			Vector2D gridCoord = new Vector2D(coord.getX() + startTegelPos.getX(), 
					coord.getY() + startTegelPos.getY());
			mainWidget.insertTegel(qTegel, gridCoord.getX(), gridCoord.getY());
			gelegdeTegels.add(qTegel);
			isTegelGeplaatst = true;
		}

		// altijd resetten naar 0 hier
		// we werken met referenties
		tegel[2] = new String("0");
		return isTegelGeplaatst;
	}

	private void kleurMogelijkhedenGroen() {
//		String[] tegel = spel.vraagNieuweTegel();
//		Vector2D startTegelPos = spel.getStartTegelPos();
//
//		if (startTegelPos != null) {
//			for (int row = upperLeftRow; row < upperLeftRow + rows; ++row)
//				for (int col = upperLeftCol; col < upperLeftCol + columns; ++col)
//					if (spel.isTegelPlaatsingGeldig(tegel, new Vector2D(col,
//							row))) {						
//						mainWidget.setGroen(row - upperLeft, col
//								- upperLeftCol);
//					}
//		}
		
		ArrayList<Vector2D> _mogelijkeZetten = spel.geefMogelijkeZetten();
		mogelijkeZetten = new ArrayList<Vector2D>();
		
		Vector2D tmp;
		int row, col;		
		for (int i = 0; i < _mogelijkeZetten.size(); ++i) {
			tmp = _mogelijkeZetten.get(i);
			row = tmp.getX() + startTegelPos.getX();
			col = tmp.getY() + startTegelPos.getY();
			
			if (row >= 0 && row < rows && col >= 0 && col < columns) {
				mogelijkeZetten.add(new Vector2D(row, col));
			}
		}
		
		mainWidget.setGroen();
	}

	private void clearGroen() {
		mainWidget.clearGroen();
	}

	protected void updateSpeelveld() {
		veranderZicht();
	}

	protected void voegTegelToe(String[] tegel, Vector2D coord) {
		;
	}

	public void update(Observable arg0, Object arg1) {
		if (!arg1.equals(true)) {
			return;
		}

		clearSpeelveld();
		initialiseerSpeelveld();

		this.mainWidget.show();
	}
}

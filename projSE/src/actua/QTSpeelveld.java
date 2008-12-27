package actua;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QSize;
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
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QShortcut;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.core.Qt;

public class QTSpeelveld extends GSpeelveld {
	private class QtGraphicsView extends QGraphicsView {
		private boolean gevuld;
		private Vector2D gridCoord;
		private QGridLayout layout;

		public QtGraphicsView(QGraphicsScene parent, Vector2D coord) {
			super(parent);
			layout = new QGridLayout();
			this.setLayout(layout);
			init();
			setAcceptDrops(true);
			gevuld = false;
			this.gridCoord = coord;
		}

		private void init() {
			setBackgroundBrush(new QBrush(new QPixmap(
					"src/icons/background.xpm")));
			setCacheMode(new QGraphicsView.CacheMode(
					QGraphicsView.CacheModeFlag.CacheBackground));
			setViewportUpdateMode(QGraphicsView.ViewportUpdateMode.FullViewportUpdate);

		}

		private void setPixmap(QPixmap pixmap) {
			scene().clear();
			scene().addPixmap(pixmap.scaled(width() - 5, height() - 5));
			update();
			gevuld = true;
		}

		private void removePixmap() {
			scene().clear();
			update();
			gevuld = false;
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

		protected void dragEnterEvent(QDragEnterEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")) {
				kleurMogelijkhedenGroen();
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
			boolean tegel = event.mimeData().hasFormat(
					"application/x-dnditemdata");
			boolean pion = event.mimeData().hasFormat(
					"application/x-pionitemdata");

			if (tegel && !gevuld) {
				QByteArray itemData = event.mimeData().data(
						"application/x-dnditemdata");
				QDataStream dataStream = new QDataStream(itemData,
						QIODevice.OpenModeFlag.ReadOnly);

				QPixmap pixmap = new QPixmap();
				QPoint offset = new QPoint();
				pixmap.readFrom(dataStream);
				offset.readFrom(dataStream);

				if (voegTegelToe(gridCoord, pixmap))
					gevuld = true;

				clearGroen();
			} else if (pion) {
				QByteArray itemData = event.mimeData().data(
						"application/x-dnditemdata");
				QDataStream dataStream = new QDataStream(itemData,
						QIODevice.OpenModeFlag.ReadOnly);

				QPixmap pixmap = new QPixmap();
				QPoint offset = new QPoint();
				pixmap.readFrom(dataStream);
				offset.readFrom(dataStream);
				short zone = getLandsdeel(event.pos().x(), event.pos().y());
				int row = getRow(event.pos().x(), event.pos().y());
				int col = getCol(event.pos().x(), event.pos().y());
				voegPionToe(gridCoord, zone, row, col, pixmap);
			}

			if (tegel || pion) {
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

		private void voegPionToe(Vector2D gridCoord, short zone, int row,
				int col, QPixmap pixmap) {
			Vector2D coord = new Vector2D(camera.getHuidigeVector().getX()
					+ gridCoord.getX(), camera.getHuidigeVector().getY()
					+ gridCoord.getY());

			if (spel.plaatsPion(coord, zone)) {
				QLabel test = new QLabel();
				test.setPixmap(pixmap);
				layout.addWidget(test, row, col);
				test.show();
			}
		}

		protected void dragLeaveEvent(QDragLeaveEvent event) {
			clearGroen();
		}

		public boolean isGevuld() {
			return gevuld;
		}

		public void setGevuld(boolean gevuld) {
			this.gevuld = gevuld;
		}
	};

	private QWidget gridWidget;
	private QGridLayout gridLayout;
	private int rows = 7;
	private int columns = 9;

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super(spel);
		gridWidget = new QWidget();
		gridLayout = new QGridLayout(gridWidget);
		clearSpeelveld();
	}

	protected void clearSpeelveld() {
		gridWidget.layout().dispose();
		List<QObject> list = gridWidget.children();
		for (int i = list.size() - 1; i >= 0; --i) {
			QObject item = list.get(i);
			item.dispose();
		}
		gridLayout = new QGridLayout(gridWidget);

		gridLayout.setSpacing(0);
		QGraphicsScene scene;
		QtGraphicsView view;

		gridWidget.setGeometry(0, 0, 720, 540);
		gridWidget.setMaximumSize(new QSize(720, 540));
		gridWidget.setMinimumSize(new QSize(720, 540));

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				scene = new QGraphicsScene(gridLayout);
				view = new QtGraphicsView(scene, new Vector2D(i, j));
				gridLayout.addWidget(view, i, j, 1, 1);
			}
		}

		// camera dizzle
		camera.setMinVector(new Vector3D(-spel.getStapelSize(), -spel
				.getStapelSize(), 0));
		camera.setMaxVector(new Vector3D(spel.getStapelSize(), spel
				.getStapelSize(), 6));
		camera.setHuidigeVector(new Vector3D(-3, -4, 3));

		QPushButton buttonUp = new QPushButton("^", gridWidget);
		buttonUp.setGeometry(gridWidget.width() / 2 - 18, 0, 35, 25);
		buttonUp.clicked.connect(this, "cameraUp()");
		QPushButton buttonDown = new QPushButton("v", gridWidget);
		buttonDown.setGeometry(gridWidget.width() / 2 - 18,
				gridWidget.height() - 26, 35, 25);
		buttonDown.clicked.connect(this, "cameraDown()");
		QPushButton buttonLeft = new QPushButton("<", gridWidget);
		buttonLeft.setGeometry(0, gridWidget.height() / 2 - 13, 25, 35);
		buttonLeft.clicked.connect(this, "cameraLeft()");
		QPushButton buttonRight = new QPushButton(">", gridWidget);
		buttonRight.setGeometry(gridWidget.width() - 26,
				gridWidget.height() / 2 - 13, 25, 35);
		buttonRight.clicked.connect(this, "cameraRight()");

		QShortcut shortcut = new QShortcut(new QKeySequence("Up"), gridWidget);
		shortcut.activated.connect(this, "cameraUp()");
		shortcut = new QShortcut(new QKeySequence("Down"), gridWidget);
		shortcut.activated.connect(this, "cameraDown()");
		shortcut = new QShortcut(new QKeySequence("Left"), gridWidget);
		shortcut.activated.connect(this, "cameraLeft()");
		shortcut = new QShortcut(new QKeySequence("Right"), gridWidget);
		shortcut.activated.connect(this, "cameraRight()");
	}

	protected void initialiseerSpeelveld() {
		QTTegel startTegel = new QTTegel(spel.geefStartTegel(), spel);
		Vector2D gridCoord = new Vector2D((rows - 1) / 2, (columns - 1) / 2);
		voegTegelToeAanGrafischeLijst(startTegel.getTegel(),
				new Vector2D(0, 0), startTegel.getPixmap());
		((QtGraphicsView) gridLayout.itemAtPosition(gridCoord.getX(),
				gridCoord.getY()).widget()).scene().addPixmap(
				startTegel.getPixmap().scaled(73, 69));
		((QtGraphicsView) gridLayout.itemAtPosition(gridCoord.getX(),
				gridCoord.getY()).widget()).setGevuld(true);
	}

	public boolean voegTegelToe(Vector2D gridCoord, QPixmap pixmap) {
		String[] tegel = spel.vraagNieuweTegel();
		Vector2D coord = new Vector2D(camera.getHuidigeVector().getX()
				+ gridCoord.getX(), camera.getHuidigeVector().getY()
				+ gridCoord.getY());
		boolean isTegelGeplaatst = false;

		if (spel.isTegelPlaatsingGeldig(tegel, coord)) {
			tegel = spel.neemTegelVanStapel();
			spel.plaatsTegel(tegel, coord);
			voegTegelToeAanGrafischeLijst(tegel, coord, pixmap);
			((QtGraphicsView) gridLayout.itemAtPosition(gridCoord.getX(),
					gridCoord.getY()).widget()).scene().addPixmap(
					pixmap.scaled(((QtGraphicsView) gridLayout.itemAtPosition(
							gridCoord.getX(), gridCoord.getY()).widget())
							.width() - 5, ((QtGraphicsView) gridLayout
							.itemAtPosition(gridCoord.getX(), gridCoord.getY())
							.widget()).height() - 5));
			isTegelGeplaatst = true;
		}

		// altijd resetten naar 0 hier, vermits we met referenties zitten
		// te werken.
		tegel[2] = new String("0");
		return isTegelGeplaatst;
	}

	private void cameraUp() {
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() - 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());
		QGraphicsScene scene;
		QtGraphicsView view;

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraDown() {
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() + 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraLeft() {
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() - 1, camera.getHuidigeVector()
						.getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraRight() {
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() + 1, camera.getHuidigeVector()
						.getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	// nog een paar foutjes
	private void veranderZicht() {
		Vector2D startTegelPos = spel.getStartTegelPos();
		if (startTegelPos != null) {
			int offsetX = startTegelPos.getX();
			int offsetY = startTegelPos.getY();
			int startX, startY, i = 0, j = 0, y;
			int sizeX = gTegels.size();
			int sizeY;

			startX = offsetX + camera.getHuidigeVector().getX();
			startY = offsetY + camera.getHuidigeVector().getY();

			for (; i < rows && startX < 0; startX++, i++)
				for (y = 0; y < columns; y++) {
					((QtGraphicsView) gridLayout.itemAtPosition(i, y).widget())
							.removePixmap();
				}

			for (; i < rows; i++, startX++) {
				for (y = startY; j < columns && y < 0; y++) {
					((QtGraphicsView) gridLayout.itemAtPosition(i, j).widget())
							.removePixmap();
					j++;
				}

				if (startX < sizeX)
					sizeY = gTegels.get(startX).size();
				else
					sizeY = 0;
				for (; j < columns; j++, y++) {
					if (startX < sizeX && y < sizeY
							&& gTegels.get(startX).get(y) != null) {
						((QtGraphicsView) gridLayout.itemAtPosition(i, j)
								.widget()).setPixmap(((QTTegel) gTegels.get(
								startX).get(y)).getPixmap());
					} else {
						((QtGraphicsView) gridLayout.itemAtPosition(i, j)
								.widget()).removePixmap();
					}
				}
				j = 0;
			}
		}
	}

	// werkt
	private void kleurMogelijkhedenGroen() {
		String[] tegel = spel.vraagNieuweTegel();
		Vector2D coord = new Vector2D();
		Vector2D startTegelPos = spel.getStartTegelPos();

		if (startTegelPos != null) {
			int offsetX = startTegelPos.getX();
			int offsetY = startTegelPos.getY();
			int startX, startY, i = 0, j = 0, y;
			int sizeX = gTegels.size();
			int sizeY = getBiggestSize();

			startX = offsetX + camera.getHuidigeVector().getX();
			startY = offsetY + camera.getHuidigeVector().getY();

			for (; i < rows && startX < -1; startX++, i++)
				;

			for (; i < rows && startX <= sizeX; i++, startX++) {
				for (y = startY; j < columns && y < -1; y++, j++)
					;

				for (; j < columns && y <= sizeY; j++, y++) {
					coord.setXY(camera.getHuidigeVector().getX() + i, camera
							.getHuidigeVector().getY()
							+ j);
					if (y <= sizeY) {
						if (!((QtGraphicsView) gridLayout.itemAtPosition(i, j)
								.widget()).isGevuld()
								&& spel.isTegelPlaatsingGeldig(tegel, coord)) {
							((QtGraphicsView) gridLayout.itemAtPosition(i, j)
									.widget()).setForegroundBrush(new QBrush(
									new QColor(0, 255, 0, 127)));
						}
					}
				}
				j = 0;
			}
		}
	}

	private void clearGroen() {
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				((QtGraphicsView) gridLayout.itemAtPosition(i, j).widget())
						.setForegroundBrush(null);
	}

	public QWidget getGridWidget() {
		return gridWidget;
	}

	public void setGridWidget(QWidget gridWidget) {
		this.gridWidget = gridWidget;
	}

	// functies van tafel m.b.t. dubbele arraylist
	// werken hier op een qt dubbele arraylist
	// misschien tijdelijk

	public boolean voegTegelToeAanGrafischeLijst(String[] tegel,
			Vector2D coord, QPixmap pixmap) {
		// startTegel wordt gezet
		// coord maken niet uit startTegel staat op (0, 0)
		if (gTegels == null || gTegels.size() == 0) {
			gTegels = new ArrayList<ArrayList<GTegel>>();
			gTegels.add(new ArrayList<GTegel>());
			// TODO !!!!!!!!!!
			gTegels.get(0).add(new QTTegel(tegel, spel));
			startGTegel = new Vector2D(0, 0);
			return true;
		}

		int rij = startGTegel.getX() + coord.getX();
		int kolom = startGTegel.getY() + coord.getY();
		// mag de tegel hier gezet worden? M.a.w. zijn zijn buren geldig?
		// TODO

		ArrayList<GTegel> kolomVector;

		// boven of onder de starttegel
		if (rij == -1) {
			rij = 0;
			kolomVector = addRij(rij);
			startGTegel.setX(startGTegel.getX() + 1);
		} else if (rij == gTegels.size()) {
			kolomVector = addRij(rij);
		} else { // toevoegen in een bestaande rij
			kolomVector = gTegels.get(rij);
		}

		// links of rechts van de starttegel
		if (kolom == -1) {
			adjustAll(rij, kolom);
			startGTegel.setY(startGTegel.getY() + 1);
			kolom = (kolom < 0) ? 0 : kolom;
		} else if (kolom > gTegels.get(rij).size()) {
			addSpacers(rij, kolom);
		}

		if (kolom < kolomVector.size() && kolomVector.get(kolom) == null) {
			kolomVector.remove(kolom);
		}

		kolomVector.add(kolom, new QTTegel(tegel, spel, pixmap));
		// // TODO functie update landsdelen schrijven
		// updateLandsdelen(rij, kolom);

		return true;
	}

	@Override
	protected void updateSpeelveld() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void voegTegelToe(String[] tegel, Vector2D coord) {
		// TODO Auto-generated method stub

	}

	public void update(Observable arg0, Object arg1) {
		if (!arg1.equals(true)) {
			return;
		}

		clearSpeelveld();
		initialiseerSpeelveld();

		this.gridWidget.show();
	}
}

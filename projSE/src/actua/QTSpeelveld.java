package actua;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragLeaveEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QShortcut;
import com.trolltech.qt.gui.QWidget;

public class QTSpeelveld extends GSpeelveld {

	private class VeldWidget extends QWidget {
		private QGridLayout gridLayout;
		private QTSpeelveld parent;

		public VeldWidget(QTSpeelveld parent) {
			gridLayout = new QGridLayout();
			this.parent = parent;
			this.setAcceptDrops(true);
		}

		public QTSpeelveld getParent() {
			return parent;
		}

		public void init() {
			if (layout() != null) {
				layout().dispose();
				List<QObject> list = children();
				for (int i = list.size() - 1; i >= 0; --i) {
					QObject item = list.get(i);
					item.dispose();
				}
			}
			gridLayout = new QGridLayout(this);
			setUpdatesEnabled(true);
			gridLayout.setSpacing(0);

			// elke tegel is 90x90... zodus
			setGeometry(0, 0, 810, 630);
			setMaximumSize(new QSize(810, 630));
			setMinimumSize(new QSize(810, 630));

			int rasterrow = upperLeftRow;
			for (int i = 0; i < rows; i++) {
				int rastercol = upperLeftCol;
				for (int j = 0; j < columns; j++) {
					QTTegel tegel = new QTTegel(rasterrow, rastercol);
					mainWidget.insertTegel(tegel, i, j);
					rastercol++;
				}
				rasterrow++;
			}
		}

		public void insertTegel(QTTegel tegel, int row, int col) {
			if (gridLayout.itemAtPosition(row, col) != null) {
				QWidget temp = (QWidget) gridLayout.itemAtPosition(row, col);
				gridLayout.removeWidget(temp);
			}

			gridLayout.addWidget(tegel.getGraphicsView(), row, col);
		}

		public void setGroen(int row, int col) {
			QGraphicsView view = (QGraphicsView) gridLayout.itemAtPosition(row,
					col);
			view.setForegroundBrush(new QBrush(new QColor(0, 255, 0, 127)));
		}

		public void clearGroen() {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					QGraphicsView view = (QGraphicsView) gridLayout
							.itemAtPosition(i, j);
					view.setForegroundBrush(null);
				}
			}
		}

		protected void dragEnterEvent(QDragEnterEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")) {
				parent.kleurMogelijkhedenGroen();
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

		protected void dragLeaveEvent(QDragLeaveEvent event) {
			parent.clearGroen();
		}

		protected void dropEvent(QDropEvent event) {
			boolean tegel = event.mimeData().hasFormat(
					"application/x-dnditemdata");

			int x = event.pos().x();
			int y = event.pos().y();
			x = x / 90;
			y = y / 90;

			if (tegel) {
				voegTegelToe(new Vector2D(y, x));
				clearGroen();
			}

			if (tegel) {
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

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super(spel);
		mainWidget = new VeldWidget(this);
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
		QTTegel startTegel = new QTTegel(spel.geefStartTegel(), spel, 0, 0);
		mainWidget.insertTegel(startTegel, (rows - 1) / 2, (columns - 1) / 2);
		gelegdeTegels.add(startTegel);
	}

	private void cameraUp() {
		upperLeftRow--;
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() - 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraDown() {
		upperLeftRow++;
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() + 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraLeft() {
		upperLeftCol--;
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() - 1, camera.getHuidigeVector()
						.getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void cameraRight() {
		upperLeftCol++;
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() + 1, camera.getHuidigeVector()
						.getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			veranderZicht();
	}

	private void veranderZicht() {
		for (int i = upperLeftCol; i < upperLeftCol + columns; ++i)
			for (int j = upperLeftRow; j < upperLeftRow + rows; ++j) {
				QTTegel tegel = getTegel(j, i);
				if (tegel != null)
					mainWidget.insertTegel(tegel, j - upperLeftRow, i
							- upperLeftCol);
				else
					mainWidget.insertTegel(new QTTegel(j, i), j - upperLeftRow,
							i - upperLeftCol);
			}

		this.mainWidget.show();
	}

	private boolean voegTegelToe(Vector2D gridCoord) {
		String[] tegel = spel.vraagNieuweTegel();
		Vector2D coord = new Vector2D(upperLeftCol + gridCoord.getX(),
				upperLeftRow + gridCoord.getY());
		boolean isTegelGeplaatst = false;

		if (spel.isTegelPlaatsingGeldig(tegel, coord)) {
			tegel = spel.neemTegelVanStapel();
			spel.plaatsTegel(tegel, coord);
			QTTegel qTegel = new QTTegel(tegel, spel);
			qTegel.setCoords(coord.getY(), coord.getX());
			mainWidget.insertTegel(qTegel, gridCoord.getY(), gridCoord.getX());
			gelegdeTegels.add(qTegel);
			isTegelGeplaatst = true;
		}

		// altijd resetten naar 0 hier
		// we werken met referenties
		tegel[2] = new String("0");
		return isTegelGeplaatst;
	}

	private void kleurMogelijkhedenGroen() {
		String[] tegel = spel.vraagNieuweTegel();
		Vector2D startTegelPos = spel.getStartTegelPos();

		if (startTegelPos != null) {
			for (int row = upperLeftRow; row < upperLeftRow + rows; ++row)
				for (int col = upperLeftCol; col < upperLeftCol + columns; ++col)
					if (spel.isTegelPlaatsingGeldig(tegel, new Vector2D(col,
							row))) {
						mainWidget.setGroen(row - upperLeftRow, col
								- upperLeftCol);
					}
		}
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

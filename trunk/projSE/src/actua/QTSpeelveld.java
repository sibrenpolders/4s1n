package actua;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QObject;
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
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QShortcut;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.core.Qt;

public class QTSpeelveld extends GSpeelveld {
	private static final QPixmap pixmap = new QPixmap("src/icons/background.xpm");
	private class VeldWidget extends QGraphicsView {
		private QGridLayout gridLayout;
		private Vector2D coord;
		public VeldWidget(int width, int height, Vector2D coord) {
			gridLayout = new QGridLayout();
			gridLayout.setSpacing(0);
			setLayout(gridLayout);
			setScene(new QGraphicsScene());
			setAcceptDrops(true);
			resize(width, height);
			this.coord = new Vector2D(coord);
			init();
		}

		public void init() {
			setBackgroundBrush(new QBrush(pixmap));
			setUpdatesEnabled(true);
			
			// elke tegel is 90x90... zodus
			// waarschijnlijk een stomme naam. Maar ik dacht dat _ voor een functie betekende dat het een
			// member functie was ofzo...
			_resize(810, 630);
		}

		public void setGroen() {
			scene().setForegroundBrush(new QBrush(new QColor(0, 255, 0, 127)));
			((QWidget)this).update();
		}

		public void clearGroen() {
			scene().setForegroundBrush(null);
			((QWidget)this).update();
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
			} else {
				event.ignore();
			}
		}

		protected void dragMoveEvent(QDragMoveEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")) {
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
			clearAllGroen();
		}

		protected void dropEvent(QDropEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")) {
				QByteArray itemData = event.mimeData().data("application/x-dnditemdata");
	            QDataStream dataStream = new QDataStream(itemData, QIODevice.OpenModeFlag.ReadOnly);
				QPixmap pixmap = new QPixmap();
				pixmap.readFrom(dataStream);
				
				Vector2D tegelCoord = new Vector2D(coord.getX() - startTegelPos.getX(),
						coord.getY() - startTegelPos.getY());
				voegTegelToe(tegelCoord, pixmap);
				clearAllGroen();

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
	}
	
	private ArrayList<QTTegel> gelegdeTegels;
	private static int DEFAULT_ROWS = 7;
	private static int DEFAULT_COLS = 9;
	private static int DEFAULT_ULROW = -3;
	private static int DEFAULT_ULCOL = -4;
	private int rows = DEFAULT_ROWS;
	private int columns = DEFAULT_COLS;
	private int upperLeftRow = DEFAULT_ULROW;
	private int upperLeftCol = DEFAULT_ULCOL;
	private QWidget gridWidget;
	private QGridLayout gridLayout;
	private Vector2D startTegelPos;
	private ArrayList<Vector2D> mogelijkeZetten;
	private boolean mogelijkeZettenBerekend = false;
	
	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super(spel);
		gridWidget = new QWidget();
		gridWidget.setMinimumSize(810, 630);
		gridWidget.setMaximumSize(810, 630);
		gridLayout = new QGridLayout(gridWidget);
		startTegelPos = new Vector2D((rows-1)/2, (columns-1)/2);
		mogelijkeZetten = new ArrayList<Vector2D>();
		clearSpeelveld();
		createEmptyTegels();
	}

	public QWidget getGridWidget() {
		return gridWidget;
	}

	private QTTegel getTegel(int row, int col) {
		for (int i = 0; i < gelegdeTegels.size(); ++i) {
			QTTegel result = (QTTegel) ((gelegdeTegels.get(i)));
			if (result.getCol() == col && result.getRow() == row)
				return result;
		}
		return null;
	}

	protected void clearSpeelveld() {
		upperLeftRow = DEFAULT_ULROW;
		upperLeftCol = DEFAULT_ULCOL;
		gelegdeTegels = new ArrayList<QTTegel>();
		clear();

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
		QTTegel tegel = this.getTegel(0, 0);
		if (tegel == null) {
			tegel = new QTTegel(spel, new Vector2D(0, 0));
			gelegdeTegels.add(tegel);
		}

		tegel.setPixmap(spel.geefStartTegel());
		veranderZicht();
	}

	private void cameraUp() {
		upperLeftRow--;
		startTegelPos.setX(startTegelPos.getX()+1);
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() - 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		veranderZicht();
	}

	private void cameraDown() {
		upperLeftRow++;
		startTegelPos.setX(startTegelPos.getX()-1);
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() + 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		veranderZicht();
	}

	private void cameraLeft() {
		upperLeftCol--;
		startTegelPos.setY(startTegelPos.getY()+1);
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() - 1, camera.getHuidigeVector()
						.getZ());

		veranderZicht();
	}

	private void cameraRight() {
		upperLeftCol++;
		startTegelPos.setY(startTegelPos.getY()-1);
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() + 1, camera.getHuidigeVector()
						.getZ());

		veranderZicht();
	}

	private void veranderZicht() {
		clear();
		createEmptyTegels();
		for (int i = 0; i < gelegdeTegels.size(); ++i) {
			QTTegel tegel = gelegdeTegels.get(i);
			int row = startTegelPos.getX() + tegel.getCol();
			int col = startTegelPos.getY() + tegel.getRow();
			
			if (col >= 0 && col < columns && row >= 0 && row < rows) {
				gridLayout.addWidget(tegel.getTegelView(), row, col, 1, 1);
			}
		}
	}

	private void kleurMogelijkhedenGroen() {
		ArrayList<Vector2D> _mogelijkeZetten = spel.geefMogelijkeZetten();
		//mogelijkeZetten = new ArrayList<Vector2D>();
		mogelijkeZetten.clear();
		
		Vector2D tmp;
		int row, col;		
		for (int i = 0; i < _mogelijkeZetten.size(); ++i) {
			tmp = _mogelijkeZetten.get(i);
			row = tmp.getX() + startTegelPos.getX()-spel.getStartTegelPos().getX();
			col = tmp.getY() + startTegelPos.getY()-spel.getStartTegelPos().getY();
			
			if (row >= 0 && row < rows && col >= 0 && col < columns) {
				mogelijkeZetten.add(new Vector2D(row, col));
			}
		}
//		mogelijkeZettenBerekend = true;
		
		setGroen();
	}

	private void setGroen() {
		if (mogelijkeZetten != null) {
			Vector2D tmp;
			VeldWidget widget;
			for (int i = 0; i < mogelijkeZetten.size(); ++i) {
				tmp = mogelijkeZetten.get(i);
				widget = (VeldWidget)gridLayout.itemAtPosition(tmp.getX(), tmp.getY()).widget();
				widget.setGroen();
			}
		}
	}
	
	public void clearAllGroen() {
		if (mogelijkeZetten != null) {
			Vector2D tmp;
			VeldWidget widget;
			for (int i = 0; i < mogelijkeZetten.size(); ++i) {
				tmp = mogelijkeZetten.get(i);
				widget = (VeldWidget)gridLayout.itemAtPosition(tmp.getX(), tmp.getY()).widget();
				widget.clearGroen();
			}
		}
		
//		mogelijkeZettenBerekend = false;
	}

	protected void updateSpeelveld() {
		this.veranderZicht();
	}

	private boolean voegTegelToe(Vector2D coord, QPixmap pixmap) {
		String[] tegel = spel.vraagNieuweTegel();
		boolean isTegelGeplaatst = false;

		if (spel.isTegelPlaatsingGeldig(tegel, coord)) {
			System.out.println("HERE");
			tegel = spel.neemTegelVanStapel();
			spel.plaatsTegel(tegel, coord);
			QTTegel qTegel = new QTTegel(tegel, spel, coord);
			qTegel.setPixmap(pixmap);
			
			Vector2D gridCoord = new Vector2D(coord.getX() + startTegelPos.getX(), 
					coord.getY() + startTegelPos.getY());
			gridLayout.addWidget(qTegel.getTegelView(), gridCoord.getX(), gridCoord.getY(), 1, 1);
			gelegdeTegels.add(qTegel);
			isTegelGeplaatst = true;
			spel.huidigeSpelerPlaatstTegel(true);
		}

		// altijd resetten naar 0 hier
		// we werken met referenties
		tegel[2] = new String("0");
		return isTegelGeplaatst;
	}

	public void update(Observable arg0, Object arg1) {
		if (!arg1.equals(true)) {
			return;
		}
//		} else if (arg1.equals("VERANDERZICHT")) {
//			veranderZicht();
//			return;
//		}

		clearSpeelveld();
		initialiseerSpeelveld();

		this.gridWidget.show();
	}

	private void createEmptyTegels() {
		int rasterrow = upperLeftRow;
		for (int i = 0; i < rows; i++) {
			int rastercol = upperLeftCol;
			for (int j = 0; j < columns; j++) {
				gridLayout.addWidget(new VeldWidget(90, 90, new Vector2D(i, j)), i, j, 1, 1);
				rastercol++;
			}
			rasterrow++;
		}			
	}
	
	public void clear() {
		if (gridLayout != null) {
			List<QObject> list = gridWidget.children();
			int start = list.size() - gelegdeTegels.size();
			int size = list.size();
			for (int i = start; i < size; ++i) {
				list.get(start).dispose();
				list.remove(start);
			}
		}
	}
	
	@Override
	// TODO nutteloze functie???
	protected void voegTegelToe(String[] tegel, Vector2D coord) {
		// TODO Auto-generated method stub
		
	}
}

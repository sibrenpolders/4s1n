package GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import Core.Spel;
import Core.Vector2D;
import Core.Vector3D;
import Opties.OptieVerwerker;
import Spelers.SpelBeurtResultaat;


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
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QShortcut;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.core.Qt;

public class QTSpeelveld extends GSpeelveld {
	private final QPixmap achtergrondPixmap = new QPixmap(this.achtergrond);

	private class VeldWidget extends QGraphicsView {
		private Vector2D coord;

		public VeldWidget(int width, int height, Vector2D coord) {
			setScene(new QGraphicsScene());
			setAcceptDrops(true);
			_resize(width, height);
			this.coord = new Vector2D(coord);
			init();
		}

		private void _resize(int width, int height) {
			setGeometry(0, 0, width, height);
			setMaximumSize(new QSize(width, height));
			setMinimumSize(new QSize(width, height));
		}

		private void init() {
			setBackgroundBrush(new QBrush(achtergrondPixmap));
			setUpdatesEnabled(true);
		}

		public int getCol() {
			return coord.getX();
		}

		public int getRow() {
			return coord.getY();
		}

		public void setGroen() {
			scene().setForegroundBrush(new QBrush(new QColor(0, 255, 0, 127)));
			this.update();
		}

		public void clearGroen() {
			scene().setForegroundBrush(null);
			this.update();
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
				QByteArray itemData = event.mimeData().data(
						"application/x-dnditemdata");
				QDataStream dataStream = new QDataStream(itemData,
						QIODevice.OpenModeFlag.ReadOnly);
				QPixmap pixmap = new QPixmap();
				pixmap.readFrom(dataStream);

				voegTegelToe(coord, pixmap);
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
	}

	private static int DEFAULT_ROWS = 7;
	private static int DEFAULT_COLS = 9;
	private static int DEFAULT_ULROW = -3;
	private static int DEFAULT_ULCOL = -4;
	private int rows = DEFAULT_ROWS;
	private int columns = DEFAULT_COLS;
	private ArrayList<QTTegel> gelegdeTegels;
	private ArrayList<VeldWidget> achtergrondTegels;
	private QWidget gridWidget;
	private QGridLayout completeGridLayout;
	private QGridLayout gridLayout; // layout voor de tegels
	private QGridLayout rowNummerkes;
	private QGridLayout colNummerkes;
	private ArrayList<QLabel> colLabels;
	private ArrayList<QLabel> rowLabels;
	private ArrayList<Vector2D> mogelijkeZetten;

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super(spel);

		mogelijkeZetten = new ArrayList<Vector2D>();
		gelegdeTegels = new ArrayList<QTTegel>();
		achtergrondTegels = new ArrayList<VeldWidget>();

		init();
		veranderZicht();
	}

	private void init() {
		gridWidget = new QWidget();
		gridWidget.setMinimumSize(DEFAULT_COLS * 90 + 80,
				DEFAULT_ROWS * 90 + 80);
		gridWidget.setMaximumSize(DEFAULT_COLS * 90 + 80,
				DEFAULT_ROWS * 90 + 80);
		completeGridLayout = new QGridLayout(gridWidget);
		completeGridLayout.setSpacing(0);
		completeGridLayout.setAlignment(new Qt.Alignment(
				Qt.AlignmentFlag.AlignCenter));

		rowLabels = new ArrayList<QLabel>();
		QWidget rN = new QWidget();
		rN.setMinimumSize(15, DEFAULT_ROWS * 90);
		rN.setMaximumSize(15, DEFAULT_ROWS * 90);
		rowNummerkes = new QGridLayout(rN);
		rowNummerkes.setSpacing(0);
		rowNummerkes
				.setAlignment(new Qt.Alignment(Qt.AlignmentFlag.AlignCenter));
		completeGridLayout.addWidget(rN, 2, 1);
		for (int i = 0; i < DEFAULT_ROWS; ++i) {
			QLabel label = new QLabel(Integer.toString(i));
			label.setMinimumSize(10, 90);
			label.setMaximumSize(10, 90);
			rowNummerkes.addWidget(label, i, 0);
			rowLabels.add(label);
		}

		colLabels = new ArrayList<QLabel>();
		QWidget cN = new QWidget();
		cN.setMinimumSize(DEFAULT_COLS * 90, 15);
		cN.setMaximumSize(DEFAULT_COLS * 90, 15);
		colNummerkes = new QGridLayout(cN);
		colNummerkes.setSpacing(0);
		colNummerkes
				.setAlignment(new Qt.Alignment(Qt.AlignmentFlag.AlignCenter));
		completeGridLayout.addWidget(cN, 1, 2);
		for (int i = 0; i < DEFAULT_COLS; ++i) {
			QLabel label = new QLabel(Integer.toString(i));
			label.setMinimumSize(90, 10);
			label.setMaximumSize(90, 10);
			colNummerkes.addWidget(label, 0, i);
			colLabels.add(label);
		}

		QWidget tegels = new QWidget();
		tegels.setMinimumSize(DEFAULT_COLS * 90, DEFAULT_ROWS * 90);
		tegels.setMaximumSize(DEFAULT_COLS * 90, DEFAULT_ROWS * 90);
		gridLayout = new QGridLayout(tegels);
		gridLayout.setSpacing(0);
		completeGridLayout.addWidget(tegels, 2, 2);

		QPushButton buttonUp = new QPushButton("^", gridWidget);
		buttonUp.setMinimumSize(DEFAULT_COLS * 90, 25);
		buttonUp.setMaximumSize(DEFAULT_COLS * 90, 25);
		buttonUp.clicked.connect(this, "cameraUp()");
		completeGridLayout.addWidget(buttonUp, 0, 2);

		QPushButton buttonDown = new QPushButton("v", gridWidget);
		buttonDown.setMinimumSize(DEFAULT_COLS * 90, 25);
		buttonDown.setMaximumSize(DEFAULT_COLS * 90, 25);
		buttonDown.clicked.connect(this, "cameraDown()");
		completeGridLayout.addWidget(buttonDown, 3, 2);

		QPushButton buttonLeft = new QPushButton("<", gridWidget);
		buttonLeft.setMinimumSize(25, DEFAULT_ROWS * 90);
		buttonLeft.setMaximumSize(25, DEFAULT_ROWS * 90);
		buttonLeft.clicked.connect(this, "cameraLeft()");
		completeGridLayout.addWidget(buttonLeft, 2, 0);

		QPushButton buttonRight = new QPushButton(">", gridWidget);
		buttonRight.setMinimumSize(25, DEFAULT_ROWS * 90);
		buttonRight.setMaximumSize(25, DEFAULT_ROWS * 90);
		buttonRight.clicked.connect(this, "cameraRight()");
		completeGridLayout.addWidget(buttonRight, 2, 3);

		QShortcut shortcut = new QShortcut(new QKeySequence("Up"), gridWidget);
		shortcut.activated.connect(this, "cameraUp()");
		shortcut = new QShortcut(new QKeySequence("Down"), gridWidget);
		shortcut.activated.connect(this, "cameraDown()");
		shortcut = new QShortcut(new QKeySequence("Left"), gridWidget);
		shortcut.activated.connect(this, "cameraLeft()");
		shortcut = new QShortcut(new QKeySequence("Right"), gridWidget);
		shortcut.activated.connect(this, "cameraRight()");
	}

	// GETTERS

	public QWidget getGridWidget() {
		return gridWidget;
	}

	private QTTegel getQTTegel(int row, int col) {
		for (int i = 0; i < gelegdeTegels.size(); ++i) {
			QTTegel result = (QTTegel) ((gelegdeTegels.get(i)));
			if (result.getCol() == col && result.getRow() == row)
				return result;
		}
		return null;
	}

	private VeldWidget getAchtergrondTegel(int row, int col) {
		for (int i = 0; i < achtergrondTegels.size(); ++i) {
			VeldWidget result = (VeldWidget) ((achtergrondTegels.get(i)));
			if (result.getCol() == col && result.getRow() == row)
				return result;
		}
		return null;
	}

	private VeldWidget removeAchtergrondTegel(int row, int col) {
		for (int i = 0; i < achtergrondTegels.size(); ++i) {
			VeldWidget result = (VeldWidget) ((achtergrondTegels.get(i)));
			if (result.getCol() == col && result.getRow() == row) {
				achtergrondTegels.remove(i);
				return result;
			}
		}
		return null;
	}

	// LAYOUT- EN WIDGETCONSTRUCTIE

	protected void clearSpeelveld() {
		achtergrondTegels.clear();
		gelegdeTegels.clear();

		clear();

		// camera dizzle
		camera.setMinVector(new Vector3D(-spel.getStapelSize(), -spel
				.getStapelSize(), 0));
		camera.setMaxVector(new Vector3D(spel.getStapelSize(), spel
				.getStapelSize(), 6));
		camera.setHuidigeVector(new Vector3D(DEFAULT_ULCOL, DEFAULT_ULROW, 3)); // linkerboventegel
	}

	public void clear() {
		if (gridLayout != null
				&& (achtergrondTegels.size() + gelegdeTegels.size() > 0)) {
			List<QObject> list = gridLayout.children();
			for (int i = 0; i < list.size();) {
				list.remove(0);
			}

			for (int i = 0; i < gelegdeTegels.size(); ++i)
				gelegdeTegels.get(i).getTegelView().hide();

			for (int i = 0; i < achtergrondTegels.size(); ++i)
				achtergrondTegels.get(i).hide();
		}
	}

	private void createEmptyTegels() {
		for (int i = 0; i < rows; i++) {
			int row = i + camera.getHuidigeVector().getY();
			for (int j = 0; j < columns; j++) {
				int col = j + camera.getHuidigeVector().getX();
				VeldWidget w = getAchtergrondTegel(row, col);
				if (w == null) {
					w = new VeldWidget(90, 90, new Vector2D(col, row));
					achtergrondTegels.add(w);
				}
				gridLayout.addWidget(w, i, j, 1, 1);
				w.show();
			}
		}
	}

	protected void initialiseerSpeelveld() {
		QTTegel tegel = this.getQTTegel(0, 0);
		if (tegel == null) {
			String[] stegel = spel.geefStartTegel();
			tegel = new QTTegel(stegel, spel, new Vector2D(0, 0));
			gelegdeTegels.add(tegel);
		}

		tegel.setPixmap(spel.geefStartTegel());
		veranderZicht();
	}

	// HINTING

	private void kleurMogelijkhedenGroen() {
		ArrayList<Vector2D> _mogelijkeZetten = spel
				.geefMogelijkeZettenVoorTegel();
		mogelijkeZetten.clear();

		Vector2D tmp;
		int row, col;
		for (int i = 0; i < _mogelijkeZetten.size(); ++i) {
			tmp = _mogelijkeZetten.get(i);
			row = tmp.getX();
			col = tmp.getY();
			if (getAchtergrondTegel(row, col) != null)
				getAchtergrondTegel(row, col).setGroen();
			mogelijkeZetten.add(new Vector2D(col, row));
		}
	}

	private void clearAllGroen() {
		if (mogelijkeZetten != null) {
			Vector2D tmp;
			for (int i = 0; i < mogelijkeZetten.size(); ++i) {
				tmp = mogelijkeZetten.get(i);
				if (getAchtergrondTegel(tmp.getY(), tmp.getX()) != null)
					getAchtergrondTegel(tmp.getY(), tmp.getX()).clearGroen();
			}
			mogelijkeZetten.clear();
		}
	}

	// QTTEGEL TOEVOEGEN

	private boolean voegTegelToe(Vector2D coord, QPixmap pixmap) {
		String[] tegel = spel.vraagNieuweTegel();
		boolean isTegelGeplaatst = false;

		Vector2D lompeCoord = new Vector2D(coord.getY(), coord.getX());

		if (spel.isTegelPlaatsingGeldig(tegel, lompeCoord)) {
			clearForegroundGelegdeTegels();
			tegel = spel.neemTegelVanStapel();
			spel.plaatsTegel(tegel, lompeCoord);
			QTTegel qTegel = new QTTegel(tegel, spel, coord);
			qTegel.setPixmap(pixmap);

			Vector2D gridCoord = new Vector2D(coord.getX()
					- camera.getHuidigeVector().getX(), coord.getY()
					- camera.getHuidigeVector().getY());
			gelegdeTegels.add(qTegel);
			preprocessSwitchAchtergrondTegelForTegel(qTegel);
			gridLayout.addWidget(qTegel.getTegelView(), gridCoord.getY(),
					gridCoord.getX(), 1, 1);
			qTegel.getTegelView().show();

			isTegelGeplaatst = true;
		}

		// altijd resetten naar 0 hier
		// we werken met referenties
		tegel[2] = new String("0");
		return isTegelGeplaatst;
	}

	protected void voegTegelToeNaAIZet(SpelBeurtResultaat result) {
		Vector2D plaatsingTegel = result.getPlaatsTegel();
		String[] tegel = result.getTegel();

		int row = plaatsingTegel.getX();
		int col = plaatsingTegel.getY();

		QTTegel qTegel = new QTTegel(tegel, spel, new Vector2D(col, row));

		gelegdeTegels.add(qTegel);

		preprocessSwitchAchtergrondTegelForTegel(qTegel);
		gridLayout.addWidget(qTegel.getTegelView(), row
				- camera.getHuidigeVector().getY(), col
				- camera.getHuidigeVector().getX(), 1, 1);
		qTegel.update();

		if (row - camera.getHuidigeVector().getY() >= 0
				&& row - camera.getHuidigeVector().getY() < rows
				&& col - camera.getHuidigeVector().getX() >= 0
				&& col - camera.getHuidigeVector().getX() < columns)
			qTegel.getTegelView().show();
		else
			qTegel.getTegelView().hide();

		qTegel.getTegelView().setForegroundBrush(
				new QBrush(getForegroundBrushSpelerKleur(result.getPion())));
	}

	private void preprocessSwitchAchtergrondTegelForTegel(QTTegel tegel) {
		if (tegel != null) {
			VeldWidget achtergrond = removeAchtergrondTegel(tegel.getRow(),
					tegel.getCol());
			if (null != achtergrond) {
				gridLayout.removeWidget(achtergrond);
				achtergrond.hide();
			}
		}
	}

	private QColor getForegroundBrushSpelerKleur(char kleur) {
		switch (kleur) {
		case Spel.ROOD:
			return new QColor(255, 0, 0, 100);
		case Spel.BLAUW:
			return new QColor(0, 0, 255, 100);
		case Spel.GEEL:
			return new QColor(255, 255, 0, 100);
		case Spel.WIT:
			return new QColor(255, 255, 255, 100);
		case Spel.ORANJE:
			return new QColor(255, 160, 0, 100);
		default:
			return new QColor(236, 233, 216, 100);
		}
	}

	private void clearForegroundGelegdeTegels() {
		for (int i = 0; i < gelegdeTegels.size(); ++i)
			gelegdeTegels.get(i).getTegelView().setForegroundBrush(null);
	}

	// CAMERA-functies

	@SuppressWarnings("unused")
	private void cameraUp() {
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() - 1, camera.getHuidigeVector()
						.getZ());
		if (camera.isBewegingGeldig(nieuwePositie))
			camera.veranderStandpunt(nieuwePositie);

		veranderZicht();
	}

	@SuppressWarnings("unused")
	private void cameraDown() {
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() + 1, camera.getHuidigeVector()
						.getZ());
		if (camera.isBewegingGeldig(nieuwePositie))
			camera.veranderStandpunt(nieuwePositie);

		veranderZicht();
	}

	@SuppressWarnings("unused")
	private void cameraLeft() {
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() - 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());
		if (camera.isBewegingGeldig(nieuwePositie))
			camera.veranderStandpunt(nieuwePositie);

		veranderZicht();
	}

	@SuppressWarnings("unused")
	private void cameraRight() {
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() + 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());
		if (camera.isBewegingGeldig(nieuwePositie))
			camera.veranderStandpunt(nieuwePositie);

		veranderZicht();
	}

	protected void veranderZicht() {
		clear();
		createEmptyTegels();

		for (int i = 0; i < rows; ++i) {
			int row = i + camera.getHuidigeVector().getY();
			for (int j = 0; j < columns; ++j) {
				int col = j + camera.getHuidigeVector().getX();
				QTTegel tegel = getQTTegel(row, col);
				if (tegel != null) {
					preprocessSwitchAchtergrondTegelForTegel(tegel);
					gridLayout.addWidget(tegel.getTegelView(), i, j, 1, 1);
					tegel.update();
					tegel.getTegelView().show();
				}
			}
		}

		for (int i = 0; i < colLabels.size(); ++i) {
			String s = new String(Integer.toString(camera.getHuidigeVector()
					.getX()
					+ i));
			colLabels.get(i).setAlignment(
					new Qt.Alignment(Qt.AlignmentFlag.AlignCenter));
			colLabels.get(i).setText(s);
		}

		for (int i = 0; i < rowLabels.size(); ++i) {
			String s = new String(Integer.toString(camera.getHuidigeVector()
					.getY()
					+ i));
			rowLabels.get(i).setAlignment(
					new Qt.Alignment(Qt.AlignmentFlag.AlignCenter));
			rowLabels.get(i).setText(s);
		}

		gridWidget.update();
	}

	protected void updateSpeelveld() {
		this.veranderZicht();
	}

	public void update(Observable arg0, Object arg1) {
		if (arg1.equals(true)) {
			clearSpeelveld();
			initialiseerSpeelveld();

			this.gridWidget.show();
		} else if (((String) arg1).compareTo(Spel.HUIDIGESPELERVERANDERD) == 0) {
			ArrayList<SpelBeurtResultaat> result = spel.geefResultaatAI();
			clearForegroundGelegdeTegels();
			for (int i = 0; i < result.size(); i++)
				if (result.get(i) != null
						&& result.get(i).getProcessed() == false) {
					voegTegelToeNaAIZet(result.get(i));
					result.get(i).toggleProcessed();
				}
		} else if (((String) arg1).compareTo(Spel.SPELGEDAAN) == 0) {
			char w = spel.geefWinnaar();
			long score = spel.geefSpelerScore(w);
			String naam = spel.geefSpelerNaam(w);

			String text = "";
			if (score == 0)
				text += "Er zijn geen winnaars in dit spel.";
			else
				text += "Proficiat " + naam + ", U heeft gewonnen !";

			QMessageBox box = new QMessageBox();
			box.setWindowTitle("Einde Spel");
			box.setText(text);
			box.show();
		}
	}
}
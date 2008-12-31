package actua;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import com.trolltech.qt.core.QObject;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QShortcut;
import com.trolltech.qt.gui.QWidget;

public class QTSpeelveld extends GSpeelveld {

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

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super(spel);
		gridWidget = new QWidget();
		gridLayout = new QGridLayout(gridWidget);
		clearSpeelveld();
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

		gridWidget.layout().dispose();
		List<QObject> list = gridWidget.children();
		for (int i = list.size() - 1; i >= 0; --i) {
			QObject item = list.get(i);
			item.dispose();
		}

		gridLayout = new QGridLayout(gridWidget);
		gridLayout.setSpacing(0);
		gridWidget.setGeometry(0, 0, 810, 630);
		gridWidget.setMaximumSize(new QSize(810, 630));
		gridWidget.setMinimumSize(new QSize(810, 630));

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				QTTegel tegel = new QTTegel(this, spel, new Vector2D(j
						+ upperLeftCol, i + upperLeftRow));
				gelegdeTegels.add(tegel);

				gridLayout.addWidget(tegel.getGraphicsView(this), i, j, 1, 1);
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
		QTTegel tegel = this.getTegel(0, 0);
		if (tegel == null) {
			tegel = new QTTegel(this, spel, new Vector2D(0, 0));
			gelegdeTegels.add(tegel);
		}

		tegel.setPixmap(spel.geefStartTegel());
		veranderZicht();
	}

	public boolean voegTegelToe(Vector2D gridCoord, QPixmap pixmap) {
		String[] tegel = spel.vraagNieuweTegel();
		boolean isTegelGeplaatst = false;

		if (spel.isTegelPlaatsingGeldig(tegel, gridCoord)) {
			tegel = spel.neemTegelVanStapel();
			spel.plaatsTegel(tegel, gridCoord);
			QTTegel t = getTegel(gridCoord.getY(), gridCoord.getX());
			t.setTegel(tegel);
			t.setPixmap(pixmap);
			isTegelGeplaatst = true;
		}

		// altijd resetten naar 0 hier
		// we werken met referenties
		tegel[2] = new String("0");
		return isTegelGeplaatst;
	}

	private void cameraUp() {
		upperLeftRow--;
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() - 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			this.notifyObservers("VERANDERZICHT");
	}

	private void cameraDown() {
		upperLeftRow++;
		Vector3D nieuwePositie = new Vector3D(
				camera.getHuidigeVector().getX() + 1, camera.getHuidigeVector()
						.getY(), camera.getHuidigeVector().getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			this.notifyObservers("VERANDERZICHT");
	}

	private void cameraLeft() {
		upperLeftCol--;
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() - 1, camera.getHuidigeVector()
						.getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			this.notifyObservers("VERANDERZICHT");
	}

	private void cameraRight() {
		upperLeftCol++;
		Vector3D nieuwePositie = new Vector3D(camera.getHuidigeVector().getX(),
				camera.getHuidigeVector().getY() + 1, camera.getHuidigeVector()
						.getZ());

		if (camera.veranderStandpunt(nieuwePositie))
			this.notifyObservers("VERANDERZICHT");
	}

	private void veranderZicht() {
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < columns; j++)
				gridLayout.removeItem(gridLayout.itemAtPosition(i, j));

		for (int i = gelegdeTegels.size() - 1; i >= 0; --i)
			if (gelegdeTegels.get(i).getCurrTegelView(this) != null)
				gelegdeTegels.get(i).getCurrTegelView(this).dispose();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				QTTegel tegel = this.getTegel(i + upperLeftRow, j
						+ upperLeftCol);
				if (tegel == null) {
					tegel = new QTTegel(this, spel, new Vector2D(j
							+ upperLeftCol, i + upperLeftRow));
					gelegdeTegels.add(tegel);
				}
				gridLayout.addWidget(tegel.getGraphicsView(this), i, j, 1, 1);
			}
		}
	}

	public void kleurMogelijkhedenGroen() {
		String[] tegel = spel.vraagNieuweTegel();
		for (int i = 0; i < gelegdeTegels.size(); ++i) {
			QTTegel t = gelegdeTegels.get(i);
			if (spel.isTegelPlaatsingGeldig(tegel, t
					.getCoordsRelativeToStartTegel()))
				t.getCurrTegelView(this).kleurGroen();
		}
	}

	public void clearGroen() {
		for (int i = 0; i < gelegdeTegels.size(); ++i)
			gelegdeTegels.get(i).getCurrTegelView(this).clearGroen();
	}

	protected void updateSpeelveld() {
		this.veranderZicht();
	}

	protected void voegTegelToe(String[] tegel, Vector2D coord) {
		;
	}

	public void update(Observable arg0, Object arg1) {
		if (!arg1.equals(true)) {
			return;
		} else if (arg1.equals("VERANDERZICHT")) {
			veranderZicht();
			return;
		}

		clearSpeelveld();
		initialiseerSpeelveld();

		this.gridWidget.show();
	}
}

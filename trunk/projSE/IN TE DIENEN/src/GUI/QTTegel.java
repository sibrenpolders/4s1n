package GUI;

import java.util.ArrayList;

import Core.Spel;
import Core.Vector2D;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QRectF;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QDragEnterEvent;
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
import com.trolltech.qt.gui.QWidget;

public class QTTegel extends GTegel {

	public class tegelView extends QGraphicsView {
		private QGridLayout gridLayout;
		// voor de pionnen te kunnen disposen en zo
		private ArrayList<QWidget> widgets;

		public tegelView() {
			setScene(new QGraphicsScene());
			init();
			scene().setForegroundBrush(null);
			update();

		}

		private void init() {
			widgets = new ArrayList<QWidget>();
			setGeometry(0, 0, 90, 90);
			setMaximumSize(90, 90);
			setMinimumSize(90, 90);
			gridLayout = new QGridLayout();
			gridLayout.setSpacing(0);
			setLayout(gridLayout);
			setAcceptDrops(true);
			createEmptyLayout();
		}

		private void createEmptyLayout() {
			for (int i = 0; i < NB_ROWS; ++i) {
				for (int j = 0; j < NB_COLS; ++j) {
					gridLayout.addItem(new QSpacerItem(30, 18), i, j, 1, 1);
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
				int row = event.pos().y() / 18;
				int col = event.pos().x() / 30;
				short zone = getLandsdeelZoneVoorRowCol(row, col);

				QByteArray itemData = event.mimeData().data(
						"application/x-pionitemdata");
				QDataStream dataStream = new QDataStream(itemData,
						QIODevice.OpenModeFlag.ReadOnly);
				QPixmap pionpixmap = new QPixmap();
				pionpixmap.readFrom(dataStream);

				Vector2D lompeCoord = new Vector2D(tegelCoord.getY(),
						tegelCoord.getX());
				if (spel.plaatsPion(lompeCoord, zone)) {
					plaatsPionInSectie(row, col, spel.geefHuidigeSpeler());
					updateTegel();
				}
			} else {
				event.ignore();
			}
		}

		private void updateTegel() {
			for (int i = 0; i < widgets.size();) {
				QWidget w = widgets.get(0);
				gridLayout.removeWidget(w);
				widgets.remove(w);
				w.hide();
				w.dispose();
			}
			widgets.clear();

			for (int i = 0; i < NB_ROWS; ++i) {
				for (int j = 0; j < NB_COLS; ++j) {
					if (geefPionInSectie(i, j) != 0) {
						QLabel label = new QLabel();
						label.setMaximumSize(12, 12);
						label.setMinimumSize(12, 12);
						label.setPixmap(getPionPixmap(geefPionInSectie(i, j)));
						gridLayout.addWidget(label, i, j, 1, 1);
						widgets.add(label);
					} else {
						gridLayout.addItem(new QSpacerItem(30, 18), i, j, 1, 1);
					}
				}
			}

			this.update();
		}

		private QPixmap getPionPixmap(char kleur) {
			return new QPixmap("icons/" + kleur + ".png");
		}
	}

	private QPixmap pixmap;
	private tegelView view;

	public QTTegel(String[] tegel, Spel spel, Vector2D tegelCoord) {
		super(tegel, spel, tegelCoord);
		pixmap = new QPixmap(90, 90);
		kiesAfbeelding();
		view = new tegelView();
		roteerUnrotatedImage();
	}

	public QTTegel(String[] tegel, Spel spel) {
		this(tegel, spel, new Vector2D(0, 0));
	}

	public tegelView getTegelView() {
		return view;
	}

	public QPixmap getPixmap() {
		return pixmap;
	}

	public void setPixmap(QPixmap pixmap) {
		this.pixmap = pixmap;
		view.setPixmap(pixmap);
	}

	public void setPixmap(String[] tegel) {
		this.tegel = tegel;
		kiesAfbeelding();
		view.setPixmap(pixmap);
	}

	/**
	 * Stad = s Wei = w Weg = g Klooster = k Kruispunt = r
	 */
	private void kiesAfbeelding() {
		pixmap.load("icons/" + tegel[TEGEL_PRESENTATIE] + ".png");
		pixmap = pixmap.scaled(90, 90);
	}

	private void roteerUnrotatedImage() {
		QMatrix matrix = new QMatrix();
		matrix = matrix.rotate(90.0 * (double) orientatie);
		setPixmap(new QPixmap(pixmap.transformed(matrix,
				TransformationMode.FastTransformation)));
	}

	public void roteer(boolean richting) {
		QMatrix matrix = new QMatrix();

		if (richting) {
			orientatie = (orientatie + 1) % MAX_DRAAIING;
		} else {
			orientatie = (MAX_DRAAIING + orientatie - 1) % MAX_DRAAIING;
		}
		matrix = matrix.rotate(90.0 * (double) orientatie);
		tegel[Spel.ORIENTATIE] = new String("" + orientatie);
		kiesAfbeelding();
		setPixmap(new QPixmap(pixmap.transformed(matrix,
				TransformationMode.FastTransformation)));
	}

	public void update() {
		updatePionnen();
		view.updateTegel();
	}
}

package actua;

import java.util.Observable;
import java.util.Vector;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QCursor;
import com.trolltech.qt.gui.QDrag;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMessageBox;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QStyle;
import com.trolltech.qt.gui.QStyleOptionFocusRect;
import com.trolltech.qt.gui.QWidget;

public class QTInfo extends GInfo {
	private short rows;
	private QWidget qtInfo;
	private QGridLayout vBox;
	private Stapel stapel;
	private QPushButton roteerR;
	private QPushButton roteerL;
	private QPushButton nieuweTegel;
	private QPushButton neemPion;
	private Vector<QWidget> spelers;
	private int TEGEL_PRESENTATIE = 0;
	private int ID_PRESENTATIE = 1;

	private class Stapel extends QWidget {
		private Spel spel;
		QLabel tegelIcon;

		public Stapel(Spel spel) {
			super();
			this.spel = spel;

			tegelIcon = new QLabel(this);

			String[] tegel = spel.vraagNieuweTegel();
			tegelIcon.setPixmap(new QPixmap("src/icons/"
					+ tegel[TEGEL_PRESENTATIE] + ".png"));
			tegelIcon.setMinimumSize(new QSize(90, 90));
			tegelIcon.setMaximumSize(new QSize(90, 90));

			setMinimumSize(new QSize(90, 90));
		}

		public void roteerRechts() {
			QLabel child = (QLabel) childAt(0, 0);

			if (child != null) {
				String[] tegel = spel.vraagNieuweTegel();

				if (tegel != null) {
					QTTegel qtTegel = new QTTegel(tegel);
					qtTegel.roteer(true);
					child.clear();
					child.setPixmap(new QPixmap(qtTegel.getPixmap()));
				}
			}
		}

		public void roteerLinks() {
			QLabel child = (QLabel) childAt(0, 0);

			if (child != null) {
				String[] tegel = spel.vraagNieuweTegel();
				if (tegel != null) {
					QTTegel qtTegel = new QTTegel(tegel);
					qtTegel.roteer(false);
					child.clear();
					child.setPixmap(new QPixmap(qtTegel.getPixmap()));
				}
			}
		}

		public void nieuweTegel() {
			String[] tegel = spel.neemTegelVanStapel();
			spel.legTerugEinde(tegel);

			QLabel child = (QLabel) childAt(0, 0);
			tegel = spel.vraagNieuweTegel();
			child.clear();
			child.setPixmap(new QPixmap("src/icons/" + tegel[TEGEL_PRESENTATIE]
					+ ".png"));
		}

		protected void dragEnterEvent(QDragEnterEvent event) {
			if (event.mimeData().hasFormat("application/x-dnditemdata")) {
				// if (event.source().equals(this)) {
				event.setDropAction(Qt.DropAction.MoveAction);
				event.accept();
				// } else {
				// event.acceptProposedAction();
				// }
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

		protected void mousePressEvent(QMouseEvent event) {
			QLabel child = (QLabel) childAt(0, 0);
			if (child == null)
				return;

			QPixmap pixmap = child.pixmap();

			QByteArray itemData = new QByteArray();
			QDataStream dataStream = new QDataStream(itemData,
					QIODevice.OpenModeFlag.WriteOnly);
			pixmap.writeTo(dataStream);
			event.pos().subtract(child.pos()).writeTo(dataStream);

			com.trolltech.qt.core.QMimeData mimeData = new com.trolltech.qt.core.QMimeData();
			mimeData.setData("application/x-dnditemdata", itemData);

			QDrag drag = new QDrag(this);
			drag.setMimeData(mimeData);
			drag.setPixmap(pixmap);
			drag.setHotSpot(event.pos().subtract(child.pos()));

			QPixmap tempPixmap = new QPixmap(pixmap);
			QPainter painter = new QPainter();
			painter.begin(tempPixmap);
			painter.fillRect(pixmap.rect(), new QBrush(new QColor(127, 127,
					127, 127)));
			painter.end();

			child.setPixmap(tempPixmap);

			if (drag.exec(new Qt.DropActions(Qt.DropAction.CopyAction,
					Qt.DropAction.MoveAction, Qt.DropAction.CopyAction)) == Qt.DropAction.MoveAction) {
				String[] tegel = spel.vraagNieuweTegel();
				if (tegel == null)
					child.close();
				else {
					child.show();
					child.setPixmap(new QPixmap("src/icons/"
							+ tegel[TEGEL_PRESENTATIE] + ".png"));
				}
			} else {
				String[] tegel = spel.vraagNieuweTegel();
				tegel[2] = new String("0"); // orientatie resetten
				child.show();
				child.setPixmap(new QPixmap("src/icons/"
						+ tegel[TEGEL_PRESENTATIE] + ".png"));
			}
		}
	}

	public QTInfo(Spel spel, OptieVerwerker opties) {
		super(spel, opties);
		qtInfo = new QWidget();
		stapel = new Stapel(mSpel);
		vBox = new QGridLayout();
		qtInfo.setLayout(vBox);
		spelers = new Vector<QWidget>();

		roteerR = new QPushButton("Draai Rechts");
		roteerL = new QPushButton("Draai Links");
		nieuweTegel = new QPushButton("Nieuwe Tegel");
		neemPion = new QPushButton("Neem Pion");

		vBox.addWidget(stapel, 0, 0, 1, 2);
		vBox.addWidget(roteerL, 1, 0, 1, 1);
		vBox.addWidget(roteerR, 1, 1, 1, 1);
		vBox.addWidget(nieuweTegel, 2, 0, 1, 2);
		vBox.addWidget(neemPion, 3, 0, 1, 2);
		rows = 4;

		qtInfo.hide();
		roteerR.clicked.connect(stapel, "roteerRechts()");
		roteerL.clicked.connect(stapel, "roteerLinks()");
		nieuweTegel.clicked.connect(stapel, "nieuweTegel()");
		neemPion.clicked.connect(this, "neemPion()");
	}

	public void updateInfo() {

	}

	public void neemPion() {
		if (mSpel.geefAantalSpelers() > 0) {
			int overigePionnen = mSpel.geefAantalOngeplaatstePionnen(mSpel
					.geefHuidigeSpeler());
			if (overigePionnen == 0) {
				QMessageBox box = new QMessageBox();
				box.setWindowTitle("Bericht");
				box.setText("Al uw pionnen zijn reeds geplaatst.");
				box.show();
			} else {
				QApplication.setOverrideCursor(new QCursor(new QPixmap(
						"src/icons/pion.png")));
			}
		}
	}

	public synchronized void updateSpelers() {
		if (vBox != null) {
			for (int i = 0; i < mSpel.geefAantalSpelers(); ++i) {
				spelers.add(new QTSpelerInfo(mSpel,
						mSpel.geefKleurVanSpeler(i), qtInfo)
						.getSpelerInfoveld());
				vBox.addWidget(spelers.lastElement(), rows++, 0, 1, 2);
			}
		}
	}

	public QWidget getQtInfo() {
		return qtInfo;
	}

	public synchronized void update(Observable o, Object arg) {
		qtInfo.hide();
		System.out.println((String) arg);
		for (int i = spelers.size() - 1; i >= 0; --i) {
			QWidget item = spelers.elementAt(i);
			vBox.removeWidget(item);
			spelers.remove(i);
			item.dispose();
			rows--;
		}

		updateSpelers();

		qtInfo.setPalette(new QPalette());
		qtInfo.show();
	}
}

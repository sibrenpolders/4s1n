package actua;

import java.util.Observable;
import java.util.Vector;
import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDrag;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QGroupBox;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSpacerItem;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class QTInfo extends GInfo {
	private short rows;
	private QWidget qtInfo;
	private QGridLayout box;
	private Stapel stapel;
	private QPushButton roteerR;
	private QPushButton roteerL;
	private QPushButton nieuweTegel;
	private QPushButton beurt;
	private QSpacerItem spacer;
	private Vector<QWidget> spelers;
	private Vector<QGroupBox> spelerBoxen;
	private static final int TEGEL_PRESENTATIE = 0;
	private static final int ID_PRESENTATIE = 1;
	private static final int BUTTON_HEIGHT = 20;
	private static final int HOOGTE = 600;
	private static final int BREEDTE = 180;
	private int hoogte;
	private int breedte;

	private class Stapel extends QWidget {
		private Spel spel;
		private QLabel tegelIcon;
		private boolean tegelGenomen;

		public Stapel(Spel spel) {
			super();
			this.spel = spel;

			tegelIcon = new QLabel(this);
			setSize(tegelIcon, 90, 90);

			String[] tegel = spel.vraagNieuweTegel();
			tegelIcon.setPixmap(new QPixmap("src/icons/"
					+ tegel[TEGEL_PRESENTATIE] + ".png"));
		}

		public boolean isTegelGenomen() {
			return tegelGenomen;
		}

		public void setTegelGenomen(boolean tegelGenomen) {
			this.tegelGenomen = tegelGenomen;
		}

		public void roteerRechts() {
			QLabel child = (QLabel) childAt(0, 0);

			if (child != null) {
				String[] tegel = spel.vraagNieuweTegel();

				if (tegel != null) {
					QTTegel qtTegel = new QTTegel(tegel, spel);
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
					QTTegel qtTegel = new QTTegel(tegel, spel);
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
				event.setDropAction(Qt.DropAction.MoveAction);
				event.accept();
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
			if (!spel.heeftHuidigeSpelerTegelGeplaatst()) {
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
					boolean gedaan = false;
					String[] tegel;
					int stapelSize, teller = 0;

					tegel = spel.vraagNieuweTegel();

					// stapelSize = spel.getStapelSize();
					// do{
					// tegel = spel.vraagNieuweTegel();
					// teller++;
					// if(teller > stapelSize){
					// gedaan = true;
					// break;
					// }
					// }while(!spel.isTegelPlaatsbaar(tegel));

					if (gedaan || tegel == null) {
						child.show();
						child.setPixmap(new QPixmap("src/icons/test.gif"));
						tegelGenomen = true;
					} else {
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
	}

	public QTInfo(Spel spel, OptieVerwerker opties) {
		super(spel, opties);
		hoogte = HOOGTE;
		breedte = BREEDTE;

		create();
		resize();
		add();
		qtInfo.hide();
		connect();
	}

	private void connect() {
		roteerR.clicked.connect(stapel, "roteerRechts()");
		roteerL.clicked.connect(stapel, "roteerLinks()");
		nieuweTegel.clicked.connect(stapel, "nieuweTegel()");
		beurt.clicked.connect(this, "volgendeSpeler()");
	}

	private void add() {
		box.addWidget(stapel, 0, 0, 1, 2);
		box.addWidget(roteerL, 1, 0, 1, 1);
		box.addWidget(roteerR, 1, 1, 1, 1);
		box.addWidget(nieuweTegel, 2, 0, 1, 2);
		box.addWidget(beurt, 3, 0, 1, 2);

		rows = 4;
	}

	private void resize() {
		setSize(stapel, 90, 90);
		setSize(roteerR, 80, BUTTON_HEIGHT);
		setSize(roteerL, 80, BUTTON_HEIGHT);

		setSize(qtInfo, breedte, hoogte);
	}

	private void create() {
		qtInfo = new QWidget();
		box = new QGridLayout();
		qtInfo.setLayout(box);

		stapel = new Stapel(mSpel);
		spelers = new Vector<QWidget>();
		spelerBoxen = new Vector<QGroupBox>();
		roteerR = new QPushButton("Draai Rechts");
		roteerL = new QPushButton("Draai Links");
		nieuweTegel = new QPushButton("Nieuwe Tegel");
		beurt = new QPushButton("Volgende Speler");
	}

	public void updateInfo() {

	}

	public synchronized void updateSpelers() {
		if (box != null) {
			QGroupBox group;
			QVBoxLayout layout;
			spelers.clear();
			for (int i = 0; i < mSpel.geefAantalSpelers(); ++i) {
				spelers.add(new QTSpelerInfo(mSpel,
						mSpel.geefKleurVanSpeler(i), qtInfo)
						.getSpelerInfoveld());
				layout = new QVBoxLayout();

				group = new QGroupBox(mSpel.geefSpelerNaam(mSpel
						.geefKleurVanSpeler(i)));
				layout.addWidget(spelers.lastElement());
				group.setLayout(layout);
				setSize(group, breedte - 15, 90);
				box.addWidget(group, rows, 0, 1, 2);
				spelerBoxen.add(group);
				++rows;
			}
		}

		spacer = new QSpacerItem(breedte, hoogte - 95 - 4 * (BUTTON_HEIGHT + 5)
				- mSpel.geefAantalSpelers() * 95);
		box.addItem(spacer, rows, 0, 1, 2);
	}

	public synchronized void verwijderSpelers() {
		box.removeItem(spacer);
		for (int i = spelers.size() - 1; i >= 0; --i) {
			QWidget item = spelers.elementAt(i);
			box.removeWidget(item);
			spelers.remove(i);
			item.dispose();
			rows--;
		}

		for (int i = spelerBoxen.size() - 1; i >= 0; --i) {
			QWidget item = spelerBoxen.elementAt(i);
			box.removeWidget(item);
			spelerBoxen.remove(i);
			item.dispose();
		}
	}

	public QWidget getQtInfo() {
		return qtInfo;
	}

	public synchronized void update(Observable o, Object arg) {
		if (!arg.equals(true)) {
			return;
		}

		verwijderSpelers();
		updateSpelers();
		stapel.nieuweTegel();

		qtInfo.setPalette(new QPalette());
		qtInfo.show();
	}

	private void setSize(QWidget widget, int w, int h) {
		widget.setMaximumSize(w, h);
		widget.setMinimumSize(w, h);
	}

	@SuppressWarnings("unused")
	private void volgendeSpeler() {
		mSpel.volgendeSpeler();
		verwijderSpelers();
		updateSpelers();
	}

}

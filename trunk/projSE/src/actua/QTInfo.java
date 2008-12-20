package actua;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDrag;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class QTInfo extends GInfo {
	private QWidget qtInfo;
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
			tegelIcon.setPixmap(new QPixmap("src/icons/"+ tegel[TEGEL_PRESENTATIE] + ".png"));
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
		
		public void nieuweTegel(){
			String[] tegel = spel.neemTegelVanStapel();
			spel.legTerugEinde(tegel);
			
			QLabel child = (QLabel) childAt(0, 0);
			tegel = spel.vraagNieuweTegel();
			child.clear();
			child.setPixmap(new QPixmap("src/icons/"+ tegel[TEGEL_PRESENTATIE] + ".png"));
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
			QDataStream dataStream = new QDataStream(itemData,QIODevice.OpenModeFlag.WriteOnly);
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
			painter.fillRect(pixmap.rect(), new QBrush(new QColor(127, 127, 127, 127)));
			painter.end();

			child.setPixmap(tempPixmap);

			if (drag.exec(new Qt.DropActions(Qt.DropAction.CopyAction,Qt.DropAction.MoveAction, Qt.DropAction.CopyAction)) == Qt.DropAction.MoveAction) {
				String[] tegel = spel.vraagNieuweTegel();
				if (tegel == null)
					child.close();
				else {
					child.show();
					child.setPixmap(new QPixmap("src/icons/"+ tegel[TEGEL_PRESENTATIE] + ".png"));
				}
			} else {
				String[] tegel = spel.vraagNieuweTegel();
				tegel[2] = new String("0"); // orientatie resetten
				child.show();
				child.setPixmap(new QPixmap("src/icons/"+ tegel[TEGEL_PRESENTATIE] + ".png"));				
			}
		}
	}

	public QTInfo(Spel spel, OptieVerwerker opties) {
		super(spel, opties);
		qtInfo = new QWidget();
		QWidget hBox = new QWidget();
		QPalette palette = new QPalette();
		QPushButton roteerR = new QPushButton("Draai Rechts");
		QPushButton roteerL = new QPushButton("Draai Links");
		QPushButton nieuweTegel = new QPushButton("Nieuwe Tegel");
		Stapel stapel = new Stapel(spel);

		QVBoxLayout vbox = new QVBoxLayout();
		QHBoxLayout hbox = new QHBoxLayout();
		hbox.addWidget(roteerL);
		hbox.addWidget(roteerR);
		vbox.addWidget(stapel);
		hBox.setLayout(hbox);
		vbox.addWidget(hBox);
		vbox.addWidget(nieuweTegel);	
		vbox.addWidget(new QTSpelerInfo(new Speler("", 'r', 0), qtInfo).getSpelerInfoveld());
		vbox.addWidget(new QTSpelerInfo(new Speler("", 'g', 0), qtInfo).getSpelerInfoveld());
		vbox.addWidget(new QTSpelerInfo(new Speler("", 'x', 0), qtInfo).getSpelerInfoveld());
		vbox.addWidget(new QTSpelerInfo(new Speler("", 'x', 0), qtInfo).getSpelerInfoveld());
		vbox.addWidget(new QTSpelerInfo(new Speler("", 'x', 0), qtInfo).getSpelerInfoveld());

		roteerR.clicked.connect(stapel, "roteerRechts()");
		roteerL.clicked.connect(stapel, "roteerLinks()");
		nieuweTegel.clicked.connect(stapel, "nieuweTegel()");

		qtInfo.setLayout(vbox);

		qtInfo.setPalette(palette);
	}

	public QWidget getQtInfo() {
		return qtInfo;
	}

	private void setQtInfo(QWidget qtInfo) {
		this.qtInfo = qtInfo;
	}

	@Override
	public void addSpeler(Speler speler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toonInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void toonSpelers() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void toonTegelStapel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateInfo() {
		// TODO Auto-generated method stub

	}

}

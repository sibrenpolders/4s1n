package actua;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
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
import com.trolltech.qt.gui.QVBoxLayout;
import com.trolltech.qt.gui.QWidget;

public class QTInfo extends GInfo {
	private QWidget qtInfo;

	private class qtInfoVenster extends QWidget {
		public qtInfoVenster() {
			super();
			// grabMouse();
			QVBoxLayout vbox = new QVBoxLayout();

			QLabel tegelIcon = new QLabel(this);
			tegelIcon.setPixmap(new QPixmap("src/icons/city1.png"));
			tegelIcon.setMinimumSize(new QSize(90, 90));
			tegelIcon.setMaximumSize(new QSize(90, 90));

			setMinimumSize(new QSize(90, 90));
			setLayout(vbox);
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

		protected void dropEvent(QDropEvent event) {
			// if (event.mimeData().hasFormat("application/x-dnditemdata")) {
			// QByteArray itemData =
			// event.mimeData().data("application/x-dnditemdata");
			// QDataStream dataStream = new QDataStream(itemData,
			// QIODevice.OpenModeFlag.ReadOnly);
			//	        
			// QPixmap pixmap = new QPixmap();
			// QPoint offset = new QPoint();
			// pixmap.readFrom(dataStream);
			// offset.readFrom(dataStream);
			//
			// QLabel newIcon = new QLabel(this);
			// newIcon.setPixmap(pixmap);
			// newIcon.move(event.pos().subtract(offset));
			// newIcon.show();
			// newIcon.setAttribute(Qt.WidgetAttribute.WA_DeleteOnClose);
			//
			// if (event.source().equals(this)) {
			// event.setDropAction(Qt.DropAction.MoveAction);
			// event.accept();
			// } else {
			// event.acceptProposedAction();
			// }
			// } else {
			// event.ignore();
			// }
		}

		protected void mousePressEvent(QMouseEvent event) {
			QLabel child = (QLabel) childAt(event.pos());
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
					Qt.DropAction.MoveAction, Qt.DropAction.CopyAction)) == Qt.DropAction.MoveAction)
				child.close();
			else {
				child.show();
				child.setPixmap(pixmap);
			}
		}
	}

	public QTInfo(Spel spel, OptieVerwerker opties) {
		super(spel, opties);
		qtInfo = new QWidget();
		QPalette palette = new QPalette();

		QHBoxLayout hbox = new QHBoxLayout();
		hbox.addWidget(new qtInfoVenster());
		hbox.addWidget(new QTSpelerInfo(new Speler("Dude", 'l', 0), qtInfo)
				.getSpelerInfoveld());

		qtInfo.setLayout(hbox);

		qtInfo.setPalette(palette);
	}

	public QWidget getQtInfo() {
		return qtInfo;
	}

	private void setQtInfo(QWidget qtInfo) {
		this.qtInfo = qtInfo;
	}

}

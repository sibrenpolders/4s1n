package GUI;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDrag;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPainter;
import com.trolltech.qt.gui.QPixmap;

public class QTPion extends QLabel {
	private boolean isDraggable = false;

	public QTPion(char kleur, boolean flag) {
		super();
		isDraggable = flag;
		setPixmap(new QPixmap("src/icons/" + kleur + ".png"));
		setMaximumSize(12, 12);
		setMinimumSize(12, 12);
	}

	public boolean isDraggable() {
		return isDraggable;
	}

	public void setDraggable(boolean flag) {
		isDraggable = flag;
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
			QByteArray itemData = event.mimeData().data(
					"application/x-dnditemdata");
			QDataStream dataStream = new QDataStream(itemData,
					QIODevice.OpenModeFlag.ReadOnly);

			QPixmap pixmap = new QPixmap();
			QPoint offset = new QPoint();
			pixmap.readFrom(dataStream);
			offset.readFrom(dataStream);

			QLabel newIcon = new QLabel(this);
			newIcon.setPixmap(pixmap);
			newIcon.move(event.pos().subtract(offset));
			newIcon.show();
			newIcon.setAttribute(Qt.WidgetAttribute.WA_DeleteOnClose);

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
		if (isDraggable) {
			QPixmap pixmap = pixmap();

			QByteArray itemData = new QByteArray();
			QDataStream dataStream = new QDataStream(itemData,
					QIODevice.OpenModeFlag.WriteOnly);
			pixmap.writeTo(dataStream);
			event.pos().writeTo(dataStream);

			com.trolltech.qt.core.QMimeData mimeData = new com.trolltech.qt.core.QMimeData();
			mimeData.setData("application/x-pionitemdata", itemData);

			QDrag drag = new QDrag(this);
			drag.setMimeData(mimeData);
			drag.setPixmap(pixmap);
			drag.setHotSpot(event.pos());

			QPixmap tempPixmap = new QPixmap(pixmap);
			QPainter painter = new QPainter();
			painter.begin(tempPixmap);
			painter.fillRect(pixmap.rect(), new QBrush(new QColor(127, 127,	127, 127)));
			painter.end();

			setPixmap(tempPixmap);

			if (drag.exec(new Qt.DropActions(Qt.DropAction.CopyAction,
					Qt.DropAction.MoveAction, Qt.DropAction.CopyAction)) == Qt.DropAction.MoveAction)
				close();
			else {
				show();
				setPixmap(pixmap);
			}
		}
	}
}
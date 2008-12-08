package actua;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.QPointF;
import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsSceneDragDropEvent;
import com.trolltech.qt.gui.QGraphicsSceneMouseEvent;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.core.Qt;

public class QTSpeelveld extends GSpeelveld {
	private QGraphicsView speelveld;
	
	private class QtGraphicsView extends QGraphicsView{
		QGraphicsScene scene;
		public QtGraphicsView() {
			init();
		}
		
		private void init() {
			scene = new QGraphicsScene();
//			setLayout(new QGridLayout());
			//speelveld.grabMouse();
			setGeometry(0, 0, 720, 540);
			setMaximumSize(new QSize(720, 540));
			setMinimumSize(new QSize(720, 540));
			
			setScene(scene);
			setBackgroundBrush(new QBrush(new QPixmap(
			"classpath:background.xpm")));
			resize(new QSize(1024, 800));
			setCacheMode(new QGraphicsView.CacheMode(
					QGraphicsView.CacheModeFlag.CacheBackground));
			setViewportUpdateMode(
					QGraphicsView.ViewportUpdateMode.FullViewportUpdate);
		}

		protected void dragEnterEvent(QDragEnterEvent event)
	    {
	    	System.out.println("bla3");
	        if (event.mimeData().hasFormat("application/x-dnditemdata")) {
		    	System.out.println("bla4");
//	            if (event.source().equals(this)) {
	                event.setDropAction(Qt.DropAction.MoveAction);
//	                event.accept();
//	            } else {
	                event.acceptProposedAction();
//	            }
	        } else {
	            event.ignore();
	        }
	    }

	    protected void dragMoveEvent(QDragMoveEvent event)
	    {
	    	System.out.println("bla");
	        if (event.mimeData().hasFormat("application/x-dnditemdata")) {
		    	System.out.println("bla2");
//	            if (event.source().equals(this)) {
	                event.setDropAction(Qt.DropAction.MoveAction);
	                event.accept();
//	            } else {
//	                event.acceptProposedAction();
//	            }
	        } else {
	            event.ignore();
	        }
	    }

	    protected void dropEvent(QDropEvent event) {
	        if (event.mimeData().hasFormat("application/x-dnditemdata")) {
	            QByteArray itemData = event.mimeData().data("application/x-dnditemdata");
	            QDataStream dataStream = new QDataStream(itemData, QIODevice.OpenModeFlag.ReadOnly);
	        
	            QPixmap pixmap = new QPixmap();
	            QPoint offset = new QPoint();
	            pixmap.readFrom(dataStream);
	            offset.readFrom(dataStream);

	            QLabel newIcon = new QLabel();
	            newIcon.setPixmap(pixmap);
	            newIcon.move(event.pos().subtract(offset));
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

	    protected void mousePressEvent(QMouseEvent event)
	    {
	    	System.out.println("bluka");
//	        QLabel child = (QLabel) childAt(event.pos());
//	        if (child == null)
//	            return;
//
//	        QPixmap pixmap = child.pixmap();
//
//	        QByteArray itemData = new QByteArray();
//	        QDataStream dataStream = new QDataStream(itemData, QIODevice.OpenModeFlag.WriteOnly);
//	        pixmap.writeTo(dataStream);
//	        event.pos().subtract(child.pos()).writeTo(dataStream);
//
//
//	        com.trolltech.qt.core.QMimeData mimeData = new com.trolltech.qt.core.QMimeData();
//	        mimeData.setData("application/x-dnditemdata", itemData);
//	        
//	        QDrag drag = new QDrag(this);
//	        drag.setMimeData(mimeData);
//	        drag.setPixmap(pixmap);
//	        drag.setHotSpot(event.pos().subtract(child.pos()));
//
//	        QPixmap tempPixmap = new QPixmap(pixmap);
//	        QPainter painter = new QPainter();
//	        painter.begin(tempPixmap);
//	        painter.fillRect(pixmap.rect(), new QBrush(new QColor(127, 127, 127, 127)));
//	        painter.end();
//
//	        child.setPixmap(tempPixmap);
//
//	        if (drag.exec(new Qt.DropActions(Qt.DropAction.CopyAction,
//	            Qt.DropAction.MoveAction, Qt.DropAction.CopyAction)) == Qt.DropAction.MoveAction)
//	            child.close();
//	        else {
//	            child.show();
//	            child.setPixmap(pixmap);
//	        }
	    }
	};	

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super();
		setSpeelveld(new QtGraphicsView());
	}
	
	public void hide() {

	}

	public void show() {

	}

	public QWidget getSpeelveld() {
		return speelveld;
	}

	private void setSpeelveld(QGraphicsView speelveld) {
		this.speelveld = speelveld;
	}
}

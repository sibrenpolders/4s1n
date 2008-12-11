package actua;

import java.util.ArrayList;
import java.util.Vector;

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
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QWheelEvent;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.core.Qt;

public class QTSpeelveld extends GSpeelveld {
	private class QtGraphicsView extends QGraphicsView{
		public QtGraphicsView(QGraphicsScene parent) {
			super(parent);
			init();
			setAcceptDrops(true);
		}
		
		private void init() {			
			setBackgroundBrush(new QBrush(new QPixmap(
			"src/icons/background.xpm")));
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

	    protected void dragMoveEvent(QDragMoveEvent event)
	    {
	    	System.out.println("bla");
	        if (event.mimeData().hasFormat("application/x-dnditemdata")) {
		    	System.out.println("bla2");
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
	    	
	        if (event.mimeData().hasFormat("application/x-dnditemdata")) {
	            QByteArray itemData = event.mimeData().data("application/x-dnditemdata");
	            QDataStream dataStream = new QDataStream(itemData, QIODevice.OpenModeFlag.ReadOnly);
	        
	            QPixmap pixmap = new QPixmap();
	            QPoint offset = new QPoint();
	            pixmap.readFrom(dataStream);
	            offset.readFrom(dataStream);
	                    
	            /*QLabel newIcon = new QLabel();
	            newIcon.setPixmap(pixmap);
	            newIcon.move(event.pos().subtract(offset));
	           
	            newIcon.setAttribute(Qt.WidgetAttribute.WA_DeleteOnClose);
	            scene.addWidget(newIcon);*/
	            
	            scene().addPixmap(pixmap.scaled(width()-5,height()-5));
	            
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
	private Vector<QtGraphicsView> speelveld;
	private Camera camera;
	private QWidget gridWidget;
	private QGridLayout gridLayout;

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super(spel);
		setSpeelveld(new Vector<QtGraphicsView>());
		init();
	}
	
	private void init() {
		gridWidget = new QWidget();		
		gridLayout = new QGridLayout(gridWidget);
		gridLayout.setSpacing(0);
		QGraphicsScene scene;
		QtGraphicsView view;
		
		gridWidget.setGeometry(0, 0, 720, 540);
		gridWidget.setMaximumSize(new QSize(720, 540));
		gridWidget.setMinimumSize(new QSize(720, 540));
		//gridWidget.resize(new QSize(1024, 800));

		for (int i=0;i<6;i++){
			for (int j=0;j<8;j++){
				scene=new QGraphicsScene(gridLayout);
				view=new QtGraphicsView(scene);
				gridLayout.addWidget(view,i,j+1,1,1);
			}
		}
		
		//camera dizzle
		camera = new Camera(new Vector3D(0,0,0),new Vector3D(1000,1000,10));
		camera.setHuidigeVector(new Vector3D(0,0,0));
		
		QPushButton buttonUp = new QPushButton("^",gridWidget);
		buttonUp.setGeometry(gridWidget.width()/2-18,0,35,25);
		buttonUp.clicked.connect(this,"cameraUp()");
		QPushButton buttonDown = new QPushButton("v",gridWidget);
		buttonDown.setGeometry(gridWidget.width()/2-18,gridWidget.height()-26,35,25);
		buttonDown.clicked.connect(this,"cameraDown()");
		QPushButton buttonLeft = new QPushButton("<",gridWidget);
		buttonLeft.setGeometry(0,gridWidget.height()/2-13,25,35);
		buttonLeft.clicked.connect(this,"cameraLeft()");
		QPushButton buttonRight = new QPushButton(">",gridWidget);
		buttonRight.setGeometry(gridWidget.width()-26,gridWidget.height()/2-13,25,35);
		buttonRight.clicked.connect(this,"cameraRight()");
		//
	}
	
	public void hide() {

	}

	public void show() {

	}

	public Vector<QtGraphicsView> getSpeelveld() {
		return speelveld;
	}

	private void setSpeelveld(Vector<QtGraphicsView> speelveld) {
		this.speelveld = speelveld;
	}
	public void voegTegelToeAanGrafischeLijst(Tegel tegel) {
		gTegels.add(new QTTegel(tegel));
	}
	
	protected void wheelEvent(QWheelEvent event) {
    	int zoomFactor = -event.delta()/8/3;
    	Vector3D beweging = new Vector3D(camera.getHuidigeVector().getX(),camera.getHuidigeVector().getY()+5,zoomFactor*camera.getHuidigeVector().getZ());
    	
    	if (camera.bewegingGeldig(beweging)) {
    		camera.veranderStandpunt(beweging);
    		System.out.println(zoomFactor);
    	}
    }
    
    private void cameraUp() {
    	Vector3D beweging = new Vector3D(camera.getHuidigeVector().getX(),camera.getHuidigeVector().getY()+5,camera.getHuidigeVector().getZ());
    	
    	if (camera.bewegingGeldig(beweging)) {
    		camera.veranderStandpunt(beweging);
    		System.out.println("up");
    	}
    }
    private void cameraDown() {
    	Vector3D beweging = new Vector3D(camera.getHuidigeVector().getX(),camera.getHuidigeVector().getY()-5,camera.getHuidigeVector().getZ());
    	
    	if (camera.bewegingGeldig(beweging)) {
    		camera.veranderStandpunt(beweging);
    		System.out.println("down");
    	}
    }
    private void cameraLeft() {
    	Vector3D beweging = new Vector3D(camera.getHuidigeVector().getX()-5,camera.getHuidigeVector().getY(),camera.getHuidigeVector().getZ());
    	
    	if (camera.bewegingGeldig(beweging)) {
    		camera.veranderStandpunt(beweging);
    		System.out.println("left");
    	}
    }
    private void cameraRight() {
    	Vector3D beweging = new Vector3D(camera.getHuidigeVector().getX()+5,camera.getHuidigeVector().getY(),camera.getHuidigeVector().getZ());
    	
    	if (camera.bewegingGeldig(beweging)) {
    		camera.veranderStandpunt(beweging);
    		System.out.println("right");
    	}
    }
    private void veranderZicht() {
    	
    }

	public QWidget getGridWidget() {
		return gridWidget;
	}

	public void setGridWidget(QWidget gridWidget) {
		this.gridWidget = gridWidget;
	}
}

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
import com.trolltech.qt.gui.QKeySequence;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QMouseEvent;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QShortcut;
import com.trolltech.qt.gui.QWheelEvent;
import com.trolltech.qt.gui.QWidget;
import com.trolltech.qt.core.Qt;

public class QTSpeelveld extends GSpeelveld {
	private class QtGraphicsView extends QGraphicsView{
		private boolean gevuld;
		private Vector2D gridCoord;
		
		public QtGraphicsView(QGraphicsScene parent,Vector2D coord) {
			super(parent);
			init();
			setAcceptDrops(true);
			gevuld=false;
			this.gridCoord=coord;
		}
		
		private void init() {			
			setBackgroundBrush(new QBrush(new QPixmap(
			"src/icons/background.xpm")));
			setCacheMode(new QGraphicsView.CacheMode(
					QGraphicsView.CacheModeFlag.CacheBackground));
			setViewportUpdateMode(
					QGraphicsView.ViewportUpdateMode.FullViewportUpdate);
		}

		private void setPixmap(QPixmap pixmap) {
			scene().clear();
			scene().addPixmap(pixmap.scaled(width()-5,height()-5));
			gevuld=true;
		}
		
		private void removePixmap() {
			scene().clear();
			gevuld=false;
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
	    	
	        if (event.mimeData().hasFormat("application/x-dnditemdata") && !gevuld) {
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
	            voegTegelToe(gridCoord,pixmap);
	            gevuld=true;
	            
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
	private QWidget gridWidget;
	private QGridLayout gridLayout;
	private int rows = 7;
	private int columns = 9;

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super(spel);
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

		for (int i=0;i<rows;i++){
			for (int j=0;j<columns;j++){
				scene=new QGraphicsScene(gridLayout);
				view=new QtGraphicsView(scene,new Vector2D(i,j));
				gridLayout.addWidget(view,i,j,1,1);
			}
		}
		
		//camera dizzle
		getSpel().getTafelVerwerker().getCamera().setMinVector(new Vector3D(-1000,-1000,0));
		getSpel().getTafelVerwerker().getCamera().setMaxVector(new Vector3D(1000,1000,10));
		getSpel().getTafelVerwerker().getCamera().setHuidigeVector(new Vector3D(-3,-4,0));
		
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
		
		QShortcut shortcut = new QShortcut(new QKeySequence("Up"),gridWidget);
		shortcut.activated.connect(this,"cameraUp()");
		shortcut = new QShortcut(new QKeySequence("Down"),gridWidget);
		shortcut.activated.connect(this,"cameraDown()");
		shortcut = new QShortcut(new QKeySequence("Left"),gridWidget);
		shortcut.activated.connect(this,"cameraLeft()");
		shortcut = new QShortcut(new QKeySequence("Right"),gridWidget);
		shortcut.activated.connect(this,"cameraRight()");
		//
	}
	
	public void hide() {

	}

	public void show() {

	}
	
	public void voegTegelToe(Vector2D gridCoord,QPixmap pixmap) {
		Tegel tegel = getSpel().getTafelVerwerker().neemTegelVanStapel();
		Vector2D coord = new Vector2D(getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getX()+gridCoord.getX(),getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getY()+gridCoord.getY());
		
//		QGraphicsScene scene;
//		QtGraphicsView view;
//		
//		if (gridCoord.getX()==0 || gridCoord.getX()==6)
//			for (int j=0;j<9;j++) {
//				scene=new QGraphicsScene(gridLayout);
//				view=new QtGraphicsView(scene,new Vector2D(gridCoord.getX()+1,j));
//				gridLayout.addWidget(view,gridCoord.getX()+1,j,1,1);
//			}
		
		if (getSpel().getTafelVerwerker().plaatsTegel(tegel,coord))
			voegTegelToeAanGrafischeLijst(tegel,coord,pixmap);
	}
	
	protected void wheelEvent(QWheelEvent event) {
    	int zoomFactor = -event.delta()/8/15;
    	Vector3D nieuwePositie = new Vector3D(getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getX(),getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getY(),zoomFactor*getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getZ());
    	
    	getSpel().getTafelVerwerker().verplaatsCamera(nieuwePositie);
    }
    
    private void cameraUp() {
    	Vector3D nieuwePositie = new Vector3D(getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getX()-1,getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getY(),getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getZ());
    	QGraphicsScene scene;
    	QtGraphicsView view;
    	
    	if (getSpel().getTafelVerwerker().verplaatsCamera(nieuwePositie))
    		veranderZicht('u');
    }
    private void cameraDown() {
    	Vector3D nieuwePositie = new Vector3D(getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getX()+1,getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getY(),getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getZ());
    	
    	if (getSpel().getTafelVerwerker().verplaatsCamera(nieuwePositie))
    		veranderZicht('d');
    }
    private void cameraLeft() {
    	Vector3D nieuwePositie = new Vector3D(getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getX(),getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getY()-1,getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getZ());
    	
    	if (getSpel().getTafelVerwerker().verplaatsCamera(nieuwePositie))
    		veranderZicht('l');
    }
    private void cameraRight() {
    	Vector3D nieuwePositie = new Vector3D(getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getX(),getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getY()+1,getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getZ());
    	
    	if (getSpel().getTafelVerwerker().verplaatsCamera(nieuwePositie))
    		veranderZicht('r');
    }
    
    //nog een paar foutjes
    private void veranderZicht(char richting) {
    	int offsetX = getSpel().getTafelVerwerker().getStartTegelPos().getX();
    	int offsetY = getSpel().getTafelVerwerker().getStartTegelPos().getY();
    	int startX,startY,i=0,j=0,y;
    	int sizeX = gTegels.size();
    	int sizeY;
		
		startX = offsetX+getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getX();
		startY = offsetY+getSpel().getTafelVerwerker().getCamera().getHuidigeVector().getY();
		
		for (;i<rows && startX<0;startX++,i++)
			for (y=0;y<columns;y++) {
				((QtGraphicsView)gridLayout.itemAtPosition(i,y).widget()).removePixmap();
			}
		
		for (;i<rows;i++,startX++) {
			for (y=startY;j<columns && y<0;y++) {
				((QtGraphicsView)gridLayout.itemAtPosition(i,j).widget()).removePixmap();
				j++;
			}
			
			if (startX<sizeX)
				sizeY = gTegels.get(startX).size();
			else
				sizeY=0;
			for (;j<columns;j++,y++) {
				if (startX < sizeX && y < sizeY && gTegels.get(startX).get(y)!=null) {
					((QtGraphicsView)gridLayout.itemAtPosition(i,j).widget()).setPixmap(((QTTegel)gTegels.get(startX).get(y)).getPixmap());
				}
				else {
					((QtGraphicsView)gridLayout.itemAtPosition(i,j).widget()).removePixmap();
				}
			}
			j=0;
		}
    }

	public QWidget getGridWidget() {
		return gridWidget;
	}

	public void setGridWidget(QWidget gridWidget) {
		this.gridWidget = gridWidget;
	}
}

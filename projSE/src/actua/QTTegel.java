package actua;

import com.trolltech.qt.core.QByteArray;
import com.trolltech.qt.core.QDataStream;
import com.trolltech.qt.core.QIODevice;
import com.trolltech.qt.core.QPoint;
import com.trolltech.qt.core.Qt;
import com.trolltech.qt.core.Qt.TransformationMode;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QDragEnterEvent;
import com.trolltech.qt.gui.QDragLeaveEvent;
import com.trolltech.qt.gui.QDragMoveEvent;
import com.trolltech.qt.gui.QDropEvent;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QMatrix;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWheelEvent;

public class QTTegel extends GTegel {
	private static final int TEGEL_PRESENTATIE = 0;
	private static final int MAX_DRAAIING = 4;
	private QPixmap pixmap;
	private Vector2D tegelCoord;
	
	private class QtGraphicsView extends QGraphicsView{
		private boolean gevuld;
		private int pionCoord;
		
		public QtGraphicsView(QGraphicsScene parent, int pionCoord) {
			super(parent);
			init();
			setAcceptDrops(true);
			gevuld=false;
			this.pionCoord = pionCoord;
		}
		
		private void init() {			
			setBackgroundBrush(new QBrush(new QPixmap("src/icons/" + tegel[TEGEL_PRESENTATIE] + ".png")));
			setCacheMode(new QGraphicsView.CacheMode(QGraphicsView.CacheModeFlag.CacheBackground));
			setViewportUpdateMode(QGraphicsView.ViewportUpdateMode.FullViewportUpdate);
		}

		private void setPixmap(QPixmap pixmap) {
			scene().clear();
			scene().addPixmap(pixmap.scaled(width()-5,height()-5));
			update();
			gevuld=true;
		}
		
		private void removePixmap() {
			scene().clear();
			update();
			gevuld=false;
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
	        if (event.mimeData().hasFormat("application/x-pionitemdata") && !gevuld) {
	            QByteArray itemData = event.mimeData().data("application/x-dnditemdata");
	            QDataStream dataStream = new QDataStream(itemData, QIODevice.OpenModeFlag.ReadOnly);
	        
	            QPixmap pixmap = new QPixmap();
	            QPoint offset = new QPoint();
	            pixmap.readFrom(dataStream);
	            offset.readFrom(dataStream);
	                    
	            if (voegPionToe(pionCoord,pixmap))
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

	    protected void dragLeaveEvent(QDragLeaveEvent event) {
	    	
	    }

		public boolean isGevuld() {
			return gevuld;
		}

		public void setGevuld(boolean gevuld) {
			this.gevuld = gevuld;
		}
	};
	
	public QTTegel() {
		super();
		pixmap = new QPixmap(90,90);
	}
	
	public QTTegel(String[] tegel, Spel spel) {
		super(tegel, spel);
		pixmap = new QPixmap(90,90);
		kiesAfbeelding();
	}
	
	public QTTegel(String[] tegel, Spel spel, QPixmap pixmap) {
		super(tegel, spel);
		this.pixmap = new QPixmap(pixmap);
	}

	public boolean voegPionToe(int pionCoord, QPixmap pixmap) {
		if (spel.plaatsPion(tegelCoord, pionCoord, spel.geefHuidigeSpeler())) {
			
		}
		
		return false;
	}
	
	public String[] getTegel(){
		return super.getTegel();
	}

	public QPixmap getPixmap() {
		return pixmap;
	}

	public void setPixmap(QPixmap pixmap) {
		this.pixmap = pixmap;
	}
	
	public void wijzigGroottePixmap(int pixels){
		pixmap = pixmap.scaled(pixels,pixels);
	}

	/**
	 * Stad = s
	 * Wei = w
	 * Weg = g
	 * Klooster = k
	 * Kruispunt = r
	*/
	private void kiesAfbeelding(){
		pixmap.load("src/icons/"+ tegel[TEGEL_PRESENTATIE] + ".png");
	}

	public void roteer(boolean richting) {
		QMatrix matrix = new QMatrix();
		
		if (richting) {
			orientatie = (orientatie + 1)%MAX_DRAAIING;
		} else {
			orientatie = (MAX_DRAAIING + orientatie - 1)%MAX_DRAAIING;
		}
		matrix = matrix.rotate(90.0*(double)orientatie);
		tegel[2] = new String("" + orientatie);
		pixmap = new QPixmap(pixmap.transformed(matrix, TransformationMode.FastTransformation));
	}

}

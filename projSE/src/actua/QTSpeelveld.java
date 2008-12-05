package actua;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QGraphicsScene;
import com.trolltech.qt.gui.QGraphicsView;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QWidget;

public class QTSpeelveld extends GSpeelveld {
	private QGraphicsView speelveld;

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super();
		setSpeelveld(new QGraphicsView());
		init();
	}

	private void init() {
		QGraphicsScene scene = new QGraphicsScene();
		scene.addText("TEST");

		speelveld.setGeometry(0, 0, 800, 600);
		speelveld.setMaximumSize(new QSize(800, 600));
		speelveld.setMinimumSize(new QSize(800, 600));
		
		speelveld.setScene(scene);
		speelveld.setBackgroundBrush(new QBrush(new QPixmap("classpath:background.xpm")));
		speelveld.resize(new QSize(1024, 800));
		speelveld.setCacheMode(new QGraphicsView.CacheMode(
	                QGraphicsView.CacheModeFlag.CacheBackground));
		speelveld.setViewportUpdateMode(QGraphicsView.ViewportUpdateMode.FullViewportUpdate);
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

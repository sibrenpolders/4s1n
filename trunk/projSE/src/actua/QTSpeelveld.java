package actua;

import com.trolltech.qt.core.QSize;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QWidget;

public class QTSpeelveld extends GSpeelveld {
	private QWidget speelveld;

	public QTSpeelveld(Spel spel, OptieVerwerker opties) {
		super();
		speelveld = new QWidget();
		init();
	}

	private void init() {
		speelveld.resize(new QSize(1024, 800));
		QHBoxLayout hbox = new QHBoxLayout();		
		QLabel label = new QLabel("FAILURE");
		hbox.addWidget(label);
		
		speelveld.setLayout(hbox);
	}

	public void hide() {
		
	}

	public void show() {

	}

	public QWidget getSpeelveld() {
		return speelveld;
	}

	public void setSpeelveld(QWidget speelveld) {
		this.speelveld = speelveld;
	}

}

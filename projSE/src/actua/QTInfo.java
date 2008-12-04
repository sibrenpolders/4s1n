package actua;

import com.trolltech.qt.gui.QBrush;
import com.trolltech.qt.gui.QHBoxLayout;
import com.trolltech.qt.gui.QImage;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QPalette;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QTextBlock;
import com.trolltech.qt.gui.QWidget;

public class QTInfo extends GInfo {
	private QWidget qtInfo;
	
	public QTInfo(Spel spel, OptieVerwerker opties) {
		super(spel, opties);
		qtInfo = new QWidget();		
		QPalette palette = new QPalette();
		
		QHBoxLayout hbox = new QHBoxLayout();		
		QLabel label = new QLabel("SUCCES");
		hbox.addWidget(label);
		
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


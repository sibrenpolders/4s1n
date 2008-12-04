package actua;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QApplication.initialize(args);
		
		QMainWindow w=new QMainWindow();
		
		QTMenubalk sam=new QTMenubalk(w);
		
		w.showMaximized();
		
		QApplication.exec();
	}
}

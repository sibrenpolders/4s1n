package actua;

import com.trolltech.qt.gui.QApplication;
import com.trolltech.qt.gui.QPushButton;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QApplication.initialize(args);
		
		QPushButton hello = new QPushButton("Hello World!");
		hello.resize(120, 40);
		hello.setWindowTitle("Hello World");
		hello.show();
		
		QApplication.exec();
	}
}

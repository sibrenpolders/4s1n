package actua;

import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSpinBox;
import com.trolltech.qt.gui.QWidget;

public class QTInitSpel extends GInitSpel{
	QMainWindow venster; 
	

	public QTInitSpel(Spel spel) {
		super(spel);
		venster = new QMainWindow();
		QWidget widget=new QWidget(venster);
		QGridLayout layout=new QGridLayout(widget);
		QPushButton begin= new QPushButton("Begin",widget);
		QLabel aantal = new QLabel("Aantal Tegels:");
		QSpinBox tegels = new QSpinBox(widget);
		
		for(int i = 0; i < 5; ++i)
			spelerOptieVeld(layout, i);
		
		layout.addWidget(aantal,7,4);
		tegels.setValue(72);
		layout.addWidget(tegels,7,5);
		layout.addWidget(begin,9,5);
		
		venster.setCentralWidget(widget);
	}

	@Override
	public void hide() {
		venster.hide();	
	}

	@Override
	public void show() {
		venster.show();	
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}
	
	private void spelerOptieVeld(QGridLayout layout,int i){
		QLabel labelNaam=new QLabel("Naam:");
		QLabel labelKleur=new QLabel("Kleur:");
		QLabel labelSoort=new QLabel("Soort:");
		QLineEdit naam=new QLineEdit("Speler" + (i+1));
		QComboBox kleur=new QComboBox();
		QComboBox soort=new QComboBox();
		
		kleur.addItem("Rood"); // normaal kleur QIcon of zo
		kleur.addItem("Blauw");
		kleur.addItem("Geel");
		kleur.addItem("Wit");
		kleur.addItem("Oranje");
		
		soort.addItem("Gesloten");
		soort.addItem("Mens");
		soort.addItem("Easy");
		soort.addItem("Hard");				
		
		layout.addWidget(labelNaam, i, 0);
		layout.addWidget(naam, i, 1);
		layout.addWidget(labelKleur, i, 2);
		layout.addWidget(kleur, i, 3);
		layout.addWidget(labelSoort, i, 4);
		layout.addWidget(soort, i, 5);
	}

}

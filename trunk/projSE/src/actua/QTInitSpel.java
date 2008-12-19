package actua;

import com.trolltech.qt.gui.QColor;
import com.trolltech.qt.gui.QComboBox;
import com.trolltech.qt.gui.QErrorMessage;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QIcon;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPixmap;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSpinBox;
import com.trolltech.qt.gui.QWidget;

public class QTInitSpel extends GInitSpel{
	QMainWindow venster; 
	QLineEdit[] naam = new QLineEdit[5];
	QComboBox[] kleur = new QComboBox[5];
	QComboBox[] soort = new QComboBox[5];
	QSpinBox tegels;

	public QTInitSpel(Spel spel) {
		super(spel);
		venster = new QMainWindow();
		QWidget widget = new QWidget(venster);
		QGridLayout layout = new QGridLayout(widget);
		QPushButton begin = new QPushButton("Begin",widget);
		QPushButton annuleer = new QPushButton("Annuleer",widget);
		QLabel aantal = new QLabel("Aantal Tegels:");
		tegels = new QSpinBox(widget);
		
		for(int i = 0; i < 5; ++i)
			spelerOptieVeld(layout, i);
		
		layout.addWidget(aantal,7,4);
		tegels.setValue(72);
		layout.addWidget(tegels,7,5);
		layout.addWidget(begin,9,4);
		layout.addWidget(annuleer,9,5);
		
		annuleer.clicked.connect(this.venster, "close()");
		begin.clicked.connect(this, "begin()");
		
		venster.setCentralWidget(widget);
		venster.setWindowTitle("Begin een nieuw Spel");
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
		if(!checkSoort()){
			foutDialoog("Te weinig spelers");
			return;
		}else if(!checkNaam()){
			foutDialoog("Twee of meerdere spelers hebben dezelfde naam");
			return;
		}else if(!checkKleur()){
			foutDialoog("Twee of meerdere spelers hebben dezelfde kleur");
			return;
		}
		
		for(int i = 0; i < 5; ++i){
			if(!soort[i].currentText().equals("Inactief")){
				this.voegSpelerToe(naam[i].text(), kleur[i].currentText().charAt(0), soort[i].currentText());
			}
		}
		
		venster.close();
	}
	
	private boolean checkSoort(){
		int numSpelers = 0;
		
		for(int i = 0; i < 5; ++i){
			if( !soort[i].currentText().equals("Inactief"))
				++numSpelers;
		}
		
		return numSpelers >= 2;
	}

	private boolean checkNaam(){
		String naamSpeler = new String();
		
		for(int i = 0; i < 5; ++i){
			if( !soort[i].currentText().equals("Inactief")){
				naamSpeler = naam[i].text();
				for(int j = 0; j < 5; ++j){
					if( !soort[j].currentText().equals("Inactief") )
						if( j != i && naamSpeler.equals(naam[j].text()))
							return false;
				}
			}
		}
			
		return true;	
	}
	
	private boolean checkKleur(){
		String kleurSpeler = new String();
		
		for(int i = 0; i < 5; ++i){
			if( !soort[i].currentText().equals("Inactief")){
				kleurSpeler = kleur[i].currentText();
				for(int j = 0; j < 5; ++j){
					if( !soort[j].currentText().equals("Inactief"))
						if( j != i && kleurSpeler.equals(kleur[j].currentText()))
							return false;
				}
			}
		}
			
		return true;	
	}
	
	private void foutDialoog(String fout){
		QErrorMessage dialoog = new QErrorMessage();
		
		dialoog.setWindowTitle("Fout 404");
		dialoog.showMessage(fout);
	}
	
	private void spelerOptieVeld(QGridLayout layout,int i){
		QLabel labelNaam = new QLabel("Naam:");
		QLabel labelKleur = new QLabel("Kleur:");
		QLabel labelSoort = new QLabel("Soort:");
		QPixmap pixmap = new QPixmap(16,16);
		naam[i] = new QLineEdit("Speler" + (i+1));
		kleur[i] = new QComboBox();
		soort[i] = new QComboBox();
		QIcon kleurIcon;
		
		pixmap.fill(new QColor(255,0,0));
		kleurIcon = new QIcon(pixmap);	
		kleur[i].addItem(kleurIcon,"Rood");
		//----------
		pixmap.fill(new QColor(0,0,255));
		kleurIcon = new QIcon(pixmap);	
		kleur[i].addItem(kleurIcon,"Blauw");
		//----------
		pixmap.fill(new QColor(255,255,0));
		kleurIcon = new QIcon(pixmap);		
		kleur[i].addItem(kleurIcon,"Geel");
		//----------
		pixmap.fill(new QColor(255,255,255));
		kleurIcon = new QIcon(pixmap);	
		kleur[i].addItem(kleurIcon,"Wit");
		//----------
		pixmap.fill(new QColor(255,160,0));
		kleurIcon = new QIcon(pixmap);		
		kleur[i].addItem(kleurIcon,"Oranje");
		
		soort[i].addItem("Inactief");
		soort[i].addItem("Mens");
		soort[i].addItem("Easy");
		soort[i].addItem("Hard");				
		
		layout.addWidget(labelNaam, i, 0);
		layout.addWidget(naam[i], i, 1);
		layout.addWidget(labelKleur, i, 2);
		layout.addWidget(kleur[i], i, 3);
		layout.addWidget(labelSoort, i, 4);
		layout.addWidget(soort[i], i, 5);
	}

}

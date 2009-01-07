package GUI;

import java.util.Vector;

import Opties.Optie;
import Opties.OptieVerwerker;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.QCheckBox;
import com.trolltech.qt.gui.QGridLayout;
import com.trolltech.qt.gui.QLabel;
import com.trolltech.qt.gui.QLineEdit;
import com.trolltech.qt.gui.QMainWindow;
import com.trolltech.qt.gui.QPushButton;
import com.trolltech.qt.gui.QSpinBox;
import com.trolltech.qt.gui.QWidget;

public class QTOptie extends GOptie {
	private QMainWindow opties;
	private QGridLayout layout;
	private QWidget venster;
	private Vector<QCheckBox> checks;
	private Vector<QLineEdit> edits;
	private Vector<QSpinBox> spins;

	public QTOptie(OptieVerwerker ov) {
		super(ov);
		checks = new Vector<QCheckBox>();
		edits = new Vector<QLineEdit>();
		spins = new Vector<QSpinBox>();

		opties = new QMainWindow();
		opties.setWindowTitle("Opties");
		createVenster();
	}

	// OPBOUW van de WIDGET

	private void createVenster() {
		int nbOpties = optieVerwerker.getNbOpties();

		venster = new QWidget();
		layout = new QGridLayout(venster);

		for (int i = 0; i < nbOpties; ++i) {
			maakOptieveld(i, layout);
		}

		QPushButton cancel = new QPushButton();
		QPushButton save = new QPushButton();
		cancel.setText("Cancel");
		save.setText("Save");
		save.clicked.connect(this, "save()");
		cancel.clicked.connect(this, "cancel()");
		layout.addWidget(cancel, nbOpties, 0);
		layout.addWidget(save, nbOpties, 1);

		venster.setLayout(layout);
		opties.setCentralWidget(venster);
	}

	private void maakOptieveld(int i, QGridLayout layout) {
		String naam = optieVerwerker.getNaam(i);
		Optie.TYPE type = optieVerwerker.getType(i);

		QLabel label = new QLabel();
		label.setText(naam + ":");
		layout.addWidget(label, i, 0);

		if (type == Optie.TYPE.BOOL)
			setBooleanVeld(i, layout);
		else if (type == Optie.TYPE.NUM)
			setNumVeld(i, layout);
		else if (type == Optie.TYPE.TEXT)
			setTextVeld(i, layout);
	}

	private void addToVectors(QLineEdit edit, QSpinBox spin, QCheckBox check) {
		edits.add(edit);
		spins.add(spin);
		checks.add(check);
	}

	private void setBooleanVeld(int i, QGridLayout layout) {
		QCheckBox check = new QCheckBox();
		check.setChecked(optieVerwerker.getWaarde(i).equals("true"));
		layout.addWidget(check, i, 1);
		if (i < checks.size())
			checks.set(i, check);
		else
			addToVectors(null, null, check);
	}

	private void setNumVeld(int i, QGridLayout layout) {
		QSpinBox spin = new QSpinBox();
		spin.setValue(Integer.parseInt(optieVerwerker.getWaarde(i)));
		spin.setMaximum(999);
		layout.addWidget(spin, i, 1);
		if (i < spins.size())
			spins.set(i, spin);
		else
			addToVectors(null, spin, null);
	}

	private void setTextVeld(int i, QGridLayout layout) {
		QLineEdit edit = new QLineEdit();
		edit.setText(optieVerwerker.getWaarde(i));
		layout.addWidget(edit, i, 1);
		if (i < edits.size())
			edits.set(i, edit);
		else
			addToVectors(edit, null, null);
	}

	// SAVE en CANCEL

	protected void cancel() {
		int nbOpties = optieVerwerker.getNbOpties();

		for (int i = 0; i < nbOpties; ++i) {
			Optie.TYPE type = optieVerwerker.getType(i);

			if (type == Optie.TYPE.BOOL)
				setBooleanVeld(i, layout);
			else if (type == Optie.TYPE.NUM)
				setNumVeld(i, layout);
			else if (type == Optie.TYPE.TEXT)
				setTextVeld(i, layout);
		}

		hide();
	}

	protected void save() {
		int nbOpties = optieVerwerker.getNbOpties();

		for (int i = 0; i < nbOpties; ++i) {
			Optie.TYPE type = optieVerwerker.getType(i);

			if (type == Optie.TYPE.BOOL)
				saveBooleanVeld(i, layout);
			else if (type == Optie.TYPE.NUM)
				saveNumVeld(i, layout);
			else if (type == Optie.TYPE.TEXT)
				saveTextVeld(i, layout);
		}

		hide();
		optieVerwerker.schrijfNaarBestand();
	}

	private void saveBooleanVeld(int i, QGridLayout layout) {
		QCheckBox check = checks.elementAt(i);
		if (check != null) {
			String value = (check.checkState() == Qt.CheckState.Checked) ? "true"
					: "false";
			optieVerwerker.veranderOptie(new String[] {
					optieVerwerker.getNaam(i), value });
		}
	}

	private void saveNumVeld(int i, QGridLayout layout) {
		QSpinBox spin = spins.elementAt(i);
		if (spin != null) {
			String value = Integer.toString(spin.value());
			optieVerwerker.veranderOptie(new String[] {
					optieVerwerker.getNaam(i), value });
		}
	}

	private void saveTextVeld(int i, QGridLayout layout) {
		QLineEdit edit = edits.elementAt(i);
		if (edit != null) {
			String value = edit.text();
			optieVerwerker.veranderOptie(new String[] {
					optieVerwerker.getNaam(i), value });
		}
	}

	public void hide() {
		opties.hide();
	}

	public void show() {
		opties.show();
	}
}

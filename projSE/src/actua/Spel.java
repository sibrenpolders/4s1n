package actua;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class Spel implements Serializable{
	private static final long serialVersionUID = 2689906234458876556L;
	private TafelVerwerker tafelVerwerker;
	// waarom heeft een spel een bestandsverwerken nodig?
	//hij heeft er geen nodig
	private SpelerVerwerker spelerVerwerker;

	public Spel() {
		tafelVerwerker = new TafelVerwerker();
		spelerVerwerker = new SpelerVerwerker();
	}

	public TafelVerwerker getTafelVerwerker () {
		return tafelVerwerker;
	}

	public SpelerVerwerker getSpelerVerwerker () {
		return spelerVerwerker;
	}
	
	 private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		 out.writeObject(tafelVerwerker);
		 out.writeObject(spelerVerwerker);
	 }
	 
	 private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		 tafelVerwerker = (TafelVerwerker) in.readObject();
		 spelerVerwerker = (SpelerVerwerker) in.readObject();
	 }
	 
	 private void readObjectNoData() throws ObjectStreamException {
		 
	 }
}

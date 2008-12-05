package actua;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Vector;

public class OptieVerwerker {
	protected static String OPTIE_BESTAND;
	protected Vector<Optie> opties;

	public OptieVerwerker() {
		opties = new Vector<Optie>();

		try {
			leesUitBestand(OPTIE_BESTAND);
		} catch (IOException e) {
			// <opties> blijft een lege vector
		}
	}

	protected void finalize() throws Throwable {
		super.finalize();
		schrijfNaarBestand();
	}

	/**
	 * @return null, als de optie met de gegeven naam niet bestaat; de waarde
	 *         van die optie in het andere geval.
	 */
	public String getWaarde(String naam) {
		for (int i = 0; i < opties.size(); ++i) {
			Optie optie = opties.get(i);
			if (optie.getNaam().compareTo(naam) == 0)
				return optie.getWaarde();
		}

		return null;
	}

	public String getNaam(int i) {
		if (i < 0 || i >= opties.size())
			throw new IllegalArgumentException();

		return opties.get(i).getNaam();
	}

	public String getWaarde(int i) {
		if (i < 0 || i >= opties.size())
			throw new IllegalArgumentException();

		return opties.get(i).getWaarde();
	}

	private void setWaarde(String naam, String waarde) {
		boolean veranderd = false;
		for (int i = 0; i < opties.size() && !veranderd; ++i) {
			Optie optie = opties.get(i);
			if (optie.getNaam().compareTo(naam) == 0) {
				optie.setWaarde(waarde);
				veranderd = true;
			}
		}

		if (!veranderd)
			try {
				addOptie(naam, waarde);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	private void addOptie(Optie optie) throws Exception {
		if (getWaarde(optie.getNaam()) != null)
			throw new Exception("Optie " + optie.getNaam()
					+ " is reeds aanwezig.");

		opties.add(optie);
	}

	private void addOptie(String naam, String waarde) throws Exception {
		if (getWaarde(naam) != null)
			throw new Exception("Optie " + naam + " is reeds aanwezig.");

		opties.add(new Optie(naam, waarde));
	}

	public void veranderOptie(String[] nieuw) {
		if (nieuw.length != 2)
			throw new IllegalArgumentException();

		String waarde = getWaarde(nieuw[0]);
		if (waarde != null && waarde == nieuw[1])
			;
		else
			setWaarde(nieuw[0], nieuw[1]);

	}

	public void veranderOpties(String[][] nieuweOpties) {
		opties.clear();

		for (int i = 0; i < nieuweOpties.length; ++i)
			veranderOptie(nieuweOpties[i]);
	}

	private class OptieParser {
		public Optie zetOmInOptie(String regel)
				throws InvalidParameterException {
			String delims = ":";
			String[] tokens = regel.split(delims);
			if (!isGeldigeRegel(regel))
				throw new InvalidParameterException();

			return new Optie(tokens[0], tokens[2]);
		}

		public String zetOmInTekst(Optie o) {
			return new String(o.getNaam() + "::" + o.getWaarde());
		}

		private boolean isGeldigeRegel(String regel) {
			String delims = ":";
			String[] tokens = regel.split(delims);
			if (tokens.length < 3 || tokens[1].length() != 0)
				return false;
			else
				return true;
		}
	}

	private void leesUitBestand(String fileName) throws IOException {
		if (opties == null || fileName == null)
			throw new NullPointerException();

		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = null;
		OptieParser op = new OptieParser();
		while ((line = reader.readLine()) != null) {
			try {
				Optie o = op.zetOmInOptie(line);
				opties.add(o);
			} catch (InvalidParameterException e) {
				// Optie <o> wordt niet toegevoegd aan <opties>
				;
			}
		}
		reader.close();
	}

	public void schrijfNaarBestand() throws IOException {
		schrijfNaarBestand(OPTIE_BESTAND);
	}

	public void schrijfNaarBestand(String fileName) throws IOException {
		if (fileName == null)
			throw new NullPointerException();

		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		OptieParser op = new OptieParser();

		for (int i = 0; i < opties.size(); ++i) {
			Optie o = opties.get(i);
			String line = op.zetOmInTekst(o);
			writer.write(line);
		}
		writer.close();
	}
}
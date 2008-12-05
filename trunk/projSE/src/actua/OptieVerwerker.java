package actua;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class OptieVerwerker {
	private static String OPTIE_BESTAND = "opties.txt";
	private Vector<Optie> opties;
	private String optieBestand;

	public OptieVerwerker() {
		this(OPTIE_BESTAND);
	}

	public OptieVerwerker(String bestand) {
		opties = new Vector<Optie>();
		if (bestand != null)
			optieBestand = bestand;
		else
			optieBestand = OPTIE_BESTAND;

		leesUitBestand();
	}

	protected void finalize() throws Throwable {
		super.finalize();
		schrijfNaarBestand();
	}

	public static String getDefaultOptieBestand() {
		return OPTIE_BESTAND;
	}

	public String getOptieBestand() {
		return optieBestand;
	}

	/**
	 * @return null, als de optie met de gegeven naam niet bestaat; de waarde
	 *         van die optie in het andere geval.
	 */
	public String getWaarde(String naam) {
		if (naam != null) {
			for (int i = 0; i < opties.size(); ++i) {
				Optie optie = opties.get(i);
				if (optie.getNaam().compareTo(naam) == 0)
					return optie.getWaarde();
			}
			return null;
		} else
			return null;
	}

	public String getNaam(int i) {
		if (i < 0 || i >= opties.size())
			return null;
		else
			return opties.get(i).getNaam();
	}

	public String getWaarde(int i) {
		if (i < 0 || i >= opties.size())
			return null;
		else
			return opties.get(i).getWaarde();
	}

	private void resetOpties() {
		if (opties != null)
			opties.clear();
	}

	private void setWaarde(String naam, String waarde) {
		if (naam != null && waarde != null) {
			boolean veranderd = false;
			for (int i = 0; i < opties.size() && !veranderd; ++i) {
				Optie optie = opties.get(i);
				if (optie.getNaam().compareTo(naam) == 0) {
					optie.setWaarde(waarde);
					veranderd = true;
				}
			}

			if (!veranderd)
				addOptie(naam, waarde);
		}
	}

	private void verwijderOptie(String naam) {
		if (naam != null)
			for (int i = 0; i < opties.size(); ++i) {
				Optie optie = opties.get(i);
				if (optie.getNaam().compareTo(naam) == 0) {
					opties.remove(i);
					--i;
				}
			}
	}

	private void addOptie(Optie optie) {
		if (optie != null) {
			if (getWaarde(optie.getNaam()) != null)
				verwijderOptie(optie.getNaam());

			opties.add(optie);
		}
	}

	public void addOptie(String naam, String waarde) {
		if (naam == null || waarde == null)
			return;
		else
			addOptie(new Optie(naam, waarde));
	}

	public void veranderOptie(String[] nieuw) {
		if (nieuw.length != 2)
			return;
		else {
			String waarde = getWaarde(nieuw[0]);
			if (waarde != null && waarde == nieuw[1])
				;
			else
				setWaarde(nieuw[0], nieuw[1]);
		}

	}

	public void veranderOpties(String[][] nieuweOpties) {
		resetOpties();
		for (int i = 0; i < nieuweOpties.length; ++i)
			veranderOptie(nieuweOpties[i]);
	}

	private class OptieParser {
		public Optie zetOmInOptie(String regel) {
			String delims = ":";
			String[] tokens = regel.split(delims);
			if (!isGeldigeRegel(regel))
				return null;
			else
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

	private void leesUitBestand() {
		if (opties == null || optieBestand == null)
			return;
		else {
			resetOpties();

			BufferedReader reader = null;
			String line = null;
			OptieParser op = new OptieParser();

			try {
				reader = new BufferedReader(new FileReader(optieBestand));
			} catch (FileNotFoundException e1) {
				return;
			}
			try {
				while ((line = reader.readLine()) != null) {
					Optie o = op.zetOmInOptie(line);
					opties.add(o);
				}

				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void schrijfNaarBestand() {
		if (optieBestand != null) {
			BufferedWriter writer = null;
			OptieParser op = new OptieParser();

			try {
				writer = new BufferedWriter(new FileWriter(optieBestand, false));
			} catch (IOException e) {
				return;
			}

			for (int i = 0; i < opties.size(); ++i) {
				Optie o = opties.get(i);
				String line = op.zetOmInTekst(o);
				try {
					writer.write(line);
					writer.newLine();
				} catch (IOException e) {
				}
			}
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}
}
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
		this.opties = new Vector<Optie>();
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

	public void setOptieBestand(String bestand) {
		optieBestand = bestand;
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

	public int getNbOpties() {
		return opties.size();
	}

	public Optie.TYPE getType(int i) {
		if (i < 0 || i >= opties.size())
			return null;
		else
			return opties.get(i).getType();
	}

	/**
	 * 
	 * @param naam
	 * @return -1 indien een optie met de gegeven naam niet bestaat, de index,
	 *         beginnende van nul, indien die optie wél bestaat
	 */
	public int indexVanOptie(String naam) {
		if (naam != null) {
			for (int i = 0; i < opties.size(); ++i) {
				Optie optie = opties.get(i);
				if (optie.getNaam().compareTo(naam) == 0)
					return i;
			}
			return -1;
		} else
			return -1;
	}

	/**
	 * @return null, als de optie met de gegeven naam niet bestaat; de waarde
	 *         van die optie in het andere geval.
	 */
	public String getWaarde(String naam) {
		int index;
		if ((index = indexVanOptie(naam)) != -1) {
			return getWaarde(index);
		} else
			return null;
	}

	/**
	 * @return null, als de optie met de gegeven naam niet bestaat; het type van
	 *         die optie in het andere geval.
	 */
	public Optie.TYPE getType(String naam) {
		int index;
		if ((index = indexVanOptie(naam)) != -1) {
			return getType(index);
		} else
			return null;
	}

	private void resetOpties() {
		if (opties != null)
			opties.clear();
	}

	private void setWaarde(String naam, String waarde) {
		int index;
		if ((index = indexVanOptie(naam)) != -1
				&& Optie.checkGeldigheidWaarde(getType(index), waarde)) {
			for (int i = 0; i < opties.size(); ++i) {
				Optie optie = opties.get(i);
				if (optie.getNaam().compareTo(naam) == 0)
					optie.setWaarde(waarde);
			}
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
			verwijderOptie(optie.getNaam());
			opties.add(optie);
		}
	}

	public void addOptie(String naam, Optie.TYPE t) {
		if (naam == null || t == null)
			return;
		else
			addOptie(new Optie(naam, t));
	}

	public void addOptie(String naam, Optie.TYPE t, String waarde) {
		if (naam == null || t == null || waarde == null
				|| Optie.checkGeldigheidWaarde(t, waarde) == false)
			return;
		else
			addOptie(new Optie(naam, t, waarde));
	}

	public void veranderOptie(String[] nieuw) {
		if (nieuw.length != 2 || nieuw[0] == null || nieuw[1] == null)
			return;
		else {
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
			else {
				Optie.TYPE t = Optie.checkGeldigheidType(tokens[1]);
				return new Optie(tokens[0], t, tokens[2]);
			}
		}

		public String zetOmInTekst(Optie o) {
			return new String(o.getNaam() + ":" + o.getType() + ":"
					+ o.getWaarde());
		}

		private boolean isGeldigeRegel(String regel) {
			String delims = ":";
			String[] tokens = regel.split(delims);
			if (tokens.length < 3 || tokens[0].length() == 0)
				return false;
			else {
				Optie.TYPE t = Optie.checkGeldigheidType(tokens[1]);
				return t != null && Optie.checkGeldigheidWaarde(t, tokens[2]);
			}
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
					if (o != null)
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
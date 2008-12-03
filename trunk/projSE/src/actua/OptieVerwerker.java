package actua;

import java.util.Vector;

public class OptieVerwerker {
	protected static String OPTIE_BESTAND;
	protected Vector<Optie> opties;

	public OptieVerwerker() {
		opties = new Vector<Optie>();
	}

	private void addOptie(Optie optie) {
		opties.add(optie);
	}

	private void addOptie(String naam, String waarde) {
		opties.add(new Optie(naam, waarde));
	}

	public String getWaarde(String naam) {
		for (int i = 0; i < opties.size(); ++i) {
			Optie optie = opties.get(i);
			if (optie.getNaam().compareTo(naam) == 0)
				return optie.getWaarde();
		}

		return null;
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
			addOptie(naam, waarde);
	}

	public String getWaarde(int i) {
		if (i < 0 || i >= opties.size())
			throw new IllegalArgumentException();

		return opties.get(i).getWaarde();
	}

	public String getNaam(int i) {
		if (i < 0 || i >= opties.size())
			throw new IllegalArgumentException();

		return opties.get(i).getNaam();
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
			addOptie(nieuweOpties[i][0], nieuweOpties[i][1]);
	}
}
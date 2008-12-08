package actua;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class TegelFabriekBestandLezer {
	private static final int LINKS = 0;
	private static final int MIDDEN = 1;
	private static final int RECHTS = 2;
	private static final String STAD = "stad";
	private static final String WEI = "wei";
	private static final String WEG = "weg";
	private static final String KLOOSTER = "klooster";
	
	NodeList tegels;
	Node aantalElement;
	
	public TegelFabriekBestandLezer(String uri) {
		DOMParser parser = new DOMParser();
		
		try {
			parser.parse(uri);
			Document doc = parser.getDocument();
			
			Element head = doc.getDocumentElement();
			
			aantalElement = head.getElementsByTagName("aantal").item(0);
			tegels = head.getElementsByTagName("tegel");
		} catch(Exception e) {
			System.err.println("Error in parsing: " + e.getMessage());
		}
	}
	
	public int getAantalTegels() {
		if (aantalElement == null) {
			return -1;
		}
		
		return Integer.parseInt(aantalElement.getTextContent());
	}
	
	public char[] getLandsdeelMatrix(int tegelNummer) {
		if (tegelNummer < 0 || tegelNummer >= tegels.getLength()) {
			return null;
		}
		
		return maakLandsdeelMatrix(tegels.item(tegelNummer));
	}

	private char[] maakLandsdeelMatrix(Node tegelElement) {
		Node noord = null, oost = null, zuid = null, west = null, midden = null;
		NodeList children = tegelElement.getChildNodes();
		Node tmp;
		String nodeName;
		
		for(int i = 0; i < children.getLength(); ++i) {
			tmp = children.item(i);
			nodeName = tmp.getLocalName();
			
			if (null != nodeName && nodeName.equals("noord")) {
				noord = tmp;
			} else if (null != nodeName && nodeName.equals("oost")) {
				oost = tmp;
			} else if (null != nodeName && nodeName.equals("zuid")) {
				zuid = tmp;
			} else if (null != nodeName && nodeName.equals("west")) {
				west = tmp;
			} else if (null != nodeName && nodeName.equals("midden")) {
				midden = tmp;
			}
		}
		
		if (noord == null || oost == null || zuid == null || west == null || midden == null) {
			return null;			
		}
		
		return vulLandsdelenIn(noord, oost, zuid, west, midden);
	}
	
	private char[] vulLandsdelenIn(Node noord, Node oost, Node zuid, Node west, Node midden) {
		char[] landsdelen = new char[13];
		
		// de landsdelen worden met de wijzer mee ingelezen (linksboven beginnen)
		landsdelen[0]  = getLandsdeel(west, RECHTS);
		landsdelen[1]  = getLandsdeel(noord, LINKS);
		landsdelen[2]  = getLandsdeel(noord, MIDDEN);
		landsdelen[3]  = getLandsdeel(noord, RECHTS);
		landsdelen[4]  = getLandsdeel(oost, RECHTS);
		landsdelen[7]  = getLandsdeel(oost, MIDDEN);
		landsdelen[12] = getLandsdeel(oost, LINKS);
		landsdelen[11] = getLandsdeel(zuid, RECHTS);
		landsdelen[10] = getLandsdeel(zuid, MIDDEN);
		landsdelen[9]  = getLandsdeel(zuid, LINKS);
		landsdelen[8]  = getLandsdeel(west, LINKS);
		landsdelen[5]  = getLandsdeel(west, MIDDEN);
		
		// midden invullen
		landsdelen[6] = stringNaarKarakter(midden.getTextContent());
		
		return landsdelen;
	}

	private char stringNaarKarakter(String nodeValue) {
		char landsdeelKarakter = 0;
		
		if (nodeValue == null) {
			;
		} else if (nodeValue.equals(STAD)) {
			landsdeelKarakter = 's';
		} else if (nodeValue.equals(WEI)) {
			landsdeelKarakter = 'w';
		} else if (nodeValue.equals(WEG)) {
			landsdeelKarakter = 'g';
		} else if (nodeValue.equals(KLOOSTER)) {
			landsdeelKarakter = 'k';
		}
		
		return landsdeelKarakter;
	}

	private char getLandsdeel(Node windrichting, int vaknummer) {
		char landsdeel = 0;
		NodeList children = windrichting.getChildNodes();
		Node tmp;
		String nodeName;
		
		boolean landsdeelGevonden = false;
		
		for (int i = 0; !landsdeelGevonden && i < children.getLength(); ++i) {
			tmp = children.item(i);
			nodeName = tmp.getLocalName();
			
			if (vaknummer == LINKS && null != nodeName && nodeName.equals("links")) {
				landsdeelGevonden = true;
				landsdeel = stringNaarKarakter(tmp.getTextContent());
			} else if (vaknummer == MIDDEN && null != nodeName && nodeName.equals("midden")) {
				landsdeelGevonden = true;
				landsdeel = stringNaarKarakter(tmp.getTextContent());
			} else if (vaknummer == RECHTS && null != nodeName && nodeName.equals("rechts")) {
				landsdeelGevonden = true;
				landsdeel = stringNaarKarakter(tmp.getTextContent());
			}  
		}
		
		return landsdeel;
	}
}

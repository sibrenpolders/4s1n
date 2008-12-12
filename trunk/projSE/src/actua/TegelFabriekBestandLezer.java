package actua;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

public class TegelFabriekBestandLezer {
	private static final String STAD = "stad";
	private static final String WEI = "wei";
	private static final String WEG = "weg";
	private static final String KLOOSTER = "klooster";
	private static final String KRUISPUNT = "kruispunt";
	
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
	
	public String[] getTegelStrings(int tegelNummer) {		
		return maakTegelStrings(tegels.item(tegelNummer));
	}
	
	private String[] maakTegelStrings(Node tegelElement) {
		NodeList children = tegelElement.getChildNodes();
		String[] NW, N, NO, ON, O, OZ, ZO, Z, ZW, WZ, W, WN, M;
		NW = N = NO = ON = O = OZ = ZO = Z = ZW = WZ = W = WN = M = null;
		String nodeName;
		Node tmp;
		
		for(int i = 0; i < children.getLength(); ++i) {
			tmp = children.item(i);
			nodeName = tmp.getLocalName();
			
			if (null != nodeName && nodeName.equals("noordWest")) {
				NW = getString(tmp);
			} else if( null != nodeName && nodeName.equals("noord")) {
				N = getString(tmp);
			} else if( null != nodeName && nodeName.equals("noordOost")) {
				NO = getString(tmp);
			} else if( null != nodeName && nodeName.equals("oostNoord")) {
				ON = getString(tmp);
			} else if( null != nodeName && nodeName.equals("oost")) {
				O = getString(tmp);
			} else if( null != nodeName && nodeName.equals("oostZuid")) {
				OZ = getString(tmp);
			} else if( null != nodeName && nodeName.equals("zuidOost")) {
				ZO = getString(tmp);
			} else if( null != nodeName && nodeName.equals("zuid")) {
				Z = getString(tmp);
			} else if( null != nodeName && nodeName.equals("zuidWest")) {
				ZW = getString(tmp);
			} else if( null != nodeName && nodeName.equals("westZuid")) {
				WZ = getString(tmp);
			} else if( null != nodeName && nodeName.equals("west")) {
				W = getString(tmp);
			} else if( null != nodeName && nodeName.equals("westNoord")) {
				WN = getString(tmp);
			} else if( null != nodeName && nodeName.equals("midden")) {
				M = getString(tmp);
			}  
		}
		
		String[] tegelStrings = new String[2];
		if (NW != null && N != null && NO != null && ON != null && O != null &&  
			OZ != null && ZO != null && Z != null && ZW != null && WZ != null &&  
			W != null && WN != null && M != null)
		tegelStrings[0] = WN[0]+NW[0]+N[0]+NO[0]+ON[0]+W[0]+M[0]+O[0]+WZ[0]+ZW[0]+Z[0]+ZO[0]+OZ[0]; 
		tegelStrings[1] = WN[1]+NW[1]+N[1]+NO[1]+ON[1]+W[1]+M[1]+O[1]+WZ[1]+ZW[1]+Z[1]+ZO[1]+OZ[0];
		
		return tegelStrings;
	}
	
	private String[] getString(Node tmp) {
		NodeList children = tmp.getChildNodes();
		String[] string = new String[2];
		Node tmp2;
		String type = null;
		
		for (int i = 0; i < children.getLength(); ++i) {
			tmp2 = children.item(i);
			
			if (tmp2.getLocalName().equals(STAD)) {
				string[0] = "s";
				type = STAD;
			} else if (tmp2.getLocalName().equals(WEI)) {
				string[0] = "w";
				type = WEI;
			} else if (tmp2.getLocalName().equals(WEG)) {
				string[0] = "g";
				type = WEG;
			} else if (tmp2.getLocalName().equals(KLOOSTER)) {
				string[0] = "k";
				type = KLOOSTER;
			}  else if (tmp2.getLocalName().equals(KRUISPUNT)) {
				string[0] = "r";
				type = KRUISPUNT;
			}
			
			NamedNodeMap attr = tmp2.getAttributes();
			for (int j = 0; j < attr.getLength(); ++j) {
				if (attr.item(i) != null && 
						attr.item(i).getNodeName().equals("id")) {
					string[1] = attr.item(i).getNodeValue();
				}
			}
		}
		
		return string;
	}	
}

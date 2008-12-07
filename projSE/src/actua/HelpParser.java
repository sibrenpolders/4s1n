package actua;

import java.io.File;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HelpParser {
	private static String HELP_BESTAND = "help.xml";
	private String helpBestand;

	public HelpParser() {
		this(HELP_BESTAND);
	}

	public HelpParser(String bestand) {
		helpBestand = bestand;
	}

	public String getHelpBestand() {
		return helpBestand;
	}

	public void setHelpBestand(String helpBestand) {
		this.helpBestand = helpBestand;
	}

	public void parseHelpDocument(Vector<HelpItem> items) {
		try {
			String uri = "file:" + new File(helpBestand).getAbsolutePath();
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(false);
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(true);

			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(uri);
			parseKnopen(doc, items);
		} catch (Exception e) {
		}
	}

	private void parseKnopen(Node ouderKnoop, Vector<HelpItem> items) {
		if (ouderKnoop == null) {
			return;
		}
		Node e = getElement(ouderKnoop);
		NodeList nl = e.getChildNodes();
		int len = nl.getLength();
		for (int i = 0; i < len; i++) {
			Node nd = nl.item(i);
			String tagName = nd.getNodeName();
			if (tagName.equals("item")) {
				HelpItem item = parseHelpItem(nd);
				items.add(item);
			}
		}
	}

	private HelpItem parseHelpItem(Node itemKnoop) {
		HelpItem result = new HelpItem();

		String id = getAttribuut(itemKnoop, "id");
		if (id != null) {
			result.setId(id);
			NodeList nl = itemKnoop.getChildNodes();
			int len = nl.getLength();
			for (int i = 0; i < len; i++) {
				Node nd = nl.item(i);
				String name = nd.getNodeName();
				if (name.equals("tag")) {
					String tag = getValue(nd);
					result.addTag(tag);
				} else if (nd.getNodeName().equals("uitleg")) {
					String uitleg = getValue(nd);
					result.setUitleg(uitleg);
				}
			}
		}

		return result;
	}

	private Node getElement(Node knoop) {
		NodeList nl = knoop.getChildNodes();
		int len = nl.getLength();
		for (int i = 0; i < len; i++) {
			Node n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				return n;
			}
		}
		return null;
	}

	private String getAttribuut(Node knoop, String naam) {
		NamedNodeMap attrs = knoop.getAttributes();
		int len = attrs.getLength();
		if (len > 0) {
			int i;
			for (i = 0; i < len; i++) {
				Node attr = attrs.item(i);
				if (naam.equals(attr.getNodeName())) {
					return attr.getNodeValue().trim();
				}
			}
		}
		return "";
	}

	private String getValue(Node knoop) {
		NodeList nl = knoop.getChildNodes();
		int len = nl.getLength();
		for (int i = 0; i < len; i++) {
			Node n = nl.item(i);
			short type = n.getNodeType();
			if (type == Node.TEXT_NODE) {
				return n.getNodeValue().trim();
			}
		}
		return "";
	}
}

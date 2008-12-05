package actua;

import java.util.Vector;

public class HelpItem {
	private Vector<String> tags;
	private String uitleg;
	private String id;

	public HelpItem() {
		tags = new Vector<String>();
	}

	public void addTag(String tag) {
		tags.add(tag);
	}

	public String[] getTags() {
		return (String[]) tags.toArray();
	}

	public String getUitleg() {
		return uitleg;
	}

	public void setUitleg(String uitleg) {
		this.uitleg = uitleg;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean bevatTag(String tag) {
		for (int i = 0; i < tags.size(); ++i)
			if (tags.get(i).compareToIgnoreCase(tag) == 0)
				return true;
		return false;
	}
}

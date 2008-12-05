package actua;

import java.util.Vector;

public class HelpItem {
	private Vector<String> tags;
	private String uitleg;

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
}

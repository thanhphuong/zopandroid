package vn.fiosoft.settings;

public class SettingItem {

	private String text;	
	private String label;

	public SettingItem(String text, String label) {
		this.text = text;
		this.label = label;		
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}

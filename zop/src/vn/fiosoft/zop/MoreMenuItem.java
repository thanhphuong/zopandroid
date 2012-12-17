package vn.fiosoft.zop;

public class MoreMenuItem {

	private String text;
	private boolean isActive;

	public MoreMenuItem(String text, boolean isActive) {
		this.text = text;
		this.isActive = isActive;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}

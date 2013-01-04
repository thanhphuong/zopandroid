package vn.fiosoft.feature;

public class FeatureItem {
	private int imageResourceId;
	private String text;

	public FeatureItem(String text, int imageResourceId) {
		this.imageResourceId = imageResourceId;
		this.text = text;
	}

	public int getImageResourceId() {
		return imageResourceId;
	}

	public void setImageResourceId(int imageResourceId) {
		this.imageResourceId = imageResourceId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}

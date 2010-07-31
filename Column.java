package ail;

public class Column {
	public String name;
	public boolean hasWidth;
	public int preferredWidth;
	public boolean editable;
	
	public Column(String newName) {
		name = newName;
	}
	
	public void setWidth(int newWidth) {
		hasWidth = true;
		preferredWidth = newWidth;
	}
}

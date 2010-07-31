package ail;

public class Column {
	public String name;
	public SizeParameter preferred, minimum, maximum;
	public boolean editable;
	
	public Column(String newName) {
		name = newName;
		
		preferred = new SizeParameter();
		minimum = new SizeParameter();
		maximum = new SizeParameter();
	}
}

package ail;

public class SizeParameter {
	public boolean isSet;
	public int size;
	
	public SizeParameter() {
		isSet = false;
	}
	
	public void setSize(int newSize) {
		isSet = true;
		size = newSize;
	}
}

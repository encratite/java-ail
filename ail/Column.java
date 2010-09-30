package ail;

import javax.swing.table.DefaultTableCellRenderer;

public class Column {
	public enum ColumnSortingType {
		normal,
		ascending,
		descending,
	}
	
	public String name;
	public SizeParameter preferred, minimum, maximum;
	public boolean editable;
	public DefaultTableCellRenderer renderer;
	public ColumnSortingType sortingType;
	public Class<?> type;
	
	public Column(String newName, Class<?> newType) {
		name = newName;
		type = newType;
		
		preferred = new SizeParameter();
		minimum = new SizeParameter();
		maximum = new SizeParameter();
		renderer = new DefaultCellRenderer();
		sortingType = ColumnSortingType.normal;
	}
	
	public void sortAscending() {
		sortingType = ColumnSortingType.ascending;
	}
	
	public void sortDescending() {
		sortingType = ColumnSortingType.descending;
	}
}

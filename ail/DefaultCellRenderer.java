package ail;

import java.util.Date;

import javax.swing.table.DefaultTableCellRenderer;

public class DefaultCellRenderer extends DefaultTableCellRenderer {
	protected void setValue(Object value) {
		if(value instanceof Date) {
			String dateString = Time.getDateString((Date)value);
			setText(dateString);
		}
		else
			super.setValue(value);
	}
}

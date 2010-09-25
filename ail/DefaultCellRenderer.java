package ail;

import java.util.Date;

import javax.swing.table.DefaultTableCellRenderer;

public class DefaultCellRenderer extends DefaultTableCellRenderer {
	protected void setValue(Object value) {
		if(value instanceof Date) {
			String dateString = Time.getDateString((Date)value);
			setText(dateString);
		}
		else if(value instanceof Icon) {
			setIcon(((Icon)value).icon);
		}
		else
			super.setValue(value);
	}
}

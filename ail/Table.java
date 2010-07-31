package ail;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Table implements TableModelListener {
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel tableModel;
	private Column[] columns;
	private Runnable selectionHandler, valueChangeHandler;
	private Object[] extendedData;
	
	public Table(Column... newColumns) {
		extendedData = new Object[0];
		selectionHandler = null;
		valueChangeHandler = null;
		columns = newColumns;
		
		tableModel = new TableModel();
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.getModel().addTableModelListener(this);
		
		SelectionHandler selectionHandler = new SelectionHandler();
		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener(selectionHandler);
		table.setSelectionModel(selectionModel);
		
		scrollPane = new JScrollPane(table);
		
		setColumnWidths();
	}
	
	private void setColumnWidths() {
		int offset = 0;
		for(Column column : columns) {
			TableColumn tableColumn = table.getColumnModel().getColumn(offset);
			if(column.preferred.isSet)
				tableColumn.setPreferredWidth(column.preferred.size);
			if(column.minimum.isSet)
				tableColumn.setMinWidth(column.minimum.size);
			if(column.maximum.isSet)
				tableColumn.setMaxWidth(column.maximum.size);
			offset++;
		}
	}
	
	public synchronized void setExtendedData(int row, Object data) {
		extendedData[row] = data;
	}
	
	public synchronized void setExtendedData(Object data) {
		extendedData[getRowCount() - 1] = data;
	}
	
	public synchronized void setRenderer(int column, TableCellRenderer renderer) {
		table.getColumnModel().getColumn(column).setCellRenderer(renderer);
	}
	
	public synchronized Object getExtendedData(int row) {
		return extendedData[row];
	}
	
	public void setEditable(int column, boolean editable) {
		columns[column].editable = editable;
	}
	
	public JScrollPane getPane() {
		return scrollPane;
	}
	
	public JTable getTable() {
		return table;
	}
	
	public void setSelectionHandler(Runnable newSelectionHandler) {
		selectionHandler = newSelectionHandler;
	}
	
	public void setValueChangeHandler(Runnable newValueChangeHandler) {
		valueChangeHandler = newValueChangeHandler;
	}
	
	public void setPreferredColumnWidth(int columnIndex, int width) {
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column = columnModel.getColumn(columnIndex);
		column.setPreferredWidth(width);
	}
	
	public void setMinimumColumnWidth(int columnIndex, int width) {
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column = columnModel.getColumn(columnIndex);
		column.setMinWidth(width);
	}
	
	public void setMaximumColumnWidth(int columnIndex, int width) {
		TableColumnModel columnModel = table.getColumnModel();
		TableColumn column = columnModel.getColumn(columnIndex);
		column.setMaxWidth(width);
	}
	
	public synchronized void addRow(Object[] row) {
		int rowCount = tableModel.getRowCount();
		Object[][] data = new Object[1][];
		data[0] = row;
		insertRows(data, rowCount);
	}
	
	public synchronized void deleteRow(int offset) {
		deleteRows(offset, 1);
	}
	
	public synchronized void setRow(Object[] row, int offset) {
		tableModel.data[offset] = row;
		tableModel.fireTableRowsUpdated(offset, offset);
	}
	
	public synchronized void insertRows(Object[][] rows, int offset) {
		int rowCount = tableModel.getRowCount();
		int columnCount = tableModel.getColumnCount();
		int newRowCount = rows.length;
		Object[][] newData = new Object[rowCount + newRowCount][columnCount];
		System.arraycopy(tableModel.data, 0, newData, 0, offset);
		System.arraycopy(rows, 0, newData, offset, newRowCount);
		System.arraycopy(tableModel.data, offset, newData, offset + newRowCount, rowCount - offset);
		tableModel.data = newData;
		tableModel.fireTableRowsInserted(offset, rowCount + newRowCount - 1);
		
		Object[] newExtendedData = new Object[rowCount + newRowCount];
		System.arraycopy(extendedData, 0, newExtendedData, 0, offset);
		System.arraycopy(extendedData, offset, newExtendedData, offset + newRowCount, rowCount - offset);
		extendedData = newExtendedData;
	}
	
	public synchronized void deleteRows(int offset, int count) {
		int rowCount = tableModel.getRowCount();
		int columnCount = tableModel.getColumnCount();
		int newRowCount = rowCount - count;
		Object[][] newData = new Object[newRowCount][columnCount];
		System.arraycopy(tableModel.data, 0, newData, 0, offset);
		System.arraycopy(tableModel.data, offset + count, newData, offset, newRowCount - offset);
		tableModel.data = newData;
		tableModel.fireTableRowsDeleted(offset, offset + count - 1);
		
		Object[] newExtendedData = new Object[newRowCount];
		System.arraycopy(extendedData, 0, newExtendedData, 0, offset);
		System.arraycopy(extendedData, offset + count, newExtendedData, offset, newRowCount - offset);
		extendedData = newExtendedData;
	}
	
	public synchronized void setRows(Object[][] rows, int offset) {
		System.arraycopy(rows, 0, tableModel.data, offset, rows.length);
		tableModel.fireTableRowsUpdated(offset, offset + rows.length - 1);
	}
	
	public synchronized void selectionChanged() {
		if(selectionHandler != null)
			selectionHandler.run();
	}
	
	public synchronized Object[][] getData() {
		return tableModel.data;
	}
	
	public synchronized int[] getSelectedRows() {
		return table.getSelectedRows();
	}
	
	public synchronized void tableChanged(TableModelEvent event) {
		if(valueChangeHandler != null)
			valueChangeHandler.run();
	}
	
	public synchronized void deleteRows(int[] rows) {
		int[] selectedRows = table.getSelectedRows();
		for(int i = 0; i < selectedRows.length; i++) {
			int row = selectedRows[i];
			for(int j = i + 1; j < selectedRows.length; j++) {
				if(selectedRows[j] > row)
					selectedRows[j]--;
			}
			deleteRow(row);
		}
	}
	
	public int getColumnCount() {
		return columns.length;
	}
	
	public int getRowCount() {
		return tableModel.data.length;
	}
	
	public Object[] getRow(int row) {
		return tableModel.data[row];
	}
	
	private class TableModel extends AbstractTableModel {
		private Object[][] data = {};
		
		public boolean isCellEditable(int row, int column) {
			return columns[column].editable;
		}
		
		public int getColumnCount() {
			return columns.length;
		}
		
		public int getRowCount() {
			return data.length;
		}
		
		public String getColumnName(int column) {
			return columns[column].name;
		}
		
		public Object getValueAt(int row, int column) {
			return data[row][column];
		}
		
		public Class<?> getColumnClass(int column) {
			return getValueAt(0, column).getClass();
		}
		
		public void setValueAt(Object value, int row, int column) {
			data[row][column] = value;
			fireTableCellUpdated(row, column);
		}
	}
	
	class SelectionHandler implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			selectionChanged();
		}
	}
}

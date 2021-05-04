package UI;

import javax.swing.table.AbstractTableModel;

public class MutationsTable extends AbstractTableModel {

	private String[] col_names ;
	private boolean[][] data;
	public MutationsTable(boolean[][] data2, String[] colum) { 

		this.data = data2; 
		this.col_names = colum;
	}


	public int getRowCount() {
		return data.length; 
	}


	public int getColumnCount() { 
		return col_names.length; 
	}


	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	public String getColumnName(int column) {
		return col_names[column]; 
	}

	public String getRowName(int column) {
		return col_names[column]; 
	}

	public Class getColumnClass(int column) {
		return getValueAt(0, column).getClass(); 
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void setValueAt(Object aValue, int row, int col) {
		if (aValue instanceof Boolean)
			data[row][col]= (boolean) aValue;
		fireTableCellUpdated(row, col);
	}

}
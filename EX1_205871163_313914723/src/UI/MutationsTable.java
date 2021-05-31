package UI;

import javax.swing.table.AbstractTableModel;

public class MutationsTable extends AbstractTableModel {

	private static MutationsTable mutations = null;
	private String[] colum;
	private boolean[][] data;

	//Constructor
	private MutationsTable() {
		String[] colum= { "BRITISH VIRUS","CHINESE VIRUS","SOUTHAFRICA VIRUS"};
		boolean[][] data = {{true, false, false}, {false, true, false}, {false, false, true}};
		this.data = data;
		this.colum = colum;
	}

	/**
	 * Getter for data.length
	 * @return: data.length, int
	 */
	public int getRowCount() {
		return data.length; 
	}

	/**
	 * Getter for col_names.length
	 * @return: col_names.length, int
	 */
	public int getColumnCount() { 
		return colum.length; 
	}

	/**
	 * Getter for data specific index from the table
	 * @param: rowIndex, int
	 *	@param: columnIndex, int
	 * @return: Object, data[rowIndex][columnIndex]
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}

	/**
	 * Getter for the column name 
	 * @param: column ,int
	 * @return: col_names[column], String
	 */
	public String getColumnName(int column) {
		return colum[column]; 
	}

	/**
	 * Getter for class by his column
	 * @param: column, int
	 * @return: class of data[rowIndex][columnIndex], Class
	 */
	public Class getColumnClass(int column) {
		return getValueAt(0, column).getClass(); 
	}

	/**
	 * Checking if we can editable  the cell
	 * @param: rowIndex, int 
	 * @param: columnIndex, int 
	 * @return: true, boolean
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	/**
	 * Set a boolean value into our data table according to the row and column
	 * @param: aValue, Object
	 * @param: col, int
	 * @param: row, int
	 */
	public void setValueAt(Object aValue, int row, int col) {
		if (aValue instanceof Boolean)
			data[row][col]= (boolean) aValue;

	}


	public boolean[][] getData() {
		return this.data;
	}

	public void setData(boolean[][] newData) {
		this.data = newData;
	}

	public String[] getColum() {
		return this.colum;
	}
	
	//getInstance
	public static MutationsTable getInstance() {
		if(mutations == null)
			mutations = new MutationsTable();
		return mutations;
	}

}

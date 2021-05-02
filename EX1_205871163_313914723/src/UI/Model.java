package UI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import Country.Map;
import Country.Settlement;

public class Model extends AbstractTableModel{
	private final String [] columnNames = {" Name", " Type", " Location", " Ramzor Color", "Number of people", " Number of vaccinate", " Linked settlements"};
	private Map mapData;

	public Model(Map mapData) {
		this.mapData = mapData;
	}

	public int getRowCount() { 
		return mapData.getSize();
	}

	public int getColumnCount () {
		return 7; 
	}
	
	public String getColumnName(int column) { 
		return columnNames[column];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Settlement settlement = mapData.at(rowIndex);
		switch(columnIndex) {
		case 0 : return settlement.getName();
		case 1 : return settlement.getType();
		case 2 : return settlement.getLocation().getPosition().toString();
		case 3 : return settlement.getRamzorColor().getColor();
		case 4 : return settlement.getTotalPersons();
		case 5 : return settlement.getTotalVaccines();
		case 6 : return settlement.printLinked();
		
		}
		
		return null;
	}
	

}

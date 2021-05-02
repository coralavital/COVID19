package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import Country.Map;
import Country.Settlement;
import IO.StatisticsFile;
import Population.Sick;
import Simulation.Clock;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public class StatisticsWindow extends JFrame {
	int row = 0;
	Object[][] data;
	static JFrame statisticFrame = new JFrame("Statistics Window");
	private JTextField textFilter;
	static TableRowSorter<Model> sorter;
	Table centerPanel;


	//---------------constructor--------------------------------------
	public StatisticsWindow(Map map, Object[][] data) {
		super("StatisticsWindow");
		statisticFrame.setLayout(new BorderLayout());

		JPanel statisticsPanel = new JPanel();

		statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.LINE_AXIS));
		JPanel southPanel =new JPanel();

		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));

		final String [] names = {"Col Select: NONE", "Col Select: Name", "Col Select: Type", "Col Select: Color", "Col Select: LinkTo"};
		JComboBox combo = new JComboBox<String>(names);
		statisticsPanel.add(combo);
		//filter for ComboBox
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == combo) {
					//					 switch (combo.getItemAt(combo.getSelectedIndex())) {
					//		                case "Col Select: NONE": panel.color = null; break;
					//		                case "Col Select: Name": panel.color = Color.RED; break;
					//		                case "Col Select: Type": panel.color = Color.BLUE; break;
					//		                case "Col Select: Color": panel.color = Color.GREEN; break;
					//		                case "Col Select: LinkTo": panel.color = Color.BLACK; break;
					//		            }
				}

			}
		});
		statisticsPanel.add(textFilter = new JTextField("text filter...", 20));
		//filter text by input by the names of the settlements 
		textFilter.setToolTipText("Filter Name Column");
		textFilter.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) { newFilter(); }
			public void removeUpdate(DocumentEvent e) { newFilter(); }
			public void changedUpdate(DocumentEvent e) { newFilter(); }
		});

		JButton save = new JButton("Save");
		southPanel.add(save);
		//save to csv file
		save.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				try {
					new StatisticsFile();
				} 
				catch (FileNotFoundException e1) {
					e1.printStackTrace();
					System.out.println("Error at save action from StatisticsWindow");
				}

			}
		});


		JButton add = new JButton("Add Sick");
		southPanel.add(add);
		//add another 10% of sicks people
		add.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				boolean VB = false;
				boolean VC = false;
				boolean VS = false;
				Object[] vac = {false,false,false};
				row = centerPanel.getTable().getSelectedRow();
				
				for(int i = 0; i < 3; i ++) {
					if((boolean)data[row][i]) {
						vac[i] = (boolean)data[row][i];
					}
				}
				
				int choice = rand.nextInt(3 - 1 + 1) + 1;
				// need to have also the index of the selected settlement from the chart/table
				switch(choice) {
				case 1 :
					BritishVariant britishVariant = new BritishVariant();
					statisticsInitialization(map.getSettlements()[row], britishVariant);
					break;
				case 2 :
					ChineseVariant chineseVariant = new ChineseVariant();
					statisticsInitialization(map.getSettlements()[row], chineseVariant);
					break;

				case 3 :
					SouthAfricanVariant southAfricanVariant = new SouthAfricanVariant();
					statisticsInitialization(map.getSettlements()[row], southAfricanVariant);
					break;

				}
			}

		});

		JButton vaccinate = new JButton("Vaccinate");
		southPanel.add(vaccinate);
		vaccinate.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
			
				String s = JOptionPane.showInputDialog("Enter a possitive number: ");
				int i = Integer.parseInt(s);
				
				row = centerPanel.getTable().getSelectedRow();
				map.getSettlements()[row].setTotalVaccines(i);

			}
		});


		statisticFrame.add(statisticsPanel, BorderLayout.NORTH);
		centerPanel = new Table(map);

		//creating the center which is the table class above
		statisticFrame.add(centerPanel, BorderLayout.CENTER);
		statisticFrame.add(southPanel, BorderLayout.SOUTH);
		statisticFrame.pack();
		statisticFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		statisticFrame.setVisible(true);


	}
	
	private void statisticsInitialization(Settlement settlement, IVirus virus) {//BUG
		//Run on all the index in the settlement array
		for(int j=0; j < (settlement.getHealthy().size()*0.01); j++) { 
			//Run on 10% of the people in that settlement 
			//Here we got new sick person with British variant that get the element of the first healthy person from the list
			settlement.addPerson(settlement.getPeople().get(j).contagion(virus));
			settlement.getPeople().remove(j);
			settlement.getHealthy().remove(j);

		}
	}



	private void newFilter() {
		try {
			sorter.setRowFilter(RowFilter.regexFilter(textFilter.getText()));
		} catch (java.util.regex.PatternSyntaxException e) {
			// If current expression doesn't parse, don't update.
		}
	}

	//static class like in the powerpoint
	private static class Model extends AbstractTableModel{
		private final String [] columnNames = {" Name", " Type", " Location", " Ramzor Color", "Number of people", "Number of sick", "Number of vaccinate", " Linked settlements"};
		private Map mapData;

		public Model(Map mapData) {
			this.mapData = mapData;
		}

		public int getRowCount() { 
			return mapData.getSize();
		}

		public int getColumnCount() {
			return 8; 
		}

		public String getColumnName(int column) { 
			return columnNames[column];
		}

		public Class getColumnClass(int column) { 
			return getValueAt(0,column).getClass(); 
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Settlement settlement = mapData.at(rowIndex);
			switch(columnIndex) {
			case 0 : return settlement.getName();
			case 1 : return settlement.getType();
			case 2 : return settlement.getLocation().getPosition().toString();
			case 3 : return settlement.getRamzorColor().getColor();
			case 4 : return settlement.getHealthy().size();
			case 5 : return settlement.getSick().size();
			case 6 : return settlement.getTotalVaccines();
			case 7 : return settlement.printLinked();

			}

			return null;
		}



	}

	public class Table extends JPanel{
		JTable table;

		Table(Map map){

			Model model = new Model(map);
			this.table = new JTable (model);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setPreferredScrollableViewportSize(new Dimension(500 , 100));
			table.setFillsViewportHeight(true);
			table.setRowSorter(sorter = new TableRowSorter<Model>(model));

			this.add(new JScrollPane(table));
			statisticFrame.pack();
			statisticFrame.setVisible(true);


		}
		public JTable getTable() {
			return table;
		}
	}
}
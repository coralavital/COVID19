package UI;
//
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import Country.Map;
import Country.Settlement;
import IO.StatisticsFile;
import Population.Person;
import Population.Sick;
import Simulation.Clock;
import Simulation.Main;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;

/**
 * Here we get the statistic stats values and show them in out GUI
 * @author coral
 *
 */
public class StatisticsWindow extends JFrame {

	public static JFrame statisticFrame = new JFrame("Statistics Window");
	private JTextField textFilter;
	private TableRowSorter<Model> sorter;
	Model model;
	private JTable table;

	//the categories inside the chart
	private final String [] columnNames = { "NAME", "TYPE", "LOCATION", "RAMZOR COLOR", "NUMBER OF PEOPLE", "NUMBER OF VACCINATE",
			"LINKED SETTLEMENT","NUMBER OF SICK","NUMBER OF NON-SICK"};

	/**
	 * used for filtering the stasts table by text
	 */
	private void newFilter() {
		try {
			sorter.setRowFilter(RowFilter.regexFilter(textFilter.getText()));
		} catch (java.util.regex.PatternSyntaxException e) {
			// If current expression doesn't parse, don't update.
		}

	}

	/**
	 * A builder that initializes the entire statistics window
	 */
	public StatisticsWindow() {

		super("StatisticsWindow");
		statisticFrame.setLayout(new BorderLayout());

		JPanel statisticsPanel = new JPanel();

		statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.LINE_AXIS));
		JPanel southPanel =new JPanel();

		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));

		  //categories inside the comboBox
		final String [] names = {"Col Select: NONE", "Col Select: CITY", "Col Select: MOSHAV", "Col Select: KIBBUTZ", "Col Select: GREEN",
				"Col Select: YELLOW", "Col Select: ORANGE", "Col Select: RED"};
		JComboBox combo = new JComboBox<String>(names);
		statisticsPanel.add(combo);

		//filter for ComboBox
		combo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == combo) {

					switch(combo.getItemAt(combo.getSelectedIndex()).toString()) {
					case "Col Select: NONE":sorter.setRowFilter(RowFilter.regexFilter("")); break;
					case "Col Select: CITY": {
						sorter.setRowFilter(RowFilter.regexFilter("CITY"));
						break; }
					case "Col Select: MOSHAV": {
						sorter.setRowFilter(RowFilter.regexFilter("MOSHAV"));
						break; }
					case "Col Select: KIBBUTZ": {
						sorter.setRowFilter(RowFilter.regexFilter("KIBBUTZ"));
						break; }
					case "Col Select: GREEN": {
						sorter.setRowFilter(RowFilter.regexFilter("GREEN"));
						break; }
					case "Col Select: YELLOW": {
						sorter.setRowFilter(RowFilter.regexFilter("YELLOW"));
						break; }
					case "Col Select: ORANGE": {
						sorter.setRowFilter(RowFilter.regexFilter("ORANGE"));
						break; }
					case "Col Select: RED": {
						sorter.setRowFilter(RowFilter.regexFilter("RED"));
						break; }
					}
					model.fireTableDataChanged();
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
		 //save into csv file the currect stats table
		save.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				try {
					new StatisticsFile(Main.getMap());
				} 
				catch (FileNotFoundException e1) {
					e1.printStackTrace();
					System.out.println("Error at save action from StatisticsWindow");
				}

			}
		});

		JButton add = new JButton("Add Sick");
		southPanel.add(add);
		  //infect 10% non-sicks people to the selected settlement
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				String selectedName = (String) table.getValueAt(table.getSelectedRow(), 0);
				IVirus virus;

				 /**
			     * running on all the settlements if we found what the user selected then add a random virus into their people
			     * if he didnt selected then we wont be able to add sick people
			     */
				for(int i = 0; i < Main.getMap().getSettlements().length; i++) {
					if(Main.getMap().getSettlements()[i].getName() == selectedName) {
						for(int j = 0; j < Main.getMap().getSettlements()[i].getNonSick().size()*0.01; j++) {
							int index = rand.nextInt(Main.getMap().getSettlements()[i].getNonSick().size());
							Person person = Main.getMap().getSettlements()[i].getNonSick().get(index);
							int value = rand.nextInt(3);
							if(value == 1)
								virus = new BritishVariant();
							else if(value == 2)
								virus = new ChineseVariant();
							else
								virus = new SouthAfricanVariant();

							Sick sick = new Sick(person.getAge(), person.getLocation(), person.getSettlement(), Clock.now(), virus);
							Main.getMap().getSettlements()[i].getSick().add(sick);
							Main.getMap().getSettlements()[i].getNonSick().remove(j);

						}
						model.fireTableDataChanged();
					}
				}
			}

		});


		this.model = new Model();
		table = new JTable (model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
		table.setPreferredScrollableViewportSize(new Dimension(500 , 100));
		table.setFillsViewportHeight(true);
		table.setRowSorter(sorter = new TableRowSorter<Model>(model));			



		JButton vaccinate = new JButton("Vaccinate");
		 /**
		   *  adding the number we got from the user as a vaccines to the settlement 
		   */
		vaccinate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					String s = JOptionPane.showInputDialog("ENTER A NUMBER: ");
					int i = Integer.parseInt(s);
					//System.out.println(i);
					int row = table.getSelectedRow();
					
					Main.getMap().getSettlements()[row].setTotalVaccines(i);
					
					model.fireTableDataChanged();					

				} catch (Exception e2) {
					JOptionPane.showConfirmDialog(statisticFrame, "Invalid input", "Error", JOptionPane.DEFAULT_OPTION);
				}
			}
			
		});


		southPanel.add(vaccinate);
		statisticFrame.add(statisticsPanel, BorderLayout.NORTH);
		//creating the center which is the table class above

		statisticFrame.add(new JScrollPane(table), BorderLayout.CENTER);
		statisticFrame.add(southPanel, BorderLayout.SOUTH);
		statisticFrame.pack();
		statisticFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		statisticFrame.setVisible(true);

	}

	/**
	 * getter function for table 
	 * @return:Object, JTable
	 */
	public JTable getTable() {
		return table;
	}

	 /**
	  * getter function for model 
	  * @return:Object, model
	  */
	public Model getModel() {
		return this.model;
	}

	/**
	 * inner class which provides what we will have inside the stats table
	 * @author yonif
	 *
	 */
	public class Model extends AbstractTableModel {

		public int getRowCount() { 
			return Main.getMap().getSize();
		}
		
		//giving the size of ampunt of column to the stats table
		public int getColumnCount () {
			return 9; 
		}

		public String getColumnName(int column) { 
			return columnNames[column];
		}
		
		
		/**
		 * @param: int, rowIndex and columnIndex
		 * @return:Object, case 0: settlement name, case 1 settlement type by String, case 2  settlement position, case 3 settlement ramzor color, case 4 the amount of people inside the settlement,
		 * case 5 the amount of vaccines inside that settlement, case 6 the linked settlements to the current settlement, case 7 the amount of the sick people in that settlement, case 8 the mount of non sick 
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			Settlement settlement = Main.getMap().at(rowIndex);
			switch(columnIndex) {
			case 0 : return settlement.getName();
			case 1 : return settlement.getType();
			case 2 : return settlement.getLocation().getPosition().toString();
			case 3 : return settlement.getRamzorColor().getColorOfGuitar();
			case 4 : return settlement.getPeople().size();
			case 5 : return settlement.getTotalVaccines();
			case 6 : return settlement.printLinked();
			case 7:return settlement.getSick().size();
			case 8:return settlement.getNonSick().size();

			}
			return null;
		}

	}

}
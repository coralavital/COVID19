package UI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MulticastSocket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import Country.Map;
import Country.Settlement;
import IO.SimulationFile;
import IO.StatisticsFile;
import Population.Person;
import Population.Sick;
import Population.Vaccinated;
import Simulation.Clock;
import Simulation.Main;
import UI.MainWindow.MapPanel;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;

/*
 * Representation of a Main class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */
public class MainWindow extends JFrame {

	//Private Data Members 
	private StatisticsWindow statistics;
	private Map mapPointer;
	private UserMenu userMenu;
	private SimulationFile simulationFile;
	private JSlider slider = new JSlider();
	private  MapPanel mapPanel;

	//Static Data Members
	static boolean data[][] = {{true,false,false}, {false,true,false}, {false, false, true}};


	//Constructor
	/**
	 * The constructor that initializes the entire main window and creates from it an object in front of which our main function will work
	 */
	public MainWindow() {
		super("Main");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		mapPanel=  new MapPanel();
		setJMenuBar(userMenu = new UserMenu(this,mapPanel));//User menu
		// create mapPanel
		mapPanel.setPreferredSize(new Dimension(600, 600));
		mapPanel.setBackground(Color.WHITE);
		this.add(mapPanel); 

		//Creating JSlider
		JLabel label = new JLabel("Current value: " + getJSlider().getValue());

		//Set
		slider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				int value = getJSlider().getValue();
				label.setText("Current value: " + value);
			}

		});

		this.add(getJSlider());
		this.add(label);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		//this.simulation = new Simulation();

	}

	//getter and setter
	/**
	 * getter function for userMenu
	 * @return: object, usermenu
	 */
	public UserMenu getUserMenu() {
		return this.userMenu;
	}
	//Setter for mapPointer
	public void setMapPointer(Map map) {
		this.mapPointer = map;
	}
	//Getter for mapPointer
	public Map getMapPointer() {
		return this.mapPointer;
	}
	//Setter for statistics
	public void setStatistics(StatisticsWindow statistics) {
		this.statistics = statistics;
	}
	//Getter for statistics
	public StatisticsWindow getStatistics() {
		return this.statistics;
	}
	//Getter for simulationFile
	public SimulationFile getSimulationFile() {
		return this.simulationFile;
	}
	/**
	 * getter function for mapPanel
	 * @return: object, mapPanel
	 */
	public MapPanel getMapPanel() {
		return this.mapPanel;
	}
	/**
	 * getter function for slider
	 * @return: object, slider
	 */
	public JSlider getJSlider() {
		return slider;
	}
	/**
	 * getter for data which is the mutation table
	 * @return: boolean, data
	 */
	public static boolean[][] getData() {
		return data;
	}
	/**
	 * setter for data which is the mutation table
	 * @param boolean, data
	 */
	public void setData(boolean[][] data) {
		this.data = data;
	}



	// Inner class in MainWidow class
	//MapPanel
	public  class MapPanel extends JPanel {
		private Shape rect;
		private ArrayList<Shape> rectangles;

		/**
		 * Each rectangles is a settlement and if we clicked on settlement k will be the index of the settlement inside the 
		 * Stats Table, then we will open the statistics window with mentioning the k settlement clicked
		 */
		MapPanel(){
			repaint();//In order to update the GUI drawing
			addMouseListener((MouseListener) new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					for(Shape rect:rectangles) {

						Point p = e.getPoint();

						if(rect.contains(p)) {

							String sPoint = rect.toString();
							int k =- 1;
							int i;
							for(i =0; i < getMapPointer().getSettlements().length; i++) {
								if(getMapPointer().getSettlements()[i].getLocation().checkRect().equals(sPoint))
									k = i;
							}
							if (k >= 0) {

								setStatistics(new StatisticsWindow());
								getStatistics().getTable().getSelectionModel().setSelectionInterval(0,k);

							}

						}

					}
				}

			});
		}

		//The method that actually draws the map in our main frame
		protected void paintComponent(Graphics gr) {
			if(getUserMenu().getFlag()) {
				Graphics2D g = (Graphics2D)gr;
				rectangles = new ArrayList<>();
				int x,y,width,height;
				int x1,x2,y1,y2;
				int i = 0;
				String sName = "";


				super.paintComponent(g); //Clears the last paint

				//Draw the connection lines
				for(int l = 0;l < getMapPointer().getSettlements().length;++l){
					for(int j = 0; j < getMapPointer().getSettlements()[l].getLinkTo().size(); j++) {
						x1 = getMapPointer().getSettlements()[l].getLocation().getPosition().getX();
						y1 = getMapPointer().getSettlements()[l].getLocation().getPosition().getY();
						x2 = getMapPointer().getSettlements()[j].getLocation().getPosition().getX();
						y2 = getMapPointer().getSettlements()[j].getLocation().getPosition().getX();
						g.drawLine(x1,y1,x2,y2);
					}
				}

				//Go on all settlements and paint them with its color and name -> settlements list
				//For each settlement we will create a rectangle, lets assume there are 10 settlements
				while (i < getMapPointer().getSettlements().length) {

					sName = getMapPointer().getSettlements()[i].getName();
					x = getMapPointer().getSettlements()[i].getLocation().getPosition().getX();
					y = getMapPointer().getSettlements()[i].getLocation().getPosition().getY();
					width = getMapPointer().getSettlements()[i].getLocation().getSize().getWidth();
					height = getMapPointer().getSettlements()[i].getLocation().getSize().getHeight();
					g.setColor(getMapPointer().getSettlements()[i].calcuateRamzorGrade().getColorEnum());

					rect = new Rectangle(x,y,width,height);
					g.draw(rect); //Draw the settlement rectangle
					g.fill(rect); //Paint the rectangle with the settlement color
					FontMetrics fm = g.getFontMetrics();
					int stringx = x + (width - fm.stringWidth(sName)) / 2;; //Determine the X coordinate for the text
					int stringy = y + ((height - fm.getHeight()) / 2) + fm.getAscent(); //Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
					g.setColor(Color.BLACK);
					g.drawString(sName,stringx,stringy); //Draw the settlement name
					rectangles.add(rect); //Adds to the rectangles list
					++i;

					//Initialize x, y, width,height, color, name of the settlement

				}

				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}

			else
				return;
		}
	}
	//RowedTableScroll
	public class RowedTableScroll extends JScrollPane {
		private class RowHeaderRenderer extends JLabel
		implements ListCellRenderer<String> {
			RowHeaderRenderer(JTable table) {
				setOpaque(true);
				setBorder(UIManager.getBorder("TableHeader.cellBorder"));
				setHorizontalAlignment(CENTER);
				JTableHeader header = table.getTableHeader();
				setForeground(header.getForeground());
				setBackground(header.getBackground());
				setFont(header.getFont());
			}

			public Component getListCellRendererComponent(JList<? extends String> list,
					String value, int index, boolean isSelected, boolean cellHasFocus) {
				setText((value == null) ? "" : value.toString());
				return this;
			}
		}

		public RowedTableScroll(final JTable table, final String[] rowHeaders) {
			super(table);
			final JList<String> rowHeader = new JList<String>(new AbstractListModel<String>() {
				public int getSize() {
					return Math.min(rowHeaders.length, table.getRowCount());
				}
				public String getElementAt(int index) {
					return rowHeaders[index];
				}
			});

			rowHeader.setFixedCellWidth(50);
			rowHeader.setFixedCellHeight(table.getRowHeight());
			rowHeader.setCellRenderer(new RowHeaderRenderer(table));
			this.setRowHeaderView(rowHeader);
		}

	}
	//MutationsTable
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
	//StatisticsWindow
	public class StatisticsWindow extends JFrame {

		public JFrame statisticFrame = new JFrame("Statistics Window");
		private JTextField textFilter;
		private TableRowSorter<Model> sorter;
		Model model;
		private JTable table;

		//the categories inside the chart
		private final String [] columnNames = { "NAME", "TYPE", "LOCATION", "RAMZOR COLOR", "NUMBER OF PEOPLE", "NUMBER OF VACCINATE",
				"LINKED SETTLEMENT","NUMBER OF SICK","NUMBER OF NON-SICK", "NUMBER OF DEAD"};

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
						new StatisticsFile(getMapPointer());
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
					String selectedName = (String) table.getValueAt(table.getSelectedRow(), 0);
					for(int i = 0; i < getMapPointer().getSettlements().length; i++) {
						if(getMapPointer().getSettlements()[i].getName() == selectedName)
							getMapPointer().getSettlements()[i].addSick();
					}
					model.fireTableDataChanged();
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

						getMapPointer().getSettlements()[row].setTotalVaccines(i);

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
				return getMapPointer().getSize();
			}

			//giving the size of ampunt of column to the stats table
			public int getColumnCount () {
				return 10; 
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
				Settlement settlement = getMapPointer().at(rowIndex);
				switch(columnIndex) {
				case 0 : return settlement.getName();
				case 1 : return settlement.getType();
				case 2 : return settlement.getLocation().getPosition().toString();
				case 3 : return settlement.getRamzorColor().getColorOfGuitar();
				case 4 : return settlement.getSick().size() + settlement.getNonSick().size();
				case 5 : return settlement.getTotalVaccines();
				case 6 : return settlement.printLinked();
				case 7 : return settlement.getSick().size();
				case 8 : return settlement.getNonSick().size();
				case 9 : return settlement.getNumberOfDead();

				}
				return null;
			}

		}

	}
	//UserMenu
	/**
	 * UserMenu class which contain all the options and thier click event
	 * in each evenet we added setVisible according to the assigment and thier task
	 * @author coral
	 *
	 */
	public class UserMenu extends JMenuBar {	

		private boolean flag;

		/**
		 * op1: file menu, op2: simulation menu, op3: help menu
		 */
		JMenu op1;
		JMenu op2;
		JMenu op3;

		/**
		 * f1: load, f2: statistic window, f3: edit mutation window, f4: exit
		 * l1:play, l2: pause, l3: stop, l4: set ticks per day
		 * h1:help, h2:about
		 */
		JMenuItem f1, f2, f3, f4, f5;
		JMenuItem l1, l2, l3, l4;
		JMenuItem h1, h2;

		public UserMenu(JFrame frame, MapPanel mapPanel) {
			// create a menu
			op1 = new JMenu("FILE");
			op2 = new JMenu("SIMULATION");
			op3 = new JMenu("HELP");


			// create menuitems
			f1 = new JMenuItem("LOAD");

			f1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					f1.setEnabled(false);
					f2.setEnabled(true);
					l3.setEnabled(true);
					l1.setEnabled(true);
					l2.setEnabled(false);

					FileDialog fd = new FileDialog(frame, "PLEASE CHOOSE A FILE:", FileDialog.LOAD);
					fd.setVisible(true);

					if (fd.getFile() == null)
						return;
					File f = new File(fd.getDirectory(), fd.getFile());
					simulationFile = new SimulationFile(f.getPath());
					System.out.println(f.getPath());

					try {
						//our final map
						final Map map = new Map(getSimulationFile().readFromFile());
						setMapPointer(map);

						for(int i = 0; i < getMapPointer().getSettlements().length; i++) {
							//need to check if working
							getMapPointer().getSettlements()[i].setTotalPersons((int)((getMapPointer().getSettlements()[i].getSick().size() + getMapPointer().getSettlements()[i].getSick().size()) * 1.3));
							getMapPointer().getSettlements()[i].setMap(getMapPointer());
							getMapPointer().getSettlements()[i].setflagToDead(false);
						}

						getMapPanel().repaint();
						//Update of the relevant flag
						flag = true;
						//pointer to the relevant map

						getMapPointer().setON(true);


						getMapPointer().setCyclic(getMapPointer().getSettlements().length, new Runnable() {

							public void run() {
								synchronized(getMapPointer()) {
									getMapPanel().repaint();
									getStatistics().getModel().fireTableDataChanged();
									Clock.nextTick();

									try {
										Thread.sleep(getJSlider().getValue() * 1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						});

					} 
					catch (Exception e1) {

						JOptionPane.showConfirmDialog(frame, "INVALID FILE", "ERROR", JOptionPane.DEFAULT_OPTION);
					}

				}

			});

			f2 = new JMenuItem("STATISTICS");
			f2.setEnabled(false);

			f2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					statistics = new StatisticsWindow();
				}
			});


			f3 = new JMenuItem("EDIT MUTATIONS");
			f3.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {


					String colum[]={ "BRITISH VIRUS","CHINESE VIRUS","SOUTHAFRICA VIRUS"};
					String row[]={ "B-virus","C-virus","S-Avirus"};
					JPanel panel= new JPanel();


					MutationsTable model = new MutationsTable(data, colum);
					JTable table = new JTable(model);
					table.setFillsViewportHeight(true);
					panel.add(new RowedTableScroll(table, row));


					JDialog d = new JDialog(frame,"MUTATIONS WINDOW",true);

					d.add(panel);
					d.pack();
					d.setVisible(true);
				}
			});

			f4 = new JMenuItem("SAVE LOG FILE");// to stop and then exit ( need to add setEnable for exit according to stop)
			f4.setEnabled(false);
			f4.addActionListener(new ActionListener() {


				public void actionPerformed(ActionEvent e) {
					Date date = new Date(System.currentTimeMillis()); // This object contains the current date value
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					String update;
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					jfc.setDialogTitle("Choose a directory to save your file: ");

					jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

					String str = null;

					int returnValue = jfc.showSaveDialog(null);

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						if (jfc.getSelectedFile().isDirectory()) {
							str =  jfc.getSelectedFile().toString();

							//setting name to the file
							str = str+"\\update.log";
						}
					}        

					File fos = new File(str);
					PrintWriter pw = null;
					try {
						pw = new PrintWriter(fos);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					for(int i = 0; i < getMapPointer().getSettlements().length; i++) {
						//reading from the object and writing into the file
						update = "CURENNT TIME: " + formatter.format(date) + "\nSETTLEMENT NAME: " + getMapPointer().getSettlements()[i].getName() 
								+ "\nNUMBER OF SICK: " + getMapPointer().getSettlements()[i].getSick().size()
								+ "\nNUMBER OF DEAD PEOPLE: " + getMapPointer().getSettlements()[i].getNumberOfDead();		
						pw.println(update);
					}




					pw.close();
				}
			});

			f5 = new JMenuItem("EXIT");// to stop and then exit ( need to add setEnable for exit according to stop)
			f5.setEnabled(true);
			f5.addActionListener(new ActionListener() {


				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			l1 = new JMenuItem("PLAY");

			l1.setEnabled(false);
			l1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					//Update of the relevant flag
					getMapPointer().setPLAY(true);

					synchronized(getMapPointer()) {
						getMapPointer().notifyAll();
					}

					l2.setEnabled(true);
					l1.setEnabled(false);

				}
			});



			l2 = new JMenuItem("PAUSE");

			l2.setEnabled(false);
			l2.addActionListener(new ActionListener() {


				public void actionPerformed(ActionEvent e) {
					//Update of the relevant flag
					getMapPointer().setPLAY(false);

					l2.setEnabled(false);
					l1.setEnabled(true);


				}
			});


			l3 = new JMenuItem("STOP");
			l3.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					//Update of the relevant flag
					getMapPointer().setON(false);
					getMapPointer().setPLAY(false);
					flag = false;

					f1.setEnabled(true);
					f2.setEnabled(false);
					f4.setEnabled(false);
					l1.setEnabled(false);
					l2.setEnabled(false);
					l3.setEnabled(false);

				}
			});


			l4 = new JMenuItem("SET TICKS PER DAY");

			l4.addActionListener(new ActionListener() {


				public void actionPerformed(ActionEvent e) {
					double tmp = 0;
					SpinnerNumberModel Model = new SpinnerNumberModel(1.0, 1.00, 100.00, 1.00);// start with 0, the minimum  is 0, the maximum is 0 and increase steps is 1;
					JSpinner spinner1 = new JSpinner(Model);
					int clickOK = JOptionPane.showOptionDialog(null, spinner1, "SET TICKS PER DAY WINDOW",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
					if (clickOK == JOptionPane.OK_OPTION)
						tmp = (Double) spinner1.getValue();
					int ticks = (int) tmp;
					System.out.println(ticks);
					Clock.setTicksPerDay(ticks);
				}
			});


			h1 = new JMenuItem("HELP");
			h1.addActionListener(new ActionListener() {


				public void actionPerformed(ActionEvent e) {


					JDialog dialog;

					String multiMessage = "\r\n\n As part of a study project in an advanced object-oriented programming course,\r\n"
							+ "we were asked to prepare a system for the State of Israel that experienced a health-economic crisis \r\nfollowing an outbreak of a virus that carries with it three different strains"
							+ "that spread rapidly in it.\r\n"
							+ "Unfortunately, the virus requires us to properly manage resources and information, \r\n"
							+ "with every form of settlement in Israel having a different probability of contracting the virus, \r\n"
							+ "which is so important in order to allow residents to continue moving around the country.\r\n"
							+ "The system makes it possible to select for each virus the mutations to which it can develop. \r\n"
							+ "To start the simulation, the user must upload a file from a folder of his choice, when he must make sure that the desired amount of tics is initialized in the spider \r\n"
							+ "(the amount received will eventually determine the number of simulations in one day.) \r\n"
							+ "The user has to click play to start the simulation, when after clicking this action it cannot be repeated, \r\n"
							+ "but the user will have two new options - stop and pause when if he wants to stop the current simulation he will use pause and if he wants to\r\n"
							+ "load a new simulation he will use stop. \r\n"
							+ "The purpose of the simulation is to describe a hypothetical situation of using a demo in the system and check its correctness.\r\n"
							+ "The simulation process will include the following steps:\r\n"
							+ "1. The system will sample 20 randomized patients and for each of them will try to infect with three non-sick people.\r\n"
							+ "2. In each locality, each patient who has passed 25 days from the date of his infection, the system will make him recover.\r\n"
							+ "3. In each locality, the system will sample 3% of all people (sick and not sick) \r\n"
							+ "and for each of them the system will try to make a move to a random locality.\r\n"
							+ "4. In each locality, if there are vaccine doses waiting and there are healthy people waiting, the system will vaccinate them,\r\n"
							+ "with each healthy person vaccinated with one vaccine. \r\n"
							+ "We hope that the simulation met your expectations and requirements and that the use came was understandable and simple. \r\n"
							+ "We are happy to be at your disposal for any future task required\r\n"
							+ "Editors:\r\n"
							+ "Coral Avital and Yoni Yifrach.";
					JOptionPane pane = new JOptionPane();

					pane.setMessage(multiMessage);

					dialog = pane.createDialog(null, "HELP WINDOW");
					dialog.setVisible(true);
				}
			});

			h2 = new JMenuItem("ABOUT");
			h2.addActionListener(new ActionListener() {//new


				public void actionPerformed(ActionEvent e) {
					//author details in Dialog
					JDialog dialog = new JDialog(frame,"ABOUT WINDOW" ,false);
					JLabel redMessage = new JLabel("WARNING", SwingConstants.CENTER);
					redMessage.setForeground(Color.red);
					dialog.setLayout(new GridLayout(5, 5));
					dialog.add(redMessage);
					dialog.add(new JLabel(""));
					dialog.add(new JLabel("<html><font color='red'>This is a purely educational task and does not attempt to come close to representing reality."
							+ "<br>Do not draw conclusions about the nature of the disease based on this simulation!!!"
							+ "<br>The formulas were selected according to our requirements and not according to real data.</font><html>"));
					dialog.add(new JLabel(""));
					dialog.add(new JLabel("Software developers:", SwingConstants.CENTER));
					dialog.add(new JLabel(""));
					dialog.add(new JLabel("NAME:Yoni Ifrah, ID: 313914723"));
					dialog.add(new JLabel("NAME: Coral Avital, ID: 205871163"));
					dialog.add(new JLabel(""));
					dialog.pack();
					dialog.setVisible(true);




				}
			});

			// add menu items to menu
			op1.add(f1);
			op1.add(f2);
			op1.add(f3);
			op1.add(f4);
			op1.add(f5);

			op2.add(l1);
			op2.add(l2);
			op2.add(l3);
			op2.add(l4);

			op3.add(h1);
			op3.add(h2);
			// add menu to menu bar
			this.add(op1);
			this.add(op2);
			this.add(op3);

		}

		/**
		 * getter function for flag
		 * @return: boolean, flag
		 */
		public boolean getFlag() {
			return flag;
		}

	}


}//Class MainWindow

package UI;

//Import staff
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.sound.sampled.Line;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import Country.Map;
import Country.RamzorColor;
import Country.Settlement;
import IO.SimulationFile;
import IO.StatisticsFile;
import Simulation.Clock;

/*
 * Representation of a Main class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */
public class MainWindow extends JFrame {

	// Private Data Members
	private StatisticsWindow statistics;
	private Map mapPointer;
	private UserMenu userMenu;
	private SimulationFile simulationFile;
	private JSlider slider = new JSlider();
	private MapPanel mapPanel;

	// Constructor
	/**
	 * The constructor that initializes the entire main window and creates from it
	 * an object in front of which our main function will work
	 */
	public MainWindow() {
		super("MAIN WINDOW");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		mapPanel = new MapPanel();
		setJMenuBar(userMenu = new UserMenu(this, mapPanel));// User menu
		// create mapPanel
		mapPanel.setPreferredSize(new Dimension(600, 600));
		mapPanel.setBackground(Color.WHITE);
		this.add(mapPanel);
		// Creating JSlider
		getJSlider().setValue(1);
		JLabel label = new JLabel("CURRENT VALUE: " + getJSlider().getValue());
		// Set
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = getJSlider().getValue();
				label.setText("CURRENT VALUE: " + value);
			}
		});

		// added silder to the current frame
		this.add(getJSlider());
		// added label to the current frame
		this.add(label);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// getter and setter
	/**
	 * Getter function for userMenu
	 * 
	 * @return: object, usermenu
	 */
	public UserMenu getUserMenu() {
		return this.userMenu;
	}

	// Setter for mapPointer
	/**
	 * Setter function for mapPointer
	 * 
	 * @param map, Map
	 */
	public void setMapPointer(Map map) {
		this.mapPointer = map;
	}

	// Getter for mapPointer
	/**
	 * // Getter for mapPointer
	 * 
	 * @return Map, mapPointer
	 */
	public Map getMapPointer() {
		return this.mapPointer;
	}

	// Setter for statistics
	/**
	 * Setter for statistics
	 * 
	 * @param statistics, StatisticsWindow
	 */
	public void setStatistics(StatisticsWindow statistics) {
		this.statistics = statistics;
	}

	// Getter for statistics
	/**
	 * Getter for statistics
	 * 
	 * @param statistics, StatisticsWindow
	 */
	public StatisticsWindow getStatistics() {
		return this.statistics;
	}

	// Getter for simulationFile
	/**
	 * Getter for simulation file
	 * 
	 * @return simulationFile, SimulationFile
	 */
	public SimulationFile getSimulationFile() {
		return this.simulationFile;
	}

	/**
	 * Getter function for mapPanel
	 * 
	 * @return: object, mapPanel
	 */
	public MapPanel getMapPanel() {
		return this.mapPanel;
	}

	/**
	 * Getter function for slider
	 * 
	 * @return: object, slider
	 */
	public JSlider getJSlider() {
		return slider;
	}

	// Inner class in MainWidow class
	// MapPanel
	public class MapPanel extends JPanel {
		// Data members
		private Shape rect;
		private ArrayList<Shape> rectangles;

		/**
		 * Each rectangles is a settlement and if we clicked on settlement k will be the
		 * index of the settlement inside the Stats Table, then we will open the
		 * statistics window with mentioning the k settlement clicked
		 */
		MapPanel() {
			// In order to update the GUI drawing
			repaint();
			addMouseListener((MouseListener) new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if (rectangles != null) {
						for (Shape rect : rectangles) {
							Point p = e.getPoint();
							if (rect.contains(p)) {
								String sPoint = rect.toString();
								int k = -1;
								int i;
								for (i = 0; i < getMapPointer().getSettlements().length; i++) {
									if (getMapPointer().getSettlements()[i].getLocation().checkRect().equals(sPoint))
										k = i;
								}
								if (k >= 0) {
									setStatistics(new StatisticsWindow());
									getStatistics().getTable().getSelectionModel().setSelectionInterval(0, k);
								}
							}
						}
					}
				}
			});
		}

		// The method that actually draws the map in our main frame
		protected void paintComponent(Graphics gr) {
			if (getMapPointer() != null) {
				Graphics2D g = (Graphics2D) gr;
				rectangles = new ArrayList<>();
				int x = 0, y = 0, width = 0, height = 0;
				int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
				int i = 0;
				String sName = "";
				// Clears the last paint
				super.paintComponent(g);
				// Draw the connection lines
				for (Settlement s : getMapPointer()) {
					for (int j = 0; j < s.getLinkTo().size(); j++) {
						x1 = s.getLocation().getPosition().getX();
						y1 = s.getLocation().getPosition().getY();
						x2 = s.getLinkTo().get(j).getLocation().getPosition().getX();
						y2 = s.getLinkTo().get(j).getLocation().getPosition().getY();
						// Draw the line
						new RGBDecorator(s.getRamzorColor().getColorEnum(),
								s.getLinkTo().get(j).getRamzorColor()
										.getColorEnum()) {}.setGrephicsColor(g);
						g.drawLine(x1, y1, x2, y2);

					}
				}

				// Go on all settlements and paint them with its color and name -> settlements
				// list
				// For each settlement we will create a rectangle, lets assume there are 10
				// settlements
				while (i < getMapPointer().getSettlements().length) {
					sName = getMapPointer().getSettlements()[i].getName();
					x = getMapPointer().getSettlements()[i].getLocation().getPosition().getX();
					y = getMapPointer().getSettlements()[i].getLocation().getPosition().getY();
					width = getMapPointer().getSettlements()[i].getLocation().getSize().getWidth();
					height = getMapPointer().getSettlements()[i].getLocation().getSize().getHeight();
					g.setColor(getMapPointer().getSettlements()[i].calcuateRamzorGrade().getColorEnum());
					rect = new Rectangle(x, y, width, height);
					// Draw the settlement rectangle
					g.draw(rect);
					// Paint the rectangle with the settlement color
					g.fill(rect);
					FontMetrics fm = g.getFontMetrics();
					// Determine the X coordinate for the text
					int stringx = x + (width - fm.stringWidth(sName)) / 2;
					// Determine the Y coordinate for the text (note we add the ascent, as in java
					// 2d 0 is top of the screen)
					int stringy = y + ((height - fm.getHeight()) / 2) + fm.getAscent();
					g.setColor(Color.BLACK);
					// Draw the settlement name
					g.drawString(sName, stringx, stringy);
					// Add to the rectangles list
					rectangles.add(rect);
					++i;
				}
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}
		}
	}

	// RowedTableScroll
	public class RowedTableScroll extends JScrollPane {
		private class RowHeaderRenderer extends JLabel implements ListCellRenderer<String> {
			RowHeaderRenderer(JTable table) {
				setOpaque(true);
				setBorder(UIManager.getBorder("TableHeader.cellBorder"));
				setHorizontalAlignment(CENTER);
				JTableHeader header = table.getTableHeader();
				setForeground(header.getForeground());
				setBackground(header.getBackground());
				setFont(header.getFont());
			}

			public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
					boolean isSelected, boolean cellHasFocus) {
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

	// MutationsTable
	public class MutationsTable extends AbstractTableModel {

		// Data members
		private String[] col_names = { VirusManagement.getBritishStr(), VirusManagement.getChineseStr(),
				VirusManagement.getAfricanStr() };

		/**
		 * Getter for data.length
		 * 
		 * @return: data.length, int
		 */
		public int getRowCount() {
			return VirusManagement.getData().length;
		}

		/**
		 * Getter for col_names.length
		 * 
		 * @return: col_names.length, int
		 */
		public int getColumnCount() {
			return col_names.length;
		}

		/**
		 * Getter for data specific index from the table
		 * 
		 * @param: rowIndex,    int
		 * @param: columnIndex, int
		 * @return: Object, data[rowIndex][columnIndex]
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			return VirusManagement.getData()[rowIndex][columnIndex];
		}

		/**
		 * Getter for the column name
		 * 
		 * @param: column ,int
		 * @return: col_names[column], String
		 */
		public String getColumnName(int column) {
			return col_names[column];
		}

		/**
		 * Getter for class by his column
		 * 
		 * @param: column, int
		 * @return: class of data[rowIndex][columnIndex], Class
		 */
		public Class getColumnClass(int column) {
			return getValueAt(0, column).getClass();
		}

		/**
		 * Checking if we can editable the cell
		 * 
		 * @param: rowIndex,    int
		 * @param: columnIndex, int
		 * @return: true, boolean
		 */
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		/**
		 * Set a boolean value into our data table according to the row and column
		 * 
		 * @param: aValue, Object
		 * @param: col,    int
		 * @param: row,    int
		 */
		public void setValueAt(Object aValue, int row, int col) {
			if (aValue instanceof Boolean)
				VirusManagement.getData()[row][col] = (boolean) aValue;
			fireTableCellUpdated(row, col);
		}
	}

	// StatisticsWindow
	public class StatisticsWindow extends JFrame {
		// Data members
		public JFrame statisticFrame = new JFrame("Statistics Window");
		private JTextField textFilter;
		private TableRowSorter<Model> sorter;
		public Model model;
		private JTable table;

		// The categories inside the chart
		private final String[] columnNames = { "NAME", "TYPE", "LOCATION", "RAMZOR COLOR", "NUMBER OF PEOPLE",
				"NUMBER OF VACCINATE", "LINKED SETTLEMENT", "NUMBER OF SICK", "NUMBER OF NON-SICK", "NUMBER OF DEAD" };

		/**
		 * Used for filtering the stasts table by text
		 */
		private void newFilter() {
			sorter.setRowFilter(RowFilter.regexFilter(textFilter.getText()));
		}

		/**
		 * A builder that initializes the entire statistics window
		 */
		public StatisticsWindow() {
			super("StatisticsWindow");
			statisticFrame.setLayout(new BorderLayout());
			// New JPanel for statisticsPanel
			JPanel statisticsPanel = new JPanel();
			statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.LINE_AXIS));
			// New JPanel for southPanel
			JPanel southPanel = new JPanel();
			// Set layout
			southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
			// Categories inside the comboBox
			final String[] names = { "Col Select: NONE", "Col Select: CITY", "Col Select: MOSHAV",
					"Col Select: KIBBUTZ", "Col Select: GREEN", "Col Select: YELLOW", "Col Select: ORANGE",
					"Col Select: RED" };
			JComboBox combo = new JComboBox<String>(names);
			statisticsPanel.add(combo);

			// Filter for ComboBox
			combo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == combo) {
						switch (combo.getItemAt(combo.getSelectedIndex()).toString()) {
						case "Col Select: NONE":
							sorter.setRowFilter(RowFilter.regexFilter(""));
							break;
						case "Col Select: CITY": {
							sorter.setRowFilter(RowFilter.regexFilter("CITY"));
							break;
						}
						case "Col Select: MOSHAV": {
							sorter.setRowFilter(RowFilter.regexFilter("MOSHAV"));
							break;
						}
						case "Col Select: KIBBUTZ": {
							sorter.setRowFilter(RowFilter.regexFilter("KIBBUTZ"));
							break;
						}
						case "Col Select: GREEN": {
							sorter.setRowFilter(RowFilter.regexFilter("GREEN"));
							break;
						}
						case "Col Select: YELLOW": {
							sorter.setRowFilter(RowFilter.regexFilter("YELLOW"));
							break;
						}
						case "Col Select: ORANGE": {
							sorter.setRowFilter(RowFilter.regexFilter("ORANGE"));
							break;
						}
						case "Col Select: RED": {
							sorter.setRowFilter(RowFilter.regexFilter("RED"));
							break;
						}
						}
						model.fireTableDataChanged();
					}
				}
			});

			statisticsPanel.add(textFilter = new JTextField("TEXT FILTER...", 20));
			// filter text by input by the names of the settlements
			textFilter.setToolTipText("FILTER NAME COLUMN");
			textFilter.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					newFilter();
				}

				public void removeUpdate(DocumentEvent e) {
					newFilter();
				}

				public void changedUpdate(DocumentEvent e) {
					newFilter();
				}
			});

			JButton save = new JButton("SAVE");
			southPanel.add(save);
			// save into csv file the currect stats table
			save.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					try {
						new StatisticsFile(getMapPointer(), getStatistics());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						System.out.println("ERROR AT SAVE ACTION FROM STATISTICS WINDOW");
					}

				}
			});

			JButton add = new JButton("ADD SICK");
			southPanel.add(add);
			// infect 10% non-sicks people to the selected settlement
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String selectedName = null;
					try {
						selectedName = (String) table.getValueAt(table.getSelectedRow(), 0);
						for (Settlement s : getMapPointer()) {
							if (selectedName == s.getName())
								s.addSick();
						}
						model.fireTableDataChanged();
					} catch (Exception ee) {
						JOptionPane.showConfirmDialog(statisticFrame, "PLEASE SELECT SETTLEMENT", "ERROR",
								JOptionPane.DEFAULT_OPTION);
					}
				}
			});

			this.model = new Model();
			table = new JTable(model);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setPreferredScrollableViewportSize(new Dimension(500, 100));
			table.setFillsViewportHeight(true);
			table.setRowSorter(sorter = new TableRowSorter<Model>(model));

			/**
			 * Adding the number we got from the user as a vaccines to the settlement
			 */
			JButton vaccinate = new JButton("VACCINATE");
			vaccinate.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String s = null;
					String messageError = "INVALID INPUT";
					try {

						if (table.getSelectedRow() == -1) {
							// If the user has not selected any locality to which he wants to add vaccines
							messageError = "PLEASE SELECT SETTLEMENT";
							throw new ArithmeticException(messageError);
						}
						s = JOptionPane.showInputDialog("ENTER A VALID NUMBER");
						int i = Integer.parseInt(s);
						if (i < 0) {
							// If the user selected a negative number
							messageError = "THE VALUE ENTERED IS INVALID!!\n                     TRY AGAIN";
							throw new ArithmeticException(messageError);
						}
						// Add vacinate for the chosen settlement
						getMapPointer().getSettlements()[table.getSelectedRow()].setTotalVaccines(i);
						// our model changes
						model.fireTableDataChanged();
					} catch (Exception e2) {
						// If the user selects a character that he does not number
						JOptionPane.showConfirmDialog(statisticFrame, messageError, "ERROR",
								JOptionPane.DEFAULT_OPTION);
					}
				}
			});

			// Add the Vaccines option
			southPanel.add(vaccinate);
			// Our statistics frame
			statisticFrame.add(statisticsPanel, BorderLayout.NORTH);
			statisticFrame.add(new JScrollPane(table), BorderLayout.CENTER);
			statisticFrame.add(southPanel, BorderLayout.SOUTH);
			statisticFrame.pack();
			statisticFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			statisticFrame.setVisible(true);
		}

		/**
		 * Getter function for table
		 * 
		 * @return:Object, JTable
		 */
		public JTable getTable() {
			return table;
		}

		/**
		 * Getter function for model
		 * 
		 * @return:Object, model
		 */
		public Model getModel() {
			return this.model;
		}

		/**
		 * inner class which provides what we will have inside the stats table
		 *
		 */
		public class Model extends AbstractTableModel {

			public int getRowCount() {
				return getMapPointer().getSize();
			}

			// giving the size of ampunt of column to the stats table
			public int getColumnCount() {
				return 10;
			}

			public String getColumnName(int column) {
				return columnNames[column];
			}

			/**
			 * @param: int, rowIndex and columnIndex
			 * @return:Object, case 0: settlement name, case 1 settlement type by String,
			 *                 case 2 settlement position, case 3 settlement ramzor color,
			 *                 case 4 the amount of people inside the settlement, case 5 the
			 *                 amount of vaccines inside that settlement, case 6 the linked
			 *                 settlements to the current settlement, case 7 the amount of
			 *                 the sick people in that settlement, case 8 the mount of non
			 *                 sick
			 */
			public Object getValueAt(int rowIndex, int columnIndex) {
				Settlement settlement = getMapPointer().at(rowIndex);
				switch (columnIndex) {
				case 0:
					return settlement.getName();
				case 1:
					return settlement.getType();
				case 2:
					return settlement.getLocation().getPosition().toString();
				case 3:
					return settlement.getRamzorColor().getColorOfGuitar();
				case 4:
					return settlement.getSick().size() + settlement.getNonSick().size();
				case 5:
					return settlement.getTotalVaccines();
				case 6:
					return settlement.printLinked();
				case 7:
					return settlement.getSick().size();
				case 8:
					return settlement.getNonSick().size();
				case 9:
					return settlement.getNumberOfDead();

				}
				return null;
			}
		}
	}

	// UserMenu
	/**
	 * UserMenu class which contain all the options and thier click event in each
	 * evenet we added setVisible according to the assigment and thier task
	 * 
	 * @author coral
	 *
	 */
	public class UserMenu extends JMenuBar {

		private boolean flagForLine;
		private PathTaker pathTaker = new PathTaker();
		private Originator originator = new Originator();
		private File fos = null;
		private String str = null;
		private String fileName = null;

		private int index = 0;

		/**
		 * op1: file menu, op2: simulation menu, op3: help menu
		 */
		JMenu op1;
		JMenu op2;
		JMenu op3;

		/**
		 * f1: load, f2: statistic window, f3: edit mutation window, f4: exit l1:play,
		 * l2: pause, l3: stop, l4: set ticks per day h1:help, h2:about
		 */
		JMenuItem f1, f2, f3, f4, f5, f6;
		JMenuItem l1, l2, l3, l4;
		JMenuItem h1, h2;

		public UserMenu(JFrame frame, MapPanel mapPanel) {
			// Create a menu
			op1 = new JMenu("FILE");
			op2 = new JMenu("SIMULATION");
			op3 = new JMenu("HELP");

			// Create menuitems
			f1 = new JMenuItem("LOAD");

			f1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// our error message if nothing else bothers
					String messageError = "INVALID FILE";
					try {
						// The user will be selected from where to upload the map file
						FileDialog fd = new FileDialog(frame, "CHOOSE A DIRECTORY TO SAVE YOUR FILE: ",
								FileDialog.LOAD);
						fd.setVisible(true);
						// If the user did not select any file
						if (fd.getFile() == null) {
							messageError = "YOU DIDN'T REALLY SELECT A FILE TO LOAD YOUR MAP FROM!!\n                                                       TRY AGAIN";
						}
						// Otherwise we will make sure to update all the flags and allow the options to
						// be opened to the user after loading
						else {
							f4.setEnabled(true);
							f1.setEnabled(false);
							f2.setEnabled(true);
							l3.setEnabled(true);
							l1.setEnabled(true);
							l2.setEnabled(false);
						}

						File f = new File(fd.getDirectory(), fd.getFile());
						simulationFile = new SimulationFile(f.getPath());
						System.out.println(f.getPath());
						try {
							// our final map
							final Map map = new Map(getSimulationFile().readFromFile());
							setMapPointer(map);
							getMapPointer().setON(true);
							for (int i = 0; i < getMapPointer().getSettlements().length; i++) {
								// need to check if working
								getMapPointer().getSettlements()[i].setMap(getMapPointer());
								getMapPointer().setflagToDead(false);
							}
							getMapPanel().repaint();
							// Update of the relevant flag
							flagForLine = true;
							getMapPointer().setflagToFile(false);
							// Creating Cyclic for this map
							getMapPointer().setCyclic(getMapPointer().getSettlements().length, new Runnable() {
								public void run() {
									synchronized (getMapPointer()) {
										getMapPanel().repaint();
										if (getStatistics() != null)
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
							getMapPointer().runAll();
						} catch (Exception e1) {
							// If the system failed to create a map
							JOptionPane.showConfirmDialog(frame, messageError, "ERROR", JOptionPane.DEFAULT_OPTION);
						}
					} catch (Exception e2) {
						// If the system failed to create a map
						JOptionPane.showConfirmDialog(frame, messageError, "ERROR", JOptionPane.DEFAULT_OPTION);
					}
				}
			});
			// Creat statistics
			f2 = new JMenuItem("STATISTICS");
			f2.setEnabled(false);
			f2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Creating our statistics window
					statistics = new StatisticsWindow();
				}
			});
			// Creat mutations table
			f3 = new JMenuItem("EDIT MUTATIONS");
			f3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String row[] = { "B-virus", "C-virus", "S-Avirus" };
					JPanel panel = new JPanel();
					MutationsTable model = new MutationsTable();
					JTable table = new JTable(model);
					table.setFillsViewportHeight(true);
					panel.add(new RowedTableScroll(table, row));
					// Creat a dialog
					JDialog d = new JDialog(frame, "MUTATIONS WINDOW", true);

					d.add(panel);
					d.pack();
					d.setVisible(true);
				}
			});
			// Creat log file
			f4 = new JMenuItem("SAVE LOG FILE");// to stop and then exit ( need to add setEnable for exit according to
												// stop)
			f4.setEnabled(false);
			f4.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					getMapPointer().setflagToFile(false);
					JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
					// If the user choose a folder to save the log file
					while (!getMapPointer().isflagToFile()) {
						fileName = JOptionPane.showInputDialog("PLEASE ENTER A VALID FILE NAME: ");
						if (fileName == null)
							return;
						jfc.setDialogTitle("CHOOSE A DIRECTORY TO SAVE YOUR FILE: ");
						jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnValue = jfc.showSaveDialog(null);
						if (returnValue == JFileChooser.APPROVE_OPTION) {
							if (jfc.getSelectedFile().isDirectory()) {
								str = jfc.getSelectedFile().toString();
								// Setting name to the file
								str = str + "\\" + fileName + ".log";
							}
						} else
							return;

						fos = new File(str);
						// pathTaker.addMemento(memento);
						try {
							PrintWriter pw = new PrintWriter(fos);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						getMapPointer().setflagToFile(true);
						getMapPointer().setFileName(str);
						System.out.println(str);
					}
					originator.setState(str);
					pathTaker.addMemento(originator.createMemento());
					++index;
					if (index > 1)
						f5.setEnabled(true);
				}
			});

			f5 = new JMenuItem("RESTORE LAST LOG FILE LOCATION");// to stop and then exit ( need to add setEnable for
																	// exit according to stop)
			f5.setEnabled(false);
			f5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					if (index <= 0) {
						f5.setEnabled(false);
						JOptionPane.showConfirmDialog(frame, "CANNOT UNDO A LOG FILE", "ERROR",
								JOptionPane.DEFAULT_OPTION);
					}
					if (index >= 2) {
						index = index - 2;
						originator.setMemento(pathTaker.getMemento(index));
						str = originator.getState();
						getMapPointer().setFileName(str);
						System.out.println(str);
					}
				}
			});

			// Creat exit button
			f6 = new JMenuItem("EXIT");// to stop and then exit ( need to add setEnable for exit according to stop)
			f6.setEnabled(true);
			f6.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// If user press exit the system will shut down
					System.exit(0);
				}
			});
			// Creat play button
			l1 = new JMenuItem("PLAY");
			l1.setEnabled(false);
			l1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					synchronized (getMapPointer()) {
						// Update of the relevant flag
						getMapPointer().setPLAY(true);
						// notifyAll for settlement thread
						getMapPointer().notifyAll();
					}
					l2.setEnabled(true);
					l1.setEnabled(false);
				}
			});
			// Creat pause button
			l2 = new JMenuItem("PAUSE");
			l2.setEnabled(false);
			l2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Update of the relevant flag
					synchronized (getMapPointer()) {
						// Update of the relevant flag
						getMapPointer().setPLAY(false);
					}
					l2.setEnabled(false);
					l1.setEnabled(true);
				}
			});
			// Creat stop button
			l3 = new JMenuItem("STOP");
			l3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Update of the relevant flag
					getMapPointer().setON(false);
					getMapPointer().setPLAY(false);
					getMapPointer().setflagToFile(false);
					getMapPointer().setflagToDead(false);
					flagForLine = false;
					// repaint
					setMapPointer(null);
					getMapPanel().repaint();

					f1.setEnabled(true);
					f2.setEnabled(false);
					f4.setEnabled(false);
					l1.setEnabled(false);
					l2.setEnabled(false);
					l3.setEnabled(false);
				}
			});
			// Creat set ticks per day spinner
			l4 = new JMenuItem("SET TICKS PER DAY");
			l4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					double tmp = 0;
					SpinnerNumberModel Model = new SpinnerNumberModel(1.0, 1.00, 100.00, 1.00);// start with 0, the
																								// minimum is 0, the
																								// maximum is 0 and
																								// increase steps is 1;
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

			// Creat help dialog
			h1 = new JMenuItem("HELP");
			h1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog dialog;
					String multiMessage = "\r\n\n As part of a study project in an advanced object-oriented programming course,\n"
							+ "we were asked to prepare a system for the State of Israel that experienced a health-economic crisis\n"
							+ "following an outbreak of a virus that carries with it three different strains"
							+ "that spread rapidly in it.\n"
							+ "Unfortunately, the virus requires us to properly manage resources and information,\n"
							+ "with every form of settlement in Israel having a different probability of contracting the virus,\n"
							+ "which is so important in order to allow residents to continue moving around the country.\n"
							+ "The system makes it possible to select for each virus the mutations to which it can develop.\n"
							+ "To start the simulation, the user must upload a file from a folder of his choice, when he must make sure that the desired amount of tics is initialized in the spider \n"
							+ "(the amount received will eventually determine the number of simulations in one day.) \n"
							+ "The user has to click play to start the simulation, when after clicking this action it cannot be repeated, \n"
							+ "but the user will have two new options - stop and pause when if he wants to stop the current simulation he will use pause and if he wants to\n"
							+ "load a new simulation he will use stop.\n"
							+ "The purpose of the simulation is to describe a hypothetical situation of using a demo in the system and check its correctness.\n"
							+ "The simulation process will include the following steps:\n"
							+ "1. The system will sample 20 randomized patients and for each of them will try to infect with three non-sick people.\n"
							+ "2. In each locality, each patient who has passed 25 days from the date of his infection, the system will make him recover.\n"
							+ "3. In each locality, the system will sample 3% of all people (sick and not sick)\n"
							+ "and for each of them the system will try to make a move to a random locality.\n"
							+ "4. In each locality, if there are vaccine doses waiting and there are healthy people waiting, the system will vaccinate them,\n"
							+ "with each healthy person vaccinated with one vaccine.\n"
							+ "We hope that the simulation met your expectations and requirements and that the use came was understandable and simple. \n"
							+ "We are happy to be at your disposal for any future task required\n\n" + "Release Date:\n"
							+ "27.05.2021\n\n" + "Editors:\n" + "Coral Avital & Yoni Ifrah.";
					JOptionPane pane = new JOptionPane();
					pane.setMessage(multiMessage);
					dialog = pane.createDialog(null, "HELP WINDOW");
					dialog.setVisible(true);
				}
			});
			// Creat about dialog
			h2 = new JMenuItem("ABOUT");
			h2.addActionListener(new ActionListener() {// new
				public void actionPerformed(ActionEvent e) {
					// Author details in Dialog
					JDialog dialog = new JDialog(frame, "ABOUT WINDOW", false);
					JLabel redMessage = new JLabel("WARNING", SwingConstants.CENTER);
					redMessage.setForeground(Color.red);
					dialog.setLayout(new GridLayout(5, 5));
					dialog.add(redMessage);
					dialog.add(new JLabel(""));
					dialog.add(new JLabel(
							"<html><font color='red'>This is a purely educational task and does not attempt to come close to representing reality."
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

			// Add menu items to menu
			op1.add(f1);
			op1.add(f2);
			op1.add(f3);
			op1.add(f4);
			op1.add(f5);
			op1.add(f6);

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
		 * Getter function for flag
		 * 
		 * @return: boolean, flag
		 */
		public boolean getFlagForLine() {
			return flagForLine;
		}

		/**
		 * Getter function for flag
		 * 
		 * @return: boolean, flag
		 */
		public void setFlagForLine(boolean flag) {
			this.flagForLine = flag;
		}

	}

}// Class MainWindow

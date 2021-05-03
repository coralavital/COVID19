package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import Country.Map;
import IO.SimulationFile;
import UI.MainWindow.MapPanel;

public class UserMenu extends JMenuBar {
	
	
	Object[][] data = {{false,false,false}, {false,false,false}, {false,false,false}};
	StatisticsWindow statistics;
	private boolean flag;
	
	Map map;

	
	JMenu op1;
	JMenu op2;
	JMenu op3;
	
	// Menu items
	JMenuItem f1, f2, f3, f4;
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
				SimulationFile simolation = new SimulationFile(f.getPath());
				System.out.println(f.getPath());
				simolation = new SimulationFile(f.getPath());
				try {
					map = new Map(simolation.readFromFile());
					flag = true;
					mapPanel.repaint();
					f4.setEnabled(true);
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
				
				statistics = new StatisticsWindow(map);
			}
		});


		f3 = new JMenuItem("EDIT MUTATIONS");
		f3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				
				String colum[]={ "BRITISH VIRUS","CHINESE VIRUS","SOUTHAFRICA VIRUS"};
				String row[]={ "BRITISH","CHINESE","SOUTHAFRICA"};
				JPanel panel= new JPanel();
				

				MutationsTable model = new MutationsTable(data, colum);
				JTable table = new JTable(model);
				table.setFillsViewportHeight(true);
				panel.add(new RowedTableScroll(table, row));
				JDialog d= new JDialog(frame,"MUTATIONS WINDOW",true);


					
			
				d.add(panel);
				d.pack();
				d.setVisible(true);
			}
		});

		f4 = new JMenuItem("EXIT");// to stop and then exit ( need to add setEnable for exit according to stop)
		f4.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		l1 = new JMenuItem("PLAY");
		l1.setEnabled(false);
		l1.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				l1.setEnabled(false);
				l2.setEnabled(true);
			}
		});

		l2 = new JMenuItem("PAUSE");
		l2.setEnabled(false);
		l2.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				l2.setEnabled(false);
				l1.setEnabled(true);
			}
		});

		l3 = new JMenuItem("STOP");
		l3.setEnabled(false);
		l3.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				f1.setEnabled(true);
				f2.setEnabled(false);
				l1.setEnabled(false);
				l2.setEnabled(false);
				l3.setEnabled(false);
				flag = false;
				mapPanel.repaint();
				//need to complete stop
			}
		});

		l4 = new JMenuItem("SET TICKS PER DAY");

		h1 = new JMenuItem("HELP");
		h1.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {

				JDialog dialog = new JDialog(frame,"HELP WINDOW" ,true);
				JLabel redMessage = new JLabel("AUXILIARY INFORMATION", SwingConstants.CENTER);
				redMessage.setForeground(Color.BLUE);
				dialog.setLayout(new GridLayout(5, 5));
				dialog.add(redMessage);
				dialog.add(new JLabel(""));

				
				
				dialog.add(new JLabel("<html><font color='blue'>As part of a study project in an advanced object-oriented programming course, we were"
						+ "asked to prepare a system for the State of Israel that experienced a health-economic crisis following an outbreak of a virus"
						+ "<br>that carries with it three different strains that spread rapidly in it."
						+ "<br>Unfortunately, the virus requires us to properly manage resources and information, with every form of settlement in Israel"
						+ "<br>having a different probability of contracting the virus, which is so important in order to allow residents to continue moving"
						+ "<br>around the country."
						+ "<br>The system makes it possible to select for each virus the mutations to which it can develop."
						+ "<br>To start the simulation, the user must upload a file from a folder of his choice, when he must make sure that the desired"
						+ "<br>amount of tics is initialized in the spider (the amount received will eventually determine the number of simulations in one"
						+ " day."
						+ "<br>The user has to click play to start the simulation, when after clicking this action it cannot be repeated, but the user will"
						+ "<br>have two new options - stop and pause when if he wants to stop the current simulation he will use pause and if he wants to"
						+ "<br>load a new simulation he will use stop."
						+ "<br>The purpose of the simulation is to describe a hypothetical situation of using a demo in the system and check its "
						+ "correctness."
						+ "<br>The simulation process will include the following steps:"
						+ "<br>1. The system will sample 20 randomized patients and for each of them will try to infect with three non-sick people."
						+ "<br>2. In each locality, each patient who has passed 25 days from the date of his infection, the system will make him recover."
						+ "<br>3. In each locality, the system will sample 3% of all people (sick and not sick) and for each of them the system will try to make"
						+ "<br>a move to a random locality."
						+ "<br>4. In each locality, if there are vaccine doses waiting and there are healthy people waiting, the system will vaccinate them,"
						+ "<br>with each healthy person vaccinated with one vaccine."
						+ "<br>We hope that the simulation met your expectations and requirements and that the use came was understandable and simple."
						+ "<br>We are happy to be at your disposal for any future task required"
						+ "<br>Editors:"
						+ "<br>Coral Avital and Yoni Yifrach.</font><html>"));
				dialog.add(new JLabel(""));
				dialog.pack();
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
	
	public boolean getFlag() {
		return flag;
	}
	public Map getMap() {
		return map;
	}

}
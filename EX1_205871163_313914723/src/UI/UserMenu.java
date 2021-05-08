package UI;

import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import Country.Map;
import Country.Settlement;
import IO.SimulationFile;
import Simulation.Clock;
import Simulation.Main;
import Simulation.Simulation;
import UI.MainWindow.MapPanel;
/**
 * UserMenu class which contain all the options and thier click event
 * in each evenet we added setVisible according to the assigment and thier task
 * @author coral
 *
 */
public class UserMenu extends JMenuBar {	

	boolean data[][] = {{true,false,false}, {false,true,false}, {false, false, true}};

	private boolean flag;


	SimulationFile simolation;

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
				simolation = new SimulationFile(f.getPath());
				System.out.println(f.getPath());
				simolation = new SimulationFile(f.getPath());
				try {
					Main.setMap(new Map(simolation.readFromFile()));
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

				Main.setStatistics(new StatisticsWindow());
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
				//update the relevant flags
				Main.setPLAY(true);
				Main.setON(true);
				
				//creat an object of the simulation
				Simulation s = new Simulation();
				
				//to sure the statistics frame is update

				l1.setEnabled(false);
				l2.setEnabled(true);
				
				Main.getStatistics().statisticFrame.setDefaultCloseOperation(frame.getDefaultCloseOperation());
				while(Main.isPLAY()) {
					//The role of the method is to sample 20% of patients out of all the people in localities that have already been initialized 
					//on the map and for each person who has become ill an attempt will be made to infect three different people
					//And for this purpose uses another method whose function is to try to infect a random person who is not ill
					s.initialization();
					s.recoverToHealthy();
					s.moveSettlement();
					s.vaccinateHealthy();
					
					
				}
				
				l1.setEnabled(true);
				l2.setEnabled(false);
				mapPanel.repaint();

			}
		});

		l2 = new JMenuItem("PAUSE");
		l2.setEnabled(false);
		l2.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				
				
				Main.setPLAY(false);
				l2.setEnabled(false);
				l1.setEnabled(true);


			}
		});

		l3 = new JMenuItem("STOP");
		l3.setEnabled(false);
		l3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Main.setON(false);
				Main.setPLAY(false);
				
				flag = false;

				f1.setEnabled(true);
				f2.setEnabled(false);

				l1.setEnabled(false);
				l2.setEnabled(false);
				l3.setEnabled(false);


				mapPanel.repaint();
				Main.getStatistics().statisticFrame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
				Main.setMap(null);

			}
		});

		
		l4 = new JMenuItem("SET TICKS PER DAY");

		l4.addActionListener(new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				double tmp = 0;
				SpinnerNumberModel Model = new SpinnerNumberModel(0.0, 0.00, 100.00, 1.00);// start with 0, the minimum  is 0, the maximum is 0 and increase steps is 1;
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
	//Getters
	public boolean getFlag() {
		return flag;
	}


	public boolean[][] getData() {
		return data;
	}


}
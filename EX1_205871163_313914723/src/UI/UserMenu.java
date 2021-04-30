package UI;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
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

import IO.SimulationFile;

public class UserMenu extends JMenuBar {

	JMenu op1;
	JMenu op2;
	JMenu op3;
	// Menu items
	JMenuItem f1, f2, f3, f4;
	JMenuItem l1, l2, l3, l4;
	JMenuItem h1, h2;

	public UserMenu(JFrame frame) {

		// create a menu
		op1 = new JMenu("File");
		op2 = new JMenu("Simulation");
		op3 = new JMenu("Help");

		// create menuitems
		f1 = new JMenuItem("Load");
		f1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {


				f1.setEnabled(false);
				f2.setEnabled(true);
				l3.setEnabled(true);
				l1.setEnabled(true);
				l2.setEnabled(false);

				FileDialog fd = new FileDialog(frame, "Please choose a file:", FileDialog.LOAD);
				fd.setVisible(true);

				if (fd.getFile() == null)
					return;
				File f = new File(fd.getDirectory(), fd.getFile());
				System.out.println(f.getPath());
				SimulationFile simolation = new SimulationFile(f.getPath());
				try {
					simolation.readFromFile();
					f4.setEnabled(true);
				} catch (Exception e1) {

					JOptionPane.showConfirmDialog(frame, "Invalid File", "Error", JOptionPane.DEFAULT_OPTION);
				}
			}

		});


		f2 = new JMenuItem("Statistics");
		f2.setEnabled(false);
		f3 = new JMenuItem("Edit Mutations");
		f3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {


				String colum[]={ "British virus","Chinese virus","SouthAfrica virus"};	

				JPanel panel= new JPanel();

				Object[][] data = {{false,false,false}, {false,false,false}, {false,false,false}};
				MutationsTable model = new MutationsTable(data, colum);
				JTable table = new JTable(model);
				table.setFillsViewportHeight(true);
				panel.add(new RowedTableScroll(table, colum));	
				JDialog d= new JDialog(frame,"Mutations Window",true);

				model.addTableModelListener(new TableModelListener() {

					public void tableChanged(TableModelEvent e) {
						//-----------------------
						System.out.println("click");
						// your code goes here, whatever you want to do when something changes in the table
					}
				});
				d.add(panel);
				d.pack();
				d.setVisible(true);
			}
		});

		f4 = new JMenuItem("Exit");// to stop and then exit ( need to add setEnable for exit according to stop)
		f4.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		l1 = new JMenuItem("Play");
		l1.setEnabled(false);
		l1.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				l1.setEnabled(false);
				l2.setEnabled(true);
			}
		});

		l2 = new JMenuItem("Pause");
		l2.setEnabled(false);
		l2.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				l2.setEnabled(false);
				l1.setEnabled(true);
			}
		});

		l3 = new JMenuItem("Stop");
		l3.setEnabled(false);
		l3.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				f1.setEnabled(true);
				f2.setEnabled(false);
				l1.setEnabled(false);
				l2.setEnabled(false);
				l3.setEnabled(false);

				//need to complete stop
			}
		});

		l4 = new JMenuItem("Set Ticks Per Day");

		h1 = new JMenuItem("Help");
		h1.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {

				// what the event will do

			}
		});

		h2 = new JMenuItem("About");
		h2.addActionListener(new ActionListener() {//new


			public void actionPerformed(ActionEvent e) {
				//author details in Dialog
				JDialog dialog = new JDialog(frame,"About window" ,false);
				JLabel redMessage = new JLabel("WARNING", SwingConstants.CENTER);
				redMessage.setForeground(Color.red);
				dialog.setLayout(new GridLayout(5, 5));
				dialog.add(redMessage);
				dialog.add(new JLabel(""));
				dialog.add(new JLabel("<html><font color='red'>This is a purely educational task and does not attempt to come close to representing reality.<br>Do not draw conclusions about the nature of the disease based on this simulation!!!<br>The formulas were selected according to our requirements and not according to real data.</font><html>"));
				dialog.add(new JLabel(""));
				dialog.add(new JLabel("Software developers:", SwingConstants.CENTER));
				dialog.add(new JLabel(""));
				dialog.add(new JLabel("Name:Yoni Ifrah, ID: 313914723"));
				dialog.add(new JLabel("Name: Coral Avital, ID: 205871163"));
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
}
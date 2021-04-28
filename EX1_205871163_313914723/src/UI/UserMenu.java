package UI;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
				// TODO Auto-generated method stub
				f1.setEnabled(false);
				FileDialog fd = new FileDialog(frame, "Please choose a file:", FileDialog.LOAD);
				fd.setVisible(true);

				if (fd.getFile() == null)
					return;
				File f = new File(fd.getDirectory(), fd.getFile());
				System.out.println(f.getPath());
				SimulationFile simolation = new SimulationFile(f.getPath());
				try {
					simolation.readFromFile();
					f4.setEnabled(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showConfirmDialog(frame, "Invalid File", "Error", JOptionPane.DEFAULT_OPTION);
				}
			}
			
		});
		
		
		f2 = new JMenuItem("Statistics");
		f3 = new JMenuItem("Edit Mutations");
		f4 = new JMenuItem("Exit");// to stop and then exit ( need to add setEnable for exit according to stop)

		l1 = new JMenuItem("Play");
		l2 = new JMenuItem("Pause");
		l3 = new JMenuItem("Stop");
		l3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				f4.setEnabled(true);
				//need to complete stop
			}
		});
		l4 = new JMenuItem("Set Ticks Per Day");

		h1 = new JMenuItem("Help");
		h1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// what the event will do

			}
		});
		h2 = new JMenuItem("About");

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
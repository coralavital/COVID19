package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class UserMenu extends JMenuBar implements ActionListener{
	
	static JMenuBar mb;
	  
    static JMenu op1;
    static JMenu op2;
    static JMenu op3;  
    // Menu items
    static JMenuItem f1, f2, f3, f4;
    static JMenuItem l1, l2, l3, l4;
    static JMenuItem h1, h2;
  
    // create a frame
    static JFrame f;
	
    public UserMenu() {
		f = new JFrame("User menu");

		// create a menubar
		mb = new JMenuBar();

		// create a menu
		op1 = new JMenu("File");
		op2 = new JMenu("Simulation");
		op3 = new JMenu("Help");
		
		// create menuitems
		f1 = new JMenuItem("Load");
		f2 = new JMenuItem("Statistics");
		f3 = new JMenuItem("Edit Mutations");
		f4 = new JMenuItem("Exit");

		l1 = new JMenuItem("Play");
		l2 = new JMenuItem("Pause");
		l3 = new JMenuItem("Stop");
		l4 = new JMenuItem("Set Ticks Per Day");
		
		h1 = new JMenuItem("Help");
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
		mb.add(op1);
		mb.add(op2);
		mb.add(op3);

		// add menubar to frame
		f.setJMenuBar(mb);

		// set the size of the frame
		f.setSize(500, 500);
		f.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		ActionListener buttomListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Object o = e.getSource();

				switch (o.getClass().getCanonicalName()) {
				case "f1": {
					return;
				}
				case "f2": {

					return;
				}
				case "f3": {
					return;
				}

				case "f4": {

					return;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + o);
				}

			}
		
			
		};
	}
}

    


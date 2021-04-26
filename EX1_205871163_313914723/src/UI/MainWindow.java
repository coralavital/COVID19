package UI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class MainWindow {
	
	static JFrame frame = new JFrame("Main Window");
	static JButton mapPanel=new JButton("Map Panel");
	static  JButton menu = new JButton("Menu");
	static  JButton simulationSpeed= new JButton("simulationSpeed");
	
	public static void main(String[] args) {

		BorderLayout myBorderLayout = new BorderLayout();
		myBorderLayout.setHgap(20);
		myBorderLayout.setVgap(20);
		frame.setLayout(myBorderLayout);
		frame.add(mapPanel, BorderLayout.CENTER);
		frame.add(menu, BorderLayout.NORTH);
		frame.add(simulationSpeed, BorderLayout.SOUTH);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		setUpButtonListeners();
	}
	
	public static void setUpButtonListeners() {
		ActionListener buttomListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				Object o = e.getSource();

				if(o==mapPanel) {
					
					System.out.println("1");
					
				}

				else if (o==menu) {
					UserMenu menu = new UserMenu();
					
				}

				else if (o ==simulationSpeed) {
					JSpeeder gui = new JSpeeder();
					gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					gui.setSize(300, 100);
					gui.setVisible(true);
					gui.setTitle("slider");
				}

			}
		};

		mapPanel.addActionListener(buttomListener);
		menu.addActionListener(buttomListener);
		simulationSpeed.addActionListener(buttomListener);


		
	}
}
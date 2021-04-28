package UI;
import java.awt.*;  
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;  
public class MainWindow extends JFrame {  
	
	//inner class if Map panel
	private class MapPanel extends JPanel {
		
		JPanel mapPanel;
		
		public MapPanel(JFrame frame) {
			mapPanel= new JPanel();
			
			mapPanel.setBackground(Color.white);
			mapPanel.setPreferredSize(new Dimension(600,400));
			frame.add(mapPanel);
			
		}
	}
	
	public MainWindow() {
		
		super("Main");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		setJMenuBar(new UserMenu(this));//User menu
		this.add(new MapPanel(this)); // map panel
		
		//creating JSlider
		JSlider slider = new JSlider();
		JLabel label = new JLabel("Current value: " + slider.getValue());
		
		// set
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = slider.getValue();
				label.setText("current value: " + value);
				//we will need to add sleep between the ticks
			}
			
		});
		
		this.add(slider);
		this.add(label);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	public static void main(String args[]){  
		
		MainWindow main = new MainWindow();
	}  
	
}
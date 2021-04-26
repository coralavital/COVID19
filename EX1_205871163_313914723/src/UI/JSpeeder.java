package UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

public class JSpeeder extends JFrame {

	JSlider slider;
	JLabel label;
	
	public JSpeeder() {
		setLayout(new FlowLayout());
		
		slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
		slider.setMajorTickSpacing(5);
		slider.setPaintTicks(true);
		add(slider);
		label = new JLabel("Current value: 0");
		add(label);
		
		event e = new event();
		slider.addChangeListener(e);
		
		
	}
	
	public class event implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int value = slider.getValue();
			
			label.setText("current value: " + value);
			
		}

	}
	
	
}

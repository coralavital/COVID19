package Simulation;

import java.awt.Window;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import UI.MainWindow;


/**
 * main class will run the simulation
 * @author coral
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {

		//our main window
		MainWindow main = new MainWindow();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

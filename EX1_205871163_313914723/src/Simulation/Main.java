package Simulation;

import java.awt.Window;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;
import UI.MainWindow;


/**
 * main class whihc will run the simulation
 * @author coral
 *
 */
public class Main {
	
	
	//Load function
	public static void load(MainWindow main) throws InterruptedException {

		//main.getMapPointer().runAll();


	}

	public static void main(String[] args) throws InterruptedException {
		
		//our main window
		MainWindow main = new MainWindow();
		
		//A while loop that makes sure that between each charge there is a second sleep
		while(true) {
			load(main);
			Thread.sleep(1000);
		}

	}
	


}

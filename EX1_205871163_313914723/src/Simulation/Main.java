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
		
		
		while(main.getUserMenu().isON()) {
			//When getIsON true the user press play and the simulation start 
			//s = new Simulation();

			while(main.getUserMenu().isPLAY()) {
				
			}
			
		}


	}

	public static void main(String[] args) throws InterruptedException {
		MainWindow main = new MainWindow();
		
		
		//A while loop that makes sure that between each charge there is a second sleep
		while(true) {
			load(main);
		}

	}
	


}

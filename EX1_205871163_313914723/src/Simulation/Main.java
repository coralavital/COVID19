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
	
	private MainWindow main;
	
	//Load function
	public static void load() throws InterruptedException {
		
		
		while(.getUserMenu().isON()) {
			//When getIsON true the user press play and the simulation start 
			//s = new Simulation();

			while(getMain().getUserMenu().isPLAY()) {

				getMain().getMapPointer().runAll();
				//Update the map panel according to recalculations of the data needed for drawing
				getMain().getMapPanel().repaint();
				//inc by one the time in the simulation
				Clock.nextTick();
				//The thread goes to sleep between iterations and iterations according to the value entered by the user in JSlider 
				Thread.sleep(getMain().getJSlider().getValue()*1000);
			}
			Thread.sleep(1000);
		}


	}

	public static void main(String[] args) throws InterruptedException {
		
		
		
		//A while loop that makes sure that between each charge there is a second sleep
		while(true) {
			load();
			Thread.sleep(1000);
		}

	}
	
	public MainWindow getMain() {
		return this.main;
	}

}

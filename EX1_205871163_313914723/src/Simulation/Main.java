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
/*

	MainWindow main;
	
	//Load function
	public void load() throws InterruptedException {

		while(true) {
			//When getIsON true the user press play and the simulation start 
			//s = new Simulation();

			while(isPLAY() == true) {
				
				//The role of the method is to sample 20% of patients out of all the people in localities that have already been initialized 
				//on the map and for each person who has become ill an attempt will be made to infect three different people
				//And for this purpose uses another method whose function is to try to infect a random person who is not ill
				for(int i = 0; i < .getSettlements().length; i++) {
					s.initialization(Main.getMap().getSettlements()[i]);
					s.recoverToHealthy(Main.getMap().getSettlements()[i]);
					s.moveSettlement(Main.getMap().getSettlements()[i]);
					s.vaccinateHealthy(Main.getMap().getSettlements()[i]);
				}
				//Update the map panel according to recalculations of the data needed for drawing
				Main.getMain().getMapPanel().repaint();

				//inc by one the time in the simulation
				Clock.nextTick();
				//The thread goes to sleep between iterations and iterations according to the value entered by the user in JSlider 
				Thread.sleep(Main.getMain().getJSlider().getValue()*1000);
			}

		}


	}


	public static void main(String[] args) throws InterruptedException {
		main = new MainWindow();
		
		//A while loop that makes sure that between each charge there is a second sleep
		while(true) {
			load();
			Thread.sleep(1000);
		}
		
	}
*/
}

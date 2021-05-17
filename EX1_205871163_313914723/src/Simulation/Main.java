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

	static MainWindow main = new MainWindow();

	//Load function
	public static void load() throws InterruptedException {

		while(main.getUserMenu().isON()) {
			//When getIsON true the user press play and the simulation start 
			//s = new Simulation();

			while(main.getUserMenu().isPLAY()) {

				//The role of the method is to sample 20% of patients out of all the people in localities that have already been initialized 
				//on the map and for each person who has become ill an attempt will be made to infect three different people
				//And for this purpose uses another method whose function is to try to infect a random person who is not ill
				for(int i = 0; i < main.getMapPointer().getSettlements().length; i++) {
					main.getSimulation().initialization(main.getMapPointer().getSettlements()[i]);
					main.getSimulation().recoverToHealthy(main.getMapPointer().getSettlements()[i]);
					main.getSimulation().moveSettlement(main.getMapPointer().getSettlements()[i]);
					main.getSimulation().vaccinateHealthy(main.getMapPointer().getSettlements()[i]);
					main.getSimulation().killPeople(main.getMapPointer().getSettlements()[i]);
					main.getMapPanel().repaint();
					if (main.getStatistics() != null)
						main.getStatistics().getModel().fireTableStructureChanged();
				}
				//Update the map panel according to recalculations of the data needed for drawing
				main.getMapPanel().repaint();
				//inc by one the time in the simulation
				Clock.nextTick();
				//The thread goes to sleep between iterations and iterations according to the value entered by the user in JSlider 
				Thread.sleep(main.getJSlider().getValue()*1000);
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

}

package Simulation;

import java.awt.Window;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;
import Country.Map;
import UI.MainWindow;
import UI.StatisticsWindow;


/**
 * main class whihc will run the simulation
 * @author coral
 *
 */
public class Main {

	//Static data members
	static Map map;
	static MainWindow main;
	static StatisticsWindow statistics;
	static Simulation s;
	static boolean isON, isPLAY;
	
	//Load function
	public static void load() throws InterruptedException {

		while(isON() == true) {
			//When getIsON true the user press play and the simulation start 
			s = new Simulation();

			while(isPLAY() == true) {
				
				//The role of the method is to sample 20% of patients out of all the people in localities that have already been initialized 
				//on the map and for each person who has become ill an attempt will be made to infect three different people
				//And for this purpose uses another method whose function is to try to infect a random person who is not ill
				s.initialization();
				s.recoverToHealthy();
				s.moveSettlement();
				s.vaccinateHealthy();

				//Update the map panel according to recalculations of the data needed for drawing
				Main.getMain().getMapPanel().repaint();

				//inc by one the time in the simulation
				Clock.nextTick();
				//The thread goes to sleep between iterations and iterations according to the value entered by the user in JSlider 
				Thread.sleep(Main.getMain().getJSlider().getValue()*1000);
			}

		}


	}

	/**
	 * Get map object
	 * @return map, Map Object
	 */
	public static Map getMap() {
		return map;
	}

	/**
	 * Set map object
	 * @param map, Map Object
	 */
	public static void setMap(Map map) {
		Main.map = map;
	}

	/**
	 * Get main object
	 * @return map, MainWindow Object
	 */
	public static MainWindow getMain() {
		return main;
	}

	/**
	 * Set statistics object
	 * @param map, StatisticsWindow Object
	 */
	public static void setStatistics(StatisticsWindow statistics) {
		Main.statistics = statistics;
	}

	/**
	 * Get statistics object
	 * @return map, StatisticsWindow Object
	 */
	public static StatisticsWindow getStatistics() {
		return statistics;
	}

	/**
	 * Get for the flag that indicates if the file is play
	 * @return isPLAY, boolean
	 */
	public static boolean isPLAY() {
		return isPLAY;
	}
	
	/**
	 * Set for the flag that indicates if the file is play
	 * @param isPLAY, boolean
	 */
	public static void setPLAY(boolean isPLAY) {
		Main.isPLAY = isPLAY;
	}

	/**
	 * Get for the flag that indicates if the file is loaded
	 * @return isON, boolean
	 */
	public static boolean isON() {
		return isON;
	}
	/**
	 * Set for the flag that indicates if the file is loaded
	 * @param isON, boolean
	 */
	public static void setON(boolean isON) {
		Main.isON = isON;
	}




	/**
	 * The main function of this project
	 * @param args, String[]
	 */
	public static void main(String[] args) throws InterruptedException {
		map = null;
		main = new MainWindow();
		
		setON(false);
		setPLAY(false);
		//A while loop that makes sure that between each charge there is a second sleep
		while(true) {
			load();
			Thread.sleep(1000);
		}
		
	}

}

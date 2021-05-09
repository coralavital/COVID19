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

	//static data members
	static Map map;
	static MainWindow main;
	static StatisticsWindow statistics;
	static Simulation s;
	static boolean isON, isPLAY;
	//load function
	public static void load() throws InterruptedException {

		map = null;
		main = new MainWindow();
		setON(false);
		setPLAY(false);


		while(isON == true) {
			// when getIsON true the user press play and the simulation start 

			do {
				Thread.sleep(Main.getMain().getJSlider().getValue()*1000);
			}while(!isPLAY);


			//update all
			s = new Simulation();


			//Main.getStatistics().statisticFrame.setDefaultCloseOperation(main.getDefaultCloseOperation());
			while(!isPLAY) {
			//while(isPLAY()) {
				//The role of the method is to sample 20% of patients out of all the people in localities that have already been initialized 
				//on the map and for each person who has become ill an attempt will be made to infect three different people
				//And for this purpose uses another method whose function is to try to infect a random person who is not ill
				s.initialization();
				s.recoverToHealthy();
				s.moveSettlement();
				s.vaccinateHealthy();

				Main.updateAll();
				//inc by one the time in the simulation
				Clock.nextTick();
				setPLAY(false);
			}
		}

	}

	//Get map
	public static Map getMap() {
		return map;
	}

	//Set map
	public static void setMap(Map map) {
		Main.map = map;
	}

	//get the main main
	public static MainWindow getMain() {
		return main;
	}

	//set statistiics window
	public static void setStatistics(StatisticsWindow statistics) {
		Main.statistics = statistics;
	}

	//get statistics window
	public static StatisticsWindow getStatistics() {
		return statistics;
	}


	public static boolean isPLAY() {
		return isPLAY;
	}

	public static void setPLAY(boolean isPLAY) {
		Main.isPLAY = isPLAY;
	}

	public static boolean isON() {
		return isON;
	}

	public static void setON(boolean isON) {
		Main.isON = isON;
	}

	//update all
	public static void updateAll() {

		Main.getStatistics().getModel().fireTableDataChanged();
		Main.getMain().getMapPanel().repaint();

	}



	//main function for all the system
	public static void main(String[] args) throws InterruptedException {

		load();

	}

}

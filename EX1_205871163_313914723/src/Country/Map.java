package Country;
//Import staff
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

import Simulation.Main;

/***
 * Representation of a Map class
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public class Map {

	Thread[] thread;
	private Settlement[] settlements;
	List<Settlement> setAllSettlement;
	private boolean isON = false;

	//Constructor
	/***
	 * Constructor, copy the settlement list param into settlement array
	 * @param settlements, Settlement list
	 */
	public Map(List<Settlement> settlements) {
		
		thread = new Thread[settlements.size()];
		this.settlements = new Settlement[settlements.size()]; //Allocation of a new locality
		
		//Deep copying
		for (int i = 0; i < settlements.size(); i++) {
			if (settlements.get(i) instanceof Moshav) {
				//The settlement in the index i is Moshav
				this.settlements[i] = new Moshav((Moshav) settlements.get(i));
				//creating a thread for this settlement
				this.thread[i] = new Thread(this.settlements[i]);

			}
			if (settlements.get(i) instanceof City) {
				//The settlement in the index i is City
				this.settlements[i] = new City((City) settlements.get(i));
				//creating a thread for this settlement
				this.thread[i] = new Thread(this.settlements[i]);

			}
			if (settlements.get(i) instanceof Kibbutz) {
				//The settlement in the index i is Kibbutz
				this.settlements[i] = new Kibbutz((Kibbutz) settlements.get(i));
				//creating a thread for this settlement
				this.thread[i] = new Thread(this.settlements[i]);

			}
		}
		isON = true;
	}

	//ToString
	/***
	 * ToString method of the Map object
	 * @return: String
	 */
	public String toString() {
		StringBuilder finalString= new StringBuilder();	
		for (int i = 0; i < settlements.length; i++) {
			finalString.append("Settlement["+i+"]: " + settlements[i].toString() + "\n");
		}
		return "\t\t\t\tThis map contains the settlement:\n\n" + finalString;
	}


	//Getter
	/***
	 * getter method 
	 * @return: Settlement[]
	 */
	public Settlement[] getSettlements() {
		return settlements;
	}
	
	/**
	  * converting array to list and returning it as a list
	  * @return: list, Settlement
	  */
	public List<Settlement> getAllSerttlement() {
		for(int i = 0; i < this.settlements.length; i++) {
			setAllSettlement.add(settlements[i]);
		}
		
		return setAllSettlement;
	}

	
	public Settlement at(int index) {
		return settlements[index];
	}
	
	
	public int getSize() {
		int counter = 0;
		for(int i = 0; i < settlements.length; i++) {
			counter++;
		}
		return counter;
	}
	
	/**
	  * finding the settlement by his name
	  * @param String, name
	  * @return: int, the index of the settlement by his name
	  */
	public int indexByStr(String name) {
		for (int i = 0; i < settlements.length; i++) {
			if(settlements[i].getName().equals(name))
				return i;		
		}
		System.out.println("cannot find settlement in indexByStr");
		return -1;
	}
	
	/**
	 * Get for the flag that indicates if the file is loaded
	 * @return isON, boolean
	 */
	public boolean isON() {
		return isON;
	}
	/**
	 * Set for the flag that indicates if the file is loaded
	 * @param isON, boolean
	 */
	public void setON(boolean isON) {
		isON = isON;
	}


	public void runAll() throws InterruptedException {
		for(int i = 0; i < getSettlements().length; i++)
			thread[i].start();
		this.runAll();
		Thread.sleep(1000);
	}

}//Map class


package Country;
//Import staff
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import Simulation.Clock;
import Simulation.Main;
import UI.MainWindow.MapPanel;

/***
 * Representation of a Map class
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public class Map {

	
	//Private Data Members
	private Thread[] thread;
	private Settlement[] settlements;
	private List<Settlement> setAllSettlement;
	private boolean isPLAY; 
	private boolean isON;
	private CyclicBarrier cyclic;
	private boolean flagToDead;
	private boolean flagLogFile;
	File fos = null;
	
	
	public Map() {
		this.thread = null;
		this.settlements = null;
		this.setAllSettlement = null;
		setON(false);
		setPLAY(false);
		setflagToDead(false);
		setflagLogFile(false);
	}
	
	
	//Constructor
	/***
	 * Constructor, copy the settlement list param into settlement array
	 * @param settlements, Settlement list
	 */
	public Map(List<Settlement> settlements) {
		this.settlements = new Settlement[settlements.size()]; //Allocation of a new locality
		//Deep copying
		for (int i = 0; i < settlements.size(); i++) {
			if (settlements.get(i) instanceof Moshav) {
				//The settlement in the index i is Moshav
				this.settlements[i] = new Moshav((Moshav) settlements.get(i));
			}
			if (settlements.get(i) instanceof City) {
				//The settlement in the index i is City
				this.settlements[i] = new City((City) settlements.get(i));
			}
			if (settlements.get(i) instanceof Kibbutz) {
				//The settlement in the index i is Kibbutz
				this.settlements[i] = new Kibbutz((Kibbutz) settlements.get(i));
			}
		}	
		setON(false);
		setPLAY(false);
		setflagToDead(false);
		setflagLogFile(false);
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


	//Getter and Setter
	/***
	 * getter method 
	 * @return: Settlement[]
	 */
	public Settlement[] getSettlements() {
		return settlements;
	}
	/**
	 * Get for the flag that indicates if the file is play
	 * @return isPLAY, boolean
	 */
	public boolean isPLAY() {
		return isPLAY;
	}
	/**
	 * Set for the flag that indicates if the file is play
	 * @return isPLAY, boolean
	 */
	public void setPLAY(boolean isPLAY) {
		this.isPLAY = isPLAY;
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
		this.isON = isON;
	}
	
	/**
	 * Get 
	 * @return flagToDead, boolean
	 */
	public boolean isflagToDead() {
		return flagToDead;
	}
	/**
	 * Set 
	 * @param flagToDead, boolean
	 */
	public void setflagToDead(boolean flagToDead) {
		this.flagToDead = flagToDead;
	}
	/**
	 * setter for data which is the mutation table
	 * @param boolean, data
	 */
	public void setflagLogFile(boolean flagLogFile) {
		this.flagLogFile = flagLogFile;
	}

	public boolean getFlagLogFile() {
		return this.flagLogFile;
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

	//get the settlement for the index sent
	public Settlement at(int index) {
		return settlements[index];
	}
	
	//get the size
	public int getSize() {
		int counter = 0;
		for(int i = 0; i < settlements.length; i++) {
			counter++;
		}
		return counter;
	}
	//get cyclic
	public CyclicBarrier getCyclic() {
		return this.cyclic;
	}
	
	public synchronized void setCyclic(int n, Runnable runnable) {
		
		this.cyclic = new CyclicBarrier(n, runnable);
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
	
	public void saveLogFile(File fos, PrintWriter pw) {
		this.fos = fos;
		Date date = new Date(System.currentTimeMillis()); // This object contains the current date value
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String update;
		
		
		int counter = 0;
		for(int j = 0; j < getSettlements().length; j++) {
			while(getSettlements()[j].getNumberOfDead() >= (getSettlements()[j].getSick().size() + getSettlements()[j].getNonSick().size()) * 0.01 )
				counter++;
			if(counter == getSettlements().length)
				setflagToDead(true);
			else
				setflagToDead(false);
		}

		while(getFlagLogFile() && isflagToDead()) {
			for(int i = 0; i < getSettlements().length; i++) {
				//reading from the object and writing into the file
				update = "CURENNT TIME: " + formatter.format(date) + "\nSETTLEMENT NAME: " + getSettlements()[i].getName() 
						+ "\nNUMBER OF SICK: " + getSettlements()[i].getSick().size()
						+ "\nNUMBER OF DEAD PEOPLE: " + getSettlements()[i].getNumberOfDead();		
				pw.println(update);
			}
		}
		pw.close();
	}
	
	
	//run all function
	public void runAll() {
		
		thread = new Thread[getSettlements().length];
		
		for(int i = 0; i < getSettlements().length; i++) {
			new Thread(getSettlements()[i]).start();
		}
		
	}
	
}//Map class


package Country;
//Import staff
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.io.FileWriter;

/***
 * Representation of a Map class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 */
public class Map implements Iterable<Settlement>{
	
	//Private Data Members

	private Settlement[] settlements;
	private List<Settlement> setAllSettlement;
	private boolean isPLAY; 
	private boolean isON;
	private CyclicBarrier cyclic;
	private boolean flagToDead;
	private boolean flagToFile;
	private String file;

	//Constructors
	/**
	 * Default Constructor
	 */
	public Map() {
		this.settlements = null;
		this.setAllSettlement = null;
		setON(false);
		setPLAY(false);
		setflagToDead(false);
		setflagToFile(false);
	}

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
		setflagToFile(false);
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
	 * Getter method 
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
	 * Get for a flag that belongs to each map and indicates to the system if there is a map that is loaded at that moment
	 * @return isON, boolean
	 */
	public boolean isON() {
		return isON;
	}
	
	/**
	 * Set for a flag that belongs to each map and indicates to the system if there is a map that is loaded at that moment
	 * @param isON, boolean
	 */
	public void setON(boolean isON) {
		this.isON = isON;
	}

	/**
	 * Set for a flag belonging to each map and responsible for notification when 1% of the people on the map are dead 
	 * @return flagToDead, boolean
	 */
	public boolean isflagToDead() {
		return flagToDead;
	}
	
	/**
	 * Set for a flag belonging to each map and responsible for notification when 1% of the people on the map are dead 
	 * @param flagToDead, boolean
	 */
	public void setflagToDead(boolean flagToDead) {
		this.flagToDead = flagToDead;
	}
	/**
	 * Set for a flag that belongs to each map and indicates to the system whether a folder has been selected for saving the log file
	 * @param flagToDead, boolean
	 */
	public void setflagToFile(boolean flagToFile) {
		this.flagToFile = flagToFile;
	}
	/**
	 * Get for a flag that belongs to each map and indicates to the system whether a folder has been selected for saving the log file
	 * @param flagToDead, boolean
	 */
	public boolean isflagToFile() {
		return this.flagToFile;
	}
	
	/**
	 * Setter a variable in the file name where records will be kept when 1% of people die in each simulation
	 * @param boolean, data
	 */
	public void setFileName(String str) {
		this.file = str;
	}

	/**
	 * Getter a variable in the file name where records will be kept when 1% of people die in each simulation
	 * @param boolean, data
	 */

	public String getFileName() {
		return this.file;
	}

	/**
	 * Converting array to list and returning it as a list
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

	//Get for the number of the settlements in the map
	/**
	 * Get function that return the size of the 
	 * @param n, int
	 * @param runnable, Runnable
	 */
	public int getSize() {
		int counter = 0;
		for(int i = 0; i < settlements.length; i++) {
			counter++;
		}
		return counter;
	}
	//get cyclic
	/**
	 * Getter function for CyclicBarrier
	 * @param n, int
	 * @param runnable, Runnable
	 */
	public CyclicBarrier getCyclic() {
		return this.cyclic;
	}
	
	/**
	 * Setter function for CyclicBarrier
	 * @param n, int
	 * @param runnable, Runnable
	 */
	public synchronized void setCyclic(int n, Runnable runnable) {

		this.cyclic = new CyclicBarrier(n, runnable);
	}

	/**
	 * Finding the settlement by his name
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
	 * A function that writes into a file every time 1% of the people on the map die.
	 */
	public void saveLogFile() throws IOException {
		Date date = new Date(System.currentTimeMillis()); // This object contains the current date value
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String update = null;

		File newFile = new File(this.file);
		FileWriter fw = new FileWriter(newFile, true);
			for(int i = 0; i < getSettlements().length; i++) {
				if(getSettlements()[i].getNumberOfDead() >= (getSettlements()[i].getSick().size() + getSettlements()[i].getNonSick().size()) * 0.01) {
				//reading from the object and writing into the file
				update = "CURENNT TIME: " + formatter.format(date) + "\nSETTLEMENT NAME: " + getSettlements()[i].getName() + "\n" 
						+ "NUMBER OF SICK: " + getSettlements()[i].getSick().size() + "\n"
						+ "NUMBER OF DEAD PEOPLE: " + getSettlements()[i].getNumberOfDead() + "\n";		
				fw.write(update);
			}
		}
		fw.close();
	}

	/**
	 * The main function that is activated as soon as a file is loaded and is responsible for activating
	 *  a trade for each stallion belonging to the map
	 */
	public void runAll() {
		Thread[] thread = new Thread[getSettlements().length];	
		for(int i = 0; i < getSettlements().length; i++) {
			new Thread(getSettlements()[i]).start();
		}
	}
	
	
	public Iterator<Settlement> iterator() {
		return Arrays.stream(this.settlements).iterator();
	}
	
}//Map class


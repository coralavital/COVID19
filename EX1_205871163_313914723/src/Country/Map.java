package Country;
//Import staff
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;

/***
 * Representation of a Map class
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public class Map {

	private Settlement[] settlements;


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

}//Map class


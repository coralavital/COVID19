package Population;
//Import staff
import Country.Settlement;
import Location.Point;
import Simulation.Clock;

/***
 * Representation of a Healthy class 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 */

public class Healthy extends Person{

	//Constructor
	/**
	 * Constructor
	 * @param age
	 * @param location
	 * @param settlement
	 */
	public Healthy(int age, Point location, Settlement settlement) {
		super(age, location, settlement);
	}

	//Copy constructor
	/**
	  * Copy constructor
	  * @param h, object
	  */
	public Healthy(Healthy h) {
		super(h.getAge(), h.getLocation(), h.getSettlement());
	}

	//ToString
	/**
	 * This method is used to print the object.
	 * @return String this is the print of the object.
	 */
	public String toString() {
		return "Healthy person --> \t\t|age: " + this.getAge() +"|"
				+ "\n\t\t\t\tPoint on the map" + getLocation().toString()
				+ "\n\t\t\t\tBelong to the settlement --> " + getSettlement().toString();
	}

	//Equals
	/**
	  * Equals method 
	  * @return: boolean true if it is the same object with the same values, else false 
	  */
	public boolean equals(Object o) {
		if(!(o instanceof Healthy))
			return false;
		Healthy other = (Healthy)o;
		if(this.getAge() == other.getAge()
				&& this.getLocation().equals(other.getLocation())
				&& this.getSettlement().equals(other.getSettlement()))
			return true;
		else
			return false;
	}

	//Method's
	/**
	  * Calculate the  contagion probability
	  * @return: double
	  */
	public double contagionProbability() {
		return 1; 
	}

	/**
	 * Method that making the healthy person vaccinated
	 * @return Object, vaccinated person
	 */
	public Person vaccinate() {
		int age = this.getAge();
		Point point = this.getLocation();
		Settlement settlement = this.getSettlement();
		return new Vaccinated(age, point, settlement, Clock.now());
	}

}//Healthy class



package Population;
//Import staff
import Country.Settlement;
import Location.Point;
import Simulation.Clock;
import Virus.IVirus;

/***
 * Representation of a Convalescent class 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public class Convalescent extends Person{

	//Data members
	private IVirus virus;

	//Constructor
	/**
	 * Constructor
	 * @param age
	 * @param location
	 * @param settlement
	 * @param virus
	 */
	public Convalescent(int age, Point location, Settlement settlement,IVirus virus) {
		super(age, location, settlement);
		this.virus = virus;
	}

	//copy constructor
	/**
	  * copy constructor
	  * @param c, object
	  */
	public Convalescent(Convalescent c) {
		super(c.getAge(), c.getLocation(), c.getSettlement());
		this.virus = c.getVirus();
	}

	//ToString
	/**
	 * This method is used to print the object.
	 * @return String this is the print of the object.
	 */
	public String toString() {
		return "Convalescent person --> \t\t|age: " + this.getAge() +"|"
				+ "\n\t\t\t\tPoint on the map" + getLocation().toString()
				+ "\n\t\t\t\tBelong to the settlement --> " + getSettlement().toString();
	}

	//Equals
	/**
	  * Equals method 
	  * @return: boolean true if it is the same object with the same values, else false 
	  */
	public boolean equals(Object o) {
		if(!(o instanceof Convalescent))
			return false;
		Convalescent other = (Convalescent)o;
		if(this.getAge() == other.getAge()
				&& this.getLocation() == other.getLocation()
				&& this.getSettlement() == other.getSettlement()
				&& this.getVirus() == other.getVirus())
			return true;
		else
			return false;
	}

	//Getter
	/**
	  * getter method
	  * @return: IVirus
	  */
	public IVirus getVirus() {
		return virus;
	}

	/**
	  * getter method for the current time of getting contagious 
	  * @return: long
	  */
	public long getContagiousTime() {
		return Clock.now();//
	}	


	//Method's
	/**
	  * Calculate the  contagion probability
	  * @return: double
	  */
	public double contagionProbability() {
		return 0.2;//
	}


}//Convalescent class

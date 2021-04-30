package Population;
//Import staff
import java.util.Random;

import Country.Settlement;
import Location.Point;
import Simulation.Clock;
import Virus.BritishVariant;
import Virus.IVirus;

/***
 * Representation of a Sick class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */

public class Sick extends Person{

	//Data members
	private long contagiousTime;
	private IVirus virus;


	//Constructor
	/**
	 * Constructor
	 * @param age
	 * @param location
	 * @param settlement
	 * @param virus
	 */
	public Sick(int age, Point location, Settlement settlement, long contagiousTime, IVirus virus) {
		super(age, location, settlement);
		this.contagiousTime = contagiousTime;
		this.virus = virus;
	}

	//Copy constructor
	/**
	 * copy constructor
	 * @param s, object
	 */
	public Sick(Sick s) {
		super(s.getAge(), s.getLocation(), s.getSettlement());
		this.contagiousTime = s.getContagiousTime();
		this.virus = s.getVirus();
	}

	//ToString
	/**
	 * This method is used to print the object.
	 * @return String this is the print of the object.
	 */
	public String toString() {
		return "Sick person --> \t\t|age: " + this.getAge() +"|"
				+ "\n\t\t\t\tPoint on the map" + getLocation().toString()
				+ "\n\t\t\t\tBelong to the settlement --> " + getSettlement().toString();
	}

	//Equals
	/**
	 * Equals method 
	 * @return: boolean true if it is the same object with the same values, else false 
	 */
	public boolean equals(Object o) {
		if(!(o instanceof Sick))
			return false;
		Sick other = (Sick)o;
		if(this.getAge() == other.getAge()
				&& this.getLocation() == other.getLocation()
				&& this.getSettlement() == other.getSettlement())
			return true;
		else
			return false;
	}

	//Getters
	/**
	 * Getter method
	 * @return: contagiousTime, Long	
	 */
	public long getContagiousTime() {
		return contagiousTime;
	}

	/**
	 * Getter method
	 * @return: virus, IVirus
	 */
	public IVirus getVirus() {
		return virus;
	}


	//Method's
	/**
	 * Method that try to kill the sick person with his virus attribute
	 * @return boolean, true if the person died, else false
	 */
	private boolean tryToDie() {
		return this.virus.tryToKill(this);
	}

	/**
	 * Method that making Sick Person Vaccinated
	 * @param virus
	 * @return Object, Vaccinated person
	 */
	public Person recover(IVirus virus) {
		int age = this.getAge();
		Point point = this.getLocation();
		Settlement settlement = this.getSettlement();
		return new Vaccinated(age, point, settlement, Clock.now());
	}

	/**
	 * returns the probability of getting contagion
	 * @return: double
	 */
	public double contagionProbability() {
		return 1;
	}


}//Sick class

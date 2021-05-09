package Population;
//Import staff
import Country.Settlement;
import Location.Point;
import Simulation.Clock;
import Virus.IVirus;

/***
 * Representation of a Vaccinated class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */

public class Vaccinated extends Person{

	//Data members
	private long vaccinationTime;
	private double p;

	//Constructor
	/**
	 * Constructor
	 * @param age
	 * @param location
	 * @param settlement
	 * @param vaccinationTime
	 */
	public Vaccinated(int age, Point location, Settlement settlement, long vaccinationTime) {
		super(age, location, settlement);
		this.vaccinationTime = vaccinationTime;
	}
	
	//Copy constructor
	/**
	  * Copy constructor
	  * @param v, object
	  */
	public Vaccinated(Vaccinated v) {
		super(v.getAge(), v.getLocation(), v.getSettlement());
		this.vaccinationTime = v.getVaccinationTime();
	}

	//ToString
	/**
	 * This method is used to print the object.
	 * @return String this is the print of the object.
	 */
	public String toString() {
		return "Vaccinated person --> \t\t|age: " + this.getAge() +"|"
				+ "\n\t\t\t\tPoint on the map" + getLocation().toString()
				+ "\n\t\t\t\tBelong to the settlement --> " + getSettlement().toString();
	}

	//Equals
	/**
	 * Equals method 
	 * @return: boolean true if it is the same object with the same values, else false 
	 */
	public boolean equals(Object o) {
		if(!(o instanceof Vaccinated))
			return false;
		Vaccinated other = (Vaccinated)o;
		if(this.getAge() == other.getAge()
				&& this.getLocation().equals(other.getLocation())
				&& this.getSettlement().equals(other.getSettlement())
				&& this.vaccinationTime == other.getVaccinationTime())
			return true;
		else
			return false;
	}


	//Getter
	/**
	  * Getter method
	  * @return: vaccinationTime, Long
	  */
	public long getVaccinationTime() {
		return vaccinationTime;
	}

	//Method
	/**
	  * Calculate the  contagion probability
	  * @return: double
	  */
	public double contagionProbability() {
		double time = Clock.days(this.getVaccinationTime());
		if(time < 21)
			p = Math.min(1, 0.56 + 0.15 * Math.sqrt(21 - vaccinationTime));
		else
			p = Math.max(0.05, (1.05/ (vaccinationTime - 14))); 
		return p;
	}


}//Vaccinated class

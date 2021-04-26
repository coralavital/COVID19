package Population;
//Import staff
import Location.Point;
import Simulation.Clock;
import Virus.IVirus;
import Country.City;
import Country.Kibbutz;
import Country.Moshav;
import Country.Settlement;

/***
 * Representation of a Person class 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public abstract class Person{

	//Data members
	private int age;
	private Point location;
	private Settlement settlement;

	//Constructor
	/**
	 * Constructor
	 * @param age
	 * @param location
	 * @param settlement
	 */
	public Person(int age, Point location, Settlement settlement) {
		this.age = age;
		this.location = location;
		this.settlement = settlement;
	}

	//Copy constructor
	/**
	  * copy constructor
	  * @param p, object
	  */
	public Person(Person p) {
		this.age = p.getAge();
		this.location = new Point(p.getLocation());
		if (settlement instanceof Moshav) {// clone
			// this.settlement= settlement.clone();
			this.settlement = new Moshav((Moshav) p.getSettlement());

		}
		if (settlement instanceof City) {
			this.settlement = new City((City) p.getSettlement());

		}
		if (settlement instanceof Kibbutz) {
			this.settlement = new Kibbutz((Kibbutz) p.getSettlement());
		}

	}


	//Equals
	/**
	  * Equals method 
	  * @return: boolean true if it is the same object with the same values, else false 
	  */
	public boolean equals(Object other) {
		boolean ans = false;
		//true if other is instance of person
		if(other instanceof Person) 
			ans = (age == ((Person)other).age 
			&& location == ((Person)other).location 
			&& settlement == ((Person)other).settlement);
		return ans;
	}

	//Getters
	/**
	 * Get functions that give age value.
	 * @return integer age.
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Get functions that give location value.
	 * @return Point location.
	 */
	public Point getLocation() {
		return location;
	}

	/**
	  * Getter function
	  * @return: settlement, Settlement
	  */
	public Settlement getSettlement() {
		return settlement;
	}

	/**
	  * Setter function
	  * @param: settlement, Settlement
	  */
	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}
	
	//Method's
	/**
	 * This method abstract functions.
	 *@author coral.
	 */
	public abstract double contagionProbability();

	/**
	 * This method is used to find average of three integers.
	 * @param IVirus This is the parameter to contagion method
	 * @return Person.
	 *@author coral.
	 */
	public Person contagion(IVirus v) {
		if (!(this instanceof Sick)) {

			long t = Clock.now();
			Sick c = new Sick(this.getAge(), this.getLocation(), this.getSettlement(), t, v);
			return c;
		}

		else {
			System.out.println("Error, the person is already sick");
		}
		return this;


	}

}//Person class




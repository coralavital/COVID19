package Country;
//Import staff
import java.util.List;

import Location.Location;
import Population.Person;

/***
 * Representation of a City class
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public class City extends Settlement{


	//Constructor
	/***
	 * Constructor
	 * @param name, String
	 * @param location. Object
	 * @param people, List of Person
	 * @param ramzorColor, Enum
	 */
	public City(String name, Location location, List<Person> people, RamzorColor ramzorColor) {
		super(name, location, people, ramzorColor);
	}
	//Copy constructor
	/***
	  * Copy constructor 
	  * @param c, Object
	  */
	public City(City c) {
		super(c.getName(), c.getLocation(), c.getPeople(), c.getRamzorColor());
	}


	//Equals
	/***
	 * Equals method
	 * @param obj: Object 
	 */
	public boolean equals(Object obj) {
		if(!(obj instanceof Settlement))
			return false;
		Settlement s = (Settlement) obj;
		if(this.getName() == s.getName() 
				&& (this.getLocation().equals(s.getLocation()) 
						&& this.getPeople().equals(s.getPeople()) 
						&& this.getRamzorColor().equals(s.getRamzorColor())))
			return true;
		else
			return false;
	}

	//Method
	/***
	  * Method that calculate the color of the city which called ramzor color
	  * @return: the color of the ramzor
	  */
	public RamzorColor calcuateRamzorGrade() {
		double p = contaiousPercent();
		double c = this.getRamzorColor().getCoefficient();// TODO problem
		c = 0.2 * Math.pow(4, 1.25 * p);
		this.setRamzorColor(RamzorColor.choice(c));
		return RamzorColor.choice(c);

	}

}//City class

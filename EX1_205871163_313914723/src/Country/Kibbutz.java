package Country;
//Import staff
import java.util.List;

import Location.Location;
import Population.Healthy;
import Population.Person;
import Population.Sick;

/***
 * Representation of a Kibbutz class
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public class Kibbutz extends Settlement{


	//Constructor
	/***
	 * Constructor
	 * @param name, String
	 * @param location. Object
	 * @param people, List of Person
	 * @param ramzorColor, Enum
	 */
	public Kibbutz(String name, Location location, List<Person> people, List<Sick> sick,
			List<Healthy> healthy,  RamzorColor ramzorColor, int totalVaccines, List<Settlement> linkTo) {
		super(name, location, people, sick, healthy, ramzorColor, totalVaccines, linkTo);
	}
	
	//Copy constructor
	/***
	  * Copy constructor 
	  * @param k, Object
	  */
	public Kibbutz(Kibbutz k) {
		super(k.getName(), k.getLocation(), k.getPeople(), k.getSick(), k.getHealthy(), k.getRamzorColor(), k.getTotalPersons(), k.getLinkTo());
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
	  * Method that calculate the color of the Kibbutz which called ramzor color
	  * @return the color of the ramzor
	  */
	public RamzorColor calcuateRamzorGrade() {
		double p = contaiousPercent();
		double c = this.getRamzorColor().getCoefficient();
		c = 0.45 + Math.pow(Math.pow(1.5, c) * (p - 0.4), 3);
		this.setRamzorColor(RamzorColor.choice(c));
		return RamzorColor.choice(c);

	}



}//Kibbutz class

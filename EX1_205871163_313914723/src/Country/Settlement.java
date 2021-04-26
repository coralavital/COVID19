package Country;
//Import staff
import Location.Location;
import Location.Point;
import Population.Convalescent;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Population.Vaccinated;
import java.util.*;
import java.util.List;
import java.util.Random;

/***
 * Representation of a Settlement class 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public abstract class Settlement {

	//data members 
	private String name;
	private Location location;
	private List<Person> people;
	private RamzorColor ramzorColor;

	//Constructor 
	/**
	 * constructor functions.
	 * @param String name
	 * @param Location location.
	 * @param List people.
	 * @param RamzorColor ramzorColor.
	 */
	public Settlement(String name, Location location, List<Person> people, RamzorColor ramzorColor) {
		this.name = name;
		this.location = location;
		this.people=people;//need to make sure of deep copy
		this.ramzorColor = ramzorColor;
	}

	//ToString
	/**
	 * toString functions.
	 * @return String.
	 */
	public String toString() {
		return "\n\t\tForm of locality --> " + this.getName() 
		+ "\n\t\t" + this.getLocation().toString() 
		+ "\n\t\tNumber of people --> " + this.getPeople().size()
		+ "\n\t\tRamzorColor is -->  "+ this.getRamzorColor();
	}

	/**
	 * Abstract functions.
	 * @return String.
	 */
	public abstract boolean equals(Object obj);

	//Getters
	/***
	  * Getter function to name
	  * @return String
	  */
	public String getName() {
		return name;
	}

	/***
	  * Getter function to location
	  * @return Object location
	  */
	public Location getLocation() {
		return location;
	}

	/***
	  * Getter function to List of people
	  * @return List
	  */
	public List<Person> getPeople() {
		return people;
	}

	/**
	 * get functions that give ramzorColor object.
	 * @return RamzorColor object.
	 *@author coral.
	 */
	public RamzorColor getRamzorColor() {
		return ramzorColor;
	}

	/**
	  * Setter function for ramzorColor
	  * @param ramzorColor
	  */
	public void setRamzorColor(RamzorColor ramzorColor) {
		this.ramzorColor = ramzorColor;
	}

	/**
	  * Setter function for ramzorColor
	  * @param ramzorColor
	  */
	public abstract RamzorColor calcuateRamzorGrade();

	/**
	 * Method  that calculate the Percentage of getting contagious
	 * @return double
	 */
	public double contaiousPercent() {
		int counter = 0;
		for(int i = 0; i < people.size(); i++) {
			if(people.get(i) instanceof Sick) {
				counter++;
			}
		}
		if(counter == 0)
			return 0;
		else
			return (double) counter / people.size();
	}

	/**
	 * Method that add Person into list of people 
	 * @param p, Person object that can be Healthy or Sick or Convalescent or Vaccinated
	 * @return boolean, true if the Person object was from the object that are mention above, else false
	 */
	public boolean addPerson(Person p) {
		if (p instanceof Sick) {
			Sick s = (Sick) p;
			this.people.add(new Sick(s));
			return true;
		} else if (p instanceof Healthy) {
			Healthy h = (Healthy) (p);
			this.people.add(new Healthy(h));
			return true;
		} else if (p instanceof Convalescent) {
			Convalescent c = (Convalescent)(p);
			this.people.add(new Convalescent(c));
			return true;
		} else if (p instanceof Vaccinated) {
			Vaccinated v = (Vaccinated) (p);
			this.people.add(new Vaccinated(v));
			return true;
		} else
			return false;
	}



	/**
	 * take the person that we got and move him to the settlement we got 
	 * @param p, Object Person
	 * @param s, Object Settlement
	 * @return boolean, true if the transfer was complete, else false
	 */
	public boolean transferPerson(Person p, Settlement s) {
		if (!(this.equals(s))) {
			p.setSettlement(s);
			this.people.remove(p);
			s.people.add(p);
			return true;

		} 
		else
			return false;
	}

	/**
	 * Calculate random location  when we need  person to have  a point in his settlement
	 * @return Object, Point
	 */
	public Point randomLocation() {
		Random rand = new Random();
		int x = this.location.getPosition().getX();
		int y = this.location.getPosition().getY();
		int h = this.location.getSize().getHeight();
		int w = this.location.getSize().getWidth();
		Point p = new Point(rand.nextInt(w) + x, rand.nextInt(h) + y);
		return p;
	}

}//Settlement class

package Country;
//Import staff
import Location.Location;
import Location.Point;
import Population.Convalescent;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Population.Vaccinated;
import Simulation.Main;

import java.util.*;
import java.util.List;
import java.util.Random;

/*
 * Representation of a Settlement class 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public abstract class Settlement implements Runnable {

	//data members 
	private String name;
	private Location location;
	private List<Sick> sick;
	private List<Person> NonSick;
	private RamzorColor ramzorColor;
	private int totalPersons;
	private int totalVaccines;
	private List<Settlement> linkTo;
	private int numberOfDead;
	private Map map;

	//Constructor 
	/**
	 * constructor functions.
	 * @param String name
	 * @param Location location.
	 * @param List people.
	 * @param RamzorColor ramzorColor.
	 */
	// delete person list
	public Settlement(String name, Location location, List<Sick> sick, List<Person> NonSick, RamzorColor ramzorColor, int totalVaccines, List<Settlement> linkTo, Map map) {
		this.name = name;
		this.location = location;
		this.sick = sick;
		this.NonSick = NonSick;
		this.ramzorColor = ramzorColor;
		this.totalVaccines = totalVaccines;
		this.linkTo = linkTo;

		if(NonSick != null && sick != null) {
			this.totalPersons = (int)((NonSick.size() + sick.size())*1.3);
		}

		else
			this.totalPersons = 0;
		this.numberOfDead = 0;
		this.map = map;

	}

	public Settlement(Settlement s) {
		this.name = s.name;
		this.location = s.location;
		this.sick = s.sick;
		this.NonSick = s.NonSick;
		this.ramzorColor = s.ramzorColor;
		this.totalVaccines = s.totalVaccines;
		this.linkTo = s.linkTo;
		this.totalPersons = s.totalPersons;
		this.numberOfDead = s.numberOfDead;
		this.map = s.map;
	}

	//ToString
	/**
	 * toString functions.
	 * @return String.
	 */
	public String toString() {
		return "\n\t\tForm of locality --> " + this.getName() 
		+ "\n\t\t" + this.getLocation().toString() 
		+ "\n\t\tNumber of people --> " + this.getNonSick().size() + this.getSick().size()
		+ "\n\t\tRamzorColor is -->  "+ this.getRamzorColor()
		+ "\n\t\tLinked settlement -->" + this.getLinkTo();
	}

	/**
	 * Abstract functions.
	 * @return String.
	 */
	public abstract boolean equals(Object obj);

	//Getters
	/*
	 * Getter function to name
	 * @return String
	 */
	public String getName() {
		return this.name;
	}

	/*
	 * Getter function to location
	 * @return Object location
	 */
	public Location getLocation() {
		return location;
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
		if(getSick().size() == 0)
			return 0;
		else
			return (double) getSick().size() / (NonSick.size() + sick.size());
	}


	/**
	 *  getter function for sick
	 * @return: list , sick
	 */
	public List<Sick> getSick() {
		return sick;
	}

	/**
	 * getter function for non sick 
	 * @return: list , NonSick
	 */
	public List<Person> getNonSick() {
		return NonSick;
	}

	/**
	 * getter function for total persons
	 * @return: list , sick
	 */
	public int getTotalPersons() {
		return totalPersons;
	}

	/**
	 * setter function for the number of vaccines
	 * @param: int, number
	 */
	public void setTotalVaccines(int number) {
		this.totalVaccines += number;
	}

	/**
	 * decreasing the number of vaccines if there is more than zero else it will stay zero 
	 */
	public void decVaccineByOne() {
		if(this.totalVaccines > 0)
			this.totalVaccines -= 1;
		else
			this.totalVaccines = 0;
	}

	/**
	 * getter function for number of vaccines
	 * @return: int, totalVaccines
	 */
	public int getTotalVaccines() {
		return totalVaccines;
	}

	/**
	 * getter function for linked settlements to the current settlement
	 * @return: list, linkTo
	 */
	public List<Settlement> getLinkTo() {
		return this.linkTo;
	}


	/**
	 * returning a string that match inside to the cell of the Excel file
	 * @return: String, finalString
	 */
	public String printLinked() {
		StringBuilder finalString = new StringBuilder();
		for(int i = 0; i < linkTo.size(); i++) {
			finalString.append(linkTo.get(i).name + " ");
		}
		return "" + finalString;

	}

	/**
	 * return the name of the type of the settlement 
	 * @return:String, CITY/MOSHAV/KIBBUTZ
	 */
	public String getType() {
		if(this instanceof City)
			return "CITY";
		else if(this instanceof Moshav)
			return "MOSHAV";
		else
			return "KIBBUTZ";
	}

	public int getNumberOfDead() {
		return this.numberOfDead;
	}

	public void incNumberOfDead() {
		this.numberOfDead += 1; 
	}
	

	/**
	 * Method that add Person into list of people 
	 * @param p, Person object that can be Healthy or Sick or Convalescent or Vaccinated
	 * @return boolean, true if the Person object was from the object that are mention above, else false
	 */
	public boolean addPerson(Person p) {
		if(p instanceof Sick) {
			//thie person is sick so we add him to the sick people
			Sick s = (Sick) p;
			this.sick.add(s);
			return true;

		} 
		else if(!(p instanceof Sick)) {

			if(p instanceof Healthy) {
				Healthy h = (Healthy) p;
				this.NonSick.add(h);
			}
			else if(p instanceof Convalescent) {
				Convalescent c = (Convalescent) p;
				this.NonSick.add(c);
			}
			else {
				Vaccinated v = (Vaccinated) p; 
				this.NonSick.add(v);
			}
			return true;
		}

		else
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
			if ((s.getTotalPersons() > (s.getNonSick().size() + getSick().size())) || 
					(this.getRamzorColor().getProbability() * s.getRamzorColor().getProbability() <= Math.random())) {
				if(p instanceof Sick) {
					this.getSick().remove(p);
					s.addPerson(p);
					return true;


				}
				else if(!(p instanceof Sick)) {
					this.getNonSick().remove(p);
					s.addPerson(p);
					return true;
				}

				else 
					return false;
			}
			else 
				System.out.println("this person is allready in the settlement");
			return false;
		}
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

	public void run() {

		while(true) {

		}

	}//Settlement class
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public Map getMap() {
		return this.map;
	}
}
package Country;
//Import staff
import Location.Location;
import Location.Point;
import Population.Convalescent;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Population.Vaccinated;
import Simulation.Clock;
import Simulation.Main;
import UI.MainWindow;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

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
		return this.totalPersons;
	}

	public void setTotalPersons() {
		this.totalPersons = (int)((getNonSick().size() + getSick().size()) * 1.3);
	}
	/**
	 * setter function for the number of vaccines
	 * @param: int, number
	 */
	public synchronized void setTotalVaccines(int number) {
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
			this.getSick().add(s);
			return true;

		} 
		else if(!(p instanceof Sick)) {

			if(p instanceof Healthy) {
				Healthy h = (Healthy) p;
				this.getNonSick().add(h);
			}
			else if(p instanceof Convalescent) {
				Convalescent c = (Convalescent) p;
				this.getNonSick().add(c);
			}
			else {
				Vaccinated v = (Vaccinated) p; 
				this.getNonSick().add(v);
			}
			return true;
		}

		else
			return false;

	}


	public synchronized boolean addSick() {
		Random rand = new Random();
		IVirus virus;

		/**
		 * running on all the settlements if we found what the user selected then add a random virus into their people
		 * if he didnt selected then we wont be able to add sick people
		 */
		if(getNonSick() != null) {
			for(int j = 0; j < getNonSick().size() * 0.01; j++) {
				int index = rand.nextInt(getNonSick().size());
				Person person = getNonSick().get(index);
				int value = rand.nextInt(3);
				if(value == 1)
					virus = new BritishVariant();
				else if(value == 2)
					virus = new ChineseVariant();
				else
					virus = new SouthAfricanVariant();
				
				Sick sick = new Sick(person.getAge(), person.getLocation(), person.getSettlement(), Clock.now(), virus);
				getNonSick().remove(person);
				addPerson(sick);
				return true;
				}
			}
		return false;
	}



	/**
	 * take the person that we got and move him to the settlement we got 
	 * @param p, Object Person
	 * @param s, Object Settlement
	 * @return boolean, true if the transfer was complete, else false
	 */
	public boolean transferPerson(Person p, Settlement s) {

		int s1 = System.identityHashCode(this);
		int s2 = System.identityHashCode(s);

		if(s1 <= s2) {
			//Prevent dead lock with the smaller int id of the settlement - now its this before 
			synchronized(this) {
				synchronized(s) {
					if (!(this.equals(s))) {
						if ((s.getTotalPersons() > (s.getNonSick().size() + s.getSick().size())) || 
								(p.getSettlement().getRamzorColor().getProbability() * s.getRamzorColor().getProbability() <= Math.random())) {
							if(p instanceof Sick) {
								s.addPerson(p);
								this.getSick().remove(p);
								System.out.println("This person move to the new settlement");
								return true;
							}
							else if(!(p instanceof Sick)) {
								s.addPerson(p);
								this.getNonSick().remove(p);
								System.out.println("This person move to the new settlement");
								return true;
							}

							else {
								System.out.println("This person caonnot move to the new settlement");
								return false;
							}
						}
					}
					else {
						System.out.println("This person is allready in the settlement");
						return false;
					}
				}
			}
		}
		else {
			//Prevent dead lock with the smaller int id of the settlement - now its s before
			synchronized(s) {
				synchronized(this) {

					if (!(this.equals(s))) {
						if ((s.getTotalPersons() > (s.getNonSick().size() + s.getSick().size())) || 
								(p.getSettlement().getRamzorColor().getProbability() * s.getRamzorColor().getProbability() <= Math.random())) {
							if(p instanceof Sick) {
								s.addPerson(p);
								p.getSettlement().getSick().remove(p);
								System.out.println("This person move to the new settlement");
								return true;
							}
							else if(!(p instanceof Sick)) {
								s.addPerson(p);
								p.getSettlement().getNonSick().remove(p);
								System.out.println("This person move to the new settlement");
								return true;
							}

							else {
								System.out.println("This person caonnot move to the new settlement");
								return false;
							}
						}
					}
					else 
						System.out.println("This person is allready in the settlement");
					return false;
				}
			}
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

	public void setMap(Map map) {
		this.map = map;
	}

	public Map getMap() {
		return this.map;
	}

	// Initialization
	/**
	 * Running on all the settlements and the 20% of people in it and make them sick
	 * @param map, Map object
	 * @param settlement, Settlement object
	 */
	private void initialization() {
		Random rand = new Random();
		IVirus virus = null;

		for(int j = 0; j < (getNonSick().size() * 0.2); j++) {
			System.out.println("initialization no. " + j );
			int index = rand.nextInt(getNonSick().size());
			Person person = getNonSick().get(index);
			int value = rand.nextInt(3);
			if(value < 0)
				value = Math.abs(value);
			if(value == 0)
				virus = new BritishVariant();
			else if(value == 1)
				virus = new ChineseVariant();
			else if(value == 2)
				virus = new SouthAfricanVariant();
			Sick sick = new Sick(person.getAge(), person.getLocation(), person.getSettlement(),
					Clock.now(), virus);
			getNonSick().remove(j);
			addPerson(sick);

			for(int k = 0; k < 3; k++) {
				System.out.println("Now the system will try to infect 3 non-sick people for this sick person.");
				if(getNonSick().size() > 3 ) {//changed from [j] to [i]
					//Here is called a method whose function is to infect a random person with the help of 
					//the created sick person.
					tryToInfect(sick);
				}
			}

		}
	}

	/**
	 * Method that try to infect 3 non-sick people by one sick person
	 * @param sick, Sick object
	 * @param settlement, Settlement object
	 */
	private void tryToInfect(Sick sick) {
		Random rand = new Random();
		IVirus virus = sick.getVirus();
		Sick s;
		IVirus v = null;
		int value = 0;
		int index = 0;
		int newP = 0;

		newP = rand.nextInt(getNonSick().size());
		Person p = getNonSick().get(newP);
		if(virus instanceof BritishVariant) {
			for(int n = 0; n < 3; n++) {
				if(MainWindow.getData()[0][n])
					value++;
			}
			index = rand.nextInt(value);
			if(index == 0) 
				v = new BritishVariant();
			else if(index == 1)
				v = new ChineseVariant();
			else if(index == 2)
				v = new SouthAfricanVariant();
			s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);
			if(v.tryToContagion(s, p)) {
				getNonSick().remove(newP);
				addPerson(p.contagion(v));
				System.out.println("The infection succeeded");
			}
			else
				System.out.println("The infection failed");
		}

		if(virus instanceof ChineseVariant) {
			for(int n = 0; n < 3; n++) {
				if(MainWindow.getData()[1][n])
					value++;
			}
			index = rand.nextInt(value);
			if(index == 0) 
				v = new BritishVariant();
			if(index == 1)
				v = new ChineseVariant();
			if(index == 2)
				v = new SouthAfricanVariant();
			s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);
			if(v.tryToContagion(s, p)) {
				getNonSick().remove(newP);
				addPerson(p.contagion(v));
				System.out.println("The infection succeeded");
			}
			else
				System.out.println("The infection failed");
		}
		if(virus instanceof SouthAfricanVariant) {

			for(int n = 0; n < 3; n++) {
				if(MainWindow.getData()[2][n])
					value++;
			}
			index = rand.nextInt(value);
			if(index == 0) 
				v = new BritishVariant();
			else if(index == 1)
				v = new ChineseVariant();
			else if(index == 2)
				v = new SouthAfricanVariant();
			s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);
			if(v.tryToContagion(s, p)) {
				getNonSick().remove(newP);
				addPerson(p.contagion(v));
				System.out.println("The infection succeeded");
			}
			else
				System.out.println("The infection failed");
		}
	}

	/**
	 * Method that try to recover sick people to be convalescent people if they getContagiousTime > 25 days.
	 */
	private void recoverToHealthy() {

		//A loop that passes over the person how found in the Sick-list
		for (int k = 0; k < getSick().size(); k++) {
			System.out.println("recoverToHealthy no. " + k);
			//A loop that passes over the number of people who have passed 25 days from the moment they were infected with the virus 
			if(Clock.days(getSick().get(k).getContagiousTime()) >= 25) {
				System.out.println("try to recover the person in the index " + k + " in the settlement " + getName());
				Sick s = getSick().get(k);
				this.getSick().remove(s);
				s.recover();
				this.addPerson(s);
				System.out.println("this person was a sick and now he healthy");
			}
			else
				System.out.println("No people were found to be ill for more than 25 days");
		}
	}	

	/**
	 * Method that try to transfer sick from one settlement to another
	 */
	private void moveSettlement() {
		int value;
		int index;
		if(getNonSick() != null) {
			if(getLinkTo().size() > 0) {
				for(int j = 0; j < (this.getNonSick().size() * 0.03); j++) {
					System.out.println("moveSettlement no. " + j);
					Random rand = new Random();
					index = rand.nextInt(getLinkTo().size());
					Settlement s = getLinkTo().get(index);
					System.out.println(getName());
					System.out.println(s.getName());

					value = selectRandom(0);

					if(transferPerson(getNonSick().get(value), s))
						System.out.println("The transfer was successful");
				}
			}
			else
				System.out.println("There are not link settlement for this settlement");
		}
		if(getSick() != null) {
			if(getLinkTo().size() > 0) {
				for(int j = 0; j < (this.getSick().size() * 0.03); j++) {
					System.out.println("moveSettlement no. " + j);
					Random rand = new Random();
					index = rand.nextInt(getLinkTo().size());
					Settlement s = getLinkTo().get(index);
					System.out.println(getName());
					System.out.println(s.getName());

					value = selectRandom(1);

					if(transferPerson(getSick().get(value), s))
						System.out.println("The transfer was successful");
				}
			}
			else
				System.out.println("There are not link settlement for this settlement");
		}
	}

	//add this method to sure that we cant try to move a person that not in the settlement
	private synchronized int selectRandom(int numberOfList) {
		Random rand = new Random();
		int value = 0;
		if(numberOfList == 0)
			value = rand.nextInt(getNonSick().size());
		else if(numberOfList == 1)
			value = rand.nextInt(getSick().size());
		return value;

	}

	/**
	 * A method that tries to vaccinate healthy people if there are vaccine doses waiting.
	 */
	private void vaccinateHealthy() {
		if(this.getTotalVaccines() > 0) {
			for(int k = 0; k < getNonSick().size(); k++) {
				Vaccinated v = new Vaccinated(getNonSick().get(k).getAge(),
						getNonSick().get(k).getLocation(),
						getNonSick().get(k).getSettlement(), Clock.now());
				getNonSick().remove(k);
				addPerson(v);
				decVaccineByOne();
				System.out.println("The person in index " + k + "resilient and now he is a resilient person");
			}
		}
		else
			System.out.println("There were no vaccines left in the pool");

		System.out.println("Finish vaccinateHealthy for this settlement");
	}

	private void killPeople() {
		if(this.getSick() != null) {
			for(int j = 0; j < this.getSick().size(); j++) {
				System.out.println("killPeople no. " + j);
				if(getSick().get(j).tryToDie()) {
					System.out.println("the person form sick list in the index " + j + " is dead!");
					getSick().remove(j);
					incNumberOfDead();
				}
				else
					System.out.println("the person form sick list in the index " + j + " is not dead!");
			}
		}
		else
			System.out.println("There are no sick people in this settlement");
	}


	public void run() {
		while(this.getMap().isON()) {
			//The role of the method is to sample 20% of patients out of all the people in localities that have already been initialized 
			//on the map and for each person who has become ill an attempt will be made to infect three different people
			//And for this purpose uses another method whose function is to try to infect a random person who is not ill

			synchronized(getMap()) {

				while(!(getMap().isPLAY())) {
					try {
						getMap().wait();
					} 
					catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			initialization();
			recoverToHealthy();
			moveSettlement();
			vaccinateHealthy();
			killPeople();

			if(getMap().getFileName() != null) {
				try {
					getMap().saveLogFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			try {
				getMap().getCyclic().await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
			catch (BrokenBarrierException e) {
				e.printStackTrace();
				return;
			}
		}
		return;
	}

}//Settlement class
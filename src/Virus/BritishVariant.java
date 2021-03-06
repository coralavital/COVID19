package Virus;
//Import staff
import Population.Person;
import Population.Sick;
import Simulation.Clock;

/***
 * Representation of a BritishVariant class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */
public class BritishVariant implements IVirus{

	//ToString
	/**
	 * ToString functions.
	 * @return String.
	 *@author coral.
	 */
	public String toString() {
		return "BritishVariant";
	}
	

	//Static mathods
	/**
	 * Calculates the probability if the person dies
	 * @param p, object
	 * @return: double
	 */
	public static double Probability_Death(Person p) {
		if (p.getAge() < 18)
			return 0.01;

		else
			return 0.1;
	}



	//Mathods
	/**
	 * Calculate the  contagion probability
	 * @param p, object
	 * @return: double
	 */
	public double probability_infection(Person p) {
		return 0.7;
	}

	/**
	 * Calculates the probability that the person will be infected
	 * @param p, object
	 * @return: double
	 */
	public double contagionProbability(Person p) {
		return p.contagionProbability() * probability_infection(p);
	}

	/**
	 * Calculates the distance between two people in a settlement
	 * @param p, object
	 * @return: double
	 */
	public double distance(Person p, Person p1) {
		double x1 = p.getLocation().getX() - p1.getLocation().getX();
		double y1 = p.getLocation().getY() - p1.getLocation().getY();
		return Math.sqrt(Math.pow(x1, 2) + Math.pow(y1, 2));
	}

	/**
	 * Getting two persons object when one is sick and try to contagion the other
	 * @param: p1 Sick, p2 not Sick Person (for example healthy)
	 * @return: boolean , true if the chance of getting contagion is higher that the random number from 0 to 1 that we got from Math.random())
	 * else false
	 */
	public boolean tryToContagion(Sick p1, Person p2) {
		if ((p2 instanceof Sick))
			return false;
		else {
			double t = Clock.days((p1).getContagiousTime());
			if((contagionProbability(p2) * Math.min(1, (0.14) * Math.pow(Math.E, (2 - 0.25 * distance(p1, p2)))) <= Math.random()) && t < 5) 
				return true;

			else
				return false;
		}
	}

	/**
	 * Calculate the percentage of getting killed
	 * @return boolean, true if the chance to kill is more than 100%, else false
	 */
	public boolean tryToKill(Sick s) {
		double p = Probability_Death(s);
		long t = (long) Clock.days(s.getContagiousTime());
		//From a grid number between 0 and 1 and if the person's probability of dying is greater than the number drawn, the method will return true
		if (Math.max(0, p - 0.01 * p * (Math.pow((t - 15), 2))) > Math.random())
			return true;
		else
			return false;
	}

}//BritishVariant class

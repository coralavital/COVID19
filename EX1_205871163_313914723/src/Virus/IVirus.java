package Virus;
//Import staff
import Population.Convalescent;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Population.Vaccinated;

/***
 * Representation of a IVirus interface
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */

public interface IVirus {
	
	/**
	* @param other an object to compare with this
	* Comparable object
	* @return a negative value, 0, or a positive value,
	* if this object has a lower, an identical, or a
	* higher rank in the order, respectively.
	*/

	public double contagionProbability(Person p);
	public boolean tryToContagion(Person p1, Person p2);//the first person is sick for sure and the method try to infect the second person
	public boolean tryToKill(Sick s);
}//IVirus interface

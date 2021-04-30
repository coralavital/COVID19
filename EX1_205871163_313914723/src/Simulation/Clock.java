package Simulation;

/***
 * Representation of a Clock class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */

public class Clock {

	//Data members
	public static long now;
	public static int ticks_per_day = 1;

	//ToString
	/**
	 * This method is used to print the object.
	 * @return String this is the print of the object.
	 */
	public String toString() {
		return "The time in the simulation is --> " + this.now();
	}

	//Static mathod's
	/**
	 * This method return the time in the simulation.
	 * @return long variable.
	 */
	public static long now() {
		return now;
	}

	/**
	 * This method increase by 1.
	 */
	public static void nextTick() {
		now += 1;
	}
	
	/**
	  * calculate the days with the time we got as parament
	  * @param t, long 
	  * @return double
	  */
	public static double days(long t) {
		return Math.ceil((double)((now-t)/ticks_per_day));
	}
	
		

}//Clock class

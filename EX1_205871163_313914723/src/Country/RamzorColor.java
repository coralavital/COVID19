package Country;

/***
 * Representation of a RamzorColor enum 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public enum RamzorColor {

	Green(0.4), Yellow(0.6), Orange(0.8), Red(1.0);

	private final double coefficient;

	//Constructor
	/***
	  * Constructor
	  * @param coefficient: enum
	  */
	RamzorColor(double coefficient) {
		this.coefficient = coefficient;
	}

	//ToString
	/***
	   * ToString method of the RamzorColor Enum
	   * @return: String
	   */
	public String toString() {
		return "" + getCoefficient();
	}

	//Getter
	/***
	    * Getter method
	    * @return:  double
	    */
	public double getCoefficient() {
		return this.coefficient;
	}

	//Method
	/***
	  * The method calculate the RamzorColor according to the double she got
	  * @param c, double
	  * @return Enum
	  */
	public static RamzorColor choice(double c) {
		//Checks by the number received what color the traffic light is
		if(c <= 0.4) return Green;
		else if(c > 0.4 && c <= 0.6) return Yellow;
		else if(c > 0.6 && c <= 0.8) return Orange;
		else return Red;
	}


}//RamzorColor class

package Country;

import java.awt.Color;

/***
 * Representation of a RamzorColor enum 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public enum RamzorColor {
	
	
	Green(0.4, Color.green, 1), Yellow(0.6, Color.yellow, 0.8), Orange(0.8, Color.orange, 0.6), Red(1.0, Color.red, 0.4);

	private final double coefficient;
	private Color color;
	private double probability;
	
	
	//Constructor
	/***
	  * Constructor
	  * @param coefficient: enum
	  * @param color: Color
	  * @param probability: double
	  */
	RamzorColor(double coefficient, Color color, double probability) {
		this.coefficient = coefficient;
		this.color = color;
		this.probability = probability;
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

	
	public double getProbability() {
		return probability;
	}


}//RamzorColor class

package Country;

import java.awt.Color;
import java.awt.color.ColorSpace;

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

	public RamzorColor getColor() {
		return this.choice(coefficient);
	}
	
	public double getProbability() {
		return probability;
	}


	public String getColorOfGuitar() {

	    if(color.equals(Color.GREEN))
	        return "GREEN";
	    else if(color.equals(Color.YELLOW))
	        return "YELLOW";
	    else if(color.equals(Color.ORANGE))
	        return "ORANGE";
	    else if(color.equals(Color.RED))
	        return "RED";
	    else
	        return "Unknown Color";
	}
	
	public Color getColorEnum() {
		double c = this.coefficient;
		if(c <= 0.4) return color.green;
		else if(c > 0.4 && c <= 0.6) return color.yellow;
		else if(c > 0.6 && c <= 0.8) return color.orange;
		else return color.red; 
	}
	
}//RamzorColor class

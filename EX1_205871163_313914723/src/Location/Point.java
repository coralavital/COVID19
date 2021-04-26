package Location;

/***
 * Representation of a Point class 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public class Point {

	//Data members
	private int x;
	private int y;

	//Constructor
	/**
	 * @param integer x.
	 * @param integer y.
	 */
	public Point(int x,int y) {
		this.x=x;
		this.y=y;
	}

	//Copy constructor
	/***
	 * Copy constructor 
	 * @param p, Object
	 */
	public Point(Point p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	//ToString
	/**
	 * ToString functions.
	 * @return String.
	 */
	public String toString() {
		return "[x=" + x + "], [y=" + y + "]";
	}

	//Equals
	/**
	 * Equals functions.
	 * @param other.
	 * @return boolean (true if the points are equals).
	 */
	public boolean equals(Object other) {
		if (other instanceof Point)
			return ((x == ((Point)other).x) && ((y == ((Point)other).y)));
		return false;
	}
	
	//Getters
	/**
	 * Get functions that give x value.
	 * @return Object.
	 */
	public int getX() {
		return this.x;
	}
	/**
	 * Get functions that give y value.
	 * @return Object.
	 */
	public int getY() {
		return this.y;
	}

}//Point class

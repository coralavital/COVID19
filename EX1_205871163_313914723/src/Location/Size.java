package Location;

/***
 * Representation of a Size class 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */

public class Size {

	//Data members
	private int width;
	private int height;

	//Constructor
	/**
	 * constructor functions.
	 * @param integer width.
	 * @param integer height.
	 */
	public Size(int width, int height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	//Copy constructor
	/***
	 * Copy constructor 
	 * @param p, Object
	 */
	public Size(Size s ) {
		
		this.width = s.getWidth();
		this.height = s.getHeight();
		
	}

	//ToString
	/**
	 * toString functions.
	 * @return String.
	 */
	public String toString() {
		return "[width=" + width + "], [height=" + height + "]";
	}

	//Equals
	/**
	 * Equals functions.
	 * @param other.
	 * @return boolean (true if the sizes are equals).
	 */
	public boolean equals(Object other) {
		if (other instanceof Size)
			return ((width == ((Size)other).width) && ((height == ((Size)other).height)));
		return false;
	}

	//Getters
	/**
	 * Get functions that give width value.
	 * @return integer width.
	 *@author coral.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Get functions that give height value.
	 * @return integer height.
	 */
	public int getHeight() {
		return this.height;
	}

}//Size class

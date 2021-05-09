package Location;
//Import staff
import java.util.Random;

/***
 * Representation of a Location class 
 * 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 * 
 *
 */


public class Location {

	//Data members
	private Point position;
	private Size size;

	//Constructor
	/**
	 * Constructor.
	 * @param Point position.
	 * @param Size size.
	 */
	public Location(Point position, Size size) {
		this.position = position;
		this.size = size;
	}

	//ToString
	/**
	 * ToString functions.
	 * @return String.
	 *@author coral.
	 */
	public String toString() {
		return "Location of the settlement --> \t|Start point --> " 
				+ position.toString() 
				+ "|,\n\t\t\t\t\t\t|The size of the settlement --> " 
				+ size.toString() + "|";
	}

	//Getters
	/**
	 * This method return the position of the person.
	 * @return Point object.
	 */
	public Point getPosition() {
		return position;
	}

	/**
	 * This method return the size of the .
	 * @return Size object.	
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * used onced in order to find the settlement we clicked on GUI
	 * @return: String
	 */
	public String checkRect() {
		return "java.awt.Rectangle[x="+this.position.getX()+",y="+this.getPosition().getY()+",width="+this.size.getWidth()+",height="+this.size.getHeight()+"]";
	}



}//Location class

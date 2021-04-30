package IO;

//Import staff
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import Country.City;
import Country.Kibbutz;
import Country.Map;
import Country.Moshav;
import Country.RamzorColor;
import Country.Settlement;
import Location.Location;
import Location.Point;
import Location.Size;
import Population.Healthy;
import Population.Person;
import Population.Sick;

/***
 * Representation of a SimulationFile class 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 */

public class SimulationFile {

	private String path ;
	private List<Person> people;
	private List<Settlement> settlement;
	private String name;
	private Location location;
	private int age;
	private Point point;
	private List<Sick> sick = null;
	private List<Healthy> healthy = null;
	private int totalPersons;
	private int totalVaccines;
	private List<Settlement> linkTo;


	//Constructor 
	/**
	 * Constructor
	 * @param url, String
	 */
	public SimulationFile(String url) {
		//Copy the url of the file in the computer into  path
		this.path = url;
	}

	//Setters
	/**
	 * setter method for name
	 * @param name, String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * setter method for location, making Point and Size as well according to the parameters we got
	 * @param Xpoint, int
	 * @param Ypoint, int 
	 * @param h, int
	 * @param w, int
	 */
	public void setLocation(int Xpoint, int Ypoint, int h, int w) {
		this.location = new Location((new Point(Xpoint,Ypoint)), (new Size(h,w)));
	}

	//Getter
	/**
	 * generate age according to the formula we got and also making sure that the age is realistic
	 * @return age , int
	 */
	public int getAge() {
		Random rand = new Random();
		while(true) {
			int y = rand.nextInt(4);
			int x = (int)rand.nextGaussian()*6+9;
			int age = ((5*x)+y);
			if(age >= 0 && age <= 120)
				return age;
		}
	}


	//Sum of line
	/**
	 * count the lines inside the text file that we got
	 * @return int, the number of lines in the file
	 */
	private int sumOfLine() {

		try {

			BufferedReader br= new BufferedReader(new FileReader(path));
			String s;
			int counter = 0;
			while (( s = br.readLine())!= null) {
				counter += 1;
			}
			br.close();
			return counter;
		}

		catch(Exception ex) {

			return 0;//exit the program if can't read from file

		}
	}

	public void setLinkTo(List<Settlement> settlement) throws IOException {
		String[] buffer;
		String s;
		
		BufferedReader br= new BufferedReader(new FileReader(path));
		
		while ((s = br.readLine())!= null) {

			//The output of the text file
			s = s.replaceAll(" ", "");
			buffer = s.split(";");

			if(buffer[0].equals("#")) {
				String s1 = buffer[1];
				String s2 = buffer[2];
				
				for(int i = 0; i < settlement.size(); i++) {
					if(settlement.get(i).getName().equals(s1)) {
						for(int j = 0; j < settlement.size(); j++)
							if(settlement.get(j).getName().equals(s2)) {
								
								settlement.get(i).getLinkTo().add(settlement.get(j));
								settlement.get(j).getLinkTo().add(settlement.get(i));
							}
					}
				}
			}
		}
		
		br.close();
	}
	//Read from file
	/**
	 * read the text from file, put them into the correct values and put them in to our objects variable, 
	 * then we are making the settlement according the values we got from the text file
	 * @return List,, Settlement
	 * @throws IOException
	 */
	public List<Settlement> readFromFile() throws IOException {

		String[] buffer;
		int size = sumOfLine();
		List<Settlement> settlement = new ArrayList<>();
		BufferedReader br= new BufferedReader(new FileReader(path));

		String s;
		int i = 0;

		while ((s = br.readLine())!= null) {

			//The output of the text file
			s = s.replaceAll(" ", "");
			buffer = s.split(";");

			if(buffer[0].equals("#")) 
				continue;

			//get all the attributes  to make settlement
			setName(buffer[1]);
			setLocation(Integer.parseInt(buffer[2]),
					Integer.parseInt(buffer[3]),
					Integer.parseInt(buffer[4]),
					Integer.parseInt(buffer[5]));
			List<Person> listPerson = new ArrayList<>();
			List<Settlement> linkTo = new ArrayList<>();
			RamzorColor ramzorColor = RamzorColor.Green;




			if(buffer[0].equals("City")) {
				City newCity = new City(name, location, listPerson, sick, healthy, ramzorColor, totalVaccines, linkTo);
				for(int j = 0; j < Integer.parseInt(buffer[6]); j++) {
					int age = getAge();
					Healthy h = new Healthy(age, newCity.randomLocation(), newCity);
					newCity.addPerson(h);
				}
				settlement.add(newCity);
			}

			else if(buffer[0].equals("Moshav")) {
				Moshav newMoshav = new Moshav(name, location, listPerson, sick, healthy, ramzorColor, totalVaccines, linkTo);
				for(int j = 0; j < Integer.parseInt(buffer[6]); j++) {
					int age = getAge();
					Healthy h = new Healthy(age, newMoshav.randomLocation(), newMoshav);
					newMoshav.addPerson(h);
				}

				settlement.add(newMoshav);
			}

			else if(buffer[0].equals("Kibbutz")) {
				Kibbutz newKibbutz = new Kibbutz(name, location, listPerson, sick, healthy, ramzorColor, totalVaccines, linkTo);
				for(int j = 0; j < Integer.parseInt(buffer[6]); j++) {
					int age = getAge();
					Healthy h = new Healthy(age, newKibbutz.randomLocation(), newKibbutz);
					newKibbutz.addPerson(h);
				}

				settlement.add(newKibbutz);
			}


			else {
				System.out.println("***Error*** The type of the settlement is not definded");

			}

			i = i + 1;
		}
		
		br.close();
		
		setLinkTo(settlement);
		
		
		return settlement;
	}


}//SimulationFile class
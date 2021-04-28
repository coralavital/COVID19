package Simulation;
//Import staff
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.*;
import java.util.Random;
import Country.Map;
import IO.SimulationFile;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Virus.BritishVariant;
import Virus.IVirus;

/***
 * Representation of a Main class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */

public class Main {


	// Loading
	/**
	 * Methods that let the user give us the path to the text file, then making SimulationFile object with that path
	 * after we are making map object with simulation object method that we just created to read from the text file and generate text into map attributes
	 * @return map, Object
	 * @throws IOException
	 */
	public static Map loading() throws IOException {
		String url = loadFileFunc();
		SimulationFile simolation = new SimulationFile(url);

		Map map =  new Map(simolation.readFromFile());
		return map;
	}


	// Initialization
	/**
	 * Running on all the settlements and the 10% of people in it and make them sick
	 * @param map
	 */
	public static void initialization(Map map) {
		BritishVariant britishVariant = new BritishVariant();
		for(int i = 0; i< map.getSettlements().length; i++) 
			//Run on all the index in the settlement array
			for(int j=0; j < (map.getSettlements()[i].getPeople().size())*0.1; j++) { 
				//Run on 10% of the people in that settlement 
				//Here we got new sick person with British variant that get the element of the first healthy person from the list
				Sick sick = new Sick(map.getSettlements()[i].getPeople().get(j).getAge(),
						map.getSettlements()[i].getPeople().get(j).getLocation(),
						map.getSettlements()[i].getPeople().get(j).getSettlement(), Clock.now(), britishVariant);
				map.getSettlements()[i].getPeople().set(j, sick);
			}
	}


	/** Let the user choose where the text file is located
	 * @return String
	 */
	private static String loadFileFunc() {

		// Instead of "(Frame) null" use a real frame, when GUI is learned
		FileDialog fd = new FileDialog((Frame) null, "Please choose a file:", FileDialog.LOAD);
		fd.setVisible(true);
		if (fd.getFile() == null)
			return null;
		File f = new File(fd.getDirectory(), fd.getFile());
		return f.getPath();

	}


	/**
	 * Running on all the settlements and the people in it, then every sick person try to contagion six other people
	 * @param map
	 * @return map
	 */
	public static Map simulation(Map map) {

		for (int j = 0; j < map.getSettlements().length; j++) {
			for (int k = 0; k < map.getSettlements()[j].getPeople().size()*0.1; k++) {
				if (map.getSettlements()[j].getPeople().get(k) instanceof Sick) {

					for (int h = 0; h < 6; h++) {
						Random rand = new Random();
						Sick s = (Sick) map.getSettlements()[j].getPeople().get(k);
						IVirus v = s.getVirus();
						int index = rand.nextInt(map.getSettlements()[j].getPeople().size());
						Person person = map.getSettlements()[j].getPeople().get(index);
						if(person instanceof Sick)
							break;
						else {
							if(v.tryToContagion(s, person)) {
								map.getSettlements()[j].getPeople().set(index, person.contagion(v));

							}
						}
					}
				}
			}
		}
		
		return map;
		
	}

	/**
	 * Main method
	 */
	public static void main(String[] args) {
		Map map = null;
		
		try {
			map = loading();
		} 
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		initialization(map);
		
		for(int n = 0; n < 5; n++) {
			simulation(map);
			Clock.nextTick();
		}
		
		int count;
		
		for (int i = 0; i < map.getSettlements().length; i++) {
			count = 0;
			for (int j = 0; j < map.getSettlements()[i].getPeople().size(); j++) {
				if(map.getSettlements()[i].getPeople().get(j) instanceof Sick) {
					++count;
				}
			}
			
			System.out.println("\t\tThe settlement -->" + map.getSettlements()[i].getName() + "\n\t\tHas --> |"+ count +"| sick people");

		}
	}

}//Main class

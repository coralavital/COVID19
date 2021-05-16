package Simulation;

//Import staff
import java.awt.FileDialog;
import java.awt.Frame;
import java.io.*;
import java.util.Random;
import Country.Map;
import Country.Settlement;
import IO.SimulationFile;
import Population.Convalescent;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Population.Vaccinated;
import UI.MainWindow;
import UI.StatisticsWindow;
import UI.UserMenu;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public class Simulation{

	//The simulation from assignment 3
	// Initialization
	/**
	 * Running on all the settlements and the 20% of people in it and make them sick
	 * @param map, Map object
	 * @param settlement, Settlement object
	 */
	public void initialization(Settlement settlement) {
		Random rand = new Random();
		IVirus virus = null;


		for(int j = 0; j < (settlement.getNonSick().size()*0.2); j++) {

			int index = rand.nextInt(settlement.getNonSick().size());

			Person person = settlement.getNonSick().get(index);

			int value = rand.nextInt(3);

			if(value < 0)
				value = Math.abs(value);

			if(value == 0)
				virus = new BritishVariant();

			else if(value == 1)
				virus = new ChineseVariant();

			else if(value == 2)
				virus = new SouthAfricanVariant();


			Sick sick = new Sick(person.getAge(), person.getLocation(), person.getSettlement(),
					Clock.now(), virus);

			settlement.getNonSick().remove(j);
			settlement.getPeople().remove(j);
			settlement.addPerson(sick);

			for(int k = 0; k < 3; k++) {

				System.out.println("The person in the index " + j + " from the settlement: " + settlement.getName() 
				+ " became ill");
				System.out.println("Now the system will try to infect 3 non-sick people for this sick person.");
				System.out.println("The non sick person number "+ k + ", index of virus = " + value + ","
						+ " the settlement name is " + settlement.getName());

				if(settlement.getNonSick().size() > 3 ) {//changed from [j] to [i]
					//Here is called a method whose function is to infect a random person with the help of 
					//the created sick person.
					tryToInfect(sick, settlement);
				}
			}

		}
	}


	/**
	 * Method that try to infect 3 non-sick people by one sick person
	 * @param sick, Sick object
	 * @param settlement, Settlement object
	 */
	public void tryToInfect(Sick sick, Settlement settlement) {

		Random rand = new Random();
		IVirus virus = sick.getVirus();
		Sick s;
		IVirus v = null;

		int value = 0;
		int index = 0;
		int newP = 0;


		newP = rand.nextInt(settlement.getNonSick().size());
		if(newP < 0)
			newP = Math.abs(newP);

		Person p = settlement.getNonSick().get(newP);


		if(virus instanceof BritishVariant) {
			for(int n = 0; n < 3; n++) {
				if(Main.getMain().getUserMenu().getData()[0][n])
					value++;
			}

			index = rand.nextInt(value);

			if(index == 0) 
				v = new BritishVariant();

			else if(index == 1)
				v = new ChineseVariant();

			else if(index == 2)
				v = new SouthAfricanVariant();


			s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);

			if(v.tryToContagion(s, p)) {
				settlement.addPerson(p.contagion(v));
				settlement.getNonSick().remove(newP);
				settlement.getPeople().remove(newP);
				System.out.println("The infection succeeded");
			}
			else
				System.out.println("The infection failed");

		}

		if(virus instanceof ChineseVariant) {
			for(int n = 0; n < 3; n++) {
				if(Main.getMain().getUserMenu().getData()[1][n])
					value++;
			}

			index = rand.nextInt(value);

			if(index == 0) 
				v = new BritishVariant();

			if(index == 1)
				v = new ChineseVariant();

			if(index == 2)
				v = new SouthAfricanVariant();


			s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);

			if(v.tryToContagion(s, p)) {
				settlement.addPerson(p.contagion(v));
				settlement.getNonSick().remove(newP);
				settlement.getPeople().remove(newP);
				System.out.println("The infection succeeded");
			}
			else
				System.out.println("The infection failed");

		}

		if(virus instanceof SouthAfricanVariant) {
			for(int n = 0; n < 3; n++) {
				if(Main.getMain().getUserMenu().getData()[2][n])
					value++;
			}

			index = rand.nextInt(value);

			if(index == 0) 
				v = new BritishVariant();

			else if(index == 1)
				v = new ChineseVariant();

			else if(index == 2)
				v = new SouthAfricanVariant();



			s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);

			if(v.tryToContagion(s, p)) {
				settlement.addPerson(p.contagion(v));
				settlement.getNonSick().remove(newP);
				settlement.getPeople().remove(newP);
				System.out.println("The infection succeeded");
			}
			else
				System.out.println("The infection failed");

		}
	}
	
	/**
	 * Method that try to recover sick people to be convalescent people if they getContagiousTime > 25 days.
	 */
	public void recoverToHealthy(Settlement settlement) {

		//A loop that passes over the person how found in the Sick-list
		for (int k = 0; k < settlement.getSick().size(); k++) {
			//A loop that passes over the number of people who have passed 25 days from the moment they were infected with the virus 
			if(Clock.days(settlement.getSick().get(k).getContagiousTime()) > 25) {

				System.out.println("try to recover the person in the index " + k + " in the settlement " + settlement.getName());

				Sick s = settlement.getSick().get(k);

				settlement.getSick().remove(s);
				settlement.getPeople().remove(s);
				s.recover();
				settlement.addPerson(s);
				System.out.println("this person was a sick and now he healthy");
			}
			else
				System.out.println("No people were found to be ill for more than 25 days");

		}

	}	

	/**
	 * Method that try to transfer sick from one settlement to another
	 */
	public void moveSettlement(Settlement settlement) {

		if(settlement.getLinkTo().size() > 0) {
			for(int j = 0; j < settlement.getPeople().size()*0.03; j++) {

				Random rand = new Random();
				int index = rand.nextInt(settlement.getLinkTo().size());
				Settlement s = settlement.getLinkTo().get(index);

				int value = rand.nextInt(settlement.getPeople().size());
				settlement.transferPerson(settlement.getPeople().get(value), s);
			}
		}
		else
			System.out.println("There are not link settlement for this settlement");
	}

	/**
	 * A method that tries to vaccinate healthy people if there are vaccine doses waiting.
	 */
	public void vaccinateHealthy(Settlement settlement) {


		if(settlement.getTotalVaccines() > 0) {
			for (int k = 0; k < settlement.getNonSick().size(); k++) {

				Vaccinated v = new Vaccinated(settlement.getNonSick().get(k).getAge(),
						settlement.getNonSick().get(k).getLocation(),
						settlement.getNonSick().get(k).getSettlement(), Clock.now());
				settlement.getNonSick().remove(k);
				settlement.getPeople().remove(k);
				settlement.addPerson(v);

				settlement.decVaccineByOne();

				System.out.println("The person in index " + k + "resilient and now he is a resilient person");
			}
		}
		else
			System.out.println("There were no vaccines left in the pool");
	}



	public void killPeople(Settlement settlement) {

		if(settlement.getSick().size() > 0) {
			for(int j = 0; j < settlement.getSick().size(); j++) {
				if(settlement.getSick().get(j).tryToDie() == true) {
					settlement.getSick().remove(j);
					settlement.setNumberOfDead();
				}
			}
		}


	}


}//Main class
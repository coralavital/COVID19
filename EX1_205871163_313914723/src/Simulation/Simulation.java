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
import Population.Vaccinated;
import UI.MainWindow;
import UI.StatisticsWindow;
import UI.UserMenu;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public class Simulation{

	
	// Initialization
	/**
	 * Running on all the settlements and the 20% of people in it and make them sick
	 * @param map
	 */
	public void initialization() {
		Random rand = new Random();
		IVirus virus = null;

		for(int i = 0; i < Main.getMap().getSettlements().length; i++) {
			for(int j = 0; j < (Main.getMap().getSettlements()[i].getNonSick().size()*0.2); j++) {

					Person person = Main.getMap().getSettlements()[i].getNonSick().get(j);

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

					Main.getMap().getSettlements()[i].getNonSick().remove(j);
					Main.getMap().getSettlements()[i].getPeople().remove(j);
					Main.getMap().getSettlements()[i].addPerson(sick);

					for(int k = 0; k < 3; k++) {
						
						System.out.println("The person in the index " + j + " from the settlement in the index: " + i 
								+ " became ill");
						System.out.println("Now the system will try to infect 3 non-sick people for this sick person.");
						System.out.println("The non sick person number "+ k + ", index of virus = " + value + ","
								+ " the settlement index is " + i);
						
						if(Main.getMap().getSettlements()[i].getNonSick().size() > 3 ) {//changed from [j] to [i]
							//Here is called a method whose function is to infect a random person with the help of 
							//the created sick person.
							tryToInfect(sick, i);
						}
					}
				
			}
		}

		//Main.setPLAY(false);
		//Main.setON(false);
	}

	
	public void tryToInfect(Sick sick, int i) {

		Random rand = new Random();
		IVirus virus = sick.getVirus();
		Sick s;
		IVirus v = null;

		int value = 0;
		int index = 0;
		int newP = 0;


		newP = rand.nextInt(Main.getMap().getSettlements()[i].getNonSick().size());
		if(newP < 0)
			newP = Math.abs(newP);
		
		Person p = Main.getMap().getSettlements()[i].getNonSick().get(newP);


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
				Main.getMap().getSettlements()[i].addPerson(p.contagion(v));
				Main.getMap().getSettlements()[i].getNonSick().remove(newP);
				Main.getMap().getSettlements()[i].getPeople().remove(newP);
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
				Main.getMap().getSettlements()[i].addPerson(p.contagion(v));
				Main.getMap().getSettlements()[i].getNonSick().remove(newP);
				Main.getMap().getSettlements()[i].getPeople().remove(newP);
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
				Main.getMap().getSettlements()[i].addPerson(p.contagion(v));
				Main.getMap().getSettlements()[i].getNonSick().remove(newP);
				Main.getMap().getSettlements()[i].getPeople().remove(newP);
				System.out.println("The infection succeeded");
			}
			else
				System.out.println("The infection failed");

		}
	}

	//Try to transfer sick from one settlement to another.
	public void recoverToHealthy() {
		//A loop that goes through all forms of settlement
		for(int j = 0; j < Main.getMap().getSettlements().length; j++) {
			//A loop that passes over the person how found in the Sick-list
			for (int k = 0; k < Main.getMap().getSettlements()[j].getSick().size(); k++) {
				//A loop that passes over the number of people who have passed 25 days from the moment they were infected with the virus 
				if(Clock.days(Main.getMap().getSettlements()[j].getSick().get(k).getContagiousTime()) > 25) {
					
					System.out.println("try to recover the person in the index " + k + " in the settlement " + j);
					Person p = Main.getMap().getSettlements()[j].getSick().get(k).recover();
					Main.getMap().getSettlements()[j].addPerson(p);
					Main.getMap().getSettlements()[j].getSick().remove(k);
					System.out.println("this person wad a sick and now he healthy");
				}
				else
					System.out.println("No people were found to be ill for more than 25 days");

			}

		}
		//Main.setPLAY(false);
		//Main.setON(false);
	}

	
	public void moveSettlement() {
		for(int i = 0; i < Main.getMap().getSettlements().length; i++) {
			for(int j = 0; j < Main.getMap().getSettlements()[i].getPeople().size()*0.03; j++) {

				Random rand = new Random();
				int index = rand.nextInt(Main.getMap().getSettlements().length);
				Main.getMap().getSettlements()[i].transferPerson(Main.getMap().getSettlements()[i].getPeople().get(j),
						Main.getMap().getSettlements()[index]);
			}
		}
	}

	
	public void vaccinateHealthy() {
		
		for(int j = 0; j < Main.getMap().getSettlements().length; j++) {
			for (int k = 0; k < Main.getMap().getSettlements()[j].getNonSick().size(); k++) {
				if(Main.getMap().getSettlements()[j].getTotalVaccines() > 0) {
					
					Vaccinated v = new Vaccinated(Main.getMap().getSettlements()[j].getNonSick().get(k).getAge(),
							Main.getMap().getSettlements()[j].getNonSick().get(k).getLocation(),
							Main.getMap().getSettlements()[j].getNonSick().get(k).getSettlement(), Clock.now());
					Main.getMap().getSettlements()[j].getNonSick().remove(k);
					Main.getMap().getSettlements()[j].getPeople().remove(k);
					Main.getMap().getSettlements()[j].addPerson(v);
					
					Main.getMap().getSettlements()[j].decONE();
					
					System.out.println("The person in index " + k + "resilient and now he is a resilient person");
				}
				else
					System.out.println("There were no vaccines left in the pool");
			}
		}
		Main.setPLAY(false);
		Main.setON(false);
	}

	

}//Main class
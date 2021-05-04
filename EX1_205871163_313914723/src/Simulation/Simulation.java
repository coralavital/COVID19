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
import UI.UserMenu;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public class Simulation{


	// Initialization
	/**
	 * Running on all the settlements and the 10% of people in it and make them sick
	 * @param map
	 */
	public void initialization() {
		Random rand = new Random();
		IVirus virus;

		for(int i = 0; i < Main.getMap().getSettlements().length; i++) {
			for(int j = 0; j < Main.getMap().getSettlements()[i].getNonSick().size()*0.2; j++) {

				int index = rand.nextInt(Main.getMap().getSettlements()[i].getNonSick().size());
				Person person = Main.getMap().getSettlements()[i].getNonSick().get(index);
				int value = rand.nextInt(3);

				if(value == 1)
					virus = new BritishVariant();

				else if(value == 2)
					virus = new ChineseVariant();

				else
					virus = new SouthAfricanVariant();

				Sick sick = new Sick(person.getAge(), person.getLocation(), person.getSettlement(), Clock.now(), virus);
				Main.getMap().getSettlements()[i].getNonSick().remove(j);
				Main.getMap().getSettlements()[i].addPerson(sick);
				
			}

		}
	}



	/** Let the user choose where the text file is located
	 * @return String
	 */



	/**
	 * Running on all the settlements and the people in it, then every sick person try to contagion six other people
	 * @param map
	 * @return map
	 */
	public void simulation() {

		for (int j = 0; j < Main.getMap().getSettlements().length; j++) {
			for (int k = 0; k < Main.getMap().getSettlements()[j].getSick().size(); k++) {
					
					for (int h = 0; h < 3; h++) {
						Random rand = new Random();
						Sick s = Main.getMap().getSettlements()[j].getSick().get(k);
						IVirus v = s.getVirus();
						int index = rand.nextInt(Main.getMap().getSettlements()[j].getNonSick().size());
						Person person = Main.getMap().getSettlements()[j].getNonSick().get(index);
						if(person instanceof Sick)
							break;
						else {
							if(v.tryToContagion(s, person)) {
								Main.getMap().getSettlements()[j].addPerson(person.contagion(v));
								Main.getMap().getSettlements()[j].getNonSick().remove(index);
							}
						}
					}
				
			}
	
		}
		
	}


	public void transferSick() {
		for(int j = 0; j < Main.getMap().getSettlements().length; j++) {
			for (int k = 0; k < Main.getMap().getSettlements()[j].getSick().size(); k++) {
				if(Main.getMap().getSettlements()[j].getSick().get(k).getContagiousTime() > 25) {
					Person p = Main.getMap().getSettlements()[j].getSick().get(k).recover();
					Main.getMap().getSettlements()[j].addPerson(p);
					Main.getMap().getSettlements()[j].getSick().remove(k);
				}
				
			}

		}
	}

	public void moveSettlement() {
		for(int i = 0; i < Main.getMap().getSettlements().length; i++) {
			for(int j = 0; j < Main.getMap().getSettlements()[i].getNonSick().size()*0.03; j++) {
				
				Random rand = new Random();
				int index = rand.nextInt(Main.getMap().getSettlements().length);
				Main.getMap().getSettlements()[i].transferPerson(Main.getMap().getSettlements()[i].getPeople().get(j), Main.getMap().getSettlements()[index]);
			}
		}
	}


	public void vaccinateHealthy() {
		for(int j = 0; j < Main.getMap().getSettlements().length; j++) {
			for (int k = 0; k < Main.getMap().getSettlements()[j].getNonSick().size(); k++) {
				if(Main.getMap().getSettlements()[j].getTotalVaccines() > 0) {
					Vaccinated v = new Vaccinated(Main.getMap().getSettlements()[j].getNonSick().get(k).getAge(),Main.getMap().getSettlements()[j].getNonSick().get(k).getLocation(),Main.getMap().getSettlements()[j].getNonSick().get(k).getSettlement(), Clock.now());
					Main.getMap().getSettlements()[j].getNonSick().remove(k);
					Main.getMap().getSettlements()[j].addPerson(v);
				}
			}
		}
	}



}//Main class
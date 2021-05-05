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
			for(int j = 0; j < (Main.getMap().getSettlements()[i].getPeople().size()*0.2); j++) {
				if(!(Main.getMap().getSettlements()[i].getPeople().get(i) instanceof Sick)) {
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

					Main.getMap().getSettlements()[i].addPerson(sick);
					Main.getMap().getSettlements()[i].getNonSick().remove(j);
					Main.getMap().getSettlements()[i].getPeople().remove(j);

					if(Main.getMap().getSettlements()[j].getNonSick().size() > 3 ) {
						//simulation(sick, person, i, j);
					}
				}
			}
		}

		//Main.getStatistics().getModel().fireTableDataChanged();
		//Main.getMain().getMapPanel().repaint();
	}


	public void simulation(Sick sick, Person person, int i, int j) {

		Random rand = new Random();
		IVirus virus = sick.getVirus();
		IVirus v = null;
		Sick s;
		int index = 0;
		//to try 
		for (int h = 0; h < 3; h++) {
			if(person instanceof Sick)
				break;
			else {

				for(int k = 0; k < 3; k++) {
					if(virus instanceof BritishVariant) {
						if(Main.getMain().getUserMenu().getData()[0][k]) {
							index++;
						}

						rand.nextInt(index);
						if(index == 1) 
							v = new BritishVariant();
						if(index == 2)
							v = new ChineseVariant();
						else
							v = new SouthAfricanVariant();


						s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);
						if(v.tryToContagion(s, person)) {
							Main.getMap().getSettlements()[i].getNonSick().remove(j);
							Main.getMap().getSettlements()[i].getPeople().remove(j);
							Main.getMap().getSettlements()[i].addPerson(person.contagion(v));
						}
					}
					if(virus instanceof ChineseVariant) {
						if(Main.getMain().getUserMenu().getData()[1][k]) {
							index++;
						}

						rand.nextInt(index);
						if(index == 1) 
							v = new BritishVariant();
						if(index == 2)
							v = new ChineseVariant();
						if(index == 3)
							v = new SouthAfricanVariant();


						s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);
						if(v.tryToContagion(s, person)) {
							Main.getMap().getSettlements()[i].getNonSick().remove(j);
							Main.getMap().getSettlements()[i].getPeople().remove(j);
							Main.getMap().getSettlements()[i].addPerson(person.contagion(v));
						}
					}
					if(virus instanceof SouthAfricanVariant) {
						if(Main.getMain().getUserMenu().getData()[2][k]) {
							index++;
						}

						rand.nextInt(index);
						if(index == 1) 
							v = new BritishVariant();
						if(index == 2)
							v = new ChineseVariant();
						if(index == 3)
							v = new SouthAfricanVariant();


						s = new Sick(sick.getAge(), sick.getLocation(), sick.getSettlement(), sick.getContagiousTime(), v);
						if(v.tryToContagion(s, person)) {
							Main.getMap().getSettlements()[i].getNonSick().remove(j);
							Main.getMap().getSettlements()[i].getPeople().remove(j);
							Main.getMap().getSettlements()[i].addPerson(person.contagion(v));
						}
					}
				}

			}
		}

		//Main.getStatistics().getModel().fireTableDataChanged();
		Main.getMain().getMapPanel().repaint();
	}


	//Try to transfer sick from one settlement to another.
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
					Main.getMap().getSettlements()[j].addPerson(v);
					Main.getMap().getSettlements()[j].getNonSick().remove(k);
					Main.getMap().getSettlements()[j].decONE();
				}
			}
		}
	}



}//Main class
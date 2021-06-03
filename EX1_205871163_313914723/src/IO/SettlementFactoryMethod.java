package IO;

import java.util.List;
import java.util.Random;
import Country.City;
import Country.Kibbutz;
import Country.Map;
import Country.Moshav;
import Country.RamzorColor;
import Country.Settlement;
import Location.Location;
import Population.Healthy;
import Population.Person;
import Population.Sick;
import Simulation.Clock;
import Virus.BritishVariant;

public class SettlementFactoryMethod {

	public Settlement settlementFactory = null;
	public int numberOfPeople;
	BritishVariant Bvirus = new BritishVariant();

	public Settlement createFactory(String type, String name, Location location, List<Sick> sick, List<Person> NonSick, RamzorColor ramzorColor, int totalVaccines, List<Settlement> LinkTo, Map map, int total) {
		if(type == null)
			return null;
		else {
			if(type.equals("City")) 
				settlementFactory = new City(name, location, sick, NonSick, ramzorColor, 0, LinkTo, map);

			else if(type.equals("Moshav")) 
				settlementFactory = new Moshav(name, location, sick, NonSick, ramzorColor, 0, LinkTo, map);

			else if(type.equals("Kibbutz")) 
				settlementFactory = new Kibbutz(name, location, sick, NonSick, ramzorColor, 0, LinkTo, map);

			if(settlementFactory != null) {
				for(int j = 0; j < total; j++) {
					int age = getAge();
					Healthy h = new Healthy(age, settlementFactory.randomLocation(), settlementFactory);
					settlementFactory.addPerson(h);
				}
				for(int k = 0; k < total * 0.1; k++) {

					Healthy hh = (Healthy)settlementFactory.getNonSick().get(k);
					Sick newSick = new Sick(hh.getAge(), hh.getLocation(), settlementFactory, Clock.now(), Bvirus);
					settlementFactory.getNonSick().remove(hh);
					settlementFactory.addPerson(newSick);
				}
				settlementFactory.setTotalPersons();
			}
		}
		return settlementFactory;
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


}

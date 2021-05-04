package Simulation;

import javax.swing.SwingUtilities;

import Country.Map;
import UI.MainWindow;

public class Main {

	static Map map;

	public static void load() {
		map = null;
		MainWindow main = new MainWindow();

		while(main.getUserMenu().getIsON()) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					//main.updateAll();
				}
			});
		}

	}

	public static Map getMap() {
		return map;
	}
	public static void setMap(Map map) {
		Main.map = map;
	}

	public static void main(String[] args) {

		load();
	}

}

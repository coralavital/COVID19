package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;
import Country.Map;
import IO.StatisticsFile;
import Population.Sick;
import Simulation.Clock;
import Virus.BritishVariant;
import Virus.ChineseVariant;
import Virus.IVirus;
import Virus.SouthAfricanVariant;


public class StatisticsWindow extends JFrame {

	static JFrame statisticFrame = new JFrame("Statistics Window");
	private JTextField textFilter;


	private void statisticsInitialization(Map map, IVirus virus) {
		for(int i = 0; i< map.getSettlements().length; i++) 
			//Run on all the index in the settlement array
			for(int j=0; j < (map.getSettlements()[i].getPeople().size())*0.1; j++) { 
				//Run on 10% of the people in that settlement 
				//Here we got new sick person with British variant that get the element of the first healthy person from the list
				Sick sick = new Sick(map.getSettlements()[i].getPeople().get(j).getAge(),
						map.getSettlements()[i].getPeople().get(j).getLocation(),
						map.getSettlements()[i].getPeople().get(j).getSettlement(), Clock.now(), virus);
				map.getSettlements()[i].getPeople().set(j, sick);
			}
	}

	public StatisticsWindow(Map map) {

		super("StatisticsWindow");
		statisticFrame.setLayout(new BorderLayout());

		JPanel statisticsPanel = new JPanel();

		statisticsPanel.setLayout(new BoxLayout(statisticsPanel, BoxLayout.LINE_AXIS));
		JPanel southPanel =new JPanel();

		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));

		final String [] names = {"Col Select: NONE", "Col Select: Name", "Col Select: Type ", "Col Select: Color", "Col Select: LinkTo"};


		statisticsPanel.add(new JComboBox<String>(names));

		statisticsPanel.add(textFilter = new JTextField("text filter...", 20));
		JButton save = new JButton("Save");
		southPanel.add(save);
		save.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				try {
					new StatisticsFile();
				} 
				catch (FileNotFoundException e1) {
					e1.printStackTrace();
					System.out.println("Error at save action from StatisticsWindow");
				}

			}
		});


		JButton add = new JButton("Add Sick");
		southPanel.add(add);
		add.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				int choice = rand.nextInt(3 - 1 + 1) + 1;

				switch(choice) {
				case 1 :
					BritishVariant britishVariant = new BritishVariant();
					statisticsInitialization(map, britishVariant);
					break;
				case 2 :
					ChineseVariant chineseVariant = new ChineseVariant();
					statisticsInitialization(map, chineseVariant);
				case 3 :
					SouthAfricanVariant southAfricanVariant = new SouthAfricanVariant();
					statisticsInitialization(map, southAfricanVariant);

				}
			}

	});


		southPanel.add(new JButton("Vaccinate"));

		statisticFrame.add(statisticsPanel, BorderLayout.NORTH);

		//Table
		class Table extends JPanel{
			private TableRowSorter<Model> sorter;

			Table(Map map){

				Model model = new Model(map);
				JTable table = new JTable (model);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setPreferredScrollableViewportSize(new Dimension(500 , 100));
				table.setFillsViewportHeight(true);
				table.setRowSorter(sorter = new TableRowSorter<Model>(model));
				statisticFrame.add(new JScrollPane(table));
				statisticFrame.pack();
				statisticFrame.setVisible(true);
			}
		}

		statisticFrame.add(new Table(map), BorderLayout.CENTER);


		statisticFrame.add(southPanel, BorderLayout.SOUTH);
		statisticFrame.pack();
		statisticFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		statisticFrame.setVisible(true);

}
}
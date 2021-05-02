package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

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

public class StatisticsWindow extends JFrame {

	static JFrame statisticFrame = new JFrame("Statistics Window");
	private JTextField textFilter;

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
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new StatisticsFile();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("Error at save action from StatisticsWindow");
				}
				
			}
		});
		
		
		southPanel.add(new JButton("Add Sick"));
		southPanel.add(new JButton("Vaccinate"));

		statisticFrame.add(statisticsPanel, BorderLayout.NORTH);

		//Table
		class Table extends JPanel{
			private TableRowSorter<Model> sorter;
			
			Table(Map map){

				Model model = new Model(map);
				JTable table = new JTable (model);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setPreferredScrollableViewportSize(new Dimension(500 , 70));
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
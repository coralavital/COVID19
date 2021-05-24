package IO;
//Import staff
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import Country.Map;

/***
 * Representation of a Location class 
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 */

/**
 * creating CSV file for Excel
 * 1st  we are getting the path where to locate the file from the user
 * 2nd columnNames will be the first row which will mention the categories 
 * 3rd filling the columns according to the categories
 * @author yonif & coral
 *
 */
public class StatisticsFile {
	//Data members
	String columnNames =  "NAME, TYPE, LOCATION, RAMZOR COLOR, NUMBER OF PEOPLE, NUMBER OF VACCINATE, LINKED SETTLEMENT, NUMBER OF SICK, NUMBER OF NON-SICK, NUMBER OF DEAD";
	String settlement;
	String fileName = null;
	public StatisticsFile(Map map, JFrame statisticFrame) throws FileNotFoundException {
		//The user choose a location for the file
		fileName = JOptionPane.showInputDialog("PLEASE ENTER A VALID FILE NAME: ");
		if(fileName != null) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("CHOOSE A DIRECTORY TO SAVE YOUR FILE: ");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			String str = null;
			int returnValue = jfc.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				if (jfc.getSelectedFile().isDirectory()) {
					str =  jfc.getSelectedFile().toString();
					//setting name to the file
					str = str + "\\ " + fileName + ".csv";
				}
			}        
			File fos = new File(str);
			PrintWriter pw = new PrintWriter(fos);
			pw.println(columnNames);
			for(int i = 0; i < map.getSettlements().length; i++) {
				//reading from the object and writing into the file	
				settlement = map.getSettlements()[i].getName() + ", " + map.getSettlements()[i].getType() 
						+ ", " + map.getSettlements()[i].getLocation().getPosition()
						+ ", " + map.getSettlements()[i].getRamzorColor().getColorOfGuitar() + ", " +
						(map.getSettlements()[i].getSick().size() + map.getSettlements()[i].getNonSick().size())  
						+ ", " + map.getSettlements()[i].getTotalVaccines() + ", " + map.getSettlements()[i].printLinked().replace(",", "") + ", " 
						+ map.getSettlements()[i].getSick().size() + ", " 
						+ map.getSettlements()[i].getNonSick().size() + ", " + map.getSettlements()[i].getNumberOfDead();			
				pw.println(settlement);
			}
			pw.close();
		}
		else {
			JOptionPane.showConfirmDialog(statisticFrame, "PLEASE SELECT NAME TO THE FILE!", "ERROR", JOptionPane.DEFAULT_OPTION);
		}

	}

}//StatisticsFile Class	


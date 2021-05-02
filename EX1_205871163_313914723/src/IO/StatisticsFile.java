package IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class StatisticsFile {
	
	public StatisticsFile() throws FileNotFoundException {
			
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose a directory to save your file: ");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        String str = null;
        
        int returnValue = jfc.showSaveDialog(null);
       
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (jfc.getSelectedFile().isDirectory()) {
                str =  jfc.getSelectedFile().toString();
                
                //setting name to the file
                str = str+"\\statistic.csv";
            }
        }        
        
        File fos = new File(str);
		PrintWriter pw = new PrintWriter(fos);

		
		
		
		
		pw.close();

		
	}


}	


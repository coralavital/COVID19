package UI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MulticastSocket;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Country.Map;
import Simulation.Main;

/*
 * Representation of a Main class
 * @author Yoni Ifrah 313914723, Coral Avital 205871163
 *
 */
public class MainWindow extends JFrame {
	
	public  MapPanel mapPanel;
	public UserMenu userMenu;
	
	JSlider slider = new JSlider();
	
	// Inner class if Map panel
	public  class MapPanel extends JPanel {
		private Shape rect;
		private ArrayList<Shape> rectangles;

		/**
	     * Each rectangles is a settlement and if we clicked on settlement k will be the index of the settlement inside the 
	     * Stats Table, then we will open the statistics window with mentioning the k settlement clicked
	     */
		MapPanel(){
			repaint();//In order to update the GUI drawing
			addMouseListener((MouseListener) new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					for(Shape rect:rectangles) {

						Point p = e.getPoint();

						if(rect.contains(p)) {

							String sPoint = rect.toString();
							int k =- 1;
							int i;
							for(i =0; i < Main.getMap().getSettlements().length; i++) {
								if(Main.getMap().getSettlements()[i].getLocation().checkRect().equals(sPoint))
									k = i;
							}
							if (k >= 0) {
								
								Main.setStatistics(new StatisticsWindow());
								Main.getStatistics().getTable().getSelectionModel().setSelectionInterval(0,k);

							}

						}

					}
				}

			});
		}

		//The method that actually draws the map in our main frame
		protected void paintComponent(Graphics gr) {
			if(userMenu.getFlag()) {
				Graphics2D g = (Graphics2D)gr;
				rectangles = new ArrayList<>();
				int x,y,width,height;
				int x1,x2,y1,y2;
				int i = 0;
				Color color;
				String sName = "";


				super.paintComponent(g); //Clears the last paint

				//Draw the connection lines
				for(int l = 0;l < Main.getMap().getSettlements().length;++l){
					for(int j = 0; j < Main.getMap().getSettlements()[l].getLinkTo().size(); j++) {
						x1 = Main.getMap().getSettlements()[l].getLocation().getPosition().getX();
						y1 = Main.getMap().getSettlements()[l].getLocation().getPosition().getY();
						x2 = Main.getMap().getSettlements()[j].getLocation().getPosition().getX();
						y2 = Main.getMap().getSettlements()[j].getLocation().getPosition().getX();
						g.drawLine(x1,y1,x2,y2);
					}
				}

				//Go on all settlements and paint them with its color and name -> settlements list
				//For each settlement we will create a rectangle, lets assume there are 10 settlements
				while (i < Main.getMap().getSettlements().length) {

					sName = Main.getMap().getSettlements()[i].getName();
					x = Main.getMap().getSettlements()[i].getLocation().getPosition().getX();
					y = Main.getMap().getSettlements()[i].getLocation().getPosition().getY();
					width = Main.getMap().getSettlements()[i].getLocation().getSize().getWidth();
					height = Main.getMap().getSettlements()[i].getLocation().getSize().getHeight();
					g.setColor(Main.getMap().getSettlements()[i].calcuateRamzorGrade().getColorEnum());

					rect = new Rectangle(x,y,width,height);
					g.draw(rect); //Draw the settlement rectangle
					g.fill(rect); //Paint the rectangle with the settlement color
					FontMetrics fm = g.getFontMetrics();
					int stringx = x + (width - fm.stringWidth(sName)) / 2;; //Determine the X coordinate for the text
					int stringy = y + ((height - fm.getHeight()) / 2) + fm.getAscent(); //Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
					g.setColor(Color.BLACK);
					g.drawString(sName,stringx,stringy); //Draw the settlement name
					rectangles.add(rect); //Adds to the rectangles list
					++i;

					//Initialize x, y, width,height, color, name of the settlement

				}

				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}

			else
				return;
		}
	}
	
	/**
     * The constructor that initializes the entire main window and creates from it an object in front of which our main function will work
     */
	public MainWindow() {
		super("Main");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		mapPanel=  new MapPanel();
		setJMenuBar(userMenu = new UserMenu(this,mapPanel));//User menu
		// create mapPanel
		mapPanel.setPreferredSize(new Dimension(600, 600));
		mapPanel.setBackground(Color.WHITE);
		this.add(mapPanel); //Map panel}
		//Creating JSlider
		JLabel label = new JLabel("Current value: " + slider.getValue());

		//Set
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = slider.getValue();
				label.setText("current value: " + value);
			}

		});

		this.add(slider);
		this.add(label);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}
	
	/**
	 * getter function for userMenu
	 * @return: object, usermenu
	 */
	public UserMenu getUserMenu() {
		return userMenu;
	}
	
	/**
	  * getter function for mapPanel
	  * @return: object, mapPanel
	  */
	public MapPanel getMapPanel() {
		return mapPanel;
	}
	
	 /**
	  * getter function for slider
	  * @return: object, slider
	  */
	public JSlider getJSlider() {
		return slider;
	}
	
}
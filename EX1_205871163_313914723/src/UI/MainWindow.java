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


public class MainWindow extends JFrame {
	protected  MapPanel mapPanel;
	private UserMenu userMenu;


	// inner class if Map panel
	protected  class MapPanel extends JPanel {
		private Shape rect;
		private ArrayList<Shape> rectangles;


		MapPanel(){
			repaint();
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
							for(i =0; i< userMenu.getMap().getSettlements().length; i++) {
								if(userMenu.getMap().getSettlements()[i].getLocation().checkRect().equals(sPoint))
									k = i;
							}
							if (k >= 0) {
								StatisticsWindow statistics = new StatisticsWindow(userMenu.getMap());

								statistics.getTable().getSelectionModel().setSelectionInterval(0,k);
								statistics.getLocationOnScreen();
								//s.getTable().clearSelection();
							}

						}

					}
				}

			});
		}

		@Override
		protected void paintComponent(Graphics gr) {
			if(userMenu.getFlag()) {
				Graphics2D g = (Graphics2D)gr;
				rectangles = new ArrayList<>();
				int x,y,width,height;
				int x1,x2,y1,y2;
				int i = 0;
				Color color;
				String sName = "";


				super.paintComponent(g); // clears the last paint

				//draw the connection lines
				for(int l = 0;l < userMenu.getMap().getSettlements().length;++l){
					for(int j = 0; j < userMenu.getMap().getSettlements()[l].getLinkTo().size(); j++) {
						x1 = userMenu.getMap().getSettlements()[l].getLocation().getPosition().getX();
						y1 = userMenu.getMap().getSettlements()[l].getLocation().getPosition().getY();
						x2 = userMenu.getMap().getSettlements()[j].getLocation().getPosition().getX();
						y2 = userMenu.getMap().getSettlements()[j].getLocation().getPosition().getX();
						g.drawLine(x1,y1,x2,y2);
					}
				}

				// go on all settlements and paint them with its color and name -> settlements list
				// for each settlement we will create a rectangle, lets assume there are 10 settlements
				while (i < userMenu.getMap().getSettlements().length) {

					sName = userMenu.getMap().getSettlements()[i].getName();
					x = userMenu.getMap().getSettlements()[i].getLocation().getPosition().getX();
					y = userMenu.getMap().getSettlements()[i].getLocation().getPosition().getY();
					width = userMenu.getMap().getSettlements()[i].getLocation().getSize().getWidth();
					height = userMenu.getMap().getSettlements()[i].getLocation().getSize().getHeight();
					g.setColor(userMenu.getMap().getSettlements()[i].getRamzorColor().getColorEnum());

					rect = new Rectangle(x,y,width,height);
					g.draw(rect); // draw the settlement rectangle
					g.fill(rect); // paint the rectangle with the settlement color
					FontMetrics fm = g.getFontMetrics();
					int stringx = x + (width - fm.stringWidth(sName)) / 2;; // Determine the X coordinate for the text
					int stringy = y + ((height - fm.getHeight()) / 2) + fm.getAscent(); // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
					g.setColor(Color.BLACK);
					g.drawString(sName,stringx,stringy); // draw the settlement name
					rectangles.add(rect); // adds to the rectangles list
					++i;

					// initialize x, y, width,height, color, name of the settlement

				}

				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			}

			else
				return;
		}
	}
	public MainWindow() {
		super("Main");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		mapPanel=  new MapPanel();
		setJMenuBar(userMenu = new UserMenu(this,mapPanel));// User menu
		// create mapPanel
		mapPanel.setPreferredSize(new Dimension(600, 600));
		mapPanel.setBackground(Color.WHITE);
		this.add(mapPanel); // map panel}
		// creating JSlider
		JSlider slider = new JSlider();
		JLabel label = new JLabel("Current value: " + slider.getValue());

		// set
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = slider.getValue();
				label.setText("current value: " + value);
				// we will need to add sleep between the ticks
			}

		});

		this.add(slider);
		this.add(label);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}

	public static void main(String args[]) {

		MainWindow main = new MainWindow();
	}

}
package UI;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class RGBDecorator {
	
	private Color color;

	public RGBDecorator(Color a, Color b) {
		int red = (a.getRed() + b.getRed()) / 2;
		int green = (a.getGreen() + b.getGreen()) / 2;
		int blue = (a.getBlue() + b.getBlue()) / 2;
		color = new Color(red,green,blue);
	}
	
	public void setGrephicsColor(Graphics2D g) {
		g.setColor(color.brighter());
	}

}

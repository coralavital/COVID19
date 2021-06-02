package UI;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Decorator {
	
	protected static Color c;

	public static Color createColor(Color a, Color b) {
		c = new Color((a.getRGB() + b.getRGB()) / 2);
		return c;
	}
	
	public static Graphics2D setGrephics(Graphics2D g) {
		g.setColor(c);
		return g;
	}
}

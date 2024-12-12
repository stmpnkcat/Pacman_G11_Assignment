import java.awt.*;
import java.io.*;

public class Fonts {

	public static Font arcade_classic;

	public static Font font_big;
	public static Font font_small;
	
	public static void createFonts () {
		//https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			
			arcade_classic = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/arcade_classic.ttf"));
			ge.registerFont(arcade_classic);
			
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
		
		font_big = arcade_classic.deriveFont(40f);
		font_small = arcade_classic.deriveFont(30f);
	}
}

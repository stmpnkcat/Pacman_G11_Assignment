import java.awt.*;
import java.io.*;

public class Fonts {

	public static Font karmatic_arcade;
	public static Font arcade_classic;
	
	public static Font title_font;
	public static Font play_font;
	public static Font score_font;
	
	public static void createFonts () {
		//https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			
			karmatic_arcade = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/karmatic_arcade.ttf"));
			ge.registerFont(karmatic_arcade);
			
			arcade_classic = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/arcade_classic.ttf"));
			ge.registerFont(arcade_classic);
			
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}

		title_font = karmatic_arcade.deriveFont(70f);
		play_font = arcade_classic.deriveFont(40f);
		score_font = arcade_classic.deriveFont(30f);
	}
}

import java.awt.*;
import java.io.*;

/*
 * This class creates the fonts for other classes to use
 * It has to set the font size for those classes to use
 */
public class Fonts {

	// Declare the font
	public static Font arcade_classic;

	// Declare the big font and the small font
	public static Font font_big;
	public static Font font_small;
	
	// This method creates the fonts and is called at the start of the program
	public static void createFonts () {
		
		// Try/Catch for reading the file
		// https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
		try {
			
			// Create a new graphics environment for the font
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			
			// Set the font using the file
			arcade_classic = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/arcade_classic.ttf"));
			
			// Register the font using the graphics environment
			ge.registerFont(arcade_classic);
			
		} catch (IOException e) {
			
		    e.printStackTrace();
		    
		} catch(FontFormatException e) {
			
		    e.printStackTrace();
		    
		}
		
		// Create the big font and the small font 
		font_big = arcade_classic.deriveFont(40f);
		font_small = arcade_classic.deriveFont(30f);
		
	}
	
}

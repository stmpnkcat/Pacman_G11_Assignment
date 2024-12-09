import javax.swing.ImageIcon;

/*
 * This class creates the images to be used by the other classes
 */
public class Icons {
	
	// Title screen background
	public static final ImageIcon TITLE = new ImageIcon("images/title.png");
	public static final ImageIcon TITLE_BACKGROUND = new ImageIcon("images/title_background.png");
	
	// Creating images for each of the tiles
	public static final ImageIcon WALL = new ImageIcon("images/Wall.bmp");
	public static final ImageIcon FOOD = new ImageIcon("images/Food.bmp");
	public static final ImageIcon BLANK = new ImageIcon("images/Black.bmp");
	public static final ImageIcon DOOR = new ImageIcon("images/Black.bmp");
	public static final ImageIcon CHERRY = new ImageIcon("images/Cherry.bmp");
	public static final ImageIcon SKULL = new ImageIcon("images/Skull.bmp");

	// This method creates images for pacman, in each of the cardinal directions
	public static final ImageIcon[] PACMAN = {
			new ImageIcon("images/PacMan0.gif"),
			new ImageIcon("images/PacMan1.gif"),
			new ImageIcon("images/PacMan2.gif"),
			new ImageIcon("images/PacMan3.gif"),
	};
	
	// This method creates images for the ghosts, one for each of them
	public static final ImageIcon[] GHOST = {
			new ImageIcon("images/Ghost0.bmp"),
			new ImageIcon("images/Ghost1.bmp"),
			new ImageIcon("images/Ghost2.bmp"),
			new ImageIcon("images/Ghost3.bmp"),
	};
}

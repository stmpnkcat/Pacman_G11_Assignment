import javax.swing.ImageIcon;

/*
 * This class creates the images to be used by the other classes
 */
public class Icons {
	
	// Logo icon
	public static final ImageIcon LOGO = new ImageIcon("images/logo.png");
	
	// Title screen background
	public static final ImageIcon TITLE = new ImageIcon("images/title.png");
	public static final ImageIcon TITLE_BACKGROUND = new ImageIcon("images/title_background.png");
	
	// Creating images for each of the tiles
	public static final ImageIcon WALL = new ImageIcon("images/Wall.png");
	public static final ImageIcon FOOD = new ImageIcon("images/Food.png");
	public static final ImageIcon BIG_FOOD = new ImageIcon("images/Big_Food.png");
	public static final ImageIcon BLANK = new ImageIcon("images/Black.png");
	public static final ImageIcon DOOR = new ImageIcon("images/Black.png");
	public static final ImageIcon CHERRY = new ImageIcon("images/Cherry.png");
	public static final ImageIcon SKULL = new ImageIcon("images/Skull.png");

	// This method creates images for pacman, in each of the cardinal directions
	public static final ImageIcon[] PACMAN = {
			new ImageIcon("images/PacMan0.gif"),
			new ImageIcon("images/PacMan1.gif"),
			new ImageIcon("images/PacMan2.gif"),
			new ImageIcon("images/PacMan3.gif"),
	};
	
	// This method creates images for the ghosts, one for each of them
	public static final ImageIcon[] GHOST = {
			new ImageIcon("images/Ghost0.png"),
			new ImageIcon("images/Ghost1.png"),
			new ImageIcon("images/Ghost2.png"),
			new ImageIcon("images/Ghost3.png"),
	};
}

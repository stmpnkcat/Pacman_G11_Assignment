/*
 *
 * Name: Daniel Cheng
 * 
 * Date: 2024/12/13
 * 
 * Course: ICS3U1-05 Mr.Fernandes
 * 
 * Title: PacMan - Daniel Cheng
 * 
 * Description:
 * 
 * - Welcome to Daniel's PacMan Game, a retro video game based off Pac-Man
 * - Navigate the title page options with Tab and press Space to select option
 * - Use Arrow Keys to guide PacMan through a maze, avoiding Ghosts and collecting points
 * - Gaze at the high scores of others in the leader board page
 * - Reach the top of the leader board, where you can enter your name (less than 9 letters long)
 * 
 * Major Skills:
 * 
 * - Swing
 * 		- Basic components (JLabel, JButton, JPanel, etc)
 * 		- Layouts (GridLayout, GridBagLayout, BoxLayout, FlowLayout, BorderLayout, null layout)
 * 		- GridBagConstraints (for GridBagLayout)
 * 		- SwingConstants (for JLabel centering)
 * 
 * - "AI" Pathfinding (A* Pathfinding)
 * 		- Manhattan Distance
 * 		- ArrayList
 * 		- Maze Traversal
 * 		- Customizable penalty for going on a path with another ghost (prevents conga lines)
 * 		- Comparing using comparator
 * 		- Stack
 * 
 * - Data Structures
 * 		- Array
 * 		- Map
 * 		- List
 * 		- LinkedHashMap
 * 
 * - Files
 *		- File Reading
 *		- File Writing (appending)
 *
 * - Custom fonts
 * 		- GraphicsEnvironment
 * 
 * - Object Oriented Programming
 * 		- Constructors
 *		- Fields
 *		- Getters/Setters
 *		- Utility Methods
 *		- Extending another object
 *
 * - KeyListener
 * 		- Getting the arrows keys
 * 
 * - Playing sound
 * 		- AudioInputStream
 * 		- AudioSystem
 * 
 * - ActionListener
 * - FocusListener
 * - ImageIcon
 * - Math.random
 * - Timers
 * - Constants
 * - Try/Catch
 * - Collections custom comparator
 * - Array Traversal
 * 
 * Basic Features:
 * 
 * - Score
 * - PacMan cannot enter the ghosts' house
 * - Ghosts get out of the house easier (Escalator)
 * - Title Screen
 * - Sounds/Music
 * - Lives
 * - Power pellets
 * - Cherry bonus item
 * 
 * Advanced Features:
 * 
 * - Ghost pathfinding "AI"
 * - Leaderboard scores with names
 * 
 * Areas of Concern: 
 * 
 * - none
 * 
 * References:
 * 
 * - A* Algorithm: https://www.youtube.com/watch?v=-L-WgKMFuhE
 * - Playing Sound: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
 * - Custom Fonts: https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
 * - Appending to an existing file: https://stackoverflow.com/questions/1625234/how-to-append-text-to-an-existing-file-in-java
 * - Sorting HashMap: https://www.geeksforgeeks.org/sorting-a-hashmap-according-to-values/
 * 
 * - Base Game: Nicholas Fernandes
 * 
 */

/*
 * This is the application lass that runs the game
 */
public class PacManGame {

	// Game settings
	public static final int SCREEN_X = 600;
	public static final int SCREEN_Y = 650;
	public static final int ROWS = 25;
	public static final int COLUMNS = 27;
	public static final int TIME_BETWEEN_TICKS = 50;
	public static final int DEATH_DURATION = 2500;
	public static final int NEIGHBOURING_PENALTY = 9;
	public static final int DEFAULT_LIVES = 3;
	public static final int VULNERABLE_DURATION = 10000;
	public static final double TIME_BEFORE_WARNING = (double) VULNERABLE_DURATION * 0.5;
	public static final int CHERRY_SPAWN_TIME = 10000;
	
	// ID settings
	public static final char ID_PLAYER = 'P';
	public static final char ID_GHOST = 'G';
	public static final char ID_PUSHER = '^';
	public static final char ID_WALL = 'W';
	public static final char ID_FOOD = 'F';
	public static final char ID_BIG_FOOD = 'B';
	public static final char ID_CHERRY = 'C';
	public static final char ID_DOOR = 'D';
	public static final char ID_EMPTY = 'E';
	
	// Scoring settings
	public static final int SCORE_PELLET = 10;
	public static final int SCORE_BIG = 50;
	public static final int SCORE_CHERRY = 200;
	public static final int SCORE_GHOST = 100;
	
	// Leaderboard settings
	public static final int LEADERBOARD_MAX_ROWS = 5;
	
	// This method runs when the program is run
	public static void main (String[] args) {
		
		Fonts.createFonts();
		
		// This calls the constructor of PacManGUI, initializing the GUI screen
		new TitlePage();
		
	}
	
}

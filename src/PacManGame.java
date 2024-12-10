/*
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
 * Major Skills:
 * 
 * Basic Features:
 * - Score
 * - PacMan cannot enter the ghosts' house
 * - Ghosts get out of the house easier (Escalator)
 * - Title Screen
 * - Music
 * - Lives
 * 
 * Advanced Features:
 * - Ghost pathfinding "AI"
 * 
 * Areas of Concern: none
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
	public static final int TIME_BETWEEN_TICKS = 10;
	public static final int DEATH_DURATION = 2500;
	public static final int NEIGHBOURING_PENALTY = 9;
	public static final int DEFAULT_LIVES = 3;
	public static final int VULNERABLE_DURATION = 10000;
	
	// ID settings
	public static final char ID_PLAYER = 'P';
	public static final char ID_GHOST = 'G';
	public static final char ID_PUSHER = '^';
	public static final char ID_WALL = 'W';
	public static final char ID_FOOD = 'F';
	public static final char ID_BIG_FOOD = 'B';
	public static final char ID_DOOR = 'D';
	public static final char ID_EMPTY = 'E';
	
	// Scoring settings
	public static final int PELLET_SCORE = 10;
	public static final int BIG_SCORE = 50;
	public static final int CHERRY_SCORE = 300;
	public static final int GHOST_SCORE = 200;
	
	// This method runs when the program is run
	public static void main (String[] args) {
		
		Fonts.createFonts();
		
		// This calls the constructor of PacManGUI, initializing the GUI screen
		new TitlePage();
		
	}
}
